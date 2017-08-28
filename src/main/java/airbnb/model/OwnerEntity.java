package airbnb.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Arianna on 23/8/2017.
 */
@Entity
@Table(name = "owner", schema = "mydb")
public class OwnerEntity {

    private int approval;

    @Id
    @Column(name = "users_username")
    private String usersUsername;

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

    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
    @JoinColumn(name="owner")
    private Set<ApartmentEntity> apartments=new HashSet<>(0);


    public Set<ApartmentEntity> getApartments() {
        return apartments;
    }

    public void setApartments(Set<ApartmentEntity> apartments) {
        this.apartments = apartments;
    }

    @OneToOne
    @JoinColumn(name = "users_username", referencedColumnName = "username")
    private UsersEntity usersByUsersUsername;

    public UsersEntity getUsersByUsersUsername() {
        return usersByUsersUsername;
    }

    public void setUsersByUsersUsername(UsersEntity usersByUsersUsername) {
        this.usersByUsersUsername = usersByUsersUsername;
    }

}
