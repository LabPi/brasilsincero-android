package com.ericmguimaraes.brasilsincero;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ericmguimaraes.brasilsincero.adapters.ConvenioDetailsAdapter;
import com.ericmguimaraes.brasilsincero.adapters.TransferenciaDetailsAdapter;
import com.ericmguimaraes.brasilsincero.fragments.DenunciationsFragment;
import com.ericmguimaraes.brasilsincero.fragments.dummy.DummyContent;
import com.ericmguimaraes.brasilsincero.model.Transferencia;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransferenciaDetailsActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    TransferenciaDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia_details);
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
                Intent intent = new Intent(TransferenciaDetailsActivity.this, DenuntiantionRegistActivity.class);
                startActivity(intent);
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String json = extras.getString("transferencia");
            if(json!=null && !json.isEmpty()) {
                adapter = new TransferenciaDetailsAdapter(json);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
            }
        }
    }
}
