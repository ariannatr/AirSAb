/*package airbnb.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Arianna on 23/8/2017.

@Entity
@Table(name = "reservation", schema = "mydb")
public class ReservationEntity implements Serializable{
   // private String renterUsersUsername;
    private Date startdate;
    private Date finaldate;
    private double totalCost;
    private int approval;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "reservation_id", nullable = false)
    private Integer reservation_id;
    public Integer getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Integer reservation_id) {
        this.reservation_id = reservation_id;
    }
   /* @ManyToOne
    @Column(name = "renter_users_username")
    public String getRenterUsersUsername() {
        return renterUsersUsername;
    }

    public void setRenterUsersUsername(String renterUsersUsername) {
        this.renterUsersUsername = renterUsersUsername;
    }



    @ManyToOne
    @JoinColumn(name = "apartment_owner", referencedColumnName = "owner")
    private OwnerEntity apartmentOwner;
    public OwnerEntity getApartmentOwner() {
        return apartmentOwner;
    }

    public void setApartmentOwner(OwnerEntity apartmentOwner) {
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
    public int getApproval() {
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

        if (Double.compare(that.totalCost, totalCost) != 0) return false;
        if (approval != that.approval) return false;
//        if (renterUsersUsername != null ? !renterUsersUsername.equals(that.renterUsersUsername) : that.renterUsersUsername != null)
//            return false;
        if (apartmentOwner != null ? !apartmentOwner.equals(that.apartmentOwner) : that.apartmentOwner != null)
            return false;
        if (startdate != null ? !startdate.equals(that.startdate) : that.startdate != null) return false;
        if (finaldate != null ? !finaldate.equals(that.finaldate) : that.finaldate != null) return false;

        return true;
    }



    @ManyToOne
    @JoinColumn(name = "renter_users_username", referencedColumnName = "users_username")
    private RenterEntity renterByRenterUsersUsername;

    public RenterEntity getRenterByRenterUsersUsername() {
        return renterByRenterUsersUsername;
    }

    public void setRenterByRenterUsersUsername(RenterEntity renterByRenterUsersUsername) {
        this.renterByRenterUsersUsername = renterByRenterUsersUsername;
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
        long temp;
//        result = renterUsersUsername != null ? renterUsersUsername.hashCode() : 0;
        result = 31 * result + (apartmentOwner != null ? apartmentOwner.hashCode() : 0);
        result = 31 * result + (startdate != null ? startdate.hashCode() : 0);
        result = 31 * result + (finaldate != null ? finaldate.hashCode() : 0);
        temp = Double.doubleToLongBits(totalCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) approval;
        return result;
    }
}
*/