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

import org.novak.breeze.data.Forecast;

class WundergroundForecast {
    private String conditions;
    private String icon;

    private Date date;
    private High high;
    private Low low;

    private int pop;

    private static class Date {
        String epoch;
    }

    private static class High {
        String fahrenheit;
        String celsius;
    }

    private static class Low {
        String fahrenheit;
        String celsius;
    }

    int getPop() {
        return pop;
    }

    Forecast toForecast(String units) {
        return new Forecast.Builder()
                .setIconResource(WundergroundIcon.toResource(icon))
                .setMinTemperature(units.equals("us") ? Math.round(Float.valueOf(low.fahrenheit)) :
                        Math.round(Float.valueOf(low.celsius)))
                .setMaxTemperature(units.equals("us") ? Math.round(Float.valueOf(high.fahrenheit)) :
                        Math.round(Float.valueOf(high.celsius)))
                .setTime(Long.valueOf(date.epoch))
                .build();
    }
}
