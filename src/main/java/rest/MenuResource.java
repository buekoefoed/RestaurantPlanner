package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.RecipeDTO;
import facades.FacadeExample;
import facades.MenuFacade;
import facades.ScraperFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("restaurant")
public class MenuResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            EMF_Creator.DbSelector.DEV,
            EMF_Creator.Strategy.CREATE);
    private static final MenuFacade MENU_FACADE = MenuFacade.getMenuFacade(EMF);
    private static final ScraperFacade SCRAPER_FACADE = ScraperFacade.getScraperFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllRecipes() {
        List<RecipeDTO> recipeDTOS = MENU_FACADE.getAllRecipes();
        return GSON.toJson(recipeDTOS);
    }
}
