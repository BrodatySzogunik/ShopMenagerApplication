package services;

import Structures.DataBase.Products.Category.Category;
import Structures.DataBase.Products.OemToProduct.OemToProduct;
import Structures.DataBase.Products.Product.Product;
import Structures.DataBase.Sales.Bill.Bill;
import Structures.DataBase.Sales.BillToProductToPrice.BillToProductToPrice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;
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

    public Product getProductById(String  productId){

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);

        query.select(productRoot);

        if (productId != null) {
            query.where(cb.equal(cb.lower(productRoot.get("id")), productId.toLowerCase()));
        }

        List<Product> products = entityManager.createQuery(query).getResultList();
        if(products.size() == 1){
            return products.get(0);
        }else{
            return null;
        }
    }

    public List<Product> searchProduct(String  searchTerm, Long categoryId){

        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);

        query.select(productRoot);

        // Create a join with OemToProduct
        Join<Product, OemToProduct> oemJoin = productRoot.join("matchingOemCodes");

        Predicate searchPredicate = cb.or(
                cb.equal(cb.lower(productRoot.get("id")), searchTerm.toLowerCase()),
                cb.like(cb.lower(productRoot.get("name")), "%" + searchTerm.toLowerCase() + "%"),
                cb.like(cb.lower(oemJoin.get("oem")), "%" + searchTerm.toLowerCase() + "%")
        );

        if (categoryId != null) {
            Predicate categoryPredicate = cb.equal(productRoot.get("categoryId").get("id"), categoryId);
            query.where(cb.and(searchPredicate, categoryPredicate));
        } else {
            query.where(searchPredicate);
        }

        List<Product> products = entityManager.createQuery(query).getResultList();
        return products;
    }

    public void updateProductAmount(Product product, int newAmount){
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Product> criteriaUpdate =  cb.createCriteriaUpdate(Product.class);

        Root<Product> root = criteriaUpdate.from(Product.class);

        criteriaUpdate.set("quantity",newAmount);
        criteriaUpdate.where(cb.equal(root.get("id"), product.getId()));

        entityManager.createQuery(criteriaUpdate).executeUpdate();

        entityManager.close();
    }

    public List<Object[]> getSalesFromPeriod(Date fromDate, Date toDate){
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);

        Root<Bill> billRoot = query.from(Bill.class);
        Join<Bill, BillToProductToPrice> joinBillToProduct = billRoot.join("salePrices");
        Join<BillToProductToPrice, Product> joinProduct = joinBillToProduct.join("productId");

        // Subquery to calculate the sum of amounts


        query.multiselect(
                joinProduct.get("id"),
                joinProduct.get("name"),
                cb.sum(joinBillToProduct.get("amount")),
                joinBillToProduct.get("salePrice"),
                joinBillToProduct.get("buyPrice")
                )
                .groupBy(
                        joinProduct.get("id"),
                        joinProduct.get("name"),
                        joinBillToProduct.get("salePrice"),
                        joinBillToProduct.get("buyPrice")
                )
                .where(cb.between(billRoot.get("saleDate"),fromDate,toDate));

        return entityManager.createQuery(query).getResultList();

    }
}
