package app.remark;

import javax.persistence.*;

@Entity
public class Remark {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int idRemark;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String remark;
    private int idAd;

    public int getIdRemark() {
        return idRemark;
    }

    public void setIdRemark(int idRemark) {
        this.idRemark = idRemark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIdAd() {
        return idAd;
    }

    public void setIdAd(int idAd) {
        this.idAd = idAd;
    }
}
