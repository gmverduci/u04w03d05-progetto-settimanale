package biblioteca;

import biblioteca.dao.PrestitoDAO;
import biblioteca.dao.PubblicazioneDAO;
import biblioteca.dao.UtenteDAO;
import biblioteca.entity.Libro;
import biblioteca.entity.Prestito;
import biblioteca.entity.Pubblicazione;
import biblioteca.entity.Utente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        EntityManager em = emf.createEntityManager();

        PubblicazioneDAO pubblicazioneDAO = new PubblicazioneDAO(em);
        UtenteDAO utenteDAO = new UtenteDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);

        Archivio archivio = new Archivio(pubblicazioneDAO, utenteDAO, prestitoDAO);

        Scanner scanner = new Scanner(System.in);
        int scelta;

        do {
            System.out.println("Seleziona un'opzione:");
            System.out.println("1. Aggiungi pubblicazione");
            System.out.println("2. Rimuovi pubblicazione");
            System.out.println("3. Aggiungi utente");
            System.out.println("4. Rimuovi utente");
            System.out.println("5. Effettua prestito");
            System.out.println("6. Restituisci pubblicazione");
            System.out.println("7. Visualizza prestiti scaduti");
            System.out.println("8. Ricerca pubblicazioni");
            System.out.println("9. Ricerca pubblicazioni in prestito per utente");
            System.out.println("10. Esci");

            scelta = scanner.nextInt();

            switch (scelta) {
                case 1:
                    System.out.println("Inserisci i dati della pubblicazione:");
                    System.out.print("ISBN: ");
                    String isbn = scanner.next();
                    System.out.print("Titolo: ");
                    String titolo = scanner.next();
                    System.out.print("Anno di pubblicazione: ");
                    int annoPubblicazione = scanner.nextInt();
                    System.out.print("Numero pagine: ");
                    int numeroPagine = scanner.nextInt();
                    System.out.print("Quantità: ");
                    int quantita = scanner.nextInt();
                    System.out.print("Autore: ");
                    String autore = scanner.next();
                    System.out.print("Genere: ");
                    String genere = scanner.next();
                    Pubblicazione nuovaPubblicazione = new Libro(isbn, titolo, annoPubblicazione, numeroPagine, quantita, autore, genere);
                    archivio.aggiungiPubblicazione(nuovaPubblicazione);
                    break;

                case 2:
                    System.out.print("Inserisci l'id della pubblicazione da rimuovere: ");
                    long id = scanner.nextLong();
                    archivio.rimuoviPubblicazione(id);
                    break;
                case 3:
                    System.out.println("Inserisci i dati dell'utente:");
                    System.out.print("Nome: ");
                    String nomeUtente = scanner.next();
                    System.out.print("Cognome: ");
                    String cognomeUtente = scanner.next();
                    System.out.print("Data di nascita (YYYY-MM-DD): ");
                    String dataNascitaUtente = scanner.next();
                    LocalDate dataNascita = LocalDate.parse(dataNascitaUtente);
                    System.out.print("Numero tessera: ");
                    String numeroTessera = scanner.next();
                    Utente nuovoUtente = new Utente(nomeUtente, cognomeUtente, dataNascita, numeroTessera);
                    archivio.aggiungiUtente(nuovoUtente);
                    break;
                case 4:
                    System.out.print("Inserisci il numero tessera dell'utente da rimuovere: ");
                    String numeroTesseraDaRimuovere = scanner.next();
                    archivio.rimuoviUtente(Long.valueOf(numeroTesseraDaRimuovere));
                    break;
                case 5:
                    System.out.println("Inserisci i dati del prestito:");
                    System.out.print("ISBN della pubblicazione: ");
                    String isbnPrestito = scanner.next();
                    System.out.print("Numero tessera dell'utente: ");
                    String numeroTesseraPrestito = scanner.next();
                    Pubblicazione pubblicazionePrestito = archivio.cercaPubblicazionePerISBN(isbnPrestito);
                    Utente utentePrestito = archivio.cercaUtentePerNumeroTessera(numeroTesseraPrestito);
                    if (pubblicazionePrestito != null && utentePrestito != null) {
                        LocalDate dataInizioPrestito = LocalDate.now();
                        LocalDate dataRestituzionePrevista = LocalDate.now().plusDays(30);
                        Prestito nuovoPrestito = new Prestito(utentePrestito, pubblicazionePrestito, dataInizioPrestito, dataRestituzionePrevista, null);
                        archivio.aggiungiPrestito(nuovoPrestito);
                    } else {
                        System.out.println("Pubblicazione o utente non trovato.");
                    }
                    break;
                case 6:
                    System.out.print("Inserisci l'ISBN della pubblicazione da restituire: ");
                    String isbnPubblicazioneDaRestituire = scanner.next();
                    System.out.print("Inserisci il numero tessera dell'utente: ");
                    String numeroTesseraUtentePerRestituzione = scanner.next();
                    archivio.rimuoviPrestito(isbnPubblicazioneDaRestituire, numeroTesseraUtentePerRestituzione);
                    break;
                case 7:
                    List<Prestito> prestitiScaduti = archivio.visualizzaPrestitiScaduti();
                    if (prestitiScaduti != null && !prestitiScaduti.isEmpty()) {
                        for (Prestito prestito : prestitiScaduti) {
                            System.out.println(prestito);
                        }
                    } else {
                        System.out.println("Nessun prestito scaduto trovato.");
                    }
                    break;
                case 8:
                    System.out.print("Inserisci una parola chiave per la ricerca: ");
                    String parola = scanner.next();
                    List<Pubblicazione> risultatiRicerca = archivio.cercaPubblicazioniPerTitolo(parola);
                    if (risultatiRicerca != null && !risultatiRicerca.isEmpty()) {
                        for (Pubblicazione pubblicazione : risultatiRicerca) {
                            System.out.println(pubblicazione);
                        }
                    } else {
                        System.out.println("Nessuna pubblicazione trovata per la parola chiave inserita.");
                    }
                    break;
                case 9:
                    System.out.print("Inserisci il numero tessera dell'utente: ");
                    String numeroTesseraUtente = scanner.next();
                    List<Pubblicazione> pubblicazioniInPrestito = archivio.ricercaPubblicazioniInPrestitoPerUtente(numeroTesseraUtente);
                    if (pubblicazioniInPrestito != null && !pubblicazioniInPrestito.isEmpty()) {
                        for (Pubblicazione pubblicazione : pubblicazioniInPrestito) {
                            System.out.println(pubblicazione);
                        }
                    } else {
                        System.out.println("Nessuna pubblicazione attualmente in prestito per l'utente specificato.");
                    }
                    break;
                case 10:
                    System.out.println("Arrivederci!");
                    break;
                default:
                    System.out.println("Scelta non valida. Riprova.");
            }
        } while (scelta != 10);

        scanner.close();
        em.close();
        emf.close();
    }
}
