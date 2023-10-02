/*
 * Copyright (C) 2016 The OmniROM Project
 *               2022 The Evolution X Project
 * SPDX-License-Identifier: GPL-2.0-or-later
 */

package com.oneplus.avicii.NordLab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.UserHandle;
import android.provider.Settings;
import androidx.preference.PreferenceManager;

import com.oneplus.avicii.NordLab.colorspace.ColorSpaceUtils;
import com.oneplus.avicii.NordLab.doze.DozeUtils;
import com.oneplus.avicii.NordLab.FileUtils;
import com.oneplus.avicii.NordLab.modeswitch.*;
import com.oneplus.avicii.NordLab.preferences.*;
import com.oneplus.avicii.NordLab.refreshrate.RefreshUtils;
import com.oneplus.avicii.NordLab.services.FPSInfoService;
import com.oneplus.avicii.NordLab.touch.TouchscreenGestureSettings;
import com.oneplus.avicii.NordLab.services.SimCardListenerService;

public class Startup extends BroadcastReceiver {

    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(final Context context, final Intent bootintent) {

        AdrenoBoostPreference.restore(context);
        BluePreference.restore(context);
        ContrastPreference.restore(context);
        ColorSpaceUtils.startService(context);
        DozeUtils.checkDozeService(context);
        GreenPreference.restore(context);
        HuePreference.restore(context);
        MaxBrightnessPreference.restore(context);
        NordLab.restoreSliderStates(context);
        RedPreference.restore(context);
        RefreshUtils.startService(context);
        SaturationPreference.restore(context);
        TouchscreenGestureSettings.MainSettingsFragment.restoreTouchscreenGestureStates(context);
        ValuePreference.restore(context);
        VibratorStrengthPreference.restore(context);

        boolean enabled = false;
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        enabled = sharedPrefs.getBoolean(NordLab.KEY_DC_SWITCH, false);
        if (enabled) {
        restore(DCModeSwitch.getFile(context), enabled);
               }
        enabled = sharedPrefs.getBoolean(NordLab.KEY_HBM_SWITCH, false);
        if (enabled) {
        restore(HBMModeSwitch.getFile(context), enabled);
               }
        enabled = sharedPrefs.getBoolean(NordLab.KEY_FPS_INFO, false);
        if (enabled) {
            context.startService(new Intent(context, FPSInfoService.class));
               }
        enabled = sharedPrefs.getBoolean(NordLab.KEY_FSYNC_SWITCH, false);
        if (enabled) {
        restore(FSyncModeSwitch.getFile(context), enabled);
               }
        enabled = sharedPrefs.getBoolean(NordLab.KEY_POWERSHARE_SWITCH, false);
        if (enabled) {
            restore(PowerShareModeSwitch.getFile(context), enabled);
               }
        enabled = sharedPrefs.getBoolean(NordLab.KEY_QUIET_MODE_SWITCH, false);
        if (enabled) {
            restore(QuietModeSwitch.getFile(context), enabled);
               }
        enabled = sharedPrefs.getBoolean(NordLab.KEY_GAME_SWITCH, false);
        if (enabled) {
            restore(GameModeSwitch.getFile(context), enabled);
               }
        enabled = sharedPrefs.getBoolean(NordLab.KEY_TP_EDGE_LIMIT_SWITCH, false);
        if (enabled) {
            restore(TPEdgeLimitModeSwitch.getFile(context), enabled);
               }
        enabled = sharedPrefs.getBoolean(NordLab.KEY_POWER_EFFICIENT_WQ_SWITCH, false);
        if (enabled) {
            restore(PowerEfficientWorkqueueModeSwitch.getFile(context), enabled);
               }
        enabled = sharedPrefs.getBoolean(NordLab.KEY_TOUCHBOOST_SWITCH, false);
        if (enabled) {
            restore(TouchBoostModeSwitch.getFile(context), enabled);
               }
        enabled = sharedPrefs.getBoolean(NordLab.KEY_OTG_SWITCH, false);
        if (enabled) {
            restore(OTGModeSwitch.getFile(context), enabled);
               }
        enabled = sharedPrefs.getBoolean(NordLab.KEY_USB2_SWITCH, false);
        if (enabled) {
        restore(USB2FastChargeModeSwitch.getFile(context), enabled);
        }
        context.startServiceAsUser(new Intent(context, SimCardListenerService.class), UserHandle.SYSTEM);
    }
    
    private void restore(String file, boolean enabled) {
        if (file == null) {
            return;
        }
        if (enabled) {
            FileUtils.writeValue(file, "1");
        }
    }

    private void restore(String file, String value) {
        if (file == null) {
            return;
        }
        FileUtils.writeValue(file, value);
    }
}
