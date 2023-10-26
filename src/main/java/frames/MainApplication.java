package frames;

import javax.swing.*;
import java.awt.*;

public class MainApplication extends JFrame{

    private final NavigationPanel navigationpanelClass;
    private final JPanel navigationPanel;
    private final JPanel contentPanel;

    private ProductList productList;
    private GetDelivery getDelivery;
    private SearchCompany searchCompany;
    public MainApplication(){

        this.productList = ProductList.getInstance();
        this.getDelivery = GetDelivery.getInstance();
        this.searchCompany = SearchCompany.getInstance();
        this.navigationpanelClass = NavigationPanel.getInstance();

        //card layout
        CardLayout cardLayout = new CardLayout();

        //content panel
        this.contentPanel = new JPanel();
        contentPanel.setLayout(cardLayout);
        contentPanel.add(productList.panel1, "productSearch");
        contentPanel.add(getDelivery.panel1, "getDelivery");
        contentPanel.add(searchCompany.panel1, "searchCompany");
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

        this.navigationpanelClass.searchCompany.addActionListener(e->{
            System.out.println("show single product");
            cardLayout.show(contentPanel,"searchCompany");
        });

        //navigation panel
        this.navigationPanel = this.navigationpanelClass.panel1;
        getContentPane().add(this.navigationPanel, BorderLayout.NORTH);










        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);



    }
}
