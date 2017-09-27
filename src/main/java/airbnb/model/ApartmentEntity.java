package airbnb.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Arianna on 23/8/2017.
*/

@Entity
@Table(name = "apartment", schema = "mydb")
public class ApartmentEntity implements Serializable{

    private String description;
    private String name;
    private String country;
    private String town;
    private String area;
    private double price;
    private String startdate;
    private String finaldate;
    private int floor;
    private int type;
    private int capacity;
    private String photo;
    private int parking;
    private int internet;
    private int ac;
    private int elevator;
    private int tv;
    private int kitchen;
    private int heating;
    private int spaceArea;
    private int minimumres;
    private int pets;
    private int smoking;
    private int baths;
    private int livingroom;
    private int events;

    private  String photo2;
    private  String photo3;
    private  String photo4;

    private Float rating;

    private int reviews;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="apartment_id")
    private Integer id;
    public Integer getId() {
        return id;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "rating")
    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "reviews")
    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    @Basic
    @Column(name = "beds")
    private int beds;

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    @Basic
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "town")
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Basic
    @Column(name = "area")
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "startdate")
    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    @Basic
    @Column(name = "finaldate")
    public String getFinaldate() {
        return finaldate;
    }

    public void setFinaldate(String finaldate) {
        this.finaldate = finaldate;
    }

    @Basic
    @Column(name = "floor")
    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Basic
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Basic
    @Column(name = "capacity")
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "photo")
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Basic
    @Column(name = "parking")
    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    @Basic
    @Column(name = "internet")
    public int getInternet() {
        return internet;
    }

    public void setInternet(int internet) {
        this.internet = internet;
    }

    @Basic
    @Column(name = "ac")
    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    @Basic
    @Column(name = "elevator")
    public int getElevator() {
        return elevator;
    }

    public void setElevator(int elevator) {
        this.elevator = elevator;
    }

    @Basic
    @Column(name = "tv")
    public int getTv() {
        return tv;
    }

    public void setTv(int tv) {
        this.tv = tv;
    }

    @Basic
    @Column(name = "kitchen")
    public int getKitchen() {
        return kitchen;
    }

    public void setKitchen(int kitchen) {
        this.kitchen = kitchen;
    }

    @Basic
    @Column(name = "heating")
    public int getHeating() {
        return heating;
    }

    public void setHeating(int heating) {
        this.heating = heating;
    }

    @Basic
    @Column(name = "space_area")
    public int getSpaceArea() {
        return spaceArea;
    }

    public void setSpaceArea(int spaceArea) {
        this.spaceArea = spaceArea;
    }


    @Basic
    @Column(name = "minimumres")
    public int getMinimumres() {
        return minimumres;
    }

    public void setMinimumres(int minimumres) {
        this.minimumres = minimumres;
    }

    @Basic
    @Column(name = "pets")
    public int getPets() {
        return pets;
    }

    public void setPets(int pets) {
        this.pets = pets;
    }

    @Basic
    @Column(name = "smoking")
    public int getSmoking() {
        return smoking;
    }

    public void setSmoking(int smoking) {
        this.smoking = smoking;
    }

    @Basic
    @Column(name = "baths")
    public int getBaths() {
        return baths;
    }

    public void setBaths(int baths) {
        this.baths = baths;
    }

    @Basic
    @Column(name = "livingroom")
    public int getLivingroom() {
        return livingroom;
    }

    public void setLivingroom(int livingroom) {
        this.livingroom = livingroom;
    }

    @Basic
    @Column(name = "events")
    public int getEvents() {
        return events;
    }

    public void setEvents(int events) {
        this.events = events;
    }


    @Basic
    @Column(name = "photo2")
    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photog) {
        this.photo2 = photog;
    }

    @Basic
    @Column(name = "photo3")
    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photog) {
        this.photo3 = photog;
    }

    @Basic
    @Column(name = "photo4")
    public String getPhoto4() {
        return photo4;
    }

    public void setPhoto4(String photo) {
        this.photo4 = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApartmentEntity that = (ApartmentEntity) o;

        if (id != that.id) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (floor != that.floor) return false;
        if (type != that.type) return false;
        if (capacity != that.capacity) return false;
        if (parking != that.parking) return false;
        if (internet != that.internet) return false;
        if (ac != that.ac) return false;
        if (elevator != that.elevator) return false;
        if (tv != that.tv) return false;
        if (kitchen != that.kitchen) return false;
        if (heating != that.heating) return false;
        if (spaceArea != that.spaceArea) return false;
        if (minimumres != that.minimumres) return false;
        if (pets != that.pets) return false;
        if (smoking != that.smoking) return false;
        if (baths != that.baths) return false;
        if (livingroom != that.livingroom) return false;
        if (events != that.events) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (town != null ? !town.equals(that.town) : that.town != null) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;
        if (startdate != null ? !startdate.equals(that.startdate) : that.startdate != null) return false;
        if (finaldate != null ? !finaldate.equals(that.finaldate) : that.finaldate != null) return false;
        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (town != null ? town.hashCode() : 0);
        result = 31 * result + (area != null ? area.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (startdate != null ? startdate.hashCode() : 0);
        result = 31 * result + (finaldate != null ? finaldate.hashCode() : 0);
        result = 31 * result + floor;
        result = 31 * result + type;
        result = 31 * result + capacity;
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (int) parking;
        result = 31 * result + (int) internet;
        result = 31 * result + (int) ac;
        result = 31 * result + (int) elevator;
        result = 31 * result + (int) tv;
        result = 31 * result + (int) kitchen;
        result = 31 * result + (int) heating;
        result = 31 * result + spaceArea;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + minimumres;
        result = 31 * result + (int) pets;
        result = 31 * result + (int) smoking;
        result = 31 * result + baths;
        result = 31 * result + (int) livingroom;
        result = 31 * result + (int) events;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "users_username")
    private OwnerEntity owner;

    public OwnerEntity getOwner() {
        return owner;
    }

    public void setOwnerByOwner(OwnerEntity owner) {
        this.owner = owner;
    }


    @OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
//    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
   @JoinColumn(name ="apartment_id")
    private Set<ReservedEntity> reservedEntities= new HashSet<>(0);

    public Set<ReservedEntity> getReservedEntities(){return reservedEntities;}

    public void setReservedEntities(Set<ReservedEntity> reservedEntities){this.reservedEntities=reservedEntities;}

    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
//    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name= "apartment_id")
    private Set<ReservationEntity> reservations= new HashSet<>(0);

    public Set<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(Set<ReservationEntity> reservations) {
        this.reservations = reservations;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
//    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name= "apartment_id")
    private Set<CommentsEntity> comments= new HashSet<>(0);

    public Set<CommentsEntity> getComments() {
        return comments;
    }

    public void setComments(Set<CommentsEntity> comments) {
        this.comments = comments;
    }

}

