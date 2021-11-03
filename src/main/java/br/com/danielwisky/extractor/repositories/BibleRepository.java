package br.com.danielwisky.extractor.repositories;

import br.com.danielwisky.extractor.domains.Bible;
import br.com.danielwisky.extractor.utils.HibernateUtil;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

@AllArgsConstructor
public class BibleRepository {

  public void save(final Bible bible) {
    Transaction transaction = null;
    try {
      final Session session = HibernateUtil.getSessionFactory().openSession();
      transaction = session.beginTransaction();
      session.save(bible);
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
    } finally {
      HibernateUtil.shutdown();
    }
  }
}
