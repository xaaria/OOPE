package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * @author OP
 * 
 */
public final class Video extends Tiedosto {
    
    /** pituus (sekuntia) */
    private double length;
    
    /**
     * Video-luokan rakennin. Kutsuu superluokkan Tiedosto rakenninta.
     * 
     * @param name Tiedoston nimi
     * @param sizeInBytes Koko tavuina
     * @param len pituus sekunteina
     */
    public Video(String name, int sizeInBytes, double len) throws IllegalArgumentException {
        super(name, sizeInBytes); // Tiedosto()
        this.setLength(len);
    }
    
    /**
     * Asettaa Videon pituuden. Pituuden on oltava enemmän kuin 0.0
     * 
     * @param len Videon pituus sekunteina
     */
    @Setteri
    public void setLength(double len) throws IllegalArgumentException {
        if(len <= 0.0) { throw new IllegalArgumentException(""); }
        this.length = len;
        
    }
    
    /**
     * Palauttaa Videon pituuden (sek.)
     * 
     * @return double
     */
    @Getteri
    public double getLength() {
        return this.length;
    }
    
    
    
    /**
     * "Jos tiedosto-olion tiedot ovat esimerkiksi "au.gif", 307200, 640 ja 480,
     * on metodin paluuarvo "au.gif 307200 B 640x480"." (Dokumentti)
     * 
     * @return String
     */
    @Override
    public String toString() {
        return super.toString() + " " + this.getLength() + " s";
    }
}
