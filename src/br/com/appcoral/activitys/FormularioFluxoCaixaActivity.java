package br.com.appcoral.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.appcoral.R;
import br.com.appcoral.activitys.helpers.FormularioFluxoCaixaHelper;
import br.com.appcoral.dao.FLuxoCaixaDAO;
import br.com.appcoral.model.FluxoCaixa;

public class FormularioFluxoCaixaActivity extends MainActivity {

	private FormularioFluxoCaixaHelper formularioFluxoCaixaHelper;
	private FluxoCaixa fluxoCaixaSelecionado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario_fluxo_caixa);

		formularioFluxoCaixaHelper = new FormularioFluxoCaixaHelper(FormularioFluxoCaixaActivity.this);

		final Button btnSalvar = (Button) findViewById(R.id.btn_salvar);
		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.getSerializable(ListagemFluxoCaixaActivity.FLUXO_CAIXA_SELECIONADO) != null) {
			fluxoCaixaSelecionado = (FluxoCaixa) extras.getSerializable(ListagemFluxoCaixaActivity.FLUXO_CAIXA_SELECIONADO);
		}
		

		if (fluxoCaixaSelecionado != null) {
			btnSalvar.setText("Atualizar");
			formularioFluxoCaixaHelper.preencheFluxoCaixaNoFormulario(fluxoCaixaSelecionado);
		} else {
			btnSalvar.setText("Salvar");
		}

		btnSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FluxoCaixa fluxoCaixa = formularioFluxoCaixaHelper.recuperaFluxoCaixaDoFormulario();
				FLuxoCaixaDAO dao = getDbConnections().getFluxoCaixaDAO();
				if (fluxoCaixaSelecionado != null) {
					fluxoCaixa.setIdFluxoCaixa(fluxoCaixaSelecionado.getIdFluxoCaixa());
					dao.atualizarFluxoCaixa(fluxoCaixa);
				} else {
					dao.inserirFluxoCaixa(fluxoCaixa);
				}
				message("Salvo com Sucesso!");
				onBackPressed();
			}
		});
	}

	private void message(String texto) {
		Toast.makeText(FormularioFluxoCaixaActivity.this, texto, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(FormularioFluxoCaixaActivity.this, ListagemFluxoCaixaActivity.class);
		startActivity(intent);
		finish();
	}

}
