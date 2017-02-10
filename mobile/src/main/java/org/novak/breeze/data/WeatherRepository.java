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

package org.novak.breeze.data;

import android.content.Context;
import android.content.SharedPreferences;

import org.novak.breeze.R;
import org.novak.breeze.data.darksky.DarkSkyDataSource;
import org.novak.breeze.data.wunderground.WundergroundDataSource;
import org.novak.breeze.ui.SettingsFragment;

public class WeatherRepository {
    private WeatherDataSource activeDataSource;

    public WeatherRepository(Context context) {
        String darkSkySource = context.getString(R.string.preference_value_dark_sky);
        String usUnits = context.getString(R.string.preference_value_us);

        SharedPreferences preferences = context.getSharedPreferences(SettingsFragment.PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        String dataSource = preferences.getString(SettingsFragment.KEY_DATA_SOURCE, darkSkySource);
        String units = preferences.getString(SettingsFragment.KEY_UNITS, usUnits);

        if (dataSource.equals(darkSkySource)) {
            activeDataSource = new DarkSkyDataSource(units);
        } else {
            activeDataSource = new WundergroundDataSource(units);
        }
    }

    public Weather getForecast(String latitude, String longitude) {
        return activeDataSource.getForecast(latitude, longitude);
    }
}
