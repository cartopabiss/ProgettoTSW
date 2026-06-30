package model;

import java.io.Serializable;

public class RigaCarrelloBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private ProdottoBean prodotto;
    private int quantita;

    public RigaCarrelloBean() {
    }

    public ProdottoBean getProdotto() {
        return prodotto;
    }

    public void setProdotto(ProdottoBean prodotto) {
        this.prodotto = prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getSubtotale() {
        if (prodotto == null) {
            return 0.0;
        }
        return prodotto.getPrezzo() * quantita;
    }
}