package com.example.catlogoarbol;

public class Arbol {
    private int numero;
    private String nombreCientifico;
    private String nombreComun;
    private String imagenUri;

    public Arbol(int numero, String nombreCientifico, String nombreComun, String imagenUri) {
        this.numero = numero;
        this.nombreCientifico = nombreCientifico;
        this.nombreComun = nombreComun;
        this.imagenUri = imagenUri;
    }

    public int getNumero() { return numero; }
    public String getNombreCientifico() { return nombreCientifico; }
    public String getNombreComun() { return nombreComun; }
    public String getImagenUri() { return imagenUri; }
}
