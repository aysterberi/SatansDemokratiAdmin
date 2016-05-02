package com.example.lisalesse.satansdemokratiadmin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String antal, meddelande, dateTime;
    private Spinner dropdown;
    private Boolean eventSelected;
    private EditText textRutan, antalen;
    private CheckBox checkBoxen;
    private AlertDialog alertDialog;

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
        Button send = (Button) findViewById(R.id.button_skicka);
        alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        textRutan = (EditText) findViewById(R.id.messagebox);
        antalen = (EditText) findViewById(R.id.antal);
        checkBoxen = (CheckBox) findViewById(R.id.checkalla);
        Button clear = (Button) findViewById(R.id.button_rensa);
        if (clear != null) {
            clear.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    textRutan.getText().clear();
                    antalen.getText().clear();
                    checkBoxen.setChecked(false);
                }
            });
        }
        messageListener(dbHandler, send, textRutan, antalen, checkBoxen);
    }

    private void messageListener(final DBHandler dbHandler, Button skicka, final EditText textRutan, final EditText antalen, final CheckBox checkBoxen) {
        skicka.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (eventSelected) {
                    dbHandler.postEventToDb(meddelande, dateTime);
                    getSnackBar();

                } else {
                    antal = "";
                    meddelande = textRutan.getText().toString();
                    //Boolean checked = checkBoxen.isChecked();
                    if(checkBoxen.isChecked()){
                        antal = "3000";
                    } else {
                        antal = antalen.getText().toString();
                    }
                    if(antal.isEmpty() && !checkBoxen.isChecked()){
                        alertDialog.setTitle("Du måste ange antal användare");
                        alertDialog.setMessage("Ange antal användare eller klicka i alla boxen");
                        getAlertDialog();
                    } else if(meddelande.isEmpty()){
                        alertDialog.setTitle("Du måste skriva in ett meddelande");
                        alertDialog.setMessage("Skriv in ett meddelande i boxen");
                        getAlertDialog();
                    }else if(!meddelande.isEmpty()&&!antal.isEmpty()) {
                        try {
                            JSONArray Usrs = new JSONArray(dbHandler.getRandomUser(antal));
                            for (int i = 0; i < Usrs.length(); i++) {
                                JSONObject juan = Usrs.getJSONObject(i);
                                String ursid = juan.getString("id");
                                System.out.println(ursid);
                                dbHandler.postMessageToDb(meddelande, ursid);
                                getSnackBar();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void getAlertDialog() {
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialog.show();
    }

    private void getSnackBar() {
        Snackbar.make(findViewById(R.id.parentbrain),"Meddelande skickat",Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                textRutan.getText().clear();
                antalen.getText().clear();
                checkBoxen.setChecked(false);
                antal = "";
                dropdown.setSelection(0);
            }
        }).show();
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
                        eventAndDate();
                        meddelande = "1";
                        break;
                    case 2:
                        eventAndDate();
                        meddelande = "2";
                        break;
                    case 3:
                        eventAndDate();
                        meddelande = "3";
                        break;
                    case 4:
                        eventAndDate();
                        meddelande = "4";
                        break;
                    case 5:
                        eventAndDate();
                        meddelande = "5";
                        break;
                    case 6:
                        eventAndDate();
                        meddelande = "6";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void eventAndDate() {
        eventSelected = true;
        dateTime = getDateTime();
    }

    private String getDateTime () {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currenTime = dateFormat.format(date);
        return currenTime;
    }
}
