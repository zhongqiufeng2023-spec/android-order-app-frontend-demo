package com.example.myapplication.ui.menu.adapter;


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
import com.example.myapplication.data.model.MenuItem;
import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<MenuItem> menuItems;
    private OnMenuItemClickListener listener;

    // 点击事件接口
    public interface OnMenuItemClickListener {
        void onAddClick(MenuItem item);    // 点击加号
        void onMinusClick(MenuItem item);  // 点击减号
        void onItemClick(MenuItem item);   // 点击菜品查看详情
    }

    public MenuAdapter(List<MenuItem> menuItems, OnMenuItemClickListener listener) {
        this.menuItems = menuItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem item = menuItems.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return menuItems == null ? 0 : menuItems.size();
    }

    // 更新数据
    public void updateData(List<MenuItem> newItems) {
        this.menuItems = newItems;
        notifyDataSetChanged();
    }

    // ViewHolder
    static class MenuViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private TextView priceText;
        private TextView descriptionText;
        private ImageView imageView;
        private TextView quantityText;
        private Button addButton;
        private Button minusButton;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.text_menu_name);
            priceText = itemView.findViewById(R.id.text_menu_price);
            descriptionText = itemView.findViewById(R.id.text_menu_description);

            imageView = itemView.findViewById(R.id.image_menu);

            quantityText = itemView.findViewById(R.id.text_quantity);
            addButton = itemView.findViewById(R.id.button_add);
            minusButton = itemView.findViewById(R.id.button_minus);
        }

        public void bind(MenuItem item, OnMenuItemClickListener listener) {
            nameText.setText(item.getName());
            priceText.setText("¥" + item.getPrice());
            descriptionText.setText(item.getDescription());
            quantityText.setText(String.valueOf(item.getQuantity()));

            Glide.with(itemView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.color.black)
                    .error(R.color.black)
                    .centerCrop()
                    .into(imageView);

            // 数量为0时隐藏减号和数量
            minusButton.setVisibility(item.getQuantity() > 0 ? View.VISIBLE : View.GONE);
            quantityText.setVisibility(item.getQuantity() > 0 ? View.VISIBLE : View.GONE);

            // 点击加号
            addButton.setOnClickListener(v -> {
                if (listener != null) listener.onAddClick(item);
            });

            // 点击减号
            minusButton.setOnClickListener(v -> {
                if (listener != null) listener.onMinusClick(item);
            });

            // 点击菜品查看详情
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onItemClick(item);
            });
        }
    }
}
