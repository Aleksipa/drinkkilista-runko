package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.RaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {

        Database database = new Database("jdbc:sqlite:drinkki.db");

        database.init();

        DrinkkiDao drinkkiDao = new DrinkkiDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);

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
            map.put("raakaAine", drinkkiDao.findAll());

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
        post("/drinkit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());
            String drinkinNimi = req.queryParams("aine");

            drinkkiDao.saveOrUpdate(new Drinkki(-1, drinkinNimi));
            res.redirect("/drinkit");
            return "";
        });
    }
}
