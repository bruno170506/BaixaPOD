
package br.com.baixapod.tasks;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import br.com.baixapod.activitys.AutenticacaoActivity;
import br.com.baixapod.dao.BancoDoCelularDAO;
import br.com.baixapod.model.Pessoa;
import br.com.baixapod.webservice.WebClient;
import br.com.baixapod.webservice.WebServiceConstants;

public class AutenticandoNoCelularTask extends AsyncTask<Object, Object, String> {

	private AutenticacaoActivity contexto;
	private ProgressDialog progress;
	private BancoDoCelularDAO bancoCelularDAO;

	public AutenticandoNoCelularTask(AutenticacaoActivity ctx, BancoDoCelularDAO bancoCelularDAO) {
		this.contexto = ctx;
		this.bancoCelularDAO = bancoCelularDAO;
	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(contexto, "Aguarde...", "Autenticando Usuário");
	}

	@Override
	protected String doInBackground(Object... params) {
		String matricula = null;
		try {
			Pessoa p = (Pessoa) params[0];
			// autentica usuario no celular
			List<Pessoa> listaPessoa = bancoCelularDAO.listaPessoa();
			for (Pessoa pessoa : listaPessoa) {
				if(pessoa.getUsuario().equalsIgnoreCase(p.getUsuario()) && pessoa.getSenha().equals(p.getSenha())){
					matricula = pessoa.getMatricula();
				}
			}
		} catch (Exception e) {
			return null;
		}

		return matricula;
	}

	@Override
	protected void onPostExecute(String matricula) {
		progress.dismiss();
		contexto.autenticouComSucesso(matricula);
	}
}
