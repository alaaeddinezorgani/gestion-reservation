package backend;

import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class Personne {
    private String nom;
    private String prenom;
    private String email;
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String newNom) {
        this.nom = newNom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String newPrenom) {
        this.prenom = newPrenom;
    }

}
