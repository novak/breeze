/*
 * Copyright 2017 Michael Novak <michael.novakjr@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.novak.breeze.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.novak.breeze.R;
import org.novak.breeze.data.Weather;
import org.novak.breeze.data.WeatherRepository;
import org.novak.breeze.util.ThemeManager;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

public class WeatherActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    public static final String EXTRA_LOCATION_LABEL = "location_label";
    public static final String EXTRA_THEME = "theme";
    public static final String EXTRA_RESOLVING_ERROR = "resolving_error";

    private static final String DIALOG_ERROR = "dialog_error";
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private boolean isResolvingError = false;

    private GoogleApiClient client;
    private String locationLabel;

    private ThemeManager.WeatherTheme theme = ThemeManager.WeatherTheme.DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (savedInstanceState != null) {
            locationLabel = savedInstanceState.getString(EXTRA_LOCATION_LABEL);
            isResolvingError = savedInstanceState.getBoolean(EXTRA_RESOLVING_ERROR, false);

            theme = ThemeManager.WeatherTheme.values()[savedInstanceState.getInt(EXTRA_THEME)];
            updateTheme(theme);
        } else {
            CurrentConditionsFragment currentConditionsFragment = new CurrentConditionsFragment();
            ForecastFragment forecastFragment = new ForecastFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_current_conditions, currentConditionsFragment)
                    .add(R.id.frame_forecast, forecastFragment)
                    .commit();
        }

        if (locationLabel != null) {
            getToolbar().setTitle(locationLabel);
        } else {
            getToolbar().setTitle(R.string.label_current_location);
        }

        if (client == null) {
            client = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected void onStart() {
        client.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        client.disconnect();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        Toolbar toolbar = getToolbar();
        toolbar.inflateMenu(R.menu.main_menu_items);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_refresh:
                determineCurrentLocation();
                return true;
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(EXTRA_LOCATION_LABEL, locationLabel);
        outState.putInt(EXTRA_THEME, theme.ordinal());
        outState.putBoolean(EXTRA_RESOLVING_ERROR, isResolvingError);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConnected(@Nullable Bundle connectionHint) {
        determineCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        //TODO: implement case where play services become temporarily unavailable.
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!isResolvingError) {
            isResolvingError = true;

            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
                } catch (IntentSender.SendIntentException e) {
                    client.connect();
                }
            } else {
                showPlayServicesErrorDialog(connectionResult.getErrorCode());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        if (results[0] == PackageManager.PERMISSION_GRANTED) {
            determineCurrentLocation();
        } else {
            //TODO: implement case where user denied access to location.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            isResolvingError = false;

            if (resultCode == RESULT_OK) {
                if (!client.isConnecting() && !client.isConnected()) {
                    client.connect();
                }
            }
        }
    }

    private void showPlayServicesErrorDialog(int errorCode) {
        ErrorDialogFragment errorFragment = new ErrorDialogFragment();

        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        errorFragment.setArguments(args);

        errorFragment.show(getSupportFragmentManager(), "error_dialog");
    }

    private void determineCurrentLocation() {
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(client);

            if (location != null) {
                new GeocodeAsyncTask(new Geocoder(this, Locale.getDefault()), this).execute(location);
                new ForecastAsyncTask(this).execute(location);
            } else {
                //TODO: implement case where current location is unavailable.
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }
    }

    private void updateTheme(ThemeManager.WeatherTheme theme) {
        this.theme = theme;

        ThemeManager.applyTheme(getWindow(), theme);
        ThemeManager.applyTheme(getToolbar(), theme);
    }

    void onDialogDismissed() {
        isResolvingError = false;
    }

    static class ForecastAsyncTask extends AsyncTask<Location, Void, Weather> {
        private WeakReference<WeatherActivity> activityRef;

        ForecastAsyncTask(WeatherActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        protected Weather doInBackground(Location... locations) {
            WeatherActivity activity = activityRef.get();

            if (activity != null) {
                WeatherRepository repository = new WeatherRepository(activity);
                return repository.getForecast(String.valueOf(locations[0].getLatitude()),
                        String.valueOf(locations[0].getLongitude()));
            }

            return null;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            WeatherActivity activity = activityRef.get();

            if (activity != null) {
                CurrentConditionsFragment fragment = (CurrentConditionsFragment) activity.getSupportFragmentManager()
                        .findFragmentById(R.id.frame_current_conditions);

                if (fragment != null) {
                    fragment.updateCurrentConditions(weather.currentConditions, weather.getTheme());
                }

                ForecastFragment forecastFragment = (ForecastFragment) activity.getSupportFragmentManager()
                        .findFragmentById(R.id.frame_forecast);

                if (forecastFragment != null) {
                    forecastFragment.updateForecast(weather.dailyForecasts, weather.getTheme());
                }

                activity.updateTheme(weather.getTheme());
            }
        }
    }

    static class GeocodeAsyncTask extends AsyncTask<Location, Void, String> {
        private Geocoder geocoder;
        private WeakReference<WeatherActivity> activityRef;

        GeocodeAsyncTask(Geocoder geocoder, WeatherActivity activity) {
            this.geocoder = geocoder;
            activityRef = new WeakReference<>(activity);
        }

        @Override
        protected String doInBackground(Location... locations) {
            Location location = locations[0];
            String locationLabel = null;

            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                if (addresses != null) {
                    locationLabel = addresses.get(0).getLocality();
                }
            } catch (IOException e) {
                Log.e("Breeze", "Unable to geocode the provided location: " + location);
            }

            return locationLabel;
        }

        @Override
        protected void onPostExecute(String locationLabel) {
            WeatherActivity activity = activityRef.get();

            if (activity != null) {
                activity.locationLabel = locationLabel;
                activity.getToolbar().setTitle(locationLabel);
            }
        }
    }

    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() { }

        @Override @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int errorCode = getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((WeatherActivity) getActivity()).onDialogDismissed();
        }
    }
}
