package br.com.appcoral.util.reports;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import br.com.appcoral.activitys.MainActivity;
import br.com.appcoral.dao.CoralistaDAO;
import br.com.appcoral.model.ControleFrequencia;
import br.com.appcoral.model.Coralista;

public class RelatorioUtil extends ReportBase {

	private static final String[] DESTINATARIOS = new String[] { "bruno170506@hotmail.com","sonatacoral@gmail.com" };
	private MainActivity context;

	public RelatorioUtil(MainActivity context) {
		this.context = context;
	}

	public void enviarRelatorioFrequenciaPorEmail() {

		StringBuilder text = new StringBuilder();
		text.append("Registros de Frequências:");
		text.append("\n\n");

		List<Coralista> listaCoralistas = context.getDbConnections().getCoralistaDAO()
				.listaCoralistas(CoralistaDAO.STATUS_ATIVO);
		for (Coralista coralista : listaCoralistas) {
			Long idCoralista = coralista.getIdCoralista();
			List<ControleFrequencia> frequencias = context.getDbConnections().getControleFrequenciaDAO()
					.frequenciasDoCoralista(idCoralista);
			text.append(coralista.getNome() + "\n");

			Set<String> datasDasChamadasRealizadas = context.getDbConnections().getControleFrequenciaDAO()
					.datasDasChamadasRealizadas();
			for (String dataChamadaRealizada : datasDasChamadasRealizadas) {
				if (!context.getDbConnections().getControleFrequenciaDAO().coralistaTeveFrequenciaNaData(idCoralista,
						dataChamadaRealizada)) {
					text.append("		 - " + dataChamadaRealizada + " - FALTA\n");
				}
			}
			for (ControleFrequencia f : frequencias) {
				text.append("		 - " + f.getDataHoraFrequencia() + "\n");
			}
		}

		enviarRelatorioPorEmail("Relatório De Frequências Sonata Coral", text.toString(), context);
	}

	@SuppressLint("InlinedApi")
	private static void enviarRelatorioPorEmail(String titulo, String conteudo, MainActivity context) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_SUBJECT, titulo);
		intent.putExtra(Intent.EXTRA_EMAIL, DESTINATARIOS);
		intent.putExtra(Intent.EXTRA_TEXT, conteudo);
		intent.setType("text/plain");
		context.startActivity(intent);
	}

	public static void enviarRelatorioCadastroCoralistas(MainActivity context) {
		StringBuilder text = new StringBuilder();
		text.append("Cadastro de Coralistas:");
		text.append("\n\n");

		List<Coralista> listaCoralistas = context.getDbConnections().getCoralistaDAO()
				.listaCoralistas(CoralistaDAO.STATUS_ATIVO);
		text.append("NOME | RG | TELEFONE | EMAIL | DATA_NASCIMENTO | NYPE" + "\n\n");
		for (Coralista c : listaCoralistas) {
			text.append(c.getNome() + " | " + c.getRg() + " | " + c.getTelefone() + " | " + c.getEmail() + " | "
					+ c.getDataNascimento() + " | " + c.getNypeVocalPorExtenso() + " | " + "\n");
		}

		enviarRelatorioPorEmail("Relatório Cadastro de Coralistas Sonata Coral", text.toString(), context);

	}

	public static void enviarRelatorioFluxoCaixa(MainActivity context) {
		ReportFluxoCaixa.gerar(context);
	}

	public static void enviarRelatorioPagamentoMensalidades(MainActivity context) {
		ReportPagamentoMensalidades.gerar(context);
	}

	@SuppressLint("InlinedApi")
	public static void gerarRelatorioCadastroCoralistas(MainActivity context) {
		ReportCadastroCoralistas.gerar(context);
	}

	public static void enviarRelatoriosPorEmail(MainActivity context) {

		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND_MULTIPLE);
		intent.putExtra(Intent.EXTRA_SUBJECT, "Relatórios Sonata Coral");
		intent.putExtra(Intent.EXTRA_EMAIL, DESTINATARIOS);
		intent.setType("text/plain");

		String pathRelatorios = Environment.getExternalStorageDirectory() + DIRETORIO_BKP + DIRETORIO_RELATORIOS;
		File folderRelatorios = new File(pathRelatorios);
		File[] relatorios = folderRelatorios.listFiles();
		ArrayList<Uri> files = new ArrayList<Uri>();
		for (File file : relatorios) {
			if(file.getName().contains(".pdf")||file.getName().contains(".txt")){
				Uri uri = Uri.fromFile(file);
				files.add(uri);
			}
		}
		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
		context.startActivity(intent);
	}

}
