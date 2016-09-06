package com.example.veb.jsondemo2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class MainActivity extends Activity {

    private ListView mListView;
    private static String jsonURL = "http://www.imooc.com/api/teacher?type=4&num=30";//json数据网址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mListView = (ListView) findViewById(R.id.list);
        new NewsAsyncTask().execute(jsonURL);
    }
    /**
     * 将URL网址上的json数据转化为我们所需的对象
     * @return
     */
    private List<Pictures> getJsonData(String url)
    {
        List<Pictures> picturesList = new ArrayList<Pictures>();//保存解析出来的所有的数据
        try {
            //获取到json字符串
            String jsonString = readStream(new URL(url).openStream());//和url.openConnection().getInputStream()一样
            //Log.d("MainActivity", jsonString);
            //将获取到的json字符串变为jsonObject对象，打开网址可以看出data节点是一个jsonArray,array里面存放了一个个的jsonObject
            Pictures pictures;
            JSONObject jsonObject;
            String iconUrl = null;
            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                iconUrl = jsonObject.getString("picSmall");//图片网址
                pictures = new Pictures(iconUrl);
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

    /**
     * 解析网络返回的数据
     */
    private String readStream(InputStream is)
    {
        InputStreamReader isReader;
        String result = "";
        String line ="";
        try {
            isReader = new InputStreamReader(is, "utf-8");//将字节流转化为字符流
            BufferedReader buffReader = new BufferedReader(isReader);//从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取
            while ((line = buffReader.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }
    /**
     * 构造一个AsyncTask，传入String类型的URL，返回一个NewsBean对象，每一个对象就是
     * listview中的每一行数据，包括一个icon,title,content
     */
    class NewsAsyncTask extends AsyncTask<String, Void, List<Pictures>>
    {

        @Override
        protected List<Pictures> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<Pictures> result) {
            super.onPostExecute(result);
            // 访问网络并解析json成功后返回结果，即我们设置的List<NewsBean>
            ListAdapter adapter = new ListAdapter(MainActivity.this, result, mListView);
            mListView.setAdapter(adapter);
        }

    }
}
