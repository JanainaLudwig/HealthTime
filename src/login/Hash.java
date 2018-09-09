package login;
  
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
  
public class Hash {
    
    public static String sha256(String string) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algorithm.digest(string.getBytes("UTF-8"));
        
        StringBuilder hexString = new StringBuilder();

        for (byte b : messageDigest) {
          hexString.append(String.format("%02X", 0xFF & b));
        }

        String stringHex = hexString.toString();

        return stringHex;
    }

}