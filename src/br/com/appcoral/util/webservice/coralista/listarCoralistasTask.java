
package br.com.appcoral.util.webservice.coralista;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import br.com.appcoral.activitys.ListagemCoralistaActivity;
import br.com.appcoral.util.webservice.WebClient;
import br.com.appcoral.util.webservice.WebServiceConstants;

public class listarCoralistasTask extends AsyncTask<Object, Object, String> {

	private ListagemCoralistaActivity contexto;
	private ProgressDialog progress;

	public listarCoralistasTask(ListagemCoralistaActivity ctx) {
		this.contexto = ctx;
	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(contexto, "Aguarde...", "Carregando Dados...");
	}

	@Override
	protected String doInBackground(Object... params) {
		String url = WebServiceConstants.Coralista.listarCoralista;
		String resposta = new WebClient(url).post();
		return resposta;
	}

	@Override
	protected void onPostExecute(String resposta) {
		progress.dismiss();
		try {
			contexto.recarregaLista(CoralistaConverter.toJSON(resposta));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
