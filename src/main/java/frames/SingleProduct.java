package frames;

import javax.swing.*;

public class SingleProduct {
    public JPanel panel1;
    private static SingleProduct instance = null ;

    private SingleProduct(){};

    public static SingleProduct getInstance(){
        if(instance == null){
            instance = new SingleProduct();
        }
        return instance;
    }}
