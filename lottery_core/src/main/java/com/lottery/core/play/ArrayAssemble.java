package com.lottery.core.play;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayAssemble {
	public static void main(String[] args) {
		 String arg1[] = "0,1,2,3,4,5,6,7,8,9".split(",");  
	     String arg2[] = "0,1,2,3,4,5,6,7,8,9".split(",");  
	     String arg3[] = "0,1,2,3,4,5,6,7,8,9".split(",");  
	     String arg4[] = "0,1,2,3,4,5,6,7,8,9".split(",");  
	     String arg5[] = "0,1,2,3,4,5,6,7,8,9".split(","); 

		System.out.println("以下是组合后的数组");

		// List arrayList = assembleArraysToList(arrOfInt);
		// for (int n = 0; n < arrayList.size(); n++) {
		// Object obj = arrayList.get(n);
		// if (obj instanceof Object[]) {
		// Object[] objArr = (Object[]) obj;
		// System.out.println(Arrays.toString(objArr));
		// } else {
		// System.out.println(obj);
		// }
		//
		// }
		Long time1 = System.currentTimeMillis();
		List<Object[]> objectsArrays = assembleArraysToList(arg1,arg2,arg3,arg4,arg5);
		System.out.println(System.currentTimeMillis() - time1);
		System.out.println(objectsArrays.size());
//		for (Object[] objArrays : objectsArrays) {
//			System.out.println(Arrays.toString(objArrays));
//		}

	}

	// 方法一：使用可变参数方法返回数组类型的List
	@SuppressWarnings("unchecked")
	public static List assembleArraysToList(Object[]... objects) {
		List arrayList = new ArrayList();
		// 遍历方法的参数
		for (int i = 0; i < objects.length; i++) {

			if (i == 0) {
				// 对于第一个数组参数,先将其转变成List类型,以便能使用辅助方法进行处理
				arrayList = Arrays.asList(objects[i]);
			} else {
				// 对从第二个参数开始的数组与前面组合过的列表进行组合
				arrayList = assembleArrayToList(arrayList, objects[i]);
			}
		}
		return arrayList;
	}

	// 方法一的辅助方法：将一个数组类型或对象类型的List与数组组合，并返回List
	@SuppressWarnings("unchecked")
	public static List assembleArrayToList(List aList, Object[] array) {
		List arrList = new ArrayList();
		// 遍历aList,将array与aList进行组合
		for (int i = 0; i < aList.size(); i++) {
			Object obj = aList.get(i);
			// 检查aList的元素是否是数组类型的，如果不是，则直接产生组合列表
			if (obj instanceof Object[]) {
				Object[] listArr = (Object[]) obj;
				// 对数组类型的aList元素与array进行组合
				for (int k = 0; k < array.length; k++) {
					Object[] newListArr = new Object[listArr.length + 1];
					for (int j = 0; j < listArr.length; j++) {
						newListArr[j] = listArr[j];
					}
					newListArr[listArr.length] = array[k];
					arrList.add(newListArr);
				}
			} else {
				// 对非数组类型的aList元素与array进行组合
				for (int j = 0; j < array.length; j++) {
					Object[] arrObj = { aList.get(i), array[j] };
					arrList.add(arrObj);
				}
			}
		}
		return arrList;
	}

	// 方法二：使用二维数组参数方法返回组合的二维数组类型，并使用了递归
	@SuppressWarnings("unchecked")
	public static Object[][] assembleArraysToPlanerArray(Object[][] objectArrays) {
		if (objectArrays.length == 2) {
			Object[] assembledArray = objectArrays[0];
			Object[] array = objectArrays[1];
			return assembleArrayToArray(assembledArray, array);
		} else if (objectArrays.length <= 1) {
			return objectArrays;
		} else {
			Object[] objArray = objectArrays[objectArrays.length - 1];
			objectArrays = Arrays.copyOf(objectArrays, objectArrays.length - 1);
			return assembleArrayToArray(
					assembleArraysToPlanerArray(objectArrays), objArray);
		}
	}

	// 方法二的辅助方法：将一个数组类型或二维数组类型与数组组合，并返回二维数组
	public static Object[][] assembleArrayToArray(Object[] assembledArray,
			Object[] array) {
		int lenAssArray = assembledArray.length;
		int lenArray = array.length;
		Object[][] objArrays = new Object[lenAssArray * lenArray][];
		for (int i = 0; i < lenAssArray; i++) {
			Object obj = assembledArray[i];
			if (obj instanceof Object[]) {
				Object[] objArr = (Object[]) obj;
				int lenObjArr = objArr.length;
				for (int k = 0; k < lenArray; k++) {
					// 复制objArr数组到newListArr数组，并将其长度加一
					Object[] newListArr = Arrays.copyOf(objArr, lenObjArr + 1);
					// 将array数组的第k+1元素赋值给newListArr数组最后的元素,并将newListArr赋值给objArrays数组的第k+1个元素
					newListArr[lenObjArr] = array[k];
					objArrays[lenArray * i + k] = newListArr;
				}
			} else {
				for (int j = 0; j < lenArray; j++) {
					Object[] newObjArray = { obj, array[j] };
					objArrays[lenArray * i + j] = newObjArray;
				}
			}
		}
		return objArrays;
	}
}
