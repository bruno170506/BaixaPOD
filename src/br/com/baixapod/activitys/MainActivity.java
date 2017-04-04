package br.com.baixapod.activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;
import br.com.baixapod.R;
import br.com.baixapod.dao.BancoDoCelularDAO;
import br.com.baixapod.dao.BancoNaNuvemDAO;
import br.com.baixapod.dao.DBConnections;

@SuppressLint("InlinedApi")
public class MainActivity extends Activity {

	public DBConnections dbConection;
	public BancoNaNuvemDAO bancoNaNuvemDAO;
	public BancoDoCelularDAO bancoDoCelularDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public DBConnections getDbConection() {
		if (dbConection == null) {
			dbConection = new DBConnections(this);
		}
		return dbConection;
	}

	public BancoNaNuvemDAO getBancoNaNuvemDAO() {
		if (bancoNaNuvemDAO == null) {
			bancoNaNuvemDAO = new BancoNaNuvemDAO();
		}
		return bancoNaNuvemDAO;
	}

	public BancoDoCelularDAO getBancoDoCelularDAO() {
		if (bancoDoCelularDAO == null) {
			bancoDoCelularDAO = new BancoDoCelularDAO(getDbConection());
		}
		return bancoDoCelularDAO;
	}

	public void msgToast(Context contexto, String mensagem) {
		Toast.makeText(contexto, mensagem, Toast.LENGTH_SHORT).show();
	}

}
