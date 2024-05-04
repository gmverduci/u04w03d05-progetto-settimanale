package biblioteca.dao;

import biblioteca.entity.Prestito;
import biblioteca.entity.Utente;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class UtenteDAO {

    private EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void aggiungiUtente(Utente utente) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(utente);
        tx.commit();
    }

    public void rimuoviUtente(Long id) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Utente utente = em.find(Utente.class, id);
        if (utente != null) {
            em.remove(utente);
        }
        tx.commit();
    }

    public Utente cercaPerNumeroTessera(String numeroTessera) {
        try {
            TypedQuery<Utente> query = em.createNamedQuery("Utente.findByNumeroTessera", Utente.class)
                    .setParameter("numeroTessera", numeroTessera);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Prestito> getPubblicazioniInPrestito(String numeroTessera) {
        TypedQuery<Prestito> query = em.createQuery("SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numeroTessera", Prestito.class);
        query.setParameter("numeroTessera", numeroTessera);
        return query.getResultList();
    }

    public List<Utente> ricercaPerNome(String nome) {
        TypedQuery<Utente> query = em.createQuery("SELECT u FROM Utente u WHERE u.nome LIKE :nome", Utente.class);
        query.setParameter("nome", "%" + nome + "%");
        return query.getResultList();
    }

    public List<Utente> ricercaPerCognome(String cognome) {
        TypedQuery<Utente> query = em.createQuery("SELECT u FROM Utente u WHERE u.cognome LIKE :cognome", Utente.class);
        query.setParameter("cognome", "%" + cognome + "%");
        return query.getResultList();
    }

    public List<Utente> ricercaPerDataNascita(int anno) {
        TypedQuery<Utente> query = em.createQuery("SELECT u FROM Utente u WHERE YEAR(u.dataNascita) = :anno", Utente.class);
        query.setParameter("anno", anno);
        return query.getResultList();
    }
}
