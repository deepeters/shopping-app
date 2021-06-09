package com.example.weshopapplication.BusinessObjects;

// Author of Application: Sabin Constantin Lungu
// Purpose of Business Layer Class: To store the data for the Years that will be used in the Payment Activity
// Date of Last Modification: 08/03/2020
// Any Bugs? None

public class Years { // Years class
    private String year; // Year variable

    public Years(String year) { // Constructor
        this.year = year;
    }

    public String getYear() { // Method to get the year
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() { // To string method will return the value of the year.
        return " " + year + " ";
    }
}
