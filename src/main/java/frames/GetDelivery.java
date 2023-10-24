package frames;

import javax.swing.*;

public class GetDelivery {
    public JPanel panel1;
    private static GetDelivery instance = null ;

    private GetDelivery(){};

    public static GetDelivery getInstance(){
        if(instance == null){
            instance = new GetDelivery();
        }
        return instance;
    }


}
