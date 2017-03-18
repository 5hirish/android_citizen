package com.alleviate.citizen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by felix on 11/1/16.
 * Created at Alleviate.
 * shirishkadam.com
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String dbName = "DB_CitiZen";
    public static final String dbCZ_table_Issue = "CZ_Issue";
    public static final String dbCZ_Id = "Id";
    public static final String dbCZ_Issue = "Issue";
    public static final String dbCZ_Opt = "Options";
    public static final String dbCZ_Answer = "Answer";
    public static final String dbCZ_Status = "Status";
    public static final String dbCZ_Explanation = "Explanation";
    public static final String dbCZ_ImageId = "ImageId";


    public static final int db_version = 1;


    public SQLiteHelper(Context context) {
        super(context, dbName, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String creat_table = "CREATE TABLE "+dbCZ_table_Issue+"  ("+dbCZ_Id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        dbCZ_Issue+" TEXT," + dbCZ_Opt+" TEXT,"+ dbCZ_Answer+" INTEGER, "+dbCZ_Status+" VARCHAR, " +
                        ""+dbCZ_Explanation+" TEXT, "+dbCZ_ImageId+" INTEGER)";

        sqLiteDatabase.execSQL(creat_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
