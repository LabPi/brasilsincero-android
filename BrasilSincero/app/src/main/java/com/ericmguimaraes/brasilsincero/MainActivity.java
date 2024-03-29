package com.ericmguimaraes.brasilsincero;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.ericmguimaraes.brasilsincero.fragments.TransferenciaFragment;
import com.ericmguimaraes.brasilsincero.model.Convenio;
import com.ericmguimaraes.brasilsincero.model.Transferencia;
import com.github.clans.fab.FloatingActionButton;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.ericmguimaraes.brasilsincero.adapters.ViewPagerAdapter;
import com.ericmguimaraes.brasilsincero.fragments.ConvenioFragment;
import com.ericmguimaraes.brasilsincero.fragments.dummy.DummyContent;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ConvenioFragment.OnListFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, TransferenciaFragment.OnListFragmentInteractionListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.fab_national)
    FloatingActionButton fabNational;

    @Bind(R.id.fab_regional)
    FloatingActionButton fabRegion;

    @Bind(R.id.fab_state)
    FloatingActionButton fabState;

    @Bind(R.id.menu_fab)
    FloatingActionMenu menufab;

    ConvenioFragment convenioFragment;
    TransferenciaFragment transferenciaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(0);

        fabNational.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "Ranking nacional ordenado por valor";
                if(viewPager.getCurrentItem()==0){
                    convenioFragment.showNationalRanking();
                    convenioFragment.setListStatus(text);
                } else if(viewPager.getCurrentItem()==1){
                    transferenciaFragment.showNationalRanking();
                    transferenciaFragment.setListStatus(text);
                }
                menufab.close(true);
            }
        });
        fabRegion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "Ranking regional ordenado por valor";
                if(viewPager.getCurrentItem()==0){
                    convenioFragment.showRegionalRanking();
                    convenioFragment.setListStatus(text);
                } else if(viewPager.getCurrentItem()==1){
                    transferenciaFragment.showRegionalRanking();
                    transferenciaFragment.setListStatus(text);
                }
                menufab.close(true);
            }
        });
        fabState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "Ranking estadual ordenado por valor";
                if(viewPager.getCurrentItem()==0){
                    convenioFragment.showStateRanking();
                    convenioFragment.setListStatus(text);
                } else if(viewPager.getCurrentItem()==1){
                    transferenciaFragment.showStateRanking();
                    transferenciaFragment.setListStatus(text);
                }
                menufab.close(true);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        handleIntent(getIntent());

        menufab.setIconAnimated(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        convenioFragment = ConvenioFragment.newInstance(true);
        transferenciaFragment = TransferenciaFragment.newInstance(false);
        adapter.addFragment(convenioFragment, "Convênios");
        adapter.addFragment(transferenciaFragment, "Transferências");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem mSearchMenuItem = menu.findItem(R.id.search);
        mSearchMenuItem.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);

        SearchView  searchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        LinearLayout linearLayoutOfSearchView = (LinearLayout) searchView.getChildAt(0);
        Button advancedSearchButton = new Button(getApplicationContext());
        Drawable icon = getResources().getDrawable(R.drawable.ic_icone_busca_avancada);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            advancedSearchButton.setBackgroundDrawable(icon);
        } else {
            advancedSearchButton.setBackground(icon);
        }
        advancedSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FilterActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout.LayoutParams navButtonsParams = new RelativeLayout.LayoutParams(toolbar.getHeight() * 2 / 3, toolbar.getHeight() * 2 / 3);
        linearLayoutOfSearchView.addView(advancedSearchButton,navButtonsParams);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Convenio item) {

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
        Intent intent = null;
        switch (id){
            case R.id.nav_denunciations :
                intent = new Intent(this,DenunciationsActivity.class);
                break;
            case R.id.nav_graphs :
                intent = new Intent(this,GraphsActivity.class);
                break;
            case R.id.nav_statistics :
                Toast toast = Toast.makeText(getApplicationContext(),"Desculpe, essa funcionalidade será desenvolvida na proxima versão.", Toast.LENGTH_LONG);
                toast.show();
                break;
            case R.id.nav_evaluation :
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;
        }
        if(intent!=null)
            startActivity(intent);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

    @Override
    public void onListFragmentInteraction(Transferencia item) {

    }
}
