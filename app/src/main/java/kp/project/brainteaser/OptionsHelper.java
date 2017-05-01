package kp.project.brainteaser;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by KP Team on 28/03/2017.
 */

public class OptionsHelper  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "options.db";
    public static final String TABLE_NAME = "options";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "music";
    public static final String COL_3 = "sound";
    public static final String COL_4 = "language";


    public OptionsHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = this.getData();
        if(res.getCount()== 0)
        {
            create(1,1,1);
        }


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+ "("+COL_1+ "ID INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_2+ " INT, "+COL_3+ " INT, "+COL_4+ " INT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST "+TABLE_NAME);
        onCreate(db);
    }
    private boolean create(int music, int sound, int language)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2,music);
        cv.put(COL_3,sound);
        cv.put(COL_4,language);
        if(db.insert(TABLE_NAME, null,cv) != -1)
            return true;
        else
            return false;
    }

    public boolean update(int music, int sound, int language)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2,music);
        cv.put(COL_3,sound);
        cv.put(COL_4,language);
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
}
