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

package org.novak.breeze.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.novak.breeze.R;
import org.novak.breeze.data.Forecast;
import org.novak.breeze.util.ThemeManager;

import java.util.ArrayList;
import java.util.List;

public class ForecastFragment extends Fragment {
    public static final int NUM_DAYS = 5;
    private List<ForecastDayView> dayViews;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dayViews = new ArrayList<>(NUM_DAYS);
        dayViews.add((ForecastDayView) view.findViewById(R.id.forecast_day_one));
        dayViews.add((ForecastDayView) view.findViewById(R.id.forecast_day_two));
        dayViews.add((ForecastDayView) view.findViewById(R.id.forecast_day_three));
        dayViews.add((ForecastDayView) view.findViewById(R.id.forecast_day_four));
        dayViews.add((ForecastDayView) view.findViewById(R.id.forecast_day_five));
    }

    public void updateForcast(List<Forecast> forecasts, ThemeManager.WeatherTheme theme) {
        for (int i = 0; i < NUM_DAYS; i++) {
            dayViews.get(i).setForecast(forecasts.get(i), theme);
        }
    }
}
