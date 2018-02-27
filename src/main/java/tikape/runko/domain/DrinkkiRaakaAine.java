/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author Markus
 */
public class DrinkkiRaakaAine {

    private String drinkkiId;
    private String raakaAineId;
    private Integer id;
    private Integer jarjestys;
    private Integer lukumaara;
    private String ohje;

    public DrinkkiRaakaAine(Integer id, String drinkkiId, String raakaAineId, Integer lukumaara, Integer jarjestys, String ohje) {
        
        this.id = id;
        this.drinkkiId = drinkkiId;
        this.jarjestys = jarjestys;
        this.lukumaara = lukumaara;
        this.ohje = ohje;
        this.raakaAineId = raakaAineId;
    }

    public String getDrinkkiId() {
        return drinkkiId;
    }

    public void setDrinkkiId(String drinkkiId) {
        this.drinkkiId = drinkkiId;
    }

    public String getRaakaAineId() {
        return raakaAineId;
    }

    public void setRaakaAineId(String raakaAineId) {
        this.raakaAineId = raakaAineId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }

    public void setJarjestys(Integer jarjestys) {
        this.jarjestys = jarjestys;
    }

    public Integer getLukumaara() {
        return lukumaara;
    }

    public void setLukumaara(Integer lukumaara) {
        this.lukumaara = lukumaara;
    }

    public String getOhje() {
        return ohje;
    }

    public void setOhje(String ohje) {
        this.ohje = ohje;
    }

   

}
