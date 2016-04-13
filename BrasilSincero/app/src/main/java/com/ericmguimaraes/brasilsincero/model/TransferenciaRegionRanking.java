package com.ericmguimaraes.brasilsincero.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ericm on 4/12/2016.
 */
public class TransferenciaRegionRanking {

    public List<Transferencia> Norte;
    public List<Transferencia> Nordeste;

    @SerializedName("Centro-Oeste")
    public List<Transferencia> CentroOeste;

    public List<Transferencia> Suldeste;
    public List<Transferencia> Sul;

}
