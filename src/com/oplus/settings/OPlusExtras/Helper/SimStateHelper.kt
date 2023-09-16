/*
 * Copyright (C) 2023 Android Open Source Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.oplus.settings.OPlusExtras.helper

import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.SIM_CARD_1
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.SIM_CARD_2

object SimStateHelper {

    private var sim1Available = false
    private var sim2Available = false

    fun isSimCardAvailable(simId: Int): Boolean {
        return when (simId) {
            SIM_CARD_1 -> sim1Available
            SIM_CARD_2 -> sim2Available
            else -> false
        }
    }

    fun setSimCardAvailable(simId: Int, available: Boolean) {
        when (simId) {
            SIM_CARD_1 -> sim1Available = available
            SIM_CARD_2 -> sim2Available = available
        }
    }
}
