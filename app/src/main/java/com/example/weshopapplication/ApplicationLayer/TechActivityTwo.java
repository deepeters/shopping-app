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
import androidx.annotation.NonNull;
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

public class TechActivityTwo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // Components for the Third Product
    private TextView thirdProductTextView;
    private ImageView thirdProductImage;

    private TextView thirdProductCostTxt;
    private TextView thirdProductColourLbl;

    private ImageView cartIcon;
    private Spinner thirdProductDropDown;

    private TextView thirdQuantityLabel;
    private Spinner thirdQuantityDropDown;
    private Button thirdAddToBasketButton;

    private TextView thirdSizeLbl;
    private Spinner thirdSizeDropDownMenu;

    // Components for the Fourth Product to sell
    private TextView fourthProductTextView;
    private ImageView fourthProductImage;

    private TextView fourthProductCost;
    private TextView fourthProductColourLbl;
    private Spinner fourthProductColourSpinner;

    private TextView fourthProductQuantity;
    private Spinner fourthProductQuantityDropDown;
    private TextView fourthProductSizeLbl;

    private Spinner fourthProductMenu;
    private Button fourthAddToBasketButton;

    // An array list of colours, quantities and capacity
    private ArrayList<TechActivity.Colours> listOfColours;
    private ArrayList<TechActivity.Quantities> listOfQuantities;
    private ArrayList<Size> listOfSizes;

    private ArrayList<TechActivity.Colours> secondListOfColours;
    private ArrayList<TechActivity.Quantities> secondListOfQuantities;
    private ArrayList<Size> secondListOfSizes;

    private double quantity_zero_cost = 0.0;
    private double quantity_one_cost = 249.99;

    // Formulae to calculate price & Capacity
    // COST FOR THE FIRST PRODUCT
    private double quantity_two_cost = 2 * quantity_one_cost; // Quantity 2 is 3 times the price of 1 quantity.
    private double quantity_three_cost = 3 * quantity_one_cost;

    private double quantity_four_cost = 4 * quantity_one_cost;
    private double quantity_five_cost = 5 * quantity_one_cost;

    private double product_four_zero_cost = 0.00;
    private double product_four_one_cost = 750.00;
    private double product_four_two_cost = 2 * product_four_one_cost;

    private double product_four_three_cost = 3 * product_four_one_cost;
    private double product_four_four_cost = 4 * product_four_one_cost;

    // Array adapters to aid the addition of colours, capacity and colours to the array list
    private QuantitiesArrayAdapter quantitiesAdapter;
    private ColourArrayAdapter colourArrayAdapter;
    private SizeArrayAdapter sizeArrayAdapter;

    // Boolean variables that holds either true or false
    private boolean addedColours = false;
    private boolean addedQuantities = false;
    private boolean addedCapacities = false;

    public int current_product_id = 1;
    private HashMap<Integer, Products> listOfProductsToAdd = new HashMap<>(); // A HashMap of products

    @Override
    protected void onCreate(Bundle savedInstanceState) { // On create method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_two);

        // Initialise components for the THIRD PRODUCT

        this.thirdProductTextView = findViewById(R.id.thirdProductTextView);
        this.thirdProductImage = findViewById(R.id.thirdProductImg);
        this.thirdProductCostTxt = findViewById(R.id.thirdProductCostTxt);
        this.thirdProductColourLbl = findViewById(R.id.thirdColourLbl);
        this.thirdProductDropDown = findViewById(R.id.thirdColourDropDownMenu);

        this.thirdSizeDropDownMenu = findViewById(R.id.thirdProductSizeDropDown);
        this.thirdSizeLbl = findViewById(R.id.thirdProductSizeLbl);

        this.thirdQuantityLabel = findViewById(R.id.thirdQuantityLbl);
        this.thirdQuantityDropDown = findViewById(R.id.thirdQuantityDropDownMenu);
        this.thirdAddToBasketButton = findViewById(R.id.thirdAddToBasketBtn);

        // INITIALISE COMPONENTS FOR THE FOURTH PRODUCT

        this.fourthProductTextView = findViewById(R.id.fourthProductTitleLbl);
        this.fourthProductImage = findViewById(R.id.fourthProductImg);
        this.fourthProductCost = findViewById(R.id.fourthProductImgCost);
        this.fourthProductColourLbl = findViewById(R.id.fourthProductColourLabel);

        this.fourthProductSizeLbl = findViewById(R.id.fourthProductSizeLbl);

        this.fourthProductColourSpinner = findViewById(R.id.fourthProductDropDownMenu);
        this.fourthProductQuantity = findViewById(R.id.fourthProductQuantityLbl);
        this.fourthAddToBasketButton = findViewById(R.id.fourthAddToBasketButton);

        this.fourthProductQuantityDropDown = findViewById(R.id.fourthProductQuantityDropDown);

        this.fourthProductSizeLbl = findViewById(R.id.fourthProductSizeLbl);
        this.fourthProductMenu = findViewById(R.id.fourthProductSizeMenu);

        // Put array list on the heap
        this.listOfColours = new ArrayList<>();
        this.listOfQuantities = new ArrayList<>();
        this.listOfSizes = new ArrayList<>();

        this.secondListOfColours = new ArrayList<>();
        this.secondListOfQuantities = new ArrayList<>();
        this.secondListOfSizes = new ArrayList<>();

        addToColoursList(); // Method to add to the colours array list
        addToQuantitiesList();

        addThirdProductSizes();
        addFourthProductSizes();

        // SET UP THE THIRD PRODUCT QUANTITIES DROP DOWN MENU TO SHOW
        this.quantitiesAdapter = new QuantitiesArrayAdapter(TechActivityTwo.this, listOfQuantities);
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        thirdQuantityDropDown.setAdapter(quantitiesAdapter);
        thirdQuantityDropDown.setOnItemSelectedListener(TechActivityTwo.this);

        // SET UP THE THIRD PRODUCT COLOUR SPINNER DROP DOWN MENU TO SHOW
        this.colourArrayAdapter = new ColourArrayAdapter(TechActivityTwo.this, listOfColours);
        colourArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        thirdProductDropDown.setAdapter(colourArrayAdapter);
        thirdProductDropDown.setOnItemSelectedListener(TechActivityTwo.this);

        // SET UP THE FOURTH PRODUCT COLOUR DROP DOWN MENU TO SHOW
        this.colourArrayAdapter = new ColourArrayAdapter(TechActivityTwo.this, secondListOfColours);
        colourArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fourthProductColourSpinner.setAdapter(colourArrayAdapter);
        fourthProductColourSpinner.setOnItemSelectedListener(TechActivityTwo.this);

        // SET UP QUANTITY FOR FOURTH PRODUCT
        this.quantitiesAdapter = new QuantitiesArrayAdapter(TechActivityTwo.this, secondListOfQuantities);
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fourthProductQuantityDropDown.setAdapter(quantitiesAdapter);
        fourthProductQuantityDropDown.setOnItemSelectedListener(TechActivityTwo.this);

        this.sizeArrayAdapter = new SizeArrayAdapter(TechActivityTwo.this, listOfSizes);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        thirdSizeDropDownMenu.setAdapter(sizeArrayAdapter);
        thirdSizeDropDownMenu.setOnItemSelectedListener(TechActivityTwo.this);

        this.sizeArrayAdapter = new SizeArrayAdapter(TechActivityTwo.this, secondListOfSizes);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fourthProductMenu.setAdapter(sizeArrayAdapter);
        fourthProductMenu.setOnItemSelectedListener(TechActivityTwo.this);

        this.thirdAddToBasketButton.setOnClickListener(new View.OnClickListener() { // Add Listener for third button
            @Override

            public void onClick(View v) {

                if (v.getId() == R.id.thirdAddToBasketBtn) {

                    if (thirdProductDropDown.getSelectedItemPosition() == 0 || thirdQuantityDropDown.getSelectedItemPosition() == 0 || thirdSizeDropDownMenu.getSelectedItemPosition() == 0) {

                        AlertDialog.Builder colourError = new AlertDialog.Builder(TechActivityTwo.this).setTitle(R.string.error)
                                .setMessage(R.string.errorMsg)
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (dialog != null) {

                                            dialog.dismiss();
                                        }
                                    }
                                });

                        colourError.show(); // Show the error
                        colourError.setCancelable(true); // Set cancelable to true
                    } 
                    
                    else {

                        addProductThreeToBasket();
                    }

                }
            }
        });

        this.fourthAddToBasketButton.setOnClickListener(new View.OnClickListener() { // Add action listener to the fourth button
            @Override

            public void onClick(View view) {
                if (view.getId() == R.id.fourthAddToBasketButton) {

                    if (fourthProductColourSpinner.getSelectedItemPosition() == 0 || fourthProductQuantityDropDown.getSelectedItemPosition() == 0 || fourthProductMenu.getSelectedItemPosition() == 0) {
                        AlertDialog.Builder error = new AlertDialog.Builder(TechActivityTwo.this).setTitle(R.string.error)

                                .setMessage(R.string.errorMsg)

                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                    }
                                });

                        error.show();
                        error.setCancelable(true);
                    } 
                    
                    else {
                        addProductFourToBasket();
                    }
                }
            }
        });
    }

    private void addProductThreeToBasket() { // Adds the third product to basket

        Context context = getApplicationContext();
        String[] temp = new String[]{context.getString(R.string.addingBasket), context.getString(R.string.wait)};

        final ProgressDialog dialog = new ProgressDialog(TechActivityTwo.this);
        dialog.setTitle(temp[0]);
        dialog.setMessage(temp[1]);

        dialog.setCancelable(false);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        new Thread(new Runnable() { // Create a new thread

            @Override
            public void run() {
                try {
                    Thread.sleep(1900);
                } 
                
                catch (InterruptedException exc) {
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }

                dialog.dismiss();

            }
        }).start();

        dialog.show(); // Show the progress bar

        Products thirdTechProduct = new Products(current_product_id, thirdProductTextView.getText().toString(), thirdProductDropDown.getSelectedItem().toString(), (int) thirdQuantityDropDown.getSelectedItemId(), thirdProductCostTxt.getText().toString(), thirdSizeDropDownMenu.getSelectedItem().toString());
        listOfProductsToAdd.put(current_product_id, thirdTechProduct);
    }

    private void addProductFourToBasket() { // Adds the fourth product to the basket

        Context context = getApplicationContext();
        String[] temp = new String[]{context.getString(R.string.addingBasket), context.getString(R.string.wait)};

        final ProgressDialog dialog = new ProgressDialog(TechActivityTwo.this);
        dialog.setTitle(temp[0]);
        dialog.setMessage(temp[1]);

        dialog.setCancelable(false);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        new Thread(new Runnable() { // Create a new thread

            @Override
            public void run() {
                try {
                    Thread.sleep(1900);
                }
                
                catch (InterruptedException exc) {
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }

                dialog.dismiss();
            }
        }).start();
        dialog.show(); // Show the progress bar

        Products fourthTechProduct = new Products(current_product_id++, fourthProductTextView.getText().toString(), fourthProductColourSpinner.getSelectedItem().toString(), (int) fourthProductQuantityDropDown.getSelectedItemId(), fourthProductCost.getText().toString(), fourthProductMenu.getSelectedItem().toString());
        listOfProductsToAdd.put(current_product_id++, fourthTechProduct);

    }

    private boolean addToColoursList() { // Routine that adds the colours to the array list

        Context context = getApplicationContext();
        String[] techTwoColourResources = new String[]{context.getString(R.string.colour), context.getString(R.string.white), context.getString(R.string.black), context.getString(R.string.salmon), context.getString(R.string.limeGreen), context.getString(R.string.rubyRed), context.getString(R.string.sportsThirdColour)};


        TechActivity.Colours[] firstColoursArray = {new TechActivity.Colours(0, techTwoColourResources[0]), new TechActivity.Colours(1, techTwoColourResources[1]), new TechActivity.Colours(2, techTwoColourResources[2])};

        TechActivity.Colours[] secondColoursArray = {new TechActivity.Colours(0, techTwoColourResources[0]), new TechActivity.Colours(1, techTwoColourResources[3]), new TechActivity.Colours(2, techTwoColourResources[4]),
                new TechActivity.Colours(3, techTwoColourResources[5]), new TechActivity.Colours(4, techTwoColourResources[6])};

        for (TechActivity.Colours colours : firstColoursArray) {
            listOfColours.add(colours);
            addedColours = true;
        }

        for (TechActivity.Colours secondColours : secondColoursArray) {
            secondListOfColours.add(secondColours);
            addedColours = true;
        }

        return true;
    }

    private boolean addThirdProductSizes() { // Adds the required sizes to the third product
        boolean addedSizes = false;
        Context context = getApplicationContext();
        String[] techTwoSizesResources = new String[]{context.getString(R.string.sizePrompt), context.getString(R.string.small), context.getString(R.string.medium), context.getString(R.string.large)};

        Size[] sizes = {new Size(0, techTwoSizesResources[0]), new Size(1, techTwoSizesResources[1]), new Size(2, techTwoSizesResources[2]), new Size(3, techTwoSizesResources[3])};

        for (Size theSizes : sizes) {
            listOfSizes.add(theSizes);
            addedSizes = true;
        }

        return true;
    }

    private boolean addFourthProductSizes() {
        boolean addedSizes = false;

        Context context = getApplicationContext();

        String[] techSizesResources = new String[]{context.getString(R.string.sizePrompt), context.getString(R.string.sixtyFourGB), context.getString(R.string.oneTwoEight), context.getString(R.string.twoFiveSix), context.getString(R.string.fiveTwelve)};

        Size[] fourthProdSizes = {new Size(0, techSizesResources[0]), new Size(1, techSizesResources[1]), new Size(2, techSizesResources[2]), new Size(3, techSizesResources[3]), new Size(4, techSizesResources[4])};

        for (Size sizes : fourthProdSizes) {
            secondListOfSizes.add(sizes);
            addedSizes = true;
        }

        return true;
    }

    private boolean addToQuantitiesList() { // Routine that adds the quantities to the array list

        Context context = getApplicationContext();

        String[] quantitiesResources = new String[]{context.getString(R.string.zero), context.getString(R.string.one), context.getString(R.string.two), context.getString(R.string.three), context.getString(R.string.four), context.getString(R.string.five)};


        TechActivity.Quantities[] quantitiesArray = {new TechActivity.Quantities(quantitiesResources[0]), new TechActivity.Quantities(quantitiesResources[1]), new TechActivity.Quantities(quantitiesResources[2]),
                new TechActivity.Quantities(quantitiesResources[3]), new TechActivity.Quantities(quantitiesResources[4]), new TechActivity.Quantities(quantitiesResources[5])};

        TechActivity.Quantities[] secondProductQuantities = {new TechActivity.Quantities(quantitiesResources[0]), new TechActivity.Quantities(quantitiesResources[1]), new TechActivity.Quantities(quantitiesResources[2]),
                new TechActivity.Quantities(quantitiesResources[3]), new TechActivity.Quantities(quantitiesResources[4]), new TechActivity.Quantities(quantitiesResources[5])};


        for (TechActivity.Quantities qty : quantitiesArray) { // For each quantity in the array
            listOfQuantities.add(qty);
            addedQuantities = true; // Quantities
        }

        for (TechActivity.Quantities qty2 : secondProductQuantities) {
            secondListOfQuantities.add(qty2);
            addedQuantities = true;
        }

        return true;
    }

    @Override
    protected void onDestroy() { // End the activity
        super.onDestroy();
    }

    @Override
    public void onBackPressed() { // Click back button
        super.onBackPressed();
    }

    @Override
    protected void onResume() { // Resumes the activity
        super.onResume();
    }

    @Override
    protected void onRestart() { // When restarted.
        super.onRestart();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { // Routine that determines which item has been selected
        boolean valueAppended = false;

        int[] quantityIndexes = {0, 1, 2, 3, 4, 5};
        Context context = getApplicationContext();

        String[] productResource = new String[]{context.getString(R.string.product_cost)};


        if (parent.getItemAtPosition(position).equals(listOfQuantities.get(quantityIndexes[0]))) {
            thirdProductCostTxt.setText(null);
            thirdProductCostTxt.append(productResource[0] + quantity_zero_cost);

            valueAppended = true;
        }

        // If the quantity at index 1 is chosen
        else if (parent.getItemAtPosition(position).equals(listOfQuantities.get(quantityIndexes[1]))) {
            thirdProductCostTxt.setText(null);
            thirdProductCostTxt.append(productResource[0] + quantity_one_cost);
            valueAppended = true; // Value is appended

        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfQuantities.get(quantityIndexes[2]))) {
            thirdProductCostTxt.setText(null);
            thirdProductCostTxt.append(productResource[0] + quantity_two_cost);

            valueAppended = true;

        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfQuantities.get(quantityIndexes[3]))) {
            thirdProductCostTxt.setText(null);
            thirdProductCostTxt.append(productResource[0] + quantity_three_cost);

            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfQuantities.get(quantityIndexes[4]))) {
            thirdProductCostTxt.setText(null);
            thirdProductCostTxt.append(productResource[0] + quantity_four_cost);

            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfQuantities.get(quantityIndexes[5]))) {
            thirdProductCostTxt.setText(null);
            thirdProductCostTxt.append(productResource[0] + quantity_five_cost);

            valueAppended = true;
        }

        if (parent.getItemAtPosition(position).equals(secondListOfQuantities.get(quantityIndexes[0]))) {
            fourthProductCost.setText(null);
            fourthProductCost.append(productResource[0] + product_four_zero_cost);
        } 
        
        else if (parent.getItemAtPosition(position).equals(secondListOfQuantities.get(quantityIndexes[1]))) {
            fourthProductCost.setText(null);
            fourthProductCost.append(productResource[0] + product_four_one_cost);

            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(secondListOfQuantities.get(quantityIndexes[2]))) {
            fourthProductCost.setText(null);
            fourthProductCost.append(productResource[0] + product_four_two_cost);

            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(secondListOfQuantities.get(quantityIndexes[3]))) {
            fourthProductCost.setText(null);
            fourthProductCost.append(productResource[0] + quantity_three_cost);

            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(secondListOfQuantities.get(quantityIndexes[4]))) {
            fourthProductCost.setText(null);
            fourthProductCost.append(productResource[0] + quantity_four_cost);

            valueAppended = true;
        }
        
        else if (parent.getItemAtPosition(position).equals(secondListOfQuantities.get(quantityIndexes[5]))) {
            fourthProductCost.setText(null);
            fourthProductCost.append(productResource[0] + quantity_five_cost);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Creates the menu bar
        MenuInflater activityInflater = getMenuInflater();
        activityInflater.inflate(R.menu.homepagemenu, menu);

        MenuInflater basketButtonInflater = getMenuInflater();
        basketButtonInflater.inflate(R.menu.basket_action_button, menu);

        View cartView = menu.findItem(R.id.cart_menu).getActionView(); // Get the action view for the cart

        cartIcon = cartView.findViewById(R.id.cart_icon);

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent basketIntent = new Intent(TechActivityTwo.this, BasketActivity.class);
                    basketIntent.putExtra("map", listOfProductsToAdd);
                    startActivity(basketIntent); // Start the basket intent

                } 
                
                catch (ActivityNotFoundException exc) {
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        try {

            switch (item.getItemId()) {

                case R.id.sportsAndOutdoorsCategory:
                    Intent sportsAndOutdoorsActivity = new Intent(TechActivityTwo.this, SportsAndOutdoorsActivity.class);
                    startActivity(sportsAndOutdoorsActivity);

                    return true;

                case R.id.techCategory:
                    Intent techCategory = new Intent(TechActivityTwo.this, TechActivity.class);
                    startActivity(techCategory);

                    return true;

                case R.id.clothingCategory:
                    Intent clothingCategory = new Intent(TechActivityTwo.this, ClothingCategory.class);
                    startActivity(clothingCategory);

                    return true;

                case R.id.diyCategory:

                    Intent diyCategory = new Intent(TechActivityTwo.this, DIYActivity.class);
                    startActivity(diyCategory);

                    return true;

                default:
                    return super.onOptionsItemSelected(item);

            }
        } 
        
        catch (ActivityNotFoundException exc) {

            Log.d(String.valueOf(R.string.error), exc.toString());
        }

        return true;
    }
}
