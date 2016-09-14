package com.example.veb.jsondemo2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by VEB on 2016/9/5.
 */
public class ImageLoader {
    private DBManager dbManager;
    private ListView mListView;
    private Set<NewsAsyncTask> mTasks;


    public ImageLoader(ListView listView, Context context) {
        mListView = listView;
        dbManager = new DBManager(context);
        mTasks = new HashSet<>();

    }

    public void addBitmapToCache(String url, Bitmap bitmap) {

        if (getBitmapFromCache(url) == null) {

            dbManager.cacheTodb(bitmap, url);
        }
    }

    public Bitmap getBitmapFromCache(String url)
    {
        return dbManager.getPicture(url);
    }

    public Bitmap getBitmapFromUrl(String urlString) {
        InputStream is = null;
        Bitmap bitmap;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(is);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally
        {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }


    public void loadImage(int sizes) {
        for (int i = 0; i < sizes; i++) {
            String url = ListAdapter.URLs[i];
            Bitmap bitmap = getBitmapFromCache(url);
            if (bitmap == null) {
                NewsAsyncTask task = new NewsAsyncTask(url);
                task.execute(url);
                mTasks.add(task);
            }else {
                ImageView imageView = (ImageView) mListView.findViewWithTag(url);
                imageView.setImageBitmap(bitmap);
            }
        }
    }



    public void showImages(ImageView imageView, String iconUrl) {
        Bitmap bitmap = getBitmapFromCache(iconUrl);
        if (bitmap == null) {
            imageView.setImageResource(R.mipmap.ic_launcher);
        }else {
            imageView.setImageBitmap(bitmap);
        }

    }

    class NewsAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private String mUrl;
        public NewsAsyncTask(String url) {
            mUrl = url;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapFromUrl(url);
            if (bitmap != null) {
                addBitmapToCache(url, bitmap);
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            mTasks.remove(this);
        }
    }

}
