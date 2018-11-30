package oope2018ht.tiedostot;
import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * Abstrakti Tiedosto-luokka, jonka perivät mm. Video ja Kuva -luokat.
 * 
 * @author OP
 */
public abstract class Tiedosto {
       
    /** Tiedoston nimi */
    private String name;
    /** Tiedoston koko (B) */
    private int sizeInBytes;
    
    
    /**
     * Tiedosto-luokan rakennin
     * 
     * @param name Tiedoston nimi
     * @param sizeInBytes Tiedoston koko tavuina
     * @throws IllegalArgumentException Heittää poikkeuksen jos jokin setteri
     * saa epäkelvon parametrin.
     */
    public Tiedosto(String name, int sizeInBytes) throws IllegalArgumentException {
        this.setName(name);
        this.setSizeInBytes(sizeInBytes);
    }
    
  
    /***
     * Asettaa Tiedoston nimen. Ei voi olla alle 1 merkin mittainen
     *
     * @param n Tiedoston nimi
     * @throws IllegalArgumentException Nimen oltava pidempi kuin 0 merkkiä
     */
    @Setteri
    public void setName(String n) throws IllegalArgumentException {
        if(n==null || n.length()<=0) {
            throw new IllegalArgumentException("");
        }
        this.name = n;
    }
    
    /**
     * Palauttaa Tiedoston nimen
     * 
     * @return String
     */
    @Getteri
    public String getName() {
        return this.name;
    }
    
    /**
    * Asettaa Tiedoston koon. Oltava suurempi kuin 0
    * 
    * @param sib Tiedoston koko tavuina.
    */
    @Setteri
    public void setSizeInBytes(int sib) {
        if(sib <= 0) { throw new IllegalArgumentException(""); }
        this.sizeInBytes = sib;
    }
    
    /**
     * Palauttaa Tiedoston koon (tavua)
     * 
     * @return int
     */
    @Getteri
    public int getSizeInBytes() {
        return this.sizeInBytes;
    }
    
    /**
     * Palauttaa ajoympäristöön sopivan polun nykyiseen ajohakemistoon.
     * 
     * esim. WETO saattaa tulkita polun eri tavalla. Vaihda tämän metodin
     * palautusarvoa ongelmatilanteissa.
     * 
     * @return String
     */
    public String getCurrentFolderFilePath() {
        return "/"+this.getName();
    }
    
    /**
     * Palauttaa Tiedostolle yheiset ominaisuuet merkkijonona
     * 
     * @return String
     */
    @Override
    public String toString() {
        return this.getName() + " " + this.getSizeInBytes() + " B";
    }
    
   
    
}
