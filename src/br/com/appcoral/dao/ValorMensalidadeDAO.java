package br.com.appcoral.dao;

import android.content.ContentValues;
import android.database.Cursor;
import br.com.appcoral.model.ValorMensalidade;

public class ValorMensalidadeDAO  {

	private DBConnections dbConnections;

	public ValorMensalidadeDAO(DBConnections dbConnections) {
		this.dbConnections = dbConnections;
	}
	
	public void definirValorMensalidade(ValorMensalidade valorMensalidade) {
		if(valorMensalidade.getIdValorMensalidade() == -1){
			ContentValues values = new ContentValues();
			values.put(IDataBaseConstants.VALOR_MENSALIDADE.VALOR_MENSALIDADE, valorMensalidade.getValorMensalidade());
			values.put(IDataBaseConstants.VALOR_MENSALIDADE.VALOR_ACRESCIMO, valorMensalidade.getValorAcrescimo());
			dbConnections.getWritableDatabase().insert(IDataBaseConstants.VALOR_MENSALIDADE.TABLE, null, values);
		}else{
			ContentValues values = new ContentValues();
			values.put(IDataBaseConstants.VALOR_MENSALIDADE.VALOR_MENSALIDADE, valorMensalidade.getValorMensalidade());
			values.put(IDataBaseConstants.VALOR_MENSALIDADE.VALOR_ACRESCIMO, valorMensalidade.getValorAcrescimo());
			String[] args = { valorMensalidade.getIdValorMensalidade().toString() };
			dbConnections.getWritableDatabase().update(IDataBaseConstants.VALOR_MENSALIDADE.TABLE, values, IDataBaseConstants.VALOR_MENSALIDADE.ID_VALOR_MENSALIDADE + "=?", args);
		}
	}
	
	public ValorMensalidade recuperaValorMensalidade() {
		ValorMensalidade valorMensalidade = null;
		String sql = "SELECT * FROM " + IDataBaseConstants.VALOR_MENSALIDADE.TABLE;
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			valorMensalidade = new ValorMensalidade();
			valorMensalidade.setIdValorMensalidade(c.getLong(c.getColumnIndex(IDataBaseConstants.VALOR_MENSALIDADE.ID_VALOR_MENSALIDADE))); 
			valorMensalidade.setValorMensalidade(c.getDouble(c.getColumnIndex(IDataBaseConstants.VALOR_MENSALIDADE.VALOR_MENSALIDADE)));
			valorMensalidade.setValorAcrescimo(c.getDouble(c.getColumnIndex(IDataBaseConstants.VALOR_MENSALIDADE.VALOR_ACRESCIMO)));
			return valorMensalidade;
			
		}
		return valorMensalidade;
	}

	


}
