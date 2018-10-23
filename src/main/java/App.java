import static spark.Spark.*;
import spark.template.velocity.VelocityTemplateEngine;
import spark.ModelAndView;
import static spark.debug.DebugScreen.enableDebugScreen;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String [] args){
        String layout = "templates/layout.vtl";
        enableDebugScreen();
        staticFileLocation("/public");
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        // This tells our app that if Heroku sets a port for us, we need to use that port.
        // Otherwise, if they do not, continue using port 4567.
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }
        setPort(port);

        get("/", (request, response)->{
            Map<String, Object> model = new HashMap<String, Object>();

            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/sighting", (request, response)->{
            Map<String, Object> model = new HashMap<String, Object>();
            String rangerName = request.queryParams("rangerName");
            String sightingLocation = request.queryParams("sightingLocation");
            String animalName = request.queryParams("animalName");
            String animalAge = request.queryParams("animalAge");
            String animalHealth = request.queryParams("animalHealth");
            String animalSpecies = request.queryParams("animalSpecies");

            if (animalSpecies.equals("safe")){
                RegisterAnimal registerAnimal = new RegisterAnimal(animalName,animalAge, animalHealth, animalSpecies);
                registerAnimal.save();
                Sighting sighting = new Sighting(rangerName, sightingLocation, registerAnimal.getId());
                sighting.save();
            }else if (animalSpecies.equals("endangered")){
                EndangeredAnimal endangeredAnimal = new EndangeredAnimal(animalName, animalAge, animalHealth, animalSpecies);
                endangeredAnimal.save();
                Sighting sighting = new Sighting(rangerName, sightingLocation, endangeredAnimal.getId());
            }

            model.put("sightings", Sighting.all());
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        },new VelocityTemplateEngine());

        get("/sighting", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();

            model.put("template", "templates/sighting.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
    }
}
