package airbnb.service;

import airbnb.model.*;
import airbnb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Arianna on 29/8/2017.
 */
@Service("apartmentService")
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ReservedRepository reservedRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RenterRepository renterRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public ApartmentEntity findById(Integer id){
        return  apartmentRepository.findById(id);
    }

    @Override
    public  void saveApartment(ApartmentEntity apartmentEntity, OwnerEntity ownerEntity,String photograph)
    {
        apartmentEntity.setOwnerByOwner(ownerEntity);
        apartmentEntity.setPhoto(photograph);
        apartmentEntity.setPhoto2("");
        apartmentEntity.setPhoto3("");
        apartmentEntity.setPhoto4("");
        apartmentEntity.setRating((float)0);
        apartmentEntity.setReviews(0);
        Set<CommentsEntity> coms0=new HashSet<CommentsEntity>(0);
        apartmentEntity.setComments( coms0);
        apartmentRepository.save(apartmentEntity);
        Set<ApartmentEntity> apartmentEntitySet=ownerEntity.getApartments();
        apartmentEntitySet.add(apartmentEntity);
        ownerRepository.save(ownerEntity);
    }

    @Override
    public ArrayList<String> getFeatures(ApartmentEntity apartmentEntity){
        ArrayList<String> features=new ArrayList<String>();
        if(apartmentEntity.getParking()==1) features.add("Parking");
        if(apartmentEntity.getInternet()==1) features.add("Internet");
        if(apartmentEntity.getAc()==1) features.add("Air Condition");
        if(apartmentEntity.getElevator()==1) features.add("Elevator");
        if(apartmentEntity.getTv()==1) features.add("Tv");
        if(apartmentEntity.getKitchen()==1) features.add("Kitchen");
        if(apartmentEntity.getHeating()==1) features.add("Central Heating");
        if(apartmentEntity.getLivingroom()==1) features.add("Living Room");
        if(apartmentEntity.getPets()==1) features.add("Pets Allowed");
        if(apartmentEntity.getSmoking()==1) features.add("Smoking Allowed");
        if(apartmentEntity.getEvents()==1) features.add("For Events");
        return features;
    }


    @Override
    public String getType(ApartmentEntity apartmentEntity)
    {
        int type=apartmentEntity.getType();
        if(type==0)
            return "Private Residence";
        else if(type==1)
            return "Public Residence";
        else if(type==2)
            return "Whole Residence";
        else
            return "Unknown";
    }



    @Override
    public void updateApartment(ApartmentEntity ap, ApartmentEntity old){
        if(!old.getDescription().replaceAll(" ","").equals("")){
            ap.setDescription(old.getDescription());
        }
        if(!old.getName().replaceAll(" ","").equals(""))
        {
            ap.setName(old.getName());
        }
        if(!old.getCountry().replaceAll(" ","").equals(""))
        {
            ap.setCountry(old.getCountry());
        }
        if(!old.getTown().replaceAll(" ","").equals(""))
        {
            ap.setTown(old.getTown());
        }
        if(!old.getArea().replaceAll(" ","").equals(""))
        {
            ap.setArea(old.getArea());
        }
        if(!old.getStartdate().replaceAll(" ","").equals(""))
        {
            ap.setStartdate(old.getStartdate());
        }
        if(!old.getFinaldate().replaceAll(" ","").equals(""))
        {
            ap.setFinaldate(old.getFinaldate());
        }

        if(!String.valueOf(old.getPrice()).replaceAll(" ","").equals(""))
        {
            ap.setPrice(old.getPrice());
        }
        if(!String.valueOf(old.getFloor()).replaceAll(" ","").equals(""))
        {
            ap.setFloor(old.getFloor());
        }
        if(!String.valueOf(old.getCapacity()).replaceAll(" ","").equals(""))
        {
            ap.setCapacity(old.getCapacity());
        }

        if(!String.valueOf(old.getSpaceArea()).replaceAll(" ","").equals(""))
        {
            ap.setSpaceArea(old.getSpaceArea());
        }

        if(!String.valueOf(old.getBeds()).replaceAll(" ","").equals(""))
        {
            ap.setBeds(old.getBeds());
        }

        if(!String.valueOf(old.getMinimumres()).replaceAll(" ","").equals(""))
        {
            ap.setMinimumres(old.getMinimumres());
        }
        if(!String.valueOf(old.getBaths()).replaceAll(" ","").equals(""))
        {
            ap.setBaths(old.getBaths());
        }
        ap.setParking(old.getParking());
        ap.setInternet(old.getInternet());
        ap.setAc(old.getAc());
        ap.setElevator(old.getElevator());
        ap.setTv(old.getTv());
        ap.setKitchen(old.getKitchen());
        ap.setHeating(old.getHeating());
        ap.setLivingroom(old.getLivingroom());

        ap.setPets(old.getPets());
        ap.setSmoking(old.getSmoking());
        ap.setEvents(old.getEvents());
        ap.setType(old.getType());
        apartmentRepository.save(ap);

    }

    @Override
    public Page<ApartmentEntity> findOwnersAparts(OwnerEntity ownerEntity, Pageable pageable){
              return apartmentRepository.findAllByOwner(ownerEntity,pageable);
    }


    @Override
    public void uploadPhoto(ApartmentEntity ap,String photo)
    {
        ap.setPhoto(photo);
        apartmentRepository.save(ap);
    }

    @Override
    public void uploadPhoto2(ApartmentEntity ap,String photo)
    {
        ap.setPhoto2(photo);
        apartmentRepository.save(ap);
    }

    @Override
    public void uploadPhoto3(ApartmentEntity ap,String photo)
    {
        ap.setPhoto3(photo);
        apartmentRepository.save(ap);
    }

    @Override
    public void uploadPhoto4(ApartmentEntity ap,String photo)
    {
        ap.setPhoto4(photo);
        apartmentRepository.save(ap);
    }

    @Override
    public Page<ApartmentEntity> findAparts(Pageable pageable){
        return apartmentRepository.findAllOrderByPrice(pageable);
    }

    @Override
    public Page<ApartmentEntity> findAparts(Optional<String> arrivalDate,Optional<String> departureDate,Optional<Integer> people,Optional<String> town,Optional<String> area,Optional<String> country,Optional<Integer>heating,Optional<Float> maxPrice,Optional<Integer> kitchen,Optional<Integer> tv,Optional<Integer> type,Optional<Integer> elevator,Optional<Integer> ac,Optional<Integer> internet,Optional<Integer> parking,Pageable pageable) throws ParseException {
        List<ApartmentEntity> aparts = null;
        aparts=findAparts(country,town,area,arrivalDate,departureDate,people);
        if (heating.isPresent()) {
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (list.next().getHeating()==0)
                    list.remove();
            }
        }
        if (maxPrice.isPresent()) {
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (list.next().getPrice() > maxPrice.get())
                    list.remove();
            }
        }
        if (kitchen.isPresent()) {
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (list.next().getKitchen() == 0)
                    list.remove();
            }
        }
        if (tv.isPresent() ) {
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (list.next().getTv() == 0)
                    list.remove();
            }
        }
        if (elevator.isPresent() ) {
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (list.next().getElevator() == 0)
                    list.remove();
            }
        }
        if (ac.isPresent()) {
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (list.next().getAc() == 0)
                    list.remove();
            }
        }
        if (parking.isPresent() ) {
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (list.next().getParking() == 0)
                    list.remove();
            }
        }
        if (type.isPresent()) {
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (list.next().getType() != type.get())
                    list.remove();
            }
        }
        if (internet.isPresent()) {
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (list.next().getInternet() == 0)
                    list.remove();
            }
        }
        int start = pageable.getOffset();
        int end = (start + pageable.getPageSize()) > aparts.size() ? aparts.size() : (start + pageable.getPageSize());
        Page<ApartmentEntity> nea= new PageImpl(aparts.subList(start,end),pageable,aparts.size());
        return nea;
    }

    @Override
    public Page<ApartmentEntity> findAparts(Optional<String> country,Optional<String> town,Optional<String> area,Optional<String> arrivalDate,Optional<String> departureDate,Optional< Integer> people, Pageable pageable) throws ParseException {
        List<ApartmentEntity> aparts = null;
        boolean nofilter = true;
        if (country.isPresent() && !country.get().replaceAll(" ","").equals("")) {
            aparts = apartmentRepository.findAllByCountry(country.get());
            nofilter = false;
        }
        if (town.isPresent() && !town.get().replaceAll(" ","").equals("")  && nofilter==false) {
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (!list.next().getTown().equals(town.get()))
                    list.remove();
            }
        }
        if (town.isPresent() && !town.get().replaceAll(" ","").equals("") && nofilter==true) {
            nofilter = false;
            aparts = apartmentRepository.findAllByTown(town.get());

        }
        if (area.isPresent() && !area.get().replaceAll(" ","").equals("") && nofilter==false) {
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (!list.next().getArea().equals(area.get()))
                    list.remove();
            }
        }
        if (area.isPresent() && !area.get().replaceAll(" ","").equals("") && nofilter==true) {
            nofilter = false;
            aparts = apartmentRepository.findAllByArea(area.get());
        }

        if (people.isPresent() && nofilter==false) {
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (list.next().getCapacity() < people.get())
                    list.remove();
            }
        }
        if (people.isPresent() && nofilter==true) {
            nofilter = false;
            aparts = apartmentRepository.findAllByCapacityIsGreaterThanEqual(people.get());
        }
        if(arrivalDate.isPresent() && departureDate.isPresent() && !arrivalDate.get().replaceAll(" ","").equals("") && !departureDate.get().replaceAll(" ","").equals("") && nofilter==false){
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext())
            {
                 if(available(arrivalDate.get(),departureDate.get(),list.next())<0)
                    list.remove();
            }
        }
        else if(arrivalDate.isPresent() && departureDate.isPresent() && !arrivalDate.get().replaceAll(" ","").equals("") && !departureDate.get().replaceAll(" ","").equals("") && nofilter==true)
        {
            aparts=apartmentRepository.findAll();
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext())
            {
                if(available(arrivalDate.get(),departureDate.get(),list.next())<0)
                {
                    list.remove();
                }
            }
        }
        else if (arrivalDate.isPresent() && !arrivalDate.get().replaceAll(" ","").equals("") && nofilter==false) {
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                  if(list.next().getStartdate().compareTo(arrivalDate.get())>0)
                    list.remove();
            }
        }
        else if (arrivalDate.isPresent() && !arrivalDate.get().replaceAll(" ","").equals("") && nofilter==true) {
            nofilter = false;
            aparts = apartmentRepository.findAllByStartDate(arrivalDate.get());
        }
        else if (departureDate.isPresent() && !departureDate.get().replaceAll(" ","").equals("") && nofilter==false) {
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
               if(list.next().getFinaldate().compareTo(departureDate.get())<0)
                    list.remove();
            }
        }
        else if (departureDate.isPresent() && !departureDate.get().replaceAll(" ","").equals("") && nofilter==true) {
            nofilter = false;
            aparts = apartmentRepository.findAllByFinalDate(departureDate.get());

        }
        if(nofilter==true)
        {
            return apartmentRepository.findAllOrderByPrice(pageable);
        }
        int start = pageable.getOffset();
        int end = (start + pageable.getPageSize()) > aparts.size() ? aparts.size() : (start + pageable.getPageSize());
        Page<ApartmentEntity> nea= new PageImpl(aparts.subList(start,end),pageable,aparts.size());
        return nea;
    }

    public List<ApartmentEntity> findAparts(Optional<String> country,Optional<String> town,Optional<String> area,Optional<String> arrivalDate,Optional<String> departureDate,Optional< Integer> people) throws ParseException {
        List<ApartmentEntity> aparts = null;
        boolean nofilter = true;
        if (country.isPresent() && !country.get().replaceAll(" ","").equals("")) {
            aparts = apartmentRepository.findAllByCountry(country.get());
            nofilter = false;
        }
        if (town.isPresent() && !town.get().replaceAll(" ","").equals("")  && nofilter==false) {
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (!list.next().getTown().equals(town.get()))
                    list.remove();
            }
        }
        if (town.isPresent() && !town.get().replaceAll(" ","").equals("") && nofilter==true) {
            nofilter = false;
            aparts = apartmentRepository.findAllByTown(town.get());

        }
        if (area.isPresent() && !area.get().replaceAll(" ","").equals("") && nofilter==false) {
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (!list.next().getArea().equals(area.get()))
                    list.remove();
            }
        }
        if (area.isPresent() && !area.get().replaceAll(" ","").equals("") && nofilter==true) {
            nofilter = false;
            aparts = apartmentRepository.findAllByArea(area.get());
        }

        if (people.isPresent() && nofilter==false) {
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if (list.next().getCapacity() < people.get())
                    list.remove();
            }
        }
        if (people.isPresent() && nofilter==true) {
            nofilter = false;
            aparts = apartmentRepository.findAllByCapacityIsGreaterThanEqual(people.get());
        }
        if(arrivalDate.isPresent() && departureDate.isPresent() && !arrivalDate.get().replaceAll(" ","").equals("") && !departureDate.get().replaceAll(" ","").equals("") && nofilter==false){
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext())
            {
                if(available(arrivalDate.get(),departureDate.get(),list.next())<0)
                    list.remove();
            }
        }
        else if(arrivalDate.isPresent() && departureDate.isPresent() && !arrivalDate.get().replaceAll(" ","").equals("") && !departureDate.get().replaceAll(" ","").equals("") && nofilter==true)
        {
            aparts=apartmentRepository.findAllOrderByPrice();
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext())
            {
                if(available(arrivalDate.get(),departureDate.get(),list.next())<0)
                {
                    list.remove();
                }
            }
        }
        else if (arrivalDate.isPresent() && !arrivalDate.get().replaceAll(" ","").equals("") && nofilter==false)
        {
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if(list.next().getStartdate().compareTo(arrivalDate.get())>0)
                    list.remove();
            }
        }
        else if (arrivalDate.isPresent() && !arrivalDate.get().replaceAll(" ","").equals("") && nofilter==true) {
            nofilter = false;
            aparts = apartmentRepository.findAllByStartDate(arrivalDate.get());
        }
        else if (departureDate.isPresent() && !departureDate.get().replaceAll(" ","").equals("") && nofilter==false) {
            nofilter = false;
            Iterator<ApartmentEntity> list = aparts.iterator();
            while (list.hasNext()) {
                if(list.next().getFinaldate().compareTo(departureDate.get())<0)
                    list.remove();
            }
        }
        else if (departureDate.isPresent() && !departureDate.get().replaceAll(" ","").equals("") && nofilter==true) {
            nofilter = false;
            aparts = apartmentRepository.findAllByFinalDate(departureDate.get());

        }
        if(nofilter==true)
        {
            return apartmentRepository.findAllOrderByPrice();
        }
        return aparts;
    }

    @Override
    public  int makeReservation(ReservationEntity reservation, ApartmentEntity apart, RenterEntity renter) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String startDate=apart.getStartdate();
        String finalDate=apart.getFinaldate();
        String arrivalDate=reservation.getStartdate();
        String departureDate=reservation.getFinaldate();

        Date date = formatter.parse(arrivalDate);
        Date date2=formatter.parse(departureDate);
        Date dates= formatter.parse(startDate);
        Date datef= formatter.parse(finalDate);


        Set<ReservedEntity> reservedDays= apart.getReservedEntities();//reservedRepository.findByApartment(apart.getId());////days occupied
        System.out.println("To diamerisma einai kleismeno tis meres "+reservedDays);
        if(date2.after(date)&& (date2.before(datef)|| date2.compareTo(datef)==0)&& (date.after(dates)|| date.compareTo(dates)==0)){
            System.out.println("To diastima einai apodekto");
            int diff = date2.getDate()- date.getDate();
            TimeUnit timeUnit = TimeUnit.DAYS;
            System.out.print(timeUnit.convert(diff, TimeUnit.DAYS));
            if(diff<apart.getMinimumres())
                return -2;
            List<String> dateslist=new ArrayList<String>();     //the days to save as reserved
            dateslist.add(arrivalDate);
            for(int i=1;i<diff;i++)
            {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                System.out.println(formatter.format(calendar.getTime()));
                date=calendar.getTime();
                String tempDate=formatter.format(calendar.getTime());
                if(!reservedRepository.findByApartmentAndDate(apart,tempDate).isEmpty())
                    return -1;
                //if(reservedDays.contains(formatter.format(calendar.getTime())))
                  //  return -2;
                dateslist.add(tempDate);
            }
            System.out.println("Tha meinw tis meres "+dateslist);

            /*Save the reservation*/
            reservation.setApartment(apart);
            reservation.setRenterUsersUsername(renter);
            reservation.setApproval(0);
            reservation.setApartmentOwner(apart.getOwner());
            reservation.setTotalCost(apart.getPrice()*diff);
            System.out.println("Ty to save reservation for apart" + apart.getId() + ", with owner " + apart.getOwner());
            reservationRepository.save(reservation);

            /*Save renter's new Reservation*/
            Set<ReservationEntity> reservationEntitySetOfRenter = renter.getReservationsByUsersUsername();// renter's reservations
            reservationEntitySetOfRenter.add(reservation);
            renterRepository.save(renter);

            System.out.println("The days reserved before "+reservedDays);
            /*Save the "new" Reserved Days */
            for(int i=0;i<dateslist.size();i++)
            {
                ReservedEntity reservedEntity=new ReservedEntity(dateslist.get(i),apart);
                reservedDays.add(reservedEntity);
                System.out.println("Tha apothikeusw tin imera "+reservedEntity.getDate());
                reservedRepository.save(reservedEntity);
            }
            System.out.println("The days reserved now "+reservedDays);
            apart.setReservedEntities(reservedDays);
            apartmentRepository.save(apart);
            return 1;
        }
        else
            return -3;
    }

    @Override
    public void removephoto(ApartmentEntity apartmentEntity, Integer num){
        if(num==1)
            apartmentEntity.setPhoto("");
        else if(num==2)
            apartmentEntity.setPhoto2("");
        else if(num==3)
            apartmentEntity.setPhoto3("");
        else if(num==4)
            apartmentEntity.setPhoto4("");

        apartmentRepository.save(apartmentEntity);
        return;
    }

    private int available(String arrivalDate,String departureDate, ApartmentEntity apart) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String startDate=apart.getStartdate();
        String finalDate=apart.getFinaldate();

        Date date = formatter.parse(arrivalDate);
        Date date2=formatter.parse(departureDate);
        Date dates= formatter.parse(startDate);
        Date datef= formatter.parse(finalDate);

        Set<ReservedEntity> reservedDays= apart.getReservedEntities();//reservedRepository.findByApartment(apart.getId());////days occupied
        System.out.println("To diamerisma einai kleismeno tis meres "+reservedDays);
        if(date2.after(date)&& (date2.before(datef)|| date2.compareTo(datef)==0)&& (date.after(dates)|| date.compareTo(dates)==0)){
            System.out.println("To diastima einai apodekto");
            int diff = date2.getDate()- date.getDate();
            TimeUnit timeUnit = TimeUnit.DAYS;
            System.out.print(timeUnit.convert(diff, TimeUnit.DAYS));
            if(diff<apart.getMinimumres())
                return -2;
            List<String> dateslist=new ArrayList<String>();     //the days to save as reserved
           /*Find the dates in this one time space*/
            dateslist.add(arrivalDate);
            for(int i=1;i<diff;i++)
            {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                System.out.println(formatter.format(calendar.getTime()));
                date=calendar.getTime();
                String tempDate=formatter.format(calendar.getTime());
                if(!reservedRepository.findByApartmentAndDate(apart,tempDate).isEmpty())
                    return -1;
                dateslist.add(tempDate);
            }
            System.out.println("Tha meinw tis meres "+dateslist);
            return 1;
        }
        else
            return -3;
    }


    @Override
    public Page<ApartmentEntity> findAllPageable(Pageable pageable){
        return apartmentRepository.findAll(pageable);
    }

    public List<ApartmentEntity> findAll(){
        return apartmentRepository.findAll();
    }


    public Page<ReservationEntity> findAllReservations(Pageable pageable){
        return reservationRepository.findAll(pageable);
    }

    public Page<ReservationEntity> findAllReservationsByRenter(RenterEntity renter,Pageable pageable){
        return reservationRepository.findAllByRenter(renter,pageable);
    }

    public ArrayList<ReservationEntity> findAllReservations(){
        return reservationRepository.findAll();
    }

    public ArrayList<ReservationEntity> findAllReservationsByRenter(RenterEntity renter){
        return reservationRepository.findAllByRenter(renter);
    }

    @Override
    public Set<ReservationEntity> findAllReservationByOwner(OwnerEntity owner){
        return reservationRepository.findAllByApartmentOwner(owner);
    }

}
