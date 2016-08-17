package br.com.appcoral.activitys.helpers;

import android.widget.EditText;
import android.widget.RadioButton;
import br.com.appcoral.R;
import br.com.appcoral.activitys.FormularioCoralistaActivity;
import br.com.appcoral.model.Coralista;
import br.com.appcoral.util.Mask;

public class FormularioCoralistaHelper {

	private static final String ID_NYPE_SOPRANO = "1";
	private static final String ID_NYPE_CONTRALTO = "2";
	private static final String ID_NYPE_TENOR = "3";
	private static final String ID_NYPE_BAIXO = "4";
	private EditText nome;
	private EditText rg;
	private EditText telefone;
	private EditText email;
	private EditText dataNascimento;
	private RadioButton radioButtonSoprano;
	private RadioButton radioButtonContralto;
	private RadioButton radioButtonTenor;
	private RadioButton radioButtonBaixo;

	public FormularioCoralistaHelper(FormularioCoralistaActivity formularioCoralistaActivity) {

		nome = (EditText) formularioCoralistaActivity.findViewById(R.id.nome);
		rg = (EditText) formularioCoralistaActivity.findViewById(R.id.rg);
		telefone = (EditText) formularioCoralistaActivity.findViewById(R.id.telefone);
		email = (EditText) formularioCoralistaActivity.findViewById(R.id.email);
		dataNascimento = (EditText) formularioCoralistaActivity.findViewById(R.id.dataNascimento);
		radioButtonSoprano = (RadioButton) formularioCoralistaActivity.findViewById(R.id.nype_soprano);
		radioButtonContralto = (RadioButton) formularioCoralistaActivity.findViewById(R.id.nype_contralto);
		radioButtonTenor = (RadioButton) formularioCoralistaActivity.findViewById(R.id.nype_tenor);
		radioButtonBaixo = (RadioButton) formularioCoralistaActivity.findViewById(R.id.nype_baixo);
		
		telefone.addTextChangedListener(Mask.insert("(##)####-####", telefone));
		dataNascimento.addTextChangedListener(Mask.insert("##/##/####", dataNascimento));
		
	}

	public void preencheCoralistaNoFormulario(Coralista coralista) {
		nome.setText(coralista.getNome());
		rg.setText(coralista.getRg());
		telefone.setText(coralista.getTelefone());
		email.setText(coralista.getEmail());
		dataNascimento.setText(coralista.getDataNascimento());
		preencheNypeVocal(coralista.getNypeVocal());
	}

	private void preencheNypeVocal(String nypeVocal) {
		if (nypeVocal.equals(ID_NYPE_SOPRANO)) {
			radioButtonSoprano.setChecked(true);
			radioButtonContralto.setChecked(false);
			radioButtonTenor.setChecked(false);
			radioButtonBaixo.setChecked(false);
		}
		if (nypeVocal.equals(ID_NYPE_CONTRALTO)) {
			radioButtonSoprano.setChecked(false);
			radioButtonContralto.setChecked(true);
			radioButtonTenor.setChecked(false);
			radioButtonBaixo.setChecked(false);
		}
		if (nypeVocal.equals(ID_NYPE_TENOR)) {
			radioButtonSoprano.setChecked(false);
			radioButtonContralto.setChecked(false);
			radioButtonTenor.setChecked(true);
			radioButtonBaixo.setChecked(false);
		}
		if (nypeVocal.equals(ID_NYPE_BAIXO)) {
			radioButtonSoprano.setChecked(false);
			radioButtonContralto.setChecked(false);
			radioButtonTenor.setChecked(false);
			radioButtonBaixo.setChecked(true);
		}

	}

	public Coralista recuperaCoralistaDoFormulario() {
		Coralista coralista = new Coralista();
		coralista.setNome(nome.getText().toString());
		coralista.setRg(rg.getText().toString());
		coralista.setTelefone(telefone.getText().toString());
		coralista.setEmail(email.getText().toString());
		coralista.setDataNascimento(dataNascimento.getText().toString());
		coralista.setNypeVocal(recuperaNypeVocalDoFormulario());
		return coralista;
	}

	private String recuperaNypeVocalDoFormulario() {
		if (radioButtonSoprano.isChecked()) {
			return ID_NYPE_SOPRANO;
		}
		if (radioButtonContralto.isChecked()) {
			return ID_NYPE_CONTRALTO;
		}
		if (radioButtonTenor.isChecked()) {
			return ID_NYPE_TENOR;
		}
		if (radioButtonBaixo.isChecked()) {
			return ID_NYPE_BAIXO;
		}
		return "";
	}

}
