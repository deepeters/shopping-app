package com.example.weshopapplication.ApplicationLayer;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.weshopapplication.DataLayer.ContactUsDatabase;
import com.example.weshopapplication.R;
import java.util.ArrayList;
import java.util.List;


public class CheckComplaints extends ListActivity { // Class Check Complaints inherits from the List Activity class
    public int idToModify; // The ID to modify.
    private TextView selection;
    private ContactUsDatabase manipulator;

    private List<String[]> listOfComplaints = new ArrayList<>(); // Creates a new list of complaints list.
    private List<String[]> listOfUsernames = null; // A list of Username.

    private String[] displayDataStrings; // A string array to display the strings in the list view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_complaints);

        manipulator = new ContactUsDatabase(this); // Creates a new instance of the contact us database
        listOfUsernames = manipulator.selectAllData(); // Selects all of the list of usernames.

        displayDataStrings = new String[listOfUsernames.size()]; // Creates a new array of strirngs.
        int index = 0; // The index
        String temp;

        for (String[] usernames : listOfUsernames) { // For each username in the list of usernames
            temp = usernames[1] + " - " + usernames[2] + " - " + usernames[3] + " - " + usernames[4];
            displayDataStrings[index] = temp; // Display the strings
            index++; // Add the username to the next index.
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CheckComplaints.this, android.R.layout.simple_list_item_1, displayDataStrings); // Create an array adapter to display the complaints in the list view.
        this.setListAdapter(adapter);
        selection = findViewById(R.id.check_selection);
    }

    public void onListItemClick(ListView parent, View view, int position, long id) {
        selection.setText(displayDataStrings[position]);
    }
}
