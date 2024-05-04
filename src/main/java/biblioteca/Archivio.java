package biblioteca;

import biblioteca.dao.PrestitoDAO;
import biblioteca.dao.PubblicazioneDAO;
import biblioteca.dao.UtenteDAO;
import biblioteca.entity.Prestito;
import biblioteca.entity.Pubblicazione;
import biblioteca.entity.Utente;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Archivio {
    private PubblicazioneDAO pubblicazioneDAO;
    private UtenteDAO utenteDAO;
    private PrestitoDAO prestitoDAO;

    public Archivio(PubblicazioneDAO pubblicazioneDAO, UtenteDAO utenteDAO, PrestitoDAO prestitoDAO) {
        this.pubblicazioneDAO = pubblicazioneDAO;
        this.utenteDAO = utenteDAO;
        this.prestitoDAO = prestitoDAO;
    }

    public void aggiungiPubblicazione(Pubblicazione pubblicazione) {
        if (pubblicazioneDAO != null) {
            pubblicazioneDAO.aggiungiPubblicazione(pubblicazione);
        } else {
            System.out.println("Impossibile aggiungere la pubblicazione: DAO non disponibile.");
        }
    }

    public void rimuoviPubblicazione(long id) {
        if (pubblicazioneDAO != null) {
            pubblicazioneDAO.rimuoviPubblicazione(id);
        } else {
            System.out.println("Impossibile rimuovere la pubblicazione: DAO non disponibile.");
        }
    }

    public Pubblicazione cercaPubblicazionePerISBN(String isbn) {
        if (pubblicazioneDAO != null) {
            return pubblicazioneDAO.cercaPerISBN(isbn);
        } else {
            System.out.println("Impossibile cercare la pubblicazione: DAO non disponibile.");
            return null;
        }
    }

    public List<Pubblicazione> cercaPubblicazioniPerAnnoPubblicazione(int anno) {
        if (pubblicazioneDAO != null) {
            return pubblicazioneDAO.ricercaPerAnnoPubblicazione(anno);
        } else {
            System.out.println("Impossibile cercare le pubblicazioni: DAO non disponibile.");
            return null;
        }
    }

    public List<Pubblicazione> cercaPubblicazioniPerAutore(String autore) {
        if (pubblicazioneDAO != null) {
            return pubblicazioneDAO.ricercaPerAutore(autore);
        } else {
            System.out.println("Impossibile cercare le pubblicazioni: DAO non disponibile.");
            return null;
        }
    }

    public List<Pubblicazione> cercaPubblicazioniPerTitolo(String titolo) {
        if (pubblicazioneDAO != null) {
            return pubblicazioneDAO.ricercaPerTitolo(titolo);
        } else {
            System.out.println("Impossibile cercare le pubblicazioni: DAO non disponibile.");
            return null;
        }
    }

    public void aggiungiUtente(Utente utente) {
        if (utenteDAO != null) {
            utenteDAO.aggiungiUtente(utente);
        } else {
            System.out.println("Impossibile aggiungere l'utente: DAO non disponibile.");
        }
    }

    public void rimuoviUtente(Long id) {
        if (utenteDAO != null) {
            utenteDAO.rimuoviUtente(id);
        } else {
            System.out.println("Impossibile rimuovere l'utente: DAO non disponibile.");
        }
    }

    public Utente cercaUtentePerNumeroTessera(String numeroTessera) {
        if (utenteDAO != null) {
            return utenteDAO.cercaPerNumeroTessera(numeroTessera);
        } else {
            System.out.println("Impossibile cercare l'utente: DAO non disponibile.");
            return null;
        }
    }

    public void aggiungiPrestito(Prestito prestito) {
        if (prestitoDAO != null) {
            prestitoDAO.effettuaPrestito(prestito);
        } else {
            System.out.println("Impossibile aggiungere il prestito: DAO non disponibile.");
        }
    }



    public List<Prestito> cercaPrestitiPerNumeroTesseraUtente(String numeroTessera) {
        if (prestitoDAO != null) {
            return prestitoDAO.cercaPrestitiPerNumeroTesseraUtente(numeroTessera);
        } else {
            System.out.println("Impossibile cercare i prestiti: DAO non disponibile.");
            return null;
        }
    }

    public List<Prestito> visualizzaPrestitiScaduti() {
        if (prestitoDAO != null) {
            return prestitoDAO.prestitiScaduti();
        } else {
            System.out.println("Impossibile visualizzare i prestiti: DAO non disponibile.");
            return null;
        }
    }

    public List<Pubblicazione> ricercaPubblicazioniInPrestitoPerUtente(String numeroTesseraUtente) {
            List<Prestito> prestitiUtente = prestitoDAO.cercaPrestitiPerNumeroTesseraUtente(numeroTesseraUtente);
            return prestitiUtente.stream()
                    .map(Prestito::getPubblicazione)
                    .collect(Collectors.toList());

    }

    public void rimuoviPrestito(String isbn, String numeroTessera) {
        List<Prestito> prestiti = prestitoDAO.cercaPrestitiPerNumeroTesseraUtente(numeroTessera);
        Prestito prestitoDaRimuovere = prestiti.stream()
                .filter(p -> p.getPubblicazione().getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);

        if (prestitoDaRimuovere != null) {
            Long idPrestito = prestitoDaRimuovere.getId();

            prestitoDAO.restituisciPubblicazione(idPrestito, LocalDate.now());
        } else {
            System.out.println("Prestito non trovato per l'ISBN specificato.");
        }
    }
}

