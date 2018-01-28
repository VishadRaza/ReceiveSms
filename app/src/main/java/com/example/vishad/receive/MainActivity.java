package com.example.vishad.receive;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.provider.Telephony.*;

public class MainActivity extends AppCompatActivity {

    String number;


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
    String s="";

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
   //    SharedPreferences preferences = getSharedPreferences("ShaPreferences",Context.MODE_PRIVATE);
     //   SharedPreferences.Editor editor = preferences.edit();
       // s = preferences.getString("MyNumber",null);
        Log.v("savedNumber"," "+s);


       /* if(savedInstanceState == null ){
          Bundle extras = getIntent().getExtras();
           if(extras == null){
               number = null;

            }
            else {
                number = extras.getString("Mynumber");
            }

        }
        else {
            number =(String) savedInstanceState.getSerializable("Mynumber");
        }*/
      number = getIntent().getStringExtra("MyNumber");

        Log.v( "friday" ,"" +number);

        messages = (ListView) findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        messages.setAdapter(arrayAdapter);
        //refreshSmsInbox();

        // SmsBroadcastReceiver smsBroadcastReceiver = new SmsBroadcastReceiver();
        //smsBroadcastReceiver.onReceive(getApplicationContext(),getIntent());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        } else {
            refreshSmsInbox();
        }

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
    }  public void refreshSmsInbox() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxcursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxcursor.getColumnIndex("body");
        int indexAddress = smsInboxcursor.getColumnIndex("address");
        String i = number;
        Log.v("hel" ,"msg" +i);
        //    String check = "00923362503565";

        if (indexBody < 0 || !smsInboxcursor.moveToFirst())
            return;
        arrayAdapter.clear();
        //  if(i.equals(indexAddress)) {
        do {
            String number = smsInboxcursor.getString(indexAddress);
            if (number.equals(i)) {

               SimpleDateFormat df= new SimpleDateFormat("dd-MM-yyyy");
            // df.format(smsInboxcursor.getString(date));

               String FormattedDate = df.format(smsInboxcursor.getColumnIndex("4"));
                String str = "SMS From: " + smsInboxcursor.getString(indexAddress) +
                        "\n" + smsInboxcursor.getString(indexBody) + "\n"+" date" +FormattedDate+ "\n" ;


                arrayAdapter.add(str);
displaySmsLog();
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
        }  }
}
