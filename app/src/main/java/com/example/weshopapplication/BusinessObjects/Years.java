package com.example.weshopapplication.BusinessObjects;



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
