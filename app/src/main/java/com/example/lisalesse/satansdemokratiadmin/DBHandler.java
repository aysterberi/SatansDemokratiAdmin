package com.example.lisalesse.satansdemokratiadmin;


import android.util.Log;
import org.json.JSONObject;
import org.json.JSONArray;

public class DBHandler {


    /** calls for ApiMessageClass "MessageModel" **/
    private MessageModel model = new MessageModel("https://people.dsv.su.se/~joso8829/Satansdemokrati/api/v1/");


    public DBHandler() {
        /**
         * EMPTY CONSTRUCTOR
         */
    }

    public String getRandomUser(String antal){
        String messageJson = "{'Antal':\"\" + Antal}";
        System.out.println(antal+" antal i db");
        try{
            System.out.println("hej");
            //String antalUsr =String.valueOf( model.apiGet("get_random_userid/" + antal));
            JSONArray antalUsr = model.apiGetrnd("get_random_userid/" + antal);
            System.out.println("jsonObjektet"+String.valueOf(antalUsr));
            Log.d("LoginActivity", String.valueOf(antalUsr));
            return String.valueOf(antalUsr);
        }
        catch (Exception e){
            //
        }
        return null;
    }
    /**
     * Creates a new entry in the DB
     */
    public void postMessageToDb(String meddelande, String UserId ) {
        /** Adding data to JSONObjects */
        String messageJson = "{'meddelande':\"" + meddelande
                + "\",'Userid':\"" + UserId
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
    public void postEventToDb(String meddelande) {
        /** Adding data to JSONObjects */
        String messageJson = "{'EventId':\"" + meddelande
                + "\"}";

        /**
         * Call the API(MessageModel) with the JSonUserData.
         */
        try {
            JSONObject response = model.apiPost("create_event/", new JSONObject(messageJson));
            Log.d("LoginActivity", String.valueOf(response));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
