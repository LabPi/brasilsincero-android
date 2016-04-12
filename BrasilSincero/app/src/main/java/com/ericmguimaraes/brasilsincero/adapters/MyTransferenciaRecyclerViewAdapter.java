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
import com.ericmguimaraes.brasilsincero.model.Transferencia;
import com.google.gson.Gson;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTransferenciaRecyclerViewAdapter extends RecyclerView.Adapter<MyTransferenciaRecyclerViewAdapter.ViewHolder> {

    private final List<Transferencia> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Activity activity;
    boolean isTransferencia = true;

    public MyTransferenciaRecyclerViewAdapter(List<Transferencia> items, OnListFragmentInteractionListener listener, Activity activity, boolean isTransferencia) {
        mValues = items;
        mListener = listener;
        this.activity = activity;
        this.isTransferencia = isTransferencia;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(isTransferencia)
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_transferencia_item, parent, false);
        else
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_transferencia_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Transferencia c = mValues.get(position);
        holder.mItem = c;
        holder.nm_programa.setText(c.nm_identif_favorecido_dl.substring(0,15)+"...");
        holder.location.setText(c.nm_municipio_convenente+" - "+c.uf_convenente);
        holder.date.setText(c.dt_emissao_dl);
        holder.vl_global.setText(c.vl_bruto_dl);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TransferenciaDetailsActivity.class);
                Gson gson = new Gson();
                intent.putExtra("transferencia",gson.toJson(holder.mItem, Transferencia.class));
                activity.startActivity(intent);
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

    }
}
