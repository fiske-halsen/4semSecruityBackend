package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.RentalDTO;
import dto.CarsDTO;
import dto.CreateRentalDTO;
import dto.RentalsDTO;
import entities.User;
import facades.OrderFacade;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;
//import utils.SetupTestUsers;

@Path("order")
public class RentalResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final ExecutorService ES = Executors.newCachedThreadPool();
    private static final OrderFacade FACADE = OrderFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static String cachedResponse;
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getreservations")
    @RolesAllowed("admin")
    public String getReservations() {
        /* Vi tager ikke params med, så vi undgår broken access control, 
        alt information hentes fra den bruger der er logget ind, derudover
        bruger vi role based access control @RolesAllowed
        */
        RentalsDTO rentals = FACADE.getReservations();
        return GSON.toJson(rentals);
    }
    

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("makereservation")
    @RolesAllowed("user")
    public String makeReservation(String createRentalDTO) {
        CreateRentalDTO rentalReservation = GSON.fromJson(createRentalDTO, CreateRentalDTO.class);
        RentalDTO rental = FACADE.makeReservation(rentalReservation);
        return GSON.toJson(rental);
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getcars")
    public String getCars() {
        CarsDTO cars = FACADE.getCars();
        return GSON.toJson(cars);
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("editreservation")
    public String editReservation(String rentalDTO) {
        RentalDTO editRentalDTO = GSON.fromJson(rentalDTO, RentalDTO.class);
        RentalDTO editReservation = FACADE.editReservation(editRentalDTO);
        return GSON.toJson(editReservation);
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deletereservation/{id}")
    public String deleteReservation(@PathParam("id") long id){
        RentalDTO deletedReservation = FACADE.deleteReservation(id);
        return GSON.toJson(deletedReservation);
    }
    


}
