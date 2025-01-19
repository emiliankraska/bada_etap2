package bada_etap2.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Controller
public class ArmatorController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/armatorzy")
    public String getArmatorzy(Model model) {
        // Query to select only ID_ARMATORA and NAZWA columns from ARMATORZY table
        List<Armator> armatorList = jdbcTemplate.query("SELECT ID_ARMATORA, NAZWA FROM ARMATORZY", new RowMapper<Armator>() {
            @Override
            public Armator mapRow(ResultSet rs, int rowNum) throws SQLException {
                Armator armator = new Armator();
                armator.setIdArmatora(rs.getInt("ID_ARMATORA"));
                armator.setNazwa(rs.getString("NAZWA"));
                return armator; // Only setting ID_ARMATORA and NAZWA
            }
        });

        // Add the list to the model to be used in the Thymeleaf template
        System.out.println("Fetched armatorList size: " + armatorList.size());
        for (Armator armator : armatorList) {
            System.out.println("Armator: " + armator.getIdArmatora() + ", " + armator.getNazwa());
        }
        model.addAttribute("armatorzy", armatorList);

        return "armatorzy";  // This will correspond to armatorzy.html Thymeleaf template
    }

    // Define the Armator class with properties for the ARMATORZY table
    public static class Armator {
        private int idArmatora;
        private String nazwa;

        // Getters and setters
        public int getIdArmatora() {
            return idArmatora;
        }

        public void setIdArmatora(int idArmatora) {
            this.idArmatora = idArmatora;
        }

        public String getNazwa() {
            return nazwa;
        }

        public void setNazwa(String nazwa) {
            this.nazwa = nazwa;
        }

        @Override
        public String toString() {
            return "Armator{" +
                    "idArmatora=" + idArmatora +
                    ", nazwa='" + nazwa + '\'' +
                    '}';
        }
    }
}
