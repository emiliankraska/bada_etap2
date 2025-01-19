package bada_etap2.SpringApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;



import java.util.List;

@Component
public class DatabaseTest implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Query the newly created TEST_TABLE
            List<String> testResults = jdbcTemplate.query("SELECT ID_PRACOWNIKA, IMIE FROM PRACOWNICY",
                    (rs, rowNum) -> "ID_PRACOWNIKA: " + rs.getInt("ID_PRACOWNIKA") + ", IMIE: " + rs.getString("IMIE"));

            // Print the results to the console
            if (testResults.isEmpty()) {
                System.out.println("No data found in TEST_TABLE.");
            } else {
                testResults.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }
}
