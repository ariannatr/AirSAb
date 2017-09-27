package airbnb.model;

/**
 * Created by Arianna on 24-Sept-17.
**/

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comments", schema = "mydb")
public class CommentsEntity implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer comment_id;
    @Column(name="comment")
    private String comment;
    @Column(name="rating")
    private float rating;


    @ManyToOne
    @JoinColumn(name="renter")
    private RenterEntity renter;

    public RenterEntity getRenter() {
        return renter;
    }

    public void setRenter(RenterEntity renter) {
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


    @ManyToOne
    @JoinColumn(name="apartment_id")
    private ApartmentEntity apartmentEntity;

    public ApartmentEntity getApartmentEntity() {
        return apartmentEntity;
    }

    public void setApartmentEntity(ApartmentEntity apartmentEntity) {
        this.apartmentEntity = apartmentEntity;
    }


 /*--------------Getters - Setters for table fields--------------*/

    public int getCommentId() {
        return comment_id;
    }

    public void setCommentId(int commentId) {
        this.comment_id = commentId;
    }

    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Column(name = "rating")
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentsEntity that = (CommentsEntity) o;

        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = comment_id;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

}
