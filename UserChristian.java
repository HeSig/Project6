
package insertpackage123;
import javax.swing.ImageIcon;
import java.io.IOException;


/*OBSERVERA klassen är halvfärdig, hade tänkt att lägga till följande funktionalitet:
  Att ImageIcon argumentet i konstruktorn endast tillåter .png eller .jpg filer. 
  Att ImageIcon skulle krympas till liten storlek för stora bilder.
  Eventuellt lägga till mer precisa Exceptions för konstruktorn.
  Därefter testa klassen.
*/
public class User{
    private String username; 
    private ImageIcon userImage;
    private boolean userOnline;
    
    /**
     * 
     * @param username stores the supplied username in String format.
     * @param userImage stores the supplied user image.
     * @throws IOException 
     */
    public User(String username, ImageIcon userImage) throws IOException{
        this.username = username;
        this.userImage = userImage;
        
    }
    
    public String getUsername(){
        return this.username;
    }  
    
    public ImageIcon getUserImage(){
        return this.userImage;
    }
    
    //Query the current userobject for online status.
    public boolean getOnlineStatus(){
        return this.userOnline;
    }  
    
    public void setAsOnline(){
        this.userOnline = true;
    }
    
    public void setAsOffline(){
        this.userOnline = false;
    }
    
}
