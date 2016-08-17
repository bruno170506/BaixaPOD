package br.com.appcoral.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import br.com.appcoral.activitys.MainActivity;
import br.com.appcoral.dao.IDataBaseConstants;

public class BackupUtil {

	private static final String DIRETORIO_BKP = "/BKP_SONATA_CORAL";
	private static final String ARQUIVO_DE_BACKUP = "/bkp_sonata.db";

	public static void enviarBackup(MainActivity mainActivity) {

		criarDiretorioDeBackup();

		File databaseSQLitePath = mainActivity.getDatabasePath(IDataBaseConstants.DATABASE);
		File backupPath = new File(localDiretorioDeBackup() + ARQUIVO_DE_BACKUP);
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(databaseSQLitePath);
			fos = new FileOutputStream(backupPath);
			while (true) {
				int i = fis.read();
				if (i != -1) {
					fos.write(i);
				} else {
					break;
				}
			}
			fos.flush();
		} catch (Exception e) {
		} finally {
			try {
				fos.close();
				fis.close();
			} catch (IOException ioe) {
			}
		}

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_SUBJECT, "Backup App Sonata Coral");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "bruno170506@hotmail.com" });
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(localDiretorioDeBackup() + ARQUIVO_DE_BACKUP)));
		intent.setType("text/plain");
		mainActivity.startActivity(intent);// Intent.createChooser(intent,"Enviar Backup!"));
	}

	public static void restaurarBackup(MainActivity contexto) {
		criarDiretorioDeBackup();

		File databaseSQLitePath = contexto.getDatabasePath(IDataBaseConstants.DATABASE);
		File backupPath = new File(localDiretorioDeBackup() + ARQUIVO_DE_BACKUP);
		if(backupPath==null || !backupPath.exists()){
			return;
		}
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(backupPath);
			fos = new FileOutputStream(databaseSQLitePath);
			while (true) {
				int i = fis.read();
				if (i != -1) {
					fos.write(i);
				} else {
					break;
				}
			}
			fos.flush();
		} catch (Exception e) {
		} finally {
			try {
				fos.close();
				fis.close();
			} catch (IOException ioe) {
			}
		}
	}

	private static void criarDiretorioDeBackup() {
		File folder = new File(Environment.getExternalStorageDirectory() + DIRETORIO_BKP);
		if (!folder.exists()) {
			folder.mkdir();
		}
	}

	private static String localDiretorioDeBackup() {
		return Environment.getExternalStorageDirectory() + DIRETORIO_BKP;
	}

}
