
package br.com.baixapod.tasks;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import br.com.baixapod.R;
import br.com.baixapod.activitys.AutenticacaoActivity;
import br.com.baixapod.model.MovimentoPOD;
import br.com.baixapod.model.Pessoa;
import br.com.baixapod.webservice.WebClient;
import br.com.baixapod.webservice.WebServiceConstants;

public class PopularTabelasNoBancoDoCelularTask extends AsyncTask<Object, Object, String> {

	private AutenticacaoActivity contexto;
	private ProgressDialog progress;

	public PopularTabelasNoBancoDoCelularTask(AutenticacaoActivity ctx) {
		this.contexto = ctx;
	}

	@Override
	protected void onPreExecute() {
		progress = ProgressDialog.show(contexto, contexto.getString(R.string.aguarde), contexto.getString(R.string.autenticando_usuario));
	}

	@Override
	protected String doInBackground(Object... params) {
		String matricula = null;
		try {
			// autentica usuario
			Pessoa p = (Pessoa) params[0];
			String parametros = "?usuario=" + p.getUsuario() + "&senha=" + p.getSenha();
			String urlAutenticaoUsuario = WebServiceConstants.PODs.autenticacaoUsuario + parametros;
			String autenticou = new WebClient(urlAutenticaoUsuario).post();
			JSONArray jsonArray = new JSONArray(autenticou);
			if (jsonArray == null || jsonArray.length() < 1) {
				return null;
			}
			JSONObject object = (JSONObject) jsonArray.get(0);
			matricula = (String) object.get("matricula");

			// popula pessoa
			String urlPessoa = WebServiceConstants.PODs.listaPessoa;
			String pessoaJson = new WebClient(urlPessoa).post();
			contexto.getBancoDoCelularDAO().popularTabelaPessoa(pessoaJson, matricula);

			// popula ocorrencia
			String urlOcorrencia = WebServiceConstants.PODs.listarOcorrencias;
			String ocorrenciaJson = new WebClient(urlOcorrencia).post();
			contexto.getBancoDoCelularDAO().popularTabelaOcorrencia(ocorrenciaJson);

			// popula lista ItemPODs
			String urlPODs = WebServiceConstants.PODs.listarPODs + "?codigo=" + matricula;
			String itemPODsJson = new WebClient(urlPODs).post();
			List<MovimentoPOD> listaMovimentos = contexto.getBancoDoCelularDAO().listaMovimentoPODs();
			if(listaMovimentos.size()==0){
				contexto.getBancoDoCelularDAO().popularTabelaItemPODs(itemPODsJson);
			}
			
			//popula usuario conectado
			contexto.getBancoDoCelularDAO().atualizaUsuarioConectado(p);

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
