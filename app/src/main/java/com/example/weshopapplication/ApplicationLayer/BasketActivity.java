package com.example.weshopapplication.ApplicationLayer;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.weshopapplication.BusinessObjects.Products;
import com.example.weshopapplication.R;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Author of Application: Sabin Constantin Lungu
// Matriculation Number: 40397517
// Purpose of Application & Class: To store the products added to the basket in a List View.
// Date of Last Modification: 13/02/2020.
// Any Errors: N/A

public class BasketActivity extends AppCompatActivity implements View.OnClickListener { // The basket activity inherits from the AppCompat Activity and implements the methods for the view on click listener.
    private Button placeOrderBtn; // Variable for the place order button
    private HashMap<Integer, Products> listOfProductsToAddToBasket = new HashMap<>(); // A HashMap of products to add to the basket

    protected void onCreate(Bundle savedInstanceState) { // Android LifeCycle method. onCreate()
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basket); // Set the content view of the android app.

        this.placeOrderBtn = findViewById(R.id.placeOrderBtn); // Initialise component for the place order button
        this.placeOrderBtn.setOnClickListener(this); // Add an on click listener for the place order button.

        Intent intent = getIntent(); // Get the current intent.
        HashMap<Integer, Products> hashMap = (HashMap<Integer, Products>) intent.getSerializableExtra("map"); // Get the hash map from the tech activity
        ArrayList<String> products = new ArrayList<>(); // Creates a temporary array list of products.

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BasketActivity.this, android.R.layout.simple_list_item_1, products) { // Create a new array adapter for the basket activity.

            public View getView(int position, View convertView, @NotNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE); // Changes the colour of the list view to white.

                return view; // Return the view
            }
        };

        ListView view = findViewById(R.id.listViewBasket); // Find the list view component
        view.setAdapter(arrayAdapter); // Set its adapter

        for (Map.Entry<Integer, Products> entry : hashMap.entrySet()) { // For every entry in the Hash Map
            arrayAdapter.add(entry.toString()); // Add the entries to the basket.
        }
    }

    protected void onDestroy() { // Android LifeCycle method. onDestroy() that destroys the current activity.
        super.onDestroy();
    }

    protected void onStop() { // Android LifeCycle Method to stop() the current activity.
        super.onStop();
        finish();
    }

    public void onBackPressed() { // Android LifeCycle Method that is called when the back button is clicked.
        super.onBackPressed();
        moveTaskToBack(true);
    }

    protected void onResume() { // Android LifeCycle method onResume() that resumes to the current activity if another activity intervenes.
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        Context context = getApplicationContext();
        String[] temp = new String[]{context.getString(R.string.finish)}; // Creates a string array of string values.

        try {
            if (v.getId() == R.id.placeOrderBtn) { // if the button is clicked

                AlertDialog.Builder builder = new AlertDialog.Builder(BasketActivity.this)
                        .setTitle(R.string.checkout) // Sets the title of the alert dialogue.
                        .setMessage(temp[0]) // Set the message to the first index in the array

                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {
                                if (dialog != null) { // If there is no dialogue.

                                    dialog.dismiss(); // Close it.
                                    finish(); // Finish the activity,
                                }
                            }
                        }).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                    
                            public void onClick(DialogInterface dialog, int which) {

                                Intent checkOutActivity = new Intent(BasketActivity.this, PaymentActivity.class); // Create an intent to switch from the basket activity to the payment activity if the customer is happy with their basket.
                                checkOutActivity.putExtra("map", listOfProductsToAddToBasket); // Put the hash map in the list of products basket view.
                                startActivity(checkOutActivity); // Starts the activity
                            }
                        });

                builder.show(); // Show the dialogue.
                builder.setCancelable(true); // User can click outside the alert dialogue to close it.
            }
        } 
        
        catch (ActivityNotFoundException exc) { // Catch the error if there is no activity.
            Log.d(String.valueOf(R.string.error), exc.toString()); // Log the error to the console.
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
