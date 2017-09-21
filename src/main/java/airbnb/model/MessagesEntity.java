package airbnb.model;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;


/**
 * Created by Σταυρίνα on 21/9/2017.
 */
@Entity
@Table(name = "messages", schema = "mydb")
public class MessagesEntity implements Serializable{
    private String question;
    private String response;
    private int apart_id;
    private String apart_name;

    @Id
    @Column(name = "messages_id", nullable = false)
    private Integer messages_id;
    public Integer getMessages_id() {
        return messages_id;
    }

    public void setMessages_id(Integer msg_id) {
        this.messages_id = msg_id;
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
    @JoinColumn(name = "owner_users_username", referencedColumnName = "users_username")
    private OwnerEntity owner;

    public OwnerEntity getOwner() {
        return owner;
    }

    public void setOwnerUsersUsername(OwnerEntity owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "question")
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Basic
    @Column(name = "response")
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Basic
    @Column(name = "apart_id")
    public Integer getApart_id() {
        return apart_id;
    }

    public void setApart_id(Integer apart_id) {
        this.apart_id = apart_id;
    }

    @Basic
    @Column(name = "apart_name")
    public String getApart_name() {
        return apart_name;
    }

    public void setApart_name(String apart_name) {
        this.apart_name= apart_name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessagesEntity that = (MessagesEntity) o;

        if (apart_id != that.apart_id) return false;
//        if (renterUsersUsername != null ? !renterUsersUsername.equals(that.renterUsersUsername) : that.renterUsersUsername != null)
//            return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null)
            return false;
        if (question != null ? !question.equals(that.question) : that.question != null) return false;
        if (response != null ? !response.equals(that.response) : that.response != null) return false;
        if (apart_name != null ? !apart_name.equals(that.apart_name) : that.apart_name != null) return false;
        return true;
    }


    @Override
    public int hashCode() {
        int result=0;
//        result = renterUsersUsername != null ? renterUsersUsername.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (question != null ? question.hashCode() : 0);
        result = 31 * result + (response != null ? response.hashCode() : 0);
        result = 31 * result + (apart_name != null ? apart_name.hashCode() : 0);
        result = 31 * result + (int) apart_id;
        return result;
    }
}
