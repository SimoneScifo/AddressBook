package simone.it.addressbook.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import simone.it.addressbook.Models.User;

/**
 * Created by Simone on 11/03/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String COLUMN_NAME_ID ="ID";
    private static final String COLUMN_NAME_NAME = "name";
    private static final String COLUMN_NAME_ADDRESS = "address";
    private static final String COLUMN_NAME_PHONE = "phone";
    private static final String COLUMN_NAME_EMAIL = "email";
    //Incrementare la versione se cambio lo schema del database
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME = "User";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ DatabaseHandler.TABLE_NAME+ "("+ DatabaseHandler.COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
            DatabaseHandler.COLUMN_NAME_NAME + " TEXT,"+ DatabaseHandler.COLUMN_NAME_ADDRESS + " TEXT," + DatabaseHandler.COLUMN_NAME_PHONE + " TEXT," + DatabaseHandler.COLUMN_NAME_EMAIL + " TEXT)";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseHandler.TABLE_NAME;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addUser (User user){
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, user.getName());
        values.put(COLUMN_NAME_ADDRESS, user.getAddress());
        values.put(COLUMN_NAME_PHONE, user.getPhone());
        values.put(COLUMN_NAME_EMAIL, user.getEmail());

// Insert the new row, returning the primary key value of the new row
        long i=db.insert(DatabaseHandler.TABLE_NAME, null, values);
        user.setId((int)i);
        db.close();
    }

    // Getting All users
    public ArrayList<User> getAllUsers() {
        ArrayList<User> usersList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setAddress(cursor.getString(2));
                user.setPhone(cursor.getString(3));
                user.setEmail(cursor.getString(4));
                // Adding User to list
                usersList.add(user);
            } while (cursor.moveToNext());
        }

        // return Users list
        return usersList;
    }

    // Updating single User
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NAME, user.getName());
        values.put(COLUMN_NAME_ADDRESS, user.getAddress());
        values.put(COLUMN_NAME_PHONE, user.getPhone());
        values.put(COLUMN_NAME_EMAIL, user.getEmail());
        // updating row
        return db.update(TABLE_NAME, values, COLUMN_NAME_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_NAME_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public ArrayList<User> getSearchUsers(CharSequence s) {
        ArrayList<User> usersList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME_NAME + " LIKE '" + s + "%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setName(cursor.getString(1));
                user.setAddress(cursor.getString(2));
                user.setPhone(cursor.getString(3));
                user.setEmail(cursor.getString(4));
                // Adding User to list
                usersList.add(user);
            } while (cursor.moveToNext());
        }

        // return Users list
        return usersList;
    }
}
