package br.com.appcoral.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import br.com.appcoral.activitys.MainActivity;
import br.com.appcoral.dao.CoralistaDAO;
import br.com.appcoral.model.Coralista;

public class FileUtil {

	private static final String DIRETORIO_BKP = "/BKP_SONATA_CORAL";
	private static final String ARQUIVO_CORALISTAS = "/Coralistas.txt";
	private static final String ARQUIVO_QRCODES_CORALISTAS = "/QrCodesCoralistas.txt";

	public static void gerarCoralistasTXT(MainActivity context) {
		File folder = new File(Environment.getExternalStorageDirectory() + DIRETORIO_BKP);
		if (!folder.exists()) {
			folder.mkdir();
		}
		CoralistaDAO coralistaDAO = context.getDbConnections().getCoralistaDAO();
		List<Coralista> listaCoralistas = coralistaDAO.listaCoralistas(CoralistaDAO.STATUS_ATIVO);

		File pathArquivoCoralistas = new File(
				Environment.getExternalStorageDirectory() + DIRETORIO_BKP + ARQUIVO_CORALISTAS);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(pathArquivoCoralistas);
			for (Coralista coralista : listaCoralistas) {
				String idNomeCoralista = coralista.getIdCoralista() + "#" + coralista.getNome();
				fos.write(idNomeCoralista.getBytes());
				fos.write("\n".getBytes());
			}
			fos.flush();
			
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(pathArquivoCoralistas), "text/plain");
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			context.startActivity(intent);
			
		} catch (Exception e) {
		} finally {
			try {
				fos.close();
			} catch (IOException ioe) {
			}
		}
	}

	public static void gerarQrCodesCoralistasTxt(MainActivity context) {
		File folder = new File(Environment.getExternalStorageDirectory() + DIRETORIO_BKP);
		if (!folder.exists()) {
			folder.mkdir();
		}
		CoralistaDAO coralistaDAO = context.getDbConnections().getCoralistaDAO();
		List<Coralista> listaCoralistas = coralistaDAO.listaCoralistas(CoralistaDAO.STATUS_ATIVO);

		File pathArquivoQrCodeCoralistas = new File(
				Environment.getExternalStorageDirectory() + DIRETORIO_BKP + ARQUIVO_QRCODES_CORALISTAS);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(pathArquivoQrCodeCoralistas);
			for (Coralista coralista : listaCoralistas) {
				fos.write(coralista.getQrCode().getBytes());
				fos.write("\n".getBytes());
			}
			fos.flush();
			
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(pathArquivoQrCodeCoralistas), "text/plain");
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			context.startActivity(intent);
		} catch (Exception e) {
		} finally {
			try {
				fos.close();
			} catch (IOException ioe) {
			}
		}
		
	}
}
