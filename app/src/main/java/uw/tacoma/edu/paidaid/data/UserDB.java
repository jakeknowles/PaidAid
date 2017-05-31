package uw.tacoma.edu.paidaid.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import uw.tacoma.edu.paidaid.R;

/**
 * @Author Jake Knowles
 * @Author Dmitriy Onishchenko
 * @version 5/30/2017
 *
 * User class for SQLite -- stores userID, rating, username, and email
 */
public class UserDB {

    /** Database version */
    public static final int DB_VERSION = 1;

    /** Database name */
    public static final String DB_NAME = "Users.db";

    /** User Table */
    private static final String USER_TABLE = "Users";

    /** User Database helper */
    private UserDBHelper mUserDBHelper;

    /** SQLiteDatabase for storing */
    private SQLiteDatabase mSQLiteDatabase;

    /** Constructor */
    public UserDB(Context context) {
        mUserDBHelper = new UserDBHelper(context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mUserDBHelper.getWritableDatabase();
    }

    /** Insert data into table */
    public boolean insertUser(int id, float rating, String username, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid", id);
        contentValues.put("user_rating", rating);
        contentValues.put("username", username);
        contentValues.put("email", email);

        long rowId = mSQLiteDatabase.insert(USER_TABLE, null, contentValues);
        return rowId != -1;
    }

    /** Inner User database helper class */
    class UserDBHelper extends SQLiteOpenHelper {

        /** Create user table String constant */
        private final String CREATE_USER_SQL;

        /** Drop user table String constant */
        private final String DROP_USER_SQL;

        /** Constructor */
        public UserDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            CREATE_USER_SQL = context.getString(R.string.CREATE_USER_SQL);
            DROP_USER_SQL = context.getString(R.string.DROP_USER_SQL);
        }

        /**
         *  onCreate launches user table info
         * @param sqLiteDatabase sqLiteDatabase
         */
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_USER_SQL);
        }

        /**
         * onUpgrade updates database if changes occur
         * @param sqLiteDatabase sqLiteDatabase
         * @param i is old version
         * @param i1 is new version
         */
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL(DROP_USER_SQL);
            onCreate(sqLiteDatabase);
        }
    }

    /**
     * Delete all the data from the COURSE_TABLE
     */
    public void logoutUser() {
        mSQLiteDatabase.delete(USER_TABLE, null, null);
    }

    /**
     * Getter for user id
     * @return int
     */
    public int getmUserId() {


        Cursor c = mSQLiteDatabase.query(USER_TABLE,
                new String[]{"userid"}, null, null, null, null, null);
        c.moveToFirst();



        return c.getInt(0);
    }

    /**
     * Getter for user rating
     * @return float
     */
    public float getmUserRating() {
        Cursor c = mSQLiteDatabase.query(USER_TABLE,
                new String[]{"user_rating"}, null, null, null, null, null);
        c.moveToFirst();

        return (float) c.getDouble(0);
    }

    /**
     * Getter for username
     * @return String
     */
    public String getmUsername() {
        Cursor c = mSQLiteDatabase.query(USER_TABLE,
                new String[]{"username"}, null, null, null, null, null);
        c.moveToFirst();



        return c.getString(0);
    }

    /**
     * Getter for email
     * @return String
     */
    public String getmEmail() {
        Cursor c = mSQLiteDatabase.query(USER_TABLE,
                new String[]{"email"}, null, null, null, null, null);
        c.moveToFirst();

        return c.getString(0);

    }
}