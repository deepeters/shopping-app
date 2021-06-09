package com.example.weshopapplication.BusinessObjects;

// Author of Application: Sabin Constantin Lungu
// Matriculation Number: 40397517
// Purpose of Business Layer Class: To store the data for the Expiry Date Month that will be used in the Payment Activity Class.
// Date of Last Modification: 08/03/2020
// Any Bugs? None

public class Months { // Months Class
    private String month; // The month variable

    public Months(String month) { // Constructor for the months that will set the data for the instantiated object

        this.month = month;
    }

    public String getMonth() { // Returns the month
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() { // Method that returns the month data.
        return " " + this.month + " ";
    }
}