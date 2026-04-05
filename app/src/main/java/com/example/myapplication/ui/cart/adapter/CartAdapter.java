package com.example.myapplication.ui.cart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.data.model.CartItem;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private OnCartItemClickListener listener;

    public interface OnCartItemClickListener {
        void onAddClick(CartItem item);    // 加号
        void onMinusClick(CartItem item);  // 减号
    }

    public CartAdapter(List<CartItem> cartItems, OnCartItemClickListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return cartItems == null ? 0 : cartItems.size();
    }

    public void updateData(List<CartItem> newItems) {
        this.cartItems = newItems;
        notifyDataSetChanged();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private TextView priceText;
        private TextView remarkText;
        private TextView quantityText;
        private ImageView imageView;
        private Button addButton;
        private Button minusButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.text_cart_name);
            priceText = itemView.findViewById(R.id.text_cart_price);
            remarkText = itemView.findViewById(R.id.text_cart_remark);
            quantityText = itemView.findViewById(R.id.text_cart_quantity);
            imageView = itemView.findViewById(R.id.image_cart);
            addButton = itemView.findViewById(R.id.button_cart_add);
            minusButton = itemView.findViewById(R.id.button_cart_minus);
        }

        public void bind(CartItem item, OnCartItemClickListener listener) {
            nameText.setText(item.getMenuItem().getName());
            priceText.setText("$" + item.getMenuItem().getPrice());
            quantityText.setText(String.valueOf(item.getQuantity()));
            Glide.with(itemView.getContext())
                    .load(item.getMenuItem().getImageUrl())
                    .placeholder(R.color.black)
                    .error(R.color.black)
                    .centerCrop()
                    .into(imageView);

            // 备注为空就隐藏
            if (item.getRemark() == null || item.getRemark().isEmpty()) {
                remarkText.setVisibility(View.GONE);
            } else {
                remarkText.setVisibility(View.VISIBLE);
                remarkText.setText("remark：" + item.getRemark());
            }

            // 加号
            addButton.setOnClickListener(v -> {
                if (listener != null) listener.onAddClick(item);
            });

            // 减号
            minusButton.setOnClickListener(v -> {
                if (listener != null) listener.onMinusClick(item);
            });
        }
    }
}