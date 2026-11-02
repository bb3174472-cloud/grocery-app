package com.grocery.app;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class CartActivity extends AppCompatActivity {
    private ArrayList<Product> cartItems;
    private TextView tvTotal;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartItems = CartManager.loadCart(this);
        tvTotal = findViewById(R.id.tvTotal);
        RecyclerView rv = findViewById(R.id.rvCart);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new ProductAdapter(cartItems, null));
        updateTotal();
        Button btnCheckout = findViewById(R.id.btnCheckout);
        btnCheckout.setOnClickListener(v -> {
            CartManager.saveCart(this, new ArrayList<>());
            android.widget.Toast.makeText(this, "تم إرسال الطلب بنجاح", android.widget.Toast.LENGTH_LONG).show();
            finish();
        });
    }
    private void updateTotal() {
        double total = 0;
        for (Product p : cartItems) total += p.price;
        tvTotal.setText("الإجمالي: " + total + " ر.ي");
    }
}