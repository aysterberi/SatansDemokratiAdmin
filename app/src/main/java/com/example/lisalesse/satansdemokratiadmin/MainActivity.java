package com.example.lisalesse.satansdemokratiadmin;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.StrictMode;
import android.content.DialogInterface;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String antal, meddelande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
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

        //jajamän kommentarer
        Skicka.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                     antal = "";
                    meddelande = textRutan.getText().toString();
                    //Boolean checked = checkBoxen.isChecked();
                    if(checkBoxen.isChecked()){
                        antal = "3000";
                    }
                    else {
                        antal = Antalen.getText().toString();
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
                    System.out.println("meddelande: "+meddelande+" antal: "+antal);
                    System.out.println("antal"+ antal);
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
                                    Antalen.getText().clear();
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

            });
        };

}
