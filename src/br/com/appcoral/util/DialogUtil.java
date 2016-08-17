package br.com.appcoral.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.Toast;

public class DialogUtil {

	public static void confirmacao(Context mainActivity, String titulo, String mensagem, final AndroidCallback callback) {

		AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
		builder.setTitle(titulo);
		builder.setMessage(mensagem);
		
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				callback.callbackResult(Boolean.TRUE);
			}
		});
		builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				callback.callbackResult(Boolean.FALSE);
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	public static void feedback(Context context, String mensagem) {
		Toast toast = Toast.makeText(context, mensagem, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}