/*
 * Copyright (C) 2023 Android Open Source Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.oneplus.avicii.NordLab.helper

import android.content.Context
import android.os.UserHandle
import android.provider.Settings

import com.oneplus.avicii.NordLab.nrmode.NrModeConstants.MODE_AUTO
import com.oneplus.avicii.NordLab.nrmode.NrModeConstants.SETTINGS_SIM_1
import com.oneplus.avicii.NordLab.nrmode.NrModeConstants.SETTINGS_SIM_2
import com.oneplus.avicii.NordLab.nrmode.NrModeConstants.SIM_CARD_1
import com.oneplus.avicii.NordLab.nrmode.NrModeConstants.SIM_CARD_2

object SettingsHelper {

    fun getUserPreferredNrMode(context: Context, simId: Int): Int {
        return when (simId) {
            SIM_CARD_1 -> {
                Settings.System.getIntForUser(context.contentResolver,
                        SETTINGS_SIM_1, MODE_AUTO,
                        UserHandle.USER_SYSTEM)
            }
            SIM_CARD_2 -> {
                Settings.System.getIntForUser(context.contentResolver,
                        SETTINGS_SIM_2, MODE_AUTO,
                        UserHandle.USER_SYSTEM)
            }
            else -> MODE_AUTO
        }
    }
    
    fun setUserPreferredNrMode(context: Context, simId: Int, mode: Int) {
        when (simId) {
            SIM_CARD_1 -> {
                Settings.System.putIntForUser(context.contentResolver,
                        SETTINGS_SIM_1, mode,
                        UserHandle.USER_SYSTEM)
            }
            SIM_CARD_2 -> {
                Settings.System.putIntForUser(context.contentResolver,
                        SETTINGS_SIM_2, mode,
                        UserHandle.USER_SYSTEM)
            }
        }
    }
}
