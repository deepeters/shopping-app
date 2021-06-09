package com.example.weshopapplication.ApplicationLayer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.weshopapplication.R;


public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        View submitComplaint = findViewById(R.id.submitComplaintBtn);
        submitComplaint.setOnClickListener(this);

        View checkComplaint = findViewById(R.id.checkComplaintsBtn);
        checkComplaint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            
            switch (v.getId()) {
                case R.id.submitComplaintBtn:
                    Intent submitComplaintIntent = new Intent(ContactUsActivity.this, SubmitComplaint.class);
                    startActivity(submitComplaintIntent);
                    break;

                case R.id.checkComplaintsBtn:
                    Intent checkComplaintsIntent = new Intent(ContactUsActivity.this, CheckComplaints.class);

                    startActivity(checkComplaintsIntent);
                    break;
            }
        }
        
        catch (ActivityNotFoundException exc) {

            Log.d(String.valueOf(R.string.error), exc.toString());
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
