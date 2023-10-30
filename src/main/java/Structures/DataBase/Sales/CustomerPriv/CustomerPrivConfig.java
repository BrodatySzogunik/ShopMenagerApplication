//package Structures.DataBase.Sales.CustomerPriv;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//
//@Configuration
//public class CustomerPrivConfig {
//    @Bean
//    CommandLineRunner commandLineRunnerComp(CustomerPrivRepository repository){
//        return args ->{
//            CustomerPriv mariam = new CustomerPriv(
//                    "2","2","3");
//
//            CustomerPriv alex = new CustomerPriv(
//                    "3","3","3");
//
//            repository.saveAll(
//                    List.of(mariam, alex)
//            );
//        } ;
//    }
//}
