package br.com.baixapod.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class GPS implements LocationListener {
	
	private LocationManager locationManager;

	@Override
	public void onLocationChanged(Location location) {
		//location.getLatitude();
		//location.getLongitude();
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
	
	public boolean verificaGPSAtivo(Context contexto){
		locationManager = (LocationManager) contexto.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	public void enviarLocalizacaoDoEntregador(Context contexto, String matricula) {
		//ENVIAR LOCALIZACAO DO ENTREGADOR
		if(verificaGPSAtivo(contexto)){
			GPS gps = new GPS();
			String locationProvider = LocationManager.NETWORK_PROVIDER;
			locationManager.requestLocationUpdates(locationProvider, 0, 0, gps);
			Location localizacao = locationManager.getLastKnownLocation(locationProvider);
			if (localizacao != null) {
				double longitude = localizacao.getLongitude();
				double latitude = localizacao.getLatitude();
//				String wkt = "POINT(" + longitude + " " + latitude + ")";
				Toast.makeText(contexto, "Lon:"+longitude+" Lat: "+latitude, Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
}