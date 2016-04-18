package com.example.lisalesse.satansdemokratiadmin;

import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //final DBHandler dbHandler = new DBHandler();
        Button Skicka = (Button) findViewById(R.id.button_skicka);
        final EditText textRutan = (EditText) findViewById(R.id.messagebox);
        final EditText Antalen = (EditText) findViewById(R.id.antal);
        final CheckBox checkBoxen = (CheckBox) findViewById(R.id.checkalla);

        assert Skicka != null;
        Skicka.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String meddelande = textRutan.getText().toString();
                    //Boolean checked = checkBoxen.isChecked();
                    String antal = Antalen.getText().toString();
                    System.out.println("meddelande: "+meddelande+" antal: "+antal);


                   // dbHandler.postMessageToDb(titel, meddelande, antal);
                }

            });
        };





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
