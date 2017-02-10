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

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.novak.breeze.R;
import org.novak.breeze.data.Forecast;
import org.novak.breeze.util.ThemeManager;

import java.util.Locale;

public class ForecastDayView extends LinearLayout {
    private TextView dayView;
    private ImageView conditionsView;
    private TextView expectedTemperatureView;

    public ForecastDayView(Context context) {
        super(context);
        createLayout();
    }

    public ForecastDayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        createLayout();
    }

    public ForecastDayView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        createLayout();
    }

    private void createLayout() {
        inflate(getContext(), R.layout.view_forecast_day, this);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

        dayView = (TextView) findViewById(R.id.text_view_day_of_week);
        conditionsView = (ImageView) findViewById(R.id.image_view_forecast_conditions);
        expectedTemperatureView = (TextView) findViewById(R.id.text_view_expected_temperature);
    }

    public void setForecast(Forecast forecast, ThemeManager.WeatherTheme theme) {
        conditionsView.setImageResource(forecast.getIconResource());
        ThemeManager.applyTheme(conditionsView, theme);

        dayView.setText(forecast.getDay());
        ThemeManager.applyTheme(dayView, theme);

        int maxTemperature = Math.round(forecast.getMaxTemperature());
        int minTemperature = Math.round(forecast.getMinTemperature());

        TypefaceSpan lightSpan = new TypefaceSpan("sans-serif-light");
        TypefaceSpan mediumSpan = new TypefaceSpan("sans-serif-medium");

        String temperaturesString = String.format(Locale.getDefault(), "%d %d", minTemperature, maxTemperature);
        Spannable temperatures = new SpannableString(temperaturesString);

        temperatures.setSpan(lightSpan, 0, temperaturesString.indexOf(' '), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        temperatures.setSpan(mediumSpan, temperaturesString.indexOf(' '), temperaturesString.length(),
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        expectedTemperatureView.setText(temperatures);
        ThemeManager.applyTheme(expectedTemperatureView, theme);
    }
}
