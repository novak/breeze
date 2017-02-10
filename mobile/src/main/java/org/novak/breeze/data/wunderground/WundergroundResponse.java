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

import java.util.Calendar;
import java.util.List;

class WundergroundResponse {
    Forecast forecast;

    @Json(name = "current_observation")
    WundergroundConditions currentConditions;

    @Json(name = "sun_phase")
    SunPhase sunPhase;

    static class Forecast {
        @Json(name = "simpleforecast")
        SimpleForecast simpleForecast;
    }

    static class SimpleForecast {
        @Json(name = "forecastday")
        List<WundergroundForecast> forecasts;
    }

    static class SunPhase {
        SunTime sunrise;
        SunTime sunset;
    }

    static class SunTime {
        public String hour;
        public String minute;

        long toMilliseconds() {
            int hour = Integer.valueOf(this.hour);
            int minute = Integer.valueOf(this.minute);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            return calendar.getTimeInMillis();
        }
    }
}
