package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarrelloBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Map<Integer, RigaCarrelloBean> righe;

    public CarrelloBean() {
        this.righe = new LinkedHashMap<>();
    }

    public Collection<RigaCarrelloBean> getRighe() {
        return righe.values(); //.values() ritorna un lista di oggetti RigaCarrello senza id 
    }
    
    public RigaCarrelloBean getRigaByIdProdotto(int idProdotto) {

        return righe.get(idProdotto);

    }

    public boolean isEmpty() {
        return righe.isEmpty();
    }

    public void aggiungiProdotto(ProdottoBean prodotto, int quantita) {
        if (prodotto == null || quantita <= 0) {
            return;
        }
        
        //uso il metodo .get() sulla mappa, che ritorna la riga con id del prodotto da aggiungere
        RigaCarrelloBean riga = righe.get(prodotto.getIdProdotto());

        if (riga == null) {// se non c'è corrispondenza 
            riga = new RigaCarrelloBean();
            riga.setProdotto(prodotto);
            riga.setQuantita(quantita);
            righe.put(prodotto.getIdProdotto(), riga);
        } else {
            riga.setQuantita(riga.getQuantita() + quantita);
        }
    }

    public void aggiornaQuantita(int idProdotto, int quantita) {
        RigaCarrelloBean riga = righe.get(idProdotto);

        if (riga == null) {
            return;
        }

        if (quantita <= 0) {
            righe.remove(idProdotto);
        } else {
            riga.setQuantita(quantita);
        }
    }

    public void rimuoviProdotto(int idProdotto) {
        righe.remove(idProdotto);
    }

    public void svuota() {
        righe.clear();
    }

    public double getTotale() {
        double totale = 0.0;

        for (RigaCarrelloBean riga : righe.values()) {
            totale += riga.getSubtotale();
        }
        return totale;
    }

    public int getNumeroProdotti() {
        int totale = 0;

        for (RigaCarrelloBean riga : righe.values()) {
            totale += riga.getQuantita();
        }
        return totale;
    }
}