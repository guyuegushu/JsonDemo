package com.example.veb.jsondemo2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by VEB on 2016/9/9.
 */
public class CreateDb extends SQLiteOpenHelper {
    private final static int VERSION = 1;
    private final static String Database_Name = "JsonSave.db";


    public CreateDb(Context mContext){

        super(mContext, Database_Name, null,VERSION);
    }

//    public CreateDb(Context mContext, String name, SQLiteDatabase.CursorFactory factory, int version ) {
//
//        super(mContext, name, factory, version);
//
//    } //??

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db = this.getReadableDatabase();
        String tables = "create table if not exists  Saves(" +
                "_id INT PRIMARY KEY NOT NULL," +
                "picture BLOB)";
        db.execSQL(tables);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > oldVersion){
            db.execSQL("DROP TABLE IF EXISTS[launcher]");
        } else {
            return;
        }
        onCreate(db);
    }

}

