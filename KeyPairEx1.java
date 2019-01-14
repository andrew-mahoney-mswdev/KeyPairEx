import java.security.*;

public class KeyPairEx1 {

	static int numBits = 1024;
	
	public static void main(String[] args) {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(numBits);
			
			long time, startTime = System.currentTimeMillis();
			for (int count = 1; count <= 1000; count++) {
				KeyPair keyPair = keyPairGenerator.generateKeyPair();
				PrivateKey privateKey = keyPair.getPrivate();
				PublicKey publicKey = keyPair.getPublic();
				switch (count) {
				case 10: case 20: case 50: case 100: case 200: case 500: case 1000:
					time = System.currentTimeMillis() - startTime;
					System.out.println("Generating " + count + " took " + time + " milliseconds.  On average, each key took " + time / count + " to generate.");
					break;
				}
			}
		} catch (NoSuchAlgorithmException e) {System.out.println(e.toString());}
	}

}
