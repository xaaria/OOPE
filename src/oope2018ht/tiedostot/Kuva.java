package oope2018ht.tiedostot;
import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * @author OP
 */
public final class Kuva extends Tiedosto {

    /** Kuvan leveys (px) */ 
    private int width = 0; 
    /** Kuvan korkeus (px) */
    private int height = 0; 
   
    /**
     * Rakennin. Kutsuu superluokan Tiedosto rakenninta.
     * Asettaa Kuvan korkeuden ja leveyden.
     * 
     * @param name Tiedoston nimi
     * @param sizeInBytes Koko tavuina
     * @param w Leveys
     * @param h Korkeus
     */
    public Kuva(String name, int sizeInBytes, int w, int h) {
        super(name, sizeInBytes); // Tiedosto
        this.setWidth(w);
        this.setHeight(h);
    }
    
    /**
     * Asettaa Kuvan leveyden. Pitää olla suurempi kuin 0.
     * 
     * @param w Leveys (px)
     * @throws IllegalArgumentException Leveyden oltava suurempi kuin 0
     */
    @Setteri
    public void setWidth(int w) throws IllegalArgumentException {
        if(w <= 0) { throw new IllegalArgumentException(""); }
        this.width = w;
    }
    /**
     * Palauttaa Kuvan leveyden
     * 
     * @return int
     */
    @Getteri
    public int getWidth() {
        return this.width;
    }
    
    /**
     * Asettaa Kuvan korkeuden. Oltava enemmän kuin 0
     * 
     * @param h Korkeus (px)
     * @throws IllegalArgumentException Korkeuden oltava suurempi kuin 0
     */
    @Setteri
    public void setHeight(int h) throws IllegalArgumentException {
        if(h <= 0) { throw new IllegalArgumentException(""); }
        this.height = h;
    }
    
    /**
     * Palauttaa kuvan korkeuden.
     * 
     * @return int
     */
    @Getteri
    public int getHeight() {
        return this.height;
    }
    
      
    /**
     * "Jos tiedosto-olion tiedot ovat esimerkiksi "au.gif", 307200, 640 ja 480,
     * on metodin paluuarvo "au.gif 307200 B 640x480"." (ks. Tehtävänanto)
     * 
     * @return String
     */
    @Override
    public String toString() {
        return super.toString() +" "+ this.getWidth() +"x"+this.getHeight();
    }
}
