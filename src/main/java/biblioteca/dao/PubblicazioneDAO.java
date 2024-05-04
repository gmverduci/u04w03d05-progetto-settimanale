package biblioteca.dao;

import biblioteca.entity.Pubblicazione;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class PubblicazioneDAO {

    private EntityManager em;

    public PubblicazioneDAO(EntityManager em) {
        this.em = em;
    }

    public void aggiungiPubblicazione(Pubblicazione pubblicazione) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(pubblicazione);
        tx.commit();
    }

    public void rimuoviPubblicazione(Long id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Pubblicazione pubblicazione = em.find(Pubblicazione.class, id);
        if (pubblicazione != null) {
            em.remove(pubblicazione);
        }
        tx.commit();
    }

    public Pubblicazione cercaPerISBN(String isbn) {
        try {
            TypedQuery<Pubblicazione> query = em.createQuery("SELECT p FROM Pubblicazione p WHERE p.isbn = :isbn", Pubblicazione.class)
                    .setParameter("isbn", isbn);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Pubblicazione> ricercaPerAnnoPubblicazione(int anno) {
        TypedQuery<Pubblicazione> query = em.createQuery("SELECT p FROM Pubblicazione p WHERE p.annoPubblicazione = :anno", Pubblicazione.class);
        query.setParameter("anno", anno);
        return query.getResultList();
    }

    public List<Pubblicazione> ricercaPerAutore(String autore) {
        TypedQuery<Pubblicazione> query = em.createQuery("SELECT p FROM Pubblicazione p WHERE p.autore = :autore", Pubblicazione.class);
        query.setParameter("autore", autore);
        return query.getResultList();
    }

    public List<Pubblicazione> ricercaPerTitolo(String titolo) {
        TypedQuery<Pubblicazione> query = em.createQuery("SELECT p FROM Pubblicazione p WHERE p.titolo LIKE :titolo", Pubblicazione.class);
        query.setParameter("titolo", "%" + titolo + "%");
        return query.getResultList();
    }
}

