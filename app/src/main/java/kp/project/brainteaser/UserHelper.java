package kp.project.brainteaser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Date;

/**
 * Created by KP Team on 29/04/2017.
 */

public class UserHelper extends SQLiteOpenHelper{

        private static final String DATABASE_NAME = "user.db";
        private static final String TABLE_NAME = "user";
        private  static final String COL_1 = "ID";
        private static final String COL_2 = "name";
        private static final String COL_3 = "birthday";
        private static final String COL_4 = "username";
        private static final String COL_5 = "password";
        private static final String COL_6 = "status";


        public UserHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
            SQLiteDatabase db = this.getWritableDatabase();

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+TABLE_NAME+ "("+COL_1+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_2+ " TEXT, "+COL_3+ " TEXT, "+COL_4+" TEXT, "+COL_5+" TEXT, "+COL_6+ " TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
            onCreate(db);
        }

        public boolean insertData(String name, String birthday, String username, String password, String status)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(COL_2,name);
            cv.put(COL_3, birthday);
            cv.put(COL_4,username);
            cv.put(COL_5,password);
            cv.put(COL_6, status);
            if(db.insert(TABLE_NAME, null,cv) != -1)
                return true;
            else
            return false;
        }
        public boolean loginStatus(int id)
    {
        String status = "1";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_6,status);
        if(db.update(TABLE_NAME,cv,"IDID = '"+id+"'",null)> 1)
            return true;
        else
            return false;
    }
    public boolean logout()
    {
        String status = "0";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_6,status);
        if(db.update(TABLE_NAME,cv,null,null)> 1)
            return true;
        else
            return false;
    }

        public Cursor getData()
        {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
            return res;

        }
    }



