package br.com.appcoral.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;
import br.com.appcoral.model.ControleFrequencia;

public class ControleFrequenciaDAO {

	private static final String TURNO_DA_MANHA = "MANHA";
	private static final String TURNO_DA_TARDE = "TARDE";
	private static final String TURNO_DA_NOITE = "NOITE";
	private DBConnections dbConnections;

	public ControleFrequenciaDAO(DBConnections dbConnections) {
		this.dbConnections = dbConnections;
	}

	public void registrarFrequencia(ControleFrequencia controleFrequencia) {
		ContentValues values = new ContentValues();
		values.put(IDataBaseConstants.CONTROLE_FREQUENCIA.FK_ID_CORALISTA, controleFrequencia.getIdCoralista());
		values.put(IDataBaseConstants.CONTROLE_FREQUENCIA.DATA_HORA_FREQUENCIA,
				controleFrequencia.getDataHoraFrequencia());

		dbConnections.getWritableDatabase().insert(IDataBaseConstants.CONTROLE_FREQUENCIA.TABLE, null, values);

	}

	public List<ControleFrequencia> frequenciasDoCoralista(Long idCoralista) {
		ArrayList<ControleFrequencia> lista = new ArrayList<ControleFrequencia>();
		String sql = "SELECT * FROM " + IDataBaseConstants.CONTROLE_FREQUENCIA.TABLE + " WHERE "
				+ IDataBaseConstants.CONTROLE_FREQUENCIA.FK_ID_CORALISTA + " = " + idCoralista;

		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			ControleFrequencia cf = new ControleFrequencia();
			cf.setIdFrequencia(c.getLong(c.getColumnIndex(IDataBaseConstants.CONTROLE_FREQUENCIA.ID_FREQUENCIA)));
			cf.setIdCoralista(c.getLong(c.getColumnIndex(IDataBaseConstants.CONTROLE_FREQUENCIA.FK_ID_CORALISTA)));
			cf.setDataHoraFrequencia(
					c.getString(c.getColumnIndex(IDataBaseConstants.CONTROLE_FREQUENCIA.DATA_HORA_FREQUENCIA)));
			lista.add(cf);
		}
		c.close();
		return lista;
	}

	public boolean coralistaTeveFrequenciaNaData(Long idCoralista, String data) {
		String sql = "SELECT COUNT(*) FROM " + IDataBaseConstants.CONTROLE_FREQUENCIA.TABLE + " WHERE "
				+ IDataBaseConstants.CONTROLE_FREQUENCIA.FK_ID_CORALISTA + " = " + idCoralista + " AND "
				+ IDataBaseConstants.CONTROLE_FREQUENCIA.DATA_HORA_FREQUENCIA + " LIKE '%" + data + "%'";

		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		c.moveToFirst();
		if (c.getInt(0) > 0) {
			c.close();
			return true;
		}
		c.close();
		return false;
	}

	public boolean coralistaJaPossuiFrequenciaNoMesmoTurnoEDia(Long idCoralista, String dataComHoraAtual) {
		String sql = "SELECT * FROM " + IDataBaseConstants.CONTROLE_FREQUENCIA.TABLE + " WHERE "
				+ IDataBaseConstants.CONTROLE_FREQUENCIA.FK_ID_CORALISTA + " = " + idCoralista;
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			String dataHoraFrequencia = c
					.getString(c.getColumnIndex(IDataBaseConstants.CONTROLE_FREQUENCIA.DATA_HORA_FREQUENCIA));
			String space = " ";
			String data1 = dataHoraFrequencia.split(space)[0];
			String data2 = dataComHoraAtual.split(space)[0];
			if (data1.equals(data2)) {
				String turnoPeriodo1 = turnoPeriodo(dataHoraFrequencia);
				String turnoPeriodo2 = turnoPeriodo(dataComHoraAtual);
				if (turnoPeriodo1.equals(turnoPeriodo2)) {
					c.close();
					return true;
				}
			}
		}
		c.close();
		return false;
	}

	public String turnoPeriodo(String dataComHoraAtual) {
		int hora = Integer.parseInt(dataComHoraAtual.substring(11, 13));
		if (hora >= 0 && hora <= 12) {
			return TURNO_DA_MANHA;
		}
		if (hora > 12 && hora < 18) {
			return TURNO_DA_TARDE;
		}
		return TURNO_DA_NOITE;
	}

	public List<ControleFrequencia> listaControleFrequencia() {
		ArrayList<ControleFrequencia> lista = new ArrayList<ControleFrequencia>();
		String sql = "SELECT * FROM " + IDataBaseConstants.CONTROLE_FREQUENCIA.TABLE;

		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			ControleFrequencia cf = new ControleFrequencia();
			cf.setIdFrequencia(c.getLong(c.getColumnIndex(IDataBaseConstants.CONTROLE_FREQUENCIA.ID_FREQUENCIA)));
			cf.setIdCoralista(c.getLong(c.getColumnIndex(IDataBaseConstants.CONTROLE_FREQUENCIA.FK_ID_CORALISTA)));
			cf.setDataHoraFrequencia(
					c.getString(c.getColumnIndex(IDataBaseConstants.CONTROLE_FREQUENCIA.DATA_HORA_FREQUENCIA)));
			lista.add(cf);
		}
		c.close();
		return lista;
	}

	public Set<String> datasDasChamadasRealizadas() {
		String sql = "SELECT DISTINCT(" + IDataBaseConstants.CONTROLE_FREQUENCIA.DATA_HORA_FREQUENCIA + ")" + " FROM "
				+ IDataBaseConstants.CONTROLE_FREQUENCIA.TABLE + " ORDER BY "
				+ IDataBaseConstants.CONTROLE_FREQUENCIA.DATA_HORA_FREQUENCIA + " ASC";
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		Set<String> datas = new HashSet<String>();
		while (c.moveToNext()) {
			String dataHoraFrequencia = c
					.getString(c.getColumnIndex(IDataBaseConstants.CONTROLE_FREQUENCIA.DATA_HORA_FREQUENCIA));
			datas.add(dataHoraFrequencia.substring(0, 10));
		}
		c.close();
		return datas;
	}

	public void limparTabelaControleFrequencia() {
		List<ControleFrequencia> listaControleFrequencia = listaControleFrequencia();
		for (ControleFrequencia controleFrequencia : listaControleFrequencia) {
			String[] args = { controleFrequencia.getIdFrequencia().toString() };
			dbConnections.getWritableDatabase().delete(IDataBaseConstants.CONTROLE_FREQUENCIA.TABLE,
					IDataBaseConstants.CONTROLE_FREQUENCIA.ID_FREQUENCIA + "=?", args);
		}
	}

}
