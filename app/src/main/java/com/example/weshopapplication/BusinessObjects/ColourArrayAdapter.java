package com.example.weshopapplication.BusinessObjects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import com.example.weshopapplication.ApplicationLayer.TechActivity;
import java.util.ArrayList;

// Author of Application / Class: Sabin Constantin Lungu
// Purpose of Application / Class: A helper class that allows objects of type colour to be stored in the drop-down menu in the product categories.
// Date of Last Modification: 08/03/2020
// Any Errors? None

public class ColourArrayAdapter extends ArrayAdapter<TechActivity.Colours> { // The colours array adapter class inherits from the array adapter base class
    private Context context;
    private ArrayList<TechActivity.Colours> listOfColours = null; // The array list of colours to be stored.

    public ColourArrayAdapter(Context context, ArrayList<TechActivity.Colours> listOfColours) { // Constructor for  the colour array adapter.
        super(context, 0, listOfColours); // Inherit the context and list of colours
        this.context = context;
        this.listOfColours = listOfColours;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) { // Routine that gets the list view at a specific position
        View listOfItems = convertView;

        if (listOfItems == null) { // If there is no list of items
            listOfItems = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false); // Inflate a drop-down menu with the colours

            TechActivity.Colours colours = listOfColours.get(position);
        }

        return listOfItems; // Returns the list of items.
    }
}
