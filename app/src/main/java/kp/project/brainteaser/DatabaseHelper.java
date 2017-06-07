package kp.project.brainteaser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.Date;

/**
 * Created by KP Team on 29/04/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "brainteaser.db";
    private static final String USER_TABLE_NAME = "user";
    private static final String USER_COL_1 = "ID";
    private static final String USER_COL_2 = "name";
    private static final String USER_COL_3 = "age";
    private static final String USER_COL_4 = "username";
    private static final String USER_COL_5 = "password";
    private static final String USER_COL_6 = "status";
    public static final String OPTIONS_TABLE_NAME = "options";
    public static final String OPTIONS_COL_1 = "ID";
    public static final String OPTIONS_COL_2 = "music";
    public static final String OPTIONS_COL_3 = "sound";
    public static final String OPTIONS_COL_4 = "language";
    public static final String SCORE_TABLE_NAME = "score";
    public static final String SCORE_COL_1 = "ID";
    public static final String SCORE_COL_2 = "user_id";
    public static final String SCORE_COL_3 = "game";
    public static final String SCORE_COL_4 = "score";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE_NAME + "(" + USER_COL_1 + "ID INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_COL_2 + " TEXT, " + USER_COL_3 + " INTEGER, " + USER_COL_4 + " TEXT, " + USER_COL_5 + " TEXT, " + USER_COL_6 + " TEXT)");
        db.execSQL("create table " + OPTIONS_TABLE_NAME + "(" + OPTIONS_COL_1 + "ID INTEGER PRIMARY KEY AUTOINCREMENT, " + OPTIONS_COL_2 + " INT, " + OPTIONS_COL_3 + " INT, " + OPTIONS_COL_4 + " INT)");
        db.execSQL("create table " + SCORE_TABLE_NAME + "(" + SCORE_COL_1 + "ID INTEGER PRIMARY KEY AUTOINCREMENT, " + SCORE_COL_2 + " INT, " + SCORE_COL_3 + " TEXT, " + SCORE_COL_4 + " INT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + OPTIONS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXIST " + SCORE_TABLE_NAME);
        onCreate(db);
    }

    public boolean createUser(String name, int age, String username, String password, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_COL_2, name);
        cv.put(USER_COL_3, age);
        cv.put(USER_COL_4, username);
        cv.put(USER_COL_5, password);
        cv.put(USER_COL_6, status);
        if (db.insert(USER_TABLE_NAME, null, cv) != -1)
            return true;
        else
            return false;
    }

    private boolean createOptions(int music, int sound, int language) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(OPTIONS_COL_2, music);
        cv.put(OPTIONS_COL_3, sound);
        cv.put(OPTIONS_COL_4, language);
        if (db.insert(OPTIONS_TABLE_NAME, null, cv) != -1)
            return true;
        else
            return false;
    }

    public boolean createScore(int user_id, String game, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SCORE_COL_2, user_id);
        cv.put(SCORE_COL_3, game);
        cv.put(SCORE_COL_4, score);
        if (db.insert(SCORE_TABLE_NAME, null, cv) != -1)
            return true;
        else
            return false;
    }

    public boolean loginStatus(int id) {
        String status = "1";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_COL_6, status);
        if (db.update(USER_TABLE_NAME, cv, "IDID = '" + id + "'", null) > 1)
            return true;
        else
            return false;
    }

    public boolean logout() {
        String status = "0";
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_COL_6, status);
        if (db.update(USER_TABLE_NAME, cv, null, null) > 1)
            return true;
        else
            return false;
    }


    public boolean updateOptions(int music, int sound, int language) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(OPTIONS_COL_2, music);
        cv.put(OPTIONS_COL_3, sound);
        cv.put(OPTIONS_COL_4, language);
        if (db.insert(OPTIONS_TABLE_NAME, null, cv) != -1)
            return true;
        else
            return false;
    }

    public Cursor getScore(int user, String game) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + SCORE_TABLE_NAME + " WHERE user_id='" + user + "' AND game='" + game + "' ORDER BY score DESC LIMIT 1  ", null);
        return res;
    }

    public Cursor getOptionsData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + OPTIONS_TABLE_NAME, null);
        return res;

    }

    public Cursor getUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + USER_TABLE_NAME, null);
        return res;

    }

    public Cursor getScoreData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + SCORE_TABLE_NAME, null);
        return res;

    }

}




