package com.example.weshopapplication.BusinessObjects;

// Author of Business Layer Class: Sabin Constantin Lungu.
// Purpose of Business Layer Class: To store the size data.
// Matriculation Number: 40397517
// Date of Last Modification: 08/03/2020

public class Size { // Size Class
    private int index; // The Size Index for the drop-down menu.
    private String productSize; // The product size

    public Size(int index, String productSize) { // Size constructor
        this.index = index;
        this.productSize = productSize;
    }

    public int getIndex() { // Returns the required index.
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getProductSize() { // Method that returns the product size
        return this.productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    @Override
    public String toString() { // To string method to return the data
        return " " + this.productSize;
    }
}
