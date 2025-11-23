package com.example.trabalhofinal;

import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public class Trilha {
    private long id;
    private String nome;
    private String dataHoraInicio;
    private String dataHoraFim;
    private String duracao; // NOVO CAMPO
    private float gastoCalorico;
    private float velocidadeMedia;
    private float velocidadeMaxima;
    private float distanciaTotal;
    private List<LatLng> coordenadas;
    private int mapType;

    public Trilha() {
        this.coordenadas = new ArrayList<>();
    }

    // Getters e Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(String dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public String getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(String dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public float getGastoCalorico() {
        return gastoCalorico;
    }

    public void setGastoCalorico(float gastoCalorico) {
        this.gastoCalorico = gastoCalorico;
    }

    public float getVelocidadeMedia() {
        return velocidadeMedia;
    }

    public void setVelocidadeMedia(float velocidadeMedia) {
        this.velocidadeMedia = velocidadeMedia;
    }

    public float getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    public void setVelocidadeMaxima(float velocidadeMaxima) {
        this.velocidadeMaxima = velocidadeMaxima;
    }

    public float getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setDistanciaTotal(float distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public List<LatLng> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<LatLng> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }
}
