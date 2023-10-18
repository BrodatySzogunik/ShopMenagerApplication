package frames;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    private JPanel panel1;
    public Main(){
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setContentPane(panel1);
        this.setLayout(new BorderLayout());
        panel1.setBackground(Color.blue);
        panel1.setPreferredSize(new Dimension(0,200));

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.red);




        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.CENTER);

    }
}
