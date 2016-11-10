package br.com.baixapod.webservice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.baixapod.model.MovimentoPOD;
import br.com.baixapod.model.ItemPOD;

public class ItemPODConverter {

	public static List<ItemPOD> toJSON(String json) {
		ArrayList<ItemPOD> lista = new ArrayList<ItemPOD>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
				ItemPOD itemPOD = new ItemPOD();
				itemPOD.setN_hawb(object.get("n_hawb").toString());
				itemPOD.setNome_desti(object.get("nome_desti").toString());
				itemPOD.setRua_desti(object.get("rua_desti").toString());
				itemPOD.setBairro_desti(object.get("bairro_desti").toString());
				itemPOD.setCidade_desti(object.get("cidade_desti").toString());
				itemPOD.setNumero_desti(object.get("numero_desti").toString());
				itemPOD.setN_tentativas(object.get("n_tentativas").toString());
				lista.add(itemPOD);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public static String json(List<ItemPOD> lista) {
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < lista.size(); i++) {
			MovimentoPOD baixaPOD = new MovimentoPOD(lista.get(0));
			jsonArray.put(baixaPOD.getJSONObject());
		}
		return jsonArray.toString();
	}

}
