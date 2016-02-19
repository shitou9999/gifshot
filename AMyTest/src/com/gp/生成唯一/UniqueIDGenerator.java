/**
 * Generate unique printable character based ID
 */
package com.gp.生成唯一;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * @author 1
 *
 */
public class UniqueIDGenerator {
	

	public static String generate() {
		
		String id = null;
		
		// Get a 1024 bit random bytes
		SecureRandom random = new SecureRandom();
		byte randomBytes[] = new byte[128];
		random.nextBytes(randomBytes);
		
		// calculate md5 of random bytes
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		
		byte[] randomBytesMd5 = md.digest(randomBytes);
		
		// Convert to base64 string
		id = NumberConverter.getInstance().convert(new BigInteger(randomBytesMd5), 8*randomBytesMd5.length);
		
		return id;
		
	}

}
