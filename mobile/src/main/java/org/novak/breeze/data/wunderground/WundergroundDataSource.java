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

import android.util.Log;

import org.novak.breeze.BuildConfig;
import org.novak.breeze.data.Forecast;
import org.novak.breeze.data.Weather;
import org.novak.breeze.data.WeatherDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class WundergroundDataSource implements WeatherDataSource {
    private WundergroundService service;
    private String units;

    public WundergroundDataSource(String units) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.wunderground.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        service = retrofit.create(WundergroundService.class);
        this.units = units;
    }

    @Override
    public Weather getForecast(String latitude, String longitude) {
        Call<WundergroundResponse> call = service.getForecast(BuildConfig.WUNDERGROUND_API_KEY, latitude, longitude);

        try {
            Response<WundergroundResponse> response = call.execute();

            if (response.code() == 200) {
                Weather weather = new Weather();

                List<WundergroundForecast> wunderForecasts = response.body().forecast.simpleForecast.forecasts;
                List<Forecast> forecasts = new ArrayList<>(5);

                for (int i = 0; i < 5; i++) {
                    forecasts.add(wunderForecasts.get(i).toForecast(units));
                }

                weather.currentConditions = response.body().currentConditions.toConditions(wunderForecasts.get(0),
                        units);
                weather.dailyForecasts = forecasts;

                weather.sunrise = response.body().sunPhase.sunrise.toMilliseconds();
                weather.sunset = response.body().sunPhase.sunset.toMilliseconds();

                return weather;
            }
        } catch (IOException e) {
            Log.e("Breeze", "Error fetching data from weather underground.", e);
        }

        return null;
    }
}
