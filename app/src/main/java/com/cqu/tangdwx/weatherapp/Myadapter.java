package com.cqu.tangdwx.weatherapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by TangDWX on 2016/3/20.
 */
public class Myadapter extends BaseAdapter{
    Context context = null;
    ArrayList<HashMap<String, Object>> chatList = null;
    int[] layout;

    public Myadapter(Context con, ArrayList<HashMap<String, Object>> list, int[] lay) {
        super();
        this.context = con;
        this.chatList = list;
        this.layout = lay;
    }
    @Override
    public int getCount() {
        return chatList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        public ImageView imgView = null;
        public TextView textView = null;
        public ImageView innerImg = null;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int type = (Integer)chatList.get(position).get("type");
        convertView = LayoutInflater.from(context).inflate(layout[type],null);
        holder = new ViewHolder();
        if(type == 0) {
            holder.imgView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.imgView.setImageResource(R.drawable.me);
            holder.textView.setText(chatList.get(position).get("text").toString());
        }
        else {
            try {
                JSONTokener jsonParser = new JSONTokener(chatList.get(position).get("text").toString());
                // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
                // 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）
                JSONObject info = (JSONObject) jsonParser.nextValue();
                int code = info.getInt("code");
                if(code == 100000) {
                    String text = info.getString("text");
                    //Log.e("Log", text);
                    holder.imgView = (ImageView) convertView.findViewById(R.id.oimageView);
                    holder.textView = (TextView) convertView.findViewById(R.id.otextView);
                    holder.imgView.setImageResource(R.mipmap.ic_launcher);
                    holder.textView.setText(text);
                }
                else if (code == 200000) {
                    String text = info.getString("text");
                    holder.imgView = (ImageView) convertView.findViewById(R.id.oimageView);
                    holder.textView = (TextView) convertView.findViewById(R.id.otextView);
                    holder.imgView.setImageResource(R.mipmap.ic_launcher);
                    holder.textView.setText(text+"\n"+"URL:"+info.getString("url"));
                    //Bitmap bitmap = getHttpBitmap(info.getString("url"));
                    //holder.innerImg.setImageBitmap(bitmap);
                }
                else {
                    String text = info.getString("text");
                    //Log.e("Log", text);
                    holder.imgView = (ImageView) convertView.findViewById(R.id.oimageView);
                    holder.textView = (TextView) convertView.findViewById(R.id.otextView);
                    holder.imgView.setImageResource(R.mipmap.ic_launcher);
                    holder.textView.setText(text);
                }
            }
            catch (JSONException ex) {
            }
            //holder.imgView = (ImageView) convertView.findViewById(R.id.oimageView);
            //holder.textView = (TextView) convertView.findViewById(R.id.otextView);
            //holder.imgView.setImageResource(R.mipmap.ic_launcher);
            //holder.textView.setText(chatList.get(position).get("text").toString());
        }

        return convertView;
    }

    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return bitmap;

    }
}
