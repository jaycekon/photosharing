package com.sherlochao.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.lang3.Validate;


/**
 * 支持SHA-1/MD5消息摘要的工具类.
 * 返回ByteSource，可进一步被编码为Hex, Base64或UrlSafeBase64
 */
public class Digests {

	private static final String SHA1 = "SHA-1";
	private static final String MD5 = "MD5";
	
	public static final String HASH_ALGORITHM = "MD5";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

//	public static final String md5_salt = "34609tghlsd";
	
	private static SecureRandom random = new SecureRandom();

	/**
	 * 对输入字符串进行md5散列.
	 */
	public static byte[] md5(byte[] input) {
		return digest(input, MD5, null, 1);
	}
	public static byte[] md5(byte[] input, int iterations) {
		return digest(input, MD5, null, iterations);
	}
	
	/**
	 * 对输入字符串进行sha1散列.
	 */
	public static byte[] sha1(byte[] input) {
		return digest(input, SHA1, null, 1);
	}

	public static byte[] sha1(byte[] input, byte[] salt) {
		return digest(input, SHA1, salt, 1);
	}

	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return digest(input, SHA1, salt, iterations);
	}

	/**
	 * 对字符串进行散列, 支持md5与sha1算法.
	 */
	private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 生成随机的Byte[]作为salt.
	 * 
	 * @param numBytes byte数组的大小
	 */
	public static byte[] generateSalt(int numBytes) {
		Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}

	/**
	 * 对文件进行md5散列.
	 */
	public static byte[] md5(InputStream input) throws IOException {
		return digest(input, MD5);
	}

	/**
	 * 对文件进行sha1散列.
	 */
	public static byte[] sha1(InputStream input) throws IOException {
		return digest(input, SHA1);
	}

	/**
	 * @author lihaishan 2015-12-02
	 * 新增从旧系统移植过来的MD5加密方法
	 * */
	public static String md5AndSalt(String source) {
	    char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	    try {
	      MessageDigest md = MessageDigest.getInstance("MD5");
	      md.update((source).getBytes()); 
	      byte[] encryptStr = md.digest(); 
	      char str[] = new char[16 * 2]; 
	      int k = 0; 
	      for (int i = 0; i < 16; i++) { 
	        byte byte0 = encryptStr[i];
	        str[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
	        str[k++] = hexDigits[byte0 & 0xf]; 
	      }
	      return new String(str); 
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }
	    return null;
	  } 
	
	private static byte[] digest(InputStream input, String algorithm) throws IOException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			int bufferLength = 8 * 1024;
			byte[] buffer = new byte[bufferLength];
			int read = input.read(buffer, 0, bufferLength);

			while (read > -1) {
				messageDigest.update(buffer, 0, read);
				read = input.read(buffer, 0, bufferLength);
			}

			return messageDigest.digest();
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * @author lihaishan
	 * 验证密码——以MD5的方式
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePasswordWithMD5AndSalt(String plainPassword, String password) {
		return password.equals(md5AndSalt(plainPassword));
	}
	
	public static void main(String[] args) {
		/*System.out.println(entryptPassword("kviuff"));
		System.out.println(validatePassword("kviuff", "66073c9476862b7e4c4e381ce415eb1469f77b6a"));*/
	}
	
}