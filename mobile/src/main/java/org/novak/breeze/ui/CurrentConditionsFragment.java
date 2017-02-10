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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.novak.breeze.R;
import org.novak.breeze.data.Conditions;
import org.novak.breeze.util.ThemeManager;

import java.util.Locale;

public class CurrentConditionsFragment extends Fragment {
    private ImageView currentConditionsIconView;
    private TextView currentConditionsTextView;
    private TextView temperatureView;

    private TextView humidityView;
    private TextView windView;
    private TextView precipitationView;

    private ImageView attributionView;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_conditions, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentConditionsIconView = (ImageView) view.findViewById(R.id.image_view_current_conditions);
        currentConditionsTextView = (TextView) view.findViewById(R.id.text_view_current_conditions);
        temperatureView = (TextView) view.findViewById(R.id.text_view_temperature);

        humidityView = (TextView) view.findViewById(R.id.text_view_humidity);
        windView = (TextView) view.findViewById(R.id.text_view_wind);
        precipitationView = (TextView) view.findViewById(R.id.text_view_chance_precipitation);

        attributionView = (ImageView) view.findViewById(R.id.image_view_data_attribution);
    }

    public void updateCurrentConditions(Conditions currentConditions, ThemeManager.WeatherTheme theme) {
        SharedPreferences preferences = getContext().getSharedPreferences(SettingsFragment.PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        String units = preferences.getString(SettingsFragment.KEY_UNITS, "us");

        temperatureView.setText(getString(R.string.temperature, currentConditions.getTemperature()));
        currentConditionsTextView.setText(currentConditions.getSummary());
        currentConditionsIconView.setImageResource(currentConditions.getIconResource());

        ThemeManager.applyTheme(temperatureView, theme);
        ThemeManager.applyTheme(currentConditionsIconView, theme);
        ThemeManager.applyTheme(currentConditionsTextView, theme);
        ThemeManager.applyTheme(windView, theme);
        ThemeManager.applyTheme(humidityView, theme);
        ThemeManager.applyTheme(precipitationView, theme);
        ThemeManager.applyTheme(attributionView, theme);

        String windSpeed = getString(R.string.wind_speed, currentConditions.getWindSpeed());

        if (units.equals("si")) {
            windSpeed = getString(R.string.wind_speed_si, currentConditions.getWindSpeed());
        }

        setStyledText(windView,
                windSpeed,
                getString(R.string.wind_description));

        setStyledText(humidityView,
                getString(R.string.humidity_percentage, currentConditions.getHumidity()),
                getString(R.string.humidity_description));

        if (currentConditions.getPrecipitationProbability() != 0) {
            setStyledText(precipitationView,
                    getString(R.string.precipitation_percentage, currentConditions.getPrecipitationProbability()),
                    getString(R.string.precipitation_description, currentConditions.getPrecipitationType()));
        } else {
            precipitationView.setVisibility(View.GONE);
        }

        attributionView.setImageResource(R.drawable.ic_darksky_logo);
        attributionView.setContentDescription(getString(R.string.dark_sky_attribution));
    }

    private void setStyledText(TextView view, String mediumString, String lightString) {
        SpannableString spannable = new SpannableString(String.format(Locale.getDefault(), "%s %s", mediumString,
                lightString));

        TypefaceSpan lightSpan = new TypefaceSpan("sans-serif-light");
        TypefaceSpan mediumSpan = new TypefaceSpan("sans-serif-medium");

        spannable.setSpan(mediumSpan, 0, mediumString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannable.setSpan(lightSpan, mediumString.length() + 1, spannable.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        view.setText(spannable);
    }
}
