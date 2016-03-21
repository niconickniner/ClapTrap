package com.cqu.tangdwx.weatherapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by TangDWX on 2016/3/19.
 * Api key: da57abda3f3496372882834a06058c35
 * Api Url: http://www.tuling123.com/openapi/api
 */
public class JsonConnection extends AsyncTask<Void,Void,String>{

    String jsonResult;
    String httpUrl = "http://www.tuling123.com/openapi/api";
    String info = "";
    String key = "key=da57abda3f3496372882834a06058c35&";
    String userid = "&userid=eb2edb736";
    String httpArg = "";

    /**
     * @param urlAll
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "da57abda3f3496372882834a06058c35");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    protected String doInBackground(Void... params) {
        httpArg = key+info+userid;
        jsonResult = request(httpUrl, httpArg);
        return jsonResult;
    }
    @Override
    protected void onPostExecute(String result) {
        //Log.e("Log",result);
    }
    public String getStr() {
        return jsonResult;
    }

}
