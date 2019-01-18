package com.xl.lottery.core.algorithm;

public class TryArrange {
	public static void main(String args[]) {

		System.out.println("对整数数组进行全排列");
		int[] intArray = new int[9];
		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = i + 1;
		}

		Arrange intArrange = new Arrange(intArray.length);
		while (intArrange.hasMore()) {
			int[] index = intArrange.getNext();
			for (int i = 0; i < intArray.length; i++) {
				System.out.print(intArray[index[i]] + " ");
			}
			System.out.println();
		}

		System.out.println("对字符数组进行全排列");
		String str = "abc";
		char[] chArray = str.toCharArray();

		Arrange strArrange = new Arrange(chArray.length);
		while (strArrange.hasMore()) {
			int[] index = strArrange.getNext();
			for (int i = 0; i < chArray.length; i++) {
				System.out.print(chArray[index[i]] + " ");
			}
			System.out.println();
		}
	}
}