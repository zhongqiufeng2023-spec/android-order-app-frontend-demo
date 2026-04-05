package com.example.myapplication.ui.order;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.data.model.Order;
import com.example.myapplication.ui.order.adapter.OrderAdapter;
import com.example.myapplication.ui.order.adapter.OrderItemAdapter;

import java.util.List;

public class OrderFragment extends Fragment {

    private OrderViewModel orderViewModel;
    private OrderAdapter orderAdapter;
    private TextView tabCurrent;
    private TextView tabHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderViewModel = new ViewModelProvider(this, new OrderViewModelFactory())
                .get(OrderViewModel.class);

        tabCurrent = view.findViewById(R.id.tab_current);
        tabHistory = view.findViewById(R.id.tab_history);

        // 初始化订单列表
        RecyclerView recyclerOrders = view.findViewById(R.id.recycler_orders);
        recyclerOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        orderAdapter = new OrderAdapter(null, order -> showOrderDetail(order));
        recyclerOrders.setAdapter(orderAdapter);

        // 左侧 Tab 点击
        tabCurrent.setOnClickListener(v -> {
            orderViewModel.selectCurrentOrder();
        });

        tabHistory.setOnClickListener(v -> {
            orderViewModel.selectHistoryOrders();
        });

        orderViewModel.getIsCurrentOrderSelected().observe(getViewLifecycleOwner(), isCurrent -> {
            if (isCurrent) {
                List<Order> orders = orderViewModel.getCurrentOrders().getValue();
                if (orders != null) orderAdapter.updateData(orders);
            } else {
                List<Order> orders = orderViewModel.getHistoryOrders().getValue();
                if (orders != null) orderAdapter.updateData(orders);
            }
            updateTabStyle(isCurrent);
        });
        orderViewModel.getCurrentOrders().observe(getViewLifecycleOwner(), orders -> {
            Boolean isCurrent = orderViewModel.getIsCurrentOrderSelected().getValue();
            if (isCurrent != null && isCurrent) {
                orderAdapter.updateData(orders);
            }
        });

        orderViewModel.getHistoryOrders().observe(getViewLifecycleOwner(), orders -> {
            Boolean isCurrent = orderViewModel.getIsCurrentOrderSelected().getValue();
            if (isCurrent != null && !isCurrent) {
                orderAdapter.updateData(orders);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        orderViewModel.refresh();
    }

    // 更新左侧分类样式
    private void updateTabStyle(boolean isCurrentSelected) {
        tabCurrent.setTextColor(isCurrentSelected ? 0xFFFF6600 : 0xFF333333);
        tabCurrent.setBackgroundColor(isCurrentSelected ? 0xFFFFFFFF : 0xFFF5F5F5);
        tabHistory.setTextColor(isCurrentSelected ? 0xFF333333 : 0xFFFF6600);
        tabHistory.setBackgroundColor(isCurrentSelected ? 0xFFF5F5F5 : 0xFFFFFFFF);
    }

    // 弹出订单详情
    private void showOrderDetail(Order order) {
        View dialogView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_order_detail, null);

        // 绑定控件
        TextView statusText = dialogView.findViewById(R.id.text_dialog_status);
        TextView orderIdText = dialogView.findViewById(R.id.text_dialog_order_id);
        TextView orderTimeText = dialogView.findViewById(R.id.text_dialog_order_time);
        TextView completeTimeText = dialogView.findViewById(R.id.text_dialog_complete_time);
        TextView totalText = dialogView.findViewById(R.id.text_dialog_total);
        RecyclerView recyclerItems = dialogView.findViewById(R.id.recycler_order_items);

        // 设置数据
        statusText.setText(order.getStatusText());
        orderIdText.setText("订单号：" + order.getOrderId());
        orderTimeText.setText("下单时间：" + order.getOrderTime());

        // 完成时间只在已完成时显示
        if (order.getStatus() == Order.Status.COMPLETED) {
            completeTimeText.setVisibility(View.VISIBLE);
            completeTimeText.setText("完成时间：" + order.getCompleteTime());
        } else {
            completeTimeText.setVisibility(View.GONE);
        }

        totalText.setText("$" + order.getTotalPrice());

        // 菜品列表
        recyclerItems.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerItems.setAdapter(new OrderItemAdapter(order.getOrderItems()));

        // 显示弹窗
        new AlertDialog.Builder(getContext())
                .setView(dialogView)
                .setPositiveButton("关闭", null)
                .show();
    }
}