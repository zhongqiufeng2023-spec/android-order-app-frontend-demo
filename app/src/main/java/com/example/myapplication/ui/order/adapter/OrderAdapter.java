package com.example.myapplication.ui.order.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.data.model.Order;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orders;
    private OnOrderClickListener listener;

    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }

    public OrderAdapter(List<Order> orders, OnOrderClickListener listener) {
        this.orders = orders;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order, listener);
    }

    @Override
    public int getItemCount() {
        return orders == null ? 0 : orders.size();
    }

    public void updateData(List<Order> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView orderIdText;
        private TextView orderStatusText;
        private TextView orderTimeText;
        private TextView orderPriceText;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdText = itemView.findViewById(R.id.text_order_id);
            orderStatusText = itemView.findViewById(R.id.text_order_status);
            orderTimeText = itemView.findViewById(R.id.text_order_time);
            orderPriceText = itemView.findViewById(R.id.text_order_price);
        }

        public void bind(Order order, OnOrderClickListener listener) {
            orderIdText.setText("订单号：" + order.getOrderId());
            orderStatusText.setText(order.getStatusText());
            orderTimeText.setText("下单时间：" + order.getOrderTime());
            orderPriceText.setText("$" + order.getTotalPrice());

            // 状态颜色
            if (order.getStatus() == Order.Status.PREPARING) {
                orderStatusText.setTextColor(0xFFFF6600);  // 橙色，制作中
            } else {
                orderStatusText.setTextColor(0xFF888888);  // 灰色，已完成
            }

            // 点击查看详情


        }
    }
}