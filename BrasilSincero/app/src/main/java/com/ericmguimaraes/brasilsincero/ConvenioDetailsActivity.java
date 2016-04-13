package com.ericmguimaraes.brasilsincero;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ericmguimaraes.brasilsincero.fragments.DenunciationsFragment;
import com.ericmguimaraes.brasilsincero.fragments.dummy.DummyContent;
import com.ericmguimaraes.brasilsincero.model.Convenio;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConvenioDetailsActivity extends AppCompatActivity implements DenunciationsFragment.OnListFragmentInteractionListener {

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.programaContent)
    public TextView nm_programa;

    @Bind(R.id.proponenteContent)
    public TextView nm_proponente;

    @Bind(R.id.responsavelContent)
    public TextView nm_respons_proponente;

    @Bind(R.id.valorContent)
    public TextView vl_global;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenio_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConvenioDetailsActivity.this, DenuntiantionRegistActivity.class);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String json = extras.getString("convenio");
            if(json!=null && !json.isEmpty())
                populateConvenioView(json);
        }
    }

    private void populateConvenioView(String json) {
        Gson gson = new Gson();
        Convenio convenio = gson.fromJson(json, Convenio.class);
        nm_programa.setText(convenio.nm_programa);
        nm_proponente.setText(convenio.nm_proponente);
        nm_respons_proponente.setText(convenio.nm_respons_proponente);
        vl_global.setText(convenio.vl_global);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, DenunciationsFragment.newInstance(1));
        fragmentTransaction.commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
