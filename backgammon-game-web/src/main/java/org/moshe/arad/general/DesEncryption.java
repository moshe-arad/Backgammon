package org.moshe.arad.general;

import static org.mockito.Mockito.doAnswer;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

@Component
public class DesEncryption {
	
	@Value("${game.token}")
	private String keyInput;
	private static SecretKey key;
	private static Cipher ecipher;
    private static Cipher dcipher;
    private static final Logger logger = LogManager.getLogger(DesEncryption.class);
    
    @PostConstruct
    public void init(){
    	try {        	      
        	SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        	key = factory.generateSecret(new DESKeySpec(get24ByteKey()));
        	
			dcipher = Cipher.getInstance("DES");
			ecipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);
		} catch (NoSuchAlgorithmException |
				NoSuchPaddingException |
				InvalidKeyException |
				InvalidKeySpecException ex) {
			logger.error("Error while trying to do key encryption preparations");
			logger.error(ex.getMessage());
			logger.error(ex);
		}
    }
    
	public static String encrypt(String str) {

    	try {
    		byte[] utf8 = str.getBytes("UTF8");
    		byte[] enc = ecipher.doFinal(utf8);
    		enc = BASE64EncoderStream.encode(enc);
    		return new String(enc);
    	}
    	catch (Exception ex) {
    		logger.error("Error while trying to encrypt.");
			logger.error(ex.getMessage());
			logger.error(ex);
    	}
    	return null;
    }
    
    public static String decrypt(String str) {
    	try {
    		byte[] dec = BASE64DecoderStream.decode(str.getBytes());
    		byte[] utf8 = dcipher.doFinal(dec);
    		return new String(utf8, "UTF8");
    	}
    	catch (Exception ex) {
    		logger.error("Error while trying to dencrypt.");
			logger.error(ex.getMessage());
			logger.error(ex);
    	}
    	return null;
    }
    
    private byte[] get24ByteKey(){
    	return Arrays.copyOf(keyInput.getBytes(), 24);
    }
}
