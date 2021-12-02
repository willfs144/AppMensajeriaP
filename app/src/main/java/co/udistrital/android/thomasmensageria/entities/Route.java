package co.udistrital.android.thomasmensageria.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Route {

    @JsonIgnore
    String id;

    int idproducto;
    int consecutivo;
    int idservicio;
    int cantidad;
    String idmensajero;
    String servicio;
    String origen;
    String fecha_envio;
    String fecha_entrega;
    String direccion;
    String barrio;
    String ciudad;
    String estado;
    String observacion;
    String telefono;
    String autorizacion;
    String valor_recaudo;
    Boolean recaudo;
    Boolean validado;
    Boolean activo;
    Boolean cc_validada;

    double latitude;
    double longitude;

    public Route(){
    }

    public int getIdservicio() {
        return idservicio;
    }

    public void setIdservicio(int idservicio) {
        this.idservicio = idservicio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public  String getIdmensajero() {
        return idmensajero;
    }

    public void setIdmensajero(String idmensajero) {
        this.idmensajero = idmensajero;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getFecha_envio() {
        return fecha_envio;
    }

    public void setFecha_envio(String fecha_envio) {
        this.fecha_envio = fecha_envio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validar) {
        this.validado = validar;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getCc_validada() {
        return cc_validada;
    }

    public void setCc_validada(Boolean cc_validada) {
        this.cc_validada = cc_validada;
    }

    public int getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(int consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getValor_recaudo() {
        return valor_recaudo;
    }

    public void setValor_recaudo(String valor_recaudo) {
        this.valor_recaudo = valor_recaudo;
    }

    public Boolean getRecaudo() {
        return recaudo;
    }

    public void setRecaudo(Boolean recaudo) {
        this.recaudo = recaudo;
    }

    public boolean equals(Object object){
        boolean equal = false;

        if (object instanceof Route){
            Route route = (Route) object;
            equal = this.consecutivo == route.getConsecutivo();
        }
        return equal;
    }

    public static final String V_ESTADO = "estado";

    public static final String A_ACTIVO = "activo";
    public static final String A_NOENTREGADO = "no entregado";
    public static final String A_ENTREGADO = "entregado";
    public static final String A_VALIDATE ="En proceso de entrega";
    public static final String A_INACTIVO = "inactivo";

    public final static String V_VALIDADO ="validado";

    public final static boolean A_TRUE =true;
    public final static boolean A_FALSE =false;

    public final static String V_CCVALIDADA ="cc_validada";
    public final static boolean A_CCVALIDADA =true;
    public final static String V_RECAUDO ="recaudo";
    public final static boolean A_RECAUDO =true;

}
