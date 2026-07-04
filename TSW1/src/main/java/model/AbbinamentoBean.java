package model;

public class AbbinamentoBean {

    private int idAbbinamento;
    private ProdottoBean prodotto1;
    private ProdottoBean prodotto2;

    public int getIdAbbinamento() {
        return idAbbinamento;
    }

    public void setIdAbbinamento(int idAbbinamento) {
        this.idAbbinamento = idAbbinamento;
    }

    public ProdottoBean getProdotto1() {
        return prodotto1;
    }

    public void setProdotto1(ProdottoBean prodotto1) {
        this.prodotto1 = prodotto1;
    }

    public ProdottoBean getProdotto2() {
        return prodotto2;
    }

    public void setProdotto2(ProdottoBean prodotto2) {
        this.prodotto2 = prodotto2;
    }
}