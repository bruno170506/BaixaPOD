
package br.com.baixapod.tasks;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.baixapod.activitys.AutenticacaoActivity;
import br.com.baixapod.model.Pessoa;
import br.com.baixapod.util.Mensagem;
import br.com.baixapod.webservice.WebClient;
import br.com.baixapod.webservice.WebServiceConstants;

public class AutenticacaoUsuarioTask extends AsyncTask<Object, Object, String> {

	private AutenticacaoActivity contexto;
	private ProgressDialog progress;

	public AutenticacaoUsuarioTask(AutenticacaoActivity ctx) {
		this.contexto = ctx;
	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(contexto, "Aguarde...", "Autenticando Usuário");
	}

	@Override
	protected String doInBackground(Object... params) {
		Pessoa p = (Pessoa) params[0];
		String parametros = "?usuario=" + p.getUsuario() + "&senha=" + p.getSenha();
		String url = WebServiceConstants.PODs.autenticacaoUsuario + parametros;
		String resposta = new WebClient(url).post();
		return resposta;
	}

	@Override
	protected void onPostExecute(String resposta) {
		progress.dismiss();
		try {
			JSONArray jsonArray = new JSONArray(resposta);
			if (jsonArray == null || jsonArray.length() < 1) {
				Toast.makeText(contexto, Mensagem.AUTENTICACAO_INVALIDA, Toast.LENGTH_SHORT).show();
				return;
			}
			JSONObject object = (JSONObject) jsonArray.get(0);
			String matricula = (String) object.get("matricula");
			contexto.autenticouComSucesso(matricula);
		} catch (Exception e) {
			Toast.makeText(contexto, Mensagem.AUTENTICACAO_INVALIDA, Toast.LENGTH_SHORT).show();
			return;
		}
	}
}
