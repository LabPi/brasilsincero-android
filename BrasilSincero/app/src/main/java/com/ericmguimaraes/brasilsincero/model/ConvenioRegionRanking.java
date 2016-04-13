package com.ericmguimaraes.brasilsincero.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ericm on 4/12/2016.
 */
public class ConvenioRegionRanking {

    List<Convenio> Norte;
    List<Convenio> Nordeste;

    @SerializedName("Centro-Oeste")
    List<Convenio> CentroOeste;

    List<Convenio> Suldeste;
    List<Convenio> Sul;

}
