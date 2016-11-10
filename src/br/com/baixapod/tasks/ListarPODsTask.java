
package br.com.baixapod.tasks;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import br.com.baixapod.activitys.ItemPodActivity;
import br.com.baixapod.dao.BancoNaNuvemDAO;
import br.com.baixapod.model.ItemPOD;
import br.com.baixapod.webservice.ItemPODConverter;
import br.com.baixapod.webservice.WebClient;
import br.com.baixapod.webservice.WebServiceConstants;

public class ListarPODsTask extends AsyncTask<Object, Object, String> {

	private ItemPodActivity contexto;
	private BancoNaNuvemDAO podDAO;
	private ProgressDialog progress;

	public ListarPODsTask(ItemPodActivity ctx, BancoNaNuvemDAO podDAO) {
		this.podDAO = podDAO;
		this.contexto = ctx;
	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(contexto, "Aguarde...", "Carregando Entregas...");
	}

	@Override
	protected String doInBackground(Object... params) {
		String matricula = String.valueOf(params[0]);
		String url = WebServiceConstants.PODs.listarPODs + "?codigo=" + matricula;
		String resposta = new WebClient(url).post();
		return resposta;
	}

	@Override
	protected void onPostExecute(String resposta) {
		progress.dismiss();
		try {
			List<ItemPOD> listaPODs = ItemPODConverter.toJSON(resposta);
			contexto.popularListaPODs(listaPODs);
			podDAO.carregarOcorrencias(contexto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
