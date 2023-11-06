package services;

import Structures.DataBase.Products.Category.Category;
import Structures.DataBase.Products.Category.Category_;
import Structures.DataBase.Products.OemToProduct.OemToProduct;
import Structures.DataBase.Products.Product.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DBController {

    private final EntityManagerFactory entityManagerFactory;
    private static DBController instance;


    private DBController(){
        this.entityManagerFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public static DBController getInstance(){
        if(instance == null){
            instance = new DBController();
        }
        return  instance;
    }



    public <T> void insertIntoTable(T objectToInsert){
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {

            entityManager.persist(objectToInsert);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public <T> List<T> getEntities(Class<T> entityClass){

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            CriteriaQuery<T>  cirteriaQuerry = criteriaBuilder.createQuery(entityClass);
            Root<T> root = cirteriaQuerry.from(entityClass);

            cirteriaQuerry.select(root);

            return entityManager.createQuery(cirteriaQuerry).getResultList();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }

    public List<Product> searchProduct(String  searchTerm, Long categoryId){

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);

        query.select(productRoot);

        // Create a join with OemToProduct
        Join<Product, OemToProduct> oemJoin = productRoot.join("matchingOemCodes");
        System.out.println(categoryId);
        query.where(
                cb.or(
                        cb.equal(cb.lower(productRoot.get("id")), searchTerm.toLowerCase()),
                        cb.like(cb.lower(productRoot.get("name")), "%" + searchTerm.toLowerCase() + "%"),
                        cb.like(cb.lower(oemJoin.get("oem")), "%" + searchTerm.toLowerCase() + "%"),
                        cb.equal(productRoot.get("categoryId").get("id"), categoryId)
                )
        );

        List<Product> products = entityManager.createQuery(query).getResultList();
        return products;
    }


//    public static void main(String[] args) {
//        Category category = new Category();
//        category.setCategoryName("PŁYN HAMULCOWY");
//        DBController dbController = new DBController();
//
////        dbController.insertIntoTable(category);
//
//        Product product = new Product();
//
//        product.setName("chłodnica honda accord");
//        dbController.insertIntoTable(product);
//
//        System.out.println(dbController.getEntities(Product.class));
//
//        for (Product employee : dbController.getEntities(Product.class)) {
//            System.out.println("ID: " + employee.getId() + ", Name: " + employee.getName());
//        }
//
//
//    }
}
