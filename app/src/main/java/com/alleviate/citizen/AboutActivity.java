package com.alleviate.citizen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView textView = (TextView)findViewById(R.id.textView);

        SQLiteHelper db = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase dbr = db.getReadableDatabase();

        //String[] col = {db.dbCZ_Id,db.dbCZ_Issue,db.dbCZ_OptA,db.dbCZ_OptB,db.dbCZ_OptC,db.dbCZ_Answer,db.dbCZ_Status,db.dbCZ_Explanation,db.dbCZ_ImageId};

        Cursor cur =dbr.query(SQLiteHelper.dbCZ_table_Issue, null, null, null, null, null, null);

        if(cur!=null){
            while (cur.moveToNext()){
                int id =cur.getInt(cur.getColumnIndex(SQLiteHelper.dbCZ_Id));
                String issue =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_Issue));
                String option =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_Opt));
                int answer =cur.getInt(cur.getColumnIndex(SQLiteHelper.dbCZ_Answer));
                String status =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_Status));
                String explanation =cur.getString(cur.getColumnIndex(SQLiteHelper.dbCZ_Explanation));
                int imageid =cur.getInt(cur.getColumnIndex(SQLiteHelper.dbCZ_ImageId));

                textView.append(id+":"+issue+":"+option+":"+answer+":"+status+":"+explanation+":"+imageid+"\n");

            }cur.close();
        }

        dbr.close();
    }
}
