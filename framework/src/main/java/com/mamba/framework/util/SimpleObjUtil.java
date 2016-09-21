package com.mamba.framework.util;

public class SimpleObjUtil {

	public static boolean isEmpty(String arg) {
		if (arg == null || arg.trim().equals("")) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String... args) {
		for (String arg : args) {
			if (isEmpty(arg)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isEmpty(Object arg) {
		if (arg == null) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Object... args) {
		for (Object arg : args) {
			if (isEmpty(arg)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 随机生成 num位数字字符数组
	 * 
	 * @param num
	 * @return
	 */
	public static String generateRandomArray(int num) {
		String chars = "0123456789";
		char[] rands = new char[num];
		for (int i = 0; i < num; i++) {
			int rand = (int) (Math.random() * 10);
			rands[i] = chars.charAt(rand);
		}
		return new String(rands);
	}
}
