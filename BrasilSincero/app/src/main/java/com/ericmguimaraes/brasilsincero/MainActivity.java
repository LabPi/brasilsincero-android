package com.ericmguimaraes.brasilsincero;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.github.clans.fab.FloatingActionButton;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ericmguimaraes.brasilsincero.adapters.ViewPagerAdapter;
import com.ericmguimaraes.brasilsincero.fragments.ConvenioFragment;
import com.ericmguimaraes.brasilsincero.fragments.dummy.DummyContent;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ConvenioFragment.OnListFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.fab_city_region)
    FloatingActionButton fabCityRegion;

    @Bind(R.id.fab_city_state)
    FloatingActionButton fabCityState;

    @Bind(R.id.fab_state_region)
    FloatingActionButton fabStateRegion;

    @Bind(R.id.fab_filters)
    FloatingActionButton fabFilters;

    View.OnClickListener fabRankingOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FilterActivity.isRanking = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(1);

        fabCityRegion.setOnClickListener(fabRankingOnClickListener);
        fabCityState.setOnClickListener(fabRankingOnClickListener);
        fabStateRegion.setOnClickListener(fabRankingOnClickListener);

        fabFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FilterActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ConvenioFragment(), "Convênios");
        adapter.addFragment(new ConvenioFragment(), "Transferencias");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent;
        switch (id){
            case R.id.denunciations_menu:
                intent = new Intent(this,DenunciationsActivity.class);
                startActivity(intent);
                break;
            case R.id.filter_menu:
                intent = new Intent(this,FilterActivity.class);
                startActivity(intent);
                break;
            case R.id.graphs_menu:
                intent = new Intent(this,GraphsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
