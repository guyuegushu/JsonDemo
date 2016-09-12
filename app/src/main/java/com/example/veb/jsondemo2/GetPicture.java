package com.example.veb.jsondemo2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.util.zip.Inflater;

/**
 * Created by VEB on 2016/9/12.
 */
public class GetPicture {

    CreateDb createDb;
    public void getPicture(Context mContext){
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String Get_Sql = "SELECT picture FROM launcher";
        View v = mInflater.inflate(R.layout.activity_list,null);
        ImageView image = (ImageView)v.findViewById(R.id.image1);
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
            bis = new ByteArrayInputStream(picture);
            image.setImageDrawable(Drawable.createFromStream(bis, "picture"));
        }

    }
}
