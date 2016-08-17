package br.com.appcoral.util.reports;

import java.io.File;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;

import android.os.Environment;

public class ReportBase {
	
	public static final String DIRETORIO_BKP = "/BKP_SONATA_CORAL";
	public static final String DIRETORIO_RELATORIOS = "/relatorios";

	public static Paragraph paragrafo(String titulo, int tamanhoFonte, BaseColor cor) {
		return new Paragraph(titulo, new Font(FontFamily.HELVETICA, tamanhoFonte, Font.BOLDITALIC, cor));
	}

	public static String criarDiretorioParaRelatorios() {
		File folderBkp = new File(Environment.getExternalStorageDirectory() + DIRETORIO_BKP);
		if (!folderBkp.exists()) {
			folderBkp.mkdir();
		}
		String pathRelatorios = Environment.getExternalStorageDirectory() + DIRETORIO_BKP + DIRETORIO_RELATORIOS;
		File folderRelatorios = new File(pathRelatorios);
		if (!folderRelatorios.exists()) {
			folderRelatorios.mkdir();
		}
		return pathRelatorios;
	}
	
	public static BaseColor baseColorAzul(){
		return new BaseColor(0, 0, 255);
	}
	public static BaseColor baseColorPreto(){
		return new BaseColor(0, 0, 0);
	}
	
}
