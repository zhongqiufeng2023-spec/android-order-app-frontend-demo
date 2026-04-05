package com.example.myapplication.ui.order.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.data.model.OrderItem;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder> {

    private List<OrderItem> orderItems;

    public OrderItemAdapter(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_detail, parent, false);
        return new OrderItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        OrderItem item = orderItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return orderItems == null ? 0 : orderItems.size();
    }

    static class OrderItemViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;
        private TextView quantityText;
        private TextView subtotalText;

        public OrderItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.text_detail_item_name);
            quantityText = itemView.findViewById(R.id.text_detail_item_quantity);
            subtotalText = itemView.findViewById(R.id.text_detail_item_subtotal);
        }

        public void bind(OrderItem item) {
            nameText.setText(item.getName());
            quantityText.setText("x" + item.getQuantity());
            subtotalText.setText("$" + item.getSubtotal());
        }
    }
}