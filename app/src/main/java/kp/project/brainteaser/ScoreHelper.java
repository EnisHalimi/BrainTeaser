package kp.project.brainteaser;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KP Team on 28/03/2017.
 */

public class ScoreHelper  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "score.db";
    public static final String TABLE_NAME = "score";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "user_id";
    public static final String COL_3 = "game";
    public static final String COL_4 = "score";


    public ScoreHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+ "("+COL_1+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_2+ " INT, "+COL_3+ " TEXT, "+COL_4+ " INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
        onCreate(db);
    }

    public boolean create(int user_id, String game, int score)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2,user_id);
        cv.put(COL_3,game);
        cv.put(COL_4,score);
        if(db.insert(TABLE_NAME, null,cv) != -1)
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

    public Cursor getScore(int user,String game)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE user_id='"+user+"' AND game='"+game+"' ORDER BY score DESC LIMIT 1  ", null);
        return res;
    }

}
