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

public class DarkSkyDataSource implements WeatherDataSource {
    private DarkSkyService service;
    private String units;

    public DarkSkyDataSource(String units) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.darksky.net")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        service = retrofit.create(DarkSkyService.class);
        this.units = units;
    }

    @Override
    public Weather getForecast(String latitude, String longitude) {
        Call<DarkSkyResponse> call = service.getForecast(BuildConfig.DARK_SKY_API_KEY, latitude, longitude, units);

        try {
            Response<DarkSkyResponse> response = call.execute();

            if (response.code() == 200) {
                Weather weather = new Weather();

                List<DarkSkyForecast> darkSkyForecasts = response.body().daily.forecasts;
                int size = darkSkyForecasts.size();

                List<Forecast> forecasts = new ArrayList<>(size);

                for (int i = 0; i < size; i++) {
                    forecasts.add(darkSkyForecasts.get(i).toForecast());
                }

                weather.currentConditions = response.body().currently.toConditions();
                weather.dailyForecasts = forecasts;

                DarkSkyForecast todayForecast = darkSkyForecasts.get(0);
                weather.sunrise = todayForecast.getSunrise();
                weather.sunset = todayForecast.getSunset();

                return weather;
            }
        } catch (IOException e) {
            Log.e("Breeze", "Error fetching data from dark sky.", e);
        }

        return null;
    }
}
