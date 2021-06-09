package com.example.weshopapplication.ApplicationLayer;

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

// Author of Application: Sabin Constantin Lungu.
// Purpose of Application / Class: Contains the Java code for the DIY activity that corresponds to the DIY XML code.
// Date of Last Modification: 03/02/2020
// Any errors? None

public class DIYActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int current_product_id = 1; // The current product id to add to basket (will be incremented)
    private TextView diyFirstProductTxt; // The first product text

    private ImageView diyFirstProductImg; // Image of the first DIY product
    private TextView diyFirstProductCost; // The DIY first product cost

    private TextView diyFirstProductColourLbl; // The label for the first product colour
    private Spinner diyFirstProductColourMenu;

    private TextView diyFirstProductSizeLbl;
    private Spinner diyFirstProductSizeMenu; // The drop-down menu for the sizes of the first DIY product.

    private TextView diyFirstProductQuantityLbl;
    private Spinner diyFirstProductQuantityMenu;
    private Button diyFirstProductToAddToBasketBtn;

    private TextView diySecondProductTxt;
    private ImageView diySecondProductImg;

    private TextView diySecondProductCost;

    private TextView diySecondProductColourLbl;
    private Spinner diySecondProductColourMenu;

    private TextView diySecondProductSizeLbl;
    private Spinner diySecondProductSizeMenu;

    private TextView diySecondProductQuantityLbl;
    private Spinner diySecondProductQuantityMenu;

    private Button diySecondProductAddToBasketBtn;

    private double[] diyFirstProductCosts = new double[]{0.00, 40.00, 80.00, 160.00, 320.00, 640.00}; // A double array of product costs for the first DIY product
    private double[] diySecondProductCosts = new double[]{0.00, 20.00, 40.00, 80.00, 160.00, 320.00};

    private boolean coloursAdded = false; // Stores either true or false when the colours have been added to the array list
    private boolean sizesAdded = false;
    private boolean quantitiesAdded = false; // Stores either true or false when the quantities have been added.

    // Adapters for the objects to add to the list
    private QuantitiesArrayAdapter quantitiesAdapter;
    private SizeArrayAdapter sizeArrayAdapter;
    private ColourArrayAdapter coloursAdapter;

    private ArrayList<TechActivity.Colours> diyListOfColoursOne = null; // An array list of colours
    private ArrayList<Size> diyListOfSizesOne = null;
    private ArrayList<TechActivity.Quantities> diyListOfQuantitiesOne = null; // An Array list of quantities for the first diy product

    // Creates the array lists for the second DIY product.
    private ArrayList<TechActivity.Colours> diyListOfColoursTwo = null;
    private ArrayList<Size> diyListOfSizesTwo = null; // An Array list of DIY sizes
    private ArrayList<TechActivity.Quantities> diyListOfQuantitiesTwo = null; // An Array list of quantities

    private ImageView cartIcon; // Private variable for the cart icon
    private HashMap<Integer, Products> listOfProductsToAddToBasket = new HashMap<Integer, Products>(); // Creates a new hash map of products with an associated ID
    private Button nextPageBtn; // Next page button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy);

        // Initialise components
        this.diyFirstProductTxt = findViewById(R.id.diyFirstProductTxt);
        this.diyFirstProductImg = findViewById(R.id.diyFirstProductImg);

        this.diyFirstProductCost = findViewById(R.id.diyFirstProductCostTxt);
        this.diyFirstProductColourLbl = findViewById(R.id.diyFirstProductColourLbl);
        this.diyFirstProductColourMenu = findViewById(R.id.diyFirstProductColourMenu);

        this.diyFirstProductSizeLbl = findViewById(R.id.diyFirstProductSizeLbl);
        this.diyFirstProductSizeMenu = findViewById(R.id.diyFirstProductSizeMenu);

        this.diyFirstProductQuantityLbl = findViewById(R.id.diyFirstProductQuantityLbl);
        this.diyFirstProductQuantityMenu = findViewById(R.id.diyFirstProductQuantityMenu);
        this.diyFirstProductToAddToBasketBtn = findViewById(R.id.diyFirstProductAddToBasketBtn);

        this.diySecondProductTxt = findViewById(R.id.diySecondProductTxt);
        this.diySecondProductImg = findViewById(R.id.diySecondProductImg);

        this.diySecondProductCost = findViewById(R.id.diySecondProductCostLbl);
        this.diySecondProductColourLbl = findViewById(R.id.diySecondProductColourLbl);
        
        this.diySecondProductColourMenu = findViewById(R.id.diySecondProductColourMenu);
        this.diySecondProductQuantityMenu = findViewById(R.id.diySecondProductQuantityMenu);

        this.diySecondProductSizeLbl = findViewById(R.id.diySecondProductSizeLbl);
        this.diySecondProductSizeMenu = findViewById(R.id.diySecondProductSizeMenu);

        this.diySecondProductAddToBasketBtn = findViewById(R.id.diySecondProductAddToBasketBtn);

        this.diyListOfColoursOne = new ArrayList<>(); // Create a new array lsit of colours
        this.diyListOfColoursTwo = new ArrayList<>();

        this.diyListOfSizesOne = new ArrayList<>(); // Creates a new array list of sizes
        this.diyListOfSizesTwo = new ArrayList<>();

        this.diyListOfQuantitiesOne = new ArrayList<>();
        this.diyListOfQuantitiesTwo = new ArrayList<>();

        addToDIYColourList(); // Invoke routine to add to the DIY colours list
        addToDIYSizesList();

        addToDIYQuantitiesListOne();
        addToDIYQuantitiesListTwo();

        // Set-up adapters for the firsts Array List

        this.coloursAdapter = new ColourArrayAdapter(DIYActivity.this, diyListOfColoursOne);
        coloursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        diyFirstProductColourMenu.setAdapter(coloursAdapter);
        diyFirstProductColourMenu.setOnItemSelectedListener(DIYActivity.this);

        this.quantitiesAdapter = new QuantitiesArrayAdapter(DIYActivity.this, diyListOfQuantitiesOne); // Creates the quantities adapter for the first list of quantities.
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        diyFirstProductQuantityMenu.setAdapter(quantitiesAdapter);
        diyFirstProductQuantityMenu.setOnItemSelectedListener(DIYActivity.this);

        this.sizeArrayAdapter = new SizeArrayAdapter(DIYActivity.this, diyListOfSizesOne);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diyFirstProductSizeMenu.setAdapter(sizeArrayAdapter);
        diyFirstProductSizeMenu.setOnItemSelectedListener(DIYActivity.this);

        // Set-up adapters for the second ArrayList

        this.coloursAdapter = new ColourArrayAdapter(DIYActivity.this, diyListOfColoursTwo);
        coloursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        diySecondProductColourMenu.setAdapter(coloursAdapter);
        diySecondProductColourMenu.setOnItemSelectedListener(this);

        this.quantitiesAdapter = new QuantitiesArrayAdapter(DIYActivity.this, diyListOfQuantitiesTwo);
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        diySecondProductQuantityMenu.setAdapter(quantitiesAdapter);
        diySecondProductQuantityMenu.setOnItemSelectedListener(this);

        this.sizeArrayAdapter = new SizeArrayAdapter(DIYActivity.this, diyListOfSizesTwo);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        diySecondProductSizeMenu.setAdapter(sizeArrayAdapter);
        diySecondProductSizeMenu.setOnItemSelectedListener(this);

        this.diyFirstProductToAddToBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (diyFirstProductToAddToBasketBtn.getId() == R.id.diyFirstProductAddToBasketBtn) { // If the first button for the DIY add to basket button is clicked

                    if (diyFirstProductColourMenu.getSelectedItemPosition() == 0 || diyFirstProductSizeMenu.getSelectedItemPosition() == 0 || diyFirstProductQuantityMenu.getSelectedItemPosition() == 0) {
                        AlertDialog.Builder error = new AlertDialog.Builder(DIYActivity.this)
                                .setTitle(R.string.error) // Set the title of the alert dialogue
                                .setMessage(R.string.errorMsg) // Set the message
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        
                                        if (dialog != null) { // If there is a dialogue being displayed
                                            dialog.dismiss(); // Dismiss it.
                                        }
                                    }
                                });

                        error.show(); // Show the error
                        error.setCancelable(true);
                    } 
                    
                    else {
                        diyAddToBasketOne(); // Otherwise add the product to the basket.
                    }
                }
            }
        });

        this.diySecondProductAddToBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View secondButton) {
                
                if (secondButton.getId() == R.id.diySecondProductAddToBasketBtn) {

                    if (diySecondProductColourMenu.getSelectedItemPosition() == 0 || diySecondProductSizeMenu.getSelectedItemPosition() == 0 || diySecondProductQuantityMenu.getSelectedItemPosition() == 0) {
                        AlertDialog.Builder error = new AlertDialog.Builder(DIYActivity.this)

                                .setTitle(R.string.error)
                                .setMessage(R.string.errorMsg)
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        if (dialog != null) { // if there is a dialog

                                            dialog.dismiss(); // Dismiss it.
                                        }
                                    }
                                });

                        error.show();
                        error.setCancelable(true);
                    }
                    
                    else {
                        diyAddToBasketTwo();
                    }
                }
            }
        });

        this.nextPageBtn = findViewById(R.id.diyNextPageBtn);

        this.nextPageBtn.setOnClickListener(new View.OnClickListener() { // Adds an action listener for the next page button to the user to the next page
            @Override
            public void onClick(View v) {
                
                try {
                    if (v.getId() == R.id.diyNextPageBtn) { // If the next page button is clicked
                        
                        Intent nextDiyIntent = new Intent(DIYActivity.this, DIYActivityTwo.class); // Create a new intent
                        startActivity(nextDiyIntent);
                    }
                } 
                
                catch (ActivityNotFoundException exc) {
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }
            }
        });
    }

    private boolean addToDIYColourList() {
        Context context = getApplicationContext();
        String[] diyColoursResources = new String[]{context.getString(R.string.colourPrompt), context.getString(R.string.gallantGray), context.getString(R.string.darkBlack), context.getString(R.string.strawberryRed), context.getString(R.string.gardenGreen)};

        TechActivity.Colours[] colours = new TechActivity.Colours[]{new TechActivity.Colours(0, diyColoursResources[0]), new TechActivity.Colours(1, diyColoursResources[1]), new TechActivity.Colours(2, diyColoursResources[2]), new TechActivity.Colours(3, diyColoursResources[3]),
                new TechActivity.Colours(4, diyColoursResources[4])};

        for (TechActivity.Colours theColours : colours) { // For each of the colours in the colours array
            
            diyListOfColoursOne.add(theColours); // Add it to the array list
            diyListOfColoursTwo.add(theColours);
            coloursAdded = true;
        }

        return true;
    }

    private boolean addToDIYSizesList() {
        Context context = getApplicationContext();
        String[] diySizesResources = new String[]{context.getString(R.string.sizePrompt), context.getString(R.string.smallSize), context.getString(R.string.mediumSize), context.getString(R.string.largeSize), context.getString(R.string.extraLargeSize)};

        Size[] sizes = new Size[]{new Size(0, diySizesResources[0]), new Size(1, diySizesResources[1]), new Size(2, diySizesResources[2]), new Size(3, diySizesResources[3]), new Size(4, diySizesResources[4])};

        for (Size theSizes : sizes) {
            diyListOfSizesOne.add(theSizes);
            diyListOfSizesTwo.add(theSizes);

            sizesAdded = true;
        }

        return true;
    }

    private boolean addToDIYQuantitiesListOne() {

        Context context = getApplicationContext();
        String[] quantityResources = new String[]{context.getString(R.string.quantitiesPrompt), context.getString(R.string.zero), context.getString(R.string.one), context.getString(R.string.two), context.getString(R.string.three), context.getString(R.string.four), context.getString(R.string.five)};

        TechActivity.Quantities[] quantities = new TechActivity.Quantities[]{new TechActivity.Quantities(quantityResources[0]), new TechActivity.Quantities(quantityResources[1]), new TechActivity.Quantities(quantityResources[2]), new TechActivity.Quantities(quantityResources[3]), new TechActivity.Quantities(quantityResources[4]),
                new TechActivity.Quantities(quantityResources[5])};

        for (TechActivity.Quantities theQuantities : quantities) {
            diyListOfQuantitiesOne.add(theQuantities);

            sizesAdded = true;
        }

        return true;
    }

    private boolean addToDIYQuantitiesListTwo() {
        Context context = getApplicationContext();
        String[] quantityResources = new String[]{context.getString(R.string.quantitiesPrompt), context.getString(R.string.zero), context.getString(R.string.one), context.getString(R.string.two), context.getString(R.string.three), context.getString(R.string.four), context.getString(R.string.five)};

        TechActivity.Quantities[] quantities = new TechActivity.Quantities[]{new TechActivity.Quantities(quantityResources[0]), new TechActivity.Quantities(quantityResources[1]), new TechActivity.Quantities(quantityResources[2]), new TechActivity.Quantities(quantityResources[3]), new TechActivity.Quantities(quantityResources[4]),
                new TechActivity.Quantities(quantityResources[5])};

        for (TechActivity.Quantities theQuantities : quantities) { // For each quantity in the array list
            diyListOfQuantitiesTwo.add(theQuantities); // Add it to the array list
            sizesAdded = true;
        }

        return true;
    }

    private boolean diyAddToBasketOne() { // Routine that adds the first DIY product to the basket list view.
        Context context = getApplicationContext();
        String[] temp = new String[]{context.getString(R.string.addingBasket), context.getString(R.string.wait)};

        final ProgressDialog dialog = new ProgressDialog(DIYActivity.this); // Spinning progress dialog
        dialog.setTitle(temp[0]); // Set the title of the dialog
        dialog.setMessage(temp[1]);

        dialog.setCancelable(false);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Sets the style of the progress bar

        new Thread(new Runnable() { // Create a new thread

            @Override
            public void run() {
                try {
                    Thread.sleep(1900); // Sleep for 1.9 seconds.
                } 
                
                catch (InterruptedException exc) {
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }

                dialog.dismiss();
            }
        }).start(); // Starts the thread

        dialog.show();

        // Create an instance for the first product and adds it to the hash map.
        Products diyFirstProduct = new Products(current_product_id, diyFirstProductTxt.getText().toString(), diyFirstProductColourMenu.getSelectedItem().toString(), (int) diyFirstProductQuantityMenu.getSelectedItemId(), diyFirstProductCost.getText().toString(), diyFirstProductSizeMenu.getSelectedItem().toString());
        listOfProductsToAddToBasket.put(current_product_id, diyFirstProduct);

        return true;
    }

    private boolean diyAddToBasketTwo() {

        Context context = getApplicationContext();
        String[] temp = new String[]{context.getString(R.string.addingBasket), context.getString(R.string.wait)};

        final ProgressDialog dialog = new ProgressDialog(DIYActivity.this); // Spinning progress dialog
        dialog.setTitle(temp[0]); // Set the title of the dialog
        dialog.setMessage(temp[1]);

        dialog.setCancelable(false);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Sets the style of the progress bar

        new Thread(new Runnable() { // Create a new thread

            @Override
            public void run() {
                try {

                    Thread.sleep(1900); // Sleep for 1.9 seconds.
                }
                
                catch (InterruptedException exc) {
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }

                dialog.dismiss();
            }
        }).start(); // Starts the thread

        dialog.show();

        // Create an instance for the first product and adds it to the hash map.
        Products diySecondProduct = new Products(current_product_id++, diySecondProductTxt.getText().toString(), diySecondProductColourMenu.getSelectedItem().toString(), (int) diySecondProductQuantityMenu.getSelectedItemId(), diySecondProductCost.getText().toString(), diySecondProductSizeMenu.getSelectedItem().toString());
        listOfProductsToAddToBasket.put(current_product_id++, diySecondProduct);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Add the toolbar menu
        // Inflate the activities menu
        MenuInflater activityInflater = getMenuInflater(); // Get the activity inflater
        activityInflater.inflate(R.menu.homepagemenu, menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.basket_action_button, menu);

        View view = menu.findItem(R.id.cart_menu).getActionView();

        cartIcon = view.findViewById(R.id.cart_icon);

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent basketIntent = new Intent(DIYActivity.this, BasketActivity.class); // Create a basket intent
                basketIntent.putExtra("map", listOfProductsToAddToBasket); // Transit over the hash map data to the basket
                startActivity(basketIntent); // Start the intent
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) { // Routine that determines which item has been selected.

        try {
            switch (item.getItemId()) {

                case R.id.mainActivity: // If the main activity option is chosen
                    Intent mainActivity = new Intent(DIYActivity.this, MainActivity.class);
                    startActivity(mainActivity); // Start that activity
                    break;

                case R.id.sportsAndOutdoorsCategory:
                    Intent sportsCategory = new Intent(DIYActivity.this, SportsAndOutdoorsActivity.class);
                    startActivity(sportsCategory);

                    break;

                case R.id.techCategory: // Case when the tech category is chosen
                    Intent techActivity = new Intent(DIYActivity.this, TechActivity.class); // Create a new tech activity intent
                    startActivity(techActivity); // Start the activity
                    break;

                case R.id.clothingCategory:
                    Intent clothingActivity = new Intent(DIYActivity.this, ClothingCategory.class);
                    startActivity(clothingActivity);
                    break;

                case R.id.diyCategory:
                    Intent diyActivity = new Intent(DIYActivity.this, DIYActivity.class);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        boolean valueAppended = false;

        int[] indexes = new int[]{0, 1, 2, 3, 4};

        Context context = getApplicationContext();
        String[] productResources = new String[]{context.getString(R.string.productCost)};

        if (parent.getItemAtPosition(position).equals(diyListOfQuantitiesOne.get(indexes[0]))) { // If the first quantity option is chosen
            diyFirstProductCost.setText(null); // Clear the text
            diyFirstProductCost.append(productResources[0] + diyFirstProductCosts[0]); // Append the new text
            valueAppended = true;

        } 
       
        else if (parent.getItemAtPosition(position).equals(diyListOfQuantitiesOne.get(indexes[1]))) {
            diyFirstProductCost.setText(null);
            diyFirstProductCost.append(productResources[0] + diyFirstProductCosts[1]);
            valueAppended = true; // Value is appended

        }
        
        else if (parent.getItemAtPosition(position).equals(diyListOfQuantitiesOne.get(indexes[2]))) {
            diyFirstProductCost.setText(null);
            diyFirstProductCost.append(productResources[0] + diyFirstProductCosts[2]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(diyListOfQuantitiesOne.get(indexes[3]))) {
            diyFirstProductCost.setText(null);
            diyFirstProductCost.append(productResources[0] + diyFirstProductCosts[3]);
            valueAppended = true;
        }
        
        else if (parent.getItemAtPosition(position).equals(diyListOfQuantitiesOne.get(indexes[4]))) {
            diyFirstProductCost.setText(null);
            diyFirstProductCost.append(productResources[0] + diyFirstProductCosts[4]);
            valueAppended = true;
        }
        
        if (parent.getItemAtPosition(position).equals(diyListOfQuantitiesTwo.get(indexes[0]))) {
            diySecondProductCost.setText(null);
            diySecondProductCost.append(productResources[0] + diySecondProductCosts[0]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(diyListOfQuantitiesTwo.get(indexes[1]))) {
            diySecondProductCost.setText(null);
            diySecondProductCost.append(productResources[0] + diySecondProductCosts[1]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(diyListOfQuantitiesTwo.get(indexes[2]))) {
            diySecondProductCost.setText(null);
            diySecondProductCost.append(productResources[0] + diySecondProductCosts[2]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(diyListOfQuantitiesTwo.get(indexes[3]))) {
            diySecondProductCost.setText(null);
            diySecondProductCost.append(productResources[0] + diySecondProductCosts[3]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(diyListOfQuantitiesTwo.get(indexes[4]))) {
            diySecondProductCost.setText(null);
            diySecondProductCost.append(productResources[0] + diySecondProductCosts[4]);
            valueAppended = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
