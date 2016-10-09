package org.moshe.arad.general;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:general-context-test.xml")
public class DesEncryptionTest {

	private final Logger logger = LogManager.getLogger(DesEncryptionTest.class);
	@Autowired
	private DesEncryption desEncryption;
	
	@Test
	public void encryptDecrypt(){
		String token = "This is a secret.";
		
		String encryptAns = desEncryption.encrypt(token);
		
		assertNotEquals(token, encryptAns);
		
		String decryptAns = desEncryption.decrypt(encryptAns);
		
		assertEquals(token, decryptAns);
		
		logger.info("Token is: " + token);
		logger.info("After encryption is: " + encryptAns);
		logger.info("After dencryption is: " + decryptAns);
	}
}
