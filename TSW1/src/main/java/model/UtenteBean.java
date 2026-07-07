package model;

import java.io.Serializable;

public class UtenteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idUtente;
    private String nome;
    private String cognome;
    private String email;
    private String passwordHash;
    private String indirizzo;
    private String citta;
    private String cap;

    public UtenteBean() {
    }

    public int getIdUtente() {
        return idUtente;
    }
    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCitta() {
        return citta;
    }
    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getCap() {
        return cap;
    }
    public void setCap(String cap) {
        this.cap = cap;
    }
}