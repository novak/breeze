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

package org.novak.breeze.data.wunderground;

import com.squareup.moshi.Json;

import org.novak.breeze.data.Conditions;

class WundergroundConditions {
    private String weather;
    private String icon;

    @Json(name = "temp_f")
    private float temperature;

    @Json(name = "temp_c")
    private float temperatureCelsius;

    @Json(name = "feelslike_f")
    private String apparentTemperature;

    @Json(name = "feelslike_c")
    private String apparentTemperatureCelsius;

    @Json(name = "wind_mph")
    private float wind;

    @Json(name = "wind_kph")
    private float windKilometers;

    @Json(name = "relative_humidity")
    private String humidity;

    @Json(name = "precip_today_in")
    private String precipitation;

    @Json(name = "precip_today_metric")
    private String precipitationMetric;

    /**
     * Takes the weather underground forecast and builds a breeze forecast. The chance of precipitation is not provided
     * in the current conditions on weather underground so the forecast for the current day is provided so the value
     * can be set with the rest of the current conditions.
     *
     * @param currentForecast The weather underground forecast for the current day.
     * @return The breeze forecast based on weather underground data.
     */
    Conditions toConditions(WundergroundForecast currentForecast, String units) {
        return new Conditions.Builder()
                .setIconResource(WundergroundIcon.toResource(icon))
                .setSummary(weather)
                .setTemperature(units.equals("us") ? Math.round(temperature) : Math.round(temperatureCelsius))
                .setApparentTemperature(units.equals("us") ? Math.round(Float.valueOf(apparentTemperature)) :
                        Math.round(Float.valueOf(apparentTemperatureCelsius)))
                .setWindSpeed(units.equals("us") ? Math.round(wind) : Math.round(windKilometers))
                .setHumidity(Math.round(Float.valueOf(humidity.replaceAll("%", ""))))
                .setPrecipitationProbability(currentForecast.getPop())
                .setPrecipitationType("precipitation")
                .build();
    }
}
