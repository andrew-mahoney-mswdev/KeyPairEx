import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CodedMessage {

	public static void main(String[] args) {
		File privateKeyFile = new File("privatekey.obj");
		File publicKeyFile = new File("publickey.obj");
		
		try {
			FileInputStream privateKeyStream = new FileInputStream(privateKeyFile);	
			FileInputStream publicKeyStream = new FileInputStream(publicKeyFile);
			ObjectInputStream privateKeyInputStream = new ObjectInputStream(privateKeyStream);
			ObjectInputStream publicKeyInputStream = new ObjectInputStream(publicKeyStream);
			
			PrivateKey privateKey = (PrivateKey)privateKeyInputStream.readObject();
			PublicKey publicKey = (PublicKey)publicKeyInputStream.readObject();
			privateKeyInputStream.close();
			publicKeyInputStream.close();
					
			String secretMessage = "This is a secret message, don't tell anyone.";
			System.out.println(secretMessage);
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] encryptedMessage = cipher.doFinal(secretMessage.getBytes());
			System.out.println(new String(encryptedMessage));
			
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			String decryptedMessage = new String(cipher.doFinal(encryptedMessage));
			System.out.println(decryptedMessage);

		} catch (FileNotFoundException e) {System.out.println(e.toString());}
		catch (NoSuchAlgorithmException e) {System.out.println(e.toString());}
		catch (NoSuchPaddingException e) {System.out.println(e.toString());}
		catch (IOException e) {System.out.println(e.toString());}
		catch (ClassNotFoundException e) {System.out.println(e.toString());}
		catch (InvalidKeyException e) {System.out.println(e.toString());}
		catch (BadPaddingException e) {System.out.println(e.toString());}
		catch (IllegalBlockSizeException e) {System.out.println(e.toString());}
	}

}
