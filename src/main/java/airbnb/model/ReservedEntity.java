package airbnb.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Arianna on 22/9/2017.
 */
@Entity
@Table(name = "reserved", schema = "mydb")
public class ReservedEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    public Integer getId() {
        return id;
    }

    public ReservedEntity()
    {
        this.date="";
    }

    public ReservedEntity(String date,ApartmentEntity apart_id){
        this.date=date;
        this.apartment=apart_id;
        System.out.println("dimiourgis tin grammi kratisii "+date);
    }



    private String date;
    @Basic
    @Column(name = "startdate")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @ManyToOne//(fetch=FetchType.LAZY)
    @JoinColumn(name ="apartment_id")
    private ApartmentEntity apartment;
    public ApartmentEntity getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentEntity apartment) {
        this.apartment = apartment;
    }


//    //@ManyToOne
//    @Basic
//    @Column(name ="apartment_id")
//    private int apartment;
//    public int getApartment() {
//        return apartment;
//    }
//
//    public void setApartment(int apartment) {
//        this.apartment = apartment;
//    }



    @Override
    public int hashCode() {
        int result=0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
       // result = 31 * result +  id;
        //result = 31 * result + (apartment != null ? apartment.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservedEntity that = (ReservedEntity) o;

        if (id != that.id) return false;
        if (apartment != null ? !apartment.equals(that.apartment) : that.apartment != null)
            return false;
//         if (apartment!= that.apartment) return false;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return true;
    }
}
