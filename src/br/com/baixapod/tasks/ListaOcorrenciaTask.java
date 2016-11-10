
package br.com.baixapod.tasks;

import java.util.List;

import android.os.AsyncTask;
import br.com.baixapod.activitys.ItemPodActivity;
import br.com.baixapod.model.Ocorrencia;
import br.com.baixapod.webservice.OcorrenciaConverter;
import br.com.baixapod.webservice.WebClient;
import br.com.baixapod.webservice.WebServiceConstants;

public class ListaOcorrenciaTask extends AsyncTask<Object, Object, String> {

	private ItemPodActivity contexto;

	public ListaOcorrenciaTask(ItemPodActivity ctx) {
		this.contexto = ctx;
	}

	@Override
	protected void onPreExecute() {}

	@Override
	protected String doInBackground(Object... params) {
		String url = WebServiceConstants.PODs.listarOcorrencias;
		String resposta = new WebClient(url).post();
		return resposta;
	}

	@Override
	protected void onPostExecute(String resposta) {
		try {
			List<Ocorrencia> listaOcorrencia = OcorrenciaConverter.toJSON(resposta);
			contexto.popularListaMotivosOcorrencia(listaOcorrencia);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
