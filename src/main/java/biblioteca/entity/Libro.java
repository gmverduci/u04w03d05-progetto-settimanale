package biblioteca.entity;

import javax.persistence.Entity;

@Entity
public class Libro extends Pubblicazione {

    private String autore;
    private String genere;

    public Libro() {
    }

    public Libro(String isbn, String titolo, int annoPubblicazione, int numeroPagine, int quantita, String autore, String genere) {
        super(isbn, titolo, annoPubblicazione, numeroPagine, quantita);
        this.autore = autore;
        this.genere = genere;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "ISBN='" + getIsbn() + '\'' +
                ", titolo='" + getTitolo() + '\'' +
                ", anno pubblicazione=" + getAnnoPubblicazione() +
                ", numero pagine=" + getNumeroPagine() +
                ", autore='" + autore + '\'' +
                ", genere='" + genere + '\'' +
                '}';
    }
}
