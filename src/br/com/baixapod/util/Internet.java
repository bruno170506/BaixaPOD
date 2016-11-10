package br.com.baixapod.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class Internet {

//	private static GPS gps;

	/*
	 * Função para verificar existência de conexão com a internet
	 */
	public static boolean temConexaoComInternet(Context contexto, String matricula) {
		boolean conectado;
		ConnectivityManager conectivtyManager = (ConnectivityManager) contexto
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conectivtyManager.getActiveNetworkInfo() != null && conectivtyManager.getActiveNetworkInfo().isAvailable()
				&& conectivtyManager.getActiveNetworkInfo().isConnected()) {
			conectado = true;
//			if(matricula!=null){
//				getGps().enviarLocalizacaoDoEntregador(contexto, matricula);
//			}
		} else {
			conectado = false;
		}
		return conectado;
	}
	
//	public static GPS getGps() {
//		if(gps==null){
//			gps = new GPS();
//		}
//		return gps;
//	}

	

}
