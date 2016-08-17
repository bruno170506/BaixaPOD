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
import br.com.appcoral.model.FluxoCaixa;
import br.com.appcoral.util.Moeda;

public class ReportFluxoCaixa extends ReportBase {

	public static void gerar(MainActivity context) {
		Document doc = null;
		OutputStream os = null;
		String pathDiretorioDosRelatorios = criarDiretorioParaRelatorios();
		try {
			doc = new Document(PageSize.A4, 10, 10, 10, 10);// 72, 72, 72, 72);
			String pathRelatorio = pathDiretorioDosRelatorios + "/fluxoCaixa.pdf";
			os = new FileOutputStream(pathRelatorio);
			PdfWriter.getInstance(doc, os);
			doc.open();

			Image figura = Image.getInstance(pathDiretorioDosRelatorios + "/sonatacoral.jpg");
			figura.setAlignment(Image.ALIGN_MIDDLE);
			doc.add(figura);

			List<FluxoCaixa> listaFluxoCaixa = context.getDbConnections().getFluxoCaixaDAO().listaFluxoCaixa();
			CharSequence totalEntradas = Moeda.valorFormatado(context.getDbConnections().getFluxoCaixaDAO().totalDeEntradas());
			CharSequence totalSaidas = Moeda.valorFormatado(context.getDbConnections().getFluxoCaixaDAO().totalDeSaidas());
			CharSequence saldoFC = Moeda.valorFormatado(context.getDbConnections().getFluxoCaixaDAO().saldoFluxoCaixa());
			
			BaseColor preto = baseColorPreto();
			BaseColor azul = baseColorAzul();
			
			doc.add(paragrafo("FLUXO DE CAIXA SONATA CORAL",24, azul));
			doc.add(paragrafo("Total Entradas: "+totalEntradas,22, preto));
			doc.add(paragrafo("Total Saídas: "+totalSaidas,22, preto));
			doc.add(paragrafo("Saldo: "+saldoFC,22,preto));
			doc.add(paragrafo("",20, new BaseColor(0, 0, 255)));
			
			doc.add(paragrafo("--------------------------------------------------",20, preto));
			doc.add(paragrafo("ENTRADAS: ",22, azul));
			doc.add(paragrafo("--------------------------------------------------",20, preto));
			for (FluxoCaixa fc : listaFluxoCaixa) {
				if(fc.getTipoFluxo().equals(FluxoCaixa.FLUXO_TIPO_ENTRADA)){
					doc.add(paragrafo("Descrição: "+fc.getDescricao(),20, preto));
					doc.add(paragrafo("Valor: "+Moeda.valorFormatado(fc.getValor()),20, preto));
					doc.add(paragrafo("",20, new BaseColor(0, 0, 255)));
					doc.add(paragrafo("----",20, preto));
				}
			}
			doc.add(paragrafo("--------------------------------------------------",20, preto));
			doc.add(paragrafo("SAÍDAS: ",22, azul));
			doc.add(paragrafo("--------------------------------------------------",20, preto));
			for (FluxoCaixa fc : listaFluxoCaixa) {
				if(fc.getTipoFluxo().equals(FluxoCaixa.FLUXO_TIPO_SAIDA)){
					doc.add(paragrafo("Descrição: "+fc.getDescricao(),20, preto));
					doc.add(paragrafo("Valor: "+Moeda.valorFormatado(fc.getValor()),20, preto));
					doc.add(paragrafo("----",20, preto));
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
