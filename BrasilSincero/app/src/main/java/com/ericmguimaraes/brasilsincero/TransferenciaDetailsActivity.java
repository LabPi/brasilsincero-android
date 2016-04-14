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

    @Bind(R.id.situacao)
    public TextView tx_situacao;

    @Bind(R.id.data)
    public TextView dt_emissao_dl;

    @Bind(R.id.esfera)
    public TextView tx_esfera_adm_convenente;

    @Bind(R.id.proponenteContent)
    public TextView nm_proponente;

    @Bind(R.id.responsavelContent)
    public TextView nm_respons_proponente;

    @Bind(R.id.valorContent)
    public TextView vl_global;

    @Bind(R.id.banco)
    public TextView nm_banco_convenio;

    @Bind(R.id.agencia)
    public TextView cd_agencia_convenio;

    @Bind(R.id.conta_corrente)
    public TextView nr_conta_corrente_convenio;

    @Bind(R.id.banco_favorecido)
    public TextView num_banco_favorecido_dl;

    @Bind(R.id.agencia_favorecido)
    public TextView cd_agencia_favorecido_dl;

    @Bind(R.id.conta_corrente_favorecido)
    public TextView nr_conta_corrente_fav_dl;

    @Bind(R.id.municipio)
    public TextView nm_municipio_convenente;

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
            if(json!=null && !json.isEmpty())
                populateTransferenciaView(json);
        }
    }

    private void populateTransferenciaView(String json) {
        Gson gson = new Gson();
        Transferencia transferencia = gson.fromJson(json, Transferencia.class);
        nm_programa.setText(transferencia.nm_identif_favorecido_dl);
        tx_situacao.setText(transferencia.tx_situacao);
        dt_emissao_dl.setText(transferencia.dt_emissao_dl);
        tx_esfera_adm_convenente.setText("Esfera " + capitalize(transferencia.tx_esfera_adm_convenente.toLowerCase()));
        nm_proponente.setText(transferencia.nm_ident_convenente);
        nm_respons_proponente.setText(transferencia.nm_orgao_concedente);
        nm_municipio_convenente.setText("Município: " + transferencia.nm_municipio_convenente + "/" + transferencia.uf_convenente);
        vl_global.setText(transferencia.vl_bruto_dl + " bruto / " + transferencia.vl_liquido_dl + " líquido");
        nm_banco_convenio.setText("Banco: " + transferencia.nm_banco_convenio);
        cd_agencia_convenio.setText("Agência: " + transferencia.cd_agencia_convenio);
        nr_conta_corrente_convenio.setText("Conta Corrente: " + transferencia.nr_conta_corrente_convenio);
        num_banco_favorecido_dl.setText("Banco: " + transferencia.num_banco_favorecido_dl);
        cd_agencia_favorecido_dl.setText("Agência: " + transferencia.cd_agencia_favorecido_dl);
        nr_conta_corrente_fav_dl.setText("Conta Corrente: " + transferencia.nr_conta_corrente_fav_dl);
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

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
