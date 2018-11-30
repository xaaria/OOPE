package oope2018ht.lauta;

import fi.uta.csjola.oope.lista.Solmu;
import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.OmaLista;
import oope2018ht.viestit.Viesti;

/**
 *
 * @author OP
 */
public final class Alue {
    
    /** Kirjanpito Ketjujen juoksevasta numeroinnista */
    private int currentId;
    /** Aleeun Ketjut */
    private OmaLista threads;
    /** Aktiiviseksi valittu Ketju */
    private Ketju activeThread;    
    /** ALueen Viestien juoksevan numeroinnin kohta */
    private int currentRunningMessageId;    
    
    /* 
        activeThread voisi myös olla Kayttoliittyma-luokan puolella,
        tosin toimii se näinkin, koska Alue on käytännössä 1 ja sama koko ajan
        Logiikan muuttaminen näin loppuvaiheella ei enää kannattavaa. 
    */
    
    /** Tulostuksessa käytettävän sisennyksen oletysmitta */
    final static int INDENT_SIZE = 3;
    
    /**
     * Alue-luokan rakennin. Alustetaan oletusarvoilla.
     * 
     */
    public Alue() {
        this.setCurrentId(1);
        this.setThreads(new OmaLista());
        this.setActiveThread(null);
        this.setCurrentRunningMessageId(1);
    }
    

    /**
     * Asettaa Aluueen Ketjujen juoksevan numeroinnin kohdan.
     * Ei voi olla nolla tai pienempi.
     * 
     * @param id Viestin ID
     * @throws IllegalArgumentException Poikkeus, jos ID pienempi kuin 1
     */
    @Setteri
    public void setCurrentId(int id) {
        if(id <= 0) { throw new IllegalArgumentException(""); }
        this.currentId = id;
    }
    
    /**
     * Palauttaa Ketjujen juoksevan numeroinnin nykyisen arvon.
     * 
     * @return int
     */
    @Getteri
    public int getCurrentId() {
        return this.currentId;
    }
    
    /**
     * Asettaa Alueen Ketjut
     * 
     * @param ol OmaLista-rakenne 
     */
    @Setteri
    public void setThreads(OmaLista ol) {
        this.threads = ol;
    }
    
    /**
     * Palauttaa Alueen Ketjut
     * 
     * @return OmaLista
     */
    @Getteri
    public OmaLista getThreads() {
        return this.threads;
    }
    
    /**
     * Asettaa Alueen aktiivisen Ketjun
     * (Ei tehdä erityisempi tarkastuksia, esim. onko Ketju kys. Alueella)
     * 
     * @param k Aktiiviseksi asetettava Ketju
     */
    @Setteri
    public void setActiveThread(Ketju k) {
        this.activeThread = k;
    }
    
    /**
     * Palauttaa Alueen aktiivisen Ketjun
     * 
     * @return Ketju
     */
    @Getteri
    public Ketju getActiveThread() {
        return this.activeThread;
    }
    
    /**
     * Asettaa Ketjun juoksevan numeroinnin kohdan.
     * ID:n korottamiseen on oma metodinsa(!)
     * 
     * @param id Uuden ID: arvo
     */
    @Setteri
    public void setCurrentRunningMessageId(int id) {
        if(id<=0) { throw new IllegalArgumentException(""); }
        this.currentRunningMessageId = id;
    }
      /**
     * Viestiketjun viesteillä on oma juokseva numerointinsa.
     * Palauttaa nykyisen ID:n yhdellä, ja palauttaa sen.
     * 
     * ID voidaan antaa uudelle viestille. Mikäli näin tehdään, pitää muistaa
     * myös kasvattaa nykyistä ID:tä metodilla 'raiseCurrentRunningMessageId()'
     * 
     * @return int
     */
    public int getCurrentRunningMessageId() {
        return this.currentRunningMessageId;
    }
    
    
    /**
     * Kasvattaa Alueen juoksevaa numeroa yhdellä (viestien ID:tä).
     * 
     * @return int: palauttaa uuden (korotetun) ID:n 
     */
    public int getBoardId() {
        return this.currentId;
    }
    
    /**
     * Korottaa Alueen Ketjujen juoksevaa numerointia yhdellä.
     * Pitäisi kutsua aina kun uusi Ketju on lisätty Alueelle.
     * 
     */
    public void raiseBoardId() {
        this.currentId++;
    }
    
    
    /**
     * Korottaa ALueen Ketjujen juoksevaa numerointia yhdellä
     * Kutsutaan mm. silloin kun uusi Viesti on onnistuneesti lisätty
     * 
     */
    public void raiseCurrentRunningMessageId() {
        this.currentRunningMessageId++;
    }
    
    
    /* == Komennot == */
    
    
    /**
     * Keskustelualueelle luodaan uusi viestiketju add-komennolla. Ohjelma antaa uudelle
     *  ketjulle tunnisteen, joka on juokseva kokonaisluku. Ensimmäisen ketjun
     *   tunniste on luku yksi.
     *  Komennon parametri on merkkijono, joka kuvaa viestiketjun aihetta. Huomaa,
     *   että merkkijono on viestien tekstin tapaan yleensä lause, jossa sanat erotetaan toisistaan
     *   välilyönnein. Merkkijonossa on oltava ainakin yksi merkki. Yksi välilyönti
     *   ei kelpaa keskustelun.
     * 
     * " Alue aktivoi automaattisesti ensimmäisen siihen luodun ketjun. "
     * 
     * @param topic: Ketjun sisältö, eli aihe, eli otsikko
     * @return boolean
     */
    public boolean addThread(String topic) {
        
        try {
            // Jos vain välilyönti tai pituus muuten alle 2 
            if(topic.length() < 1 || topic.equals(' ')) {
                return false;
            }

            Ketju thread = new Ketju(this.getBoardId(), topic, null);

            // Suorita lisäys lankoihin. Palauttaa totuusarvon TRUE jos onnistui.
            if(this.getThreads().lisaa(thread)) {

                this.raiseBoardId(); // Kasvata juoksevaa numerointia

                //"Alue aktivoi automaattisesti ensimmäisen siihen luodun ketjun. "
                if(this.getThreads().koko()==1) {
                    this.setActiveThread(thread);
                }

                return true;
            } else {
                return false;
            }
            
            
        } catch(Exception e) {
            return false;
        }
    }
   
    
    
    /**
     * Suorittaa catalog-toiminnon, eli listaa aktiivisen Alueen
     * kaikki Ketjut luontijärjestyksessä (vanhin ensin).
     * 
     */
    @SuppressWarnings("unchecked")
    public void catalogThreads() {
    
        OmaLista threads_ = this.getThreads();
        
        if(threads_ == null || threads_.onkoTyhja()) {
            return;
        }
        
        Solmu s = threads_.paa();

        do {
            if(s.alkio() instanceof Ketju) {
                System.out.println("#"+((Ketju) s.alkio()).getId() + " "
                    + ((Ketju) s.alkio()).getTopic() + " "
                    + "(" + ((Ketju) s.alkio()).getReplyCount() + " "
                    + "messages)");
            }
            s = s.seuraava();
        } while(s != null);
    }
    
    
    /**
     * Tulostaa aktiivisen langan n määrää uusimpia (listan viimeisimpiä)
     * viestejä.
     * 
     * @param n Ketjujen lukumäärä
     * @return boolean: Palauttaa FALSE jos kohdistetaan virheellisesti
     * lankaan, jota ei ole. FALSE myös jos n on suurempi kuin koko tai <= 0
     * 
     *
     */
    @SuppressWarnings("unchecked")
    public boolean tailThreads(int n) {
    
        try {
            if(n<=0 || n>this.getActiveThread().getReplys().koko()) {
                return false;
            }

            OmaLista repl = this.getActiveThread().getReplys().annaLoppu(n);
            Solmu s = repl.paa();

            do {
                System.out.println(s.alkio()); // .toString()
                s = s.seuraava();
            } while(s != null);
        }
        catch(Exception e) { }
        return true;
    }
    
    
    
    /**
     * Aktiivisen ketjun kaikki viestit voidaan tulostaa myös listana, 
     * joka on järjestetty viestien tunnisteiden mukaiseen nousevaan
     * järjestykseen, jolloin ketjun vanhin
     * viesti tulostetaan ensimmäisenä ja uusin viesti viimeisenä.
     * 
     * -- List-komento tulostaa 
     * ketjun aiheen kuten tree-komento (luku 2.8)
     * ja myös viestien sisältö on sama.
     * 
     * 
     */
    public void listThreads() {

        // Tulosta Ketjun Viestit listana.
        if(this.getActiveThread() != null ) {
            System.out.println("=\n== "+ this.getActiveThread() +"\n===");
            //System.out.println( this.getActiveThread().getReplys() );
            
            try {
            
                Solmu s = this.getActiveThread().getReplys().paa();
                if(s == null) { return; }

                do {
                    System.out.println(s.alkio().toString());
                    s = s.seuraava();
                } while(s != null);
            } catch(Exception e) {}
            
        }
    }
    
    /**
     * Käyttöliittymäkomento
     * 
     * Asettaa aktiiviseksi (attr: this.activeThread) langaksi
     * langan (eli Viestin) sen ID:n perusteella.
     * 
     * Tarkastellaan, onko kyseisellä ID:llä viestiä Alueella, jos on,
     * asetetaan aktiiviseksi ja palautetaan TRUE.
     *
     * 
     * @param id: Viestin id
     * @return boolean. Onnistuiko aktiivisen viestiketun asettaminen.
     */
    public boolean selectActiveThread(int id) {
        
        if(id<=0) { return false; }
        
        // Luodaan ns. pseudo-Ketju, jotta vertailu onnistuu hae()-metodilla
        Ketju tmpThread = new Ketju(id, "..", null);
        
        // Hae. Vertaillaan Listan Viestien Id:tä ja pseudoviestin ID:tä
        // Palauttaa täsmäävän Solmun ALKION listasta.
        Ketju thread_ = (Ketju) this.getThreads().hae(tmpThread);
        
        if(thread_ != null) {
            // Suorita varsinainen asetus
            this.setActiveThread(thread_);
            return true;
        } else {
            return false;
        }
        
    }
    
    

    /**
     * 
     * Tulostaa Alueen katiivisen Ketjun Viestit puumaisena rakenteena.
     * 
     * Tässä metodissa haetaan ns. suorat vastauksen Ketjuun, sekä
     * kutsutaan näille rekursiivista printTree(Viesti) -metodia joka tulostaa
     * vastaukset [replyt] parametina annetulle viestille. 
     * 
     * 
     */
    public void printTree() {
    
        // Haetaan Alueen aloitusviestit
        Ketju k =  this.getActiveThread();
        OmaLista rep = k.getReplys();   // vastaukset
        
        // Tulosta + Kutsu vastauksien tulostavaa metodia
        System.out.println("=\n== " + k.toString() +"\n===");

        // Tulosta Ketjun vastaukset (Viestit), ja (niihin vastaavat)
        // viestit jne...
        if(rep == null || rep.paa()==null) { return; }
        
        Solmu reply = rep.paa();

        if(reply.alkio() instanceof Viesti) {

            do {
                // Onko ns. suora vastaus
                if( ((Viesti)reply.alkio()).getReplyTo() == null) {
                    // Tulosta
                    System.out.println(reply.alkio());
                    // Tulosta Viestin vastaukset (...ja niiden vastaukset)
                    this.printTree( (Viesti)reply.alkio(), 1 ); // tul. vast.
                }
                reply = reply.seuraava();
            } while(reply != null);
        }
        
    }
    
    /**
     * Viestien tulostamiseen, kutsuu ITSEÄÄN hakeakseen rekusriivisesti
     * viestit jotka vastaavat ja niiden viestit jne...
     * 
     * Sisennyksen teossa käytetään omaa metodia
     * 
     * @param m: Viesti
     * @param depth: Tulostuksen syvyys
     */
    public void printTree(Viesti m, int depth) {
        // Hae parametrina saadun viestiin liittyvät vastaukset
        OmaLista replys = m.getReplys();
        
        // Tarkista tyhjän varalta ja jos onkin tyhjä
        if(replys == null || replys.onkoTyhja()) {
            return;
        }
        
        // Hae (vastausten) OmaListan pää. s on kursori
        Solmu s = replys.paa();
        
        // Käy vastaukset yksitellen läpi
        // Jos ei tyhjäarvoa, tulosta kys. viesti ja jälleen sen sen vastaukset
        // kutsuen samaa metodia [=rekursiivisuus]
        do {
            Viesti x = (Viesti) s.alkio();
            if(x != null) {
                System.out.println( this.printIndent(depth) + x.toString());
                this.printTree(x, depth+1);
            }
            s = s.seuraava();
        } while(s != null);
    }
    
    
    /**
     * Tekee välilyöntejä aina 3 kpl syvennyksen tason mukaan
     * 0,3,6,9...
     * 
     * @param depth Tulostuksen taso/syvyys
     * @return String
     */
    private String printIndent(int depth) {
        String indent = "";
        for(int i=0; i<INDENT_SIZE*(depth); i++) {
            indent += ' ';
        }
        return indent;
    }
    
    
    
}
