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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import org.novak.breeze.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static final String PREFERENCES_NAME = "org.novak.breeze";
    public static final String KEY_DATA_SOURCE = "data_source";
    public static final String KEY_UNITS = "measurement_units";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Preference dataSourcePreference = findPreference(KEY_DATA_SOURCE);
        dataSourcePreference.setOnPreferenceChangeListener(dataSourceListener);

        Preference unitPreference = findPreference(KEY_UNITS);
        unitPreference.setOnPreferenceChangeListener(unitsListener);

        SharedPreferences preferences = getPreferenceManager().getSharedPreferences();

        String dataSource = preferences.getString(KEY_DATA_SOURCE, getString(R.string.preference_value_dark_sky));
        updateDataSourceSummary(dataSourcePreference, dataSource);

        String units = preferences.getString(KEY_UNITS, getString(R.string.preference_value_us));
        updateUnitsSummary(unitPreference, units);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesName(PREFERENCES_NAME);
        addPreferencesFromResource(R.xml.preferences);
    }

    void updateDataSourceSummary(Preference preference, String dataSource) {
        String darkSkySource = getString(R.string.preference_value_dark_sky);

        if (dataSource.equals(darkSkySource)) {
            preference.setSummary(getString(R.string.preference_label_dark_sky));
        } else {
            preference.setSummary(getString(R.string.preference_label_wunderground));
        }
    }

    void updateUnitsSummary(Preference preference, String units) {
        String usUnits = getString(R.string.preference_value_us);

        if (units.equals(usUnits)) {
            preference.setSummary(getString(R.string.preference_label_us));
        } else {
            preference.setSummary(getString(R.string.preference_label_si));
        }
    }

    Preference.OnPreferenceChangeListener dataSourceListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            updateDataSourceSummary(preference, (String) newValue);
            return true;
        }
    };

    Preference.OnPreferenceChangeListener unitsListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            updateUnitsSummary(preference, (String) newValue);
            return true;
        }
    };
}
