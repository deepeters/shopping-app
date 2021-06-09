package com.example.weshopapplication.BusinessObjects;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// Author of Application/Class: Sabin Constantin Lungu
// Purpose of Application/Class: This class uses the JavaMail API that automatically sends an e-mail to the user after they have purchased a specific product(s)
// Date of Last Modification: 19/02/2020
// Any Bugs? None

public class SendPaymentInvoiceAPI extends AsyncTask<Void, Void, Void> { // The class inherits from an Asynchronous Task Class. with 3 parameters initially Void
    private Context context; // The context.
    private Session session; // The current session.
    
    private String mail; // The mail instance.
    private String subject; // The subject variable.
    
    private String theMessage; // The message that will be sent will be stored in this variable.
    private ProgressDialog dialog; // The progress dialog

    public SendPaymentInvoiceAPI(Context context, String mail, String subject, String theMessage) { // Constructor for the send payment invoice that stores the context, email, subject and message
        this.context = context;
        this.mail = mail;
        this.subject = subject;
        this.theMessage = theMessage;
    }

    protected void onPreExecute() { // Method that is called on pre-execution.
        super.onPreExecute(); // Call the base method to pre execute.
        dialog = ProgressDialog.show(context, "Processing..", "Please wait..", false, false); // When the Confirm Payment button is clicked, this dialogue is shown.
    }

    protected void onPostExecute(Void aVoid) { // Method overridden after post-execution.
        super.onPostExecute(aVoid);

        dialog.dismiss(); // The dialogue is gone
        Toast.makeText(context, "Payment Invoice Sent Successfully", Toast.LENGTH_SHORT).show(); // Displays a toast message saying that the order is confirmed.
    }

    protected Void doInBackground(Void... params) { // A protected routine that performs the following tasks below in the background
        Properties properties = new Properties(); // Create a new properties instance

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() { // Gets the default instance of the current session

            protected PasswordAuthentication getPasswordAuthentication() { // Routine to get the password authentication
                return new PasswordAuthentication(MailCredentialsAPI.EMAIL_ADDRESS, MailCredentialsAPI.PASSWORD); // Returns a new e-mail address and password each time.
            }
        });

        try {
            MimeMessage message = new MimeMessage(session); // A new message instance.
            message.setFrom(new InternetAddress(MailCredentialsAPI.EMAIL_ADDRESS));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail)); // Creates the recipient to send the e-mail.

            message.setSubject(subject); // Sets the subject of the e-mail
            message.setText(theMessage); // Sets the text to be sent
            Transport.send(message); // Send the e-mail through the transport layer.
        } 
        
        catch (MessagingException exc) { // Catch the exception if there is no recipient.
            exc.printStackTrace(); // Print the stack trace.
        }

        return null; // Return nothing otherwise
    }
}
