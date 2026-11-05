package hr.algebra.hibernate3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;


public class Main {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {

        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        addProduct(new Product("Majica", new BigDecimal("15.15")));
        updateProduct("Košulja", new BigDecimal("20.99"), 1);
        deleteProduct(1);
    }

    public static void addProduct(Product product) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
            System.out.println("Product added successfully!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void updateProduct(String newName, BigDecimal newPrice, Object id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);

            if(product != null) {
                product.setName(newName);
                product.setPrice(newPrice);
            }

            transaction.commit();
            System.out.println("Product updated successfully!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void deleteProduct(Object id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Product product = session.get(Product.class, id);

            if(product != null) {
                session.remove(product);
            }
            transaction.commit();
            System.out.println("Product deleted successfully!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

}
