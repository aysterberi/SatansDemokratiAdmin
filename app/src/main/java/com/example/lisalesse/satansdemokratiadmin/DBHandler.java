package com.example.lisalesse.satansdemokratiadmin;


import android.util.Log;
import org.json.JSONObject;

public class DBHandler {


    /** calls for ApiMessageClass "MessageModel" **/
    private MessageModel model = new MessageModel("https://people.dsv.su.se/~anth3046/SatansDemokrati/api/v1/");

    public DBHandler() {
        /**
         * EMPTY CONSTRUCTOR
         */
    }


    /**
     * Creates a new entry in the DB
     */
    public void postMessageToDb(String Titel, String meddelande, String Antal ) {

        /** Adding data to JSONObjects */
        String messageJson = "{'Titel':\"" + Titel
                + "\",'Meddelande':\"" + meddelande
                + "\",'Antal':\"" + Antal
                + "\"}";
        /**
         * Call the API(MessageModel) with the JSonUserData.
         */
        try {
            JSONObject response = model.apiPost("create_message/", new JSONObject(messageJson));
            Log.d("LoginActivity", String.valueOf(response));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
