package com.example.weshopapplication.BusinessObjects;



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