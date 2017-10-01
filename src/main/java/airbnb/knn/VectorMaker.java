package airbnb.knn;

import airbnb.model.CommentsEntity;
import airbnb.model.CookieApEntity;
import airbnb.model.RenterEntity;
import airbnb.model.ReservationEntity;
import airbnb.sentimental.sentiment;
import org.springframework.security.access.method.P;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Σταυρίνα on 1/10/2017.
 */
public class VectorMaker {

    public ArrayList<Instance> buildVectors(Integer mode,ArrayList<RenterEntity> renterList,Integer apart_sum)
    {
        ArrayList<Instance> instances = new ArrayList<Instance>();
        for(RenterEntity renter:renterList)
        {
            Instance in_renter=new Instance();
            in_renter.setUuid(renter.getUsersUsername());
            if(mode==1)
                in_renter.setAttributes(buidFeaturesComments(renter.getComments(),apart_sum));
            else if(mode==2)
                in_renter.setAttributes(buidFeaturesCookies(renter,apart_sum));
            instances.add(in_renter);

        }

        return  instances;
    }


    private ArrayList<Feature> buidFeaturesComments(Set<CommentsEntity> comments,Integer apart_sum)
    {
        ArrayList<Feature> attributes =new ArrayList<Feature>();
        for(int x=0;x<apart_sum;x++)
        {
            attributes.add(new Feature(0));
        }
        System.out.println("size is "+attributes.size() +" , aparts are "+apart_sum);
        int index,sentnum;
        sentiment sentiment=new sentiment();
        for(CommentsEntity commentsEntity:comments)
        {
            index=commentsEntity.getApartmentEntity().getId() -1;
            if(attributes.get(index).getRate()>0)         //already posted comment
            {
                System.out.println("neo comment->ignore\n");
            }
            else            //first comment
            {
                Feature f=attributes.get(index);
                sentnum=sentiment.getsentiment(commentsEntity.getComment());
                if(commentsEntity.getRating()==0)
                    f.setRate(sentnum);
                else
                {
                    float rate= (sentnum+commentsEntity.getRating() )/2;
                    f.setRate(Math.round(rate));

                }

            }
        }


        return  attributes;
    }



    private ArrayList<Feature> buidFeaturesCookies(RenterEntity renterEntity, Integer apart_sum)
    {
        ArrayList<Feature> attributes =new ArrayList<Feature>();
        for(int x=0;x<apart_sum;x++)
        {
            attributes.add(new Feature(0));
        }
        System.out.println("size is "+attributes.size() +" , aparts are "+apart_sum);
        int index;
        if(renterEntity.getReservationsByUsersUsername().size()!=0)        //calc on reservations
        {
            Set<ReservationEntity> reservationEntitySet=renterEntity.getReservationsByUsersUsername();
            for(ReservationEntity res:reservationEntitySet)
            {
                index=res.getApartment().getId() -1;
                if(attributes.get(index).getRate()>0)
                    continue;
                else
                {
                    Feature f=attributes.get(index);
                    f.setRate(1);
                }
            }
        }
        else        //calc on cookies
        {
            Set<CookieApEntity> cookieApEntitySet=renterEntity.getCookieAp();
            for(CookieApEntity cookie:cookieApEntitySet)
            {
                index=cookie.getApartmentid()-1;
                Feature f=attributes.get(index);
                f.setRate(1);
            }
        }


        return  attributes;
    }
}
