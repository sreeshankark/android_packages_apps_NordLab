/*
 * Copyright (C) 2023 Android Open Source Project
 * SPDX-License-Identifier: Apache-2.0
 */

package com.oplus.settings.OPlusExtras.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED
import android.content.Intent.FLAG_RECEIVER_REGISTERED_ONLY
import android.content.IntentFilter
import android.os.IBinder
import android.os.UserHandle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.telephony.TelephonyManager.SIM_STATE_READY

import com.android.internal.telephony.TelephonyIntents.ACTION_SIM_STATE_CHANGED

import com.oplus.settings.OPlusExtras.radio.OplusRadioWrapper.setNrMode
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.INTENT_SIM_STATE_CHANGED_CUSTOM
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.SIM_CARD_1
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.SIM_CARD_2
import com.oplus.settings.OPlusExtras.nrmode.NrModeConstants.logD
import com.oplus.settings.OPlusExtras.helper.SettingsHelper.getUserPreferredNrMode
import com.oplus.settings.OPlusExtras.helper.SimStateHelper.isSimCardAvailable
import com.oplus.settings.OPlusExtras.helper.SimStateHelper.setSimCardAvailable
 
class SimCardListenerService : Service() {

    private var telephonyManager: TelephonyManager? = null

    private var airplaneMode = false

    private var sim1Available = false
    private var sim2Available = false

    private val simStateChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                ACTION_AIRPLANE_MODE_CHANGED -> {
                    airplaneMode = intent.getBooleanExtra("state", false)
                    logD(TAG, "Airplane mode state changed to $airplaneMode")
                }
                ACTION_SIM_STATE_CHANGED -> {
                    sim1Available = isSimCardAvailableInternal(0)
                    sim2Available = isSimCardAvailableInternal(1)
                    logD(TAG, "Sim card state changed, sim1: $sim1Available, sim2: $sim2Available")
                }
            }
            updateSimState()
            updateNrMode()
            sendSimStateChangedBroadcast()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        telephonyManager = getSystemService(TelephonyManager::class.java)

        airplaneMode = Settings.Global.getInt(contentResolver, 
                Settings.Global.AIRPLANE_MODE_ON, 0) == 1
        sim1Available = isSimCardAvailableInternal(0)
        sim2Available = isSimCardAvailableInternal(1)

        registerReceiver(simStateChangeReceiver, IntentFilter().apply {
            addAction(ACTION_AIRPLANE_MODE_CHANGED)
            addAction(ACTION_SIM_STATE_CHANGED)
        })
    }

    override fun onDestroy() {
        unregisterReceiver(simStateChangeReceiver)
        super.onDestroy()
    }

    private fun isSimCardAvailableInternal(slotId: Int): Boolean {
        return telephonyManager?.getSimState(slotId) == SIM_STATE_READY
    }

    private fun updateSimState() {
        (sim1Available && !airplaneMode).let {
            setSimCardAvailable(SIM_CARD_1, it)
            logD(TAG, "updateSimState, sim1: $it")
        }
        (sim2Available && !airplaneMode).let {
            setSimCardAvailable(SIM_CARD_2, it)
            logD(TAG, "updateSimState, sim2: $it")
        }
    }

    private fun updateNrMode() {
        if (isSimCardAvailable(SIM_CARD_1)) {
            getUserPreferredNrMode(this, SIM_CARD_1).let {
                setNrMode(SIM_CARD_1, it)
                logD(TAG, "updateNrMode, sim1: $it")
            }
        }
        if (isSimCardAvailable(SIM_CARD_2)) {
            getUserPreferredNrMode(this, SIM_CARD_2).let {
                setNrMode(SIM_CARD_2, it)
                logD(TAG, "updateNrMode, sim2: $it")
            }
        }
    }

    private fun sendSimStateChangedBroadcast() {
        sendBroadcastAsUser(Intent().apply {
            setAction(INTENT_SIM_STATE_CHANGED_CUSTOM)
            setFlags(FLAG_RECEIVER_REGISTERED_ONLY)
        }, UserHandle.SYSTEM)
    }

    companion object {
        private const val TAG = "SimCardListenerService"
    }
}
