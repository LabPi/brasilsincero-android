package com.ericmguimaraes.brasilsincero.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ericmguimaraes.brasilsincero.TransferenciaDetailsActivity;
import com.ericmguimaraes.brasilsincero.R;
import com.ericmguimaraes.brasilsincero.fragments.TransferenciaFragment.OnListFragmentInteractionListener;
import com.ericmguimaraes.brasilsincero.fragments.dummy.DummyContent.DummyItem;
import com.ericmguimaraes.brasilsincero.model.Convenio;
import com.ericmguimaraes.brasilsincero.model.Transferencia;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTransferenciaRecyclerViewAdapter extends RecyclerView.Adapter<MyTransferenciaRecyclerViewAdapter.ViewHolder> {

    private List<Transferencia> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Activity activity;
    boolean isTransferencia = true;
    private int HEADER_VIEW_ID = 0;
    private int TRANSACAO_VIEW_ID = 1;

    public MyTransferenciaRecyclerViewAdapter(List<Transferencia> items, OnListFragmentInteractionListener listener, Activity activity, boolean isTransferencia) {
        mValues = items;
        mListener = listener;
        this.activity = activity;
        this.isTransferencia = isTransferencia;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==TRANSACAO_VIEW_ID) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_convenio_item, parent, false);
            return new TransferenciaViewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_layout, parent, false);
            return new HeaderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Transferencia t = mValues.get(position);
        if(mValues.get(position).header!=null){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
            headerViewHolder.content.setText(t.header);
        } else {
            final TransferenciaViewHolder holder = (TransferenciaViewHolder) viewHolder;
            holder.mItem = t;
            holder.nm_programa.setText(t.nm_identif_favorecido_dl);
            holder.location.setText(t.nm_municipio_convenente + " - " + t.uf_convenente);
            holder.date.setText(t.dt_emissao_dl);
            holder.vl_global.setText(t.vl_bruto_dl);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, TransferenciaDetailsActivity.class);
                    Gson gson = new Gson();
                    intent.putExtra("transferencia", gson.toJson(holder.mItem, Transferencia.class));
                    activity.startActivity(intent);
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mValues.get(position).header!=null)
            return HEADER_VIEW_ID;
        return TRANSACAO_VIEW_ID;
    }

    public void setFilter(List<Transferencia> countryModels) {
        mValues = new ArrayList<>();
        mValues.addAll(countryModels);
        notifyDataSetChanged();
    }

    public void setData(List<Transferencia> newList){
        mValues = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class TransferenciaViewHolder extends ViewHolder {
        public final View mView;
        public Transferencia mItem;

        @Bind(R.id.titleText)
        public TextView nm_programa;

        @Bind(R.id.locationText)
        public TextView location;

        @Bind(R.id.dateText)
        public TextView date;

        @Bind(R.id.valueText)
        public TextView vl_global;

        public TransferenciaViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

    }

    public class HeaderViewHolder extends ViewHolder {

        public final View mView;

        public Convenio mItem;

        @Bind(R.id.content)
        public TextView content;

        public HeaderViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }
    }
}
