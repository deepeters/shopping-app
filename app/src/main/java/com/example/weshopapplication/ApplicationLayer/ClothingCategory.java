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


public class ClothingCategory extends AppCompatActivity implements AdapterView.OnItemSelectedListener { // Class implements the item selected listener methods from the adapter view class.
    private int current_product_id = 1; // The current product ID
    private TextView clothingFirstProductTxt; // The text view for the first product
    
    private ImageView clothingFirstProductImg; // The Image for the first product
    private TextView clothingFirstProductCostLbl;

    private TextView clothingFirstProductColourLbl;
    private Spinner clothingFirstProductColourMenu; // The colour menu for the first clothing product.

    private TextView clothingFirstProductSizeLbl; // The size label of the first clothing product.
    private Spinner clothingFirstProductSizeMenu; // The size menu of the clothing activity.

    private TextView clothingFirstProductQuantityLbl;
    private Spinner clothingFirstProductQuantityMenu;

    private Button clothingFirstProductAddToBasketBtn; // The first clothing product add to basket button.

    private TextView clothingSecondProductTxt;
    private ImageView clothingSecondProductImg; // The image for the second clothing product.
    private TextView clothingSecondProductCostLbl;

    private TextView clothingSecondProductColourLbl;
    private Spinner clothingSecondProductColourMenu;

    private TextView clothingSecondProductSizeLbl; // The label for the second clothing product.
    private Spinner clothingSecondProductSizeMenu;

    private TextView clothingSecondProductQuantityLbl;
    private Spinner clothingSecondProductQuantityMenu; // Quantity menu for the second clothing product

    private Button clothingSecondProductAddToBasketBtn; // Button for adding the second product to the basket

    private double[] clothingProductOneCosts = new double[]{0.00, 25.00, 50.00, 150.00, 450.00, 1350.00}; // Clothing product one costs.
    private double[] clothingProductTwoCosts = new double[]{0.00, 30.00, 60.00, 120.00, 240.00, 480.00}; // Clothing product 2 costs.

    private QuantitiesArrayAdapter quantitiesAdapter; // Quantities Array adapter to store custom instances of Quantity.
    private ColourArrayAdapter coloursAdapter;
    private SizeArrayAdapter sizeArrayAdapter;

    // Array List of colours, clothing and sizes.
    private ArrayList<TechActivity.Colours> listOfClothingColoursOne = null;
    private ArrayList<Size> listOfClothingSizesOne = null; // An Array List of clothing sizes, initially null (empty)
    private ArrayList<TechActivity.Quantities> listOfClothingQuantitiesOne = null;

    private ArrayList<TechActivity.Colours> listOfClothingColoursTwo = null; // An Array list of clothing colours two
    private ArrayList<Size> listOfClothingSizesTwo = null;
    private ArrayList<TechActivity.Quantities> listOfClothingQuantitiesTwo = null;

    private ImageView cartIcon; // The cart icon that represents the basket
    private transient boolean coloursAdded = false; // Determines if the colours have been added.

    private Button nextPageBtn; // The next page button variable.
    private HashMap<Integer, Products> listOfProductsToAddToBasket; // A Hash Map that stores a list of products to add to the basket.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_category);

        this.clothingFirstProductTxt = findViewById(R.id.clothingFirstProductTxt);
        this.clothingFirstProductImg = findViewById(R.id.clothingFirstProductImg);
        this.clothingFirstProductCostLbl = findViewById(R.id.clothingFirstProductCostLbl);

        this.clothingFirstProductColourLbl = findViewById(R.id.clothingFirstProductColourLbl);
        this.clothingFirstProductColourMenu = findViewById(R.id.clothingFirstProductColourMenu);

        this.clothingFirstProductSizeLbl = findViewById(R.id.clothingFirstProductSizeLbl);
        this.clothingFirstProductSizeMenu = findViewById(R.id.clothingFirstProductSizeMenu);
        
        this.clothingFirstProductQuantityLbl = findViewById(R.id.clothingFirstProductQuantityLbl);
        this.clothingFirstProductQuantityMenu = findViewById(R.id.clothingFirstProductQuantityMenu);
        
        this.clothingFirstProductAddToBasketBtn = findViewById(R.id.clothingFirstProductAddToBasketBtn);
        this.clothingSecondProductTxt = findViewById(R.id.clothingSecondProductTxt);
        
        this.clothingSecondProductImg = findViewById(R.id.clothingSecondProductImg);
        this.clothingSecondProductCostLbl = findViewById(R.id.clothingSecondProductCostLbl);

        this.clothingSecondProductColourLbl = findViewById(R.id.clothingSecondProductColourLbl);
        this.clothingSecondProductColourMenu = findViewById(R.id.clothingSecondProductColourMenu);

        this.clothingSecondProductSizeLbl = findViewById(R.id.clothingSecondProductSizeLbl);
        this.clothingSecondProductSizeMenu = findViewById(R.id.clothingSecondProductSizeMenu);

        this.clothingSecondProductQuantityLbl = findViewById(R.id.clothingSecondProductQuantityLbl);
        this.clothingSecondProductQuantityMenu = findViewById(R.id.clothingSecondProductQuantityMenu);
        
        this.clothingSecondProductAddToBasketBtn = findViewById(R.id.clothingSecondProductAddToBasketBtn);
        this.nextPageBtn = findViewById(R.id.clothingNextPageBtn);

        this.listOfClothingColoursOne = new ArrayList<>(); // Creates a new array list of colours.
        this.listOfClothingSizesOne = new ArrayList<>(); // A new array list of clothing sizes.
        
        this.listOfClothingQuantitiesOne = new ArrayList<>();
        this.listOfClothingColoursTwo = new ArrayList<>();
        
        this.listOfClothingSizesTwo = new ArrayList<>();
        this.listOfClothingQuantitiesTwo = new ArrayList<>();

        this.listOfProductsToAddToBasket = new HashMap<Integer, Products>(); // Creates a new hash map.

        addToColoursList(); // Method invoked to add to the colours list
        addToSizesList();

        addToQuantitiesList(); // Invoke routine to add to the quantities list.
        addToQuantitiesListTwo();

        // Set-up Adapters.
        this.coloursAdapter = new ColourArrayAdapter(ClothingCategory.this, listOfClothingColoursOne);
        coloursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clothingFirstProductColourMenu.setAdapter(coloursAdapter);
        clothingFirstProductColourMenu.setOnItemSelectedListener(ClothingCategory.this);

        this.sizeArrayAdapter = new SizeArrayAdapter(ClothingCategory.this, listOfClothingSizesOne);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clothingFirstProductSizeMenu.setAdapter(sizeArrayAdapter);
        clothingFirstProductSizeMenu.setOnItemSelectedListener(this);

        this.quantitiesAdapter = new QuantitiesArrayAdapter(ClothingCategory.this, listOfClothingQuantitiesOne);
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clothingFirstProductQuantityMenu.setAdapter(quantitiesAdapter);
        clothingFirstProductQuantityMenu.setOnItemSelectedListener(this);

        this.coloursAdapter = new ColourArrayAdapter(ClothingCategory.this, listOfClothingColoursTwo);
        coloursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clothingSecondProductColourMenu.setAdapter(coloursAdapter);
        clothingSecondProductColourMenu.setOnItemSelectedListener(ClothingCategory.this);

        this.sizeArrayAdapter = new SizeArrayAdapter(ClothingCategory.this, listOfClothingSizesTwo);
        sizeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clothingSecondProductSizeMenu.setAdapter(sizeArrayAdapter);
        clothingSecondProductSizeMenu.setOnItemSelectedListener(this);

        this.quantitiesAdapter = new QuantitiesArrayAdapter(ClothingCategory.this, listOfClothingQuantitiesTwo);
        quantitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clothingSecondProductQuantityMenu.setAdapter(quantitiesAdapter);
        clothingSecondProductQuantityMenu.setOnItemSelectedListener(this);

        this.clothingFirstProductAddToBasketBtn.setOnClickListener(new View.OnClickListener() { // Add action listener to the first clothing add to product button
            @Override

            public void onClick(View firstButton) {
                if (firstButton.getId() == R.id.clothingFirstProductAddToBasketBtn) { // If the button chosen is the first product add to basket button

                    if (clothingFirstProductColourMenu.getSelectedItemPosition() == 0 || clothingFirstProductSizeMenu.getSelectedItemPosition() == 0 || clothingFirstProductQuantityMenu.getSelectedItemPosition() == 0) {
                        AlertDialog.Builder error = new AlertDialog.Builder(ClothingCategory.this)
                                .setTitle(R.string.error) // Set the title of the error.
                                .setMessage(R.string.errorMsg) // Set the error message.

                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        
                                        if (dialog != null) { // If there is a dialog
                                            dialog.dismiss(); // Dismiss it.
                                        }
                                    }
                                });

                        error.show(); // Show the error.
                        error.setCancelable(true);
                    } 
                    
                    else {
                        clothingAddToBasketOne(); // Otherwise add the product to the basket.
                    }
                }
            }
        });

        this.clothingSecondProductAddToBasketBtn.setOnClickListener(new View.OnClickListener() { // Adds an action listener to the second product add to basket button.
            @Override
            public void onClick(View secondButton) {
                if (secondButton.getId() == R.id.clothingSecondProductAddToBasketBtn) {

                    if (clothingSecondProductColourMenu.getSelectedItemPosition() == 0 || clothingSecondProductSizeMenu.getSelectedItemPosition() == 0 || clothingSecondProductQuantityMenu.getSelectedItemPosition() == 0) {
                        AlertDialog.Builder error = new AlertDialog.Builder(ClothingCategory.this)
                                .setTitle(R.string.error)
                                .setMessage(R.string.errorMsg)
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        
                                        if (dialog != null) { // If there is a dialogue
                                            dialog.dismiss(); // Dismiss it.
                                        }
                                    }
                                });

                        error.show();
                        error.setCancelable(true);
                    } 
                    
                    else {
                        clothingAddToBasketTwo(); // Otherwise if an option is selected, add the product to the basket.
                    }
                }
            }
        });

        this.nextPageBtn.setOnClickListener(new View.OnClickListener() { // Adds an action listener for the next page button.
            @Override

            public void onClick(View v) {
                
                try {
                    Intent intent = new Intent(ClothingCategory.this, ClothingActivityTwo.class);
                    startActivity(intent);
                } 
                
                catch (ActivityNotFoundException exc) { // Catch the error if the activity does not exist.
                    Log.d(String.valueOf(R.string.error), exc.toString());
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        boolean valueAppended = false; // Boolean value that stores true or false if the value has been appended.

        int[] indexes = new int[]{0, 1, 2, 3, 4}; // An Array of indexes from 0-4

        Context context = getApplicationContext();
        String[] productResources = new String[]{context.getString(R.string.productCost)};

        if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesOne.get(indexes[0]))) { // If the first index in the drop-down menu option is chosen
            clothingFirstProductCostLbl.setText(null); // Flush the text and set it to null.
            clothingFirstProductCostLbl.append(productResources[0] + clothingProductOneCosts[0]); // Append the cost to the field.
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesOne.get(indexes[1]))) {
            clothingFirstProductCostLbl.setText(null);
            clothingFirstProductCostLbl.append(productResources[0] + clothingProductOneCosts[1]);
            valueAppended = true; // Value is appended
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesOne.get(indexes[2]))) {
            clothingFirstProductCostLbl.setText(null);
            clothingFirstProductCostLbl.append(productResources[0] + clothingProductOneCosts[2]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesOne.get(indexes[3]))) {
            clothingFirstProductCostLbl.setText(null);
            clothingFirstProductCostLbl.append(productResources[0] + clothingProductOneCosts[3]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesOne.get(indexes[4]))) {
            clothingFirstProductCostLbl.setText(null);
            clothingFirstProductCostLbl.append(productResources[0] + clothingProductOneCosts[4]);
            valueAppended = true;
        }


        if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesTwo.get(indexes[0]))) {
            clothingSecondProductCostLbl.setText(null);
            clothingSecondProductCostLbl.append(productResources[0] + clothingProductTwoCosts[0]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesTwo.get(indexes[1]))) {
            clothingSecondProductCostLbl.setText(null);
            clothingSecondProductCostLbl.append(productResources[0] + clothingProductTwoCosts[1]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesTwo.get(indexes[2]))) {
            clothingSecondProductCostLbl.setText(null);
            clothingSecondProductCostLbl.append(productResources[0] + clothingProductTwoCosts[2]);
            valueAppended = true;
        }
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesTwo.get(indexes[3]))) {
            clothingSecondProductCostLbl.setText(null);
            clothingSecondProductCostLbl.append(productResources[0] + clothingProductTwoCosts[3]);
            valueAppended = true;
        } 
        
        else if (parent.getItemAtPosition(position).equals(listOfClothingQuantitiesTwo.get(indexes[4]))) {
            clothingSecondProductCostLbl.setText(null);
            clothingSecondProductCostLbl.append(productResources[0] + clothingProductTwoCosts[4]);
            valueAppended = true;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private boolean addToColoursList() { // Routine 
        Context context = getApplicationContext();

        String[] clothingResources = new String[]{context.getString(R.string.colourPrompt), context.getString(R.string.brown), context.getString(R.string.navyBlue),
                context.getString(R.string.darkRed), context.getString(R.string.checkeredBlack)};

        TechActivity.Colours[] colours = new TechActivity.Colours[]{new TechActivity.Colours(0, clothingResources[0]), new TechActivity.Colours(1, clothingResources[1]), new TechActivity.Colours(2, clothingResources[2]),
                new TechActivity.Colours(3, clothingResources[3]), new TechActivity.Colours(4, clothingResources[4])};

        for (TechActivity.Colours theColours : colours) { // For every colour in the object array

            listOfClothingColoursOne.add(theColours);
            listOfClothingColoursTwo.add(theColours);

            coloursAdded = true; // Colours added variable is true.
        }

        return true;
    }

    private boolean addToSizesList() {
        boolean sizes_added = false;
        Context context = getApplicationContext();

        String[] clothingSizes = new String[]{context.getString(R.string.sizePrompt), context.getString(R.string.thirtyFourRegular), context.getString(R.string.thirtyEightRegular)
                , context.getString(R.string.forty), context.getString(R.string.fortyTwo)};

        Size[] sizes = new Size[]{new Size(0, clothingSizes[0]), new Size(1, clothingSizes[1]), new Size(2, clothingSizes[2]), new Size(3, clothingSizes[3]), new Size(4, clothingSizes[4])};

        for (Size theSizes : sizes) {
            listOfClothingSizesOne.add(theSizes);
            listOfClothingSizesTwo.add(theSizes);
            sizes_added = true;

        }

        return true;
    }

    private boolean addToQuantitiesList() { // Routine to add to quantities list.
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

    private boolean addToQuantitiesListTwo() { // Adds to the quantitites list.
        boolean quantitiesAdded = false;
        Context context = getApplicationContext();

        String[] quantitiesResources = new String[]{context.getString(R.string.zero), context.getString(R.string.one), context.getString(R.string.two),
                context.getString(R.string.three), context.getString(R.string.four), context.getString(R.string.five)};

        TechActivity.Quantities[] quantities = new TechActivity.Quantities[]{new TechActivity.Quantities(quantitiesResources[0]), new TechActivity.Quantities(quantitiesResources[1]), new TechActivity.Quantities(quantitiesResources[2]),
                new TechActivity.Quantities(quantitiesResources[3]), new TechActivity.Quantities(quantitiesResources[4]), new TechActivity.Quantities(quantitiesResources[5])};

        for (TechActivity.Quantities theQuantities : quantities) { // For every quantity in the quantities.
            listOfClothingQuantitiesTwo.add(theQuantities); // Add the quantities to the first array list
            quantitiesAdded = true;
        }

        return true;
    }

    private boolean clothingAddToBasketOne() {
        Context context = getApplicationContext();
        String[] temp = new String[]{context.getString(R.string.addingBasket), context.getString(R.string.wait)};

        final ProgressDialog dialog = new ProgressDialog(ClothingCategory.this); // Spinning progress dialog
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
        Products clothingFirstProduct = new Products(current_product_id, clothingFirstProductTxt.getText().toString(), clothingFirstProductColourMenu.getSelectedItem().toString(), (int) clothingFirstProductQuantityMenu.getSelectedItemId(), clothingFirstProductCostLbl.getText().toString(), clothingFirstProductSizeMenu.getSelectedItem().toString());
        listOfProductsToAddToBasket.put(current_product_id, clothingFirstProduct);

        return true;
    }

    private boolean clothingAddToBasketTwo() { // Adds the second clothing product to the basket.

        Context context = getApplicationContext();
        String[] temp = new String[]{context.getString(R.string.addingBasket), context.getString(R.string.wait)};

        final ProgressDialog dialog = new ProgressDialog(ClothingCategory.this); // Spinning progress dialog
        dialog.setTitle(temp[0]); // Set the title of the dialog
        dialog.setMessage(temp[1]);

        dialog.setCancelable(false);

        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Sets the style of the progress bar

        new Thread(new Runnable() { // Create a new thread

            @Override
            public void run() { // Run the thread.
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
        Products clothingSecondProduct = new Products(current_product_id++, clothingSecondProductTxt.getText().toString(), clothingSecondProductColourMenu.getSelectedItem().toString(), (int) clothingSecondProductQuantityMenu.getSelectedItemId(), clothingSecondProductCostLbl.getText().toString(), clothingSecondProductSizeMenu.getSelectedItem().toString());
        listOfProductsToAddToBasket.put(current_product_id++, clothingSecondProduct); // Add the product instance to the hash map

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Add the toolbar menu
        // Inflate the activities menu
        MenuInflater activityInflater = getMenuInflater(); // Get the activity inflater
        activityInflater.inflate(R.menu.homepagemenu, menu);

        MenuInflater menuInflater = getMenuInflater(); // Get the menu inflater to inflate the menu.
        menuInflater.inflate(R.menu.basket_action_button, menu); // Inflates the menu.

        View view = menu.findItem(R.id.cart_menu).getActionView(); // Get the action view for the cart menu

        cartIcon = view.findViewById(R.id.cart_icon);

        cartIcon.setOnClickListener(new View.OnClickListener() { // Add a listener to the cart icon when clicked
            @Override
            public void onClick(View v) {
                Intent basketIntent = new Intent(ClothingCategory.this, BasketActivity.class); // Create a basket intent
                basketIntent.putExtra("map", listOfProductsToAddToBasket); // Transit over the hash map data to the basket
                startActivity(basketIntent); // Start the intent.
            }
        });

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) { // Routine that determines which menu item has been selected.

        try {
            switch (item.getItemId()) {
                case R.id.sportsAndOutdoorsCategory: // If the sports and outdoors category is chosen
                    Intent sportsCategory = new Intent(ClothingCategory.this, SportsAndOutdoorsActivity.class);
                    startActivity(sportsCategory); // Start that activity.

                    break;

                case R.id.techCategory: // If the tech activity is chosen
                    Intent techActivity = new Intent(ClothingCategory.this, TechActivity.class);
                    startActivity(techActivity); // Start that activity
                    break;

                case R.id.clothingCategory:
                    Intent clothingActivity = new Intent(ClothingCategory.this, ClothingCategory.class);
                    startActivity(clothingActivity);
                    break;

                case R.id.diyCategory: // If the diy category is chosen
                    Intent diyActivity = new Intent(ClothingCategory.this, DIYActivity.class);
                    startActivity(diyActivity); // Start the DIY Activity.
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
