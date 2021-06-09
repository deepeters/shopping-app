package com.example.weshopapplication.BusinessObjects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import com.example.weshopapplication.ApplicationLayer.TechActivity;
import java.util.ArrayList;



public class QuantitiesArrayAdapter extends ArrayAdapter<TechActivity.Quantities> { // The quantities array adapter class inherits from the base array adapter class.

    private Context context;
    private ArrayList<TechActivity.Quantities> quantitiesList = null; // Array list of quantities

    public QuantitiesArrayAdapter(Context context, ArrayList<TechActivity.Quantities> quantitiesList) {
        super(context, 0, quantitiesList); // Super() used to inherit the features from the base class
        this.context = context;
        this.quantitiesList = quantitiesList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItems = convertView;

        if (listItems == null) { // If there is no list of items
            listItems = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false); // Inflate the drop-down menu with this type of instance Quantity

            TechActivity.Quantities quantities = quantitiesList.get(position);
        }

        return listItems; // Returns the quantities list of items.
    }
}
