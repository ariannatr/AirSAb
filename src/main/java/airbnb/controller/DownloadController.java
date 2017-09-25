package airbnb.controller;

import airbnb.model.ApartmentEntity;
import airbnb.model.UsersEntity;
import airbnb.service.ApartmentService;
import airbnb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Σταυρίνα on 25/9/2017.
 */
@Controller
public class DownloadController {

    @Autowired
    private UsersService userService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    ServletContext context;

    @RequestMapping("/downloadusers/{ftype}")
    public void downloadusers(@PathVariable("ftype") String ftype,HttpServletRequest request, HttpServletResponse response) throws IOException {

        // get absolute path of the application
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        System.out.println("appPath = " + appPath);

        // construct the complete absolute path of the file
        String fullPath = appPath + "/uploads/"+ftype+".xml";
        File downloadFile = new File(fullPath);

        // Check if file exists
        try {
            if(ftype.equals("users"))
                createUsersFile(userService.findAll(), ftype, downloadFile);
            else if(ftype.equals("owners"))
                createUsersFile(userService.findAllOwners(), ftype, downloadFile);
            else if(ftype.equals("renters"))
                createUsersFile(userService.findAllRenters(), ftype, downloadFile);

            String downloadFolder = context.getRealPath("/uploads/");
            Path file = Paths.get(downloadFolder, new String(ftype + ".xml"));

            response.setContentType("application/xml");
            response.addHeader("Content-Disposition", "attachment; filename=" + ftype + ".xml");

            //copies all bytes from a file to an output stream
            Files.copy(file, response.getOutputStream());
            //flushes output stream
            response.getOutputStream().flush();
        }
        catch (Exception e){
            System.out.println("Error :- " + e.getMessage());
        }


    }

    private void createUsersFile(ArrayList<UsersEntity> users, String ftype ,File file)throws ParserConfigurationException ,TransformerException{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("CONFIGURATION");
        doc.appendChild(rootElement);
        Element browser = doc.createElement("BROWSER");
        browser.appendChild(doc.createTextNode("chrome"));
        rootElement.appendChild(browser);
        Element base = doc.createElement("TITLE");
        base.appendChild(doc.createTextNode(ftype));
        rootElement.appendChild(base);

        for(UsersEntity usersEntity :users) {
            Element user = doc.createElement("USER");
            rootElement.appendChild(user);

            Element usrUsername = doc.createElement("USERNAME");
            usrUsername.appendChild(doc.createTextNode(usersEntity.getUsername()));
            user.appendChild(usrUsername);

            Element usrName = doc.createElement("NAME");
            usrName.appendChild(doc.createTextNode(usersEntity.getName()));
            user.appendChild(usrName);

            Element usrSurname = doc.createElement("SURNAME");
            usrSurname.appendChild(doc.createTextNode(usersEntity.getSurname()));
            user.appendChild(usrSurname);

            Element usrMail = doc.createElement("EMAIL");
            usrMail.appendChild(doc.createTextNode(usersEntity.getEmail()));
            user.appendChild(usrMail);

            Element usrTel = doc.createElement("TELEPHONE");
            usrTel.appendChild(doc.createTextNode(usersEntity.getTelephone()));
            user.appendChild(usrTel);

            Element usrType = doc.createElement("TYPE");
            usrType.appendChild(doc.createTextNode(userService.getType(usersEntity)));
            user.appendChild(usrType);
        }


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);

        System.out.println("File saved!");

    }

    @RequestMapping("/downloadapartments")
    public void downloadapartments(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // get absolute path of the application
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        System.out.println("appPath = " + appPath);

        // construct the complete absolute path of the file
        String fullPath = appPath + "/uploads/apartments.xml";
        File downloadFile = new File(fullPath);

        // Check if file exists
        try {

            createApartmentsFile(apartmentService.findAll(), downloadFile);

            String downloadFolder = context.getRealPath("/uploads/");
            Path file = Paths.get(downloadFolder, new String("apartments.xml"));

            response.setContentType("application/xml");
            response.addHeader("Content-Disposition", "attachment; filename=apartments.xml");

            //copies all bytes from a file to an output stream
            Files.copy(file, response.getOutputStream());
            //flushes output stream
            response.getOutputStream().flush();
        }
        catch (Exception e){
            System.out.println("Error :- " + e.getMessage());
        }
    }

    private void createApartmentsFile(ArrayList<ApartmentEntity> aparts,  File file)throws ParserConfigurationException ,TransformerException{
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("CONFIGURATION");
        doc.appendChild(rootElement);
        Element browser = doc.createElement("BROWSER");
        browser.appendChild(doc.createTextNode("chrome"));
        rootElement.appendChild(browser);
        Element base = doc.createElement("TITLE");
        base.appendChild(doc.createTextNode("Apartments"));
        rootElement.appendChild(base);

        for(ApartmentEntity apartmentEntity :aparts) {
            Element ap = doc.createElement("APARTMENT");
            rootElement.appendChild(ap);

            Element id = doc.createElement("ID");
            id.appendChild(doc.createTextNode(String.valueOf(apartmentEntity.getId())));
            ap.appendChild(id);

            Element apName = doc.createElement("NAME");
            apName.appendChild(doc.createTextNode(apartmentEntity.getName()));
            ap.appendChild(apName);

            Element description = doc.createElement("DESCRIPTION");
            description.appendChild(doc.createTextNode(apartmentEntity.getDescription()));
            ap.appendChild(description);


            Element country = doc.createElement("COUNTRY");
            country.appendChild(doc.createTextNode(apartmentEntity.getCountry()));
            ap.appendChild(country);

            Element town = doc.createElement("TOWN");
            town.appendChild(doc.createTextNode(apartmentEntity.getTown()));
            ap.appendChild(town);

            Element area= doc.createElement("AREA");
            area.appendChild(doc.createTextNode(apartmentEntity.getArea()));
            ap.appendChild(area);

            Element startdate = doc.createElement("STARTDATE");
            startdate.appendChild(doc.createTextNode(apartmentEntity.getStartdate()));
            ap.appendChild(startdate);

            Element finaldate = doc.createElement("FINALDATE");
            finaldate.appendChild(doc.createTextNode(apartmentEntity.getFinaldate()));
            ap.appendChild(finaldate);

            Element rating = doc.createElement("RATING");
            rating.appendChild(doc.createTextNode(String.valueOf(apartmentEntity.getRating())));
            ap.appendChild(rating);

            Element owner = doc.createElement("OWNER");
            owner.appendChild(doc.createTextNode(apartmentEntity.getOwner().getUsersUsername()));
            ap.appendChild(owner);

            Element price= doc.createElement("PRICE");
            price.appendChild(doc.createTextNode(String .valueOf(apartmentEntity.getPrice())));
            ap.appendChild(price);

            Element minres = doc.createElement("MINRESERVATION");
            minres.appendChild(doc.createTextNode(String.valueOf(apartmentEntity.getMinimumres())));
            ap.appendChild(minres);

            Element type = doc.createElement("TYPE");
            type.appendChild(doc.createTextNode(apartmentService.getType(apartmentEntity)));
            ap.appendChild(type);

            Element capacity = doc.createElement("CAPACITY");
            capacity.appendChild(doc.createTextNode(String.valueOf(apartmentEntity.getCapacity())));
            ap.appendChild(capacity);

            Element floor = doc.createElement("FLOOR");
            floor.appendChild(doc.createTextNode(String.valueOf(apartmentEntity.getFloor())));
            ap.appendChild(floor);

            Element spacearea = doc.createElement("SPACEAREA");
            spacearea.appendChild(doc.createTextNode(String.valueOf(apartmentEntity.getSpaceArea())));
            ap.appendChild(spacearea);

            Element beds= doc.createElement("BEDS");
            beds.appendChild(doc.createTextNode(String.valueOf(apartmentEntity.getBeds())));
            ap.appendChild(beds);

            Element baths = doc.createElement("BATHS");
            baths.appendChild(doc.createTextNode(String.valueOf(apartmentEntity.getBaths())));
            ap.appendChild(baths);

            Element parking = doc.createElement("PARKING");
            if(apartmentEntity.getParking()==1)
                parking.appendChild(doc.createTextNode("YES"));
            else
                parking.appendChild(doc.createTextNode("NO"));
            ap.appendChild(parking);

            Element internet = doc.createElement("INTERNET");
            if(apartmentEntity.getInternet()==1)
                internet.appendChild(doc.createTextNode("YES"));
            else
                internet.appendChild(doc.createTextNode("NO"));
            ap.appendChild(internet);

            Element ac = doc.createElement("AC");
            if(apartmentEntity.getAc()==1)
                ac.appendChild(doc.createTextNode("YES"));
            else
                ac.appendChild(doc.createTextNode("NO"));
            ap.appendChild(ac);

            Element lift = doc.createElement("ELEVATOR");
            if(apartmentEntity.getElevator()==1)
                lift.appendChild(doc.createTextNode("YES"));
            else
                lift.appendChild(doc.createTextNode("NO"));
            ap.appendChild(lift);

            Element tv= doc.createElement("TV");
            if(apartmentEntity.getTv()==1)
                tv.appendChild(doc.createTextNode("YES"));
            else
                tv.appendChild(doc.createTextNode("NO"));
            ap.appendChild(tv);

            Element kitchen = doc.createElement("KITCHEN");
            if(apartmentEntity.getKitchen()==1)
                kitchen.appendChild(doc.createTextNode("YES"));
            else
                kitchen.appendChild(doc.createTextNode("NO"));
            ap.appendChild(kitchen);

            Element heating = doc.createElement("HEATING");
            if(apartmentEntity.getHeating()==1)
                heating.appendChild(doc.createTextNode("YES"));
            else
                heating.appendChild(doc.createTextNode("NO"));
            ap.appendChild(heating);

            Element livingroom = doc.createElement("LIVINGROOM");
            if(apartmentEntity.getLivingroom()==1)
                livingroom.appendChild(doc.createTextNode("YES"));
            else
                livingroom.appendChild(doc.createTextNode("NO"));
            ap.appendChild(livingroom);

            Element pets = doc.createElement("PETS-ALLOWED");
            if(apartmentEntity.getPets()==1)
                pets.appendChild(doc.createTextNode("YES"));
            else
                pets.appendChild(doc.createTextNode("NO"));
            ap.appendChild(pets);

            Element smoking = doc.createElement("SMOKING-ALLOWED");
            if(apartmentEntity.getSmoking()==1)
                smoking.appendChild(doc.createTextNode("YES"));
            else
                smoking.appendChild(doc.createTextNode("NO"));
            ap.appendChild(smoking);

            Element events = doc.createElement("FOR-EVENTS");
            if(apartmentEntity.getPets()==1)
                events.appendChild(doc.createTextNode("YES"));
            else
                events.appendChild(doc.createTextNode("NO"));
            ap.appendChild(events);



        }


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);

        System.out.println("File saved!");

    }

}
