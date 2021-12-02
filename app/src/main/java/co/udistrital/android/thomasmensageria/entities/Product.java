package co.udistrital.android.thomasmensageria.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Product {

    @JsonIgnore
    String id;

    String nombre;
    String cliente;
    String estado;
    String precio;


    public Product(){
        precio="0";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
