package frames;

import javax.swing.*;

public class GetDelivery {
    public JPanel panel1;
    private JButton manualImprtButton;
    private JButton interCarsImprtButton;
    private static GetDelivery instance = null ;

    private GetDelivery(){
        manualImprtButton.setEnabled(false);
        interCarsImprtButton.addActionListener(e -> {
            new InterCarsImport();
        });
    };

    public static GetDelivery getInstance(){
        if(instance == null){
            instance = new GetDelivery();
        }
        return instance;
    }


}
