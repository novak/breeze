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

package org.novak.breeze.util;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.novak.breeze.R;

public class ThemeManager {
    public static void applyTheme(Window window, WeatherTheme weatherTheme) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = window.getDecorView().getSystemUiVisibility();

            if (weatherTheme.usesLightStatusBar()) {
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }

            window.getDecorView().setSystemUiVisibility(flags);
            window.setStatusBarColor(getColor(window.getContext().getResources(), weatherTheme.getStatusBarColor()));
        }

        window.setBackgroundDrawable(new ColorDrawable(getColor(window.getContext().getResources(),
                weatherTheme.getBackgroundColor())));
    }

    public static void applyTheme(ImageView view, WeatherTheme weatherTheme) {
        view.setColorFilter(getColor(view.getResources(), weatherTheme.getContentColor()));
    }

    public static void applyTheme(TextView view, WeatherTheme weatherTheme) {
        view.setTextColor(getColor(view.getResources(), weatherTheme.getContentColor()));
    }

    public static void applyTheme(Toolbar toolbar, WeatherTheme theme) {
        int contentColor = getColor(toolbar.getContext().getResources(), theme.getContentColor());
        toolbar.setTitleTextColor(contentColor);

        if (toolbar.getOverflowIcon() != null) {
            Drawable overflow = toolbar.getOverflowIcon().mutate();
            overflow.setColorFilter(contentColor, PorterDuff.Mode.SRC_IN);
            toolbar.setOverflowIcon(overflow);
        }
    }

    @SuppressWarnings("deprecation")
    private static int getColor(Resources resources, @ColorRes int resourceId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return resources.getColor(resourceId, resources.newTheme());
        } else {
            return resources.getColor(resourceId);
        }
    }

    public enum WeatherTheme {
        DEFAULT(false, R.color.colorPrimaryDark, R.color.colorPrimary, R.color.contentColor),
        NIGHT(false, R.color.colorPrimaryNightDark, R.color.colorPrimaryNight, R.color.contentColorNight),
        CLOUDY(true, R.color.colorPrimaryCloudyDark, R.color.colorPrimaryCloudy, R.color.contentColorCloudy),
        SNOW(true, R.color.colorPrimarySnowDark, R.color.colorPrimarySnow, R.color.contentColorSnow);

        private boolean lightStatusBar;
        private int statusBarColor;
        private int backgroundColor;
        private int contentColor;

        WeatherTheme(boolean lightStatusBar, @ColorRes int statusBarColor, @ColorRes int backgroundColor,
                             @ColorRes int contentColor) {
            this.lightStatusBar = lightStatusBar;
            this.statusBarColor = statusBarColor;
            this.backgroundColor = backgroundColor;
            this.contentColor = contentColor;
        }

        boolean usesLightStatusBar() {
            return lightStatusBar;
        }

        int getStatusBarColor() {
            return statusBarColor;
        }

        int getBackgroundColor() {
            return backgroundColor;
        }

        int getContentColor() {
            return contentColor;
        }
    }
}
