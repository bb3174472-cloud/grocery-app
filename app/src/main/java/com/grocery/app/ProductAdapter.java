package com.grocery.app;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {
    private List<Product> items;
    private final OnItemClick listener;
    public interface OnItemClick { void onAdd(Product p); }
    public ProductAdapter(List<Product> items, OnItemClick listener) {
        this.items = items; this.listener = listener;
    }
    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }
    @Override public void onBindViewHolder(@NonNull VH h, int position) {
        Product p = items.get(position);
        h.tvName.setText(p.name);
        h.tvPrice.setText(p.price + " ر.ي / " + p.unit);
        if (listener != null) { h.btnAdd.setVisibility(View.VISIBLE); h.btnAdd.setOnClickListener(v -> listener.onAdd(p)); }
        else { h.btnAdd.setVisibility(View.GONE); }
    }
    @Override public int getItemCount() { return items.size(); }
    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice; Button btnAdd;
        VH(View v) { super(v); tvName = v.findViewById(R.id.tvName); tvPrice = v.findViewById(R.id.tvPrice); btnAdd = v.findViewById(R.id.btnAdd); }
    }
}