package br.com.appcoral.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import br.com.appcoral.R;
import br.com.appcoral.dao.ValorMensalidadeDAO;
import br.com.appcoral.model.ValorMensalidade;
import br.com.appcoral.util.DialogUtil;
import br.com.appcoral.util.Moeda;

public class DefineValorMensalidadeActivity extends MainActivity {

	private EditText valorMensalidadeEditText;
	private EditText valorAcrescimoEditText;
	private Button btnDefinirMensalidade;
	private ValorMensalidadeDAO valorMensalidadeDAO;
	private ValorMensalidade valorMensalidadeDefinido;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.define_valor_mensalidade);

		valorMensalidadeDAO = getDbConnections().getValorMensalidadeDAO();

		valorMensalidadeEditText = (EditText) findViewById(R.id.valorMensalidade);
		valorMensalidadeEditText.addTextChangedListener(Moeda.insert(valorMensalidadeEditText));

		valorAcrescimoEditText = (EditText) findViewById(R.id.valorAcrescimo);
		valorAcrescimoEditText.addTextChangedListener(Moeda.insert(valorAcrescimoEditText));

		valorMensalidadeDefinido = valorMensalidadeDAO.recuperaValorMensalidade();
		if (valorMensalidadeDefinido != null) {
			valorMensalidadeEditText.setText(Moeda.valorFormatado(valorMensalidadeDefinido.getValorMensalidade()));
			valorAcrescimoEditText.setText(Moeda.valorFormatado(valorMensalidadeDefinido.getValorAcrescimo()));
		}

		btnDefinirMensalidade = (Button) findViewById(R.id.btnDefinirValorMensalidade);
		btnDefinirMensalidade.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ValorMensalidade valorMensalidade = new ValorMensalidade();
				if (valorMensalidadeDefinido != null) {
					valorMensalidade.setIdValorMensalidade(valorMensalidadeDefinido.getIdValorMensalidade());
				}
				
				String valorMensalidadePreenchido = valorMensalidadeEditText.getText().toString();
				String valorAcrescimoPreenchido = valorAcrescimoEditText.getText().toString();
				if(valorMensalidadePreenchido == null || valorMensalidadePreenchido.isEmpty() || valorAcrescimoPreenchido==null|| valorAcrescimoPreenchido.isEmpty()){
					DialogUtil.feedback(DefineValorMensalidadeActivity.this, "Preencha o Valor da Mensalidade e Acréscimo!");
					return;
				}
				valorMensalidade.setValorMensalidade(Moeda.getValor(valorMensalidadePreenchido));
				valorMensalidade.setValorAcrescimo(Moeda.getValor(valorAcrescimoPreenchido));
				valorMensalidadeDAO.definirValorMensalidade(valorMensalidade);
				DialogUtil.feedback(DefineValorMensalidadeActivity.this, "Valor Definido Com Sucesso!");
				onBackPressed();
			}
		});
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(DefineValorMensalidadeActivity.this, ListagemCoralistaActivity.class);
		startActivity(intent);
		finish();
	}

}
