package uw.tacoma.edu.paidaid.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import uw.tacoma.edu.paidaid.R;

/**
 * Created by jake on 5/30/17.
 */

public class UserDB {

    public static final int DB_VERSION = 1;

    public static final String DB_NAME = "User.db";

    private static final String USER_TABLE = "User";

    private UserDBHelper mUserDBHelper;

    private SQLiteDatabase mSQLiteDatabase;

    public UserDB(Context context) {
        mUserDBHelper = new UserDBHelper(context, DB_NAME, null, DB_VERSION);
        mSQLiteDatabase = mUserDBHelper.getWritableDatabase();
    }

    public boolean insertUser(int id, float rating, String username, String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid", id);
        contentValues.put("user_rating", rating);
        contentValues.put("username", username);
        contentValues.put("email", email);

        long rowId = mSQLiteDatabase.insert("User", null, contentValues);
        return rowId != -1;
    }

    public void closeDB() {
        mSQLiteDatabase.close();
    }

    public void updateUser() {

        mUserDBHelper.onUpgrade(mSQLiteDatabase, 1, 1);
    }



    class UserDBHelper extends SQLiteOpenHelper {

        private final String CREATE_USER_SQL;

        private final String DROP_USER_SQL;

        public UserDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            CREATE_USER_SQL = context.getString(R.string.CREATE_USER_SQL);
            DROP_USER_SQL = context.getString(R.string.DROP_USER_SQL);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_USER_SQL);
        }

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
