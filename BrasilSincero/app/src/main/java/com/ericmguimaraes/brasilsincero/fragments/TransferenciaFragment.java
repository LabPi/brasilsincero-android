package com.ericmguimaraes.brasilsincero.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ericmguimaraes.brasilsincero.R;
import com.ericmguimaraes.brasilsincero.adapters.MyTransferenciaRecyclerViewAdapter;
import com.ericmguimaraes.brasilsincero.adapters.MyTransferenciaRecyclerViewAdapter;
import com.ericmguimaraes.brasilsincero.model.Convenio;
import com.ericmguimaraes.brasilsincero.model.Transferencia;
import com.ericmguimaraes.brasilsincero.model.Transferencia;
import com.ericmguimaraes.brasilsincero.model.TransferenciaRegionRanking;
import com.ericmguimaraes.brasilsincero.model.TransferenciaStateRanking;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TransferenciaFragment extends Fragment implements SearchView.OnQueryTextListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    @Bind(R.id.list)
    RecyclerView recyclerView;

    @Bind(R.id.status)
    TextView statusTextView;

    MyTransferenciaRecyclerViewAdapter adapter;
    List<Transferencia> transferencias;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TransferenciaFragment() {
    }

    public static TransferenciaFragment newInstance(boolean isTransferencia) {
        TransferenciaFragment fragment = new TransferenciaFragment();
        Bundle args = new Bundle();
        args.putBoolean("isTransferencia", isTransferencia);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TransferenciaFragment newInstance(int columnCount) {
        TransferenciaFragment fragment = new TransferenciaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = 0;
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transferencia_list, container, false);
        ButterKnife.bind(this,view);

        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapter = new MyTransferenciaRecyclerViewAdapter(getTransferencias("transferencias_ranking_nacional.json"), mListener, getActivity(), true);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private List<Transferencia> getTransferencias(String fileName) {
        String json = loadJSONFromAsset(fileName);
        Type listType = new TypeToken<List<Transferencia>>() {}.getType();
        transferencias = new Gson().fromJson(json, listType);
        return transferencias;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        final MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        adapter.setFilter(transferencias);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private List<Transferencia> filter(List<Transferencia> transferencias, String newText) {
        newText = newText.toLowerCase();
        final List<Transferencia> filteredModelList = new ArrayList<>();
        for (Transferencia model : transferencias) {
            if(model.header==null) {
                String text = model.nm_identif_favorecido_dl.toLowerCase();
                text += model.uf_convenente.toLowerCase();
                text += model.dt_emissao_dl.toLowerCase();
                text += model.nm_identif_favorecido_dl.toLowerCase();
                text += model.nm_orgao_concedente.toLowerCase();
                text += model.vl_bruto_dl.toLowerCase();
                text += model.nm_municipio_convenente.toLowerCase();
                if (text.contains(newText)) {
                    filteredModelList.add(model);
                }
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Transferencia> filteredModelList = filter(transferencias, newText);
        adapter.setFilter(filteredModelList);
        return false;
    }

    public void showNationalRanking(){
        getTransferencias("transferencias_ranking_nacional.json");
        adapter.setData(transferencias);
    }

    public void showRegionalRanking(){
        try {
            String json = loadJSONFromAsset("transferencias_regioes.json");
            TransferenciaRegionRanking transferenciaRegionRanking = new Gson().fromJson(json, TransferenciaRegionRanking.class);
            List<Transferencia> list = new ArrayList<>();
            list.addAll(addHeader(transferenciaRegionRanking.Norte, "Norte"));
            list.addAll(addHeader(transferenciaRegionRanking.Nordeste, "Nordeste"));
            list.addAll(addHeader(transferenciaRegionRanking.CentroOeste, "Centro-Oeste"));
            list.addAll(addHeader(transferenciaRegionRanking.Suldeste, "Suldeste"));
            list.addAll(addHeader(transferenciaRegionRanking.Sul, "Sul"));
            transferencias = list;
            adapter.setData(transferencias);
        } catch (Exception e){
            e.printStackTrace();
            Toast t = Toast.makeText(getContext(),"Ops, tivemos um problema buscando os dados", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    public List<Transferencia> addHeader(List<Transferencia> transferencias, String headerstr){
        List<Transferencia> list = new ArrayList<>();
        Transferencia header = new Transferencia();
        header.header = headerstr;
        if(transferencias!=null && !transferencias.isEmpty()) {
            list.add(header);
            list.addAll(transferencias);
        }
        return list;
    }

    public void showStateRanking(){
        try {
            String json = loadJSONFromAsset("transferencias_estados.json");
            TransferenciaStateRanking transferenciaStateRanking = new Gson().fromJson(json, TransferenciaStateRanking.class);
            List<Transferencia> list = new ArrayList<>();
            list.addAll(addHeader(transferenciaStateRanking.AC, "AC"));
            list.addAll(addHeader(transferenciaStateRanking.AL, "AL"));
            list.addAll(addHeader(transferenciaStateRanking.AM, "AM"));
            list.addAll(addHeader(transferenciaStateRanking.AP, "AP"));
            list.addAll(addHeader(transferenciaStateRanking.BA, "BA"));
            list.addAll(addHeader(transferenciaStateRanking.CE, "CE"));
            list.addAll(addHeader(transferenciaStateRanking.DF, "DF"));
            list.addAll(addHeader(transferenciaStateRanking.ES, "ES"));
            list.addAll(addHeader(transferenciaStateRanking.GO, "GO"));
            list.addAll(addHeader(transferenciaStateRanking.MA, "MA"));
            list.addAll(addHeader(transferenciaStateRanking.MG, "MG"));
            list.addAll(addHeader(transferenciaStateRanking.MS, "MS"));
            list.addAll(addHeader(transferenciaStateRanking.MT, "MT"));
            list.addAll(addHeader(transferenciaStateRanking.PA, "PA"));
            list.addAll(addHeader(transferenciaStateRanking.PB, "PB"));
            list.addAll(addHeader(transferenciaStateRanking.PE, "PE"));
            list.addAll(addHeader(transferenciaStateRanking.PI, "PI"));
            list.addAll(addHeader(transferenciaStateRanking.PR, "PR"));
            list.addAll(addHeader(transferenciaStateRanking.RJ, "RJ"));
            list.addAll(addHeader(transferenciaStateRanking.RN, "RN"));
            list.addAll(addHeader(transferenciaStateRanking.RO, "RO"));
            list.addAll(addHeader(transferenciaStateRanking.RR, "RR"));
            list.addAll(addHeader(transferenciaStateRanking.RS, "RS"));
            list.addAll(addHeader(transferenciaStateRanking.SC, "SC"));
            list.addAll(addHeader(transferenciaStateRanking.SE, "SE"));
            list.addAll(addHeader(transferenciaStateRanking.TO, "TO"));
            transferencias = list;
            adapter.setData(transferencias);
        } catch (Exception e){
            e.printStackTrace();
            Toast t = Toast.makeText(getContext(),"Ops, tivemos um problema buscando os dados", Toast.LENGTH_SHORT);
            t.show();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Transferencia item);
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void setListStatus(String newStatus){
        statusTextView.setText(newStatus);
    }

}
