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
import com.example.weshopapplication.BusinessObjects.SizesAdapter;
import com.example.weshopapplication.R;
import java.util.ArrayList;
import java.util.HashMap;



public class TechActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public int current_product_id = 1; // The current product ID
    private long sleepSeconds = 1900; // Number of seconds for the thread to sleep for.
    
    private TextView firstProductText; // Text for the first product
    private Thread firstActivityThread; // The Activity Thread.
    
    private ImageView firstProductImg; // The Image for the first product
    private TextView productCost; // Product Cost Text.

    private TextView firstProductColour; // The colour for the first product.
    private Button firstAddToBasketButton; // The first add to basket button.
    
    private TextView firstProductSizes;
    private Spinner firstProductSizesMenu; // Drop-down menu for the first product sizes.

    private TextView secondProductText; // The text for the second product.
    private ImageView secondProductImg;
    private TextView secondProductCost;

    private TextView secondProductColour;
    private Button secondAddToBasketButton;

    private TextView secondProductSizes;
    private Spinner secondProductSizesMenu;

    private ImageView cartIcon; // Cart Icon should be red once a product is added
    private Spinner firstProductColourOptions;
    private Spinner firstProductQuantityOptions;

    private Spinner secondProductColourOptions;
    private Spinner secondProductQuantityOptions;

    private ArrayList<Colours> listOfColours = null; // An Array List of colours to store the colours for the product.
    private ArrayList<Quantities> listOfQuantities = null; // An Array List of Quantities to store the product quantities.
    private ArrayList<Size> listOfSizes = null; // An Array List of sizes. Null (empty) initially.

    private QuantitiesArrayAdapter quantitiesCustomAdapter = null; // Quantities Array Adapter to store a custom object.
    private ColourArrayAdapter colourArrayAdapter = null;
    private SizesAdapter sizesAdapter = null;

    private ArrayList<Colours> secondListOfColours = null;
    private ArrayList<Quantities> secondListOfQuantities = null;
    private ArrayList<Size> secondListOfSizes = null;

    private Button nextPageBtn; // Button for the next page.
    private double[] techProductOneCosts = new double[]{0.00, 500.00, 1000.00, 2000.00, 4000.00, 8000.00}; // Prices for the first product
    private double[] techProductTwoCosts = new double[]{0.00, 300.00, 600.00, 1200.00, 2400.00, 4800.00};

    private HashMap<Integer, Products> listOfProductsToAddToBasket = new HashMap<>(); // A hashmap of products with an integer key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech);

        // INITIALISE COMPONENTS FOR FIRST PRODUCT
        this.firstActivityThread = new Thread();
        this.firstProductText = findViewById(R.id.firstProductText);
        
        this.firstProductImg = findViewById(R.id.firstProductImg);
        this.productCost = findViewById(R.id.firstProductCost);

        this.firstProductColourOptions = findViewById(R.id.firstColourSpinner);
        this.firstProductColour = findViewById(R.id.firstProductColorText); // Text View for Product Cost £: (1)

        this.firstProductSizes = findViewById(R.id.firstTechProductSizeLabel);
        this.firstProductSizesMenu = findViewById(R.id.firstProductSizeDropDownMenu);

        this.firstProductColourOptions = findViewById(R.id.firstColourSpinner); // Spinner 1. -> COLOURS
        this.firstProductQuantityOptions = findViewById(R.id.firstQuantitySpinner); // Spinner 2 -> QUANTITIES
        this.firstAddToBasketButton = findViewById(R.id.thirdAddToBasketBtn); // Button: -> ADD TO BASKET BUTTON 1

        this.secondProductColourOptions = findViewById(R.id.secondColourSpinner);
        this.secondProductQuantityOptions = findViewById(R.id.secondQuantitySpinner);

        this.secondProductSizes = findViewById(R.id.secondProductSizeLabel);
        this.secondProductSizesMenu = findViewById(R.id.secondProductSizeDropDownMenu);

        this.listOfColours = new ArrayList<>();
        this.listOfQuantities = new ArrayList<>();
        this.listOfSizes = new ArrayList<>();

        // Create 2nd array list
        this.secondListOfColours = new ArrayList<>();
        this.secondListOfQuantities = new ArrayList<>();
        this.secondListOfSizes = new ArrayList<>();

        addToColoursList(); // Invoke routine to add the colours to the array list
        addToQuantitiesList();

        addToSizesList(); // Invoke routine to add to the sizes array list.
        addToSizesTwoList();

        this.colourArrayAdapter = new ColourArrayAdapter(TechActivity.this, listOfColours); // Create an array adapter for the colours drop down menu
        colourArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstProductColourOptions.setAdapter(colourArrayAdapter); // Set its adapter

        this.quantitiesCustomAdapter = new QuantitiesArrayAdapter(TechActivity.this, listOfQuantities);
        quantitiesCustomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstProductQuantityOptions.setAdapter(quantitiesCustomAdapter);

        // Add action listener for first product colour and first product quantity
        firstProductColourOptions.setOnItemSelectedListener(this);
        firstProductQuantityOptions.setOnItemSelectedListener(this);

        this.sizesAdapter = new SizesAdapter(TechActivity.this, listOfSizes);
      
        sizesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        firstProductSizesMenu.setAdapter(sizesAdapter); // Initialise the adapter
        firstProductSizesMenu.setOnItemSelectedListener(this);

        secondProductSizesMenu.setAdapter(sizesAdapter);
        secondProductSizesMenu.setOnItemSelectedListener(this);

        secondProductColourOptions.setOnItemSelectedListener(this);
        secondProductQuantityOptions.setOnItemSelectedListener(this);

        // Initialise components for SECOND PRODUCT
        this.secondProductText = findViewById(R.id.secondProductTxt);
        this.secondProductImg = findViewById(R.id.appleWatchImg);
        this.secondProductCost = findViewById(R.id.secondProductCost);

        this.secondProductColour = findViewById(R.id.secondProductColourTxt);
        this.secondAddToBasketButton = findViewById(R.id.secondAddToBasketBtn);

        // Create adapters for the 2nd product
        this.quantitiesCustomAdapter = new QuantitiesArrayAdapter(TechActivity.this, secondListOfQuantities);
        this.colourArrayAdapter = new ColourArrayAdapter(TechActivity.this, secondListOfColours);

        quantitiesCustomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colourArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        secondProductQuantityOptions.setAdapter(quantitiesCustomAdapter);
        secondProductColourOptions.setAdapter(colourArrayAdapter);

        this.sizesAdapter = new SizesAdapter(TechActivity.this, secondListOfSizes);
        sizesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        secondProductSizesMenu.setAdapter(sizesAdapter);
        secondProductSizesMenu.setOnItemSelectedListener(this);

        this.nextPageBtn = findViewById(R.id.sportsNextPage);

        this.nextPageBtn.setOnClickListener(new View.OnClickListener() { // Add a listener for the next page button
            @Override
            public void onClick(View v) {
                // Create intent for next Tech Activity
                try {

                    // Start the next intent
                    Intent techActivityTwo = new Intent(TechActivity.this, TechActivityTwo.class);
                    startActivity(techActivityTwo);
                } 
                
                catch (ActivityNotFoundException not) {
                    Log.d(String.valueOf(R.string.error), not.toString());
                }
            }
        });

        firstAddToBasketButton.setOnClickListener(new View.OnClickListener() { // Adds a listener for the first add to basket button
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.thirdAddToBasketBtn) { // If the first add to basket button is clicked

                    if (firstProductColourOptions.getSelectedItemPosition() == 0 || firstProductQuantityOptions.getSelectedItemPosition() == 0 || firstProductSizesMenu.getSelectedItemPosition() == 0) { //

                        AlertDialog.Builder colourErrorOne = new AlertDialog.Builder(TechActivity.this)
                                .setTitle(R.string.error)
                                .setMessage(R.string.errorMsg).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override

                                    public void onClick(DialogInterface dialog, int which) {

                                        if (dialog != null) { // If there is a dialog
                                            dialog.dismiss(); // Dismiss it.
                                        }
                                    }
                                });

                        colourErrorOne.show(); // Display the error
                        colourErrorOne.setCancelable(true);
                    }

                    else {

                        addToBasketOne(); // Otherwise add to basket
                    }
                }
            }
        });

        secondAddToBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View secondButton) {

                if (secondButton.getId() == R.id.secondAddToBasketBtn) { // If the second add to basket button is clicked

                    if (secondProductColourOptions.getSelectedItemPosition() == 0 || secondProductQuantityOptions.getSelectedItemPosition() == 0 || secondProductSizesMenu.getSelectedItemPosition() == 0) {

                        AlertDialog.Builder secondProductColourError = new AlertDialog.Builder(TechActivity.this)
                                .setTitle(R.string.error)
                                .setMessage(R.string.errorMsg)
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override

                                    public void onClick(DialogInterface dialog, int which) {
                                        if (dialog != null) { // If there is a dialogue visible
                                            dialog.dismiss(); // Dismiss it.
                                        }
                                    }
                                });

                        secondProductColourError.show();
                        secondProductColourError.setCancelable(true); // User can click outside the Window to cancel the dialogue
                    } 
                    
                    else {
                        addToBasketTwo(); // Otherwise invoke routine to add to the second basket.
                    }
                }
            }
        });
    }

    private boolean addToBasketOne() { // Routine to add the product to the basket

        Context context = getApplicationContext();
        String[] temp = new String[]{context.getString(R.string.wait), context.getString(R.string.addingBasket)};
        
        final ProgressDialog dialog = new ProgressDialog(TechActivity.this);
        dialog.setTitle(temp[1]); // Set the title of the dialogue
        
        dialog.setMessage(temp[0]);
        dialog.setCancelable(false);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        new Thread(new Runnable() { // Create a new thread

            @Override
            public void run() { // Runs the thread
                try {
                    Thread.sleep(sleepSeconds); // Sleep for 1.9 seconds
                } 
                
                catch (InterruptedException exc) { // Catch the error.
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }

                dialog.dismiss(); // Dismiss the dialogue.
            }
        }).start();

        dialog.show(); // Show the spinning dialogue.

        Products firstProduct = new Products(current_product_id, firstProductText.getText().toString(), firstProductColourOptions.getSelectedItem().toString(), (int) firstProductQuantityOptions.getSelectedItemId(), productCost.getText().toString(), firstProductSizesMenu.getSelectedItem().toString());
        listOfProductsToAddToBasket.put(current_product_id, firstProduct); // Add the product data to the hash map

        return true;
    }

    private boolean addToBasketTwo() { // Adds the second product on the First Tech Activity to the basket

        Context context = getApplicationContext();
        String[] temp = new String[]{context.getString(R.string.wait), context.getString(R.string.addingBasket)};

        final ProgressDialog dialog = new ProgressDialog(TechActivity.this);
        dialog.setTitle(temp[0]);
        dialog.setMessage(temp[1]);

        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        new Thread(new Runnable() { // Create a new thread

            @Override
            public void run() {
                try {
                    Thread.sleep(sleepSeconds);
                } 
                
                catch (InterruptedException exc) {
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }

                dialog.dismiss();
            }
        }).start();

        dialog.show();

        Products secondProduct = new Products(current_product_id++, secondProductText.getText().toString(), secondProductColourOptions.getSelectedItem().toString(), (int) secondProductQuantityOptions.getSelectedItemId(), secondProductCost.getText().toString(), secondProductSizesMenu.getSelectedItem().toString());
        listOfProductsToAddToBasket.put(current_product_id++, secondProduct);

        return true;
    }

    private boolean addToColoursList() { // Routine to add the first tech product colours to an array list
        boolean addedColours = false;
        Context context = getApplicationContext();

        String[] colourResources = new String[]{context.getString(R.string.colour), context.getString(R.string.spaceGray), context.getString(R.string.secondColour), context.getString(R.string.thirdColour)};

        Colours[] coloursArray = {new Colours(0, colourResources[0]), new Colours(1, colourResources[1]), new Colours(2, colourResources[2]), new Colours(3, colourResources[3])};

        for (Colours colours : coloursArray) { // For each colour in the array
            listOfColours.add(colours); // Add it to the array list
            
            secondListOfColours.add(colours); // Add the second colours
            addedColours = true;
        }

        return true;
    }

    private boolean addToSizesList() { // Routine to add to the sizes array list
        boolean addedSizes = false;

        Context context = getApplicationContext();

        String[] sizeResources = new String[]{context.getString(R.string.sizePrompt), context.getString(R.string.sixtyFourGB), context.getString(R.string.oneTwoEight), context.getString(R.string.twoFiveSix), context.getString(R.string.fiveTwelve)};

        Size[] techSizes = {new Size(0, sizeResources[0]), new Size(1, sizeResources[1]), new Size(2, sizeResources[2]), new Size(3, sizeResources[3]),
                new Size(4, sizeResources[4])};

        for (Size sizes : techSizes) { // For every size in the object array
            listOfSizes.add(sizes); // Add it to the array list
            addedSizes = true; // Sizes have been added
        }

        return true;
    }

    private boolean addToSizesTwoList() {
        boolean addedSizes = false;

        Context context = getApplicationContext();
        String[] watchResources = new String[]{context.getString(R.string.sizePrompt), context.getString(R.string.fourtyMM), context.getString(R.string.fourtyTwoMM)};

        Size[] watchSizes = {new Size(0, watchResources[0]), new Size(1, watchResources[1]), new Size(2, watchResources[2])};

        for (Size size : watchSizes) {
            secondListOfSizes.add(size);
            addedSizes = true;
        }

        return true;
    }

    private boolean addToQuantitiesList() { // Routine to add the quantities to the array list
        boolean addedQuantities = false;

        Context context = getApplicationContext();

        String[] quantitiesResources = new String[]{context.getString(R.string.zero), context.getString(R.string.one), context.getString(R.string.two), context.getString(R.string.three), context.getString(R.string.four), context.getString(R.string.five)};

        Quantities[] firstProductQuantities = { // Create quantities array of objects
                new Quantities(quantitiesResources[0]), new Quantities(quantitiesResources[1]), new Quantities(quantitiesResources[2])
                , new Quantities(quantitiesResources[3]), new Quantities(quantitiesResources[4]), new Quantities(quantitiesResources[5])};

        Quantities[] secondProductQuantities = {new Quantities(quantitiesResources[0]), new Quantities(quantitiesResources[1]), new Quantities(quantitiesResources[2]),
                new Quantities(quantitiesResources[3]), new Quantities(quantitiesResources[4]), new Quantities(quantitiesResources[5])};

        for (Quantities quantities : firstProductQuantities) { // For each quantity in the array
            listOfQuantities.add(quantities); // Add it to the array list

            addedQuantities = true;
        }

        for (Quantities secondQuantities : secondProductQuantities) { // For each quantities in the second list of quantities
            secondListOfQuantities.add(secondQuantities); // Add it to the list

            addedQuantities = true;
        }

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        boolean valueAppended = false;
        int[] indexes = {0, 1, 2, 3, 4, 5}; // Array of indexes

        Context context = getApplicationContext();
        String[] productResource = new String[]{context.getString(R.string.product_cost)};

        if (parent.getItemAtPosition(position).equals(listOfQuantities.get(indexes[0]))) { // If the first option in the drop down menu is selected at index 0
            productCost.setText(null);
            productCost.append(productResource[0] + techProductOneCosts[0]);

            valueAppended = true;
        }

        else if (parent.getItemAtPosition(position).equals(listOfQuantities.get(indexes[1]))) { // If the value at index 1 is chosen
            productCost.setText(null); // Flush out the product cost
            productCost.append(productResource[0] + techProductOneCosts[1]); // Append the cost

            valueAppended = true;
        }

        else if (parent.getItemAtPosition(position).equals(listOfQuantities.get(indexes[2]))) {
            productCost.setText(null);
            productCost.append(productResource[0] + techProductOneCosts[2]);

            valueAppended = true;
        }

        else if (parent.getItemAtPosition(position).equals(listOfQuantities.get(indexes[3]))) {
            productCost.setText(null);
            productCost.append(productResource[0] + techProductOneCosts[3]);
            valueAppended = true;

        }

        else if (parent.getItemAtPosition(position).equals(listOfQuantities.get(indexes[4]))) {
            productCost.setText(null);
            productCost.append(productResource[0] + techProductOneCosts[4]);
            valueAppended = true;

        }

        if (parent.getItemAtPosition(position).equals(secondListOfQuantities.get(indexes[0]))) {
            secondProductCost.setText(null);
            secondProductCost.append(productResource[0] + techProductTwoCosts[0]);
            valueAppended = true;
        }

        else if (parent.getItemAtPosition(position).equals(secondListOfQuantities.get(indexes[1]))) {
            secondProductCost.setText(null);
            secondProductCost.append(productResource[0] + techProductTwoCosts[1]);
            valueAppended = true;
        }

        else if (parent.getItemAtPosition(position).equals(secondListOfQuantities.get(indexes[2]))) {
            secondProductCost.setText(null);
            secondProductCost.append(productResource[0] + techProductTwoCosts[2]);


            valueAppended = true;
        }

        else if (parent.getItemAtPosition(position).equals(secondListOfQuantities.get(indexes[3]))) { // If the item at index 3 is chosen
            secondProductCost.setText(null); // Set text to null
            secondProductCost.append(productResource[0] + techProductTwoCosts[3]); // Then append the cost + string.
        }

        else if (parent.getItemAtPosition(position).equals(secondListOfQuantities.get(indexes[4]))) {
            secondProductCost.setText(null);
            secondProductCost.append(productResource[0] + techProductTwoCosts[4]);
        } 
        
        else if (parent.getItemAtPosition(position).equals(secondListOfQuantities.get(indexes[5]))) {
            secondProductCost.setText(null);
            secondProductCost.append(productResource[0] + techProductTwoCosts[5]);
        } 
        
        else {
            valueAppended = false;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) { // Routine that gets an item selected from the menu

        try {

            switch (item.getItemId()) {

                case R.id.sportsAndOutdoorsCategory: // When the sports and activity category is chosen
                    Intent sportsAndOutdoors = new Intent(TechActivity.this, SportsAndOutdoorsActivity.class); // Create intent for sports and outdoors
                    Thread.sleep(1); // Sleep for 1ms
                    startActivity(sportsAndOutdoors); // Start the activity

                    return true; // Returns true

                case R.id.techCategory: // If the tech category is chosen
                    Intent techCategory = new Intent(TechActivity.this, TechActivity.class); // Go to the tech category
                    
                    Thread.sleep(1);
                    startActivity(techCategory); // Start that activity

                    return true;

                case R.id.clothingCategory:
                    Intent clothingCategory = new Intent(TechActivity.this, ClothingCategory.class);
                    Thread.sleep(1);
                    
                    startActivity(clothingCategory);
                    return true;

                case R.id.diyCategory:
                    Intent diyCategory = new Intent(TechActivity.this, DIYActivity.class);
                    Thread.sleep(1);
                    startActivity(diyCategory);

                    return true;

                default:
                    return super.onOptionsItemSelected(item); // Return base item if no option is chosen

            }
        } 
        
        catch (ActivityNotFoundException act) { // Catch error

            Log.d(String.valueOf(R.string.error), act.toString()); // Log error if there is a problem.
        } 
        
        catch (InterruptedException act) {

            Log.d(String.valueOf(R.string.error), act.toString());
        }

        return true;
    }

    public static class Quantities { // Anonymous Inner Class.
        private String quantity; // Quantity variable

        public Quantities(String quantity) { // Parameterised constructor that creates the object and the data when this is called.
            this.quantity = quantity;
        }

        public String getQuantity() { // Gets the quantity
            return this.quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String toString() { // Returns the data.
            return " " + this.quantity + " ";
        }
    }

    // Anonymous inner class Quantities that is used to add the quantities to the drop-down menu. This class will be reused throughout other activities in order to retrieve specific data.

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onDestroy() { // On destroy method
        super.onDestroy();
    }

    public void onResume() { // On resume method when the activity resumes
        super.onResume();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public boolean onCreateOptionsMenu(Menu menu) { // Routine to create the options menu.

        // Inflate the activities menu
        MenuInflater activityInflater = getMenuInflater(); // Get the activity inflater
        activityInflater.inflate(R.menu.homepagemenu, menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.basket_action_button, menu);

        View view = menu.findItem(R.id.cart_menu).getActionView();

        cartIcon = view.findViewById(R.id.cart_icon);

        cartIcon.setOnClickListener(new View.OnClickListener() { // Adds an on click listener to the Cart Icon.
            @Override
            public void onClick(View v) {

                Intent basketIntent = new Intent(TechActivity.this, BasketActivity.class);
                basketIntent.putExtra("map", listOfProductsToAddToBasket);
                startActivity(basketIntent); // Start the basket activity
            }
        });

        return true;
    }

    // Anonymous inner classes that will be used later on in the basket activity and the payment form

    public static class Colours { // Anonymous inner class of colours
        private int index; // Index of the colour
        private String colour; // The colour

        public Colours(int index, String colour) { // Parameterised constructor
            this.index = index;
            this.colour = colour;
        }

        public int getIndex() { // Returns the index
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getColour() { // Returns the colour
            return this.colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        @Override
        public String toString() {
            return " " + this.colour;
        }
    }

    public static class Size {
        private int index;
        private String theSize;

        public Size(int index, String theSize) {
            this.index = index;
            this.theSize = theSize;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getTheSize() {
            return theSize;
        }

        public void setTheSize(String theSize) {
            this.theSize = theSize;
        }

        @Override
        public String toString() {
            return " " + this.theSize;
        }
    }
}
