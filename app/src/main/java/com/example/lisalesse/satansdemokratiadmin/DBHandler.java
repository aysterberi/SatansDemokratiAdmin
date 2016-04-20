package com.example.lisalesse.satansdemokratiadmin;


import android.util.Log;
import org.json.JSONObject;

public class DBHandler {


    /** calls for ApiMessageClass "MessageModel" **/
    private MessageModel model = new MessageModel("https://people.dsv.su.se/~joso8829/Satansdemokrati/api/v1/");


    public DBHandler() {
        /**
         * EMPTY CONSTRUCTOR
         */
    }

    public void getRandomUser(String antal){
        String messageJson = "{'Antal':\"\" + Antal}";
        try{
            JSONObject response = model.apiPost("Get_random_user", new JSONObject(messageJson));
            Log.d("LoginActivity", String.valueOf(response));

        }
        catch (Exception e){
            //
        }
    }
    /**
     * Creates a new entry in the DB
     */
    public void postMessageToDb(String meddelande, String Antal ) {
        /** Adding data to JSONObjects */
        String messageJson = "{'meddelande':\"" + meddelande
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
