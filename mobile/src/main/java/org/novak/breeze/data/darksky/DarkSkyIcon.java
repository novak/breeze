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

import org.novak.breeze.R;

class DarkSkyIcon {
    static final String CLEAR_DAY = "clear-day";
    static final String CLEAR_NIGHT = "clear-night";

    static final String RAIN = "rain";
    static final String SNOW = "snow";
    static final String SLEET = "sleet";

    static final String WIND = "wind";
    static final String FOG = "fog";

    static final String CLOUDY = "cloudy";
    static final String PARTLY_CLOUDY_NIGHT = "partly-cloudy-night";
    static final String PARTLY_CLOUDY_DAY = "partly-cloudy-day";

    static final String HAIL = "hail";
    static final String THUNDERSTORM = "thunderstorm";
    static final String TORNADO = "tornado";

    static int toResource(String darkSkyIcon) {
        if (darkSkyIcon.equals(CLEAR_DAY)) {
            return R.drawable.ic_clear_day;
        } else if (darkSkyIcon.equals(CLEAR_NIGHT)) {
            return R.drawable.ic_clear_night;
        } else if (darkSkyIcon.equals(RAIN)) {
            return R.drawable.ic_rain;
        } else if (darkSkyIcon.equals(SNOW)) {
            return R.drawable.ic_snow;
        } else if (darkSkyIcon.equals(SLEET)) {
            return R.drawable.ic_sleet;
        } else if (darkSkyIcon.equals(WIND)) {
            return R.drawable.ic_wind;
        } else if (darkSkyIcon.equals(FOG)) {
            return R.drawable.ic_fog;
        } else if (darkSkyIcon.equals(CLOUDY)) {
            return R.drawable.ic_cloudy;
        } else if (darkSkyIcon.equals(PARTLY_CLOUDY_DAY)) {
            return R.drawable.ic_partly_cloudy_day;
        } else if (darkSkyIcon.equals(PARTLY_CLOUDY_NIGHT)) {
            return R.drawable.ic_partly_cloudy_night;
        } else if (darkSkyIcon.equals(HAIL)) {
            return R.drawable.ic_hail;
        } else if (darkSkyIcon.equals(THUNDERSTORM)) {
            return R.drawable.ic_thunderstorm;
        } else if (darkSkyIcon.equals(TORNADO)) {
            return R.drawable.ic_tornado;
        } else {
            return R.drawable.ic_clear_day;
        }
    }
}