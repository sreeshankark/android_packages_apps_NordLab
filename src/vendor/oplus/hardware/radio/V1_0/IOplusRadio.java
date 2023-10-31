/*
 * Copyright (C) 2023 Android Open Source Project
 * SPDX-License-Identifier: Apache-2.0
 */

package vendor.oplus.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public interface IOplusRadio extends IHwInterface {

    public static final String kBaseName = "android.hidl.base@1.0::IBase";
    public static final String kInterfaceName = "vendor.oplus.hardware.radio@1.0::IOplusRadio";

    static IOplusRadio asInterface(IHwBinder binder) {
        if (binder == null) {
            return null;
        }
        final IHwInterface iface = binder.queryLocalInterface(kInterfaceName);
        if (iface != null && (iface instanceof IOplusRadio)) {
            return (IOplusRadio) iface;
        }
        final Proxy proxy = new Proxy(binder);
        try {
            for (String descriptor : proxy.interfaceChain()) {
                if (descriptor.equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    static IOplusRadio getService(String serviceName) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, serviceName));
    }

    ArrayList<String> interfaceChain()
            throws RemoteException;

    void setCallback(IOplusRadioResponse responseCallback, IOplusRadioIndication indicationCallback)
            throws RemoteException;

    void setNrMode(int serial, int mode)
            throws RemoteException;

    public static final class Proxy implements IOplusRadio {

        private final IHwBinder mRemote;

        public Proxy(IHwBinder binder) {
            mRemote = Objects.requireNonNull(binder);
        }

        public final IHwBinder asBinder() {
            return mRemote;
        }

        public final String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of " + kInterfaceName + "]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override
        public void setCallback(IOplusRadioResponse responseCallback, IOplusRadioIndication indicationCallback)
                throws RemoteException {
            final HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(kInterfaceName);
            _hidl_request.writeStrongBinder(responseCallback == null ? null : responseCallback.asBinder());
            _hidl_request.writeStrongBinder(indicationCallback == null ? null : indicationCallback.asBinder());
            final HwParcel _hidl_reply = new HwParcel();
            try {
                mRemote.transact(1, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public void setNrMode(int serial, int mode)
                throws RemoteException {
            final HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(kInterfaceName);
            _hidl_request.writeInt32(serial);
            _hidl_request.writeInt32(mode);
            final HwParcel _hidl_reply = new HwParcel();
            try {
                mRemote.transact(28, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
            } finally {
                _hidl_reply.release();
            }
        }

        @Override
        public ArrayList<String> interfaceChain()
                throws RemoteException {
            final HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(kBaseName);
            final HwParcel _hidl_reply = new HwParcel();
            try {
                mRemote.transact(256067662, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readStringVector();
            } finally {
                _hidl_reply.release();
            }
        }

        public String interfaceDescriptor()
                throws RemoteException {
            final HwParcel _hidl_request = new HwParcel();
            _hidl_request.writeInterfaceToken(kBaseName);
            final HwParcel _hidl_reply = new HwParcel();
            try {
                mRemote.transact(256136003, _hidl_request, _hidl_reply, 0);
                _hidl_reply.verifySuccess();
                _hidl_request.releaseTemporaryStorage();
                return _hidl_reply.readString();
            } finally {
                _hidl_reply.release();
            }
        }
    }
}
