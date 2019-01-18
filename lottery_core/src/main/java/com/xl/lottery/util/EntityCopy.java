package com.xl.lottery.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EntityCopy {

	public static Object copy(Object obj) throws IOException, ClassNotFoundException{
		   ByteArrayOutputStream bos = new ByteArrayOutputStream();
		   ObjectOutputStream oos = new ObjectOutputStream(bos);
		   oos.writeObject(obj);
		   ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
		   return ois.readObject();
	}
}
