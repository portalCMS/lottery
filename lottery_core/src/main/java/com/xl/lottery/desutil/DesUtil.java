package com.xl.lottery.desutil;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;



public class DesUtil {

	public static String encrypt(String encryptString, String encryptKey) throws Exception {    
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");    
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");    
        cipher.init(Cipher.ENCRYPT_MODE, key);    
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());    
         
        return parseByte2HexStr(encryptedData);
    }    
    public static String decrypt(String decryptString, String decryptKey) throws Exception {    
        byte[] byteMi = parseHexStr2Byte(decryptString);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");    
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");    
        cipher.init(Cipher.DECRYPT_MODE, key);    
        byte decryptedData[] = cipher.doFinal(byteMi);    
        return new String(decryptedData);    
    }  

    public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	
	public static void main(String[] args) throws Exception {
		String test = "1_"+(System.currentTimeMillis()/1000);

		// DES加密
		test = encrypt(test, "cw201406");

		System.out.println("加密后的数据：" + test);

		// DES解密
		test = decrypt(test, "cw201406");

		System.out.println("解密后的数据：" + test);
	}
}
