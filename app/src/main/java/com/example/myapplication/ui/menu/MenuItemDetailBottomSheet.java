package com.example.myapplication.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.model.MenuItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MenuItemDetailBottomSheet extends BottomSheetDialogFragment {

    // 用来传递菜品数据的 key
    private static final String ARG_ITEM_ID = "item_id";
    private static final String ARG_ITEM_NAME = "item_name";
    private static final String ARG_ITEM_PRICE = "item_price";
    private static final String ARG_ITEM_DESCRIPTION = "item_description";
    private MenuItem menuItem;
    private int quantity = 0;

    // 回调接口，通知 Fragment 加入购物车
    public interface OnAddToCartListener {
        void onAddToCart(MenuItem item, int quantity, String remark);
    }

    private OnAddToCartListener listener;

    public void setOnAddToCartListener(OnAddToCartListener listener) {
        this.listener = listener;
    }

    // 创建 BottomSheet 的静态方法，传入菜品数据
    public static MenuItemDetailBottomSheet newInstance(MenuItem item) {
        MenuItemDetailBottomSheet sheet = new MenuItemDetailBottomSheet();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_ID, item.getId());
        args.putString(ARG_ITEM_NAME, item.getName());
        args.putDouble(ARG_ITEM_PRICE, item.getPrice());
        args.putString(ARG_ITEM_DESCRIPTION, item.getDescription());
        sheet.setArguments(args);
        sheet.menuItem = item;
        return sheet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_menu_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 获取传入的数据
        Bundle args = getArguments();
        if (args == null) return;

        String name = args.getString(ARG_ITEM_NAME);
        double price = args.getDouble(ARG_ITEM_PRICE);
        String description = args.getString(ARG_ITEM_DESCRIPTION);

        // 绑定控件
        TextView nameText = view.findViewById(R.id.text_detail_name);
        TextView priceText = view.findViewById(R.id.text_detail_price);
        TextView descriptionText = view.findViewById(R.id.text_detail_description);
        TextView quantityText = view.findViewById(R.id.text_detail_quantity);
        Button addButton = view.findViewById(R.id.button_detail_add);
        Button minusButton = view.findViewById(R.id.button_detail_minus);
        Button addToCartButton = view.findViewById(R.id.button_add_to_cart);
        EditText remarkEdit = view.findViewById(R.id.edit_remark);

        // 设置数据
        nameText.setText(name);
        priceText.setText("¥" + price);
        descriptionText.setText(description);

        // 加号点击
        addButton.setOnClickListener(v -> {
            quantity++;
            quantityText.setText(String.valueOf(quantity));
            quantityText.setVisibility(View.VISIBLE);
            minusButton.setVisibility(View.VISIBLE);

        });

        // 减号点击
        minusButton.setOnClickListener(v -> {
            if (quantity > 0) {
                quantity--;
                quantityText.setText(String.valueOf(quantity));
                if (quantity == 0) {
                    quantityText.setVisibility(View.GONE);
                    minusButton.setVisibility(View.GONE);
                }
            }
        });

        // 加入购物车
        addToCartButton.setOnClickListener(v -> {
            if (!MainActivity.checkLogin(MenuItemDetailBottomSheet.this)) return;
            if (quantity == 0) {
                quantity++;
                quantityText.setText(String.valueOf(quantity));
                quantityText.setVisibility(View.VISIBLE);
                minusButton.setVisibility(View.VISIBLE);
            }
            String remark = remarkEdit.getText().toString().trim();
            if (listener != null && menuItem != null) {
                listener.onAddToCart(menuItem, quantity, remark);
            }
//            dismiss(); // 关闭 BottomSheet
        });
    }
}