package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.data.login.LoginRepository;
import com.example.myapplication.data.model.LoggedInUser;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
//        finish(); // 关掉 MainActivity，防止用户按返回键回来

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavView, navController);

        final Button loginButton = binding.loginbutton;
        loginButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        final Button loginoutButton = binding.logoutbutton;
        loginoutButton.setOnClickListener(v -> {
            LoginRepository.getInstance().logout();
            refreshAuthUi();
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        refreshAuthUi();
    }
    private void refreshAuthUi() {
        TextView displayName = findViewById(R.id.display_name);
        boolean loggedIn = LoginRepository.getInstance().isLoggedIn();
        binding.loginbutton.setVisibility(loggedIn ? View.GONE : View.VISIBLE);
        binding.logoutbutton.setVisibility(loggedIn ? View.VISIBLE : View.GONE);
        displayName.setVisibility(loggedIn ? View.VISIBLE : View.GONE);
        if (loggedIn) {
            LoggedInUser user = LoginRepository.getInstance().getCurrentUser();
            String name = (user != null) ? user.getDisplayName() : "";
            binding.displayName.setText(name);
        }
    }
    public static boolean checkLogin(Fragment fragment) {
        if (!LoginRepository.getInstance().isLoggedIn()) {
            fragment.startActivity(
                    new Intent(fragment.getActivity(), LoginActivity.class));
            Toast.makeText(fragment.getContext(),
                    "请先登录", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}