package bada_etap2.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DatabaseTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/pracownicy")
    public String getPracownicy(Model model) {
        try {
            // Query to select all rows from PRACOWNICY table
            List<String> pracownicyList = jdbcTemplate.query("SELECT * FROM PRACOWNICY",
                    (rs, rowNum) -> "ID_PRACOWNIKA: " + rs.getInt("ID_PRACOWNIKA") +
                            ", IMIE: " + rs.getString("IMIE") +
                            ", NAZWISKO: " + rs.getString("NAZWISKO") +
                            ", PESEL: " + rs.getString("PESEL") +
                            ", DATA_URODZENIA: " + rs.getDate("DATA_URODZENIA") +
                            ", PLEC: " + rs.getString("PLEC") +
                            ", ID_ARMATORA: " + rs.getInt("ID_ARMATORA") +
                            ", ID_ADRESU: " + rs.getInt("ID_ADRESU") +
                            ", ID_STANOWISKA: " + rs.getInt("ID_STANOWISKA"));

            // Add the list to the model to be used in the Thymeleaf template
            model.addAttribute("pracownicy", pracownicyList);

        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return "pracownicy";  // Corresponds to pracownicy.html Thymeleaf template
    }

    @GetMapping("/kontrakty")
    public String getKontrakty(Model model) {
        try {
            // SQL query to select data from KONTRAKTY, ARMATORZY, and STATKI tables
            String sql = "SELECT k.ID_KONTRAKTU, k.TYTUL_KONTRAKTU, k.DATA_ZAWARCIA, " +
                    "a.NAZWA AS ARMATOR_NAZWA, s.NAZWA AS STATKI_NAZWA " +
                    "FROM KONTRAKTY k " +
                    "JOIN ARMATORZY a ON k.ID_ARMATORA = a.ID_ARMATORA " +
                    "JOIN STATKI s ON k.ID_STATKU = s.ID_STATKU";

            // Query the KONTRAKTY table with joins
            List<String> kontraktyList = jdbcTemplate.query(sql,
                    (rs, rowNum) -> "ID_KONTRAKTU: " + rs.getInt("ID_KONTRAKTU") +
                            ", TYTUL_KONTRAKTU: " + rs.getString("TYTUL_KONTRAKTU") +
                            ", DATA_ZAWARCIA: " + rs.getDate("DATA_ZAWARCIA") +
                            ", ARMATOR_NAZWA: " + rs.getString("ARMATOR_NAZWA") +
                            ", STATKI_NAZWA: " + rs.getString("STATKI_NAZWA"));

            // Add the list to the model to be used in the Thymeleaf template
            model.addAttribute("kontrakty", kontraktyList);

        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return "kontrakty";  // Corresponds to kontrakty.html Thymeleaf template
    }
}
