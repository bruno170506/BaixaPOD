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
import br.com.appcoral.dao.CoralistaDAO;
import br.com.appcoral.model.Coralista;

public class ReportCadastroCoralistas extends ReportBase{

	public static void gerar(MainActivity context) {
		Document doc = null;
		OutputStream os = null;
		String pathDiretorioDosRelatorios = criarDiretorioParaRelatorios();
		try {
			doc = new Document(PageSize.A4, 10, 10, 10, 10);// 72, 72, 72, 72);
			String pathRelatorio = pathDiretorioDosRelatorios + "/cadastroCoralistas.pdf";
			os = new FileOutputStream(pathRelatorio);
			PdfWriter.getInstance(doc, os);
			doc.open();

			Image figura = Image.getInstance(pathDiretorioDosRelatorios + "/sonatacoral.jpg");
			figura.setAlignment(Image.ALIGN_MIDDLE);
			doc.add(figura);

			List<Coralista> ativos = context.getDbConnections().getCoralistaDAO()
					.listaCoralistas(CoralistaDAO.STATUS_ATIVO);
			doc.add(paragrafo("Cadastro de Coralistas",24, new BaseColor(0, 0, 255)));
			int numRegistro = 0;
			for (Coralista coralista : ativos) {
				numRegistro++;
				int tamanhoFonte = 20;
				doc.add(paragrafo("--------------------------------------------------",24, new BaseColor(0, 0, 255)));
				doc.add(paragrafo("Nome: " + coralista.getNome(),tamanhoFonte, new BaseColor(0, 0, 0)));
				doc.add(paragrafo("Email: " + coralista.getEmail(),tamanhoFonte, new BaseColor(0, 0, 0)));
				doc.add(paragrafo("Data Nasc: " + coralista.getDataNascimento(),tamanhoFonte, new BaseColor(0, 0, 0)));
				doc.add(paragrafo("Rg: " + coralista.getRg(),tamanhoFonte, new BaseColor(0, 0, 0)));
				doc.add(paragrafo("Telefone: " + coralista.getTelefone(),tamanhoFonte, new BaseColor(0, 0, 0)));
				doc.add(paragrafo("Nype: " + coralista.getNypeVocalPorExtenso(),tamanhoFonte, new BaseColor(0, 0, 0)));
				if(numRegistro==3){
					doc.newPage();
					numRegistro=0;
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
