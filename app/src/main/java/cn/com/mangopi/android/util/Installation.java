package cn.com.mangopi.android.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class Installation {
	private static String iId = "";
	private static String rId = "";
	private static final String INSTALLATION = "INSTALLATION";
	private static final String REGISTER = "REGISTER";

	public synchronized static String id(Context context) {
		if (TextUtils.isEmpty(iId)) {
			File installation = new File(context.getFilesDir(), INSTALLATION);
			try {
				if (!installation.exists())
					writeFile(installation);
				iId = readFile(installation);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return iId;
	}

	public synchronized static String registerId(Context context) {
		if (TextUtils.isEmpty(rId)) {
			File registration = new File(context.getFilesDir(), REGISTER);
			try {
				if (registration.exists())
					rId = readFile(registration);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return rId;
	}

	public synchronized static void updateRegisterId(Context context, String id) {
		rId = id;
		File registration = new File(context.getFilesDir(), REGISTER);
		try {
			writeFile(registration, id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String readFile(File file) throws IOException {
		RandomAccessFile f = new RandomAccessFile(file, "r");
		byte[] bytes = new byte[(int) f.length()];
		f.readFully(bytes);
		f.close();
		return new String(bytes);
	}

	private static void writeFile(File file) throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		String id = UUID.randomUUID().toString();
		out.write(id.getBytes());
		out.close();
	}

	private static void writeFile(File file, String id) throws IOException {
		FileOutputStream out = new FileOutputStream(file);
		out.write(id.getBytes());
		out.close();
	}
}
