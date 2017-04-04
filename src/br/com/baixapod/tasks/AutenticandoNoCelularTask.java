
package br.com.baixapod.tasks;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import br.com.baixapod.R;
import br.com.baixapod.activitys.AutenticacaoActivity;
import br.com.baixapod.dao.BancoDoCelularDAO;
import br.com.baixapod.model.Pessoa;

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
		progress = ProgressDialog.show(contexto, contexto.getString(R.string.aguarde), contexto.getString(R.string.autenticando_usuario));
	}

	@Override
	protected String doInBackground(Object... params) {
		String matricula = null;
		try {
			//tempo para exibir o feedback do progress
			Thread.sleep(1500);
			
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
