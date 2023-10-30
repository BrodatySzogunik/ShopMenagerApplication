package services;

import Structures.DataBase.Products.Category.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DBController {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Category> employees = session.createQuery("FROM Employee", Category.class).getResultList();
        for (Category employee : employees) {
            System.out.println("ID: " + employee.getId() + ", Name: " + employee.getCategoryName());
        }
        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
