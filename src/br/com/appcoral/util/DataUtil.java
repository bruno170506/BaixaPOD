package br.com.appcoral.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

public class DataUtil {

	private static final String FORMATO_DATA = "dd/MM/yyyy";
	private static final String FORMATO_DATA_HORA = "dd/MM/yyyy HH:mm:ss";

	@SuppressLint("SimpleDateFormat")
	public static String dataComHoraAtual() {
		DateFormat df = new SimpleDateFormat(FORMATO_DATA_HORA);
		Date today = Calendar.getInstance().getTime();
		return df.format(today);
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String dataAtual() {
		DateFormat df = new SimpleDateFormat(FORMATO_DATA);
		Date today = Calendar.getInstance().getTime();
		return df.format(today);
	}

}
