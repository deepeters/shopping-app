package com.example.weshopapplication.ApplicationLayer;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.example.weshopapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.regex.Pattern;

// Author: Sabin Constantin Lungu.
// Matriculation Number: 40397517.
// Date of Last Modification: 08/03/2020
// Purpose of Activity: To allow users to register an account and write their registration data to a Firebase database.
// Any errors? Pending testing..

public class RegisterActivity extends AppCompatActivity { // Register class
    private static final String CHANNEL_ID = "register_channel"; // The channel to send the notification on
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); // Get an instance of Firebase
    private EditText usernameField;

    private TextView registerText; // The register text
    private EditText passwordField; // The password entry field.
    private RadioButton termsAndConditions;

    private Button registerButton; // Register button
    private EditText emailAddressField;

    private boolean hasDigits; // True or false if the inputs have numbers
    private boolean startsWithUppercase; // True or false if the inputs start with an upper case.

    private boolean hasCharacters; // True or false if the input has characters
    private boolean hasRegex; // Determines if the field contains special characters (regex)

    private boolean isEmpty; // Variable that determines if any of the
    private boolean isValid;
    private boolean isRegistered;
    
    private NotificationManagerCompat notificationManager; // Notification manager variable
    private FirebaseAuth authentication = FirebaseAuth.getInstance();
    private Pattern regexPatterns = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]"); // Regex patterns that will be checked.

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Android Lifecycle method 1
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialise components
        this.usernameField = findViewById(R.id.usernameField);
        this.emailAddressField = findViewById(R.id.emailAddressField);

        this.registerText = findViewById(R.id.registerTxt);
        this.passwordField = findViewById(R.id.passwordField);

        this.termsAndConditions = findViewById(R.id.termsAndConditionsBox);
        this.registerButton = findViewById(R.id.registerBtn);
        notificationManager = NotificationManagerCompat.from(this); // Register the notification manager


        this.registerButton.setOnClickListener(new View.OnClickListener() { // Add listener to the button
            @Override
            public void onClick(View buttonView) {
                validateUsername(); // Call method to validate username
                validateEmailAddress(); // Validate the e-mail address

                validatePassword(); // Call method to validate the password
                validateTermsAndConditions();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) { // Routine that creates the menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.register_menu, menu); // Inflate the menu

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) { // Routine that determines which menu item is chosen

        try {
            switch (item.getItemId()) {

                case R.id.sportsAndOutdoorsCategory: // If the sports and outdoors category is clicked on
                    Intent sportsActivity = new Intent(RegisterActivity.this, SportsAndOutdoorsActivity.class); // Create intent for sports activity
                    startActivity(sportsActivity);

                    return true;

                case R.id.techCategory: // If the tech category is chosen
                    Intent techActivity = new Intent(RegisterActivity.this, TechActivity.class);
                    startActivity(techActivity); // Take the user to the activity

                    return true;

                case R.id.clothingCategory:
                    Intent clothingCategory = new Intent(RegisterActivity.this, ClothingCategory.class);
                    startActivity(clothingCategory);

                    return true;

                case R.id.diyCategory: // Case when the DIY category is chosen
                    Intent diyCategory = new Intent(RegisterActivity.this, DIYActivity.class); // Create a new intent for the DIY category
                    startActivity(diyCategory); // Start the activity

                    return true;

                default:
                    return super.onOptionsItemSelected(item); // Return the base item selected
            }
        } 
        
        catch (ActivityNotFoundException act) {
            Log.d(String.valueOf(R.string.error), act.toString()); // Get the cause of the error.
        }

        return true;
    }

    public void onStart() { // Android Lifecycle method 2.
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true); // Move back a activity
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }

    private boolean validateUsername() { // Routine that validates the username entered by the user against specific criteria
        String usernameInputField = usernameField.getText().toString().trim();

        Context context = getApplicationContext();
        String[] usernameValidationResource = new String[]{context.getString(R.string.empty), context.getString(R.string.usernameLength), context.getString(R.string.usernameReEnter), context.getString(R.string.usernameRegex),
                context.getString(R.string.usernameReEnter), context.getString(R.string.usernameRegexWarning)};

        if (usernameInputField.isEmpty()) { // If the input field is left empty

            AlertDialog.Builder emptyDialog = new AlertDialog.Builder(RegisterActivity.this).setTitle(R.string.usernameError)
                    .setMessage(R.string.usernameErrorMsg).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (dialog != null) {
                                dialog.dismiss(); // Dismiss the dialogue
                            }
                        }
                    });

            emptyDialog.show();
            
            usernameField.setError(usernameValidationResource[0]); // Set the error.
            usernameField.setText(""); // Flush the empty field out
            
            isEmpty = true; // The field is empty
            isValid = false; // Is valid is false.
        }

        for (int i = 0; i < usernameInputField.length(); i++) { // Loop over the username

            if (!Character.isDigit(usernameInputField.charAt(i)) && usernameInputField.length() > 20) { // If the username has no digits or the length is bigger than 20

                usernameField.setError(usernameValidationResource[1]);

                AlertDialog.Builder usernameError = new AlertDialog.Builder(RegisterActivity.this).setMessage(usernameValidationResource[2])
                        .setTitle(usernameValidationResource[0]).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        });

                usernameError.show(); // Show the dialogue
                usernameField.setText(""); // Flush out the data

                hasDigits = false; // Has digits is false.
                isValid = false;
                break;
            }

            if (Character.isDigit(usernameInputField.charAt(i)) && usernameInputField.length() > 20) { // If the username field contains characters and the length is not 20
                isValid = true;
            }

            if (regexPatterns.matcher(usernameInputField).find()) { // If the username has a regex character.
                usernameField.setError(usernameValidationResource[3]);

                AlertDialog.Builder regexWarning = new AlertDialog.Builder(RegisterActivity.this).setMessage(usernameValidationResource[4])
                        .setTitle(usernameValidationResource[5]).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface dialog, int which) {
                                
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        });

                regexWarning.show();
                usernameField.setText("");
                isValid = false;
            } 
            
            else {
                isValid = true;
                usernameField.setError(null); // Set username error to null if no errors occur
            }

        }

        return true;
    }

    private boolean validateEmailAddress() { // Routine that validates the e-mail address.
        Context context = getApplicationContext();
        String[] emailAddressResources = new String[]{context.getString(R.string.emailError), context.getString(R.string.reEnterEmail), context.getString(R.string.emailEmpty), context.getString(R.string.emailLength)
                , context.getString(R.string.emailAtSymbol), context.getString(R.string.emailRegexWarning)};

        String emailAddressInputField = emailAddressField.getText().toString().trim(); // Get the input for the emailAddress

        if (emailAddressInputField.isEmpty()) { // IF the E-mail Address field is left empty

            AlertDialog.Builder emailError = new AlertDialog.Builder(RegisterActivity.this).setTitle(emailAddressResources[0]).setMessage(emailAddressResources[1])
                    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialog, int which) {
                            if (dialog != null) {

                                dialog.dismiss(); // Dismiss the dialogue if the dialog is not shown
                            }
                        }
                    });

            emailError.show();
            emailError.setCancelable(true); // User can click outside the window to cancel the error dialogue

            emailAddressField.setError(emailAddressResources[2]);
            isEmpty = true;
            isValid = false;

            return true;
        }

        if (emailAddressInputField.length() > 30) { // If the e-mail length is bigger than 25 characters
            emailAddressField.setError(emailAddressResources[3]); // Display error

            isValid = false; // Not valid
            return false;
        }

        if (!regexPatterns.matcher(emailAddressInputField).find()) { // If there is no regex characters matched including the @ symbol that is needed

            emailAddressField.setError(emailAddressResources[4]);
            AlertDialog.Builder emailRegexWarning = new AlertDialog.Builder(RegisterActivity.this).setTitle(emailAddressResources[5]).setMessage(emailAddressResources[4])
                    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (dialog != null) { // If the dialog is not empty

                                dialog.dismiss();
                                dialog.cancel();
                            }
                        }
                    });

            emailRegexWarning.show();
            emailAddressField.setText(""); // Set the field to empty
            
            isValid = false;
            return false;
        } 
        
        else {
            isValid = true; // Otherwise the
            return true;
        }
    }

    private boolean validatePassword() { // Routine to validate the password
        Context context = getApplicationContext();

        String[] passwordResources = new String[]{context.getString(R.string.passwordWarning), context.getString(R.string.passwordReEnter), context.getString(R.string.passwordRegexEmpty), context.getString(R.string.passwordError),
                context.getString(R.string.passwordUppercase)};
        String passwordEntryField = passwordField.getText().toString().trim(); // Get the password input and trim it

        if (passwordEntryField.isEmpty() && !regexPatterns.matcher(passwordEntryField).matches()) { // If the password is empty and there are no regex characters found

            AlertDialog.Builder passwordWarning = new AlertDialog.Builder(RegisterActivity.this).setTitle(passwordResources[0])
                    .setMessage(passwordResources[1]).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialog, int which) {
                            if (dialog != null) { // If the dialog is empty
                                dialog.dismiss(); // Dismiss it
                            }
                        }
                    });

            passwordWarning.show();
            passwordField.setText("");
            passwordField.setError(passwordResources[2]);
            
            isEmpty = true; // Is empty is true
            hasRegex = false;

            isValid = false; // Not valid
            return false; // Return false
        }

        for (int i = 0; i < passwordEntryField.length(); i++) { // Loop over the password entry

            if (!Character.isUpperCase(passwordEntryField.charAt(0))) { // If the password does not start with an upper case character.

                AlertDialog.Builder pwUpperCase = new AlertDialog.Builder(RegisterActivity.this).setTitle(passwordResources[3])

                        .setMessage(passwordResources[1]).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }
                        });

                pwUpperCase.show();
                passwordField.setText(""); // Set the field empty

                passwordField.setError(passwordResources[4]);
                startsWithUppercase = false;

                isValid = false;
                break;

            } 
        
            else {
                isValid = true;
            }
        }

        return true;
    }

    private boolean validateTermsAndConditions() { // Validates the terms and conditions box
        Context context = getApplicationContext();

        String[] termsAndConditionsResources = new String[]{context.getString(R.string.termsAndConditionsError), context.getString(R.string.tickTermsAndConditions),
                context.getString(R.string.termsAndConditionsMust)};

        if (!termsAndConditions.isChecked()) { // If the terms and conditions box is not checked

            AlertDialog.Builder boxError = new AlertDialog.Builder(RegisterActivity.this).setTitle(termsAndConditionsResources[0])
                    .setMessage(termsAndConditionsResources[1])
                    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialog, int which) {
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    });

            boxError.show(); // Show the error
            termsAndConditions.setError(termsAndConditionsResources[2]);
            isValid = false;
        }

        if (termsAndConditions.isChecked() && isValid) { // If the terms and conditions box is checked and the validation is all valid
            sendNotification(); // CALL METHOD TO SEND NOTIFICATION
            writeToDatabase(); // Write registration data to database

            writeToFirestore(); // Writes to firestore database
            showSpinningDialogue();
            
            transitionToLogin(); // Take user to login page
            isRegistered = true;
        } 
        
        else {

            isRegistered = false;
            termsAndConditions.setError(null); // Otherwise set no error

            return false; // Otherwise return false
        }

        return true; // Fallback onto previous statement and return true
    }

    private void showSpinningDialogue() { // Routine that shows the spinning dialogue when register button is clicked
        // Create the progress dialogue
        long sleepSeconds = 4000;
        final ProgressDialog dialog = new ProgressDialog(RegisterActivity.this); // The progress dialogue that will be shown.
        Context context = getApplicationContext();

        String[] temp = new String[]{context.getString(R.string.creatingAccount), context.getString(R.string.wait)};
        dialog.setTitle(temp[0]);

        dialog.setMessage(temp[1]);
        dialog.setCancelable(false);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        new Thread(new Runnable() { // Create a new thread
            @Override
            public void run() {

                try {
                    Thread.sleep(sleepSeconds); // Sleep for 4 seconds.
                } 
                
                catch (InterruptedException exc) {
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }

                dialog.dismiss(); // Dismiss the dialogue
            }
        }).start();

        dialog.show(); // Show the progress bar
    }

    private void sendNotification() { // Routine to send notification after registration
        int channelID = 12345; // The channel ID to send the notification on.
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // create channel in new versions of android
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
            notificationChannel.enableLights(true); // Enable the lights
            notificationChannel.setLightColor(Color.RED); // Sets the light colour

            notificationChannel.enableVibration(true);

            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // Code below shows the notification
        Intent intent = new Intent(this, RegisterActivity.class); // Get the intent
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // 0 is request code
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT); // Get the pending intent activity

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // Creates a uniform resource identifier for the sound.
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID) // Creates a new channel.
                        .setSmallIcon(R.drawable.ic_message_black_24dp)

                        .setContentTitle(getString(R.string.app_name))
                        .setContentText("Register Success") // Sets the content of the notification.
                        .setTicker("Success")
                        .setAutoCancel(true) // Auto cancels itself.
                        //.setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        notificationManager.notify(0, notificationBuilder.build()); // Shows the notification
    }

    private void writeToDatabase() { // Writes to database.

        // Get the user inputs
        String emailEntry = emailAddressField.getText().toString();
        String passwordEntry = passwordField.getText().toString();

        authentication.createUserWithEmailAndPassword(emailEntry, passwordEntry).addOnCompleteListener(new OnCompleteListener<AuthResult>() { // Create user account with e-mail and password.
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) { // If the register task is successful

                    Toast.makeText(RegisterActivity.this, "Data written to DB", Toast.LENGTH_LONG).show(); // Debug code.
                } 
                
                else {
                    Toast.makeText(RegisterActivity.this, "Could not write to DB", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void writeToFirestore() { // Routine to write to Fire Store database.
        Context context = getApplicationContext();
        String[] registrationResources = new String[]{context.getString(R.string.basket_username), context.getString(R.string.basket_email_address), context.getString(R.string.basket_password),
                context.getString(R.string.collection_name)};

        String usernameEntry = usernameField.getText().toString(); // Get the username entry
        String emailEntry = emailAddressField.getText().toString();
        String passwordEntry = passwordField.getText().toString();

        HashMap<String, Object> user_data = new HashMap<>(); // HashMap for the user data

        user_data.put(registrationResources[0], usernameEntry); // Add the Username Entry to the hash map
        user_data.put(registrationResources[1], emailEntry);
        user_data.put(registrationResources[2], passwordEntry);

        db.collection(registrationResources[3]).add(user_data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override

            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(RegisterActivity.this, "Added Data To Fire Store", Toast.LENGTH_LONG).show(); // Debug toast to make sure that the data is added to fire store.
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(String.valueOf(R.string.error), e.toString());
            }
        });
    }

    private void transitionToLogin() { // Take the user to the login page after registration

        try {
            // Take user to login
            Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(loginIntent); // Start the login activity
        } 
        
        catch (ActivityNotFoundException act) { // Catch the error if the activity is not found.
            Log.d(String.valueOf(R.string.error), act.toString()); // Log the error.
        }
    }
}
