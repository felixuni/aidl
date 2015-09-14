package com.aidlclass;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Javier on 13/09/2015.
 */
public class AddService extends Service {
    public static final String CLASS_TAG=AddService.class.getName();

    public void onCreate(){
        super.onCreate();

        Log.i(CLASS_TAG,"onCreate()");
    }


    @Override
    public IBinder onBind(Intent intent) {

        return new IAddService.Stub() {;

            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            public int add(int PrimerValor, int SegundoValor) throws RemoteException {
                Log.i(CLASS_TAG, String.format("AddService.add(%d, %d)", PrimerValor, SegundoValor));
                return (PrimerValor + SegundoValor);
            }
        };

    }

    public void onDestroy(){
        super.onDestroy();

    Log.d(CLASS_TAG,"onDestroy()");
    }

}
