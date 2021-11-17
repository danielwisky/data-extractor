package br.com.danielwisky.extractor.repositories;

import br.com.danielwisky.extractor.domains.vehicle.Brand;
import br.com.danielwisky.extractor.domains.vehicle.GroupModel;
import br.com.danielwisky.extractor.domains.vehicle.Model;
import br.com.danielwisky.extractor.domains.vehicle.Vehicle;
import br.com.danielwisky.extractor.utils.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class GenericRepository {

  public Object save(final Object object) {
    Transaction transaction = null;
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    try {
      final Session session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      session.merge(object);
      transaction.commit();
    } catch (Exception e) {
      e.printStackTrace();
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      return object;
    }
  }

  public Model findModelByExternalCode(final String externalCode) {
    final Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("from Model where externalCode = :externalCode")
            .setParameter("externalCode", externalCode);
    return (Model) query.uniqueResult();
  }

  public static Brand findBrandByName(final String name) {
    Brand brand = null;

    try {
      final Session session = HibernateUtil.getSessionFactory().openSession();
      final Query query = session.createQuery("from Brand where name = :name").setParameter("name", name);
      brand = (Brand) query.uniqueResult();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      return brand;
    }
  }

  public List<Brand> findBrands() {
    final Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("from Brand");
    return query.getResultList();
  }

  public GroupModel findGroupModelByName(final String name) {
    final Session session = HibernateUtil.getSessionFactory().openSession();
    final Query query = session.createQuery("from GroupModel where name = :name").setParameter("name", name);
    return (GroupModel) query.uniqueResult();
  }

  public List<Vehicle> findVehicles() {
    final Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("from Vehicle v");
    return query.getResultList();
  }
}