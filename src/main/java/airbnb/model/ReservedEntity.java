package airbnb.model;

import javax.persistence.*;

/**
 * Created by Arianna on 22/9/2017.
 */
@Entity
@Table(name = "reserved", schema = "mydb")
public class ReservedEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    public int getId() {
        return id;
    }

    public void setReservation_id(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "startdate")
    private String date;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @ManyToOne
    @JoinColumn(name = "apartment_id")
    private ApartmentEntity apartment;

    public ApartmentEntity getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentEntity apartment) {
        this.apartment = apartment;
    }


    @Override
    public int hashCode() {
        int result=0;
//        result = renterUsersUsername != null ? renterUsersUsername.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) id;

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservedEntity that = (ReservedEntity) o;

        if (id != that.id) return false;
        if (apartment != null ? !apartment.equals(that.apartment) : that.apartment != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }
}
