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

import org.novak.breeze.data.Conditions;

class DarkSkyConditions {
    private String icon;
    private String summary;

    private float temperature;
    private float apparentTemperature;

    private float precipProbability;
    private String precipType;

    private float humidity;
    private float windSpeed;
    private float cloudCover;

    public String getIcon() {
        return icon;
    }

    public String getSummary() {
        return summary;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getApparentTemperature() {
        return apparentTemperature;
    }

    public float getPrecipProbability() {
        return precipProbability;
    }

    public String getPrecipType() {
        return precipType;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public float getCloudCover() {
        return cloudCover;
    }

    public Conditions toConditions() {
        return new Conditions.Builder()
                .setIconResource(DarkSkyIcon.toResource(icon))
                .setSummary(summary)
                .setTemperature(Math.round(temperature))
                .setApparentTemperature(Math.round(apparentTemperature))
                .setPrecipitationProbability(Math.round(precipProbability * 100))
                .setPrecipitationType(precipType)
                .setHumidity(Math.round(humidity * 100))
                .setWindSpeed(Math.round(windSpeed))
                .build();
    }
}
