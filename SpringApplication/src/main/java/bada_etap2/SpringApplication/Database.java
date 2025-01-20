package bada_etap2.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class Database {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/admin/pracownicy")
    public String getPracownicy(Model model) {
        try {
            // Query to select all rows from PRACOWNICY table
            List<String> pracownicyList = jdbcTemplate.query("SELECT * FROM PRACOWNICY",
                    (rs, rowNum) -> rs.getInt("ID_PRACOWNIKA") +
                            "," + rs.getString("IMIE") +
                            "," + rs.getString("NAZWISKO") +
                            "," + rs.getString("PESEL") +
                            "," + rs.getDate("DATA_URODZENIA") +
                            "," + rs.getString("PLEC") +
                            "," + rs.getInt("ID_ARMATORA") +
                            "," + rs.getInt("ID_ADRESU") +
                            "," + rs.getInt("ID_STANOWISKA"));

            // Add the list to the model to be used in the Thymeleaf template
            model.addAttribute("pracownicy", pracownicyList);

        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return "pracownicy";  // Corresponds to pracownicy.html Thymeleaf template
    }

    @GetMapping("/admin/kontrakty")
    public String getKontraktyForAdmin(Model model) {
        try {
            // SQL query to fetch all contract details for admin, ordered by ID_KONTRAKTU
            String sql = "SELECT k.ID_KONTRAKTU, k.TYTUL_KONTRAKTU, k.DATA_ZAWARCIA, " +
                    "a.NAZWA AS ARMATOR_NAZWA, s.NAZWA AS STATKI_NAZWA " +
                    "FROM KONTRAKTY k " +
                    "JOIN ARMATORZY a ON k.ID_ARMATORA = a.ID_ARMATORA " +
                    "JOIN STATKI s ON k.ID_STATKU = s.ID_STATKU " +
                    "ORDER BY k.ID_KONTRAKTU"; // Ensure results are sorted by ID_KONTRAKTU

            // Query the database and map the results
            List<String> kontraktyList = jdbcTemplate.query(sql,
                    (rs, rowNum) -> "ID_KONTRAKTU: " + rs.getInt("ID_KONTRAKTU") +
                            ", TYTUL_KONTRAKTU: " + rs.getString("TYTUL_KONTRAKTU") +
                            ", DATA_ZAWARCIA: " + rs.getDate("DATA_ZAWARCIA") +
                            ", ARMATOR_NAZWA: " + rs.getString("ARMATOR_NAZWA") +
                            ", STATKI_NAZWA: " + rs.getString("STATKI_NAZWA"));

            model.addAttribute("kontrakty", kontraktyList);
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return "kontrakty_admin"; // Admin-specific Thymeleaf template
    }

    @GetMapping("/user/kontrakty")
    public String getKontraktyForUser(Model model) {
        try {
            // SQL query for Oracle to fetch the first 5 contracts
            String sql = "SELECT k.ID_KONTRAKTU, k.TYTUL_KONTRAKTU, k.DATA_ZAWARCIA, " +
                    "a.NAZWA AS ARMATOR_NAZWA " +
                    "FROM KONTRAKTY k " +
                    "JOIN ARMATORZY a ON k.ID_ARMATORA = a.ID_ARMATORA " +
                    "ORDER BY k.ID_KONTRAKTU " +
                    "FETCH FIRST 5 ROWS ONLY"; // Fetch the first 5 rows

            // Query the database and map the results
            List<String> kontraktyList = jdbcTemplate.query(sql,
                    (rs, rowNum) -> "ID_KONTRAKTU: " + rs.getInt("ID_KONTRAKTU") +
                            ", TYTUL_KONTRAKTU: " + rs.getString("TYTUL_KONTRAKTU") +
                            ", DATA_ZAWARCIA: " + rs.getDate("DATA_ZAWARCIA") +
                            ", ARMATOR_NAZWA: " + rs.getString("ARMATOR_NAZWA"));

            model.addAttribute("kontrakty", kontraktyList);
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return "kontrakty_user"; // User-specific Thymeleaf template
    }

}
