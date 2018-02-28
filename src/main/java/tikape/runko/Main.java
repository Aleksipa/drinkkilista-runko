package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.database.DrinkkiRaakaAineDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.DrinkkiRaakaAine;
import tikape.runko.domain.RaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {

        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        Database database = new Database("jdbc:sqlite:drinkki.db");

        database.init();

        DrinkkiDao drinkkiDao = new DrinkkiDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);
        DrinkkiRaakaAineDao drinkkiRaakaAineDao = new DrinkkiRaakaAineDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/drinkit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());
            map.put("raakaaineet", raakaAineDao.findAll());
            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());

        get("/drinkit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "drinkki");
        }, new ThymeleafTemplateEngine());

        get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAineet", raakaAineDao.findAll());

            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());

        post("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaAine", raakaAineDao.findAll());
            String raakaAineenNimi = req.queryParams("aine");

            raakaAineDao.saveOrUpdate(new RaakaAine(-1, raakaAineenNimi));
            res.redirect("/ainekset");
            return "";
        });
        post("/ykkoslomake", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());
            String drinkinNimi = req.queryParams("aine");

            drinkkiDao.saveOrUpdate(new Drinkki(-1, drinkinNimi));
            res.redirect("/drinkit");
            return "";
        });
        post("/kakkoslomake", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());

            String drinkinNimi = req.queryParams("valittuDrinkki");
            String raakaAineenNimi = req.queryParams("valittuRaakaAine");
            Integer lukumaara = Integer.parseInt(req.queryParams("annettuMaara"));
            Integer jarjestys = Integer.parseInt(req.queryParams("annettuJarjestys"));
            String ohje = req.queryParams("annettuOhje");

            drinkkiRaakaAineDao.saveOrUpdate(new DrinkkiRaakaAine(-1, drinkinNimi, raakaAineenNimi, lukumaara, jarjestys, ohje));
            res.redirect("/drinkit");
            return "";
        });
    }
}
