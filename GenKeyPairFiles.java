import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.*;

public class GenKeyPairFiles {

	public static void main(String[] args) {
		File privateKeyFile = new File("privatekey.obj");
		File publicKeyFile = new File("publickey.obj");
		
		try {
			FileOutputStream privateKeyOutputStream = new FileOutputStream(privateKeyFile);
			FileOutputStream publicKeyOutputStream = new FileOutputStream(publicKeyFile);
			ObjectOutputStream privateKeyStream = new ObjectOutputStream(privateKeyOutputStream);
			ObjectOutputStream publicKeyStream = new ObjectOutputStream(publicKeyOutputStream);
			
			try {
				KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
				keyPairGenerator.initialize(1024);			
				KeyPair keyPair = keyPairGenerator.generateKeyPair();
				PrivateKey privateKey = keyPair.getPrivate();
				PublicKey publicKey = keyPair.getPublic();
				
				privateKeyStream.writeObject(privateKey);
				publicKeyStream.writeObject(publicKey);
			} catch (NoSuchAlgorithmException e) {System.out.println(e.toString());}
		} catch (FileNotFoundException e) {System.out.println(e.toString());}
		catch (IOException e) {System.out.println(e.toString());}
	}
}
