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

package org.novak.breeze.data.darksky;

import org.novak.breeze.data.Forecast;

class DarkSkyForecast {
    private String icon;
    private long time;

    private float temperatureMax;
    private float temperatureMin;
    private float apparentTemperatureMax;
    private float apparentTemperatureMin;

    private long sunriseTime;
    private long sunsetTime;

    public String getIcon() {
        return icon;
    }

    public long getTime() {
        return time;
    }

    public float getTemperatureMax() {
        return temperatureMax;
    }

    public float getTemperatureMin() {
        return temperatureMin;
    }

    public float getApparentTemperatureMax() {
        return apparentTemperatureMax;
    }

    public float getApparentTemperatureMin() {
        return apparentTemperatureMin;
    }

    public long getSunrise() {
        return sunriseTime * 1000;
    }

    public long getSunset() {
        return sunsetTime * 1000;
    }

    Forecast toForecast() {
        return new Forecast.Builder()
                .setIconResource(DarkSkyIcon.toResource(icon))
                .setTime(time)
                .setMaxTemperature(Math.round(temperatureMax))
                .setMinTemperature(Math.round(temperatureMin))
                .build();
    }
}
