package biblioteca.entity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "Pubblicazione.findByISBN", query = "SELECT p FROM Pubblicazione p WHERE p.isbn = :isbn"),
        @NamedQuery(name = "Pubblicazione.findByAnno", query = "SELECT p FROM Pubblicazione p WHERE p.annoPubblicazione = :anno"),
        @NamedQuery(name = "Pubblicazione.findByAutore", query = "SELECT l FROM Libro l WHERE l.autore = :autore"),
        @NamedQuery(name = "Pubblicazione.findByTitolo", query = "SELECT p FROM Pubblicazione p WHERE p.titolo LIKE :titolo")
})
public abstract class Pubblicazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String isbn;
    private String titolo;
    private int annoPubblicazione;
    private int numeroPagine;
    private int quantita;

    public Pubblicazione() {
    }

    public Pubblicazione(String isbn, String titolo, int annoPubblicazione, int numeroPagine, int quantita) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;
        this.quantita = quantita;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(int annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    public void setNumeroPagine(int numeroPagine) {
        this.numeroPagine = numeroPagine;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return "Pubblicazione{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", numeroPagine=" + numeroPagine +
                ", quantita=" + quantita +
                '}';
    }
}
