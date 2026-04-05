package com.example.myapplication.ui.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.login.LoginRepository;
import com.example.myapplication.data.model.MenuItem;
import com.example.myapplication.ui.cart.CartViewModel;
import com.example.myapplication.ui.menu.adapter.CategoryAdapter;
import com.example.myapplication.ui.menu.adapter.MenuAdapter;

public class MenuFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private MenuAdapter menuAdapter;
    private CategoryAdapter categoryAdapter;
    private CartViewModel cartViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //初始化cartView
        cartViewModel = new ViewModelProvider(requireActivity())
                .get(CartViewModel.class);
        // 初始化 ViewModel
        menuViewModel = new ViewModelProvider(this, new MenuViewModelFactory())
                .get(MenuViewModel.class);

        // 初始化分类列表
        RecyclerView categoryRecycler = view.findViewById(R.id.recycler_category);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryAdapter = new CategoryAdapter(null, category -> {
            menuViewModel.selectCategory(category.getName());
        });
        categoryRecycler.setAdapter(categoryAdapter);

        // 初始化菜品列表
        RecyclerView menuRecycler = view.findViewById(R.id.recycler_menu);
        menuRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        menuAdapter = new MenuAdapter(null, new MenuAdapter.OnMenuItemClickListener() {
            @Override
            public void onAddClick(MenuItem item) {
                // 数量加一
                if (!MainActivity.checkLogin(MenuFragment.this)) return;
                if (item.getQuantity() == 0)
                {
                    cartViewModel.addToCart(item,1,"");
                }
                else {
                    cartViewModel.increaseQuantityByMenuItem(item);
                }
                item.setQuantity(item.getQuantity()+1);
                menuAdapter.notifyDataSetChanged();

            }

            @Override
            public void onMinusClick(MenuItem item) {
                // 数量减一
                if (!MainActivity.checkLogin(MenuFragment.this)) return;
                if (item.getQuantity() > 0) {
                    cartViewModel.decreaseQuantityByMenuItem(item);
                    item.setQuantity(item.getQuantity() - 1);
                    menuAdapter.notifyDataSetChanged();
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemClick(MenuItem item) {
                MenuItemDetailBottomSheet sheet = MenuItemDetailBottomSheet.newInstance(item);
                sheet.setOnAddToCartListener((menuItem, quantity, remark) -> {
                    menuItem.setQuantity(menuItem.getQuantity() + quantity);
                    cartViewModel.addToCart(menuItem, quantity, remark);
                    menuAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(),
                            menuItem.getName() + " x" + quantity + " 已加入购物车",
                            Toast.LENGTH_SHORT).show();
                });
                sheet.show(getChildFragmentManager(), "MenuItemDetail");
            }
        });
        menuRecycler.setAdapter(menuAdapter);

        // 观察分类数据
        menuViewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            categoryAdapter.updateData(categories);
        });

        // 观察菜品数据
        menuViewModel.getMenuItems().observe(getViewLifecycleOwner(), menuItems -> {
            menuAdapter.updateData(menuItems);
        });
    }
    public void onResume()
    {
        super.onResume();
        if (menuAdapter != null) {
            menuAdapter.notifyDataSetChanged();
        }
    }
}