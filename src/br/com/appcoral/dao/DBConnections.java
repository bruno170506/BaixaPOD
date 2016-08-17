package br.com.appcoral.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnections extends SQLiteOpenHelper {

	private ValorMensalidadeDAO valorMensalidadeDAO;
	private MensalidadeDAO mensalidadeDAO;
	private CoralistaDAO coralistaDAO;
	private FLuxoCaixaDAO fluxoCaixaDAO;
	private ControleFrequenciaDAO controleFrequenciaDAO;

	public DBConnections(Context context) {
		super(context, IDataBaseConstants.DATABASE, null, IDataBaseConstants.VERSION);
		valorMensalidadeDAO = new ValorMensalidadeDAO(this);
		mensalidadeDAO = new MensalidadeDAO(this);
		coralistaDAO = new CoralistaDAO(this);
		fluxoCaixaDAO = new FLuxoCaixaDAO(this);
		controleFrequenciaDAO = new ControleFrequenciaDAO(this);
	}

	public ValorMensalidadeDAO getValorMensalidadeDAO() {
		return valorMensalidadeDAO;
	}

	public MensalidadeDAO getMensalidadeDAO() {
		return mensalidadeDAO;
	}

	public CoralistaDAO getCoralistaDAO() {
		return coralistaDAO;
	}

	public FLuxoCaixaDAO getFluxoCaixaDAO() {
		return fluxoCaixaDAO;
	}

	public ControleFrequenciaDAO getControleFrequenciaDAO() {
		return controleFrequenciaDAO;
	}
	
	@Override
	public void onCreate(SQLiteDatabase dataBase) {
		// executado na criação do banco;

		// CORALISTAS
		String sqlTabelaCoralista = "CREATE TABLE " + //
				IDataBaseConstants.CORALISTAS.TABLE + "(" + //
				IDataBaseConstants.CORALISTAS.ID_CORALISTA + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + //
				IDataBaseConstants.CORALISTAS.NOME + " TEXT UNIQUE NOT NULL," + //
				IDataBaseConstants.CORALISTAS.RG + " TEXT," + //
				IDataBaseConstants.CORALISTAS.TELEFONE + " TEXT," + //
				IDataBaseConstants.CORALISTAS.EMAIL + " TEXT," + //
				IDataBaseConstants.CORALISTAS.DATA_NASCIMENTO + " TEXT," + //
				IDataBaseConstants.CORALISTAS.NYPE_VOCAL + " TEXT," + //
				IDataBaseConstants.CORALISTAS.QR_CODE + " TEXT," + //
				IDataBaseConstants.CORALISTAS.STATUS_ATIVO + " TEXT" + //
				");";
		dataBase.execSQL(sqlTabelaCoralista);

		// MENSALIDADES
		String sqlTabelaMensalidadespagas = "CREATE TABLE " + //
				IDataBaseConstants.MENSALIDADE_PAGA.TABLE + "(" + //
				IDataBaseConstants.MENSALIDADE_PAGA.ID_MENSALIDADE + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + //
				IDataBaseConstants.MENSALIDADE_PAGA.FK_ID_CORALISTA + " INTEGER," + //
				IDataBaseConstants.MENSALIDADE_PAGA.ANO + " TEXT," + //
				IDataBaseConstants.MENSALIDADE_PAGA.MES + " TEXT," + //
				IDataBaseConstants.MENSALIDADE_PAGA.VALOR_PAGO + " REAL," + //
				IDataBaseConstants.MENSALIDADE_PAGA.SITUACAO_PAGAMENTO + " TEXT," + //
				IDataBaseConstants.MENSALIDADE_PAGA.DATA_PAGAMENTO + " TEXT" + //
				");";
		dataBase.execSQL(sqlTabelaMensalidadespagas);

		// VALOR_MENSALIDADE
		String sqlTabelaValorMensalidade = "CREATE TABLE " + //
				IDataBaseConstants.VALOR_MENSALIDADE.TABLE + "(" + //
				IDataBaseConstants.VALOR_MENSALIDADE.ID_VALOR_MENSALIDADE
				+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + //
				IDataBaseConstants.VALOR_MENSALIDADE.VALOR_MENSALIDADE + " REAL," + //
				IDataBaseConstants.VALOR_MENSALIDADE.VALOR_ACRESCIMO + " REAL" + //
				");";
		dataBase.execSQL(sqlTabelaValorMensalidade);

		// FLUXO_CAIXA
		String sqlTabelaFluxoCaixa = "CREATE TABLE " + //
				IDataBaseConstants.FLUXO_CAIXA.TABLE + "(" + //
				IDataBaseConstants.FLUXO_CAIXA.ID_FLUXO_CAIXA + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + //
				IDataBaseConstants.FLUXO_CAIXA.DESCRICAO + " TEXT," + //
				IDataBaseConstants.FLUXO_CAIXA.TIPO_FLUXO + " TEXT," + //
				IDataBaseConstants.FLUXO_CAIXA.VALOR + " REAL" + //
				");";
		dataBase.execSQL(sqlTabelaFluxoCaixa);

		// CONTROLE FREQUENCIA
		String sqlTabelaControleFrequencia = "CREATE TABLE " + //
				IDataBaseConstants.CONTROLE_FREQUENCIA.TABLE + "(" + //
				IDataBaseConstants.CONTROLE_FREQUENCIA.ID_FREQUENCIA + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + //
				IDataBaseConstants.CONTROLE_FREQUENCIA.FK_ID_CORALISTA + " INTEGER," + //
				IDataBaseConstants.CONTROLE_FREQUENCIA.DATA_HORA_FREQUENCIA + " TEXT" + //
				");";
		dataBase.execSQL(sqlTabelaControleFrequencia);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// executado na alteração da estrutura do banco.

		String sqlTabelaValorMensalidade = "DROP TABLE IF EXISTS " + IDataBaseConstants.VALOR_MENSALIDADE.TABLE;
		database.execSQL(sqlTabelaValorMensalidade);

		String sqlTabelaCoralistas = "DROP TABLE IF EXISTS " + IDataBaseConstants.MENSALIDADE_PAGA.TABLE;
		database.execSQL(sqlTabelaCoralistas);

		String sqlTabelaMensalidadespagas = "DROP TABLE IF EXISTS " + IDataBaseConstants.CORALISTAS.TABLE;
		database.execSQL(sqlTabelaMensalidadespagas);

		String sqlTabelaFluxoCaixa = "DROP TABLE IF EXISTS " + IDataBaseConstants.FLUXO_CAIXA.TABLE;
		database.execSQL(sqlTabelaFluxoCaixa);
		
		String sqlTabelaControleFrequencia = "DROP TABLE IF EXISTS " + IDataBaseConstants.CONTROLE_FREQUENCIA.TABLE;
		database.execSQL(sqlTabelaControleFrequencia);
	       
		onCreate(database);

	}

}
