package com.example.vishad.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;

import java.lang.reflect.Method;

/**
 * Created by Vishad on 15-01-2018.
 */

public class PhoneStateReceiver extends BroadcastReceiver {

    private String blockingNumber = "+923362503565";
    @Override
    public void onReceive(Context context, Intent intent) {

  if(intent.getAction().equals("android.provider.Telephony.SMSRECEIVED")) {
      Bundle bundle = intent.getExtras();
      Object messages[] = (Object[]) bundle.get("pdus");
      SmsMessage smsMessage[] = new SmsMessage[messages.length];
      for (int n = 0; n < messages.length; n++) {
          smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
      }

      final String numberSms = smsMessage[0].getOriginatingAddress();

      if (numberSms.equals(blockingNumber)) {
        // disconnectPhoneItelephony(context);
          abortBroadcast();

      }
  }
  }
    // Keep this method as it is
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void disconnectPhoneItelephony(Context context) {
        try {

            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
