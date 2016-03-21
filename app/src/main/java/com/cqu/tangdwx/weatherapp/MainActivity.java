package com.cqu.tangdwx.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String,Object>> chatList = null;
    Myadapter myadapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatList = new ArrayList<HashMap<String, Object>>();
        ListView lv = (ListView) findViewById(R.id.listView);
        int[] lays = {R.layout.local,R.layout.webside};
        myadapter = new Myadapter(this,chatList,lays);
        lv.setAdapter(myadapter);
    }
    public void addTest(String test, int type) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("img", R.drawable.me);
        map.put("text", test);
        map.put("type", type);
        chatList.add(map);
    }
    public void click(View v) {
        EditText ed = (EditText)findViewById(R.id.editText);
        String word = (ed.getText()+"").toString();
        if(word.length() == 0)
            return;
        ed.setText("");
        addTest(word, 0);

        jsonCon(word);

        myadapter.notifyDataSetChanged();
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setSelection(chatList.size()-1);
    }
    public void jsonCon(String input) {
        JsonConnection jsonConnection  = new JsonConnection();
        //convert Char to UTf-8 and add info
        if(input.length() == 0) return;
        jsonConnection.info = "info="+getUTF8XMLString(input);

        jsonConnection.execute();
        String test = "Nothing";
        try {
            test = jsonConnection.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, test,Toast.LENGTH_SHORT).show();
        addTest(test, 1);
    }
    public static String getUTF8XMLString(String xml) {
        // A StringBuffer Object
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        String xmlUTF8="";
        try {
            xmString = new String(sb.toString().getBytes("UTF-8"));
            xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // return to String Formed
        return xmlUTF8;
    }
}
