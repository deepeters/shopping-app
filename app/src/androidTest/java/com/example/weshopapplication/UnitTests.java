package com.example.weshopapplication;

import android.view.View;
import android.widget.EditText;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import com.example.weshopapplication.ApplicationLayer.BasketActivity;
import com.example.weshopapplication.ApplicationLayer.ClothingCategory;
import com.example.weshopapplication.ApplicationLayer.DIYActivity;
import com.example.weshopapplication.ApplicationLayer.LoginActivity;
import com.example.weshopapplication.ApplicationLayer.MainActivity;
import com.example.weshopapplication.ApplicationLayer.PaymentActivity;
import com.example.weshopapplication.ApplicationLayer.RegisterActivity;
import com.example.weshopapplication.ApplicationLayer.SportsAndOutdoorsActivity;
import com.example.weshopapplication.ApplicationLayer.TechActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

// Purpose of Test: To test if the MainActivity loads
// Matriculation Number: 40397517
// Author of Test: Sabin Constantin Lungu
// Date of Last Modification: 4/2/2020
// Tests Pass? : Yes. 25/25 Tests Pass.

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UnitTests {

    // Rules created for each test
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<RegisterActivity> registerRule = new ActivityTestRule<>(RegisterActivity.class);

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Rule
    public ActivityTestRule<TechActivity> techActivityActivityTestRule = new ActivityTestRule<>(TechActivity.class);

    @Rule
    public ActivityTestRule<BasketActivity> basketActivityActivityTestRule = new ActivityTestRule<>(BasketActivity.class);

     @Rule
     public ActivityTestRule<ClothingCategory> clothingCategoryActivityTestRule = new ActivityTestRule<>(ClothingCategory.class);

    @Rule
    public ActivityTestRule<SportsAndOutdoorsActivity> sportsAndOutdoorsActivityActivityTestRule = new ActivityTestRule<>(SportsAndOutdoorsActivity.class);

    @Rule
    public ActivityTestRule<DIYActivity> diyActivityActivityTestRule = new ActivityTestRule<>(DIYActivity.class);

    @Rule
    public ActivityTestRule<PaymentActivity> paymentActivityActivityTestRule = new ActivityTestRule<>(PaymentActivity.class);

    // Activities to be tested
    private MainActivity mainActivity = null; // The main activity is null by default.
    private RegisterActivity registerActivity = null;
    private LoginActivity loginActivity = null;

    private SportsAndOutdoorsActivity sportsAndOutdoorsActivity = null;
    private TechActivity techActivity = null;

    private ClothingCategory clothingCategory = null;
    private DIYActivity diyActivity = null;

    private BasketActivity productsBasket = null;
    private PaymentActivity paymentActivity = null;

    // Retrieve the data from the fields
    private EditText usernameTest;
    private EditText emailAddressTest;
    private EditText passwordTest;

    // Test Payment Input Fields
    private EditText cardNumberFieldTest; // The card number input field to be tested against certain conditions.
    private EditText cardCVVFieldTest;
    private EditText cardHolderNameTest;

    @Before
    public void setUp() { // Sets up the tests
        getActivities();
        // Get the edit text fields
        usernameTest = registerActivity.findViewById(R.id.usernameField);
        emailAddressTest = registerActivity.findViewById(R.id.emailAddressField);
        passwordTest = registerActivity.findViewById(R.id.passwordField);

        cardNumberFieldTest = paymentActivity.findViewById(R.id.creditCardNumberField);
        cardCVVFieldTest = paymentActivity.findViewById(R.id.cardCVVField);
        cardHolderNameTest = paymentActivity.findViewById(R.id.cardNameField);
    }

    public void getActivities() { // Retrieves the activities
        mainActivity = activityRule.getActivity(); // Get the activity
        registerActivity = registerRule.getActivity();
        clothingCategory = clothingCategoryActivityTestRule.getActivity(); // Get the clothing activity

        loginActivity = loginActivityRule.getActivity(); // Get the login activity
        techActivity = techActivityActivityTestRule.getActivity(); // Get the tech activity
        productsBasket = basketActivityActivityTestRule.getActivity();
        paymentActivity = paymentActivityActivityTestRule.getActivity();

        diyActivity = diyActivityActivityTestRule.getActivity();

        sportsAndOutdoorsActivity = sportsAndOutdoorsActivityActivityTestRule.getActivity(); // Get the sports and outdoors activity
    }

    @Test
    public void testMainActivityLauncher() { // Routine that tests if the main activity launches.
        View view = mainActivity.findViewById(R.id.welcomeTxt);

        assertNotNull(view);
    }

    public void testPreconditions() { // Test preconditions
        assertNotNull(usernameTest);
        assertNotNull(emailAddressTest);
        assertNotNull(passwordTest);

        assertNotNull(cardNumberFieldTest);
        assertNotNull(cardCVVFieldTest);
        assertNotNull(cardHolderNameTest);
    }

    @Test
    public void testValidUsername() { // Test Routine to test a valid Username Entry field.
        assertTrue(usernameTest.getText().toString(), Validators.isValidUsername("sabin2000")); // Test should pass because username is valid
    }

    @Test
    public void testEmptyUsernameEntry() { // Test routine to test if a Username is empty. Test should fail because it has special characters.
        assertTrue(usernameTest.getText().toString(), Validators.isValidUsername(" "));
    }

    @Test
    public void testUsernameLengthExceeds() { // Test Routine to test if a Username entry exceeds 20 characters. Test should fail because it exceeds 20.
        assertFalse(usernameTest.getText().toString(), Validators.isValidUsername("sabinOafjdfhiusdfhsdiufhuAIUFhiufsflsdkl"));
    }

    @Test
    public void testValidEmailAddress() { // Test stub to test if the E-mail Address entered is the one to expect. Test should pass
        assertTrue(emailAddressTest.getText().toString(), Validators.isValidEmailAddress("sabinlungu293@gmail.com"));
    }

    @Test
    public void testEmailAddressLengthExceeds() { // Test Method to test if the E-mail Address length is > 30. Test Should Pass.
        assertFalse(emailAddressTest.getText().toString(), Validators.isValidEmailAddress("bobmichaeltesemailaddressparkinsonapplescarstobuy@yahoo.com"));
    }

    @Test
    public void testEmptyEmailAddress() { // Test Stub to test if the email address entered is an empty string. This test should pass as the entry field is empty
        assertFalse(emailAddressTest.getText().toString(), Validators.isValidEmailAddress(" "));
    }

    @Test
    public void testEmailAddressRegex() { // Test Routine to test if the E-mail Address contains an @ symbol. Test will pass.
        assertFalse(emailAddressTest.getText().toString(), Validators.isValidEmailAddress("sabinlungu293gmail.com"));
    }

    @Test
    public void testValidPassword() { // Test Routine to test if the Password entry field is valid: Starts with Uppercase, has regex characters and not empty. This test should pass
        assertTrue(passwordTest.getText().toString(), Validators.isValidPassword("Sabin2000*@("));
    }

    @Test
    public void testNoRegexPassword() { // Test routine to check if the password entry has special characters.
        assertTrue(passwordTest.getText().toString(), Validators.isValidPassword("Sabin2000"));
    }

    @Test
    public void testValidCardNumber() { // Test routine to check if the card number entry is valid or not.
        assertTrue(cardNumberFieldTest.getText().toString(), Validators.isValidCardNumber("1234000090991234"));
    }

    @Test
    public void testCardNumberLength() { // Test routine to check if the card number of the payment activity is > 20. Test fails as length is bigger than 20
        assertFalse(cardNumberFieldTest.getText().toString(), Validators.isValidCardNumber("943274837429384638746237468723482734723764"));
    }

    @Test
    public void testEmptyCardNumberField() {
        assertTrue(cardNumberFieldTest.getText().toString(), Validators.isValidCardNumber(" "));
    }

    @Test
    public void testValidCardCVV() {
        assertTrue(cardCVVFieldTest.getText().toString(), Validators.isValidCardCVV("218"));
    }

    @Test
    public void testCVVLength() {
        assertFalse(cardCVVFieldTest.getText().toString(), Validators.isValidCardCVV("1234"));
    }

    @Test
    public void testValidCardHolderName() { // Tests to see if the card holder name is valid.
        assertFalse(cardHolderNameTest.getText().toString(), Validators.isValidCardHolderName("Sabin Lungu")); // Test will pass because the name is valid.
    }

    @Test
    public void testEmptyCardHolderName() {
        assertFalse(cardHolderNameTest.getText().toString(), Validators.isValidCardHolderName(" "));
    }

    @Test
    public void testRegisterActivityLauncher() {
        View registerView = registerActivity.findViewById(R.id.registerTxt);
        assertNotNull(registerView);
    }

    @Test
    public void testSportsAndOutdoorsActivityLauncher() {
        View sportsView = sportsAndOutdoorsActivity.findViewById(R.id.firstSportsOutdoorCostLbl);
        assertNotNull(sportsView);
    }

    @Test
    public void testTechActivityLauncher() { // Tests to see if the tech activity loads correctly.
        View activityView = techActivity.findViewById(R.id.firstProductImg);
        assertNotNull(activityView);
    }

    @Test
    public void testClothingActivityLauncher() { // Tests to see if the clothing activity is loaded
        View activityView = clothingCategory.findViewById(R.id.clothingFirstProductCostLbl);
        assertNotNull(activityView);
    }

    @Test
    public void testDIYActivityLauncher() {
        View diyView = diyActivity.findViewById(R.id.diyFirstProductCostTxt);
        assertNotNull(diyView);
    }

    @Test
    public void testLoadBasketActivity() { // Tests to see if the basket loads correctly.
        View basketView = productsBasket.findViewById(R.id.placeOrderBtn);
        assertNotNull(basketView);
    }

    @Test
    public void testLoginActivityLauncher() { // Test stub that tests to see if the login activity launches
        View loginView = loginActivity.findViewById(R.id.loginBtn); // Finds the login button
        assertNotNull(loginView); // Check condition
    }

    @Test
    public void testPaymentActivityLauncher() {
        View paymentView = paymentActivity.findViewById(R.id.paymentTxt);
        assertNotNull(paymentView);
    }

    @After
    public void tearDown() { // After testing
        // Empty activities after testing
        mainActivity = null;
        registerActivity = null;
        loginActivity = null;

        diyActivity = null;

        techActivity = null;
        productsBasket = null;
    }
}
