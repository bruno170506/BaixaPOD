package br.com.appcoral.activitys.helpers;

import android.widget.EditText;
import android.widget.RadioButton;
import br.com.appcoral.R;
import br.com.appcoral.activitys.FormularioFluxoCaixaActivity;
import br.com.appcoral.model.FluxoCaixa;
import br.com.appcoral.util.Moeda;

public class FormularioFluxoCaixaHelper {

	private EditText descricao;
	private EditText valor;
	private RadioButton radioButtonEntrada;
	private RadioButton radioButtonSaida;

	public FormularioFluxoCaixaHelper(FormularioFluxoCaixaActivity formularioFluxoCaixaActivity) {

		descricao = (EditText) formularioFluxoCaixaActivity.findViewById(R.id.descricao);
		valor = (EditText) formularioFluxoCaixaActivity.findViewById(R.id.valor);
		valor.addTextChangedListener(Moeda.insert(valor));

		radioButtonEntrada = (RadioButton) formularioFluxoCaixaActivity.findViewById(R.id.radiobutton_entrada);
		radioButtonSaida = (RadioButton) formularioFluxoCaixaActivity.findViewById(R.id.radiobutton_saida);
		
	}

	public void preencheFluxoCaixaNoFormulario(FluxoCaixa fluxoCaixa) {
		descricao.setText(fluxoCaixa.getDescricao());
		valor.setText(Moeda.valorFormatado(fluxoCaixa.getValor()));
		preencheTipoFluxoCaixa(fluxoCaixa.getTipoFluxo());
	}

	private void preencheTipoFluxoCaixa(String tipoFluxo) {
		if (tipoFluxo.equals(FluxoCaixa.FLUXO_TIPO_ENTRADA)) {
			radioButtonEntrada.setChecked(true);
			radioButtonSaida.setChecked(false);
		}else{
			radioButtonSaida.setChecked(true);
			radioButtonEntrada.setChecked(false);
		}
	}

	public FluxoCaixa recuperaFluxoCaixaDoFormulario() {
		FluxoCaixa fluxoCaixa = new FluxoCaixa();
		fluxoCaixa.setDescricao(descricao.getText().toString());
		fluxoCaixa.setTipoFluxo(recuperaTipoFluxoCaixaDoFormulario());
		fluxoCaixa.setValor(Moeda.getValor(valor.getText().toString()));
		return fluxoCaixa;
	}

	private String recuperaTipoFluxoCaixaDoFormulario() {
		if (radioButtonEntrada.isChecked()) {
			return FluxoCaixa.FLUXO_TIPO_ENTRADA;
		}else{
			return FluxoCaixa.FLUXO_TIPO_SAIDA;
		}
	}

}
