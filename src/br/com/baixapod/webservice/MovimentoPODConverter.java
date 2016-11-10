package br.com.baixapod.webservice;

import java.util.List;

import org.json.JSONArray;

import br.com.baixapod.model.MovimentoPOD;

public class MovimentoPODConverter {

	public static String json(List<MovimentoPOD> lista) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < lista.size(); i++) {
			MovimentoPOD baixaPOD = lista.get(i);
			jsonArray.put(baixaPOD.getJSONObject());
		}
		return jsonArray.toString();
	}

}
