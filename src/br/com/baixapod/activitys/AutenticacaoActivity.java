package br.com.baixapod.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import br.com.baixapod.R;
import br.com.baixapod.model.Pessoa;
import br.com.baixapod.model.UsuarioConectado;
import br.com.baixapod.util.Internet;

public class AutenticacaoActivity extends MainActivity {

	private TextView usuario;
	private TextView senha;
	private CheckBox manterConectado;

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

			@Override
			public void onClick(View v) {

				desabilitarTecladoInicialmente();
				ocultarTeclado();

				boolean usuarioNaoPreenchido = usuario.getText() == null || usuario.getText().toString().isEmpty();
				boolean senhaNaoPreenchida = senha.getText() == null || senha.getText().toString().isEmpty();
				// VALIDA PREENCHIMENTO
				if (usuarioNaoPreenchido || senhaNaoPreenchida) {
					msgToast(AutenticacaoActivity.this, getString(R.string.usuario_senha_nao_preenchidos));
					return;
				}

				Pessoa p = new Pessoa();
				p.setUsuario(usuario.getText().toString());
				p.setSenha(senha.getText().toString());
				p.setConectado(manterConectado.isChecked());

				boolean temConexaoComInternet = Internet.temConexaoComInternet(AutenticacaoActivity.this, null);
				if (temConexaoComInternet) {
					getBancoNaNuvemDAO().popularTodasAsTabelasNoBancoDoCelular(AutenticacaoActivity.this, p);
					return;
				}
				boolean bancoDoCelularPreenchido = getBancoDoCelularDAO().bancoPreenchido();
				if (!temConexaoComInternet && !bancoDoCelularPreenchido) {
					msgToast(AutenticacaoActivity.this, getString(R.string.conecte_a_internet));
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
			msgToast(AutenticacaoActivity.this, getString(R.string.autenticacao_invalida));
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
