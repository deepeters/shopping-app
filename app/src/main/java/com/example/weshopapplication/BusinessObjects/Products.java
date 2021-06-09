package com.example.weshopapplication.BusinessObjects;

import java.io.Serializable;

// Author of Application: Sabin Constantin Lungu 40397517
// Purpose of Application: To store data regarding the products when they are added to basket, the data will get displayed in a list view
// Date of Last Modification: 07/02/2020
// Any Errors? No

public class Products implements Serializable { // Products Class
    private int productID; // The Product ID
    private String productName; // The Product Name
    private String colour; // Product Colour.

    private int quantity; // The Product quantity .
    private String cost; // The Product cost.
    private String size; // The Product size.

    public Products(int productID, String productName, String colour, int quantity, String cost, String size) { // Constructor for products class that stores the data necessary to represent a product
        this.productID = productID;
        this.productName = productName;
        this.colour = colour;
        
        this.quantity = quantity;
        this.cost = cost; // Set the cost.
        this.size = size;
    }

    public int getProductID() { // Returns the product ID
        return productID;
    }

    public void setProductID(int productID) { // Set the product id
        this.productID = productID;
    }

    public String getProductName() { // Returns the product name.
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColour() { // Returns the product colour if called.
        return this.colour; // Returns the colour
    }

    public void setColour(String colour) { // Set the colour of the product
        this.colour = colour;
    }

    public int getQuantity() { // Returns the product quantity
        return this.quantity;
    }

    public void setQuantity(int quantity) { // Routine that will set the quantity of a product
        this.quantity = quantity;
    }

    public String getCost() { // Returns the product cost
        return this.cost;
    }

    public void setCost(String cost) { // Sets the cost of a product.
        this.cost = cost;
    }

    public String getSize() { // Returns the product size
        return this.size;
    }

    public void setSize(String size) { // Routine that sets the size of a product.
        this.size = size;
    }

    @Override
    public String toString() { // To string method returns all of the data from the instance
        return "Product Name : " + this.productName + "\n " + "Product Colour " + colour + "\n " + "Product Quantity : " + this.quantity + "\n " + this.cost
                + "\n Product Size : " + this.size;
    }
}
