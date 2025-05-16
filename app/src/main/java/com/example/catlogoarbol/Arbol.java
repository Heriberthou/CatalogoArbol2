package com.example.catlogoarbol;

public class Arbol {
    private int numero;
    private String nombreCientifico;
    private String nombreComun;
    private String coordenadas;
    private float diametro;
    private float altura;
    private int hojas;
    private int flores;
    private int frutos;
    private String madurez;
    private String estadoHojas;
    private String interaccion;
    private String organismo;
    private String observaciones;
    private String imagenUri;

    // Constructor completo (para usar con DatabaseHelper)
    public Arbol(int numero, String nombreCientifico, String nombreComun, String coordenadas,
                 float diametro, float altura, String hojas, String flores, String frutos,
                 String madurez, String estadoHojas, String interaccion,
                 String organismo, String observaciones, String imagenUri) {
        this.numero = numero;
        this.nombreCientifico = nombreCientifico;
        this.nombreComun = nombreComun;
        this.coordenadas = coordenadas;
        this.diametro = diametro;
        this.altura = altura;
        this.hojas = hojas;
        this.flores = flores;
        this.frutos = frutos;
        this.madurez = madurez;
        this.estadoHojas = estadoHojas;
        this.interaccion = interaccion;
        this.organismo = organismo;
        this.observaciones = observaciones;
        this.imagenUri = imagenUri;
    }

    // Getters
    public int getNumero() { return numero; }
    public String getNombreCientifico() { return nombreCientifico; }
    public String getNombreComun() { return nombreComun; }
    public String getCoordenadas() { return coordenadas; }
    public float getDiametro() { return diametro; }
    public float getAltura() { return altura; }
    public int getHojas() { return hojas; }
    public int getFlores() { return flores; }
    public int getFrutos() { return frutos; }
    public String getMadurez() { return madurez; }
    public String getEstadoHojas() { return estadoHojas; }
    public String getInteraccion() { return interaccion; }
    public String getOrganismo() { return organismo; }
    public String getObservaciones() { return observaciones; }
    public String getImagenUri() { return imagenUri; }

    // Setters (si los necesitas)
    public void setImagenUri(String imagenUri) { this.imagenUri = imagenUri; }
}
