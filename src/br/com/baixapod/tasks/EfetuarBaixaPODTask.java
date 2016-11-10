
package br.com.baixapod.tasks;

import java.util.List;

import android.os.AsyncTask;
import br.com.baixapod.activitys.ItemPodActivity;
import br.com.baixapod.model.ItemPOD;
import br.com.baixapod.webservice.ItemPODConverter;
import br.com.baixapod.webservice.WebClient;
import br.com.baixapod.webservice.WebServiceConstants;

public class EfetuarBaixaPODTask extends AsyncTask<Object, Object, String> {

	private ItemPodActivity contexto;
	private List<ItemPOD> pods;

	public EfetuarBaixaPODTask(ItemPodActivity ctx) {
		this.contexto = ctx;
	}

	@Override
	protected void onPreExecute() {}

	@Override
	protected String doInBackground(Object... params) {
		pods = (List<ItemPOD>) params[0];
		String json = ItemPODConverter.json(pods);
		String url = WebServiceConstants.PODs.enviarPODs;
		String resposta = new WebClient(url).post(json);
		return resposta;
	}

	@Override
	protected void onPostExecute(String resposta) {
		try {
			if (resposta.equals("sucesso")) {
				contexto.baixaEfetuadaComSucesso(pods);
			}
		} catch (Exception e) {
			contexto.baixaPODFailed();
		}
	}
}
