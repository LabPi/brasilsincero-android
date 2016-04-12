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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ericmguimaraes.brasilsincero.fragments.DenunciationsFragment;
import com.ericmguimaraes.brasilsincero.fragments.dummy.DummyContent;
import com.ericmguimaraes.brasilsincero.model.Transferencia;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransferenciaDetailsActivity extends AppCompatActivity implements DenunciationsFragment.OnListFragmentInteractionListener {

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

        Drawable img = null;
        img = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icone_denuncias);
        img.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        fab.setImageDrawable(img);

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
            if(json!=null && !json.isEmpty())
                populateTransferenciaView(json);
        }
    }

    private void populateTransferenciaView(String json) {
        Gson gson = new Gson();
        Transferencia transferencia = gson.fromJson(json, Transferencia.class);
        nm_programa.setText(transferencia.nm_identif_favorecido_dl);
        nm_proponente.setText(transferencia.nm_orgao_concedente);
        nm_respons_proponente.setText(transferencia.nm_orgao_concedente);
        vl_global.setText(transferencia.vl_bruto_dl);
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
