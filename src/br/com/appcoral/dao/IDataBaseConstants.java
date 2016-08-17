package br.com.appcoral.dao;

public class IDataBaseConstants {

	public static final int VERSION = 1;
	public static String DATABASE = "DBAppCoral";

	/**
	 *	Tabela contendo os dados dos coralistas  
	 */
	public interface CORALISTAS {
		String TABLE = "coralistas";
		String ID_CORALISTA = "id_coralista";
		String NOME = "nome";
		String RG = "rg";
		String TELEFONE = "telefone";
		String EMAIL = "email";
		String DATA_NASCIMENTO = "dataNascimento";
		String NYPE_VOCAL = "nypeVocal";
		String QR_CODE = "qrcode";
		String STATUS_ATIVO = "statusAtivo";
	}

	/**
	 *	Tabela contendo os dados dos pagamentos de mensalidades dos coralistas  
	 */
	public interface MENSALIDADE_PAGA {
		String TABLE = "mensalidadePaga";
		String ID_MENSALIDADE = "idMensalidade";
		String FK_ID_CORALISTA = "idCoralista";
		String ANO = "ano";
		String MES = "mes";
		String VALOR_PAGO = "valorPago";
		String SITUACAO_PAGAMENTO = "situacaoPagamento";
		String DATA_PAGAMENTO = "dataPagamento";
	}
	
	/**
	 *	Tabela contendo o valor a ser cobrado de mensalidade e o valor de acrescimo por atraso  
	 */
	public interface VALOR_MENSALIDADE {
		String TABLE = "valorMensalidade";
		String ID_VALOR_MENSALIDADE = "idValorMensalidade";
		String VALOR_MENSALIDADE = "valorMensalidade";
		String VALOR_ACRESCIMO = "valorAcrescimo";
	}
	
	/**
	 *	Tabela contendo o fluxo de caixa
	 */
	public interface FLUXO_CAIXA {
		String TABLE = "fluxoCaixa";
		String ID_FLUXO_CAIXA = "idFluxoCaixa";
		String DESCRICAO = "descricao";
		String TIPO_FLUXO = "tipoFluxo";
		String VALOR = "valor";
	}
	

	/**
	 *	Tabela contendo as frequencias dos Coralistas  
	 */
	public interface CONTROLE_FREQUENCIA {
		String TABLE = "controleFrequencia";
		String ID_FREQUENCIA = "idFrequencia";
		String FK_ID_CORALISTA = "idCoralista";
		String DATA_HORA_FREQUENCIA = "dataHoraFrequencia";
	}

}
