package br.com.baixapod.webservice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.baixapod.model.Pessoa;

public class PessoaConverter {
	
	public static List<Pessoa> toJSON(String json) {
		ArrayList<Pessoa> lista = new ArrayList<Pessoa>();
		try {
			JSONArray jsonArray = new JSONArray(json);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
				Pessoa pessoa = new Pessoa();
				pessoa.setUsuario(object.get("usuario").toString());
				pessoa.setSenha(object.get("senha").toString());
				pessoa.setMatricula(object.get("matricula").toString());
				lista.add(pessoa);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lista;
	}

}
