package br.com.baixapod.webservice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.baixapod.model.Ocorrencia;

public class OcorrenciaConverter {
	
	public static List<Ocorrencia> toJSON(String json) {
		ArrayList<Ocorrencia> lista = new ArrayList<Ocorrencia>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
				Ocorrencia ocorrencia = new Ocorrencia();
				ocorrencia.setCodigo(object.get("codigo").toString());
				ocorrencia.setDescricao(object.get("descricao").toString());
				lista.add(ocorrencia);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lista;
	}

}
