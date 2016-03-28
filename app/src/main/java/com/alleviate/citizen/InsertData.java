package com.alleviate.citizen;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Scanner;

/**
 * Created by felix on 28/3/16.
 * Created at Alleviate.
 * shirishkadam.com
 */
public class InsertData implements Runnable {

    Context context;

    public InsertData(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        SQLiteHelper db = new SQLiteHelper(context);
        SQLiteDatabase dbw = db.getWritableDatabase();

        try {

            Scanner in = new Scanner(context.getAssets().open("issues.txt"));
            String status = "N";

            in.nextLine();		//Skip Header

            String regex = "\\|";

            while (in.hasNext()) {
                String[] row = in.nextLine().split(regex);

                int id = Integer.parseInt(row[0]);
                String issue = row[1];
                String optA = row[2];
                String optB = row[3];
                String optC = row[4];
                String answer = row[5];
                String explanation = row[6];
                int imageid = Integer.parseInt(row[7]);

                ContentValues insert_data = new ContentValues();
                insert_data.put(db.dbCZ_Issue,issue);
                insert_data.put(db.dbCZ_OptA,optA);
                insert_data.put(db.dbCZ_OptB, optB);
                insert_data.put(db.dbCZ_OptC, optC);
                insert_data.put(db.dbCZ_Answer, answer);
                insert_data.put(db.dbCZ_Status, status);
                insert_data.put(db.dbCZ_Explanation, explanation);
                insert_data.put(db.dbCZ_ImageId, imageid);

                long dbid = dbw.insert(db.dbCZ_table_Issue, null, insert_data);
                Log.d("Data",dbid+"");
            }

        } catch (java.io.IOException e) {
            Log.d("Error",e.toString());
            System.out.println("File Not Found : "+e);
        }

        dbw.close();

    }
}
