package oope2018ht.tiedostot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * Tiedosto-luokkan ja Tiedostojen käsittelyyn liittyvä apuluokka.
 * 
 * Hakee mm. Tiedostoon liittyvät tiedot vastaavasta tekstitiedostosta.
 * 
 * Ei liity varsinaisesti Tiedosto-olion käsittelyyn, 
 * vaan enemmänkin datan lukemiseen Tiedosto-luokan oliolle ja Tiedosto-olion
 * luomiseen.
 * 
 * 
 * @author O
 * 
 */
public final class TiedostoHelper {
    
    /** Tiedostonimi kokonaisuudessaan. esim. 'kissa.jpg' */
    private String filename;
    
    // private String fileExtension;
    // private Tiedosto file; // Varsinainen tiedosto jota käsitellään.
    
    /*

        Manuaalinen työkansion polun määritys
        
        NetBeans '/src'
        WETO './'
        Jorman testitiedostoja ajettaessa (komentorivi) ""
    */
    private static final String WORK_FOLDER_PATH = ""; // "src/"; 
    
    /**
     * TiedostoHelperin rakentaja
     * 
     * @param fn tiedostonimi
     */
    public TiedostoHelper(String fn) {
        this.filename = fn;
    }
    
    /**
     * Asettaa tiedoston nimen
     * 
     * @param n Tiedostonimi
     */
    @Setteri
    public void setFilename(String n) {
        this.filename = n;
    }
    
    /**
     * Palauttaa tiedostonimen. Tiedostopääte sisältyy (jos sellainen annetttu).
     * 
     * @return String
     */
    @Getteri
    public String getFilename() {
        return this.filename;
    }
    
    
    /**
     * Palauttaa ajohakemiston ja tiedostonimen kokonaisuudessaan
     * 
     * @return String
     */ 
    public String getFullFilePath() {
        return WORK_FOLDER_PATH + this.getFilename();
    }
    
    
    /**
     * Tarkastaa, löytyykö tiedostonimellä vastaavaa tiedostoa
     * ajohakemistosta. (HUOM! Tarkista ajohakemiston määritykset!)
     * 
     * 
     * @return boolean
     */
    public boolean doesFileExistsInWorkFolder() {
        File f = new File( this.getFullFilePath() );
        return (f.exists() && f.isFile() && !f.isDirectory());
    }
    
    /**
     * Staattinen metodi tiedoston olemassaolon tarkastukseen.
     * Ottaa tiedostonimen parametrina.
     * 
     * @param filename Tiedoston nimi
     * @return boolean
     */
    public static boolean doesFileExistsInWorkFolder(String filename) {
        File f = new File( WORK_FOLDER_PATH+filename ); // ./kissa.jpg
        return (f.exists() && f.isFile() && !f.isDirectory());
    }
    
    /**
     * Palauttaa tiedostonimen ILMAN pisteen jälkeen tulevaa 
     * osaa ja itse pistettä
     * 
     * ESIM: kissa.jpg -> kissa 
     * Olettaen että tiedostonimessä ei ole useita pisteitä.
     * 
     * @return String
     */
    public String getFilenameWithoutExtension() {
        int i = this.getFilename().indexOf(".");
        if(i != -1) {
            return this.getFilename().substring(0, i);
        }
        return null;
    }
    

    /**
     * Avaa tiedoston hakemistosta ja parsii merkkijonotaulukkoon
     * sen tiedot. Tiedoston voidaan olettaa aina olevan kunnossa.
     * 
     * Tiedot ovat: Tiedoston tyyppi, koko, (korkeus, leveys) TAI (pituus)
     * 
     * 
     * @return String[] Taulukko, jossa Tiedoston tiedot
     * @throws java.io.FileNotFoundException Tiedostoa ei löytynyt
     * @throws Exception ...
     */
    public String[] parseAndGetFileData() throws FileNotFoundException, Exception {
   
        String row = "";
        String[] result = new String[4];
        
        if( doesFileExistsInWorkFolder( this.getFilename() )) {
        
            try {
                File f = new File( this.getFullFilePath() ); // polku+tied.nimi
                Scanner sc = new Scanner(f);
                
                // Oletetaan tiedon olevan 1. rivillä. Ei enempää rivejä
                row = sc.nextLine();
                sc.close();
                
            } catch(FileNotFoundException err) {
                throw err;
            } catch(Exception err) {
                throw err;
            }
        
            if(row != null && row.length() >= 1) {
                result = row.split(" "); // taulukkoon
            }
        }
        return result;
    }

    
    /**
     * Palauttaa Tiedosto-olion alatyypin (Kuva tai Video) riippuen
     * tekstitiedoston datasta, joka säilöö Tiedoston tiedot.
     * Tietojen oletetaan olevan aina oikeellisia.
     * 
     * @return Tiedosto: Jokin Tiedoston perivä luokka, tai null
     * @throws java.io.FileNotFoundException Poikkeus, jos tiedostoa ei löydy
     * @throws Exception ...
     */
    public Tiedosto returnTiedostoObject() 
    throws FileNotFoundException, Exception {
        
        String[] fileData = this.parseAndGetFileData();
        //System.out.println("Saatiin mediatiedostosta: "+Arrays.toString(fileData));
        
        try {
            switch(fileData[0]) {
                case "Kuva":
                    return new Kuva(
                        this.getFilename(),
                        Integer.parseInt(fileData[1]),
                        Integer.parseInt(fileData[2]),
                        Integer.parseInt(fileData[3])
                    );
                case "Video":
                    return new Video(
                        this.getFilename(),
                        Integer.parseInt(fileData[1]),
                        Double.parseDouble(fileData[2])
                    );
            }
        }
        // Jokin virhe. Tiedoston käsittely hylätään ja palautetaan NULL
        catch(Exception e) {
            return null;
        }
        return null;
    }
}
