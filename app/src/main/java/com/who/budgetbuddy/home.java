package com.who.budgetbuddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

public class home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationItemView bottomNavigationView;
    private FrameLayout frameLayout;
    private Dashboard dashboard_fragment;
    private Income income_fragment;
    private FirebaseAuth mAuth;
    private Expense expense_fragment;
    Button dash,inc,exp;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar=findViewById(R.id.tool_bar);
        toolbar.setTitle("Budget Buddy");
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser= mAuth.getCurrentUser();
        String uid=mUser.getUid();
        frameLayout = findViewById(R.id.main_frame);
        DrawerLayout drawerLayout=findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(
                this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        name=findViewById(R.id.username);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference();
        NavigationView navigationView=findViewById(R.id.Nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.expense) {
                    displaySelectedListener(itemId);
                } else if (itemId == R.id.income) {
                    displaySelectedListener(itemId);
                } else if (itemId == R.id.logout) {
                    mAuth.signOut();
                    Intent next;
                    next = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(next);
                    finish();
                }
                return true;
            }
        });




//        Fragments
        dashboard_fragment=new Dashboard();
        income_fragment=new Income();
        expense_fragment=new Expense();
//setFragment(dashboard_fragment);
dash=findViewById(R.id.ac_dashboard);
inc=findViewById(R.id.ac_income);
exp=findViewById(R.id.ac_expense);
setFragment(dashboard_fragment);

dash.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        setFragment(dashboard_fragment);
    }
});
exp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        setFragment(expense_fragment);
    }
});

inc.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        setFragment(income_fragment);
    }
});
    }

    private void setFragment(Fragment fragment){

         FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
         fragmentTransaction.replace(R.id.main_frame,fragment);
         fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

    DrawerLayout drawerLayout=findViewById(R.id.drawer);
        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
             drawerLayout.closeDrawer(GravityCompat.END);
             }
        else{
          super.onBackPressed();
            }

    }
    public void displaySelectedListener(int itemId){
        Fragment fragment=null;
        switch(itemId) {
            case R.id.expense:
            fragment=new Expense();
            setFragment(fragment);
                break;

            case R.id.income:
                fragment=new Income() ;
            break;

        }
        if(fragment!=null){

            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame,fragment);
            ft.commit();
        }
        DrawerLayout drawerLayout=findViewById(R.id.drawer);
        drawerLayout.closeDrawer(GravityCompat.START);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return true;
    }
}