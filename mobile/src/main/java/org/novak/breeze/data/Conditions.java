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

import android.support.annotation.DrawableRes;

import org.novak.breeze.R;
import org.novak.breeze.util.ThemeManager;

public class Conditions {
    @DrawableRes
    private int iconResource;
    private String summary;

    private int temperature;
    private int apparentTemperature;

    private int precipitationProbability;
    private String precipitationType;

    private int humidity;
    private int windSpeed;

    public int getIconResource() {
        switch (iconResource) {
            case R.drawable.ic_clear_day:
                return R.drawable.ic_clear_day_100dp;
            case R.drawable.ic_clear_night:
                return R.drawable.ic_clear_night_100dp;
            case R.drawable.ic_cloudy:
                return R.drawable.ic_cloudy_100dp;
            case R.drawable.ic_fog:
                return R.drawable.ic_fog_100dp;
            case R.drawable.ic_hail:
                return R.drawable.ic_hail_100dp;
            case R.drawable.ic_partly_cloudy_day:
                return R.drawable.ic_partly_cloudy_day_100dp;
            case R.drawable.ic_partly_cloudy_night:
                return R.drawable.ic_partly_cloudy_night_100dp;
            case R.drawable.ic_rain:
                return R.drawable.ic_rain_100dp;
            case R.drawable.ic_sleet:
                return R.drawable.ic_sleet_100dp;
            case R.drawable.ic_snow:
                return R.drawable.ic_snow_100dp;
            case R.drawable.ic_thunderstorm:
                return R.drawable.ic_thunderstorm_100dp;
            case R.drawable.ic_tornado:
                return R.drawable.ic_tornado_100dp;
            case R.drawable.ic_wind:
                return R.drawable.ic_wind_100dp;
            default:
                return R.drawable.ic_clear_day_100dp;
        }
    }

    public String getSummary() {
        return summary;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getApparentTemperature() {
        return apparentTemperature;
    }

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    public String getPrecipitationType() {
        return precipitationType;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    ThemeManager.WeatherTheme getTheme() {
        switch (iconResource) {
            case R.drawable.ic_clear_day:
            case R.drawable.ic_partly_cloudy_day:
            case R.drawable.ic_wind:
                return ThemeManager.WeatherTheme.DEFAULT;
            case R.drawable.ic_clear_night:
            case R.drawable.ic_partly_cloudy_night:
                return ThemeManager.WeatherTheme.NIGHT;
            case R.drawable.ic_cloudy:
            case R.drawable.ic_fog:
            case R.drawable.ic_hail:
            case R.drawable.ic_rain:
            case R.drawable.ic_thunderstorm:
            case R.drawable.ic_tornado:
                return ThemeManager.WeatherTheme.CLOUDY;
            case R.drawable.ic_sleet:
            case R.drawable.ic_snow:
                return ThemeManager.WeatherTheme.SNOW;
            default:
                return ThemeManager.WeatherTheme.DEFAULT;
        }
    }

    public static class Builder {
        private Conditions conditions;

        public Builder() {
            conditions = new Conditions();
        }

        public Builder setIconResource(int resource) {
            conditions.iconResource = resource;
            return this;
        }

        public Builder setSummary(String summary) {
            conditions.summary = summary;
            return this;
        }

        public Builder setTemperature(int temperature) {
            conditions.temperature = temperature;
            return this;
        }

        public Builder setApparentTemperature(int apparentTemperature) {
            conditions.apparentTemperature = apparentTemperature;
            return this;
        }

        public Builder setPrecipitationProbability(int precipitationProbability) {
            conditions.precipitationProbability = precipitationProbability;
            return this;
        }

        public Builder setPrecipitationType(String precipitationType) {
            conditions.precipitationType = precipitationType;
            return this;
        }

        public Builder setHumidity(int humidity) {
            conditions.humidity = humidity;
            return this;
        }

        public Builder setWindSpeed(int windSpeed) {
            conditions.windSpeed = windSpeed;
            return this;
        }

        public Conditions build() {
            return conditions;
        }
    }
}
