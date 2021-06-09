package com.example.weshopapplication.ApplicationLayer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.weshopapplication.DataLayer.ContactUsDatabase;
import com.example.weshopapplication.R;

// Author of Application: Sabin Constantin Lungu
// Purpose of Class: Allows customers to Submit a complaint through the Contact Us Form if they are experiencing any issues with the app.
// Date of Last Modification: 22/02/2020
// Any Bugs? None.

public class SubmitComplaint extends AppCompatActivity implements View.OnClickListener {
    private ContactUsDatabase databaseManipulator;

    private EditText usernameField; // The username entry field
    private EditText emailAddressField; // The E-mail Address entry field
    private EditText phoneNumberField;

    private EditText problemField;
    private boolean isValidated = false; // Determines if the form fields are validated.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_complaint);

        View submitComplaintBtn = findViewById(R.id.btnSubmit);
        submitComplaintBtn.setOnClickListener(this);

        View backButton = findViewById(R.id.btnBack); // Identifies the back button view
        backButton.setOnClickListener(this);

        // Initialise components
        this.usernameField = findViewById(R.id.add_usernameField);
        this.emailAddressField = findViewById(R.id.add_complaintEmailField);

        this.phoneNumberField = findViewById(R.id.add_complaint_phoneNumberField);
        this.problemField = findViewById(R.id.add_complaint_fieldProblem);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnBack:
                this.finish(); // Finish the process
                break;

            case R.id.btnSubmit: // When the submit button is clicked

                String username = ((EditText) findViewById(R.id.add_usernameField)).getText().toString();
                String email = ((EditText) findViewById(R.id.add_complaintEmailField)).getText().toString();

                String phone_number = ((EditText) findViewById(R.id.add_complaint_phoneNumberField)).getText().toString();
                String problem = ((EditText) findViewById(R.id.add_complaint_fieldProblem)).getText().toString();

                this.databaseManipulator = new ContactUsDatabase(this);
                this.databaseManipulator.insert(username, email, phone_number, problem); // Insert the strings above into the DB table

                showSavedComplaintsDialog();
                break;
        }
    }

    protected void showSavedComplaintsDialog() {
        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(SubmitComplaint.this);

        builder.setMessage(R.string.add_next_dialog_message);
        builder.setCancelable(false);

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int which) {
                SubmitComplaint.this.finish();
            }
        });

        builder.setPositiveButton(R.string.add_next_dialog_confirm_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create(); // Create the new alert and show it.
        alert.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }
}
