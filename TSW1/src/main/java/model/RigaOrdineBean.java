package model;

import java.io.Serializable;

public class RigaOrdineBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idRiga;
    private int idOrdine;
    private Integer idProdotto;
    private String nomeProdotto;
    private double prezzoUnitario;
    private int quantita;
    private double subtotale;

    public RigaOrdineBean() {}

    public int getIdRiga() {
        return idRiga;
    }
    public void setIdRiga(int idRiga) {
        this.idRiga = idRiga;
    }

    public int getIdOrdine() {
        return idOrdine;
    }
    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public Integer getIdProdotto() {
        return idProdotto;
    }
    public void setIdProdotto(Integer idProdotto) {
        this.idProdotto = idProdotto;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }
    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }
    public void setPrezzoUnitario(double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }

    public int getQuantita() {
        return quantita;
    }
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getSubtotale() {
        return subtotale;
    }
    public void setSubtotale(double subtotale) {
        this.subtotale = subtotale;
    }
}