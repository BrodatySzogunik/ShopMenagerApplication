package frames;

import javax.swing.*;
import java.awt.*;

public class NavigationPanel extends JFrame {
    public JPanel panel1;
    private JLabel companyName;
    private JLabel owner;
    private JLabel address;
    public JButton productSearch;
    public JButton getDelivery;
    public JButton searchCompany;

    public static NavigationPanel instance;
    private NavigationPanel(){
        panel1.setBackground(Color.gray);
        panel1.setPreferredSize(new Dimension(0,150));
    }

    public static NavigationPanel getInstance() {
        if(instance == null){
            instance = new NavigationPanel();
        }
        return instance;
    }
}
