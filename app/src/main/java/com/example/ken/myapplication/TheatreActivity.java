package com.example.ken.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ken.myapplication.Controllers.Main.Contact.ContactFragment;
import com.example.ken.myapplication.Controllers.Main.MainFragment;
import com.example.ken.myapplication.Domain.Film;
import com.example.ken.myapplication.Tickets.TicketOverviewFragment;
import com.example.ken.myapplication.Util.FilmGridAdapter;
import com.example.ken.myapplication.Util.PagerAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class TheatreActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ArrayList<Film> films;
    private FilmGridAdapter filmGridAdapter;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MainFragment mainFragment;
    private PagerAdapter adapter;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_theatre);
        mainFragment = new MainFragment();

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(getColor(R.color.colorText));
//        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getColor(R.color.colorText), getColor(R.color.colorText));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchView searchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.search_button).getActionView();
        System.out.println(searchView.getSolidColor());


        searchView.setOnQueryTextListener(mainFragment);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupViewPager(ViewPager viewPager){
        adapter = new PagerAdapter(getSupportFragmentManager());


        ContactFragment contactFragment = new ContactFragment();
        TicketOverviewFragment ticketOverviewFragment = new TicketOverviewFragment();

        adapter.addFragment(mainFragment, "Overview");
        adapter.addFragment(contactFragment, "Contact");
        adapter.addFragment(ticketOverviewFragment, "Booked Tickets");


        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logout){
            mAuth.signOut();
            startActivity(new Intent(TheatreActivity.this,LogInActivity.class));
        }
        if(id == R.id.next){
            Toast.makeText(this,"Book ticket first",Toast.LENGTH_LONG);
        }
        return super.onOptionsItemSelected(item);
    }
}
