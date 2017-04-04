package br.com.baixapod.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import br.com.baixapod.dto.CoordenadaDTO;

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
	
	public CoordenadaDTO recuperarCoordenada(Context contexto) {
		CoordenadaDTO coordenadaDTO = null;
		if(verificaGPSAtivo(contexto)){
			String locationProvider = LocationManager.NETWORK_PROVIDER;
			locationManager.requestLocationUpdates(locationProvider, 0, 0, this);
			Location localizacao = locationManager.getLastKnownLocation(locationProvider);
			if (localizacao != null) {
				coordenadaDTO = new CoordenadaDTO();
				coordenadaDTO.setLongitude(String.valueOf(localizacao.getLongitude()));
				coordenadaDTO.setLatitude(String.valueOf(localizacao.getLatitude()));
			}
		}
		return coordenadaDTO;
	}
	
}