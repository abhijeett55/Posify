    package com.example.posify;

    import static android.app.PendingIntent.getActivity;

    import android.annotation.SuppressLint;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.MenuItem;

    import androidx.activity.EdgeToEdge;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.ActionBarDrawerToggle;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.core.view.GravityCompat;
    import androidx.drawerlayout.widget.DrawerLayout;
    import androidx.viewpager2.widget.ViewPager2;

    import com.example.posify.items.ItemStoragePage;
    import com.example.posify.modal.ViewPagerAdapter;
    import com.example.posify.orders.OrderPage;
    import com.example.posify.signin.LoginPage;
    import com.google.android.material.bottomnavigation.BottomNavigationView;
    import com.google.android.material.navigation.NavigationView;
    import com.google.firebase.auth.FirebaseAuth;

    public class MainActivity extends AppCompatActivity {

        private ViewPager2 viewPager;
        private BottomNavigationView bottomNavigation;

        private DrawerLayout drawerLayout;
        private NavigationView navigationView;
        private Toolbar toolBar1;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);

            drawerLayout = findViewById(R.id.drawerLayout);
            navigationView = findViewById(R.id.navigationView);

            viewPager = findViewById(R.id.viewPager);
            bottomNavigation = findViewById(R.id.bottomNavigation);

            setupViewPager();
            setupBottomNavigation();

            toolBar1 = findViewById(R.id.toolBar1);
            setSupportActionBar(toolBar1);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, toolBar1,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close
            );
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();


            navigationView.setNavigationItemSelectedListener(item -> {
                setDrawerLayout(item);
                return true;
            });


        }

        private void setDrawerLayout(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.menu_dashboard) {
                viewPager.setCurrentItem(0);
            } else if (id == R.id.menu_storage) {
                Intent intent = new Intent(this, ItemStoragePage.class);
                startActivity(intent);
            } else if (id == R.id.menu_order) {
                    Intent intent = new Intent(this, OrderPage.class);
                    startActivity(intent);
            } else if (id == R.id.menu_logout) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
        }



        private void setupViewPager() {
            ViewPagerAdapter adapter = new ViewPagerAdapter(this);
            viewPager.setAdapter(adapter);

            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    bottomNavigation.getMenu().getItem(position).setChecked(true);
                }
            });
        }

        @SuppressLint("NonConstantResourceId")
        private void setupBottomNavigation() {
            bottomNavigation.setOnItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    viewPager.setCurrentItem(0);
                    return true;
                } else if (id == R.id.menu_order) {
                    viewPager.setCurrentItem(1);
                    return true;
                } else if (id == R.id.menu_cart) {
                    viewPager.setCurrentItem(2);
                    return true;
                } else if (id == R.id.menu_profile) {
                    viewPager.setCurrentItem(3);
                    return true;
                }
                return false;
            });
        }

    }