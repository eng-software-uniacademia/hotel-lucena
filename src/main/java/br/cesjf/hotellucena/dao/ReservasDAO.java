package br.cesjf.hotellucena.dao;

import br.cesjf.hotellucena.model.Reservas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.cesjf.hotellucena.util.PersistenceUtil;
import java.util.Date;

public class ReservasDAO {
    public List<Reservas> buscarReservas(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();

        Query query = em.createQuery("select a from Reservas a where a.usuarioscodUsuario.codUsuario =:id");
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Reservas> buscarReservasApartamento(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();

        Query query = em.createQuery("select a from Reservas a where a.apartamentocodigoApartamento.idApartamento =:id");
        query.setParameter("id", id);
        return query.getResultList();
    }
    
    public Reservas buscar(String nome) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select a from Reservas a where a.codigoReserva =:nome ");
        query.setParameter("nome", nome);

        List<Reservas> reservas = query.getResultList();
        if (reservas != null && !reservas.isEmpty()) {
            return reservas.get(0);
        }

        return null;
    }

    public List<Reservas> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("from Reservas As a");
        return query.getResultList();
    }

    public List<Reservas> buscarAtivos() {
        Date hoje = new Date();
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("from Reservas As a where a.status not like 'Concluído' and a.dataEntrada >= :hoje ");
        query.setParameter("hoje", hoje);
        return query.getResultList();
    }

    public void remover(Reservas reserva) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(reserva)) {
            reserva = em.merge(reserva);
        }
        em.remove(reserva);
        em.getTransaction().commit();
    }

    public Reservas persistir(Reservas reserva) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        reserva = em.merge(reserva);
        em.getTransaction().commit();
        return reserva;
    }

    public void checkin(int reserva) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("update Reservas set status = 'Ocupado' where codigoReserva = :nome");
        query.setParameter("nome", reserva);
        query.executeUpdate();
        em.getTransaction().commit();
    }

    public void checkout(int reserva) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("update Reservas set status = 'Concluído' where codigoReserva = :nome");
        query.setParameter("nome", reserva);
        query.executeUpdate();
        em.getTransaction().commit();
    }

    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(" delete from Reservas ");
        query.executeUpdate();
        em.getTransaction().commit();
    }

}
