package frames;

import javax.swing.*;

public class GetDeliveryPanel {
    public JPanel panel1;
    private JButton manualImprtButton;
    private JButton interCarsImprtButton;
    private static GetDeliveryPanel instance = null ;

    private GetDeliveryPanel(){
        manualImprtButton.setEnabled(false);
        interCarsImprtButton.addActionListener(e -> {
            new InterCarsImportPanel();
        });
    };

    public static GetDeliveryPanel getInstance(){
        if(instance == null){
            instance = new GetDeliveryPanel();
        }
        return instance;
    }


}
