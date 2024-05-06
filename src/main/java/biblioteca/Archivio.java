package biblioteca;

import biblioteca.dao.PrestitoDAO;
import biblioteca.dao.PubblicazioneDAO;
import biblioteca.dao.UtenteDAO;
import biblioteca.entity.Prestito;
import biblioteca.entity.Pubblicazione;
import biblioteca.entity.Utente;

import java.time.LocalDate;
import java.util.Collections;
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
        pubblicazioneDAO.aggiungiPubblicazione(pubblicazione);
    }

    public void rimuoviPubblicazione(long id) {
        pubblicazioneDAO.rimuoviPubblicazione(id);
    }

    public Pubblicazione cercaPubblicazionePerISBN(String isbn) {
        Pubblicazione pubblicazione = pubblicazioneDAO.cercaPerISBN(isbn);
        if (pubblicazione == null) {
            System.out.println(STR."Pubblicazione non trovata per l'ISBN specificato: \{isbn}");
        }
        return pubblicazione;
    }

    public List<Pubblicazione> cercaPubblicazioniPerAnnoPubblicazione(int anno) {
        List<Pubblicazione> result = pubblicazioneDAO.ricercaPerAnnoPubblicazione(anno);
        if (result.isEmpty()) {
            System.out.println("Nessuna pubblicazione trovata per l'anno specificato.");
        }
        return result;
    }

    public List<Pubblicazione> cercaPubblicazioniPerAutore(String autore) {
        List<Pubblicazione> result = pubblicazioneDAO.ricercaPerAutore(autore);
        if (result.isEmpty()) {
            System.out.println("Nessuna pubblicazione trovata per l'autore specificato.");
        }
        return result;
    }

    public List<Pubblicazione> cercaPubblicazioniPerTitolo(String titolo) {
        List<Pubblicazione> result = pubblicazioneDAO.ricercaPerTitolo(titolo);
        if (result.isEmpty()) {
            System.out.println("Nessuna pubblicazione trovata per il titolo specificato.");
        }
        return result;
    }

    public void aggiungiUtente(Utente utente) {
        utenteDAO.aggiungiUtente(utente);
    }

    public void rimuoviUtente(Long id) {
        utenteDAO.rimuoviUtente(id);
    }


    public Utente cercaUtentePerNumeroTessera(String numeroTessera) {
        Utente utente = utenteDAO.cercaPerNumeroTessera(numeroTessera);
        if (utente == null) {
            System.out.println("Nessun utente trovato per il numero di tessera specificato.");
        }
        return utente;
    }


    public void aggiungiPrestito(Prestito prestito) {
        if (prestitoDAO != null) {
            prestitoDAO.effettuaPrestito(prestito);
        } else {
            System.out.println("Impossibile aggiungere il prestito: DAO non disponibile.");
        }
    }



    public List<Prestito> cercaPrestitiPerNumeroTesseraUtente(String numeroTessera) {
        List<Prestito> prestiti = prestitoDAO.cercaPrestitiPerNumeroTesseraUtente(numeroTessera);
        if (prestiti == null) {
            System.out.println("Nessun prestito trovato per il numero di tessera utente specificato.");
            return Collections.emptyList();
        }
        return prestiti;
    }

    public List<Prestito> visualizzaPrestitiScaduti() {
        List<Prestito> prestitiScaduti = prestitoDAO.prestitiScaduti();
        if (prestitiScaduti == null) {
            System.out.println("Nessun prestito scaduto trovato.");
            return Collections.emptyList();
        }
        return prestitiScaduti;
    }

    public List<Pubblicazione> ricercaPubblicazioniInPrestitoPerUtente(String numeroTesseraUtente) {
        List<Prestito> prestitiUtente = prestitoDAO.cercaPrestitiPerNumeroTesseraUtente(numeroTesseraUtente);
        if (prestitiUtente == null || prestitiUtente.isEmpty()) {
            System.out.println("Nessuna pubblicazione in prestito trovata per l'utente specificato.");
            return Collections.emptyList();
        }
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

