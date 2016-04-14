package com.ericmguimaraes.brasilsincero.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericmguimaraes.brasilsincero.R;
import com.ericmguimaraes.brasilsincero.model.Convenio;
import com.ericmguimaraes.brasilsincero.model.Denunciations;
import com.ericmguimaraes.brasilsincero.model.Transferencia;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Daniely Botelho on 4/14/16.
 */
public class TransferenciaDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    List<Denunciations> data = new ArrayList();
    private String JSON;

    public TransferenciaDetailsAdapter(String json) {
        Denunciations d = new Denunciations("Descrição", "12/12/2012");
        data.add(d);
        data.add(d);
        data.add(d);
        data.add(d);
        data.add(d);

        JSON = json;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_convenio_denunciation_item, parent, false);
            return new VHItem(view);
        } else if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_transferencia_details, parent, false);
            return new VHHeader(view);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof VHItem) {
            Denunciations dataItem = getItem(position);
            //cast holder to VHItem and set data
        } else if (holder instanceof VHHeader) {

            Gson gson = new Gson();
            Transferencia transferencia = gson.fromJson(JSON, Transferencia.class);
            ((VHHeader) holder).nm_programa.setText(transferencia.nm_identif_favorecido_dl);
            ((VHHeader) holder).tx_situacao.setText(transferencia.tx_situacao);
            ((VHHeader) holder).dt_emissao_dl.setText(transferencia.dt_emissao_dl);
            ((VHHeader) holder).tx_esfera_adm_convenente.setText("Esfera " + capitalize(transferencia.tx_esfera_adm_convenente.toLowerCase()));
            ((VHHeader) holder).nm_proponente.setText(transferencia.nm_ident_convenente);
            ((VHHeader) holder).nm_respons_proponente.setText(transferencia.nm_orgao_concedente);
            ((VHHeader) holder).nm_municipio_convenente.setText("Município: " + transferencia.nm_municipio_convenente + "/" + transferencia.uf_convenente);
            ((VHHeader) holder).vl_global.setText(transferencia.vl_bruto_dl + " bruto / " + transferencia.vl_liquido_dl + " líquido");
            ((VHHeader) holder).nm_banco_convenio.setText("Banco: " + transferencia.nm_banco_convenio);
            ((VHHeader) holder).cd_agencia_convenio.setText("Agência: " + transferencia.cd_agencia_convenio);
            ((VHHeader) holder).nr_conta_corrente_convenio.setText("Conta Corrente: " + transferencia.nr_conta_corrente_convenio);
            ((VHHeader) holder).num_banco_favorecido_dl.setText("Banco: " + transferencia.num_banco_favorecido_dl);
            ((VHHeader) holder).cd_agencia_favorecido_dl.setText("Agência: " + transferencia.cd_agencia_favorecido_dl);
            ((VHHeader) holder).nr_conta_corrente_fav_dl.setText("Conta Corrente: " + transferencia.nr_conta_corrente_fav_dl);
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private Denunciations getItem(int position) {
        return data.get(position - 1);
    }

    class VHItem extends RecyclerView.ViewHolder {

        public VHItem(View itemView) {
            super(itemView);
        }
    }

    class VHHeader extends RecyclerView.ViewHolder {

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

        public VHHeader(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
