/*package airbnb.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Created by Arianna on 23/8/2017.

@Entity
@Table(name = "apartment", schema = "mydb")
@IdClass(ApartmentEntityPK.class)
public class ApartmentEntity {
    private int id;
    private String description;
    private String name;
    private String country;
    private String town;
    private String area;
    private double price;
    private Date startdate;
    private Date finaldate;
    private int floor;
    private int type;
    private int capacity;
    private String photo;
    private byte parking;
    private byte internet;
    private byte ac;
    private byte elevator;
    private byte tv;
    private byte kitchen;
    private byte heating;
    private int spaceArea;
    private String owner;
    private int minimumres;
    private byte pets;
    private byte smoking;
    private int baths;
    private byte livingroom;
    private String photos;
    private byte events;
    private OwnerEntity ownerByOwner;
    private Collection<ReservationEntity> reservations;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public byte getParking() {
        return parking;
    }

    public void setParking(byte parking) {
        this.parking = parking;
    }

    @Basic
    @Column(name = "internet")
    public byte getInternet() {
        return internet;
    }

    public void setInternet(byte internet) {
        this.internet = internet;
    }

    @Basic
    @Column(name = "ac")
    public byte getAc() {
        return ac;
    }

    public void setAc(byte ac) {
        this.ac = ac;
    }

    @Basic
    @Column(name = "elevator")
    public byte getElevator() {
        return elevator;
    }

    public void setElevator(byte elevator) {
        this.elevator = elevator;
    }

    @Basic
    @Column(name = "tv")
    public byte getTv() {
        return tv;
    }

    public void setTv(byte tv) {
        this.tv = tv;
    }

    @Basic
    @Column(name = "kitchen")
    public byte getKitchen() {
        return kitchen;
    }

    public void setKitchen(byte kitchen) {
        this.kitchen = kitchen;
    }

    @Basic
    @Column(name = "heating")
    public byte getHeating() {
        return heating;
    }

    public void setHeating(byte heating) {
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

    @Id
    @Column(name = "owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
    public byte getPets() {
        return pets;
    }

    public void setPets(byte pets) {
        this.pets = pets;
    }

    @Basic
    @Column(name = "smoking")
    public byte getSmoking() {
        return smoking;
    }

    public void setSmoking(byte smoking) {
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
    public byte getLivingroom() {
        return livingroom;
    }

    public void setLivingroom(byte livingroom) {
        this.livingroom = livingroom;
    }

    @Basic
    @Column(name = "photos")
    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    @Basic
    @Column(name = "events")
    public byte getEvents() {
        return events;
    }

    public void setEvents(byte events) {
        this.events = events;
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
        if (photos != null ? !photos.equals(that.photos) : that.photos != null) return false;

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
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        result = 31 * result + (int) events;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "users_username")
    public OwnerEntity getOwnerByOwner() {
        return ownerByOwner;
    }

    public void setOwnerByOwner(OwnerEntity ownerByOwner) {
        this.ownerByOwner = ownerByOwner;
    }

    @OneToMany(mappedBy = "apartment")
    public Collection<ReservationEntity> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<ReservationEntity> reservations) {
        this.reservations = reservations;
    }
}
*/