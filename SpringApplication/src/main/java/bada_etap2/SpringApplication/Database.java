package bada_etap2.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import bada_etap2.SpringApplication.classes.Pracownik;

import java.text.SimpleDateFormat;
import java.util.List;


@Controller
public class Database {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/admin/pracownicy")
    public String getPracownicy(Model model) {
        try {
            // Query excluding DATA_URODZENIA
            String sql = "SELECT ID_PRACOWNIKA, IMIE, NAZWISKO, PESEL, " +
                    "PLEC, ID_ARMATORA, ID_ADRESU, ID_STANOWISKA FROM PRACOWNICY";

            // Query the database and map the results to Pracownik objects
            List<Pracownik> pracownicyList = jdbcTemplate.query(sql,
                    (rs, rowNum) -> {
                        Pracownik pracownik = new Pracownik();
                        pracownik.setIdPracownika(rs.getInt("ID_PRACOWNIKA"));
                        pracownik.setImie(rs.getString("IMIE"));
                        pracownik.setNazwisko(rs.getString("NAZWISKO"));
                        pracownik.setPesel(rs.getString("PESEL"));
                        pracownik.setPlec(rs.getString("PLEC"));
                        pracownik.setIdArmatora(rs.getInt("ID_ARMATORA"));
                        pracownik.setIdAdresu(rs.getInt("ID_ADRESU"));
                        pracownik.setIdStanowiska(rs.getInt("ID_STANOWISKA"));
                        return pracownik;
                    });

            // Add the list to the model
            model.addAttribute("pracownicy", pracownicyList);

        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return "pracownicy";  // Thymeleaf template
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
    @PostMapping("/admin/pracownicy/{id}")
    public String deletePracownik(@PathVariable("id") int id) {
        try {
            // SQL query to delete employee by ID
            String sql = "DELETE FROM PRACOWNICY WHERE ID_PRACOWNIKA = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return "redirect:/admin/pracownicy";  // Redirect back to the list of employees
    }


}
