package app.ad;

import javax.persistence.*;

@Entity
public class Ad {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idAd;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private int remuneration;
    private int idCompany;

    public int getIdAd() {
        return idAd;
    }

    public void setIdAd(int id_annonce) {
        this.idAd = id_annonce;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titre) {
        this.title = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRemuneration() {
        return remuneration;
    }

    public void setRemuneration(int remuneration) {
        this.remuneration = remuneration;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idEntreprise) {
        this.idCompany = idEntreprise;
    }
}