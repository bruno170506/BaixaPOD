package br.com.appcoral.activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import br.com.appcoral.R;
import br.com.appcoral.dao.DBConnections;
import br.com.appcoral.util.BackupUtil;
import br.com.appcoral.util.FileUtil;
import br.com.appcoral.util.reports.RelatorioUtil;
import br.com.appcoral.util.webservice.coralista.listarCoralistasTask;

@SuppressLint("InlinedApi")
public class MainActivity extends Activity {

	private DBConnections dbConnections;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbConnections = new DBConnections(MainActivity.this);
	}

	public DBConnections getDbConnections() {
		return dbConnections;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_principal, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.menu_coralistas:
			irParaListagemCoralistas();
			break;
		case R.id.fluxo_caixa:
			irParaFluxoDeCaixa();
			break;
		case R.id.defineValorMensalidade:
			irParaDefinirValorMensalidade();
			break;	
		case R.id.enviarBackup:
			BackupUtil.enviarBackup(this);
			break;
		case R.id.restaurarBackup:
			BackupUtil.restaurarBackup(this);
			break;
		case R.id.relatorioCadastroCoralistas:
			RelatorioUtil.gerarRelatorioCadastroCoralistas(this);
			break;
		case R.id.relatorioFluxoCaixa:
			RelatorioUtil.enviarRelatorioFluxoCaixa(this);
			break;
		case R.id.relatorioPagamentoMensalidades:
			RelatorioUtil.enviarRelatorioPagamentoMensalidades(this);
			break;
		case R.id.gerarCoralistasTxt:
			FileUtil.gerarCoralistasTXT(this);
			break;
		case R.id.gerarQrCodesCoralistasTxt:
			FileUtil.gerarQrCodesCoralistasTxt(this);
			break;
		case R.id.enviarEmail:
			RelatorioUtil.enviarRelatoriosPorEmail(this);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void irParaDefinirValorMensalidade() {
		if (!(this instanceof DefineValorMensalidadeActivity)) {
			Intent intent = new Intent(MainActivity.this, DefineValorMensalidadeActivity.class);
			startActivity(intent);
			finish();
		}
	}

	public void irParaListagemCoralistas() {
		if (!(this instanceof ListagemCoralistaActivity)) {
			Intent intent = new Intent(MainActivity.this, ListagemCoralistaActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private void irParaFluxoDeCaixa() {
		if (!(this instanceof ListagemFluxoCaixaActivity)) {
			Intent intent = new Intent(MainActivity.this, ListagemFluxoCaixaActivity.class);
			startActivity(intent);
			finish();
		}
	}

}
