/*
 * Copyright (C) 2023 Android Open Source Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.oplus.settings.OPlusExtras.radio

import android.os.SystemProperties

import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.MODE_AUTO
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.MODE_NSA_ONLY
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.MODE_NSA_PRE
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.MODE_SA_ONLY
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.MODE_SA_PRE
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.PROP_AUTO_MODE
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.SIM_CARD_1
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.SIM_CARD_2
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.logD
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.logE
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.nrmodeToString

import vendor.oplus.hardware.radio.V1_0.IOplusRadio

object OplusRadioWrapper {

    private const val TAG = "OplusRadioWrapper"
    
    private const val OPLUS_SLOT_1 = "oplus_slot1"
    private const val OPLUS_SLOT_2 = "oplus_slot2"
    
    private const val OPLUS_RIL_SERIAL = 1001

    private var oplusRadioSim1: IOplusRadio? = null
    private var oplusRadioSim2: IOplusRadio? = null

    private val oplusRadioIndicationStub = IOplusRadioIndicationStub()
    private val oplusRadioResponseStub = IOplusRadioResponseStub()

    private fun getService(simId: Int): IOplusRadio? {
        try {
            when (simId) {
                SIM_CARD_1 -> {
                    if (oplusRadioSim1 == null) {
                        oplusRadioSim1 = IOplusRadio.getService(OPLUS_SLOT_1)
                        oplusRadioSim1?.setCallback(oplusRadioResponseStub, oplusRadioIndicationStub)
                    }
                    return oplusRadioSim1
                }
                SIM_CARD_2 -> {
                    if (oplusRadioSim2 == null) {
                        oplusRadioSim2 = IOplusRadio.getService(OPLUS_SLOT_2)
                        oplusRadioSim2?.setCallback(oplusRadioResponseStub, oplusRadioIndicationStub)
                    }
                    return oplusRadioSim2
                }
                else -> return null
            }
        } catch (e: Exception) {
            logE(TAG, "Exception on get oplus radio for simId $simId", e)
            return null
        }
    }

    private fun checkService(simId: Int): Boolean {
        return when (simId) {
            SIM_CARD_1 -> oplusRadioSim1 != null
            SIM_CARD_2 -> oplusRadioSim2 != null
            else -> false
        } || getService(simId) != null
    }

    private fun isModeValid(mode: Int): Boolean {
        return mode == MODE_NSA_ONLY ||
                mode == MODE_NSA_PRE ||
                mode == MODE_SA_ONLY ||
                mode == MODE_SA_PRE
    }

    /*
     * Stock rom chooses best mode based on location and signal strength,
     * which won't be implemented in custom roms.
     * 
     * Auto mode here is more like a placeholder for users who want to restore preferences.
     * Convert auto mode to prop set mode (Default is SA Preferred)
     */
    private fun convertMode(mode: Int): Int {
        if (mode != MODE_AUTO) {
            return mode
        }
        return SystemProperties.getInt(PROP_AUTO_MODE, MODE_SA_PRE)
    }

    fun setNrMode(simId: Int, mode: Int): Boolean {
        if (!checkService(simId)) {
            logE(TAG, "Oplus radio for simId $simId is null")
            return false
        }
        val realMode = convertMode(mode)
        if (!isModeValid(realMode)) {
            logE(TAG, "Invalid mode $mode")
            return false
        }
        logD(TAG, "setNrMode for simId: $simId, mode: " + nrmodeToString(realMode))
        try {
            getService(simId)?.setNrMode(OPLUS_RIL_SERIAL, realMode)
            return true
        } catch (e: Exception) {
            logE(TAG, "Exception on set nr mode for simId $simId", e)
            return false
        }
    }
}
