package biblioteca.entity;

import biblioteca.enums.Periodicita;

import javax.persistence.*;

@Entity
@DiscriminatorValue("rivista")
@NamedQuery(name = "Rivista.findByIsbn", query = "SELECT r FROM Rivista r WHERE r.isbn = :isbn")
@NamedQuery(name = "Rivista.findByAnnoPubblicazione", query = "SELECT r FROM Rivista r WHERE r.annoPubblicazione = :annoPubblicazione")
public class Rivista extends Pubblicazione {
    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;


    public Rivista(String isbn, String titolo, int annoPubblicazione, int numeroPagine, int quantita, Periodicita periodicita) {
        super(isbn, titolo, annoPubblicazione, numeroPagine, quantita);
        this.periodicita = periodicita;
    }

    public Rivista() {
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return "Rivista{" +
                "periodicita=" + periodicita +
                "} " + super.toString();
    }
}

