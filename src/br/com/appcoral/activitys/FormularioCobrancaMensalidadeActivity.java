package br.com.appcoral.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import br.com.appcoral.R;
import br.com.appcoral.dao.MensalidadeDAO;
import br.com.appcoral.dto.MensalidadeDTO;
import br.com.appcoral.model.Coralista;
import br.com.appcoral.model.MensalidadePaga;
import br.com.appcoral.model.ValorMensalidade;
import br.com.appcoral.util.DataUtil;
import br.com.appcoral.util.DialogUtil;
import br.com.appcoral.util.Moeda;

public class FormularioCobrancaMensalidadeActivity extends MainActivity {

	private TextView coralistaTextView;
	private TextView mesAnoTextView;
	private EditText valorPagoEditText;
	private EditText valorAPagarEditText;
	private RadioButton radioButtonSemAcrescimo;
	private RadioButton radioButtonComAcrescimo;
	private Button btnConfirmacao;
	private String anoSelecionado;
	private String mesSelecionado;
	private Coralista coralistaSelecionado;
	private MensalidadeDAO mensalidadeDAO;
	private LinearLayout linearLayoutValorPago;
	private ValorMensalidade valorCobrado;
	private MensalidadePaga mensalidadePaga;
	private CheckBox descontoTerceiroFamiliar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario_cobranca_mensalidade);

		mensalidadeDAO = getDbConnections().getMensalidadeDAO();
		
		valorCobrado = getDbConnections().getValorMensalidadeDAO().recuperaValorMensalidade();
		
		coralistaTextView = (TextView) findViewById(R.id.cobranca_mensalidade_coralista);
		mesAnoTextView = (TextView) findViewById(R.id.cobranca_mensalidade_mesreferencia);
		valorPagoEditText = (EditText) findViewById(R.id.cobranca_mensalidade_valorPago);
		valorAPagarEditText = (EditText) findViewById(R.id.cobranca_mensalidade_valorAPagar);

		radioButtonSemAcrescimo = (RadioButton) findViewById(R.id.radiobutton_semAcrescimo);
		radioButtonSemAcrescimo.setChecked(true);
		radioButtonComAcrescimo = (RadioButton) findViewById(R.id.radiobutton_comAcrescimo);
		radioButtonComAcrescimo.setChecked(false);

		btnConfirmacao = (Button) findViewById(R.id.btnConfirmacaoMensalidade);

		Bundle extras = getIntent().getExtras();
		if (extras != null && extras.getSerializable(ListagemMensalidadeActivity.MENSALIDADE_DTO) != null) {
			MensalidadeDTO mensalidadeDTO = (MensalidadeDTO) extras
					.getSerializable(ListagemMensalidadeActivity.MENSALIDADE_DTO);
			mesSelecionado = mensalidadeDTO.getItem().getMes();
			anoSelecionado = mensalidadeDTO.getAno();
			coralistaSelecionado = mensalidadeDTO.getCoralista();
			coralistaTextView.setText(coralistaSelecionado.getNome());
			mesAnoTextView.setText(mesSelecionado + "/" + anoSelecionado);
			valorAPagarEditText.setText(Moeda.valorFormatado(mensalidadeDTO.getValor()));
			valorAPagarEditText.addTextChangedListener(Moeda.insert(valorAPagarEditText));

			linearLayoutValorPago = (LinearLayout) findViewById(R.id.layoutValorPago);

			mensalidadePaga = mensalidadeDAO.mensalidadePaga(coralistaSelecionado.getIdCoralista(),
					anoSelecionado, mesSelecionado);

			String situacaoPagamento = mensalidadeDTO.getSituacaoPagamento();
			if (situacaoPagamento != null && situacaoPagamento.equals(MensalidadeDAO.MENSALIDADE_PAGA_PARCIALMENTE)) {
				valorPagoEditText.setText(Moeda.valorFormatado(mensalidadePaga.getValorPago()));
				valorPagoEditText.setEnabled(false);
				linearLayoutValorPago.setVisibility(View.VISIBLE);
				valorAPagarEditText.setText(Moeda.valorFormatado(valorAPagar(false)-mensalidadePaga.getValorPago()));
			} else {
				linearLayoutValorPago.setVisibility(View.INVISIBLE);
				valorAPagarEditText.setText(Moeda.valorFormatado(valorCobrado.getValorMensalidade()));
			}

			btnConfirmacao.setOnClickListener(listnerConfirmarPagamento());
		}
		
		descontoTerceiroFamiliar = (CheckBox) findViewById(R.id.checkbox_descontoTerceiro);
		descontoTerceiroFamiliar.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				double valorMensalidade = getDbConnections().getValorMensalidadeDAO().recuperaValorMensalidade().getValorMensalidade();
				if(isChecked){
					valorCobrado.setValorMensalidade(valorMensalidade/2);
				}else{
					valorCobrado.setValorMensalidade(valorMensalidade);
				}
				radioButtonSemAcrescimo.setChecked(true);
				radioButtonComAcrescimo.setChecked(false);
				
				if(mensalidadePaga!=null){
					valorAPagarEditText.setText(Moeda.valorFormatado(valorAPagar(false)-mensalidadePaga.getValorPago()));
				}else{
					valorAPagarEditText.setText(Moeda.valorFormatado(valorAPagar(false)));
				}
			}
		});
		
		radioButtonSemAcrescimo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				radioButtonComAcrescimo.setChecked(false);
				if(mensalidadePaga!=null){
					valorAPagarEditText.setText(Moeda.valorFormatado(valorAPagar(false)-mensalidadePaga.getValorPago()));
				}else{
					valorAPagarEditText.setText(Moeda.valorFormatado(valorAPagar(false)));
				}
			}
		});
		radioButtonComAcrescimo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				radioButtonSemAcrescimo.setChecked(false);
				if(mensalidadePaga!=null){
					valorAPagarEditText.setText(Moeda.valorFormatado(valorAPagar(true)-mensalidadePaga.getValorPago()));
				}else{
					valorAPagarEditText.setText(Moeda.valorFormatado(valorAPagar(true)));
				}
			}
		});
	}

	private double valorAPagar(boolean comAcrescimo) {
		if(comAcrescimo){
			return valorCobrado.getValorMensalidade()+valorCobrado.getValorAcrescimo();
		}
		return valorCobrado.getValorMensalidade();
	}

	private OnClickListener listnerConfirmarPagamento() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Cobrar Mensalidade
				MensalidadePaga mensalidadePaga = getDbConnections().getMensalidadeDAO()
						.mensalidadePaga(coralistaSelecionado.getIdCoralista(), anoSelecionado, mesSelecionado);
				if (mensalidadePaga == null) {
					mensalidadePaga = new MensalidadePaga();
				}
				mensalidadePaga.setIdCoralista(coralistaSelecionado.getIdCoralista());
				mensalidadePaga.setAno(anoSelecionado);
				mensalidadePaga.setMes(mesSelecionado);
				mensalidadePaga.setDescontoTerceiroFamiliar(descontoTerceiroFamiliar.isChecked());

				Double valorAPagar = Moeda.getValor(valorAPagarEditText.getText().toString());
				if (linearLayoutValorPago.getVisibility() == View.VISIBLE) {
					Double valorPago = Moeda.getValor(valorPagoEditText.getText().toString());
					mensalidadePaga.setValorPago(
							Moeda.getValor(String.valueOf(Moeda.valorFormatado(valorPago + valorAPagar))));
				} else {
					mensalidadePaga.setValorPago(valorAPagar);
				}
				mensalidadePaga.setDataPagamento(DataUtil.dataComHoraAtual());

				mensalidadeDAO.cobrarMensalidade(mensalidadePaga);
				DialogUtil.feedback(FormularioCobrancaMensalidadeActivity.this, "Pagamento Confirmado");
				irParaListaMensalidade();
			}
		};
	}

	@Override
	public void onBackPressed() {
		irParaListaMensalidade();
	}

	private void irParaListaMensalidade() {
		Intent irParaAListagemDeMensalidade = new Intent(FormularioCobrancaMensalidadeActivity.this,
				ListagemMensalidadeActivity.class);
		irParaAListagemDeMensalidade.putExtra(ListagemCoralistaActivity.CORALISTA_SELECIONADO, coralistaSelecionado);
		startActivity(irParaAListagemDeMensalidade);
		finish();
	}

}
