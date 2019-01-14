import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CreateDigitalSig {
	
	static String fileName = "Group chat protocol.docx";

	public static void main(String[] args) {
		try {
			//Read file into byte array
			Path myFile = Paths.get(fileName);
			byte[] fileBytes = Files.readAllBytes(myFile);
			
			//Create hash of the file
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] hash = md.digest(fileBytes);
			System.out.println("Hash of file generated.");
			
			//Load my private key
			File privateKeyFile = new File("privatekey.obj");
			FileInputStream privateKeyStream = new FileInputStream(privateKeyFile);
			ObjectInputStream privateKeyInputStream = new ObjectInputStream(privateKeyStream);
			PrivateKey privateKey = (PrivateKey)privateKeyInputStream.readObject();
			privateKeyInputStream.close();
			
			//Encrypt the hash
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] encryptedHash = cipher.doFinal(hash);
			System.out.println("Hash encrypted with private key.");

			//Output the encrypted hash to a file
			File encryptedHashFile = new File("encryptedHash.bin");
			FileOutputStream outputStream = new FileOutputStream(encryptedHashFile);
			outputStream.write(encryptedHash);
			outputStream.close();
			System.out.println("Encrypted hash saved to file.");

		} catch (NoSuchAlgorithmException e) {System.out.println(e.toString());}
		catch (IOException e) {System.out.println(e.toString());}
		catch (ClassNotFoundException e) {System.out.println(e.toString());}
		catch (NoSuchPaddingException e) {System.out.println(e.toString());}
		catch (InvalidKeyException e) {System.out.println(e.toString());}
		catch (BadPaddingException e) {System.out.println(e.toString());}
		catch (IllegalBlockSizeException e) {System.out.println(e.toString());}
	}

}
