package br.com.baixapod.dao;

public class IDataBaseConstants {

	public static final int VERSION = 9;
	public static String DATABASE = "DBBaixaPOD";

	/**
	 * Tabela contendo todas os movimentos ainda não enviados
	 */
	public interface MOVIMENTO {
		String TABLE = "MOVIMENTO";
		String DT_ENTREGA = "dtEntrega";
		String HR_ENTREGA = "hrEntrega";
		String OCORRENCIA = "ocorrencia";
		String N_TENTATIVA = "nTentativa";
		String N_HAWB = "nHawb";
		String SUCESSO = "sucesso"; // Sucesso ao realizar a baixa Sim(1) ou Nao(0)//
		String LONGITUDE = "longitude";
		String LATITUDE = "latitude";
		String OBSERVACAO = "observacao";	
	}

	/**
	 * Tabela contendo pessoa
	 */
	public interface PESSOA {
		String TABLE = "PESSOA";
		String USUARIO = "usuario";
		String SENHA = "senha";
		String MATRICULA = "matricula";
	}

	/**
	 * Tabela contendo motivos da ocorrencia
	 */
	public interface OCORRENCIA {
		String TABLE = "OCORRENCIA";
		String CODIGO = "codigo";
		String DESCRICAO = "descricao";
	}

	/**
	 * Tabela contendo os PODS a entregar (PODs da Lista)
	 */
	public interface PODS {
		String TABLE = "PODS";
		String N_HAWB = "n_hawb";
		String NOME_DESTI = "nome_desti";
		String RUA_DESTI = "rua_desti";
		String NUMERO_DESTI = "numero_desti";
		String BAIRRO_DESTI = "bairro_desti";
		String CIDADE_DESTI = "cidade_desti";
		String N_TENTATIVAS = "n_tentativas";
	}

	public interface USUARIO_CONECTADO {
		String TABLE = "usuario_conectado";
		String USUARIO = "usuario";
		String SENHA = "senha";
	}
}
