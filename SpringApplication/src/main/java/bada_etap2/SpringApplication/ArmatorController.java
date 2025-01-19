//package bada_etap2.SpringApplication;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import java.util.List;
//
//@Controller
//public class ArmatorController {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @GetMapping("/pracownicy")
//    public String getPracownicy(Model model) {
//        try {
//            // Query to select all rows from PRACOWNICY table
//            List<String> pracownicyList = jdbcTemplate.query("SELECT * FROM PRACOWNICY",
//                    (rs, rowNum) -> "ID_PRACOWNIKA: " + rs.getInt("ID_PRACOWNIKA") +
//                            ", IMIE: " + rs.getString("IMIE") +
//                            ", NAZWISKO: " + rs.getString("NAZWISKO") +
//                            ", PESEL: " + rs.getString("PESEL") +
//                            ", DATA_URODZENIA: " + rs.getDate("DATA_URODZENIA") +
//                            ", PLEC: " + rs.getString("PLEC") +
//                            ", ID_ARMATORA: " + rs.getInt("ID_ARMATORA") +
//                            ", ID_ADRESU: " + rs.getInt("ID_ADRESU") +
//                            ", ID_STANOWISKA: " + rs.getInt("ID_STANOWISKA"));
//
//            // Pass the list to the model to be used in the Thymeleaf template
//            model.addAttribute("pracownicy", pracownicyList);
//
//        } catch (Exception e) {
//            System.err.println("Database connection failed: " + e.getMessage());
//        }
//        return "pracownicy";  // Corresponds to pracownicy.html Thymeleaf template
//    }
//}
