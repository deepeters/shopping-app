package com.example.weshopapplication.ApplicationLayer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.weshopapplication.R;
import com.google.firebase.auth.FirebaseAuth;

// Author of Application: Sabin Constantin Lungu
// Purpose of Class: Main Activity implements homepage functionality of application.
// Last modified: 26 January 2020
// Any Bugs?: N/A

public class MainActivity extends AppCompatActivity {
    // Encapsulated variables
    private TextView text;

    private Button registerButton; // Variable Button to register
    private Button loginButton; // Variable to store the login button
    
    private Button contactUsBtn; // Variable to store the contact us button details
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise components
        this.text = findViewById(R.id.welcomeTxt);
        this.registerButton = findViewById(R.id.registerBtn);
        
        this.loginButton = findViewById(R.id.loginBtn);
        this.contactUsBtn = findViewById(R.id.contactUsBtn);

        this.auth = FirebaseAuth.getInstance(); // Get instance of fire base

        this.registerButton.setOnClickListener(new View.OnClickListener() { // Listener added to register button
            @Override
            public void onClick(View v) {
                
                try {
                    
                    if (v.getId() == R.id.registerBtn) { // If the register button is clicked
                        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(registerIntent); // Take user to the register page
                    }
                } 
                
                catch (ActivityNotFoundException act) { // Catch exception if the activity is not found
                    act.printStackTrace(); // Print the stack trace of the errors
                    Log.d("Cause : ", act.getMessage()); // Log the cause of the error
                }
            }
        });

        this.loginButton.setOnClickListener(new View.OnClickListener() { // Listener for login button
            @Override
            public void onClick(View v) {
                
                try {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent); // Invoke routine to start the activity.
                } 
                
                catch (ActivityNotFoundException exc) { // Catch the activity not found.
                    Log.d("Error : ", exc.getMessage());
                }
            }
        });

        this.contactUsBtn.setOnClickListener(new View.OnClickListener() { // An action listener for the contact us button
            @Override
            public void onClick(View v) {
                
                try {
                    Intent contactUsIntent = new Intent(MainActivity.this, ContactUsActivity.class);
                    startActivity(contactUsIntent);
                } 
                
                catch (ActivityNotFoundException exc) {
                    Log.d("Error", exc.toString());
                }
            }
        });
    }
    
    public boolean onCreateOptionsMenu(Menu menu) { // Overidden method that creates the menu

        // Inflate the activities
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homepagemenu, menu);

        // Inflate the logout button
        MenuInflater logoutInflater = getMenuInflater();
        logoutInflater.inflate(R.menu.logout_menu, menu);

        MenuItem logout = menu.findItem(R.id.logout_button);
        logout.setVisible(true); // Set the logout button to be initially true. Will be changed to false

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) { // Determines which item is selected from the menu
        try {
            if (item == null) { // If there is no item to choose
                return false; // Return false
            }

            // Determines which activity is chosen
            switch (item.getItemId()) {
                    
                case R.id.sportsAndOutdoorsCategory: // If the sports and outdoors category is chosen
                    Intent sportsIntent = new Intent(MainActivity.this, SportsAndOutdoorsActivity.class); // Take user to the sports and outdoors activity
                    startActivity(sportsIntent);

                    return true; // Return true.

                case R.id.techCategory:
                    Intent techIntent = new Intent(MainActivity.this, TechActivity.class);
                    startActivity(techIntent);

                    return true;
                    
                    
                case R.id.clothingCategory: // If the clothing category is chosen

                    Intent clothingIntent = new Intent(MainActivity.this, ClothingCategory.class);
                    startActivity(clothingIntent); // Go to the clothing intent

                    return true;

                case R.id.diyCategory:

                    Intent intent = new Intent(MainActivity.this, DIYActivity.class);
                    startActivity(intent);

                    return true; // Return true;

                case R.id.logout_button:

                    logout(); // Logs the user out
                    return true;

                default:
                    return super.onOptionsItemSelected(item); // Return the base item
            }
        } 
        
        catch (ActivityNotFoundException exc) { // Catch exception
            exc.printStackTrace();
        }

        return true; // Return true.
    }

    public void onBackPressed() { // Routine that determines if the back button is pressed
        moveTaskToBack(true); // Move back a task
    }

    private void logout() { // Logout the user
        auth.signOut(); // Sign the user out from firebase
        finish(); // finish the process
        startActivity(new Intent(MainActivity.this, LoginActivity.class)); // Take user to login activity
    }
}
