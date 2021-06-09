package com.example.weshopapplication.BusinessObjects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import java.util.ArrayList;

// Author of Application / Class: Sabin Constantin Lungu
// Purpose of Application / Class: A helper class that allows objects of type Month to be stored in the drop-down menu in the product categories.
// Date of Last Modification: 08/03/2020
// Any Errors? None

public class MonthsArrayAdapter extends ArrayAdapter<Months> { // Array adapter class to allow the months to be stored as instances which will be added to an array list of months
    private Context context; // The context
    private ArrayList<Months> listOfMonths = null; // The array list of months

    public MonthsArrayAdapter(Context context, ArrayList<Months> listOfMonths) { // Constructor for months array adapter.
        super(context, 0, listOfMonths); // Inherit the features
        this.context = context;
        this.listOfMonths = listOfMonths;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) { // Gets the view.
        View listOfItems = convertView;

        if (listOfItems == null) { // If there is no items
            listOfItems = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false); // Inflate the list of items

            Months theMonths = listOfMonths.get(position); // Get the item position of the month
        }

        return listOfItems; // Return back the list of items
    }
}
