package br.com.appcoral.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import android.os.Environment;
import br.com.appcoral.activitys.MainActivity;
import br.com.appcoral.dao.CoralistaDAO;
import br.com.appcoral.model.Coralista;

public class GeradorArquivoQRCodeCoralistasUtil {
	
	private MainActivity context;

	public GeradorArquivoQRCodeCoralistasUtil(MainActivity context){
		this.context = context; 
	}

	public void criarArquivoComQRCODES() {
		File folder = new File(Environment.getExternalStorageDirectory() + "/QR_CODE/");
		if (!folder.exists()) {
			folder.mkdir();
		}

		File pathFile = new File(Environment.getExternalStorageDirectory() + "/QR_CODE/qr_code.txt");
		FileWriter arquivoContendoQRCODES = null;
		try {
			arquivoContendoQRCODES = new FileWriter(pathFile, false);
			List<Coralista> listaCoralistas = context.getDbConnections().getCoralistaDAO().listaCoralistas(CoralistaDAO.STATUS_ATIVO);
			for (Coralista coralista : listaCoralistas) {
				String qrCode = coralista.getQrCode();
				String pularLinha = System.getProperty("line.separator");
				arquivoContendoQRCODES.write(pularLinha + qrCode + pularLinha);
				arquivoContendoQRCODES.flush();
			}
		} catch (Exception e) {
			DialogUtil.feedback(context, "Erro: " + e.getMessage());
		} finally {
			try {
				arquivoContendoQRCODES.close();
			} catch (IOException ioe) {
			}
		}
	}
}
