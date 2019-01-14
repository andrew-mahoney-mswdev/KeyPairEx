import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class VerifyDigitalSig {

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
			
			//Load the public key
			File publicKeyFile = new File("publickey.obj");
			FileInputStream publicKeyStream = new FileInputStream(publicKeyFile);
			ObjectInputStream publicKeyInputStream = new ObjectInputStream(publicKeyStream);
			PublicKey publicKey = (PublicKey)publicKeyInputStream.readObject();
			publicKeyInputStream.close();
			
			//Load the encrypted hash
			File encryptedHashFile = new File("encryptedHash.bin");
			FileInputStream encryptedHashStream = new FileInputStream(encryptedHashFile);
			byte[] encryptedHash = new byte[256];
			encryptedHashStream.read(encryptedHash);
			encryptedHashStream.close();
			System.out.println("Encrypted hash loaded from file.");
						
			//Decrypt the encrypted hash
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] decryptedHash = cipher.doFinal(encryptedHash);
			System.out.println("Hash decrypted with public key.");
			
			if (Arrays.equals(hash, decryptedHash)) {
				System.out.println("Congrats. The decrypted hash matches the file hash!");
			} else {
				System.out.println("Sorry, it didn't match. :(");
			}
			
		} catch (IOException e) {System.out.println(e.toString());}
		catch (NoSuchAlgorithmException e) {System.out.println(e.toString());}
		catch (ClassNotFoundException e) {System.out.println(e.toString());}
		catch (NoSuchPaddingException e) {System.out.println(e.toString());}
		catch (InvalidKeyException e) {System.out.println(e.toString());}
		catch (BadPaddingException e) {System.out.println(e.toString());}
		catch (IllegalBlockSizeException e) {System.out.println(e.toString());}
	}

}
