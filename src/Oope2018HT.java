import oope2018ht.lauta.Alue;
import oope2018ht.lauta.Kayttoliittyma;


/**
 *
 * @author OP
 * 
 * Project created 2018-03-14
 * 
 */
public class Oope2018HT {
   
    public static void main(String[] args) throws Exception {
        
        Alue b = new Alue(); 
        Kayttoliittyma sob = new Kayttoliittyma(b);
        sob.runSOB();

    }
    
}
