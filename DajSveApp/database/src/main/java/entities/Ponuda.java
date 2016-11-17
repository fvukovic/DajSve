package entities;

/**
 * Created by Helena on 12.11.2016..
 */

public class Ponuda {

    String tekstPonude;
    int cijena;
    int popust;
    int cijenaOriginal;
    String urlSlike;
    int usteda;
    String kategorija;
    String grad;
    String datumPonude;

    public Ponuda(String tekstPonude, int cijena, int popust, int cijenaOriginal, String urlSlike, int usteda, String kategorija, String grad, String datumPonude){
        this.tekstPonude = tekstPonude;
        this.cijena = cijena;
        this.popust = popust;
        this.cijenaOriginal = cijenaOriginal;
        this.urlSlike = urlSlike;
        this.usteda = usteda;
        this.kategorija = kategorija;
        this.grad = grad;
        this.datumPonude = datumPonude;
    }

    public String getNaziv() {
        return tekstPonude;
    }
    public String getCijena() {
        return Integer.toString(cijena);
    }
    public String getURL() {
        return urlSlike;
    }
}
