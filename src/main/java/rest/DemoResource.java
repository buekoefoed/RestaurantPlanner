package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.User;
import facades.ScraperFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            List<User> users = em.createQuery("select user from User user").getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

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

    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("luke")
    @RolesAllowed("user")
    public void getLuke(@Suspended final AsyncResponse ar) throws ExecutionException, InterruptedException {
        ar.setTimeoutHandler(asyncResponse -> asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE).entity("You fucked up").build()));
        ar.setTimeout(12, TimeUnit.SECONDS);
        new Thread(() -> {
            ScraperFacade scraperFacade = new ScraperFacade();
            try {
                ar.resume(GSON.toJson(scraperFacade.runParralel()));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    */
}
