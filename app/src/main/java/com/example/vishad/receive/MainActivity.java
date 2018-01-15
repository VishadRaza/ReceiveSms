package com.example.vishad.receive;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static MainActivity instance() {
        return inst;
    }

    public void onStart() {
        super.onStart();
        inst = this;
    }


    private static final int READ_SMS_PERMISSION_REQUEST = 1;
    ArrayList<String> smsMessageList = new ArrayList<>();
    ListView messages;
    ArrayAdapter arrayAdapter;
    private static MainActivity inst;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messages = (ListView) findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        messages.setAdapter(arrayAdapter);

        // SmsBroadcastReceiver smsBroadcastReceiver = new SmsBroadcastReceiver();
        //smsBroadcastReceiver.onReceive(getApplicationContext(),getIntent());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        } else {
            refreshSmsInbox();
        }
        displaySmsLog();
    }


    public void updateList(final String newSms) {
        //arrayAdapter.insert(newSms, 0);
        arrayAdapter.add(newSms);
        arrayAdapter.notifyDataSetChanged();
        // arrayAdapter.add(newSms);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)) {
                Toast.makeText(this, "Please Allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSION_REQUEST);

        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == READ_SMS_PERMISSION_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
                // refreshSmsInbox();
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        }
    }

    public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxcursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxcursor.getColumnIndex("body");
        int indexAddress = smsInboxcursor.getColumnIndex("address");


        String i = "+923362503565";
        //    String check = "00923362503565";

        if (indexBody < 0 || !smsInboxcursor.moveToFirst())
            return;
        arrayAdapter.clear();

        //  if(i.equals(indexAddress)) {
        do {
            String number = smsInboxcursor.getString(indexAddress);
            if (number.equals(i)) {

                String str = "SMS From: " + smsInboxcursor.getString(indexAddress) +
                        "\n" + smsInboxcursor.getString(indexBody) + "\n";
                arrayAdapter.add(str);



            }

        }
        while (smsInboxcursor.moveToNext());


        //   }
    }


    private void displaySmsLog() {
        Uri allMessages = Uri.parse("content://sms/");
        //Cursor cursor = managedQuery(allMessages, null, null, null, null); Both are same
        Cursor cursor = this.getContentResolver().query(allMessages, null,
                null, null, null);

        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.d(cursor.getColumnName(i) + "", cursor.getString(i) + "");
            }
            Log.d("One row finished",
                    "**************************************************");
        }

    }
}
