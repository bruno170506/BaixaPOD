
package br.com.appcoral.util.webservice.coralista;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.appcoral.activitys.MainActivity;
import br.com.appcoral.model.Coralista;
import br.com.appcoral.util.webservice.WebClient;
import br.com.appcoral.util.webservice.WebServiceConstants;

public class adicionaCoralistaTask extends AsyncTask<Object, Object, String> {

	private MainActivity contexto;
	private ProgressDialog progress;
	private List<Coralista> coralistas;

	public adicionaCoralistaTask(MainActivity ctx, List<Coralista> coralistas) {
		this.coralistas = coralistas;
		this.contexto = ctx;
	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(contexto, "Aguarde...", "Salvando Coralista");
	}

	@Override
	protected String doInBackground(Object... params) {
		for (Coralista c : coralistas) {
			String json = new CoralistaConverter().toJSON(c);
			String url = WebServiceConstants.Coralista.adicionarCoralista;
			String resposta = new WebClient(url).post(json);
			if (!resposta.contains("sucess")) {
				return "Erro de Conexao com o Servidor.";
			}
		}
		return "Sucesso!";
	}

	@Override
	protected void onPostExecute(String resposta) {
		progress.dismiss();
		try {
			Toast.makeText(contexto, resposta, Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
