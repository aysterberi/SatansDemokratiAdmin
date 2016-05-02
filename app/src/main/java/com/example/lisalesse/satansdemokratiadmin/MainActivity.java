package com.example.lisalesse.satansdemokratiadmin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.CheckBoxPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.StrictMode;
import android.content.DialogInterface;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String antal, meddelande, dateTime;
    private Spinner dropdown;
    private Boolean eventSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpDropdown();


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final DBHandler dbHandler = new DBHandler();
        Button Skicka = (Button) findViewById(R.id.button_skicka);
        final EditText textRutan = (EditText) findViewById(R.id.messagebox);
        final EditText Antalen = (EditText) findViewById(R.id.antal);
        final CheckBox checkBoxen = (CheckBox) findViewById(R.id.checkalla);
        Button rensa = (Button) findViewById(R.id.button_rensa);
        assert Skicka != null;
        rensa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textRutan.getText().clear();
                Antalen.getText().clear();
                checkBoxen.setChecked(false);
            }

        });


        messageListener(dbHandler, Skicka, textRutan, Antalen, checkBoxen);
    }

    private void messageListener(final DBHandler dbHandler, Button skicka, final EditText textRutan, final EditText antalen, final CheckBox checkBoxen) {
        skicka.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (eventSelected) {
                        dbHandler.postEventToDb(meddelande, dateTime);
                        dropdown.setSelection(0);

                    } else {
                        antal = "";
                        meddelande = textRutan.getText().toString();
                        //Boolean checked = checkBoxen.isChecked();
                        if(checkBoxen.isChecked()){
                            antal = "3000";
                        }
                        else {
                            antal = antalen.getText().toString();
                        }
                        if(antal.isEmpty() && !checkBoxen.isChecked()){
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("Du måste ange antal användare");
                            alertDialog.setMessage("Ange antal användare eller klicka i alla boxen");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            return;
                                        }
                                    });
                            alertDialog.show();
                        }
                        if(meddelande.isEmpty()){
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("Du måste skriva in ett meddelande");
                            alertDialog.setMessage("Skriv in ett meddelande i boxen");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            return;
                                        }
                                    });
                            alertDialog.show();
                        }
                        if(!meddelande.isEmpty()&&!antal.isEmpty()) {
                            try {
                                JSONArray Usrs = new JSONArray(dbHandler.getRandomUser(antal));
                                for (int i = 0; i < Usrs.length(); i++) {
                                    JSONObject juan = Usrs.getJSONObject(i);
                                    String ursid = juan.getString("id");
                                    System.out.println(ursid);
                                    dbHandler.postMessageToDb(meddelande, ursid);

                                }
                                Snackbar.make(findViewById(R.id.parentbrain),"Meddelande skickat",Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
                                    @Override
                                    public void onDismissed(Snackbar snackbar, int event) {
                                        super.onDismissed(snackbar, event);
                                        textRutan.getText().clear();
                                        antalen.getText().clear();
                                        checkBoxen.setChecked(false);
                                        antal = "";
                                        meddelande="";
                                    }
                                }).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
    }

    private void setUpDropdown (){
        dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Välj Förbestämda Events", "Omröstning: Val", "Toby", "Lisa", "Robin", "Judas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        eventSelected = false;
                        meddelande = "";
                        dateTime = "";
                        break;
                    case 1:
                        eventSelected = true;
                        meddelande = "1";
                        dateTime = getDateTime();
                        break;
                    case 2:
                        eventSelected = true;
                        meddelande = "2";
                        dateTime = getDateTime();
                        break;
                    case 3:
                        eventSelected = true;
                        meddelande = "3";
                        dateTime = getDateTime();
                        break;
                    case 4:
                        eventSelected = true;
                        meddelande = "4";
                        dateTime = getDateTime();
                        break;
                    case 5:
                        eventSelected = true;
                        meddelande = "5";
                        dateTime = getDateTime();
                        break;
                    case 6:
                        eventSelected = true;
                        meddelande = "6";
                        dateTime = getDateTime();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String getDateTime () {
        Date dt = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currenTime = dateFormat.format(dt);
        return currenTime;
    }
}
