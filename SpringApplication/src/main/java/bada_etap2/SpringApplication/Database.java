package bada_etap2.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import bada_etap2.SpringApplication.classes.Pracownik;
import bada_etap2.SpringApplication.classes.Kontrakt;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
public class Database {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/admin/pracownicy")
    public String getPracownicy(Model model) {
        try {
            String sql = "SELECT ID_PRACOWNIKA, IMIE, NAZWISKO, PESEL, " +
                    "PLEC, ID_ARMATORA, ID_ADRESU, ID_STANOWISKA FROM PRACOWNICY";

            List<Pracownik> pracownicyList = jdbcTemplate.query(sql,
                    (rs, rowNum) -> {
                        Pracownik pracownik = new Pracownik();
                        pracownik.setIdPracownika(rs.getInt("ID_PRACOWNIKA"));
                        pracownik.setImie(rs.getString("IMIE"));
                        pracownik.setNazwisko(rs.getString("NAZWISKO"));
                        pracownik.setPesel(rs.getString("PESEL"));
                        pracownik.setPlec(rs.getString("PLEC"));
                        return pracownik;
                    });

            model.addAttribute("pracownicy", pracownicyList);

        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return "pracownicy";
    }

    @GetMapping("/admin/kontrakty")
    public String getKontraktyForAdmin(Model model) {
        try {
            String sql = "SELECT k.ID_KONTRAKTU, k.TYTUL_KONTRAKTU, " +
                    "a.NAZWA AS ARMATOR_NAZWA, s.NAZWA AS STATKI_NAZWA " +
                    "FROM KONTRAKTY k " +
                    "JOIN ARMATORZY a ON k.ID_ARMATORA = a.ID_ARMATORA " +
                    "JOIN STATKI s ON k.ID_STATKU = s.ID_STATKU " +
                    "ORDER BY k.ID_KONTRAKTU";

            List<Kontrakt> kontraktyList = jdbcTemplate.query(sql,
                    (rs, rowNum) -> {
                        Kontrakt kontrakt = new Kontrakt();
                        kontrakt.setIdKontraktu(rs.getInt("ID_KONTRAKTU"));
                        kontrakt.setTytulKontraktu(rs.getString("TYTUL_KONTRAKTU"));
                        kontrakt.setArmatorNazwa(rs.getString("ARMATOR_NAZWA"));
                        kontrakt.setStatkiNazwa(rs.getString("STATKI_NAZWA"));
                        return kontrakt;
                    });

            model.addAttribute("kontrakty", kontraktyList);
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return "kontrakty_admin";
    }
    @GetMapping("/user/kontrakty")
    public String getKontraktyForUser(Model model) {
        try {
            // Updated SQL query to include ship names
            String sql = "SELECT k.ID_KONTRAKTU, k.TYTUL_KONTRAKTU, " +
                    "a.NAZWA AS ARMATOR_NAZWA, s.NAZWA AS STATKI_NAZWA " +
                    "FROM KONTRAKTY k " +
                    "JOIN ARMATORZY a ON k.ID_ARMATORA = a.ID_ARMATORA " +
                    "JOIN STATKI s ON k.ID_STATKU = s.ID_STATKU " +
                    "WHERE a.ID_ARMATORA = 1 " +
                    "ORDER BY k.ID_KONTRAKTU";


            List<Kontrakt> kontraktyList = jdbcTemplate.query(sql,
                    (rs, rowNum) -> {
                        Kontrakt kontrakt = new Kontrakt();
                        kontrakt.setIdKontraktu(rs.getInt("ID_KONTRAKTU"));
                        kontrakt.setTytulKontraktu(rs.getString("TYTUL_KONTRAKTU"));
                        kontrakt.setArmatorNazwa(rs.getString("ARMATOR_NAZWA"));
                        kontrakt.setStatkiNazwa(rs.getString("STATKI_NAZWA"));
                        return kontrakt;
                    });


            model.addAttribute("kontrakty", kontraktyList);
        } catch (Exception e) {

            System.err.println("Database connection failed: " + e.getMessage());
        }
        return "kontrakty_user";
    }

    @PostMapping("/admin/pracownicy/{id}")
    public String deletePracownik(@PathVariable("id") int id) {
        try {
            String sql = "DELETE FROM PRACOWNICY WHERE ID_PRACOWNIKA = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
        return "redirect:/admin/pracownicy";
    }

    @GetMapping("/admin/pracownicy/add")
    public String showAddPracownikForm(Model model) {
        model.addAttribute("pracownik", new Pracownik());
        return "add_pracownik";
    }

    @PostMapping("/admin/pracownicy/add")
    public String addPracownik(Pracownik pracownik) {
        try {
            String sql = "INSERT INTO PRACOWNICY (IMIE, NAZWISKO, PESEL, PLEC) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, pracownik.getImie(), pracownik.getNazwisko(), pracownik.getPesel(), pracownik.getPlec());
        } catch (Exception e) {
            System.err.println("Failed to add employee: " + e.getMessage());
        }
        return "redirect:/admin/pracownicy";
    }
    @PostMapping("/admin/kontrakty/{id}")
    public String deleteKontraktAdmin(@PathVariable("id") int id) {
        try {
            String sql = "DELETE FROM KONTRAKTY WHERE ID_KONTRAKTU = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            System.err.println("Failed to delete contract: " + e.getMessage());
        }
        return "redirect:/admin/kontrakty";
    }
    @PostMapping("/user/kontrakty/{id}")
    public String deleteKontraktUser(@PathVariable("id") int id) {
        try {
            String sql = "DELETE FROM KONTRAKTY WHERE ID_KONTRAKTU = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            System.err.println("Failed to delete contract: " + e.getMessage());
        }
        return "redirect:/user/kontrakty";
    }
    @GetMapping("/admin/kontrakty/add")
    public String showAddKontraktForm(Model model) {
        try {

            model.addAttribute("kontrakt", new Kontrakt());
        } catch (Exception e) {
            System.err.println("Failed to load form data: " + e.getMessage());
        }
        return "add_kontrakt";
    }

    @PostMapping("/admin/kontrakty/add")
    public String addKontrakt(Kontrakt kontrakt) {
        try {
            String sql = "INSERT INTO KONTRAKTY (TYTUL_KONTRAKTU, ID_ARMATORA, ID_STATKU) " +
                    "VALUES (?, ?, ?)";

            jdbcTemplate.update(sql,
                    kontrakt.getTytulKontraktu(),
                    kontrakt.getIdArmatora(),
                    kontrakt.getIdStatku()
            );
        } catch (Exception e) {
            System.err.println("Failed to add contract: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/admin/kontrakty";
    }
}
