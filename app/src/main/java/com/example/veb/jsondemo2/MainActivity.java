package com.example.veb.jsondemo2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VEB on 2016/9/5.
 */

public class MainActivity extends Activity implements ClickListener {

    private ListView mListView;
//    private GetPicture getP;
//    private CreateDb createDb;
    private static String jsonURL = "http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        SQLiteDatabase db = openOrCreateDatabase("JasonSave.db", Context.MODE_PRIVATE, null);
        mListView = (ListView) findViewById(R.id.list);
        new NewsAsyncTask().execute(jsonURL);
    }

    @Override
    public void onClicks(View item, View widget, int position, int which) {
        int check;
        if((position+1)%2 == 0){
            check = 1;
        }
        else {
            check = 0;
        }

        switch (check){
            case 1:
                Uri uri = Uri.parse("http://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                MainActivity.this.startActivity(intent);
                break;
            case 0:
                 uri = Uri.parse("http://www.hao123.com");
                 intent = new Intent(Intent.ACTION_VIEW,uri);
                MainActivity.this.startActivity(intent);
                break;
            default:
                break;
        }
    }

    private List<Pictures> getJsonData(String url) {
        List<Pictures> picturesList = new ArrayList<>();
        try {
            String jsonString = readStream(new URL(url).openStream());
            Pictures pictures;
            JSONObject jsonObject;
            String iconUrl = null;
            String text = null;
            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                iconUrl = jsonObject.getString("picSmall");
                text = jsonObject.getString("description");
                pictures = new Pictures(iconUrl, text);
                picturesList.add(pictures);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return picturesList;
    }

    private String readStream(InputStream is) {
        InputStreamReader isReader;
        String result = "";
        String line ="";
        try {
            isReader = new InputStreamReader(is, "utf-8");
            BufferedReader buffReader = new BufferedReader(isReader);
            while ((line = buffReader.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    class NewsAsyncTask extends AsyncTask<String, Void, List<Pictures>> {

        @Override
        protected List<Pictures> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<Pictures> result) {
            super.onPostExecute(result);
            ListAdapter adapter = new ListAdapter(MainActivity.this, result, mListView, MainActivity.this);
            mListView.setAdapter(adapter);
        }

    }
}
