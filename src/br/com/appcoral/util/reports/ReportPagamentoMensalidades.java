package br.com.appcoral.util.reports;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import android.content.Intent;
import android.net.Uri;
import br.com.appcoral.activitys.MainActivity;
import br.com.appcoral.model.Coralista;
import br.com.appcoral.model.MensalidadePaga;
import br.com.appcoral.util.Moeda;

public class ReportPagamentoMensalidades extends ReportBase {

	public static void gerar(MainActivity context) {
		Document doc = null;
		OutputStream os = null;
		String pathDiretorioDosRelatorios = criarDiretorioParaRelatorios();
		try {
			doc = new Document(PageSize.A4, 10, 10, 10, 10);// 72, 72, 72, 72);
			String pathRelatorio = pathDiretorioDosRelatorios + "/pagamentoMensalidades.pdf";
			os = new FileOutputStream(pathRelatorio);
			PdfWriter.getInstance(doc, os);
			doc.open();

			Image figura = Image.getInstance(pathDiretorioDosRelatorios + "/sonatacoral.jpg");
			figura.setAlignment(Image.ALIGN_MIDDLE);
			doc.add(figura);

			
			BaseColor azul = baseColorAzul();
			BaseColor preto = baseColorPreto();
			doc.add(paragrafo("MENSALIDADES PAGAS",24, azul));

			List<Coralista> coralistas = context.getDbConnections().getCoralistaDAO().listaComTodosOsCoralistas();
			for (Coralista coralista : coralistas) {
				doc.add(paragrafo("--------------------------------------------------",22, preto));
				doc.add(paragrafo(coralista.getNome(),22, azul));
				List<MensalidadePaga> mensalidadePaga = context.getDbConnections().getMensalidadeDAO().mensalidadesPagasDoCoralista(coralista.getIdCoralista());
				for (MensalidadePaga m : mensalidadePaga) {
					doc.add(paragrafo(m.getMes()+ " "+Moeda.valorFormatado(m.getValorPago()),20, preto));
				}
			}
			
			
			File file = new File(pathRelatorio);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "application/pdf");
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			context.startActivity(intent);

		} catch (Exception e) {
		} finally {
			if (doc != null) {
				doc.close();
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}

		
	}

}
