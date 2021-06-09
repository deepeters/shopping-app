package com.example.weshopapplication.ApplicationLayer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.weshopapplication.BusinessObjects.ColourArrayAdapter;
import com.example.weshopapplication.BusinessObjects.Products;
import com.example.weshopapplication.BusinessObjects.QuantitiesArrayAdapter;
import com.example.weshopapplication.BusinessObjects.Size;
import com.example.weshopapplication.BusinessObjects.SizeArrayAdapter;
import com.example.weshopapplication.R;
import java.util.ArrayList;
import java.util.HashMap;

// Author of Application & Class: Sabin Constantin Lungu
// Purpose of Class: Stores the code needed to implement the Sports and Outdoors Activity 1.
// Date of Last Modification: 13/02/2020
// Any Errors? Currently None.

public class SportsAndOutdoorsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int current_product_id = 1;
    private ImageView cartIcon;

    private TextView firstSportsOutdoorTxt;
    private ImageView firstSportsOutdoorImg;

    private TextView firstSportsOutdoorCostTxt;
    private TextView firstSportsOutdoorColourLbl;
    private Spinner firstSportsOutdoorsColourMenu;

    private TextView firstSportsOutdoorQuantityLbl;
    private Spinner firstSportsOutdoorQuantityMenu;

    private TextView firstSportsOutdoorsSizeLbl;
    private Spinner firstSportsOutdoorsSizeMenu;
    private Button firstSportsAddToBasketBtn;

    private TextView secondSportsOutdoorTxt;
    private ImageView secondSportsOutdoorImg;

    private TextView secondSportsOutdoorCostLbl;
    private TextView secondSportsOutdoorColourLbl;
    private Spinner secondSportsOutdoorsColourMenu;

    private TextView secondSportsOutdoorQuantityLbl;
    private Spinner secondSportsOutdoorQuantityMenu;

    private TextView secondSportsOutdoorSizeLbl;
    private Spinner secondSportsOutdoorSizeMenu;

    private Button secondSportsAddToBasketBtn;
    private Button nextPageBtn; // Button to take the user to the next page 

    // The costs of the products
    private double[] productOneCosts = {0.00, 90.00, 180.00, 360.00, 720.00, 1440.00};
    private double[] productTwoCosts = {0.00, 50.00, 100.00, 150.00, 200.00, 250.00};
    private long sleepSeconds = 1900;

    private ArrayList<TechActivity.Colours> listOfColoursOne; // An array list of colours
    private ArrayList<TechActivity.Quantities> listOfQuantitiesOne; // An array list of quantities
    private ArrayList<Size> listOfSizesOne;

    private ArrayList<TechActivity.Colours> listOfColoursTwo;
    private ArrayList<TechActivity.Quantities> listOfQuantitiesTwo;
    private ArrayList<Size> listOfSizesTwo;

    private ColourArrayAdapter coloursAdapter; // A colours adapter is needed to store objects in a drop-down menu (spinner)
    private QuantitiesArrayAdapter quantitiesAdapter;

    private QuantitiesArrayAdapter secondQuantitiesAdapter;
    private SizeArrayAdapter sizeArrayAdapter;

    private boolean coloursAdded; // Flag to determine if the colours have been added to the drop-down list
    private boolean quantitiesAdded;

    private boolean sizesAdded; // Determines if the sizes have been added to the array list
    private boolean addedToBasket; // True or false to determine if the products have been added to the basket after adding to the hash map

    private HashMap<Integer, Products> listOfProductsToAddToBasket = new HashMap<>(); // A HashMap of products to add to the basket with a corresponding ID that each product will have.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_and_outdoors);

        // INITIALISE COMPONENTS
        this.firstSportsOutdoorTxt = findViewById(R.id.firstSportsOutdoorTxt);
        this.firstSportsOutdoorImg = findViewById(R.id.firstSportsOutdoorImg);
        this.firstSportsOutdoorCostTxt = findViewById(R.id.firstSportsOutdoorCostLbl);
        this.firstSportsOutdoorColourLbl = findViewById(R.id.firstSportsColourLbl);

        this.firstSportsOutdoorsColourMenu = findViewById(R.id.firstSportsOutdoorColourMenu);
        this.firstSportsOutdoorQuantityLbl = findViewById(R.id.firstSportsOutdoorQuantityLbl);
        this.firstSportsOutdoorQuantityMenu = findViewById(R.id.firstSportsOutdoorQuantityMenu);

        this.firstSportsOutdoorsSizeLbl = findViewById(R.id.firstSportsOutdoorSizeLbl);
        this.firstSportsOutdoorsSizeMenu = findViewById(R.id.firstSportsOutdoorSizeMenu);
        this.firstSportsAddToBasketBtn = findViewById(R.id.firstAddToBasketBtn); // Button for the first product to add to the basket.

        this.secondSportsOutdoorTxt = findViewById(R.id.secondSportsOutdoorsProductTxt);
        this.secondSportsOutdoorImg = findViewById(R.id.secondSportsOutdoorsImg);

        this.secondSportsOutdoorCostLbl = findViewById(R.id.secondSportsOutdoorProductCostLbl);
        this.secondSportsOutdoorColourLbl = findViewById(R.id.secondSportsOutdoorsColourLbl);
        this.secondSportsOutdoorsColourMenu = findViewById(R.id.secondSportsOutdoorsColourMenu);

        this.secondSportsOutdoorQuantityLbl = findViewById(R.id.secondsSportsOutdoorQuantityLbl);
        this.secondSportsOutdoorQuantityMenu = findViewById(R.id.secondSportsOutdoorsQuantityMenu);

        this.secondSportsOutdoorSizeLbl = findViewById(R.id.secondSportsOutdoorsSizeLbl);
        this.secondSportsOutdoorSizeMenu = findViewById(R.id.secondSportsOutdoorsSizeMenu);

        this.secondSportsAddToBasketBtn = findViewById(R.id.secondAddToBasketBtn);
        this.nextPageBtn = findViewById(R.id.sportsNextPage); // Button for taking the user to the next page.

        // Create the array lists
        this.listOfColoursOne = new ArrayList<>();
        this.listOfQuantitiesOne = new ArrayList<>();
        this.listOfSizesOne = new ArrayList<>();

        this.listOfColoursTwo = new ArrayList<>();
        this.listOfQuantitiesTwo = new ArrayList<>();
        this.listOfSizesTwo = new ArrayList<>();

        // Method calls to add to the specific array lists
        addToColoursList();
        addToColoursListTwo(); // Method call to add to the colours list two.

        addToQuantitiesListOne();
        addToQuantitiesListTwo();
        addToSizesList();

        // Set up the colours adapter
        this.coloursAdapter = new ColourArrayAdapter(SportsAndOutdoorsActivity.this, listOfColoursOne);
        coloursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        firstSportsOutdoorsColourMenu.setAdapter(coloursAdapter);
        firstSportsOutdoorsColourMenu.setOnItemSelectedListener(this);

        // Create array adapter for the quantities for product 1
        this.quantitiesAdapter = new QuantitiesArrayAdapter(SportsAndOutdoorsActivity.this, listOfQuantitiesOne);
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        firstSportsOutdoorQuantityMenu.setAdapter(quantitiesAdapter);
        firstSportsOutdoorQuantityMenu.setOnItemSelectedListener(this);

        // Create array adapter for the sizes
        this.sizeArrayAdapter = new SizeArrayAdapter(SportsAndOutdoorsActivity.this, listOfSizesOne);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        firstSportsOutdoorsSizeMenu.setAdapter(sizeArrayAdapter);
        firstSportsOutdoorsSizeMenu.setOnItemSelectedListener(this);

        this.coloursAdapter = new ColourArrayAdapter(SportsAndOutdoorsActivity.this, listOfColoursTwo);
        coloursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        secondSportsOutdoorsColourMenu.setAdapter(coloursAdapter);
        secondSportsOutdoorsColourMenu.setOnItemSelectedListener(this);

        // Create the Array Adapter for the quantities for the second product
        this.secondQuantitiesAdapter = new QuantitiesArrayAdapter(SportsAndOutdoorsActivity.this, listOfQuantitiesTwo);
        secondQuantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        secondSportsOutdoorQuantityMenu.setAdapter(secondQuantitiesAdapter);
        secondSportsOutdoorQuantityMenu.setOnItemSelectedListener(this);

        // Create the sizes array adapter for the second product
        this.sizeArrayAdapter = new SizeArrayAdapter(SportsAndOutdoorsActivity.this, listOfSizesTwo);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        secondSportsOutdoorSizeMenu.setAdapter(sizeArrayAdapter);
        secondSportsOutdoorSizeMenu.setOnItemSelectedListener(this);

        this.nextPageBtn = findViewById(R.id.sportsNextPage); // The next page button

        this.nextPageBtn.setOnClickListener(new View.OnClickListener() { // Add an action listener to the next page button to take the user to the next page
            @Override
            public void onClick(View v) {
                try {

                    Intent nextSportsActivity = new Intent(SportsAndOutdoorsActivity.this, SportsAndOutdoorsActivityTwo.class);
                    startActivity(nextSportsActivity);
                } 
                
                catch (ActivityNotFoundException exc) { // Catch the error if the activity has not been found
                    Log.d(String.valueOf(R.string.error), exc.toString()); // Log the error to the console
                }
            }
        });

        firstSportsAddToBasketBtn.setOnClickListener(new View.OnClickListener() { // Add action listener to the first button
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.firstAddToBasketBtn) {
                    if (firstSportsOutdoorsColourMenu.getSelectedItemPosition() == 0 || firstSportsOutdoorsSizeMenu.getSelectedItemPosition() == 0 || firstSportsOutdoorsSizeMenu.getSelectedItemId() == 0) {

                        AlertDialog.Builder error = new AlertDialog.Builder(SportsAndOutdoorsActivity.this) // Create an alert dialogue for the user to see.
                                .setTitle(R.string.error)
                                .setMessage(R.string.errorMsg)
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {

                                    @Override

                                    public void onClick(DialogInterface dialog, int which) {
                                        if (dialog != null) { // If there is a dialog
                                            dialog.dismiss(); // Dismiss it
                                        }
                                    }
                                });

                        error.show();
                        error.setCancelable(true);
                    }

                    else {

                        addToBasketOne(); // Otherwise add the product to the basket one
                    }
                }
            }
        });

        secondSportsAddToBasketBtn.setOnClickListener(new View.OnClickListener() { // Adds an action listener to the second add to basket button
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                String[] tempResources = new String[]{context.getString(R.string.chooseOption)};

                if (v.getId() == R.id.secondAddToBasketBtn) { // If the second add to basket button is clicked

                    if (secondSportsOutdoorsColourMenu.getSelectedItemPosition() == 0 || secondSportsOutdoorQuantityMenu.getSelectedItemPosition() == 0 || secondSportsOutdoorSizeMenu.getSelectedItemPosition() == 0) { // If index 0 is chosen for the colour menu

                        AlertDialog.Builder errorMenu = new AlertDialog.Builder(SportsAndOutdoorsActivity.this)
                                .setTitle(R.string.error)
                                .setMessage(tempResources[0])
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override

                                    public void onClick(DialogInterface dialog, int which) {
                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                    }
                                });

                        errorMenu.show();
                        errorMenu.setCancelable(true);
                    } 
                    
                    else {

                        addToBasketTwo(); // Otherwise if the above condition is false, add to the basket two.
                    }
                }
            }
        });
    }

    private boolean addToColoursList() { // Routine returns true or false if the colours have been added to the array list or not.

        Context context = getApplicationContext();
        String[] coloursResources = new String[]{context.getString(R.string.colourPrompt), context.getString(R.string.colourYellow), context.getString(R.string.colourBlack),
                context.getString(R.string.colourRed), context.getString(R.string.skyBlue)};

        TechActivity.Colours[] colours = {new TechActivity.Colours(0, coloursResources[0]), new TechActivity.Colours(1, coloursResources[1]),
                new TechActivity.Colours(2, coloursResources[2]), new TechActivity.Colours(3, coloursResources[3]),
                new TechActivity.Colours(4, coloursResources[4])};

        for (TechActivity.Colours productColours : colours) { // For each colours in the array
            listOfColoursOne.add(productColours); // Add it to the array list
            coloursAdded = true; // Colours have been added
        }

        return true;
    }

    private boolean addToColoursListTwo() {
        Context context = getApplicationContext();
        String[] coloursResources = new String[]{context.getString(R.string.colourPrompt), context.getString(R.string.blue), context.getString(R.string.red), context.getString(R.string.limeGreen)};

        TechActivity.Colours[] colours = {new TechActivity.Colours(0, coloursResources[0]), new TechActivity.Colours(1, coloursResources[1]),
                new TechActivity.Colours(2, coloursResources[2]), new TechActivity.Colours(3, coloursResources[3])};

        for (TechActivity.Colours theColours : colours) {
            listOfColoursTwo.add(theColours);
            coloursAdded = true;
        }

        return true;
    }

    private boolean addToQuantitiesListOne() { // Routine to add the quantities to the array list

        Context context = getApplicationContext();

        String[] quantitiesResources = new String[]{context.getString(R.string.zero), context.getString(R.string.one), context.getString(R.string.two), context.getString(R.string.three), context.getString(R.string.four), context.getString(R.string.five)};

        TechActivity.Quantities[] quantities = {new TechActivity.Quantities(quantitiesResources[0]), new TechActivity.Quantities(quantitiesResources[1]), new TechActivity.Quantities(quantitiesResources[2])
                , new TechActivity.Quantities(quantitiesResources[3]), new TechActivity.Quantities(quantitiesResources[4]), new TechActivity.Quantities(quantitiesResources[5])};

        for (TechActivity.Quantities quantitiesArray : quantities) {
            listOfQuantitiesOne.add(quantitiesArray);
            quantitiesAdded = true;
        }

        return true;
    }

    private boolean addToQuantitiesListTwo() { // Add to quantities list two.

        Context context = getApplicationContext();

        String[] quantitiesResources = new String[]{context.getString(R.string.zero), context.getString(R.string.one), context.getString(R.string.two), context.getString(R.string.three), context.getString(R.string.four), context.getString(R.string.five)};

        TechActivity.Quantities[] quantities = {new TechActivity.Quantities(quantitiesResources[0]), new TechActivity.Quantities(quantitiesResources[1]), new TechActivity.Quantities(quantitiesResources[2])
                , new TechActivity.Quantities(quantitiesResources[3]), new TechActivity.Quantities(quantitiesResources[4]), new TechActivity.Quantities(quantitiesResources[5])};

        for (TechActivity.Quantities quantitiesArray : quantities) { // For every quantity in the object array
            listOfQuantitiesTwo.add(quantitiesArray); // Add it to the array list
            quantitiesAdded = true; // The quantities have been added to the list is true.
        }

        return true;
    }

    private boolean addToSizesList() {
        Context context = getApplicationContext();
        String[] sizesResources = new String[]{context.getString(R.string.sizePrompt), context.getString(R.string.smallSize), context.getString(R.string.mediumSize), context.getString(R.string.largeSize), context.getString(R.string.extraLargeSize)};

        Size[] sizes = {new Size(0, sizesResources[0]), new Size(1, sizesResources[1]), new Size(2, sizesResources[2]),
                new Size(3, sizesResources[3]), new Size(4, sizesResources[4])}; // Creates an array of object of type size.

        for (Size theSizes : sizes) { // For each of the sizes in the array
            listOfSizesOne.add(theSizes);
            listOfSizesTwo.add(theSizes);
            sizesAdded = true;
        }

        return true;
    }

    private boolean addToBasketOne() { // Adds the first product to the basket
        Context context = getApplicationContext();
        String[] temp = new String[]{context.getString(R.string.addingBasket), context.getString(R.string.wait)};

        final ProgressDialog dialog = new ProgressDialog(SportsAndOutdoorsActivity.this); // Spinning progress dialog
        dialog.setTitle(temp[0]); // Set the title of the dialog
        dialog.setMessage(temp[1]);

        dialog.setCancelable(false); // The dialog can be cancelled by the user by clicking out of bounds.

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Sets the style of the progress bar

        new Thread(new Runnable() { // Create a new thread

            @Override
            public void run() { // Routine that runs the thread.

                try {
                    Thread.sleep(sleepSeconds); // Sleep for 1.9 seconds.
                } 
                
                catch (InterruptedException exc) {
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }

                dialog.dismiss();
            }
        }).start(); // Starts the thread

        dialog.show(); // Shows the dialog

        // Create an instance for the first product and adds it to the hash map.
        Products firstSportsProduct = new Products(current_product_id, firstSportsOutdoorTxt.getText().toString(), firstSportsOutdoorsColourMenu.getSelectedItem().toString(), (int) firstSportsOutdoorQuantityMenu.getSelectedItemId(), firstSportsOutdoorCostTxt.getText().toString(), firstSportsOutdoorsSizeMenu.getSelectedItem().toString());
        listOfProductsToAddToBasket.put(current_product_id, firstSportsProduct); // Add the product instance to the hash map.

        return true; // Returns true.
    }

    private boolean addToBasketTwo() { // Adds the second product to the basket
        Context context = getApplicationContext();

        String[] temp = new String[]{context.getString(R.string.addingBasket), context.getString(R.string.wait)};
        final ProgressDialog dialog = new ProgressDialog(SportsAndOutdoorsActivity.this); // Spinning progress dialog
        dialog.setTitle(temp[0]); // Set the title of the dialog.

        dialog.setMessage(temp[1]);

        dialog.setCancelable(false);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Sets the style of the progress bar

        new Thread(new Runnable() { // Create a new thread

            @Override
            public void run() {
                try {
                    Thread.sleep(sleepSeconds); // Sleep for 1.9 seconds.
                } 
                
                catch (InterruptedException exc) {
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }

                dialog.dismiss();
            }
        }).start(); // Starts the thread

        dialog.show();

        // Create an instance for the first product and adds it to the hash map.
        Products secondSportsProduct = new Products(current_product_id++, secondSportsOutdoorTxt.getText().toString(), secondSportsOutdoorsColourMenu.getSelectedItem().toString(), (int) secondSportsOutdoorQuantityMenu.getSelectedItemId(), secondSportsOutdoorCostLbl.getText().toString(), secondSportsOutdoorSizeMenu.getSelectedItem().toString());
        listOfProductsToAddToBasket.put(current_product_id++, secondSportsProduct);

        return true;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { // Method that determines which item has been selected and at which index
        boolean valueAppended = false;
        int[] indexes = {0, 1, 2, 3, 4};

        Context context = getApplicationContext();
        String[] productResources = new String[]{context.getString(R.string.productCost)};

        if (parent.getItemAtPosition(position).equals(listOfQuantitiesOne.get(indexes[0]))) {
            firstSportsOutdoorCostTxt.setText(null);
            firstSportsOutdoorCostTxt.setText(productResources[0] + (productOneCosts[0]));
            
            valueAppended = true;
        }
        
        else if (parent.getItemAtPosition(position).equals(listOfQuantitiesOne.get(indexes[1]))) {
            firstSportsOutdoorCostTxt.setText(null);
            firstSportsOutdoorCostTxt.setText(productResources[0] + (productOneCosts[1]));
            valueAppended = true; // Value is appended

        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfQuantitiesOne.get(indexes[2]))) {
            firstSportsOutdoorCostTxt.setText(null);
            firstSportsOutdoorCostTxt.append(productResources[0] + productOneCosts[2]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfQuantitiesOne.get(indexes[3]))) {
            firstSportsOutdoorCostTxt.setText(null);
            firstSportsOutdoorCostTxt.append(productResources[0] + productOneCosts[3]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfQuantitiesOne.get(indexes[4]))) {
            firstSportsOutdoorCostTxt.setText(null);
            firstSportsOutdoorCostTxt.append(productResources[0] + productOneCosts[4]);
            valueAppended = true;
        }


        if (parent.getItemAtPosition(position).equals(listOfQuantitiesTwo.get(indexes[0]))) {
            secondSportsOutdoorCostLbl.setText(null);
            secondSportsOutdoorCostLbl.append(productResources[0] + productTwoCosts[0]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfQuantitiesTwo.get(indexes[1]))) {
            secondSportsOutdoorCostLbl.setText(null);
            secondSportsOutdoorCostLbl.append(productResources[0] + productTwoCosts[1]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfQuantitiesTwo.get(indexes[2]))) {
            secondSportsOutdoorCostLbl.setText(null);
            secondSportsOutdoorCostLbl.append(productResources[0] + productTwoCosts[2]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfQuantitiesTwo.get(indexes[3]))) {
            secondSportsOutdoorCostLbl.setText(null);
            secondSportsOutdoorCostLbl.append(productResources[0] + productTwoCosts[3]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfQuantitiesTwo.get(indexes[4]))) {
            secondSportsOutdoorCostLbl.setText(null);
            secondSportsOutdoorCostLbl.append(productResources[0] + productTwoCosts[4]);
            valueAppended = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { // Nothing selected

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Add the toolbar menu
        // Inflate the activities menu
        MenuInflater activityInflater = getMenuInflater(); // Get the activity inflater
        activityInflater.inflate(R.menu.homepagemenu, menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.basket_action_button, menu);

        View view = menu.findItem(R.id.cart_menu).getActionView(); // Create a view for the cart icon
        cartIcon = view.findViewById(R.id.cart_icon); // Identify the Cart Icon by its ID

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent basketIntent = new Intent(SportsAndOutdoorsActivity.this, BasketActivity.class); // Create a basket intent
                basketIntent.putExtra("map", listOfProductsToAddToBasket); // Transit over the hash map data to the basket
                startActivity(basketIntent); // Start the intent
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) { // Determines which menu item has been selected
        try {
            switch (item.getItemId()) {
                    
                case R.id.sportsAndOutdoorsCategory: // If the sports and outdoors category is chosen
                    Intent sportsCategory = new Intent(SportsAndOutdoorsActivity.this, SportsAndOutdoorsActivity.class);
                    startActivity(sportsCategory); // Start the activity
                    break;

                case R.id.techCategory:
                    Intent techActivity = new Intent(SportsAndOutdoorsActivity.this, TechActivity.class);
                    startActivity(techActivity);
                    break;

                case R.id.clothingCategory:
                    Intent clothingActivity = new Intent(SportsAndOutdoorsActivity.this, ClothingCategory.class);
                    startActivity(clothingActivity);
                    break;

                case R.id.diyCategory:
                    Intent diyActivity = new Intent(SportsAndOutdoorsActivity.this, DIYActivity.class);
                    startActivity(diyActivity);
                    break;

                default:
                    super.onOptionsItemSelected(item);

            }
        } 
        
        catch (ActivityNotFoundException exc) {
            Log.d(String.valueOf(R.string.error), exc.toString());
        }

        return true;
    }
}
