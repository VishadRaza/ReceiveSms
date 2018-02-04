package com.example.vishad.receive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Fragment;

public class firstTime extends AppCompatActivity {

    EditText editText;
    Button button;
    SharedPreferences prefs;

    String number;
    //SharedPreferences preferences;
  //SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean("first",Boolean.TRUE);
        if(previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("first", Boolean.FALSE);
            edit.commit();
        }
        else {
            Intent intent = new Intent(this, Main2Activity.class);
                intent.putExtra("MyNumber",number);
                startActivity(intent);
                finish();

        }//  preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
      // preferences = getSharedPreferences("ShaPreferences", Context.MODE_PRIVATE);
     //  sharedPreferences.Editor editor = preferences.edit();
      //editor = preferences.edit();
        //boolean firstTime = preferences.getBoolean("first", true);
        //if(firstTime) {
          //  editor.putBoolean("first",false);
           // editor.putString("number", number);
            //Log.v("number" , " "+number);
           // editor.commit();
            //editor.apply();
        //}

//        else
  //      {
    //        Intent intent = new Intent(this, MainActivity.class);
      //      intent.putExtra("MyNumber",number);
        //    startActivity(intent);

          //  finish();
        //}
     //SharedPreferences pref = getApplicationContext().getSharedPreferences()

        setContentView(R.layout.activity_first_time);
        button = (Button) findViewById(R.id.go);
        editText = (EditText) findViewById(R.id.number);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                number = editText.getText().toString();
                SharedPreferences.Editor edit = prefs.edit();
                edit.putString("number",number);
                edit.commit();

                Log.v("Save",""+number);
                Intent i = new Intent(firstTime.this,Main2Activity.class);
                String str = number;
                i.putExtra("MyNumber",str);
                startActivity(i);

                Log.v("Tuesday"," "+str);
            Log.v("Monday" , " "+number);
            }

        });
      }

    //  Intent intent = new Intent(MainActivity.this,BroadcastReceiver);



}

