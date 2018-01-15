package com.example.vishad.receive;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Vishad on 20-12-2017.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {


    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras = intent.getExtras();

        if (intentExtras != null) {

            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
           String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {

                String format = intentExtras.getString("format");
                SmsMessage smsMessage = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], format);
                }

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";

                String number = "+923362503565";
                if(address.equals(number)){

                }

             // deleteSMS( context,address);
            }
            deleteSMS(context);
            MainActivity inst = MainActivity.instance();
            inst.updateList(smsMessageStr);
        }
    }

    
    public void deleteSMS(Context context) {
        Uri deleteUri = Uri.parse("content://sms");
        int count;
        Cursor c = context.getContentResolver().query(deleteUri, null, null,
                null, null);

    /*    while (c.moveToNext()) {
            try {
                // Delete the SMS
                String pid = c.getString(0);
                Log.v("MainActivity", pid);
                // Get id;
                String uri = "content://sms/" + pid;


                    count = context.getContentResolver().delete(Uri.parse(uri),
                            null, null);
                    Log.v("Count",""+count);

            }
            catch(Exception e){


            }
        } */


                // Delete the SMS
                int pid= 315;
                Log.v("MainActivity",""+ pid);
                // Get id;
                //String uri = "content://sms/" + pid;
                //count = context.getContentResolver().delete(Uri.parse(uri),null, null);
        String uri = "content://sms/conversations/" +315;
        count = context.getContentResolver().delete(Uri.parse(uri), null, null);
        Log.v("Count",""+count);

            }

        }




