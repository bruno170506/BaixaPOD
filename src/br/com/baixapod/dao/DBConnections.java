package br.com.baixapod.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnections extends SQLiteOpenHelper {

	public DBConnections(Context context) {
		super(context, IDataBaseConstants.DATABASE, null, IDataBaseConstants.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase dataBase) {
		// executado na criacao do banco;

		// MOVIMENTO
		dataBase.execSQL("CREATE TABLE " + //
				IDataBaseConstants.MOVIMENTO.TABLE + "(" + //
				IDataBaseConstants.MOVIMENTO.N_HAWB + " TEXT," + //
				IDataBaseConstants.MOVIMENTO.DT_ENTREGA + " TEXT," + //
				IDataBaseConstants.MOVIMENTO.HR_ENTREGA + " TEXT," + //
				IDataBaseConstants.MOVIMENTO.N_TENTATIVA + " TEXT," + //
				IDataBaseConstants.MOVIMENTO.OCORRENCIA + " TEXT,"+//
				IDataBaseConstants.MOVIMENTO.SUCESSO + " TEXT);");

		// OCORRENCIA
		dataBase.execSQL("CREATE TABLE " + //
				IDataBaseConstants.OCORRENCIA.TABLE + "(" + //
				IDataBaseConstants.OCORRENCIA.CODIGO + " TEXT," + //
				IDataBaseConstants.OCORRENCIA.DESCRICAO + " TEXT);");

		// PESSOA
		dataBase.execSQL("CREATE TABLE " + //
				IDataBaseConstants.PESSOA.TABLE + "(" + //
				IDataBaseConstants.PESSOA.USUARIO + " TEXT," + //
				IDataBaseConstants.PESSOA.SENHA + " TEXT," + //
				IDataBaseConstants.PESSOA.MATRICULA + " TEXT);");

		// PODs
		dataBase.execSQL("CREATE TABLE " + //
				IDataBaseConstants.PODS.TABLE + "(" + //
				IDataBaseConstants.PODS.N_HAWB + " TEXT," + //
				IDataBaseConstants.PODS.NOME_DESTI + " TEXT," + //
				IDataBaseConstants.PODS.RUA_DESTI + " TEXT," + //
				IDataBaseConstants.PODS.NUMERO_DESTI + " TEXT," + //
				IDataBaseConstants.PODS.BAIRRO_DESTI + " TEXT," + //
				IDataBaseConstants.PODS.CIDADE_DESTI + " TEXT," + //
				IDataBaseConstants.PODS.N_TENTATIVAS + " TEXT);");
		
		// MANTER_CONECTADO
		dataBase.execSQL("CREATE TABLE " + //
				IDataBaseConstants.USUARIO_CONECTADO.TABLE + "(" + //
				IDataBaseConstants.USUARIO_CONECTADO.USUARIO + " TEXT," + //
				IDataBaseConstants.USUARIO_CONECTADO.SENHA+ " TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS " + IDataBaseConstants.MOVIMENTO.TABLE);
		database.execSQL("DROP TABLE IF EXISTS " + IDataBaseConstants.OCORRENCIA.TABLE);
		database.execSQL("DROP TABLE IF EXISTS " + IDataBaseConstants.PESSOA.TABLE);
		database.execSQL("DROP TABLE IF EXISTS " + IDataBaseConstants.PODS.TABLE);
		database.execSQL("DROP TABLE IF EXISTS " + IDataBaseConstants.USUARIO_CONECTADO.TABLE);
		onCreate(database);
	}

}
