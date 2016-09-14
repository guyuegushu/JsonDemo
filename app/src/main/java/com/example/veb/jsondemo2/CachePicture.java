package com.example.veb.jsondemo2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by VEB on 2016/9/12.
 */
public class CachePicture {

    CreateDb createDb;
    public void cacheTodb(Drawable picture, Context mContext){
        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(R.layout.activity_list,null);
        ImageView iv = (ImageView) v.findViewById(R.id.image1);
        iv.setImageDrawable(picture);
        String Insert_Sql = "INSERT INTO launcher(photo) values(?)";
        SQLiteDatabase db2 = createDb.getWritableDatabase();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ((BitmapDrawable) iv.getDrawable()).getBitmap().compress(
                Bitmap.CompressFormat.PNG, 100, bos
        );
        Object[] args = new Object[]{
                bos.toByteArray()
        };
        db2.execSQL(Insert_Sql, args);
        db2.close();

    }
}
