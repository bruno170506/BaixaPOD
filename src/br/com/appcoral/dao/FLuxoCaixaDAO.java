package br.com.appcoral.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import br.com.appcoral.model.FluxoCaixa;

public class FLuxoCaixaDAO {

	private DBConnections dbConnections;

	public FLuxoCaixaDAO(DBConnections dbConnections) {
		this.dbConnections = dbConnections;
	}

	public void inserirFluxoCaixa(FluxoCaixa fluxoCaixa) {
		ContentValues values = new ContentValues();
		values.put(IDataBaseConstants.FLUXO_CAIXA.DESCRICAO, fluxoCaixa.getDescricao());
		values.put(IDataBaseConstants.FLUXO_CAIXA.TIPO_FLUXO, fluxoCaixa.getTipoFluxo());
		values.put(IDataBaseConstants.FLUXO_CAIXA.VALOR, fluxoCaixa.getValor());
		dbConnections.getWritableDatabase().insert(IDataBaseConstants.FLUXO_CAIXA.TABLE, null, values);
	}

	public void atualizarFluxoCaixa(FluxoCaixa fluxoCaixa) {
		ContentValues values = new ContentValues();
		values.put(IDataBaseConstants.FLUXO_CAIXA.DESCRICAO, fluxoCaixa.getDescricao());
		values.put(IDataBaseConstants.FLUXO_CAIXA.TIPO_FLUXO, fluxoCaixa.getTipoFluxo());
		values.put(IDataBaseConstants.FLUXO_CAIXA.VALOR, fluxoCaixa.getValor());
		String[] args = { fluxoCaixa.getIdFluxoCaixa().toString() };
		dbConnections.getWritableDatabase().update(IDataBaseConstants.FLUXO_CAIXA.TABLE, values,
				IDataBaseConstants.FLUXO_CAIXA.ID_FLUXO_CAIXA + "=?", args);
	}

	public List<FluxoCaixa> listaFluxoCaixa() {
		ArrayList<FluxoCaixa> lista = new ArrayList<FluxoCaixa>();
		String sql = "SELECT * FROM " + IDataBaseConstants.FLUXO_CAIXA.TABLE;
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			FluxoCaixa fluxoCaixa = new FluxoCaixa();
			fluxoCaixa.setIdFluxoCaixa(c.getLong(c.getColumnIndex(IDataBaseConstants.FLUXO_CAIXA.ID_FLUXO_CAIXA)));
			fluxoCaixa.setDescricao(c.getString(c.getColumnIndex(IDataBaseConstants.FLUXO_CAIXA.DESCRICAO)));
			fluxoCaixa.setTipoFluxo(c.getString(c.getColumnIndex(IDataBaseConstants.FLUXO_CAIXA.TIPO_FLUXO)));
			fluxoCaixa.setValor(c.getDouble(c.getColumnIndex(IDataBaseConstants.FLUXO_CAIXA.VALOR)));
			lista.add(fluxoCaixa);
		}
		return lista;
	}

	public void deletar(FluxoCaixa fluxoCaixa) {
		String[] args = { fluxoCaixa.getIdFluxoCaixa().toString() };
		dbConnections.getWritableDatabase().delete(IDataBaseConstants.FLUXO_CAIXA.TABLE,
				IDataBaseConstants.FLUXO_CAIXA.ID_FLUXO_CAIXA + "=?", args);
	}

	public Double totalDeEntradas() {
		Double totalDeEntradas = 0.0;
		List<FluxoCaixa> listaDeFluxoDeCaixa = listaFluxoCaixa();
		for (FluxoCaixa fluxoCaixa : listaDeFluxoDeCaixa) {
			if (fluxoCaixa.getTipoFluxo().equals(FluxoCaixa.FLUXO_TIPO_ENTRADA)) {
				totalDeEntradas += fluxoCaixa.getValor();
			}
		}
		return totalDeEntradas + dbConnections.getMensalidadeDAO().totalMensalidadesPagas();
	}

	public Double totalDeSaidas() {
		Double totalDeSaidas = 0.0;
		List<FluxoCaixa> listaDeFluxoDeCaixa = listaFluxoCaixa();
		for (FluxoCaixa fluxoCaixa : listaDeFluxoDeCaixa) {
			if (fluxoCaixa.getTipoFluxo().equals(FluxoCaixa.FLUXO_TIPO_SAIDA)) {
				totalDeSaidas += fluxoCaixa.getValor();
			}
		}
		return totalDeSaidas;
	}

	public Double saldoFluxoCaixa() {
		return totalDeEntradas() - totalDeSaidas();
	}

}
