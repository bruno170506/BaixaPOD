package br.com.appcoral.util.webservice.coralista;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import br.com.appcoral.model.Coralista;

public class CoralistaConverter {

	public static String toJSON(Coralista coralista) {
		JSONStringer js = new JSONStringer();
		try {
			js.object().key("lista").array();
			js.object();
			js.key("id_coralista").value(coralista.getIdCoralista());
			js.key("nome").value(coralista.getNome());
			js.key("rg").value(coralista.getRg());
			js.key("telefone").value(coralista.getTelefone());
			js.key("email").value(coralista.getEmail());
			js.key("data_nascimento").value(coralista.getDataNascimento());
			js.key("nype_vocal").value(coralista.getNypeVocal());
			js.key("qr_code").value(coralista.getQrCode());
			js.key("status_ativo").value(coralista.getStatusAtivo());
			js.endObject();
			js.endArray().endObject();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return js.toString();
	}

	public static List<Coralista> toJSON(String json) {
		ArrayList<Coralista> lista = new ArrayList<Coralista>();

		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
				Coralista c = new Coralista();
				c.setIdCoralista(Long.parseLong(object.get("id_coralista").toString()));
				c.setNome(object.getString("nome"));
				lista.add(c);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lista;
	}

	// public String toJSON(List<Coralista> coralistas) {
	// JSONStringer js = new JSONStringer();
	// try {
	// js.object().key("lista").array();
	// int i=0;
	// for (Coralista coralista : coralistas) {
	// if(i==5){
	// break;
	// }
	// i++;
	// js.object();
	// js.key("id_coralista").value(coralista.getIdCoralista());
	// js.key("nome").value(coralista.getNome());
	// js.key("rg").value(coralista.getRg());
	// js.key("telefone").value(coralista.getTelefone());
	// js.key("email").value(coralista.getEmail());
	// js.key("data_nascimento").value(coralista.getDataNascimento());
	// js.key("nype_vocal").value(coralista.getNypeVocal());
	// js.key("qr_code").value(coralista.getQrCode());
	// js.key("status_ativo").value(coralista.getStatusAtivo());
	// js.endObject();
	//// break;
	// }
	// js.endArray().endObject();
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// return js.toString();
	// }

}
