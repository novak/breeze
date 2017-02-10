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

import org.novak.breeze.util.ThemeManager;

import java.util.Date;
import java.util.List;

public class Weather {
    public Conditions currentConditions;
    public List<Forecast> dailyForecasts;

    public long sunrise;
    public long sunset;

    public ThemeManager.WeatherTheme getTheme() {
        Date now = new Date();

        if (now.before(new Date(sunrise)) || now.after(new Date(sunset))) {
            return ThemeManager.WeatherTheme.NIGHT;
        } else {
            return currentConditions.getTheme();
        }
    }
}
