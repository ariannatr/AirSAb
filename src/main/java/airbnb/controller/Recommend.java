package airbnb.controller;

import airbnb.model.*;
import airbnb.repository.RenterRepository;
import airbnb.repository.ReservationRepository;
import airbnb.sentimental.sentiment;
import airbnb.service.ReservationService;
import airbnb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.lang.Math.abs;

/**
 * Created by Σταυρίνα on 30/9/2017.
 */

public class Recommend {


    public LinkedHashSet<ApartmentEntity> getRecommendationsRes(RenterEntity renterEntity, ArrayList<RenterEntity> renterList) {
        LinkedHashSet<ApartmentEntity> rec = new LinkedHashSet<ApartmentEntity>();
        Set<ReservationEntity> res = renterEntity.getReservationsByUsersUsername();
        //  ArrayList<UsersEntity> renterList = userService.findAllRenters();

        HashMap<RenterEntity, Integer> to_Rec = new HashMap<RenterEntity, Integer>();
        for (RenterEntity u : renterList) {
            Integer amount = 0;
            Set<ReservationEntity> allu=u.getReservationsByUsersUsername();
            for (ReservationEntity ur : allu) {
                for (ReservationEntity myr : res) {
                    if (myr.getApartment().getId() == ur.getApartment().getId()) {
                        amount++;
                    }
                }
            }
            to_Rec.put(u, amount);
        }

        Integer i1 = 0;
        Integer i2 = 0;
        Integer i3 = 0;
        Integer i4 = 0;
        Integer i5 = 0;
        RenterEntity test_user = new RenterEntity();
        HashMap<RenterEntity, Integer> train_set = new HashMap<RenterEntity, Integer>();
        for (HashMap.Entry<RenterEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i1) {
                i1 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i1);
        to_Rec.remove(test_user, i1);
        for (HashMap.Entry<RenterEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i2) {
                i2 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i2);
        to_Rec.remove(test_user, i2);
        for (HashMap.Entry<RenterEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i3) {
                i3 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i3);
        to_Rec.remove(test_user, i3);
        for (HashMap.Entry<RenterEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i4) {
                i4 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i4);
        to_Rec.remove(test_user, i4);
        for (HashMap.Entry<RenterEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i5) {
                i5 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i5);
        to_Rec.remove(test_user, i5);
        int k = 0;

        sentiment sentiment=new sentiment();
        for (HashMap.Entry<RenterEntity, Integer> entry : train_set.entrySet()) {
            RenterEntity u = entry.getKey();
            Set<ReservationEntity> allu=u.getReservationsByUsersUsername();
            for (ReservationEntity ur : allu) {
                for (ReservationEntity bb : res) {
                    Set<CommentsEntity> comments = ur.getApartment().getComments();
                    for (CommentsEntity com : comments) {
                        Set<CommentsEntity> caat = bb.getApartment().getComments();
                        for (CommentsEntity ct : caat) {
                            if  ((sentiment.getsentiment(ct.getComment())==sentiment.getsentiment(com.getComment())) || (abs(ct.getRating()-com.getRating()) <=1) ) {

                                if (!(ur.getApartmentOwner().getUsersUsername()).equals(renterEntity.getUsersUsername())
                                        && ur.getApartment().getId() != bb.getApartment().getId()) {

                                    if (k < 6) {
                                        rec.add(ur.getApartment());
                                        k++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return rec;
    }




    public LinkedHashSet<ApartmentEntity> getRecommendationsCookie(RenterEntity renterEntity, ArrayList<RenterEntity> renterList) {
        LinkedHashSet<ApartmentEntity> rec = new LinkedHashSet<ApartmentEntity>();
        Set<CookieApEntity> res = renterEntity.getCookieAp();



        HashMap<RenterEntity, Integer> to_Rec = new HashMap<RenterEntity, Integer>();
        for (RenterEntity renter : renterList) {
            Integer amount = 0;
            Set<ReservationEntity> allu=renter.getReservationsByUsersUsername();
            for (ReservationEntity ur : allu) {
                for (CookieApEntity myr : res) {
                    if (myr.getApartmentid() == ur.getApartment().getId()) {
                        if(myr.getTimes()>1)
                            amount+=2;
                        else
                            amount++;
                    }
                }
            }
            to_Rec.put(renter, amount);
        }

        Integer i1 = 0;
        Integer i2 = 0;
        Integer i3 = 0;
        Integer i4 = 0;
        Integer i5 = 0;
        RenterEntity test_user = new RenterEntity();
        HashMap<RenterEntity, Integer> train_set = new HashMap<RenterEntity, Integer>();
        for (HashMap.Entry<RenterEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i1) {
                i1 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i1);
        to_Rec.remove(test_user, i1);
        for (HashMap.Entry<RenterEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i2) {
                i2 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i2);
        to_Rec.remove(test_user, i2);
        for (HashMap.Entry<RenterEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i3) {
                i3 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i3);
        to_Rec.remove(test_user, i3);
        for (HashMap.Entry<RenterEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i4) {
                i4 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i4);
        to_Rec.remove(test_user, i4);
        for (HashMap.Entry<RenterEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i5) {
                i5 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i5);
        to_Rec.remove(test_user, i5);
        int k = 0;

        Set<CookieSearchEntity> res2=renterEntity.getCookieSearch();
        for (HashMap.Entry<RenterEntity, Integer> entry : train_set.entrySet()) {
            RenterEntity u = entry.getKey();
            Set<ReservationEntity> allu=u.getReservationsByUsersUsername();
            for (ReservationEntity ur : allu) {
                for (CookieSearchEntity bb : res2) {

                    int score=0;
                    if (!(ur.getApartmentOwner().getUsersUsername()).equals(renterEntity.getUsersUsername())
                            && ur.getApartment().getCapacity() >= bb.getNum()) {

                        if(   bb.getLocation().toLowerCase().contains(ur.getApartment().getCountry().toLowerCase()))
                            score+=2;
                        if(   bb.getLocation().toLowerCase().contains(ur.getApartment().getTown().toLowerCase()))
                            score+=1;
                        if(    bb.getLocation().toLowerCase().contains(ur.getApartment().getArea().toLowerCase()))
                            score+=1;
                        if (k < 6 && score>=2) {
                            rec.add(ur.getApartment());
                            k++;
                        }
                    }
                }
            }
        }

        return rec;
    }



    /*
    public LinkedHashSet<ApartmentEntity> getRecommendationsRes(RenterEntity renterEntity,ArrayList<UsersEntity> renterList) {
        LinkedHashSet<ApartmentEntity> rec = new LinkedHashSet<ApartmentEntity>();
        Set<ReservationEntity> res = renterEntity.getReservationsByUsersUsername();


        HashMap<UsersEntity, Integer> to_Rec = new HashMap<UsersEntity, Integer>();
        for (UsersEntity u : renterList) {
            Integer amount = 0;
            ArrayList<ReservationEntity> allu=reservationRepository.findAllByRenter(u.getRenterByUsername());
            for (ReservationEntity ur : allu) {
                for (ReservationEntity myr : res) {
                    if (myr.getApartment().getId() == ur.getApartment().getId()) {
                        amount++;
                    }
                }
            }
            to_Rec.put(u, amount);
        }

        Integer i1 = 0;
        Integer i2 = 0;
        Integer i3 = 0;
        Integer i4 = 0;
        Integer i5 = 0;
        UsersEntity test_user = new UsersEntity();
        HashMap<UsersEntity, Integer> train_set = new HashMap<UsersEntity, Integer>();
        for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i1) {
                i1 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i1);
        to_Rec.remove(test_user, i1);
        for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i2) {
                i2 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i2);
        to_Rec.remove(test_user, i2);
        for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i3) {
                i2 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i3);
        to_Rec.remove(test_user, i3);
        for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i4) {
                i2 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i4);
        to_Rec.remove(test_user, i4);
        for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
            if (entry.getValue() > i5) {
                i2 = entry.getValue();
                test_user = entry.getKey();
            }
        }
        train_set.put(test_user, i5);
        to_Rec.remove(test_user, i5);
        int k = 0;

        sentiment sentiment=new sentiment();
        for (HashMap.Entry<UsersEntity, Integer> entry : train_set.entrySet()) {
            UsersEntity u = entry.getKey();
            ArrayList<ReservationEntity> allu=reservationRepository.findAllByRenter(u.getRenterByUsername());
            for (ReservationEntity ur : allu) {
                for (ReservationEntity bb : res) {
                    Set<CommentsEntity> comments = ur.getApartment().getComments();
                    for (CommentsEntity com : comments) {
                        Set<CommentsEntity> caat = bb.getApartment().getComments();
                        for (CommentsEntity ct : caat) {
                            if  ((sentiment.getsentiment(ct.getComment())==sentiment.getsentiment(com.getComment())) || (abs(ct.getRating()-com.getRating()) <=1) ) {
                                 if (!(ur.getApartmentOwner().getUsersUsername()).equals(renterEntity.getUsersUsername())
                                        && ur.getApartment().getId() != bb.getApartment().getId()) {
                                    if (k < 6) {
                                        rec.add(ur.getApartment());
                                        k++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return rec;
    }



public LinkedHashSet<ApartmentEntity> getRecommendationsCookie(RenterEntity renterEntity, ArrayList<UsersEntity> renterList) {
    LinkedHashSet<ApartmentEntity> rec = new LinkedHashSet<ApartmentEntity>();
    Set<CookieApEntity> res = renterEntity.getCookieAp();


    HashMap<UsersEntity, Integer> to_Rec = new HashMap<UsersEntity, Integer>();
    for (UsersEntity u : renterList) {
        Integer amount = 0;

        RenterEntity renter=renterRepository.findByUsersUsername(u.getUsername());
        ArrayList<ReservationEntity> allu=reservationRepository.findAllByRenter(renter);
        for (ReservationEntity ur : allu) {
            for (CookieApEntity myr : res) {
                if (myr.getApartmentid() == ur.getApartment().getId()) {
                    if(myr.getTimes()>1)
                        amount+=2;
                    else
                        amount++;
                }
            }
        }
        to_Rec.put(u, amount);
    }

    Integer i1 = 0;
    Integer i2 = 0;
    Integer i3 = 0;
    Integer i4 = 0;
    Integer i5 = 0;
    UsersEntity test_user = new UsersEntity();
    HashMap<UsersEntity, Integer> train_set = new HashMap<UsersEntity, Integer>();
    for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
        if (entry.getValue() > i1) {
            i1 = entry.getValue();
            test_user = entry.getKey();
        }
    }
    train_set.put(test_user, i1);
    to_Rec.remove(test_user, i1);
    for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
        if (entry.getValue() > i2) {
            i2 = entry.getValue();
            test_user = entry.getKey();
        }
    }
    train_set.put(test_user, i2);
    to_Rec.remove(test_user, i2);
    for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
        if (entry.getValue() > i3) {
            i3 = entry.getValue();
            test_user = entry.getKey();
        }
    }
    train_set.put(test_user, i3);
    to_Rec.remove(test_user, i3);
    for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
        if (entry.getValue() > i4) {
            i4 = entry.getValue();
            test_user = entry.getKey();
        }
    }
    train_set.put(test_user, i4);
    to_Rec.remove(test_user, i4);
    for (HashMap.Entry<UsersEntity, Integer> entry : to_Rec.entrySet()) {
        if (entry.getValue() > i5) {
            i5 = entry.getValue();
            test_user = entry.getKey();
        }
    }
    train_set.put(test_user, i5);
    to_Rec.remove(test_user, i5);
    int k = 0;

    Set<CookieSearchEntity> res2=renterEntity.getCookieSearch();
    for (HashMap.Entry<UsersEntity, Integer> entry : train_set.entrySet()) {
        UsersEntity u = entry.getKey();
        ArrayList<ReservationEntity> allu=reservationRepository.findAllByRenter(u.getRenterByUsername());
        for (ReservationEntity ur : allu) {
            for (CookieSearchEntity bb : res2) {

                int score=0;

                if (!(ur.getApartmentOwner().getUsersUsername()).equals(renterEntity.getUsersUsername())
                        && ur.getApartment().getCapacity() >= bb.getNum()) {

                    if(   bb.getLocation().toLowerCase().contains(ur.getApartment().getCountry().toLowerCase()))
                        score+=2;
                    if(   bb.getLocation().toLowerCase().contains(ur.getApartment().getTown().toLowerCase()))
                        score+=1;
                    if(    bb.getLocation().toLowerCase().contains(ur.getApartment().getArea().toLowerCase()))
                        score+=1;
                    if (k < 6 && score>=2) {
                        rec.add(ur.getApartment());
                        k++;
                    }
                }
            }
        }
    }

    return rec;
}
     */
}
