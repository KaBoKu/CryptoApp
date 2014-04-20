package main.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

public class AES {
	private static String algorithm = "AES";

	// Performs Encryption
	public static void encrypt(String filePlain,char [] pass,String fileEncrypt) throws Exception {
		FileInputStream fis = new FileInputStream(filePlain);
		FileOutputStream fos = new FileOutputStream(fileEncrypt);
		Key key = generateKey(pass);//"squirrel123alamasquirrel123alama".toCharArray());
		pass = null;
		Cipher chiper = Cipher.getInstance(algorithm);
		chiper.init(Cipher.ENCRYPT_MODE, key);
		CipherInputStream cIS = new CipherInputStream(fis, chiper);
		doCopy(cIS, fos);
	}

	// Performs decryption
	public static void decrypt(String fileEncrypt,char [] pass,String fileDecrypt) throws Exception {
		FileInputStream fis2 = new FileInputStream(fileEncrypt);
		FileOutputStream fos2 = new FileOutputStream(fileDecrypt);
		Key key = generateKey(pass);//"squirrel123alamasquirrel123alama".toCharArray());
		pass = null;
		Cipher chiper = Cipher.getInstance(algorithm);
		chiper.init(Cipher.DECRYPT_MODE, key);
		CipherOutputStream cos = new CipherOutputStream(fos2, chiper);
		doCopy(fis2, cos);
	}

	// generateKey() is used to generate a secret key for AES algorithm
	private static Key generateKey(char[] keya) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(String.valueOf(keya).getBytes("UTF-8"));
		byte [] k = md.digest();
		Key key = new SecretKeySpec(k/*String.valueOf(keya).getBytes()*/, algorithm);
		return key;
	}

	// performs encryption & decryption
	public static void main(String[] args) throws Exception {

		String plainText = "Password";
		encrypt("0.jpg","squirrel123alamasquirrel123alama".toCharArray(),"asd.sasa");
		decrypt("asd.sasa","squirrel123alamasquirrel123alama".toCharArray(),"ds.jpg");
	
	}
	
	
	public static void doCopy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[256];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
	}
	
}
