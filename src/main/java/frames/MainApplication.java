package frames;

import services.ConfigService;

import javax.swing.*;
import java.awt.*;

public class MainApplication extends JFrame{

    private final NavigationPanel navigationpanelClass;
    private final JPanel navigationPanel;
    private final JPanel contentPanel;

    private ProductList productList;
    private GetDelivery getDelivery;
    private GenerateBill generateBill;

    private ConfigService configService;
    private Cart cart;


    public MainApplication(){

        this.productList = ProductList.getInstance();
        this.getDelivery = GetDelivery.getInstance();
        this.generateBill = GenerateBill.getInstance();
        this.navigationpanelClass = NavigationPanel.getInstance();
        this.configService = ConfigService.getInstance();
        this.cart = Cart.getInstance();

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                configService.saveConfig();
            }
        });

        //card layout
        CardLayout cardLayout = new CardLayout();

        //content panel
        this.contentPanel = new JPanel();
        contentPanel.setLayout(cardLayout);
        contentPanel.add(productList.panel1, "productSearch");
        contentPanel.add(getDelivery.panel1, "getDelivery");
        contentPanel.add(generateBill.panel1, "generateBill");
        contentPanel.add(cart.panel1, "cart");
        getContentPane().add(this.contentPanel, BorderLayout.CENTER);

        //panel steering
        this.navigationpanelClass.productSearch.addActionListener(e -> {
            System.out.println("show product search");
            cardLayout.show(contentPanel,"productSearch");
        });

        this.navigationpanelClass.getDelivery.addActionListener(e->{
            System.out.println("show single product");
            cardLayout.show(contentPanel,"getDelivery");
        });

        this.cart.generateBillButton.addActionListener(e->{
            System.out.println("showBillGeneration");
            cardLayout.show(contentPanel,"generateBill");
        });

        this.navigationpanelClass.cartButton.addActionListener(e->{
            System.out.println("show cart");
            cart.updateCartTable();
            cardLayout.show(contentPanel,"cart");
        });

        //navigation panel
        this.navigationPanel = this.navigationpanelClass.panel1;
        getContentPane().add(this.navigationPanel, BorderLayout.NORTH);


        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}
