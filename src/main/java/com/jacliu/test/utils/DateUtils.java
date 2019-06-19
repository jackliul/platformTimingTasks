package com.jacliu.test.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static long getTime(String oldTime, String newTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long diff = 0;
		try {
			long NTime = new Date().getTime();
			if (null != newTime) {
				NTime = df.parse(newTime).getTime();
			}
			long OTime = df.parse(oldTime).getTime();
			diff = (NTime - OTime) / 1000 / 60;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return diff;
	}

	public static long getTime(Date oldTime, Date newTime) {
		long diff = 0;
		try {
			long NTime = new Date().getTime();
			if (null != newTime) {
				NTime = newTime.getTime();
			}
			long OTime = oldTime.getTime();
			diff = (NTime - OTime) / 1000 / 60;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diff;
	}

	public static String getTimeStr(Date date) {
		return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

}
