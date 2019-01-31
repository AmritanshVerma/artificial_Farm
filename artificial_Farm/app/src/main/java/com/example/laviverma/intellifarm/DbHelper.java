package com.example.laviverma.intellifarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lavi Verma on 09-Jul-18.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = DbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "customer.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_TABLE =  "CREATE TABLE " + Contact.Entry.TABLE_NAME + " ("
                +Contact.Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contact.Entry.COLUMN_name + " TEXT NOT NULL, "
                + Contact.Entry.COLUMN_application_key+ " TEXT, "
                + Contact.Entry.COLUMN_application_password + " TEXT, "
                + Contact.Entry.COLUMN_password + " TEXT, "
                + Contact.Entry.COLUMN_email + " TEXT, "
                + Contact.Entry.COLUMN_address + " TEXT, "
                + Contact.Entry.COLUMN_phone_number + " TEXT, "
                + Contact.Entry.COLUMN_poly_farms+ " TEXT, "
                + Contact.Entry.COLUMN_farm_types + " TEXT); ";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
