package PizzaApp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PizzaDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "customers";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone_number";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PASSWORD = "password";



    public DatabaseManager(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creates the table.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_NAME + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_PASSWORD + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_PHONE + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_ADDRESS + " varchar(200) NOT NULL\n" +
                ");";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        db.execSQL(sql);
        onCreate(db);
    }

    public boolean addCustomer(String name, String password, String phone_number, String address) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_PHONE, phone_number);
        contentValues.put(COLUMN_ADDRESS, address);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME, null, contentValues) != -1;
    }

    boolean updateCustomer(int id, String name, String password, String phone_number, String address) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_PHONE, phone_number);
        contentValues.put(COLUMN_ADDRESS, address);
        return db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }

    boolean deleteCustomer(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }

    Cursor getAllCustomers() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
