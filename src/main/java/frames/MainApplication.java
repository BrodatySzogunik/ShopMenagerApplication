package frames;

import services.ConfigService;

import javax.swing.*;
import java.awt.*;

public class MainApplication extends JFrame{

    private final NavigationPanel navigationpanelClass;
    private final JPanel navigationPanel;
    private final JPanel contentPanel;

    private ProductListPanel productList;
    private GetDeliveryPanel getDelivery;
    private GenerateBillPanel generateBill;

    private ConfigService configService;
    private CartPanel cart;


    public MainApplication(){

        this.productList = ProductListPanel.getInstance();
        this.getDelivery = GetDeliveryPanel.getInstance();
        this.generateBill = GenerateBillPanel.getInstance();
        this.navigationpanelClass = NavigationPanel.getInstance();
        this.configService = ConfigService.getInstance();
        this.cart = CartPanel.getInstance();

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
            productList.updateTable();
            cardLayout.show(contentPanel,"productSearch");
        });

        this.navigationpanelClass.getDelivery.addActionListener(e->{
            System.out.println("getDeliveryPanel");
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

        this.navigationpanelClass.salesReport.addActionListener(e->{
            System.out.println("generate sales report");
            new generateSaleRaportPanel();
        });

        this.navigationpanelClass.settingsButton.addActionListener(e -> {
            new ConfigPanel();
        });

        //navigation panel
        this.navigationPanel = this.navigationpanelClass.panel1;
        getContentPane().add(this.navigationPanel, BorderLayout.NORTH);


        this.setSize(1000,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}
