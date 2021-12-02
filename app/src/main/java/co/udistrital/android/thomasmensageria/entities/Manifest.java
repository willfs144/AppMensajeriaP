package co.udistrital.android.thomasmensageria.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Manifest {

    @JsonIgnore
    private String id;
    private String idRuta;
    private String estado;
    private String fecha;
    private String novedad;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNovedad() {
        return novedad;
    }

    public void setNovedad(String novedad) {
        this.novedad = novedad;
    }


    public static final String V_NOVEDAD = "novedad";

    public final static String A_ADD ="Mensajero agrega ruta";
    public final static String A_DELETE ="Mensajero inactiva ruta";
}
