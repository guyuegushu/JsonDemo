package com.example.veb.jsondemo2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;

/**
 * Created by VEB on 2016/9/14.
 */
public class DBManager {

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context){

        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void cacheTodb(Bitmap picture, String url){
        String Insert_Sql = "INSERT INTO Saves values(?, ?)";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 100, bos);
        Object[] args = new Object[]{
                url,
                bos.toByteArray()
        };
        db.execSQL(Insert_Sql, args);
        db.close();
    }

    public Bitmap getPicture(String url){
        Bitmap bitmap = null;
        byte[] picture = null;
        String args [] = {url};
        String Get_Sql = "SELECT picture FROM Saves _ID  WHERE _ID ";
        Cursor mCursor = db.rawQuery(Get_Sql, args);
        if(mCursor != null){
            if(mCursor.moveToFirst()){
                picture = mCursor.getBlob(mCursor.getColumnIndex("picture"));
            }
        }
        if(mCursor != null){
            mCursor.close();
        }
        db.close();
        if(picture != null){
            bitmap = BitmapFactory.decodeByteArray(picture, 0,picture.length);
        }
        return bitmap;
    }
}
