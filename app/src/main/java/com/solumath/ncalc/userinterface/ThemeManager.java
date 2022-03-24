/*
 * Copyright (C) 2018 Duy Tran Le
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.solumath.ncalc.userinterface;

import android.content.Context;
import android.content.res.Resources;
import com.solumath.calculator.R;
import com.solumath.ncalc.utils.ViewUtils;

public class ThemeManager {
    public static final String NULL = "";
    public static final int THEME_NOT_FOUND = -1;

    private final int mLightTheme;
    private final int mDarkTheme;

    private Resources mResources;

    public ThemeManager(Context applicationContext) {
        this.mResources = applicationContext.getResources();

        mLightTheme = R.style.AppThemeLight;
        mDarkTheme = R.style.AppThemeDark;

    }

    /**
     * get theme from mResult
     *
     * @param name
     * @return
     */
    public int getTheme(String name) {
        name = name.trim();
        if (name.equals(NULL)) {
            return THEME_NOT_FOUND;
        }
        if (name.equals(mResources.getString(R.string.theme_light))) {
            return mLightTheme;
        }   else if (name.equals(mResources.getString(R.string.theme_dark))) {
            return mDarkTheme;
        }
        return mLightTheme;
    }


    public static boolean isLightTheme(Context context) {
        return ViewUtils.getBoolean(context, R.attr.isLightTheme, false);
    }
}

