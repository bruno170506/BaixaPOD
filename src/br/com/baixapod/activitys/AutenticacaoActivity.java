package br.com.baixapod.activitys;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import br.com.baixapod.R;
import br.com.baixapod.model.Pessoa;
import br.com.baixapod.model.UsuarioConectado;
import br.com.baixapod.util.GPS;
import br.com.baixapod.util.Internet;
import br.com.baixapod.util.Mensagem;

public class AutenticacaoActivity extends MainActivity {

	private TextView usuario;
	private TextView senha;
	private CheckBox manterConectado;
	protected GPS gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.autenticacao);

		desabilitarTecladoInicialmente();

		usuario = (TextView) findViewById(R.id.usuario);
		usuario.setText("");
		senha = (TextView) findViewById(R.id.senha);
		senha.setText("");
		manterConectado = (CheckBox) findViewById(R.id.mantenhaMeConectado);
		manterConectado.setChecked(false);

		
		UsuarioConectado usuarioConectado = getBancoDoCelularDAO().usuarioConectado();
		if (usuarioConectado != null) {
			usuario.setText(usuarioConectado.getUsuario());
			senha.setText(usuarioConectado.getSenha());
			manterConectado.setChecked(true);
		}
		
		
		Button entrar = (Button) findViewById(R.id.entrar);
		entrar.setOnClickListener(new OnClickListener() {

			private LocationManager locationManager;

			@Override
			public void onClick(View v) {
				desabilitarTecladoInicialmente();
				ocultarTeclado();

				boolean usuarioNaoPreenchido = usuario.getText() == null || usuario.getText().toString().isEmpty();
				boolean senhaNaoPreenchida = senha.getText() == null || senha.getText().toString().isEmpty();
				// VALIDA PREENCHIMENTO
				if (usuarioNaoPreenchido || senhaNaoPreenchida) {
					Toast.makeText(AutenticacaoActivity.this, Mensagem.AUTENTICACAO_INVALIDA, Toast.LENGTH_SHORT)
							.show();
					return;
				}

				Pessoa p = new Pessoa();
				p.setUsuario(usuario.getText().toString());
				p.setSenha(senha.getText().toString());
				p.setConectado(manterConectado.isChecked());

//				gps = new GPS();//TODO
				boolean temConexaoComInternet = Internet.temConexaoComInternet(AutenticacaoActivity.this,null);
				if (temConexaoComInternet) {
					//VERIFICA GPS ATIVO //TODO
//					if(!gps.verificaGPSAtivo(AutenticacaoActivity.this)){
//						Toast.makeText(AutenticacaoActivity.this, Mensagem.HABILITE_GPS, Toast.LENGTH_SHORT).show();
//						return;
//					}
					
					getBancoNaNuvemDAO().popularTodasAsTabelasNoBancoDoCelular(AutenticacaoActivity.this, p);
					return;
				}
				boolean bancoDoCelularPreenchido = getBancoDoCelularDAO().bancoPreenchido();
				if (!temConexaoComInternet && !bancoDoCelularPreenchido) {
					Toast.makeText(AutenticacaoActivity.this, Mensagem.CONECTE_A_INTERNET, Toast.LENGTH_SHORT).show();
					return;
				}
				// sem internet e banco do celular preenchido
				getBancoDoCelularDAO().fazerAutenticacaoBancoDoCelular(AutenticacaoActivity.this, p);
			}


		});
	}

	public void autenticouComSucesso(String matricula) {
		if (matricula != null) {
			// autenticou e tem a matricula
			irParaTelaItemPOD(matricula);
			
		} else {
			Toast.makeText(AutenticacaoActivity.this, Mensagem.AUTENTICACAO_INVALIDA, Toast.LENGTH_SHORT).show();
		}
	}

	private void irParaTelaItemPOD(String matricula) {
		Intent intent = new Intent(AutenticacaoActivity.this, ItemPodActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("matricula", matricula);
		intent.putExtras(bundle);
		startActivity(intent);
		finish();
	}

	private void ocultarTeclado() {
		((InputMethodManager) getSystemService(ItemPodActivity.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(usuario.getWindowToken(), 0);
		((InputMethodManager) getSystemService(ItemPodActivity.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(senha.getWindowToken(), 0);
	}

	private void desabilitarTecladoInicialmente() {
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

}
