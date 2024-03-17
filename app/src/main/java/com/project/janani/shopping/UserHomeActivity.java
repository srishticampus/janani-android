package com.project.janani.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class UserHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout userDrawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView userNavigationView;
    public BottomNavigationView bottomNavigationView;
    UserHomeFragment userHomeFragment = new UserHomeFragment();
    UserOrderFragment userOrderFragment = new UserOrderFragment();
    UserHistoryFragment userHistoryFragment = new UserHistoryFragment();
    UserAccountFragment userAccountFragment = new UserAccountFragment();
    UserWishlistFragment userWishlistFragment = new UserWishlistFragment();
    DietChartFragment userDietChartFragment = new DietChartFragment();
    FAQFragment faqFragment = new FAQFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        userDrawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, userDrawerLayout, R.string.nav_open, R.string.nav_close);

        userNavigationView = findViewById(R.id.nav_view);
        userNavigationView.setNavigationItemSelectedListener(this);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        userDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.user_main_fragment, userHomeFragment).commit();

        }
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_to_cart:
                startActivity(new Intent(getApplicationContext(), ShoppingCartActivity.class));
                return true;
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    public void closeDrawer() {
        if (userDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            userDrawerLayout.closeDrawer(GravityCompat.START);

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_home_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.user_main_fragment, userHomeFragment).commit();
                closeDrawer();
                return true;

            case R.id.user_history_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.user_main_fragment, userHistoryFragment).commit();
                closeDrawer();
                return true;

            case R.id.user_account_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.user_main_fragment, userAccountFragment).commit();
                closeDrawer();
                return true;

            case R.id.user_wishlist_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.user_main_fragment, userWishlistFragment).commit();
                closeDrawer();
                return true;

            case R.id.user_faq_option:
                getSupportFragmentManager().beginTransaction().replace(R.id.user_main_fragment, faqFragment).commit();
                closeDrawer();
                return true;

            case R.id.user_diet_chart:
                getSupportFragmentManager().beginTransaction().replace(R.id.user_main_fragment, userDietChartFragment).commit();
                closeDrawer();
                return true;
        }
        return false;
    }
}