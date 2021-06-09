package com.example.weshopapplication.DataLayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;



public class ContactUsDatabase {

    private static final String DATABASE_NAME = "complaints.db"; // The Database to create
    private static int DB_VERSION = 1; // Database version.

    private static final String TABLE_NAME = "issues"; // The table name
    private static Context context; // The current context.

    private static final String INSERT_DATA = "INSERT INTO " + TABLE_NAME
            + " (username, email, phone_number, problem) VALUES (?,?,?,?)"; // Insert Query (DML) that inserts data into the contacts DB
    private SQLiteStatement sqlStatement; // The SQL statement

    private static SQLiteDatabase db; // The SQL database

    public ContactUsDatabase(Context context) { // Constructor for the database manipulator
        ContactUsDatabase.context = context; // Sets the current context
        OpenHelper helper = new OpenHelper(ContactUsDatabase.context);
        ContactUsDatabase.db = helper.getWritableDatabase();

        this.sqlStatement = ContactUsDatabase.db.compileStatement(INSERT_DATA); // Sets the sql statement to compile the INSERT DATA query to insert into the DB.

    }

    // Routine that inserts data into the table
    public long insert(String username, String email, String phone_number, String problem) { // Routine to insert data into the table
        this.sqlStatement.bindString(1, username);
        this.sqlStatement.bindString(2, email);

        this.sqlStatement.bindString(3, phone_number); // Bind the
        this.sqlStatement.bindString(4, problem);

        return this.sqlStatement.executeInsert(); // Return the execution of the statement
    }


    public void deleteAllData() { // Routine to delete all the data from the DB
        db.delete(TABLE_NAME, null, null); // Deletes the table if required
    }

    public List<String[]> selectAllData() { // Routine to select all the data from the db
        List<String[]> listOfComplaints = new ArrayList<>(); // An array list of complaints

        Cursor cursor = db.query(TABLE_NAME, new String[]{"id", "username", "email", "phone_number", "problem"}, null, null, null, null, "username ASC");

        int index = 0;

        if (cursor.moveToFirst()) {

            do {
                String[] complaints_data = new String[]{cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)};

                listOfComplaints.add(complaints_data); // Add the retrieved data to the array list
                index++; // Increment the index

            } while (cursor.moveToNext()); // While loop to go to the next row.
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close(); // Close cursor
        }

        cursor.close();

        return listOfComplaints;
    }

    public static class OpenHelper extends SQLiteOpenHelper { // A Helper Class that inherits from SQLiteOpenHelper.
        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DB_VERSION); // Inherit the features using super()
        }

        public void onCreate(SQLiteDatabase db) { // Creates the DB. Method overridden
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY, username TEXT, email TEXT, phone_number TEXT, problem TEXT)"); // Creates the database table
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DB_VERSION = newVersion; // Set the DB version to the newest version

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // DROP The specific table if it exists.
            onCreate(db); // Execute method
        }
    }
}