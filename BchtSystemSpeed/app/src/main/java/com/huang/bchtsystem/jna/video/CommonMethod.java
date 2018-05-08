package com.huang.bchtsystem.jna.video;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class CommonMethod {
	
	public static byte[] string2ASCII(String s,int length) {// 字符串转换为ASCII�?
		if (s == null || "".equals(s)) {
			return null;
		}

		char[] chars = s.toCharArray();
		byte[] asciiArray = new byte[length];

		for (int i = 0; i < length; i++) {
			if (i < chars.length) {
				asciiArray[i] = char2ASCII(chars[i]);
			} else {
				asciiArray[i] = char2ASCII('\0');
			}
		}
		return asciiArray;
	}
	
	public static byte char2ASCII(char c){
    	return (byte)c;
    }
	public static String toValidString(String s){
        String[] sIP = new String[2]; 
        sIP = s.split("\0", 2);
        return sIP[0];
    }

	public static String bytes2HexString(byte[] b) { //byte数组转字符串
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			if (b[i] == 0){
				byte [] a = new byte[i] ;
				for(int j =0 ;j<i;j++){
					a[j] = b[j];
				}
				try {
					ret = new String(a , "GBK");
					return ret;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			}
		return ret;
	}

	public static byte[] Stringtobyte2(String str){
		byte[]  resbyte = null;
		try {

			resbyte = str.getBytes("GBK");

			return resbyte ;

		} catch (UnsupportedEncodingException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}
		return resbyte;
	}
}

