package com.example.myapplication.ui.cart;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.data.model.CartItem;
import com.example.myapplication.data.model.Order;
import com.example.myapplication.data.order.OrderRepository;
import com.example.myapplication.ui.cart.adapter.CartAdapter;

import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;
    private CartAdapter cartAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the cart screen layout. Keep it minimal for mock stage.
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        final Button clearButton = view.findViewById(R.id.clear_button);
        final Button orderButton = view.findViewById(R.id.order_button);
        final TextView textView = view.findViewById(R.id.text_total_price);

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        clearButton.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("清空购物车")
                    .setMessage("确定要清空购物车吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        cartViewModel.clearCart();
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });
        orderButton.setOnClickListener(v -> {
            List<CartItem> cartItems = cartViewModel.getCartItems().getValue();
            if (cartItems == null || cartItems.isEmpty()) {
                Toast.makeText(getContext(), "购物车是空的", Toast.LENGTH_SHORT).show();
                return;
            }
            // 把购物车数据传给 OrderRepository
            OrderRepository.getInstance().addOrder(cartItems);
            // 清空购物车
            cartViewModel.clearCart();
            // 跳转到订单页
            Toast.makeText(getContext(), "下单成功", Toast.LENGTH_SHORT).show();
        });
        RecyclerView cartRecycler = view.findViewById(R.id.recycler_item);
        cartRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        cartAdapter = new CartAdapter(null, new CartAdapter.OnCartItemClickListener() {
            @Override
            public void onAddClick(CartItem item) {
                cartViewModel.increaseQuantity(item);
            }

            @Override
            public void onMinusClick(CartItem item) {
                cartViewModel.decreaseQuantity(item);
            }
        });
        cartRecycler.setAdapter(cartAdapter);
        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), cartItems -> {
            cartAdapter.updateData(cartItems);
            textView.setText("$" +  String.format(Locale.getDefault(), "%.2f", cartViewModel.getTotalPrice()));
        });
    }
}
