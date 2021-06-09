package com.example.weshopapplication.DataLayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

// Author of Data Layer / Class: Sabin Constantin Lungu
// Matriculation Number: 40397517
// Purpose of Data Layer Class: The Payment Database Data Layer class is used to store the payment data of customers in a MYSQLite Database.
// Date of Last Modification: 08/03/2020
// Any Errors? None. 25/25 Tests Passed.

public class PaymentDatabase {
    private static final String DATABASE_NAME = "payments.db"; // The Payment Database to create.
    private static final String TABLE_NAME = "payments"; // The Database Table name.

    private static final String INSERT_DATA = "INSERT INTO " + TABLE_NAME
            + " (email_address, card_number, card_cvv, card_name, expiry_month, expiry_year) VALUES (?,?,?,?,?,?)"; // Insert Query (DML) that inserts data into the contacts DB
    private static int DATABASE_VERSION = 1;

    private static Context context; // The current context.
    private static SQLiteDatabase db; // The SQL database
    private SQLiteStatement sqlStatement; // The SQL statement

    public PaymentDatabase(Context context) { // Constructor for the database manipulator
        PaymentDatabase.context = context;
        PaymentDatabase.OpenHelper helper = new OpenHelper(PaymentDatabase.context);
        PaymentDatabase.db = helper.getWritableDatabase();

        this.sqlStatement = PaymentDatabase.db.compileStatement(INSERT_DATA); // Compiles the Insert query.
    }

    // Routine that inserts data into the table
    public long insert(String email_address, String card_number, String card_cvv, String card_name, String expiry_month, String expiry_year) { // Routine to insert data into the table
        this.sqlStatement.bindString(1, email_address); // Binds the email_address string value into the database table.
        this.sqlStatement.bindString(2, card_number);

        this.sqlStatement.bindString(3, card_cvv);
        this.sqlStatement.bindString(4, card_name);
        this.sqlStatement.bindString(5, expiry_month);
        this.sqlStatement.bindString(6, expiry_year);

        return this.sqlStatement.executeInsert(); // Return the execution of the statement
    }

    public void deleteData() { // Routine is only called when the database needs to be deleted.
        db.delete(TABLE_NAME, null, null); // Deletes the data in the database.
    }

    public static class OpenHelper extends SQLiteOpenHelper { // A Helper Class that inherits from SQLiteOpenHelper
        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION); // Inherit the features from the base SQLiteOpenHelper class. It inherits the context, database name and version.
        }

        public void onCreate(SQLiteDatabase db) { // Creates the DB. Method overridden
            // This line of code will create the table.
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY, email_address TEXT, card_number TEXT, card_cvv TEXT, card_name TEXT, expiry_month TEXT, expiry_year TEXT)");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            DATABASE_VERSION = newVersion; // Set the DB version to the newest version

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // DROP The specific table if it exists
            onCreate(db); // Execute method
        }
    }
}
