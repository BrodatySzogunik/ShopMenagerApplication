
import frames.MainApplication;
import services.ConfigService;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
public class main {
    public static void main(String[] args){
        ConfigService.getInstance().readConfig();

        new MainApplication();
    }
}
