package com.guihe.platform.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

public class CipherAESUtil {

	public static String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	private static Logger logger = LoggerFactory.getLogger(CipherAESUtil.class);
	private final static String KEY = "b597def38402ba8c";
	private final static String IV = "9583012938476028";

	public static Key getKey(String strKey) {
		Key retVal = null;
		try {
			if (strKey == null) {
				strKey = "";
			}
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes());
			generator.init(128, secureRandom);
			retVal = generator.generateKey();
		} catch (Exception e) {
			logger.error("[生成密钥异常]",e);
		}
		return  retVal;
	}

	/**
	 * AES加密
	 * @param plainText 明文
	 * @return
	 */
	public static String encrypt(String plainText) {
		String retVal = null;
		try{
			SecureRandom sr = new SecureRandom();
			Key secureKey = getKey(KEY);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secureKey, sr);
			byte[] bt = cipher.doFinal(plainText.getBytes());
			retVal = new BASE64Encoder().encode(bt);
		}catch (Exception e) {
			logger.error("[AES加密异常]",e);
		}
		return retVal;

	}
	/**
	 * AES解密
	 * @param cipherText 密文
	 * @return
	 */
	public static String decrypt(String cipherText)  {
		String retVal = null;
		try {
			SecureRandom sr = new SecureRandom();
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			Key secureKey = getKey(KEY);
			cipher.init(Cipher.DECRYPT_MODE, secureKey, sr);
			byte[] res = new BASE64Decoder().decodeBuffer(cipherText);
			byte[] bytes = cipher.doFinal(res);
			retVal =  new String(bytes);
		}catch (Exception e) {
			logger.error("[AES解密异常]",e);
		}
		return retVal;
	}

	public static void main(String[] args) throws Exception {
//		String encrypted = RSAEncrypt.encryptByPublicKey("j5z9at8nw51qkxhr", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8HMr2CBpoZPm3t9tCVlrKtTmI4jNJc7/HhxjIEiDjC8czP4PV+44LjXvLYcSV0fwi6nE4LH2c5PBPEnPfqp0g8TZeX+bYGvd70cXee9d8wHgBqi4k0J0X33c0ZnW7JruftPyvJo9OelYSofBXQTcwI+3uIl/YvrgQRv6A5mW01QIDAQAB");
		String decode = CipherAESUtil.AESDecode("ygmZRFmqWWtw4UnNERavAru8ZhQ0xNpxznAeObAv9zz98g17kKqgpBVttBn19ENoc1rLpMWYsHqM\n" +
				"UWLVjDocBAu6/oskeurVdqXDJtKPsFGDdPWyIa8maCnKhoS7KJABNOC+8hp5c8yDAvPfnXGkGi2D\n" +
				"Kk1tmg60RRspyBHxT1QtnSGYbevh21gfr+7a5s+4R9aaOgqlQGd4QUCH629fB+IoNXac07ccoaV9\n" +
				"No+H4NKONrChCBLk/U9esGZBkGlW4P04gu4PNW2CBnO9ybYW9pmR1ItVMdykLDnG7Vzm5/y9iTm6\n" +
				"nnIxY+bvGZB/ygWF", "seg9gtjq75773ubk");
		System.out.println("seg9gtjq75773ubk");
//		System.out.println(encode);
		System.out.println(decode);
//		String desEncrypt = CipherAESUtil.desEncrypt("111111");
//		String encrypt = CipherAESUtil.desDecrypt("cVzVN410JTb9Zt50P+Nq+g==");
//		System.out.println(desEncrypt);
//		System.out.println(encrypt);
	}

	public static String AESDecode(String content, String key) throws Exception {
		if(StringUtils.isBlank(content)) return null;
		byte[] raw = key.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips);
		byte[] encrypted1 = new BASE64Decoder().decodeBuffer(content);
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		return originalString;
	}

	public static String AESEncode(String content, String key) throws Exception {
		if(StringUtils.isBlank(content)) return null;
		byte[] raw = key.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
		//使用CBC模式，需要一个向量iv，可增加加密算法的强度
		IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips);
		byte[] encrypted = cipher.doFinal(content.getBytes());
		return new BASE64Encoder().encode(encrypted);
	}

	public static String desEncrypt(String content) throws Exception {
		if(StringUtils.isBlank(content)) return null;
		byte[] raw = KEY.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
		//使用CBC模式，需要一个向量iv，可增加加密算法的强度
		IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips);
		byte[] encrypted = cipher.doFinal(content.getBytes());
		return new BASE64Encoder().encode(encrypted);
	}

	public static String desDecrypt(String data) throws Exception {
		if(StringUtils.isBlank(data)) return null;
		try {
			byte[] raw = KEY.getBytes("utf-8");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips);
			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**将二进制转换成16进制
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i]  );
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}
	/**将16进制转换为二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1){
			return null;
		}
		byte[] result = new byte[hexStr.length()/2];
		for (int i = 0;i< hexStr.length()/2; i++) {
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}



}
