package oope2018ht.viestit;

import oope2018ht.tiedostot.Tiedosto;
import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Komennettava;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.OmaLista;

/**
 *
 * @author OP
 * 
 * Viesti sisältää tunnisteen, tekstisisällön ja mahdollisen Tiedoston 
 * liitteenä. Viesti-luokalla on lisäksi attribuuttiuna siihen
 * liittyäv vastaukset, ja tieto siitä, mihin Viestiin se itse mahd. vastaa.s
 * 
 * "Viesti-luokan on toteutettava Komennettava-rajapinta, jossa on ohjelman 
 * toteuttamiseen ja testaamiseen soveltuvia metodeja. 
 * Kiinnitä geneerisiksi tyypeiksi Tieto ja OmaLista."
 * 
 */
public final class Viesti 
        implements Komennettava<Viesti>, Comparable<Viesti> {
 
    /** Viestin tunniste, eli ID */
    private int id;
    /** Viestin tekstisisältö */
    private String content ;
    /** Mihin Viesti-olioon tämä viestii mahdollisesti vastaa */
    private Viesti replyTo; 
    /** OmaLista-rakenne tähän Viestiin kohdistuvien vast. tallentamiseen */
    private OmaLista replys ;
    /** Mahdollinen liitetiedosto [tyyppiä Tiedosto] */
    private Tiedosto attachment;
    
    
    /**
     * Viesti-luokan rakennin
     * 
     * 
     * @param id Viestin ID
     * @param content Viestin tekstisisältö
     * @param replyTo Mihin viestiin mahdollisesti vastaa.
     * @param attch LiiteTiedosto
     * 
     * @throws IllegalArgumentException Poikkeus jos parametrit epäkelpoja.
     */
    public Viesti(int id, String content, Viesti replyTo, Tiedosto attch)
            throws IllegalArgumentException {
        this.setId(id);
        this.setContent(content);
        this.setReplyTo(replyTo);
        this.setReplys(new OmaLista()); // oletuksena tyhjä omalista
        this.setAttachment(attch);
    }
   
    /**
     * Asettaa Viestin ID:n
     * ID:n olta suurempi kuin 0
     * 
     * @param id Viestin ID
     * @throws IllegalArgumentException Poiukkeus jos ID pienempi kuin 1
     */
    @Setteri
    public void setId(int id) throws IllegalArgumentException {
        if(id <= 0) { throw new IllegalArgumentException(""); }
        this.id = id;
    }
    
    /**
     * Palauttaa viestin ID:n
     * 
     * @return int
     */
    @Getteri
    public int getId() {
        return this.id;
    }
    
    /**
     * Asettaa Viestin sisällön. Sisällön oltava pituudeltaan ainakin 1 merkki
     * 
     * @param cont Viestin tekstisisältö
     * @throws IllegalArgumentException Poikkeus, jos pituus alle 1.
     */
    @Setteri
    public void setContent(String cont) throws IllegalArgumentException {
        if(cont.length() <= 0) { throw new IllegalArgumentException(""); }
        this.content = cont;
    }
    
    /**
     * Palauttaa Viestin sisällön
     * 
     * @return String
     */
    @Getteri
    public String getContent() {
        return this.content;
    }
    
    /**
     * Asettaa attriuutin replyTo, eli Viestin johon tämä Viesti vastaa.
     * 
     * @param rt Viesti-olio. Viesti johon tämä (this) Viesti vastaa
     * @throws IllegalArgumentException Poikkeus, jos väärän tyyppinen parametri
     */
    @Setteri
    public void setReplyTo(Viesti rt) throws IllegalArgumentException {
        if(rt == null) {
            this.replyTo = null;
            return;
        }
        if(!(rt instanceof Viesti)) {
            throw new IllegalArgumentException("");
        }
        this.replyTo = rt;
    }
    /**
     * Palauttaa sen Viestin, johon tämä Viest-olio vastaa.
     * 
     * @return Viesti
     */
    @Getteri
    public Viesti getReplyTo() {
        return this.replyTo;
    }
    
    /**
    * Asettaa Viestille sen vastaukset. Rakenteena OmaLista.
    * 
    * @param replys Vastausket OmaLista-muodossa
    * @throws IllegalArgumentException Poikkeus jos väärän tyyppinen parametri
    * (toisaalta, onko metodin kutsuminen edes mahdollista väärällä?)
    */
    @Setteri
    public void setReplys(OmaLista replys) throws IllegalArgumentException {
        if(replys == null) {
            this.replyTo = null;
        }
        else if(!(replys instanceof OmaLista)) {
            throw new IllegalArgumentException("");
        }
        this.replys = replys;
    }
    
    /**
     * Palauttaa Viestiin vastaavat Viestit jotka ovat tallessa
     * OmaLista -rakeneteessa.
     * 
     * @return OmaLista
     */
    @Getteri
    public OmaLista getReplys() {
        return this.replys;
    }
    
    /**
     * Asettaa attribuutin attachment ("liite") arvon, eli Viestin Tiedoston.
     * 
     * @param attch Tiedosto-olio
     * @throws IllegalArgumentException Poikkeus, jos parametri väärää tyyppiä
     */
    @Setteri
    public void setAttachment(Tiedosto attch) throws IllegalArgumentException {
        if(attch == null) {
            this.attachment = null;
        }
        else if(!(attch instanceof Tiedosto)) {
            throw new IllegalArgumentException("");
        }
        this.attachment = attch;
    }
    
    /**
     * Palauttaa Viestiin liitetyn Tiedosto-olion (tai NULL)
     * 
     * @return Tiedosto
     */
    @Getteri
    public Tiedosto getAttachment() {
        return this.attachment;
    }

    
    /**
     * Komennettava -rajapinnan hae()-metodin rajapintametodi.
     * 
     * Tarksitetaan syöte, jos validi, haetaan vastaavaa Viestiä vastausten
     * joukosta. Palautetaan Viesti, joka täyttää haun ehdot.
     * 
     * @param haettava: Viesti-olio, jota haetaan vastauksista.
     * @return Viesti: Palauttaa Viestin tai null
     * @throws IllegalArgumentException Poikkeus, jos parametri NULL tai
     * ei Viesti-tyyppiä
     */
    @Override
    public Viesti hae(Viesti haettava) throws IllegalArgumentException {
        if(haettava == null) { throw new IllegalArgumentException(""); }
        if(!(haettava instanceof Viesti)) {
            throw new IllegalArgumentException("");
        }
        return (Viesti) this.getReplys().hae(haettava);
    }

    
    /**
     * Lisää Viestille vastauksen, eli laittaa 'replys' attr. (OmaLista)
     * Viestin sille kuuluvaan paikkaan.
     * 
     * Tarkistetan ensin, onko viesti jo listalla
     * 
     * @param lisattava Viesti-olio joka lisätään vastaukseksi Viestille
     * @throws IllegalArgumentException Poikkeus annetusta parametista Viesti,
     * jos Viestiä ei ole Ketjussa tai jos (vastaus)Viesti vastaa jo Viestiin.
     */
    @Override
    public void lisaaVastaus(Viesti lisattava) throws IllegalArgumentException {
        
        // Jos Viestillä on jo vast. (palautettu hakutulos != null)
        // tai jos lisättävä on sama kuin jo olemassa oleva replyTo
        // niin heitä poikkeus
        if(this.hae(lisattava) != null || lisattava == this.getReplyTo()) {
            throw new IllegalArgumentException("");
        }
        
        // Suorita lisäys. Lisää Solmun jonka alkio=lisattava
        try {
            this.getReplys().lisaa(lisattava);
        } catch(Exception e) {}
    }

    
    /**
    * Tyhjentää Viestin sisällön ja Tiedoston.
    * Asettaa attribuuteille uudet arvot.
    * 
    */
    @Override
    public void tyhjenna() {
        this.setContent(POISTETTUTEKSTI);
        this.setAttachment(null);
    }
    
    
    
    /**
     * Korvaa luokassa Object-luokan toString-metodi. 
     * Metodi palauttaa merkkijonon, jonka alussa on ristikkomerkki, 
     * viestin tunniste ja välilyönti.
     * Alkua seuraa viestin merkkijono. 
     * Merkkijonon loppuosa koostuu välilyönnistä ja kaarisulkuparin sisään 
     * liitetystä tiedoston merkkijonoesityksestä
     *
     * @return String 
     */
    @Override
    public String toString() {
        
        if(this.getAttachment() != null) {        
            return "#" + this.getId() + " " + this.getContent()
            + " (" + this.attachment.toString() + ")";
        } else {
            return "#" + this.getId() + " " + this.getContent();
        }  
    }
    
    /**
     * Korvaa
     *  luokassa myös Object-luokan equals-metodi siten, että oliot 
     * katsotaan samoiksi, jos niiden tunnisteet samat.
     *
     * Tarkastetaan onko paramteri o Viesti-tyypin kanssa yhteensopiva,
     * jos on, tarkastetaan tunnukset (.getId())
     * 
     * @param o Objekti, jonka oletetaan olevan Viesti. Verrataan ID:tä  
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
    
        if (o == this) {
            return true;
        } else if(o instanceof Viesti) {
            // Varsinainen ID:n vertailu
            return this.getId() == ((Viesti) o).getId();
        }
        return false;
    }
    
    
    /**
     * 
     * Vertaa viestin (this) attribuutti id:n parametrina saatuun objektiin.
     * Palauttaa -1, 0 tai 1 riippuen keskenäisistä suhteista.
     * -1 jos pienempi, 1 jos suurempi
     * 
     * 1 jos this > parametrina oleva vertailtava
     * 
     * @param v (oletettu) Viesti-olio
     * @return boolean
     */
    @Override
    public int compareTo(Viesti v) {
    
        if(v == this) {
            return 0;
        } else if(v instanceof Viesti) {
            // Varsinainen vertailu jos olion tyyppi kelpaa
            if(this.getId() > v.getId()) {
                return 1;
            } else if(this.getId() == v.getId()) {
                return 0;
            } else {
                return -1;
            }
        }
        return -1;
    }
}
