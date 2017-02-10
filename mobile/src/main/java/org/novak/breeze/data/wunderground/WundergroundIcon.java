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

import org.novak.breeze.R;

public class WundergroundIcon {
    private static final String CHANCE_FLURRIES = "chanceflurries";
    private static final String CHANCE_RAIN = "chancerain";
    private static final String CHANCE_SLEET = "chancesleet";
    private static final String CHANCE_SNOW = "chancesnow";
    private static final String CHANCE_TSTORMS = "chancetstorms";

    private static final String CLEAR = "clear";
    private static final String CLOUDY = "cloudy";
    private static final String FLURRIES = "flurries";
    private static final String FOG = "fog";
    private static final String HAZY = "hazy";
    private static final String MOSTLY_CLOUDY = "mostlycloudy";
    private static final String MOSTLY_SUNNY = "mostlysunny";
    private static final String PARTLY_CLOUDY = "partlycloudy";
    private static final String PARTLY_SUNNY = "partlysunny";
    private static final String SLEET = "sleet";
    private static final String RAIN = "rain";
    private static final String SNOW = "snow";
    private static final String SUNNY = "sunny";
    private static final String TSTORMS = "tstorms";
    private static final String UNKNOWN = "unknown";

    static int toResource(String icon) {
        if (icon.equals(CHANCE_FLURRIES)) {
            return R.drawable.ic_snow;
        } else if (icon.equals(CHANCE_RAIN)) {
            return R.drawable.ic_rain;
        } else if (icon.equals(CHANCE_SLEET)) {
            return R.drawable.ic_sleet;
        } else if (icon.equals(CHANCE_SNOW)) {
            return R.drawable.ic_snow;
        } else if (icon.equals(CHANCE_TSTORMS)) {
            return R.drawable.ic_thunderstorm;
        } else if (icon.equals(CLEAR)) {
            return R.drawable.ic_clear_day;
        } else if (icon.equals(CLOUDY)) {
            return R.drawable.ic_cloudy;
        } else if (icon.equals(FLURRIES)) {
            return R.drawable.ic_snow;
        } else if (icon.equals(FOG)) {
            return R.drawable.ic_fog;
        } else if (icon.equals(HAZY)) {
            return R.drawable.ic_fog;
        } else if (icon.equals(MOSTLY_CLOUDY)) {
            return R.drawable.ic_cloudy;
        } else if (icon.equals(MOSTLY_SUNNY)) {
            return R.drawable.ic_clear_day;
        } else if (icon.equals(PARTLY_CLOUDY)) {
            return R.drawable.ic_partly_cloudy_day;
        } else if (icon.equals(PARTLY_SUNNY)) {
            return R.drawable.ic_partly_cloudy_day;
        } else if (icon.equals(SLEET)) {
            return R.drawable.ic_sleet;
        } else if (icon.equals(RAIN)) {
            return R.drawable.ic_rain;
        } else if (icon.equals(SNOW)) {
            return R.drawable.ic_snow;
        } else if (icon.equals(SUNNY)) {
            return R.drawable.ic_clear_day;
        } else if (icon.equals(TSTORMS)) {
            return R.drawable.ic_thunderstorm;
        } else if (icon.equals(UNKNOWN)) {
            return R.drawable.ic_wind;
        }
        return -1;
    }
}
