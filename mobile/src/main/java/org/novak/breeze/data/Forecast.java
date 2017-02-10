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

import org.novak.breeze.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Forecast {
    private int iconResource;
    private long time;

    private int maxTemperature;
    private int minTemperature;

    public int getIconResource() {
        return iconResource;
    }

    public String getDay() {
        Date date = new Date(time * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat("EEE", Locale.getDefault());

        return formatter.format(date).toUpperCase();
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public static class Builder {
        private Forecast forecast;

        public Builder() {
            forecast = new Forecast();
        }

        public Builder setIconResource(int resource) {
            /* For some reason night icon resources are returned for forecasts sometimes. Convert to day resources. */
            if (resource == R.drawable.ic_partly_cloudy_night) {
                resource = R.drawable.ic_partly_cloudy_day;
            } else if (resource == R.drawable.ic_clear_night) {
                resource = R.drawable.ic_partly_cloudy_day;
            }

            forecast.iconResource = resource;
            return this;
        }

        public Builder setTime(long time) {
            forecast.time = time;
            return this;
        }

        public Builder setMaxTemperature(int maxTemperature) {
            forecast.maxTemperature = maxTemperature;
            return this;
        }

        public Builder setMinTemperature(int minTemperature) {
            forecast.minTemperature = minTemperature;
            return this;
        }

        public Forecast build() {
            return forecast;
        }
    }
}
