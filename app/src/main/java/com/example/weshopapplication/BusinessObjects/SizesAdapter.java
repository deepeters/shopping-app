package com.example.weshopapplication.BusinessObjects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import com.example.weshopapplication.ApplicationLayer.TechActivity;
import java.util.ArrayList;

// Author: Sabin Constantin Lungu
// Purpose of Activity: Allows the sizes to be stored in a drop-down menu.
// Date of Last Modified: 4/2/2020
// Any Bugs?: Currently none. Unit tested recently. 11/11 Tests completed

public class SizesAdapter extends ArrayAdapter<TechActivity.Size> {
    private Context context;
    private ArrayList<TechActivity.Size> listOfSizes;

    public SizesAdapter(Context context, ArrayList<TechActivity.Size> listOfSizes) {
        super(context, 0, listOfSizes);
        this.context = context;
        this.listOfSizes = listOfSizes;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listOfItems = convertView;

        if (listOfItems == null) { // If there are no list of items
            listOfItems = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

            TechActivity.Size size = listOfSizes.get(position);
        }

        return listOfItems; // Return the list of items.
    }
}
