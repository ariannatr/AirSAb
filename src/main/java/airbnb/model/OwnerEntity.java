package airbnb.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Arianna on 23/8/2017.
 */
@Entity
@Table(name = "owner", schema = "mydb")
public class OwnerEntity {
    private String usersUsername;
    private int approval;
  //  private Collection<ApartmentEntity> apartmentsByUsersUsername;
    private UsersEntity usersByUsersUsername;

    @Id
    @Column(name = "users_username")
    public String getUsersUsername() {
        return usersUsername;
    }

    public void setUsersUsername(String usersUsername) {
        this.usersUsername = usersUsername;
    }

    @Basic
    @Column(name = "approval")
    public int getApproval() {
        return approval;
    }

    public void setApproval(int approval) {
        this.approval = approval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OwnerEntity that = (OwnerEntity) o;

        if (approval != that.approval) return false;
        if (usersUsername != null ? !usersUsername.equals(that.usersUsername) : that.usersUsername != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usersUsername != null ? usersUsername.hashCode() : 0;
        result = 31 * result + (int) approval;
        return result;
    }

  /*  @OneToMany(mappedBy = "ownerByOwner")
    public Collection<ApartmentEntity> getApartmentsByUsersUsername() {
        return apartmentsByUsersUsername;
    }

    public void setApartmentsByUsersUsername(Collection<ApartmentEntity> apartmentsByUsersUsername) {
        this.apartmentsByUsersUsername = apartmentsByUsersUsername;
    }*/

    @OneToOne
    @JoinColumn(name = "users_username", referencedColumnName = "username")
    public UsersEntity getUsersByUsersUsername() {
        return usersByUsersUsername;
    }

    public void setUsersByUsersUsername(UsersEntity usersByUsersUsername) {
        this.usersByUsersUsername = usersByUsersUsername;
    }
}
