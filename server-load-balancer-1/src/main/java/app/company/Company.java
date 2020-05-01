package app.company;

public class Company {

    private int idCompany;
    private String corporateName;
    private int siren;
    private String address;
    private String email;
    private String password;

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idEntreprise) {
        this.idCompany = idEntreprise;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String raisonSociale) {
        this.corporateName = raisonSociale;
    }

    public int getSiren() {
        return siren;
    }

    public void setSiren(int siren) {
        this.siren = siren;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adresse) {
        this.address = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
