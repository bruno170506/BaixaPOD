package br.com.appcoral.util;

import java.text.NumberFormat;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Moeda {

	public static TextWatcher insert(final EditText ediTxt) {
		return new TextWatcher() {
			private boolean isUpdating = false;
			// Pega a formatacao do sistema, se for brasil R$ se EUA US$
			private NumberFormat nf = NumberFormat.getCurrencyInstance();
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int after) {
				// Evita que o método seja executado varias vezes.
				// Se tirar ele entre em loop
				if (isUpdating) {
					isUpdating = false;
					return;
				}
				isUpdating = true;
				String str = s.toString();
				// Verifica se já existe a máscara no texto.
				boolean hasMask = ((str.indexOf("R$") > -1 || str.indexOf("$") > -1)
						&& (str.indexOf(".") > -1 || str.indexOf(",") > -1));
				// Verificamos se existe máscara
				if (hasMask) {
					// Retiramos a máscara.
					str = str.replaceAll("[R$]", "").replaceAll("[,]", "").replaceAll("[.]", "");
				}
				try {
					// Transformamos o número que está escrito no EditText em
					// monetário.
					str = nf.format(Double.parseDouble(str) / 100);
					ediTxt.setText(str);
					ediTxt.setSelection(ediTxt.getText().length());
				} catch (NumberFormatException e) {
					s = "";
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		};
	}

	public static CharSequence valorFormatado(Double valor) {
		String valorFormatado = NumberFormat.getCurrencyInstance().format(valor);
		if(valor<0){
			valorFormatado = valorFormatado.replace("(","");
			valorFormatado = valorFormatado.replace(")","");
			valorFormatado = valorFormatado.replace("R$", "R$-");
			return valorFormatado;
		}
		return valorFormatado;
	}

	public static Double getValor(String valor) {
		String result = valor.replace("R$", "");
		result = result.replace(".", "");
		result = result.replace(",", ".");
		return Double.parseDouble(result);
	}
}
