package com.ericmguimaraes.brasilsincero;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;


import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FilterActivity extends AppCompatActivity {

    @Bind(R.id.confirmationBtn)
    Button confirmationButton;

    @Bind(R.id.esfera_spinner)
    MaterialBetterSpinner esferaSpinner;

    @Bind(R.id.proponente_spinner)
    MaterialBetterSpinner proponenteSpinner;

    @Bind(R.id.regions_spinner)
    MaterialBetterSpinner regionSpinner;

    @Bind(R.id.states_spinner)
    MaterialBetterSpinner stateSpinner;

    public static boolean isRanking = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
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

        confirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.states_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.regions_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regionSpinner.setAdapter(adapter2);

        ArrayAdapter<String> proponenteArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, qualificacaoDoProponente);
        proponenteSpinner.setAdapter(proponenteArrayAdapter);

        ArrayAdapter<String> esferaArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, esfera);
        esferaSpinner.setAdapter(esferaArrayAdapter);

        ArrayAdapter<String> stateArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.states_array));
        stateSpinner.setAdapter(stateArrayAdapter);

        ArrayAdapter<String> regionArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.regions_array));
        regionSpinner.setAdapter(regionArrayAdapter);

    }

    private static final String[] qualificacaoDoProponente = new String[] {
            "Beneficiário Ementa Parlamentar", "Beneficiário Específico", "Repasse Voluntário"
    };

    private static final String[] esfera = new String[] {
            "Empresa Pública Sociedade Economica Mista Estadual", "Federal", "Municipal", "Privada", "Consórcio Público", "Organismo Internacional"
    };

}
