package biblioteca.dao;

import biblioteca.entity.Prestito;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class PrestitoDAO {

    private EntityManager em;

    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }

    public void effettuaPrestito(Prestito prestito) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(prestito);
        tx.commit();
    }

    public void restituisciPubblicazione(Long idPrestito, LocalDate dataRestituzioneEffettiva) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Prestito prestito = em.find(Prestito.class, idPrestito);
        if (prestito != null) {
            prestito.setDataRestituzioneEffettiva(dataRestituzioneEffettiva);
            em.remove(prestito);
        }
        tx.commit();
    }

    public List<Prestito> getPubblicazioniInPrestito(String numeroTessera) {
        TypedQuery<Prestito> query = em.createQuery("SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera", Prestito.class);
        query.setParameter("numeroTessera", numeroTessera);
        return query.getResultList();
    }

    public List<Prestito> prestitiScaduti() {
        TypedQuery<Prestito> query = em.createQuery("SELECT p FROM Prestito p WHERE p.dataRestituzionePrevista < :today AND p.dataRestituzioneEffettiva IS NULL", Prestito.class);
        query.setParameter("today", LocalDate.now());
        return query.getResultList();
    }

    public List<Prestito> cercaPrestitiPerNumeroTesseraUtente(String numeroTessera) {
        try {
            TypedQuery<Prestito> query = em.createQuery(
                    "SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera", Prestito.class);
            query.setParameter("numeroTessera", numeroTessera);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(STR."Si è verificato un errore durante la ricerca dei prestiti per numero tessera utente: \{e.getMessage()}");
            return Collections.emptyList();
        }
    }
}

