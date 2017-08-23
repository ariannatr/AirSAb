package airbnb.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Arianna on 23/8/2017.
 */
@Entity
@Table(name = "renter", schema = "mydb")
public class RenterEntity {
    private String usersUsername;
    private UsersEntity usersByUsersUsername;
   // private Collection<ReservationEntity> reservationsByUsersUsername;

    @Id
    @Column(name = "users_username")
    public String getUsersUsername() {
        return usersUsername;
    }

    public void setUsersUsername(String usersUsername) {
        this.usersUsername = usersUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RenterEntity that = (RenterEntity) o;

        if (usersUsername != null ? !usersUsername.equals(that.usersUsername) : that.usersUsername != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return usersUsername != null ? usersUsername.hashCode() : 0;
    }

    @OneToOne
    @JoinColumn(name = "users_username", referencedColumnName = "username", nullable = false)
    public UsersEntity getUsersByUsersUsername() {
        return usersByUsersUsername;
    }

    public void setUsersByUsersUsername(UsersEntity usersByUsersUsername) {
        this.usersByUsersUsername = usersByUsersUsername;
    }

    /*@OneToMany(mappedBy = "renterByRenterUsersUsername")
    public Collection<ReservationEntity> getReservationsByUsersUsername() {
        return reservationsByUsersUsername;
    }

    public void setReservationsByUsersUsername(Collection<ReservationEntity> reservationsByUsersUsername) {
        this.reservationsByUsersUsername = reservationsByUsersUsername;
    }*/
}
