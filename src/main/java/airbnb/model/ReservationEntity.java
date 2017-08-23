/*package airbnb.model;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Arianna on 23/8/2017.

@Entity
@Table(name = "reservation", schema = "mydb")
@IdClass(ReservationEntityPK.class)
public class ReservationEntity {
    private String renterUsersUsername;
    private int apartmentId;
    private String apartmentOwner;
    private Date startdate;
    private Date finaldate;
    private double totalCost;
    private byte approval;
    private RenterEntity renterByRenterUsersUsername;
    private ApartmentEntity apartment;

    @Id
    @Column(name = "renter_users_username")
    public String getRenterUsersUsername() {
        return renterUsersUsername;
    }

    public void setRenterUsersUsername(String renterUsersUsername) {
        this.renterUsersUsername = renterUsersUsername;
    }

    @Id
    @Column(name = "apartment_id")
    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Id
    @Column(name = "apartment_owner")
    public String getApartmentOwner() {
        return apartmentOwner;
    }

    public void setApartmentOwner(String apartmentOwner) {
        this.apartmentOwner = apartmentOwner;
    }

    @Basic
    @Column(name = "startdate")
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @Basic
    @Column(name = "finaldate")
    public Date getFinaldate() {
        return finaldate;
    }

    public void setFinaldate(Date finaldate) {
        this.finaldate = finaldate;
    }

    @Basic
    @Column(name = "total_cost")
    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Basic
    @Column(name = "approval")
    public byte getApproval() {
        return approval;
    }

    public void setApproval(byte approval) {
        this.approval = approval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationEntity that = (ReservationEntity) o;

        if (apartmentId != that.apartmentId) return false;
        if (Double.compare(that.totalCost, totalCost) != 0) return false;
        if (approval != that.approval) return false;
        if (renterUsersUsername != null ? !renterUsersUsername.equals(that.renterUsersUsername) : that.renterUsersUsername != null)
            return false;
        if (apartmentOwner != null ? !apartmentOwner.equals(that.apartmentOwner) : that.apartmentOwner != null)
            return false;
        if (startdate != null ? !startdate.equals(that.startdate) : that.startdate != null) return false;
        if (finaldate != null ? !finaldate.equals(that.finaldate) : that.finaldate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = renterUsersUsername != null ? renterUsersUsername.hashCode() : 0;
        result = 31 * result + apartmentId;
        result = 31 * result + (apartmentOwner != null ? apartmentOwner.hashCode() : 0);
        result = 31 * result + (startdate != null ? startdate.hashCode() : 0);
        result = 31 * result + (finaldate != null ? finaldate.hashCode() : 0);
        temp = Double.doubleToLongBits(totalCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) approval;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "renter_users_username", referencedColumnName = "users_username")
    public RenterEntity getRenterByRenterUsersUsername() {
        return renterByRenterUsersUsername;
    }

    public void setRenterByRenterUsersUsername(RenterEntity renterByRenterUsersUsername) {
        this.renterByRenterUsersUsername = renterByRenterUsersUsername;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "apartment_id", referencedColumnName = "id"), @JoinColumn(name = "apartment_owner", referencedColumnName = "owner")})
    public ApartmentEntity getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentEntity apartment) {
        this.apartment = apartment;
    }
}
*/