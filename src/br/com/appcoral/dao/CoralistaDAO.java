package br.com.appcoral.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import br.com.appcoral.model.Coralista;

public class CoralistaDAO {

	public static final String STATUS_ATIVO = "S";
	public static final String STATUS_INATIVO = "N";
	private DBConnections dbConnections;

	public CoralistaDAO(DBConnections dbConnections) {
		this.dbConnections = dbConnections;
	}

	public void insereCoralista(Coralista coralista) {
		ContentValues values = new ContentValues();
		values.put(IDataBaseConstants.CORALISTAS.NOME, coralista.getNome());
		values.put(IDataBaseConstants.CORALISTAS.RG, coralista.getRg());
		values.put(IDataBaseConstants.CORALISTAS.TELEFONE, coralista.getTelefone());
		values.put(IDataBaseConstants.CORALISTAS.EMAIL, coralista.getEmail());
		values.put(IDataBaseConstants.CORALISTAS.DATA_NASCIMENTO, coralista.getDataNascimento());
		values.put(IDataBaseConstants.CORALISTAS.NYPE_VOCAL, coralista.getNypeVocal());
		values.put(IDataBaseConstants.CORALISTAS.QR_CODE, coralista.getQrCode());
		values.put(IDataBaseConstants.CORALISTAS.STATUS_ATIVO, STATUS_ATIVO);
		dbConnections.getWritableDatabase().insert(IDataBaseConstants.CORALISTAS.TABLE, null, values);
	}

	public void atualizarCoralista(Coralista coralista) {
		ContentValues values = new ContentValues();
		values.put(IDataBaseConstants.CORALISTAS.NOME, coralista.getNome());
		values.put(IDataBaseConstants.CORALISTAS.RG, coralista.getRg());
		values.put(IDataBaseConstants.CORALISTAS.TELEFONE, coralista.getTelefone());
		values.put(IDataBaseConstants.CORALISTAS.EMAIL, coralista.getEmail());
		values.put(IDataBaseConstants.CORALISTAS.DATA_NASCIMENTO, coralista.getDataNascimento());
		values.put(IDataBaseConstants.CORALISTAS.NYPE_VOCAL, coralista.getNypeVocal());
		values.put(IDataBaseConstants.CORALISTAS.QR_CODE, coralista.getQrCode());
		values.put(IDataBaseConstants.CORALISTAS.STATUS_ATIVO, STATUS_ATIVO);
		String[] args = { coralista.getIdCoralista().toString() };
		dbConnections.getWritableDatabase().update(IDataBaseConstants.CORALISTAS.TABLE, values,
				IDataBaseConstants.CORALISTAS.ID_CORALISTA + "=?", args);

	}

	public List<Coralista> listaComTodosOsCoralistas() {

		ArrayList<Coralista> lista = new ArrayList<Coralista>();
		String sql = "SELECT * FROM " + IDataBaseConstants.CORALISTAS.TABLE + " order by " + IDataBaseConstants.CORALISTAS.NOME + " ASC";
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			Coralista coralista = new Coralista();
			coralista.setIdCoralista(c.getLong(c.getColumnIndex(IDataBaseConstants.CORALISTAS.ID_CORALISTA)));
			coralista.setNome(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.NOME)));
			coralista.setRg(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.RG)));
			coralista.setTelefone(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.TELEFONE)));
			coralista.setEmail(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.EMAIL)));
			coralista.setDataNascimento(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.DATA_NASCIMENTO)));
			coralista.setNypeVocal(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.NYPE_VOCAL)));
			coralista.setQrCode(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.QR_CODE)));
			coralista.setStatusAtivo(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.STATUS_ATIVO)));
			lista.add(coralista);
		}
		return lista;
	}

	public List<Coralista> listaCoralistas(String status) {

		ArrayList<Coralista> lista = new ArrayList<Coralista>();
		String sql = "SELECT * FROM " + IDataBaseConstants.CORALISTAS.TABLE + " where statusAtivo = '" + status
				+ "' order by " + IDataBaseConstants.CORALISTAS.NOME + " ASC";
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			Coralista coralista = new Coralista();
			coralista.setIdCoralista(c.getLong(c.getColumnIndex(IDataBaseConstants.CORALISTAS.ID_CORALISTA)));
			coralista.setNome(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.NOME)));
			coralista.setRg(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.RG)));
			coralista.setTelefone(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.TELEFONE)));
			coralista.setEmail(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.EMAIL)));
			coralista.setDataNascimento(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.DATA_NASCIMENTO)));
			coralista.setNypeVocal(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.NYPE_VOCAL)));
			coralista.setQrCode(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.QR_CODE)));
			coralista.setStatusAtivo(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.STATUS_ATIVO)));
			lista.add(coralista);
		}
		return lista;
	}

	public void inativarCoralista(Coralista coralista) {
		ContentValues values = new ContentValues();
		values.put(IDataBaseConstants.CORALISTAS.STATUS_ATIVO, STATUS_INATIVO);
		String[] args = { coralista.getIdCoralista().toString() };
		dbConnections.getWritableDatabase().update(IDataBaseConstants.CORALISTAS.TABLE, values,
				IDataBaseConstants.CORALISTAS.ID_CORALISTA + "=?", args);
	}

	public void ativarCoralista(Coralista coralista) {
		ContentValues values = new ContentValues();
		values.put(IDataBaseConstants.CORALISTAS.STATUS_ATIVO, STATUS_ATIVO);
		String[] args = { coralista.getIdCoralista().toString() };
		dbConnections.getWritableDatabase().update(IDataBaseConstants.CORALISTAS.TABLE, values,
				IDataBaseConstants.CORALISTAS.ID_CORALISTA + "=?", args);
	}

	public Coralista coralistaById(Long id) {
		Coralista coralista;
		String sql = "SELECT * FROM " + IDataBaseConstants.CORALISTAS.TABLE + " WHERE "
				+ IDataBaseConstants.CORALISTAS.ID_CORALISTA + "=" + id;
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			coralista = new Coralista();
			coralista.setIdCoralista(c.getLong(c.getColumnIndex(IDataBaseConstants.CORALISTAS.ID_CORALISTA)));
			coralista.setNome(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.NOME)));
			coralista.setRg(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.RG)));
			coralista.setTelefone(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.TELEFONE)));
			coralista.setEmail(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.EMAIL)));
			coralista.setDataNascimento(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.DATA_NASCIMENTO)));
			coralista.setNypeVocal(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.NYPE_VOCAL)));
			coralista.setQrCode(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.QR_CODE)));
			coralista.setStatusAtivo(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.STATUS_ATIVO)));
			return coralista;
		}
		return null;
	}

	public ArrayList<Coralista> pesquisarPorNome(CharSequence s, String status) {
		String nomePesquisado = s.toString();
		ArrayList<Coralista> lista = new ArrayList<Coralista>();
		String sql = "SELECT * FROM " + IDataBaseConstants.CORALISTAS.TABLE + " WHERE "
				+ IDataBaseConstants.CORALISTAS.NOME + " LIKE '%" + nomePesquisado + "%' and statusAtivo = '" + status
				+ "' order by " + IDataBaseConstants.CORALISTAS.NOME + " ASC";
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			String statusAtivo = c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.STATUS_ATIVO));
			if (statusAtivo.equals(status)) {
				Coralista coralista = new Coralista();
				coralista.setIdCoralista(c.getLong(c.getColumnIndex(IDataBaseConstants.CORALISTAS.ID_CORALISTA)));
				coralista.setNome(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.NOME)));
				coralista.setRg(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.RG)));
				coralista.setTelefone(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.TELEFONE)));
				coralista.setEmail(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.EMAIL)));
				coralista.setDataNascimento(
						c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.DATA_NASCIMENTO)));
				coralista.setNypeVocal(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.NYPE_VOCAL)));
				coralista.setQrCode(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.QR_CODE)));
				coralista.setStatusAtivo(c.getString(c.getColumnIndex(IDataBaseConstants.CORALISTAS.STATUS_ATIVO)));
				lista.add(coralista);
			}
		}
		return lista;
	}

	public void preencheQrCodeCoralista(Coralista coralista) {
		coralista.setQrCode(
				"http://sonatacoral.com/frequencia/" + coralista.getIdCoralista() + "#" + coralista.getNome());
		atualizarCoralista(coralista);
	}

}
