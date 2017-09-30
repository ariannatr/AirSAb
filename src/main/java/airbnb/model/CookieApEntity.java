package airbnb.model;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

/**
 * Created by Σταυρίνα on 30/9/2017.
 */
@Entity
@Table(name = "cookieap", schema = "mydb")
public class CookieApEntity {
    private Integer apartmentid;
    private Integer times;

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
    @Column(name = "times")
    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times1) {
        this.times = times1;
    }

    @Basic
    @Column(name = "apartmentid")
    public Integer getApartmentid() {
        return apartmentid;
    }

    public void setApartmentid(Integer apartmentid1) {
        this.apartmentid = apartmentid1;
    }

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
