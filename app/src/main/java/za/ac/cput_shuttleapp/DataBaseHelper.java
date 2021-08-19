package za.ac.cput_shuttleapp;
//Breyton Ernszten - 217203027
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "student_Registration.db";
    public static final String MY_DB_TABLE_NAME = "studenDetails";
    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "First_Name";
    public static final String COLUMN_3 = "Last_Name";
    public static final String COLUMN_4 = "Student_Number";
    public static final String COLUMN_5 = "Password";
    public static final String COLUMN_6 = "Cell_No";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null,  1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + MY_DB_TABLE_NAME + " (ID INTEGER, First_Name TEXT, Last_Name TEXT, Student_Number TEXT PRIMARY KEY, Password TEXT, Cell_No TEXT)");
         //Take out the code above if not working.The real code is seen below.
        //db.execSQL("CREATE TABLE " + MY_DB_TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, First_Name TEXT, Last_Name TEXT, Student_Number TEXT, Password TEXT, Cell_No TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MY_DB_TABLE_NAME);
        onCreate(db);
    }

    //Remove this method if it doesn't work.

}
