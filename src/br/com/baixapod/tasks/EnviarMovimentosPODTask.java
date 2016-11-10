
package br.com.baixapod.tasks;

import java.io.File;
import java.util.List;

import android.os.AsyncTask;
import android.os.Environment;
import br.com.baixapod.activitys.ItemPodActivity;
import br.com.baixapod.model.MovimentoPOD;
import br.com.baixapod.webservice.MovimentoPODConverter;
import br.com.baixapod.webservice.WebClient;
import br.com.baixapod.webservice.WebServiceConstants;

public class EnviarMovimentosPODTask extends AsyncTask<Object, Object, String> {

	private ItemPodActivity contexto;
	private List<MovimentoPOD> listaMovimentoPODs;

	public EnviarMovimentosPODTask(ItemPodActivity ctx) {
		this.contexto = ctx;
	}

	@Override
	protected void onPreExecute() {}

	@Override
	protected String doInBackground(Object... params) {
		listaMovimentoPODs = (List<MovimentoPOD>) params[0];
		String json = MovimentoPODConverter.json(listaMovimentoPODs);
		String url = WebServiceConstants.PODs.enviarPODs;
		String resposta = new WebClient(url).post(json);
		return resposta;
	}

	@Override
	protected void onPostExecute(String resposta) {
		try {
			if (resposta.equals("sucesso")) {
				contexto.sucessoAoEnviarMovimentoPODs();
				contexto.getBancoDoCelularDAO().limparTabelaMovimentoPODs();
				
				File diretorio = new File(
						Environment.getExternalStorageDirectory() + "/ImagemPOD/");
				if (diretorio.exists() && diretorio.isDirectory()) {
					deleteDir(diretorio);
				}
				
			}else{
				contexto.falhaAoEnviarMovimentoPODs();
			}
		} catch (Exception e) {
			contexto.falhaAoEnviarMovimentoPODs();
		}
	}
	
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// Agora o diretório está vazio, restando apenas deletá-lo.
		return dir.delete();
	}
}
