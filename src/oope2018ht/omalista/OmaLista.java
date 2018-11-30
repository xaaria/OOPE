package oope2018ht.omalista;
import fi.uta.csjola.oope.lista.LinkitettyLista;
import fi.uta.csjola.oope.lista.Solmu;
import oope2018ht.apulaiset.Ooperoiva;

/**
 *
 * @author OP
 * 
 * "Ohjelman pääasiallisena tietorakenteena käytetään 
 *  fi.uta.csjola.oope.listapakkauksen
 *  LinkitettyLista-luokasta perittyä OmaLista-luokkaa" 
 */
public class OmaLista extends LinkitettyLista
        implements Ooperoiva<OmaLista> {

    
    /**
     * OmaLista rakennin. 
     * Kutsuu superluokan LikitettyLista rakenninta.
     */
    public OmaLista() {
        super();
    }
  
    
    /**
     * palauttaa 1. Solmun listasta - eli pään
     * 
     * @return Solmu
     */
    @Override
    public Solmu paa() {
        return super.paa();
    }
    

      
    
    /**
     * Tulostaa omanlistan sisällön käyttäen kunkin alkion .toString():iä.
     * Peittää  toString() -metodin kantaluokastaan
     * 
     * @return String
     */
    @Override
    public String toString() {
        String output = "";
        try {
            Solmu s = this.paa();
            if(s==null) { return ""; }
            
            do {
                output += s.alkio().toString()+"\n";
                s = s.seuraava();
            } while(s != null);
            
        } catch(Exception e) {}
        return output;
    }
    
        
    /**
     * Käydään läpi linkitettyä listaa, kunnes a.haettava(b) täsmäävät
     * määritellyllä tavalla [ a.equals(b) ]
     * 
     * @param haettava Olio, jota haetaan OmaltaListalta
     * @return Object: palauttaa täsmäävän Solmun listasta.
     */
    @Override
    public Object hae(Object haettava) {
        
        // Alkutarkastus. Jos omalista on tyhjä, turha edes hakea
        if(this.onkoTyhja()) { return null; }
        
        Solmu node = this.paa();
        
        // Tutkaile samankaltaisuutta .equals(n), ja jos täsmää, palauta 
        // kyseinen solmu, jos ei. Mene seur. jos ei seuraavaa, palauta null.
        // Olion luokasta riippuen, equals() on voitu ylikirjoittaa!
        // Esim. Viesti-luokassa tutkaillaan viestien ID:eitä
        do {
            
            if(node.alkio().equals(haettava)) {
                return node.alkio();
            }
            node = node.seuraava(); // vertailtavaksi nodeksi seuraava
        } while(node != null); // Ei osumia, listan arvot päättyivät null-arvoon
        
        return null; 
    }

    
    /**
    * "-- uusi alkion lisätään listalle siten,
    * että alkio sijoittuu kaikkien itseään pienempien tai yhtä suurien alk.
    * j�lkeen ja ennen kaikkia itseään suurempia alkioita. --"
    * 
    * 6 --> [1,2,3,4,5,5,5,*,7]
    * 
    * @param uusi Lisättävä Objekti
    * @return boolean
    */
    @Override
    @SuppressWarnings({"unchecked"})
    public boolean lisaa(Object uusi) {
        
        // Tarkasta parametri.
        // Jos OmaLista on tyhjä, lisää param. uusi alkuun ja palauta true
        if(uusi == null) {
            return false;
        } else if(this.koko()==0) {
            this.lisaaAlkuun(uusi);
            return true;
        }
        if( !(uusi instanceof Comparable) ) {
            return false;
        }
        
        // Jos ei omituisuuksia alun jälkeen, aletaan käymään läpi
        // ja etsitään asianmukainen lisäyskohta
        
        // n eli 'nykyinen' node. Aloitetaan 1. Solmusta.
        Solmu n = this.paa();
        
        // Tarkastetaan erikoistilanne, jossa objekti on kaikkia pienempi,
        // eli lisätään heti alkuun
        // melkoista purkkakoodia!
        Solmu uusiSolmu = new Solmu(uusi);
        Comparable newNodeComp = (Comparable) uusiSolmu.alkio(); 
        
        // vertaa nykyisenä 1. olevaan
        // HUOM! pitää vertailla < 0, ei vain ==-1, koska String-luokan
        // compareTo() palauttaa ERON kirjainten välillä (a,z = 25)
        if(newNodeComp.compareTo(n.alkio()) < 0) {
            //System.out.println("Lisätään omanListan 0. indeksiin!");
            this.lisaaAlkuun(uusiSolmu.alkio()); // param: alkio, ei Solmu
            return true;
        }
        
        do {            
            // boolean addHere() totetuttaa tarkastelun, lisätäänkö Solmu
            if(this.addHere(n, uusi)) {
                
                // n nykyinen node (1), next (3) seuraava node
                // uusi 'newNode' (2) laitetaan näiden kahden väliin
                Solmu next = n.seuraava();       
                
                // Tee Solmu, jonka sisältö on 'uusi' ja päivitä
                Solmu newNode = new Solmu(uusi); // alkio=uusi
                n.seuraava(newNode);        // 1 --> 2 
                newNode.seuraava(next);     // 2 --> 3
                
                this.koko++;
                return true; // lisäys onnistui
                
            } else {
                n = n.seuraava(); // vertailtavaksi nodeksi seuraava
            }
            
        } while(n != null);
        return false; 
    }

    
    /**
    * Tarkistaa Solmujen arvoista, lisätäänkö UUSI solmu tähän kohtaan,
    * eli solmun S jälkeen ja X:ää ennen.
    * 
    * Skenaarioita on esimerkiksi:
    * 
    * (E1),2,2,(A2),3,3,(B4),5,6,6,6,(C6),(D7),[null]
    * 
    * A) sama luku, seuraava on isompi [0 ja -1]
    * B) Kahden solmun väliin [1, -1]
    * C) Sama luku, listan loppu [0 ja null]
    * D) suurempi kuin ed., listan loppu [1, null]
    * E) Alkuun [null, -1]
    * 
    * 
    * @param s: S on tässäkin 'nykyinen' Solmu
    * @param uusi: uusi on näiden väliin (mahdollisesti) tulevan Solmun alkio
    * @return boolean: True, jos lisäys on syytä tehdä tähän väliin
    */
    @SuppressWarnings({"unchecked"})
    private boolean addHere(Solmu s, Object uusi) {
        
        // Jos seuraava ei ole, eli ollaan listan lopussa,
        // niin voidaan lisätä tähän kohtaan. Muutoin jatketaan vertialuja.
        if(s.seuraava() == null) {
            return true;
        }
        // Luodaan vertailu-oliot
        Solmu x = s.seuraava();
        Solmu newNode = new Solmu(uusi);
        Comparable cnn = (Comparable) newNode.alkio(); 
        
        // Kahden Solmun väliin lisättävän vertailut listan vas. ja oik. puolen
        int comparsion_prev = cnn.compareTo(s.alkio());
        int comparsion_next = cnn.compareTo(x.alkio());
        
        // Suoritetaan vertailu. Tuleeko 'uusi' S:stä seuraavaksi listalle...
        return (comparsion_prev==0 && comparsion_next==-1)
            || (comparsion_prev==1 && comparsion_next==-1);
        
    }
    
    
    /**
     * Palauttaa OmaListan alkuosan N ensimmäistä Solmua OmaLista-rakenteessa.
     * Palauttaa NULL jos parametri n alle 1, suurempi kuin koko tai OmaLista
     * on tyhjä.
     * 
     * @param n Haluttujen Solmujen määrä 
     * @return OmaLista
     */
    @Override
    public OmaLista annaAlku(int n) {
        
        if(this.onkoTyhja() || n > this.koko() || n <= 0) {
            return null;
        }
        OmaLista ol = new OmaLista(); // palautettava
        
        // Aseta uuden omalistan 1. alkioksi nykyisen omalistan 1. alkio
        // s tarkoittaa nykyistä nodea.
        ol.lisaaAlkuun( this.paa().alkio() );
        
        // ns. Iteraattorit molemmille listoille
        Solmu s = ol.paa();     // omalista, eli tämä uusi lista
        Solmu o = this.paa();   // ns. vanha lista jota liikutaan et.päin.
        
        // Haetaan loputkin uuteen OmaListaan (ol).
        for(int i=1; i<=n-1; i++) {

            // Luo uusi solmu X joka tallennetaan uuteen omaanlistaan
            // X on siis listan seuraava solmu.
            // X:n alkio on vanhan listan 'nykyisen solmun' seruaavan alkio.
            // Alustetaan siis o.seur().alkio()
            
            Solmu x = new Solmu(o.seuraava().alkio());
            
            // vanhalla listalla yksi solmu eteenpäin
            o = o.seuraava();
            
            // Seuraava solmu, eli X on luotu, mutta nykyinen solmu S ei osaa
            // vielä osoittaa siihen (s.seuraava=null).
            // S:n seuraavaksi asetetaan juur luotu solmu X
            s.seuraava(x);
            // Siirryy (s:n) seuraavaan, eli x:ään joka juuri lisättiin loppuun
            // käytännössä siis s = x
            s = s.seuraava();
        }
        ol.koko = n;
        return ol;   
    }

    
    /**
     * Haetaan loppuosa linkitetystä listasta. 
     * Käytetään hyväksi aiemmin toteutettuja metodia annaAlku().
     * 
     * Koska haeSolmu(int) on private, eikä sitä voida muuttaa, pitää
     * iteroida manuaaliesti halutun solmun kohdalle
     * 
     * Käytännössä mennään N:teen alkioon, ja haetaan koko loppuosa
     * 
     * @param n haettavien Solmujen määrä lopusta (n kpl)
     * @return OmaLista
     */
    @Override
    public OmaLista annaLoppu(int n) {
        
        if(this.koko() < n || n <= 0) { return null; }
        
        Solmu o = this.paa(); // 'vanhan' (o=old) linkitetyn listan iteraattori
        OmaLista ol = new OmaLista(); // Alustetaan uusi omalista (ol)
        
        // Siirrytään N:nteen solmuun listalla.
        for(int i=1; i<=this.koko()-n; i++) {
            o = o.seuraava();
        }
        // O:n pitäisi olla linkitetyn listan halutun loppuosan 1. solmu...
        // ...laita ol:n alkuun Solmu o:n alkiolla
        ol.lisaaAlkuun(o.alkio());
        
        Solmu s = ol.paa(); // S on uuden listan solmujen iteraattori
        
        // Luodaan ol:ään lisää solmuja ja kopioidaan viitteet
        while(o.seuraava() != null) {
            // uuden solmun X sisältö (alkio) on nykyisestä seur. alkio
            Solmu x = new Solmu(o.seuraava().alkio());
                    
            // nykyisen solmun S seuraava on uusi solmu X
            s.seuraava(x);
            
            // Siirrytään seuraaviin Solmuihin
            s = s.seuraava(); // eli s = x
            o = o.seuraava(); // jos ei seur. (eli null) poistu silmukasta
        }
        
        // Palauta lopuski juuri luotu linkitetty lista ol
        ol.koko = n;
        return ol;
    }
    
}
