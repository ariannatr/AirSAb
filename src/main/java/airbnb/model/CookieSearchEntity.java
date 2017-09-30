package airbnb.model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

/**
 * Created by Σταυρίνα on 30/9/2017.
 */
@Entity
@Table(name = "cookiesearch", schema = "mydb")
public class CookieSearchEntity {
    private String location;
    private  Integer num;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "num")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num1) {
        this.num = num1;
    }

    @Basic
    @Column(name="location")
    public String getLocation(){return  location;}

    public void setLocation(String where1){ this.location=where1;}

    @ManyToOne
    @JoinColumn(name = "renter",referencedColumnName = "users_username")
    private RenterEntity renter;

    public RenterEntity getRenter() {
        return renter;
    }

    public void setRenter(RenterEntity renter) {
        this.renter = renter;
    }
}
