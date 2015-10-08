package com.example.eduardo.tcc.Push;

import android.content.Context;
import android.view.View;

import com.parse.ParsePush;
import com.parse.ParseUser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wagner on 06/10/2015.
 */
public class DataPush {

    private static final String APPLICATION_ID = "ih17LzHvWd7nsSZ9iQSqo0ymIfXWwYzmMG3sOilj";
    private static final String REST_API_KEY = "PJW3q1jRs1Pll0LxfovghEzEjDiKufTPMUBc8yjH";
    private static final String PUSH_URL = "https://api.parse.com/1/push";

    public static void main(String[] args) {
        String[] channels = new String[]{"testsddg"};
        String type = "android";
        Map<String, String> data = new HashMap<String, String>();
        data.put("alert", "push data test");

        try {
            //new DataPush().sendPost(channels, type, data);
            sendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPost(String[] channels, String type, Map<String, String> data) throws Exception {
        JSONObject jo = new JSONObject();
        jo.put("channels", ParseUser.getCurrentUser().getObjectId());
        if(type != null) {
            //??type?????android?ios???
            jo.put("type", type);
        }
        jo.put("data", data);

        this.pushData(jo.toString());
    }

    private void pushData(String postData) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        HttpEntity entity = null;
        String responseString = null;
        HttpPost httpost = new HttpPost(PUSH_URL);
        httpost.addHeader("X-Parse-Application-Id", APPLICATION_ID);
        httpost.addHeader("X-Parse-REST-API-Key", REST_API_KEY);
        httpost.addHeader("Content-Type", "application/json");
        StringEntity reqEntity = new StringEntity(postData);
        httpost.setEntity(reqEntity);
        response = httpclient.execute(httpost);
        entity = response.getEntity();
        if (entity != null) {
            responseString = EntityUtils.toString(response.getEntity());
        }

        System.out.println(responseString);
    }

    //Use Java methods to push a message
    public static void sendMessage()
    {
        ParsePush push = new ParsePush();
        String message = "Client message";// + Integer.toString(i++);
        push.setChannel(ParseUser.getCurrentUser().getObjectId());
        push.setMessage(message);
        push.sendInBackground();
    }
}
