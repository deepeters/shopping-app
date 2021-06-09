package com.example.weshopapplication.ApplicationLayer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.weshopapplication.R;


public class PaypalPaymentGateway extends AppCompatActivity {
    private WebView paypalView; // The PayPal Web View. Opens up a browser.
    private ProgressBar progressBar; // A spinning progress bar.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_payment_gateway);

        this.paypalView = findViewById(R.id.webView);
        this.progressBar = findViewById(R.id.progressBar);

        paypalView.getSettings().setJavaScriptEnabled(true);
        paypalView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        paypalView.setWebViewClient(new WebViewClient() {

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                paypalView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                if (url.contains("https://www.google.co.uk/")) { // If the URL contains google.co.uk
                    Toast.makeText(PaypalPaymentGateway.this, "Payment Cancelled", Toast.LENGTH_LONG).show();
                    finish(); // Finish activity
                } 
                
                else if (url.contains("https://www.facebook.com/")) {
                    Toast.makeText(PaypalPaymentGateway.this, "Payment Successful", Toast.LENGTH_LONG).show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                paypalView.setVisibility(View.VISIBLE);

                progressBar.setVisibility(View.GONE); // Progress Bar is gone now.
            }
        });

        paypalView.loadUrl("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=C8KCZ98RCKUQW");
    }
}
