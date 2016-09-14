package com.example.veb.jsondemo2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by VEB on 2016/9/12.
 */
public class CachePicture {

    CreateDb createDb;
    Pictures pictures;
    public void cacheToTable(Bitmap picture, Context mContext, String url){
//        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = mInflater.inflate(R.layout.activity_list,null);
//        ImageView iv = (ImageView) v.findViewById(R.id.image1);
//        iv.setImageDrawable(picture);
        createDb = new CreateDb(mContext);

        String Insert_Sql = "INSERT INTO Saves values(?,?)";
        SQLiteDatabase db2 = createDb.getWritableDatabase();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        picture.compress(Bitmap.CompressFormat.PNG, 100, bos);
        Object[] args = new Object[]{
                url,
                bos.toByteArray()
        };
        db2.execSQL(Insert_Sql, args);
        db2.close();

    }
}
