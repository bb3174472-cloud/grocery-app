package com.grocery.app;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    private List<Product> allProducts = new ArrayList<>();
    private List<Product> displayProducts = new ArrayList<>();
    private ProductAdapter adapter;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadProducts(); setupRecyclerView(); setupSearch();
        FloatingActionButton fabCart = findViewById(R.id.fabCart);
        fabCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
    }
    private void loadProducts() {
        try {
            InputStream is = getAssets().open("products.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer); is.close();
            allProducts = new Gson().fromJson(new String(buffer, "UTF-8"), new TypeToken<List<Product>>(){}.getType());
            displayProducts.addAll(allProducts);
        } catch (Exception e) { e.printStackTrace(); }
    }
    private void setupRecyclerView() {
        RecyclerView rv = findViewById(R.id.rvProducts);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(displayProducts, product -> {
            ArrayList<Product> cart = CartManager.loadCart(this);
            cart.add(product);
            CartManager.saveCart(this, cart);
            android.widget.Toast.makeText(this, "تمت الإضافة", android.widget.Toast.LENGTH_SHORT).show();
        });
        rv.setAdapter(adapter);
    }
    private void setupSearch() {
        EditText etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) { filter(s.toString()); }
        });
    }
    private void filter(String text) {
        displayProducts.clear();
        if (text.isEmpty()) { displayProducts.addAll(allProducts); }
        else { for (Product p : allProducts) { if (p.name.contains(text)) { displayProducts.add(p); } } }
        adapter.notifyDataSetChanged();
    }
}