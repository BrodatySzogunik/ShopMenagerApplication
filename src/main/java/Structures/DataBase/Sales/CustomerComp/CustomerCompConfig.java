//package Structures.DataBase.Sales.CustomerComp;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//
//@Configuration
//public class CustomerCompConfig {
//    @Bean
//    CommandLineRunner commandLineRunnerPriv(CustomerCompRepository repository){
//        return args ->{
//            CustomerComp mariam = new CustomerComp(
//            "2","2");
//
//            CustomerComp alex = new CustomerComp(
//            "3","3");
//
//            repository.saveAll(
//                    List.of(mariam, alex)
//            );
//        } ;
//    }
//}
