package com.ericmguimaraes.brasilsincero.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ericmguimaraes.brasilsincero.R;
import com.ericmguimaraes.brasilsincero.model.Convenio;
import com.ericmguimaraes.brasilsincero.model.Denunciations;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Daniely Botelho on 4/14/16.
 */
public class ConvenioDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    List<Denunciations> data = new ArrayList();
    private String JSON;

    public ConvenioDetailsAdapter(String json) {
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
                    .inflate(R.layout.header_convenio_details, parent, false);
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
            Convenio convenio = gson.fromJson(JSON, Convenio.class);
            ((VHHeader) holder).nm_programa.setText(convenio.nm_programa);
            ((VHHeader) holder).tx_situacao.setText(convenio.tx_situacao);
            ((VHHeader) holder).tx_esfera_adm_proponente.setText("Esfera " + capitalize(convenio.tx_esfera_adm_proponente.toLowerCase()));
            ((VHHeader) holder).tx_qualific_proponente.setText(capitalize(convenio.tx_qualific_proponente.replace('_', ' ').toLowerCase()));
            ((VHHeader) holder).nm_proponente.setText(convenio.nm_proponente);
            ((VHHeader) holder).nm_respons_proponente.setText(convenio.nm_respons_proponente);
            ((VHHeader) holder).vl_global.setText(convenio.vl_global + " / " + convenio.vl_repasse + " pago");
            ((VHHeader) holder).tx_objeto_convenio.setText(convenio.tx_objeto_convenio);
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

        @Bind(R.id.esfera)
        public TextView tx_esfera_adm_proponente;

        @Bind(R.id.qualificacao)
        public TextView tx_qualific_proponente;

        @Bind(R.id.proponenteContent)
        public TextView nm_proponente;

        @Bind(R.id.responsavelContent)
        public TextView nm_respons_proponente;

        @Bind(R.id.valorContent)
        public TextView vl_global;

        @Bind(R.id.descricao)
        public TextView tx_objeto_convenio;

        public VHHeader(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }
}
