package bada_etap2.SpringApplication.classes;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Kontrakt {
    private int idKontraktu;
    private String tytulKontraktu;
    private String armatorNazwa;
    private String statkiNazwa;
    private int idArmatora;
    private int idStatku;

    // Getters and setters
    public int getIdKontraktu() {
        return idKontraktu;
    }

    public void setIdKontraktu(int idKontraktu) {
        this.idKontraktu = idKontraktu;
    }

    public String getTytulKontraktu() {
        return tytulKontraktu;
    }

    public void setTytulKontraktu(String tytulKontraktu) {
        this.tytulKontraktu = tytulKontraktu;
    }

    public String getArmatorNazwa() {
        return armatorNazwa;
    }

    public void setArmatorNazwa(String armatorNazwa) {
        this.armatorNazwa = armatorNazwa;
    }

    public String getStatkiNazwa() {
        return statkiNazwa;
    }

    public void setStatkiNazwa(String statkiNazwa) {
        this.statkiNazwa = statkiNazwa;
    }

    public int getIdArmatora() {
        return idArmatora;
    }

    public void setIdArmatora(int idArmatora) {
        this.idArmatora = idArmatora;
    }

    public int getIdStatku() {
        return idStatku;
    }

    public void setIdStatku(int idStatku) {
        this.idStatku = idStatku;
    }
}