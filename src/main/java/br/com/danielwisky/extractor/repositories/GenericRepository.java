package br.com.danielwisky.extractor.repositories;

import br.com.danielwisky.extractor.domains.vehicle.Brand;
import br.com.danielwisky.extractor.domains.vehicle.Model;
import br.com.danielwisky.extractor.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class GenericRepository {

  public Object save(final Object object) {
    Transaction transaction = null;
    try {
      final Session session = HibernateUtil.getSessionFactory().openSession();
      transaction = session.beginTransaction();
      session.save(object);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      HibernateUtil.shutdown();
      return object;
    }
  }

  public Model findModelByExternalCode(final String externalCode) {
    try {
      final Session session = HibernateUtil.getSessionFactory().openSession();
      Query query = session.createQuery("from Model where externalCode = :externalCode")
              .setParameter("externalCode", externalCode);
      return (Model) query.uniqueResult();
    } finally {
      HibernateUtil.shutdown();
    }
  }

  public Brand findBrandByName(final String name) {
    try {
      final Session session = HibernateUtil.getSessionFactory().openSession();
      Query query = session.createQuery("from Brand where name = :name")
              .setParameter("name", name);
      return (Brand) query.uniqueResult();
    } finally {
      HibernateUtil.shutdown();
    }
  }
}
