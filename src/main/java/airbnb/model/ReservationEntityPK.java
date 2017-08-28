package airbnb.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Arianna on 23/8/2017.

public class ReservationEntityPK implements Serializable {
    private String renterUsersUsername;
    private int apartmentId;
    private String apartmentOwner;

    @Column(name = "renter_users_username")
    @Id
    public String getRenterUsersUsername() {
        return renterUsersUsername;
    }

    public void setRenterUsersUsername(String renterUsersUsername) {
        this.renterUsersUsername = renterUsersUsername;
    }

    @Column(name = "apartment_id")
    @Id
    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Column(name = "apartment_owner")
    @Id
    public String getApartmentOwner() {
        return apartmentOwner;
    }

    public void setApartmentOwner(String apartmentOwner) {
        this.apartmentOwner = apartmentOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationEntityPK that = (ReservationEntityPK) o;

        if (apartmentId != that.apartmentId) return false;
        if (renterUsersUsername != null ? !renterUsersUsername.equals(that.renterUsersUsername) : that.renterUsersUsername != null)
            return false;
        if (apartmentOwner != null ? !apartmentOwner.equals(that.apartmentOwner) : that.apartmentOwner != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = renterUsersUsername != null ? renterUsersUsername.hashCode() : 0;
        result = 31 * result + apartmentId;
        result = 31 * result + (apartmentOwner != null ? apartmentOwner.hashCode() : 0);
        return result;
    }
}
*/