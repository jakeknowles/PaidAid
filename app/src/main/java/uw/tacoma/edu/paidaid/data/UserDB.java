package uw.tacoma.edu.paidaid.data;

import android.content.ContentValues;
import android.content.Context;
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
    public static final String DB_NAME = "User.db";

    /** User Table */
    private static final String USER_TABLE = "User";

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

        long rowId = mSQLiteDatabase.insert("User", null, contentValues);
        return rowId != -1;
    }

    /** Close database */
    public void closeDB() {
        mSQLiteDatabase.close();
    }

    /** Update user table */
    public void updateUser() {
        mUserDBHelper.onUpgrade(mSQLiteDatabase, 1, 1);
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

//    public String getmUserId() {
//        return mUserId;
//    }
//
//    public String getmUserRating() {
//        return mUserRating;
//    }
//
//    public String getmUsername() {
//        return mUsername;
//    }
//
//    public String getmEmail() {
//        return mEmail;
//    }







}
