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


public class ClothingActivityTwo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int current_product_id = 1; // The current product id.
    private ImageView cartIcon; // The Basket Icon.

    private TextView clothingThirdProductTxt; // The text for the third Clothing Product
    private ImageView clothingThirdProductImg; // The third product image.

    private TextView clothingThirdProductColourLbl;

    private TextView clothingThirdProductCostLbl;
    private Spinner clothingThirdProductColourMenu; // The drop-down menu for the third clothing product colour

    private TextView clothingThirdProductSizeLbl; // The size for the clothing third product.
    private Spinner clothingThirdProductSizeMenu;
    private TextView clothingFourthProductCostLbl; // The label for the fourth product.

    private TextView clothingThirdProductQuantityLbl;
    private Spinner clothingThirdProductQuantityMenu;
    private Button clothingThirdAddToBasketBtn; // Button for adding the third product to the basket.

    private TextView clothingFourthProductTxt;
    private ImageView clothingFourthProductImageView;

    private TextView clothingFourthProductColourLbl;
    private Spinner clothingFourthProductColourMenu;

    private TextView clothingFourthProductSizeLbl;
    private Spinner clothingFourthProductSizeMenu;

    private TextView clothingFourthProductQuantityLbl;
    private Spinner clothingFourthProductQuantityMenu;
    private Button clothingFourthProductAddToBasketBtn;

    private double[] clothingThirdProductCosts = new double[]{0.00, 30.00, 60.00, 120.00, 240.00, 480.00}; // This double array stores the costs for the third product
    private double[] clothingFourthProductCosts = new double[]{0.00, 40.00, 80.00, 160.00, 320.00, 640.00}; // The product costs for the fourth products.

    private QuantitiesArrayAdapter quantitiesAdapter;
    private ColourArrayAdapter coloursAdapter;
    private SizeArrayAdapter sizesAdapter;

    private ArrayList<TechActivity.Colours> listOfClothingColoursOne = null; // An Array lsit of colours.
    private ArrayList<Size> listOfClothingSizesOne = null;
    private ArrayList<TechActivity.Quantities> listOfClothingQuantitiesOne = null; // An Array list of quantities.

    private ArrayList<TechActivity.Colours> listOfClothingColoursTwo = null;
    private ArrayList<Size> listOfClothingSizesTwo = null;
    private ArrayList<TechActivity.Quantities> listOfClothingQuantitiesTwo = null;

    private boolean coloursAdded = false; // Boolean variable that stores either true or false if the colours have been added to the array list
    private boolean quantitiesAdded = false; // Boolean variables that stores either true or false when the quantities have been added to the Array List.
    private boolean sizesAdded = false; // Boolean variable that stores true or false if the sizes have been added.

    private HashMap<Integer, Products> listOfProductsToAddToBasket; // A hash map that stores the product id and the product instance.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_two);

        // Initialises the components
        this.clothingThirdProductTxt = findViewById(R.id.clothingThirdProductTxt); // Set up the clothing third product text view.
        this.clothingThirdProductImg = findViewById(R.id.clothingThirdProductImg); // Set-up the image component

        this.clothingThirdProductColourLbl = findViewById(R.id.clothingThirdProductColourLbl);
        this.clothingThirdProductColourMenu = findViewById(R.id.clothingThirdProductColourMenu);
        this.clothingThirdProductCostLbl = findViewById(R.id.clothingThirdProductCost);

        this.clothingThirdProductQuantityLbl = findViewById(R.id.clothingThirdProductQuantityLbl);
        this.clothingThirdProductQuantityMenu = findViewById(R.id.clothingThirdProductQuantityMenu);

        this.clothingThirdProductSizeLbl = findViewById(R.id.clothingThirdProductSizeLbl);
        this.clothingThirdProductSizeMenu = findViewById(R.id.clothingThirdProductSizeMenu);
        this.clothingThirdAddToBasketBtn = findViewById(R.id.clothingThirdProductAddToBasketBtn);

        this.clothingFourthProductTxt = findViewById(R.id.clothingFourthProductTxt);
        this.clothingFourthProductCostLbl = findViewById(R.id.clothingFourthProductCost);
        this.clothingFourthProductImageView = findViewById(R.id.clothingFourthProductImg);

        this.clothingFourthProductColourLbl = findViewById(R.id.clothingFourthProductColourLbl);
        this.clothingFourthProductColourMenu = findViewById(R.id.clothingFourthProductColourMenu);

        this.clothingFourthProductSizeLbl = findViewById(R.id.clothingFourthProductSizeLbl);
        this.clothingFourthProductSizeMenu = findViewById(R.id.clothingFourthProductSizeMenu);

        this.clothingFourthProductQuantityLbl = findViewById(R.id.clothingFourthProductQuantityLbl);
        this.clothingFourthProductQuantityMenu = findViewById(R.id.clothingFourthProductQuantityMenu);

        this.clothingFourthProductAddToBasketBtn = findViewById(R.id.clothingFourthProductAddToBasketBtn);
        this.listOfClothingColoursOne = new ArrayList<>(); // Creates a new array list of colours
        
        this.listOfClothingSizesOne = new ArrayList<>(); // Creates a new array list of clothing sizes.
        this.listOfClothingQuantitiesOne = new ArrayList<>();
        
        this.listOfClothingColoursTwo = new ArrayList<>();
        this.listOfClothingSizesTwo = new ArrayList<>();
        
        this.listOfClothingQuantitiesTwo = new ArrayList<>();
        this.listOfProductsToAddToBasket = new HashMap<>();

        addToColoursList(); // Routine that adds the colours to the array list
        addToSizesList(); // Adds the sizes to the array list

        addToQuantitiesListOne();
        addToQuantitiesListTwo();

        // Set-up Adapters.
        this.coloursAdapter = new ColourArrayAdapter(ClothingActivityTwo.this, listOfClothingColoursOne);
        coloursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Set the drop down view for the colours.

        clothingThirdProductColourMenu.setAdapter(coloursAdapter);
        clothingThirdProductColourMenu.setOnItemSelectedListener(ClothingActivityTwo.this);

        this.sizesAdapter = new SizeArrayAdapter(ClothingActivityTwo.this, listOfClothingSizesOne);
        sizesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clothingThirdProductSizeMenu.setAdapter(sizesAdapter);
        clothingThirdProductSizeMenu.setOnItemSelectedListener(this);

        this.quantitiesAdapter = new QuantitiesArrayAdapter(ClothingActivityTwo.this, listOfClothingQuantitiesOne);
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clothingThirdProductQuantityMenu.setAdapter(quantitiesAdapter);
        clothingThirdProductQuantityMenu.setOnItemSelectedListener(this);

        this.coloursAdapter = new ColourArrayAdapter(ClothingActivityTwo.this, listOfClothingColoursTwo);
        coloursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clothingFourthProductColourMenu.setAdapter(coloursAdapter);
        clothingFourthProductColourMenu.setOnItemSelectedListener(ClothingActivityTwo.this);

        this.sizesAdapter = new SizeArrayAdapter(ClothingActivityTwo.this, listOfClothingSizesTwo);
        sizesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clothingFourthProductSizeMenu.setAdapter(sizesAdapter);
        clothingFourthProductSizeMenu.setOnItemSelectedListener(this);

        this.quantitiesAdapter = new QuantitiesArrayAdapter(ClothingActivityTwo.this, listOfClothingQuantitiesTwo);
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clothingFourthProductQuantityMenu.setAdapter(quantitiesAdapter);
        clothingFourthProductQuantityMenu.setOnItemSelectedListener(this); // Add an on click listener for the clothing product.

        this.clothingThirdAddToBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View thirdBtn) {
                if (thirdBtn.getId() == R.id.clothingThirdProductAddToBasketBtn) { // If the third button is clicked

                    if (clothingThirdProductColourMenu.getSelectedItemPosition() == 0 || clothingThirdProductSizeMenu.getSelectedItemPosition() == 0 || clothingThirdProductQuantityMenu.getSelectedItemPosition() == 0) { // If no colour, size and quantity is chosen
                        AlertDialog.Builder error = new AlertDialog.Builder(ClothingActivityTwo.this) // Create an alert dialogue to display an error.
                                .setTitle(R.string.error) // Sets the title of the alert dialogue.
                                .setMessage(R.string.errorMsg) // Shows the message.

                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (dialog != null) {
                                            dialog.dismiss();
                                        }
                                    }
                                });

                        error.show(); // Show the error.
                        error.setCancelable(true);
                    } 
                    
                    else {
                        clothingAddToBasketThree(); // Otherwise add the product to the basket.
                    }
                }
            }
        });

        this.clothingFourthProductAddToBasketBtn.setOnClickListener(new View.OnClickListener() { // Adds an on click listener for the fourth product.
            @Override
            public void onClick(View fourthBtn) {
                
                if (fourthBtn.getId() == R.id.clothingFourthProductAddToBasketBtn) {

                    if (clothingFourthProductColourMenu.getSelectedItemPosition() == 0 || clothingFourthProductSizeMenu.getSelectedItemPosition() == 0 || clothingFourthProductQuantityMenu.getSelectedItemPosition() == 0) {
                        AlertDialog.Builder error = new AlertDialog.Builder(ClothingActivityTwo.this)
                                .setTitle(R.string.error) // Set the title of the dialogue
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
                        clothingAddToBasketFour();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Add the toolbar menu
        // Inflate the activities menu
        MenuInflater activityInflater = getMenuInflater(); // Get the activity inflater
        activityInflater.inflate(R.menu.homepagemenu, menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.basket_action_button, menu);

        View view = menu.findItem(R.id.cart_menu).getActionView(); // Get the action view from the cart.
        cartIcon = view.findViewById(R.id.cart_icon);

        cartIcon.setOnClickListener(new View.OnClickListener() { // Add an action listener to the cart icon
            @Override
            public void onClick(View v) {

                Intent basketIntent = new Intent(ClothingActivityTwo.this, BasketActivity.class); // Create a basket intent
                basketIntent.putExtra("map", listOfProductsToAddToBasket); // Transit over the hash map data to the basket
                startActivity(basketIntent); // Start the intent
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) { // Routine that determines which item has been selected from the menu

        try {
            switch (item.getItemId()) {
                    
                case R.id.sportsAndOutdoorsCategory: // If the sports and outdoors activity is chosen
                    Intent sportsCategory = new Intent(ClothingActivityTwo.this, SportsAndOutdoorsActivity.class);
                    startActivity(sportsCategory); // Start that activity
                    break;

                case R.id.techCategory: // When the tech category is chosen
                    Intent techActivity = new Intent(ClothingActivityTwo.this, TechActivity.class); // Create a new intent
                    startActivity(techActivity); // Start that activity
                    break;

                case R.id.clothingCategory:
                    Intent clothingActivity = new Intent(ClothingActivityTwo.this, ClothingCategory.class);
                    startActivity(clothingActivity);
                    break;

                case R.id.diyCategory:
                    Intent diyActivity = new Intent(ClothingActivityTwo.this, DIYActivity.class);
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

    private boolean addToColoursList() { // Routine that adds to the colours array list

        Context context = getApplicationContext();

        String[] clothingResources = new String[]{context.getString(R.string.colourPrompt), context.getString(R.string.salmonPink), context.getString(R.string.skyBlue),
                context.getString(R.string.rubyRed), context.getString(R.string.cityGray)}; // Creates a string array of resources to add

        TechActivity.Colours[] colours = new TechActivity.Colours[]{new TechActivity.Colours(0, clothingResources[0]), new TechActivity.Colours(1, clothingResources[1]), new TechActivity.Colours(2, clothingResources[2]),
                new TechActivity.Colours(3, clothingResources[3]), new TechActivity.Colours(4, clothingResources[4])};

        for (TechActivity.Colours theColours : colours) { // For every colour in the colours array
            listOfClothingColoursOne.add(theColours); // Add it to the array list
            listOfClothingColoursTwo.add(theColours);

            coloursAdded = true; // Colours added is now true.
        }

        return true;
    }

    private boolean addToSizesList() { // Adds to the sizes list.

        boolean sizes_added = false;
        Context context = getApplicationContext();

        String[] clothingSizes = new String[]{context.getString(R.string.sizePrompt), context.getString(R.string.smallSize), context.getString(R.string.mediumSize)
                , context.getString(R.string.largeSize), context.getString(R.string.extraLargeSize)};

        Size[] sizes = new Size[]{new Size(0, clothingSizes[0]), new Size(1, clothingSizes[1]), new Size(2, clothingSizes[2]), new Size(3, clothingSizes[3]), new Size(4, clothingSizes[4])};

        for (Size theSizes : sizes) { // For every size
            listOfClothingSizesOne.add(theSizes); // Add it to the array list
            listOfClothingSizesTwo.add(theSizes);
            sizes_added = true;
        }

        return true; // Return true
    }

    private boolean addToQuantitiesListOne() { // Adds to the quantities array list

        boolean quantitiesAdded = false;
        Context context = getApplicationContext();

        String[] quantitiesResources = new String[]{context.getString(R.string.zero), context.getString(R.string.one), context.getString(R.string.two),
                context.getString(R.string.three), context.getString(R.string.four), context.getString(R.string.five)};

        TechActivity.Quantities[] quantities = new TechActivity.Quantities[]{new TechActivity.Quantities(quantitiesResources[0]), new TechActivity.Quantities(quantitiesResources[1]), new TechActivity.Quantities(quantitiesResources[2]),
                new TechActivity.Quantities(quantitiesResources[3]), new TechActivity.Quantities(quantitiesResources[4]), new TechActivity.Quantities(quantitiesResources[5])};

        for (TechActivity.Quantities theQuantities : quantities) {
            listOfClothingQuantitiesOne.add(theQuantities); // Add the quantities to the first array list
            quantitiesAdded = true;
        }

        return true;
    }

    private boolean addToQuantitiesListTwo() {

        boolean quantitiesAdded = false;
        Context context = getApplicationContext();

        String[] quantitiesResources = new String[]{context.getString(R.string.zero), context.getString(R.string.one), context.getString(R.string.two),
                context.getString(R.string.three), context.getString(R.string.four), context.getString(R.string.five)};

        TechActivity.Quantities[] quantities = new TechActivity.Quantities[]{new TechActivity.Quantities(quantitiesResources[0]), new TechActivity.Quantities(quantitiesResources[1]), new TechActivity.Quantities(quantitiesResources[2]),
                new TechActivity.Quantities(quantitiesResources[3]), new TechActivity.Quantities(quantitiesResources[4]), new TechActivity.Quantities(quantitiesResources[5])};

        for (TechActivity.Quantities theQuantities : quantities) {
            listOfClothingQuantitiesTwo.add(theQuantities); // Add the quantities to the first array list
            quantitiesAdded = true;
        }

        return true;
    }

    private boolean clothingAddToBasketThree() { // Adds the third clothing product to the basket
        Context context = getApplicationContext();
        String[] temp = new String[]{context.getString(R.string.addingBasket), context.getString(R.string.wait)};

        final ProgressDialog dialog = new ProgressDialog(ClothingActivityTwo.this); // Spinning progress dialog
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
        Products clothingThirdProduct = new Products(current_product_id, clothingThirdProductTxt.getText().toString(), clothingThirdProductColourMenu.getSelectedItem().toString(), (int) clothingThirdProductQuantityMenu.getSelectedItemId(), clothingThirdProductCostLbl.getText().toString(), clothingThirdProductSizeMenu.getSelectedItem().toString());
        listOfProductsToAddToBasket.put(current_product_id, clothingThirdProduct);

        return true;
    }

    private boolean clothingAddToBasketFour() {

        Context context = getApplicationContext();
        String[] temp = new String[]{context.getString(R.string.addingBasket), context.getString(R.string.wait)};

        final ProgressDialog dialog = new ProgressDialog(ClothingActivityTwo.this); // Spinning progress dialog
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
        Products clothingThirdProduct = new Products(current_product_id++, clothingFourthProductTxt.getText().toString(), clothingFourthProductColourMenu.getSelectedItem().toString(), (int) clothingFourthProductQuantityMenu.getSelectedItemId(), clothingFourthProductCostLbl.getText().toString(), clothingThirdProductSizeMenu.getSelectedItem().toString());
        listOfProductsToAddToBasket.put(current_product_id++, clothingThirdProduct);

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { // Routine that will determine which item has been selected in the spinner.
        boolean valueAppended = false;

        int[] indexes = new int[]{0, 1, 2, 3, 4}; // An array of indexes

        Context context = getApplicationContext();
        String[] productResources = new String[]{context.getString(R.string.productCost)};

        if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesOne.get(indexes[0]))) { // If the first index is chosen in the drop-down list of quantities.
            clothingThirdProductCostLbl.setText(null); // Empty the text by default.
            clothingThirdProductCostLbl.append(productResources[0] + clothingThirdProductCosts[0]); // Append the product cost. Product Cost £: 0.00
            valueAppended = true; // Value has been appended.

        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesOne.get(indexes[1]))) {
            clothingThirdProductCostLbl.setText(null);
            clothingThirdProductCostLbl.append(productResources[0] + clothingThirdProductCosts[1]);
            valueAppended = true; // Value is appended

        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesOne.get(indexes[2]))) {
            clothingThirdProductCostLbl.setText(null);
            clothingThirdProductCostLbl.append(productResources[0] + clothingThirdProductCosts[2]);
            valueAppended = true;
        }
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesOne.get(indexes[3]))) {
            clothingThirdProductCostLbl.setText(null);
            clothingThirdProductCostLbl.append(productResources[0] + clothingThirdProductCosts[3]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesOne.get(indexes[4]))) {
            clothingThirdProductCostLbl.setText(null);
            clothingThirdProductCostLbl.append(productResources[0] + clothingThirdProductCosts[4]);
            valueAppended = true;
        }

        if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesTwo.get(indexes[0]))) {
            clothingFourthProductCostLbl.setText(null);
            clothingFourthProductCostLbl.append(productResources[0] + clothingFourthProductCosts[0]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesTwo.get(indexes[1]))) {
            clothingFourthProductCostLbl.setText(null);
            clothingFourthProductCostLbl.append(productResources[0] + clothingFourthProductCosts[1]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesTwo.get(indexes[2]))) {
            clothingFourthProductCostLbl.setText(null);
            clothingFourthProductCostLbl.append(productResources[0] + clothingFourthProductCosts[2]);
            valueAppended = true;
        }
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesTwo.get(indexes[3]))) {
            clothingFourthProductCostLbl.setText(null);
            clothingFourthProductCostLbl.append(productResources[0] + clothingFourthProductCosts[3]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesTwo.get(indexes[4]))) {
            clothingFourthProductCostLbl.setText(null);
            clothingFourthProductCostLbl.append(productResources[0] + clothingFourthProductCosts[4]);
            valueAppended = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { // Routine that determines if nothing is selected.

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
