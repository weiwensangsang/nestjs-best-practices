package com.weiwensangsang.service.util.sdk.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * MD5校验码生成工具
 * 
 */
public class Md5Util {

	/**
	 * 生成md5校验码
	 * 
	 * @param srcContent
	 *            需要加密的数据
	 * @return 加密后的md5校验码。出错则返回null。
	 */
	public static String makeMd5Sum(byte[] srcContent) {
		if (srcContent == null) {
			return null;
		}

		String strDes = null;

		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(srcContent);
			strDes = bytes2Hex(md5.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	/**
	 * 将byte转换为字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String bytes2Hex(byte[] byteArray) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (byteArray[i] >= 0 && byteArray[i] < 16) {
				strBuf.append("0");
			}
			strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
		}
		return strBuf.toString();
	}

	/**
	 * 新的md5签名，首尾放secret。
	 * 
	 * @param params
	 *            传给服务器的参数
	 * @param secret
	 *            首尾放secret
	 */
	public static String md5Signature(TreeMap<String, String> params, String secret) {
		String result = null;
		String orgin = getOrginSign(params, secret);
		if (orgin == null)
			return result;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = bytes2Hex(md.digest(orgin.toString().getBytes("utf-8")));
		} catch (Exception e) {
			throw new java.lang.RuntimeException("sign error !");
		}
		return result;
	}

	/**
	 * 将params进行连接，并在首尾添加字符串orgin
	 * 
	 * @param params
	 *            待连接的字符串
	 * @param secret
	 *            首尾添加的字符串
	 * @return
	 */
	public static String getOrginSign(TreeMap<String, String> params, String secret) {
		if (params == null)
			return null;
		StringBuffer sb = new StringBuffer();
		sb.append(secret).append("&");

		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.putAll(params);
		Iterator<String> iter = treeMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = params.get(key);
			sb.append(key).append("=").append(value).append("&");
		}
		sb.append(secret);
		return sb.toString();
	}

}
