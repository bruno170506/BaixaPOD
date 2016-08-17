package br.com.appcoral.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.appcoral.R;
import br.com.appcoral.activitys.helpers.FormularioCoralistaHelper;
import br.com.appcoral.dao.CoralistaDAO;
import br.com.appcoral.model.Coralista;
import br.com.appcoral.util.DialogUtil;

public class FormularioCoralistaActivity extends MainActivity {

	private FormularioCoralistaHelper formularioCoralistaHelper;
	private Coralista coralistaSelecionado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario_coralistas);

		formularioCoralistaHelper = new FormularioCoralistaHelper(FormularioCoralistaActivity.this);

		final Button btnSalvar = (Button) findViewById(R.id.btn_salvarCoralista);

		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.getSerializable(ListagemCoralistaActivity.CORALISTA_SELECIONADO) != null) {
			coralistaSelecionado = (Coralista) extras.getSerializable(ListagemCoralistaActivity.CORALISTA_SELECIONADO);
		}

		btnSalvar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Coralista coralista = formularioCoralistaHelper.recuperaCoralistaDoFormulario();
				CoralistaDAO dao = getDbConnections().getCoralistaDAO();
				if (coralistaSelecionado != null) {
					coralista.setIdCoralista(coralistaSelecionado.getIdCoralista());
					coralista.setStatusAtivo("S");
					dao.atualizarCoralista(coralista);
					if(coralista.getQrCode() == null){
						dao.preencheQrCodeCoralista(dao.pesquisarPorNome(coralista.getNome(),CoralistaDAO.STATUS_ATIVO).get(0));
					}
				} else {
					dao.insereCoralista(coralista);
					dao.preencheQrCodeCoralista(dao.pesquisarPorNome(coralista.getNome(),CoralistaDAO.STATUS_ATIVO).get(0));
				}
				DialogUtil.feedback(FormularioCoralistaActivity.this, "Salvo com Sucesso!");
				irParaListagemDeCoralistas();
			}

		});
		

		if (coralistaSelecionado != null) {
			btnSalvar.setText("Atualizar");
			formularioCoralistaHelper.preencheCoralistaNoFormulario(coralistaSelecionado);
		} else {
			btnSalvar.setText("Salvar");
		}

	}
	
	private void irParaListagemDeCoralistas() {
		Intent irParaAListagemDeCoralistas = new Intent(FormularioCoralistaActivity.this,
				ListagemCoralistaActivity.class);
		startActivity(irParaAListagemDeCoralistas);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		irParaListagemDeCoralistas();
	}

}
