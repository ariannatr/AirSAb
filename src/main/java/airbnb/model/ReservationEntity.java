package airbnb.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Arianna on 23/8/2017.
*/
@Entity
@Table(name = "reservation", schema = "mydb")
public class ReservationEntity implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int reservation_id;
    public int getReservation_id() {
        return reservation_id;
    }

    @ManyToOne
    @JoinColumn(name = "renter_users_username", referencedColumnName = "users_username")
    private RenterEntity renter;

    public RenterEntity getRenter() {
        return renter;
    }

    public void setRenterUsersUsername(RenterEntity renter) {
        this.renter = renter;
    }

    @ManyToOne
    @JoinColumn(name = "apartment_owner", referencedColumnName ="users_username")
    private OwnerEntity apartmentOwner;
    public OwnerEntity getApartmentOwner() {
        return apartmentOwner;
    }

    public void setApartmentOwner(OwnerEntity apartmentOwner) {
        this.apartmentOwner = apartmentOwner;
    }

    @Basic
    @Column(name = "startdate")
    private String startdate;
    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    @Basic
    @Column(name = "finaldate")
    private String finaldate;
    public String getFinaldate() {
        return finaldate;
    }

    public void setFinaldate(String finaldate) {
        this.finaldate = finaldate;
    }

    @Basic
    @Column(name = "total_cost")
    private double totalCost;
    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Basic
    @Column(name = "approval")
    private int approval;
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
