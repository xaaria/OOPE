package oope2018ht.lauta;

import fi.uta.csjola.oope.lista.Solmu;
import java.util.regex.Pattern;
import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.OmaLista;
import oope2018ht.viestit.Viesti;

/**
 * 
 * @author OP
 * 
 * Ketju, eli Thread, eli lanka
 */
public class Ketju
    implements Comparable {
    
    /** Ketjun ID */
    private int id;
    /** Ketjun tekstisisältö, eli aihe, eli otsikko */
    private String topic; 
    /** Kaikki ketjuun vastaavat viestit (välillisesti tai suoraan) */
    private OmaLista replys;
    
    /**
     * Rakennin
     * 
     * @param id Ketjun ID
     * @param topic Ketjun aihe
     * @param r Ketjun kaikki Viestit, eli vastaukset
     */
    public Ketju(int id, String topic, OmaLista r) {
        this.setId(id);
        this.setTopic(topic);
        this.setReplys(new OmaLista()); // tyhjä lista
    }
    
    
    /**
     * Asettaa Ketjun ID:n. Oltavsa suurempi kuin 0
     * 
     * @param id Ketjun ID
     * @throws IllegalArgumentException Viestin ID:n oltava suurempi kuin 0
     */
    @Setteri
    public void setId(int id) {
        if(id<=0) { throw new IllegalArgumentException(); }
        this.id = id;
    }
    /**
     * Palauttaa Ketjun ID:n
     * 
     * @return int
     */
    @Getteri
    public int getId() {
        return this.id;
    }
    
    /**
     * Asettaa Ketjun aiheen/sisällön. Oltava väh. 1 merkin mittainen.
     * 
     * @param t Ketjun aihe
     */
    @Setteri
    public void setTopic(String t) {
        if(t==null || t.length()<=0) {
            throw new IllegalArgumentException("");
        }
        this.topic = t;
    }
    
    /**
     * Palauttaa Ketjun aiheen
     * 
     * @return String
     */
    @Getteri
    public String getTopic() {
        return this.topic;
    }
   
    
    /**
     * Asettaa Ketjun Viestit.
     * (Ei käytännössä koskaan käytetty)
     * 
     * @param replys Vastaukset OmaLista-muodossa
     */
    @Setteri
    public void setReplys(OmaLista replys) {
        this.replys = replys;
    }
    
    /**
     * Palauttaa Ketjun vastaukset OmaLista-muodossa.
     * 
     * @return OmaLista
     */
    @Getteri
    public OmaLista getReplys() {
        return this.replys;
    }
    

    
    
    /**
     * Hakee Ketjun vastauksien määrän. Myös välillisesti vastaavat. 
     * 
     * 0, jos getReplys() esim. palauttaa NULL eikä koko():a saada.
     * 
     * @return int
     */
    public int getReplyCount() {
        try {
            return this.getReplys().koko();
        } catch(Exception e) {
            return 0;
        }
    }
    

    /**
     * Palautaa Ketjun merkkijonoesityksen
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "#" + this.getId() +" "+ this.getTopic() + " (" + this.getReplyCount() + " messages)";
    }

    
    /**
     * Suorittaa vertailun Ketjun ja Objetkin o välillä.
     * Vertaillaan siis objektien välistä suhdetta.
     * Käytännössä vertaillaan Ketjun ja oletetun Ketju-olion ID:eitä.
     * 
     * 0 jos sama olio kuin itse TAI sama ID
     * 1 jos this > o
     * -1 jos this < 0 tai parametri epäkelpo vertailuun.
     * 
     * @param o Olio, johon verrataan
     * @return int
     */
    @Override
    public int compareTo(Object o) {
        if(this == o) { return 0; }
        
        if(this.getId() > ((Ketju) o).getId()) {
            return 1;
        } else if(this.getId() == ((Ketju) o).getId()) {
            return 0;
        }
        return -1;       
    }
    
    /**
     * Suorittaa haun Viestistä (joka on samaa muotoa kuin Viesti.toString()).
     * 
     * Pattern-luokka on merkkikokoherkkä (Case sensitive) oletuksena.
     * 
     * @param q Hakusana (q = query)
     * @return OmaLista Lista löydetyistä Viesteistä.
     */
    public OmaLista searchFromThreadReplys(String q) {
    
        // Hakutulokset. Aluksi tyhjä OmaLista.
        OmaLista resultReplys = new OmaLista();
        
        // Mitä tahansa merkkiä 0-X määrä, hakusana ja mitä tahansa merkkiä
        // 0-X määrä.
        Pattern pattern = Pattern.compile(".*"+q+".*");
        
        try {
           
            Solmu s = this.getReplys().paa();

            if(this.getReplys() == null || this.getReplys().onkoTyhja()) {
                return resultReplys; // tyhjä OmaLista
            }

            do {

                if(s.alkio() instanceof Viesti) {
                    //System.out.println("...Etsitään :: "+s.alkio());
                    if(pattern.matcher( ((Viesti)s.alkio()).toString()).matches()){
                        // löytyi osuma. Lisää tuloksiin.
                       //System.out.println("--Osuma!");
                       resultReplys.lisaa(s.alkio());
                    } 
                }

                s = s.seuraava();
            } while(s != null);
    
        } catch(Exception e) { // pass
        }
        
        return resultReplys; // Palauta tulokset
    }
    

    /**
     * Vertaillaan, ovatko kaksi Ketjua yhtenäiset ID:ltään. 
     * Palautetaan True, jos ovat.
     * 
     * @param o Objekti
     * @return boolean 
     */
    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        else if(o instanceof Ketju) {
            return this.getId() == ((Ketju) o).getId();
        }
        return false;
    }
    
    
}
