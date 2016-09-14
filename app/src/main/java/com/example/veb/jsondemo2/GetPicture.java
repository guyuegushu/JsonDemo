package com.example.veb.jsondemo2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;

/**
 * Created by VEB on 2016/9/12.
 */
public class GetPicture {

    private CreateDb createDb;
    public Bitmap getPicture(Context mContext){
        String Get_Sql = "SELECT picture FROM Saves";
        byte[] picture = null;
        createDb = new CreateDb(mContext);
        SQLiteDatabase db3 = createDb.getReadableDatabase();
        Cursor mCursor = db3.rawQuery(Get_Sql, null);
        if(mCursor != null){
            if(mCursor.moveToFirst()){
                picture = mCursor.getBlob(mCursor.getColumnIndex("picture"));
            }
        }

        if(mCursor != null){
            mCursor.close();
        }
        db3.close();

        ByteArrayInputStream bis = null;
        if(picture != null){
            return BitmapFactory.decodeByteArray(picture, 0, picture.length);
        }
        return null;
    }
}
