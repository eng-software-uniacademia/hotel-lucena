package br.cesjf.hotellucena.dao;

import br.cesjf.hotellucena.model.Categoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.cesjf.hotellucena.util.PersistenceUtil;

public class CategoriaDAO {

    public final CategoriaDAO usuarioDAO = new CategoriaDAO();

    public Categoria buscar(String nome) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select a from Categoria a where a.name =:nome ");
        query.setParameter("nome", nome);

        List<Categoria> categoria = query.getResultList();
        if (categoria != null && !categoria.isEmpty()) {
            return categoria.get(0);
        }

        return null;
    }

    public List<Categoria> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("from Categoria As a");
        return query.getResultList();
    }

    public void remover(Categoria usuario) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(usuario)) {
            usuario = em.merge(usuario);
        }
        em.remove(usuario);
        em.getTransaction().commit();
    }

    public Categoria persistir(Categoria usuario) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        usuario = em.merge(usuario);
        em.getTransaction().commit();
        return usuario;
    }

    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(" delete from Categoria ");
        query.executeUpdate();
        em.getTransaction().commit();
    }

}
