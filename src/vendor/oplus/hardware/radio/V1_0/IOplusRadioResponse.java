/*
 * Copyright (C) 2023 Android Open Source Project
 * SPDX-License-Identifier: Apache-2.0
 */

package vendor.oplus.hardware.radio.V1_0;

import android.hidl.base.V1_0.DebugInfo;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.Arrays;

public interface IOplusRadioResponse extends IHwInterface {

    public static final String kBaseName = "android.hidl.base@1.0::IBase";
    public static final String kInterfaceName = "vendor.oplus.hardware.radio@1.0::IOplusRadioResponse";

    IHwBinder asBinder();

    public static abstract class Stub extends HwBinder implements IOplusRadioResponse {

        @Override
        public IHwBinder asBinder() {
            return this;
        }

        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        public final String interfaceDescriptor() {
            return kInterfaceName;
        }

        public final boolean linkToDeath(IHwBinder.DeathRecipient recipient, long cookie) {
            return true;
        }

        public final void ping() {
        }

        public final void setHALInstrumentation() {
        }

        public final boolean unlinkToDeath(IHwBinder.DeathRecipient recipient) {
            return true;
        }

        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(kInterfaceName, kBaseName));
        }

        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(
                new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                new byte[]{-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        public final DebugInfo getDebugInfo() {
            final DebugInfo info = new DebugInfo();
            info.pid = HidlSupport.getPidIfSharable();
            info.ptr = 0L;
            info.arch = 0;
            return info;
        }

        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public final IHwInterface queryLocalInterface(String descriptor) {
            if (kInterfaceName.equals(descriptor)) {
                return this;
            }
            return null;
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        public void onTransact(int _hidl_code, HwParcel _hidl_request, HwParcel _hidl_reply, int _hidl_flags)
                throws RemoteException {
            switch (_hidl_code) {
                case 256067662:
                    _hidl_request.enforceInterface(kBaseName);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeStringVector(interfaceChain());
                    _hidl_reply.send();
                    return;
                case 256131655:
                    _hidl_request.enforceInterface(kBaseName);
                    debug(_hidl_request.readNativeHandle(), _hidl_request.readStringVector());
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 256136003:
                    _hidl_request.enforceInterface(kBaseName);
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.writeString(interfaceDescriptor());
                    _hidl_reply.send();
                    return;
                case 256398152:
                    _hidl_request.enforceInterface(kBaseName);
                    _hidl_reply.writeStatus(0);
                    final HwBlob _hidl_blob = new HwBlob(16);
                    final ArrayList<byte[]> _hidl_out_hashChain = getHashChain();
                    final int size = _hidl_out_hashChain.size();
                    _hidl_blob.putInt32(8L, size);
                    _hidl_blob.putBool(12L, false);
                    final HwBlob _hidl_child_blob = new HwBlob(size * 32);
                    for (int i = 0; i < size; i++) {
                        long offset = i * 32;
                        byte[] hashChain = _hidl_out_hashChain.get(i);
                        if (hashChain == null || hashChain.length != 32) {
                            throw new IllegalArgumentException("Array element is not of the expected length");
                        }
                        _hidl_child_blob.putInt8Array(offset, hashChain);
                    }
                    _hidl_blob.putBlob(0L, _hidl_child_blob);
                    _hidl_reply.writeBuffer(_hidl_blob);
                    _hidl_reply.send();
                    return;
                case 256462420:
                    _hidl_request.enforceInterface(kBaseName);
                    setHALInstrumentation();
                    return;
                case 256921159:
                    _hidl_request.enforceInterface(kBaseName);
                    ping();
                    _hidl_reply.writeStatus(0);
                    _hidl_reply.send();
                    return;
                case 257049926:
                    _hidl_request.enforceInterface(kBaseName);
                    final DebugInfo info = getDebugInfo();
                    _hidl_reply.writeStatus(0);
                    info.writeToParcel(_hidl_reply);
                    _hidl_reply.send();
                    return;
                case 257120595:
                    _hidl_request.enforceInterface(kBaseName);
                    notifySyspropsChanged();
                    return;
                default:
                    return;
            }
        }
    }
}
