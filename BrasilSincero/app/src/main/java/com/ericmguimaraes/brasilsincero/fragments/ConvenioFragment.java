package com.ericmguimaraes.brasilsincero.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.ericmguimaraes.brasilsincero.FilterActivity;
import com.ericmguimaraes.brasilsincero.R;
import com.ericmguimaraes.brasilsincero.adapters.MyConvenioRecyclerViewAdapter;
import com.ericmguimaraes.brasilsincero.fragments.dummy.DummyContent;
import com.ericmguimaraes.brasilsincero.fragments.dummy.DummyContent.DummyItem;
import com.ericmguimaraes.brasilsincero.model.Convenio;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ConvenioFragment extends Fragment implements SearchView.OnQueryTextListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    @Bind(R.id.list)
    RecyclerView recyclerView;

    @Bind(R.id.status)
    TextView statusTextView;

    private boolean isConvenio = true;

    MyConvenioRecyclerViewAdapter adapter;

    List<Convenio> convenios;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ConvenioFragment() {
    }

    public static ConvenioFragment newInstance(boolean isConvenio) {
        ConvenioFragment fragment = new ConvenioFragment();
        Bundle args = new Bundle();
        args.putBoolean("isConvenio", isConvenio);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ConvenioFragment newInstance(int columnCount) {
        ConvenioFragment fragment = new ConvenioFragment();
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
            isConvenio = getArguments().getBoolean("isConvenio");
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_convenio_list, container, false);
        ButterKnife.bind(this,view);

        Context context = view.getContext();
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        adapter = new MyConvenioRecyclerViewAdapter(getConvenios("convenios_ranking_nacional.json"), mListener, getActivity(), isConvenio);
        recyclerView.setAdapter(adapter);
        return view;
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
                        adapter.setFilter(convenios);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });
    }

    private List<Convenio> getConvenios(String fileName) {
        Gson gson = new Gson();
        String json = loadJSONFromAsset(fileName);
        Type listType = new TypeToken<List<Convenio>>() {}.getType();
        convenios = new Gson().fromJson(json, listType);
        return convenios;
    }

    public void showNationalRanking(){
        getConvenios("convenios_ranking_nacional.json");
        adapter.setData(convenios);

    }

    public void showRegionalRanking(){
        getConvenios("convenios_ranking_nacional.json");
        adapter.setData(convenios);
    }

    public void showStateRanking(){
        getConvenios("convenios_ranking_nacional.json");
        adapter.setData(convenios);
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

    private List<Convenio> filter(List<Convenio> convenios, String newText) {
        newText = newText.toLowerCase();
        final List<Convenio> filteredModelList = new ArrayList<>();
        for (Convenio model : convenios) {
            String text = model.nm_respons_proponente.toLowerCase();
            text+=model.vl_global.toLowerCase();
            text+=model.uf_proponente.toLowerCase();
            text+=model.dt_proposta.toLowerCase();
            text+=model.nm_municipio_proponente.toLowerCase();
            text+=model.uf_proponente.toLowerCase();
            text+=model.nm_programa.toLowerCase();
            if (text.contains(newText)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Convenio> filteredModelList = filter(convenios, newText);
        adapter.setFilter(filteredModelList);
        return false;
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
        void onListFragmentInteraction(Convenio item);
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

}
