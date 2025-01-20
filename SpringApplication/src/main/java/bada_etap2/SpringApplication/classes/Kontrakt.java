package bada_etap2.SpringApplication.classes;


import java.util.Date;

public class Kontrakt {
    private int idKontraktu;
    private String tytulKontraktu;
    private Date dataZawarcia;
    private String armatorNazwa;
    private String statkiNazwa;

    // Getters and Setters
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

    public Date getDataZawarcia() {
        return dataZawarcia;
    }

    public void setDataZawarcia(Date dataZawarcia) {
        this.dataZawarcia = dataZawarcia;
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
}