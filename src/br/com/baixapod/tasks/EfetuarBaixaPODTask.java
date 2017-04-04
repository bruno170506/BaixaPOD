
package br.com.baixapod.tasks;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import br.com.baixapod.R;
import br.com.baixapod.activitys.ItemPodActivity;
import br.com.baixapod.dto.CoordenadaDTO;
import br.com.baixapod.model.ItemPOD;
import br.com.baixapod.webservice.ItemPODConverter;
import br.com.baixapod.webservice.WebClient;
import br.com.baixapod.webservice.WebServiceConstants;

public class EfetuarBaixaPODTask extends AsyncTask<Object, Object, String> {

	private ItemPodActivity contexto;
	private List<ItemPOD> pods;
	private CoordenadaDTO coordenadaDTO;
	ProgressDialog dialog = null;

	public EfetuarBaixaPODTask(ItemPodActivity ctx) {
		this.contexto = ctx;
	}

	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(contexto, contexto.getString(R.string.aguarde),
				contexto.getString(R.string.efetuando_baixa_pod), true);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String doInBackground(Object... params) {
		pods = (List<ItemPOD>) params[0];
		coordenadaDTO = (CoordenadaDTO) params[1];
		String json = ItemPODConverter.json(pods, coordenadaDTO);
		String url = WebServiceConstants.PODs.enviarPODs;
		String resposta = new WebClient(url).post(json);
		return resposta;
	}

	@Override
	protected void onPostExecute(String resposta) {
		dialog.dismiss();
		try {
			if (resposta.equals(WebServiceConstants.SUCESSO)) {
				contexto.baixaEfetuadaComSucesso(pods, contexto.getString(R.string.baixa_pod_online_sucess));
				return;
			} else if (resposta.contains(WebServiceConstants.PODS_FALHA)) {
				// String[] n_hawbsFALHA = resposta.split("#");
				contexto.falhaAoEnviarMovimentoPODs();
				return;
			}
			contexto.baixaPODFailed(contexto.getString(R.string.baixa_pod_online_failed));
		} catch (Exception e){
			contexto.baixaPODFailed(contexto.getString(R.string.baixa_pod_online_failed));
		}
	}
}
