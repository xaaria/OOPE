package oope2018ht.lauta;

import java.io.FileNotFoundException;
import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.In;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;
import oope2018ht.tiedostot.TiedostoHelper;
import oope2018ht.viestit.Viesti;

/**
 *
 * @author OP
 */
public class Kayttoliittyma {
    
    
    /** Aktiivinen keskusteluAlue */
    private Alue activeBoard;
    
    /** Sallitut käyttäjän primaarikomennot */
    private static final String[] COMMANDS = {
        "add",
        "catalog",
        "select",
        "new",        
        "reply",
        "tree",
        "list",
        "head",
        "tail",
        "empty",
        "find",
        "exit"
    };
    
    /** Tervehdysviesti */
    private static final String HELLO_MESSAGE = "Welcome to S.O.B.";
    /** Poistumisviesti */
    private static final String QUIT_MESSAGE = "Bye! See you soon.";
    /** Virheviesti */
    private static final String ERROR_MESSAGE = "Error!";
    
    /**
     * Kayttoliittyma-luokan rakennin.
     * 
     * @param ab . Alue, joka asetetaan aktiiviseksi käyttöliittymälle
     */
    public Kayttoliittyma(Alue ab) {
        this.activeBoard = ab;
    }
    
    /**
     * Asettaa Käyttöliittymän aktiivisen Alueen (eli board:in)
     * 
     * @param b Alue
     */
    @Setteri
    public void setActiveBoard(Alue b) {
        this.activeBoard = b;
    }
    
    /**
     * Palauttaa Käyttöliittymän aktiivisena olevan Alueen
     * (Alue tyyppinen-olio).
     * 
     * @return Alue
     */
    @Getteri
    public Alue getActiveBoard() {
        return this.activeBoard;
    }
    
    /**
     * OSB:n käyttöliittyvämän suorittava metodi. 
     * 
     * @throws java.lang.Exception Poikkeus jos jokin menee pieleen
     */
    public void runSOB() throws Exception {
    
        String command = null;
        String initialCommand = null;
        boolean running = true;
        
        System.out.println(HELLO_MESSAGE);
        
        // Lue komentoja niin kauan kunnes lopetuskomento saadaan.
        do {
        
            System.out.print(">");
            
            // Lue käyttään ensisijainen syöte/komento
            command = In.readString(); 
            initialCommand = command.trim().split(" ")[0];
            boolean commandSuccessfull = true; // tuliko virheitä 
            
            // Tarkista
            if(isInitialCommandAllowed(initialCommand)) {
                
                // Ohjaa oikeaan toimintoon
                switch(initialCommand) {
                    case "add":
                        commandSuccessfull = this.addTopic(command);
                        break;
                    case "catalog":
                        commandSuccessfull = this.performCatalog();
                        break;
                    case "select":
                        commandSuccessfull = this.performSelect(command);
                        break;
                    case "new":
                        commandSuccessfull = this.performAddNewMessageToThread(command);
                        break;
                    case "reply":
                        commandSuccessfull = this.performReply(command);
                        break;
                    case "tree":
                        commandSuccessfull = this.performTree();
                        break;
                    case "list":
                        commandSuccessfull = this.performList();
                        break;
                    case "head":
                        commandSuccessfull = this.performHead(command);
                        break;
                    case "tail":
                        commandSuccessfull = this.performTail(command);
                        break;
                    case "empty":
                        commandSuccessfull = this.performEmpty(command);
                        break;
                    case "find":
                        commandSuccessfull = this.performFindAndOutputResults(command);
                        break;
                    case "exit":
                        running = false;
                        System.out.println(QUIT_MESSAGE);
                        break;
                    default:
                        // ...
                }
                
                // Jos virheitä [eli palautui FALSE], anna virhe
                if(!commandSuccessfull) {
                    System.out.println(ERROR_MESSAGE);
                }
                
            } else {
                System.out.println(ERROR_MESSAGE);
                // jatkuu ...
            }
            
            
        } while(running == true);
        // *** END ***
    }
    
    
    /*  Varsinaiset komentojen suoritukset 
        
        - Parametreiksi koko käytäjänn antama komento 
        - Palauttavat totuusarvon komennon kokonaissuorituksesta.
            -- TRUE jos komento suorittui ilman virheitä, eikä ole tarvetta 
            antaa >"Error!" -viestiä.
            FALSE jos jokin meni pieleen.
        
            HUOM: Paluuarvo voi olla TRUE, vaikka komento ei varsinaisesti 
            tekiskään mitään (esim. haku, joka ei löydä mitään).
        
    */
    
    
    
    /**
     * Lisää Ketjun aktiiviselle Aluueelle.
     * 
     * Ketjun viestin on oltava ainakin 1 merkin pitkä.
     * Pelkkä välilyönti ei kelpaa. (Setteri heittää poikkeuksen)
     * 
     * @param command Käyttäjän koko komento
     * @return boolean
     */
    private boolean addTopic(String command) {
    
        String[] content = command.split(" ", 2);
        if(content.length < 1) { return false; }
            
        try {
            // Yritetään lisätä aktiiviselle Alueelle uusi ketju.
            return this.getActiveBoard().addThread(content[1]); // .trim()
        } catch(Exception e) {
            return false; 
        }
    } 
    
    /**
     * Suorittaa catalog -toiminnon. ks. Alue-luokka
     * 
     * @return boolean: Totuusarvo TRUE, jos komento onnistuis
     * 
     */
    private boolean performCatalog() {
        if(this.getActiveBoard() != null) {
            this.getActiveBoard().catalogThreads();
            return true;
        }
        return false;
    }
    
    /**
     * Suorittaa viestiketjun aktivoimisen
     * 
     * @param command Annettu komento
     * @return boolean
     */
    private boolean performSelect(String command) {
        
        String[] content = command.split(" ");
        try {
            // pituus on oltava 2 "select <luku>"
            //ks. Jorman testit "select 1 2 3" (/esimerkit/board/)
            if(content.length == 2 && this.getActiveBoard() != null) {
                return this.getActiveBoard().selectActiveThread(
                    Integer.parseInt(content[1])
                ); // Palauta tieto suorittumisesta
            }
            
        } catch(Exception e) {
            return false;
        }
        
        return false;   
    }
    
    /**
     * Viestiketjuun uuden viestin asettaminen. Viesti ei vastaa aiempaan
     * viestiin
     * 
     * Komento 'new'
     * 
     * @param command Käyttäjän komentosyöte kokonaisuudessaan
     * @return boolean
     * @throws Exception ...
     */
    private boolean performAddNewMessageToThread(String command) throws Exception {
    
        String[] content = command.split(" ", 2);
        String filename;
        Tiedosto attachment = null;
        
        if(content.length <= 1) { // || content[1] == null) {
            return false;
        }
        
        // Tutkaile onko tiedostoparametria annettu
        filename = getFileNameParameter(content[1]);
        if(filename != null) {
            attachment = new TiedostoHelper(filename).returnTiedostoObject();
            // Karsi tiedostonimi pois itse Viesin sisällöstä
            content[1]=this.removeFileParamFromMessage(content[1]);
        }
        
        try {
            this.getActiveBoard().getActiveThread().getReplys();
        } catch(NullPointerException e) { return false; }
            
        Viesti m = new Viesti(
            this.getActiveBoard().getCurrentRunningMessageId(),
            content[1],
            null, // uusi viesti ei vastaa mihinkään
            attachment
        );

        //System.out.println("Lisätään suora vastaus ketjuun...");
        if(this.getActiveBoard().getActiveThread().getReplys().lisaa(m)) {
            // Jos onnistui, kasvata ID:tä yhdellä
            this.getActiveBoard().raiseCurrentRunningMessageId();
            return true;
        }
        return false;
    }
    
    /**
     * Suorittaa aktiivisen Ketjun tulostamisen puumaisessa muodossa.
     * Palauttaa aina tosi (komento onnistui), vaikka ei voitaisikaan
     * tulostaa mitään.
     * 
     * @return boolean
     */
    private boolean performTree() {
        try {
            this.getActiveBoard().printTree();
        } catch(Exception e) {}
        return true;
    }
    
    /**
     * Suorittaa Alueen ketjujen listaamisen. Varhaisin ensin
     * Jos tyhjä, ei tulosta mitään. Komento onnistuu aina
     * 
     * "Komento ei tulosta mitään, jos alueella ei ole vielä ketjuja"
     * 
     * @return boolean
     */
    private boolean performList() {
        try { this.getActiveBoard().listThreads(); } catch(Exception e) {}
        return true; // onnistuu aina, vaikkei tulostaisikaan mitään
    }
    
    /**
     * Suorittaa head-komennon, eli tulostaa X määrän viestiketjun
     * Viesteistä, alkean sen vanhimmista Viesteistä.
     * 
     * @param command Käyttäjän komento kokonaisuudessaan
     * @return boolean
     */
    private boolean performHead(String command) {
       
        String[] cmd = command.split(" ", 2);
        
        if(cmd.length <= 1 || this.getActiveBoard() == null) { return false; }
        
        int n;
        try {
            n = Integer.parseInt(cmd[1]);
        } catch(Exception e) {
            return false;
        }
        
        /* Tyhjä ketju, n<=0 tai n suurempi kuin vastauksia: virhe */
        if(
            n <= 0
            || this.getActiveBoard().getActiveThread().getReplys().onkoTyhja() 
            || this.getActiveBoard().getActiveThread().getReplys().koko() < n) {
            return false;
        }
        
        System.out.print(this.getActiveBoard().getActiveThread().getReplys().annaAlku(n));
        return true;
    }
    
    /**
     * Suorittaa tail-komennon, eli listaa viimeisimpiä Ketjuja n kpl.
     * Ketjujen tulokstuksessa käytetään OmaListan toString()-metodia apuna.
     * 
     * @param command Käyttäjän komento kokonaisuudessaan
     * @return boolean
     */
    private boolean performTail(String command) {
    
        int n;
        String[] cmd = command.split(" ", 2);
        
        // Määrän lukeminen
        try {
            n = Integer.parseInt(cmd[1]);
        } catch(Exception e) {
            return false;
        }
        
        // "Komennon kohdistaminen tyhjään viestiketjuun on virhe."
        // Tai jos suurempi kuin viestin määrä tai pienempi kuin 1
        if( n<=0 
            || this.getActiveBoard().getActiveThread() == null 
            || this.getActiveBoard().getActiveThread().getReplys().onkoTyhja()
            || this.getActiveBoard().getActiveThread().getReplys().koko() < n) {
            return false;
        }

        
        try {
            System.out.print(this.getActiveBoard().getActiveThread().getReplys().annaLoppu(n));
        } catch(Exception e) { return false; }
        return true;
    }
    
    
    
    /**
     * Tyhjentää Viestin sisällön, jolla sama tunniste aktiivisessa Ketjussa.
     * 
     * Luodaan ns. psudo-Viesti jolla etsittävän viestin ID.
     * Jos haku tuottaa tulosta, tyhjennetään palautettu varsinainen Viesti.
     * 
     * @param command Käyttäjän komento kokonaisuudessaan
     * @return boolean
     */
    private boolean performEmpty(String command) {
        
        String[] cmd = command.split(" ", 2); // viestin id
        
        int id;
        try {
            id = Integer.parseInt(cmd[1]);
        } catch(NumberFormatException e) {
            return false;
        }
        
        // pseudo-objekti
        Viesti pm = new Viesti( id, "...", null,null);
        Viesti match; // hakutulos (Viesti)
        
        try {
            match = (Viesti) this.getActiveBoard().getActiveThread().getReplys().hae(pm);
        } catch(NullPointerException e) {
            return false;
        } catch(Exception e) {
            return false;
        }
        
        // Suorita tyhjennys 'hakutulokselle'
        if(match != null) {
            match.tyhjenna();
        }
        
        return true;
    }
        
    /**
     * Suorittaa haun aktiiviseen viestiketjuun.
     * Tulostaa hakutulokset (Viestit) tunnisteen mukaisessa järjestyksessä.
     * 
     * @param command Käyttäjän komento kokonaisuudessaan
     * @return boolean
     */
    private boolean performFindAndOutputResults(String command) {

        OmaLista results;
        String[] params = command.split(" ", 2); // hakusana [1]
        
        try {
            results = this.getActiveBoard().getActiveThread().searchFromThreadReplys(params[1]);
        } catch(Exception e) {
            return true;
            // poistutaan, mutta ei palauteta false. Komento onnistuu vaikka
            // haku ei itsessään tuottaisi tulosta
        }
        
        // Tulostus
        if(!results.onkoTyhja()) {
            System.out.print( results.toString() ); // omaLista.toString
        }
        
        return true;
    }
    
    
    
    /**
     * Luo Viestin, joka vastaa parametrinaan saamaan viestiin (id)
     * 
     * - Tarkasta, onko ID:llä viestiä
     * - Suorita lisäys, ei lisätä jos on jo listalla ks. [lisaaVastaus()]
     * 
     * @param command Käyttäjän komento kokonaisuudessaan
     * @return boolean
     * @throws Java.io.Exception Poikkeus jos jokin menee vikaan
     */
    private boolean performReply(String command) throws Exception {
        
        String[] cmd = command.split(" ", 3); // viestin id
        
        // Saatinko varmasti ainakin 3 parametria?
        if(cmd.length < 3) { return false; }
        
        int id;
        try {
            id = Integer.parseInt(cmd[1]);
        } catch(NumberFormatException e) {
            return false;
        }
        
        if(cmd[2] == null || cmd[2].length() == 0) {
            return false;
        }
        
        // Tutkaile onko tiedostoparametria annettu
        String filename;
        Tiedosto attachment = null; 
        filename = getFileNameParameter(cmd[2]);
        if(filename != null) {
            //TiedostoHelper th = new TiedostoHelper(filename);
            attachment = new TiedostoHelper(filename).returnTiedostoObject();
            
            /* Jos tiedosto-parametri on annettu viestissä, mutta sitä ei ole
           (tai ei voida avata). Annetaan virhe! (ks. Jorman testi: 'sniper') */
            if(attachment==null) {
                return false; // Virhe
            }
            // Karsi tiedostonimi pois itse Viesin sisällöstä
            cmd[2] = this.removeFileParamFromMessage(cmd[2]);
        }
          
        // pseudoviesti jota haetaan ID:llä
        Viesti vast = new Viesti(id, "xxx" ,null, null);
        
        if(this.getActiveBoard().getActiveThread() != null) {
            
            Viesti m = new Viesti(
                this.getActiveBoard().getCurrentRunningMessageId(),
                cmd[2],
                vast,
                attachment     
            );
            
            Viesti v;
            try {
                v = (Viesti) this.getActiveBoard().getActiveThread().getReplys().hae(vast);
            } catch(Exception e) {
                return false;
            }
            if(v == null) {
                return false;
            }
            
            if(this.getActiveBoard().getActiveThread().getReplys().lisaa(m)) {
                // Lisäys onnistui: kasvata juoksevaa ID:tä
                this.getActiveBoard().raiseCurrentRunningMessageId();
            }
            try {
                v.lisaaVastaus(m); // m vastaa viestiin v
            } catch(Exception e) {
                return false;
                // throw new
            }
            return true;
            
        }
        return false;
    }
    
    
    /**
     * Palauttaa komennon lopussa olevan mahdollisen tiedostonimen,
     * joka on kirjoitettu & -merkin jälkeen. Sitä ennen on välilyönti!
     * 
     * @param command Annettu komento kokonaisuudessaan
     * @return String: Tyhjä, jos ei tiedostonimeä annettus
     */
    private String getFileNameParameter(String command) {
        if(command.lastIndexOf(" &") != -1) {
            return command.substring(command.lastIndexOf(" &") + 2);
        }
        return null;
    }
    
    /**
     * Poistaa Viestin sisältöosan Tiedosto-parametrin. Apumetodi.
     * Syytä kutsua vasta kun tiedostoparametrin data on luettu
     * 
     * Esim, 'kissakuva &kissa.gif' muotoon 'kissakuva'
     * 
     * @param message Viestin sisältö
     * @return String
     */
    private String removeFileParamFromMessage(String message) {
        return message.substring(0, message.indexOf(" &"));
    }
    
    
    
    /**
     * Tarkastaa käyttäjän primaarisen komennon oikeuden, eli 1. parametrin
     * 
     * @param cmd Käyttäjän komennon 1. sana, eli ns. "pääkomento"
     * @return boolean
     */
    private boolean isInitialCommandAllowed(String cmd) {
         for(String s: this.COMMANDS) {
            if(cmd.equals(s)) {
                return true;
            }
        }
        return false;
        
    }
    
    
}
