package com.ericmguimaraes.brasilsincero.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ericm on 4/12/2016.
 */
public class ConvenioRegionRanking {

    public List<Convenio> Norte;
    public List<Convenio> Nordeste;

    @SerializedName("Centro-Oeste")
    public List<Convenio> CentroOeste;

    public List<Convenio> Suldeste;
    public List<Convenio> Sul;

}
