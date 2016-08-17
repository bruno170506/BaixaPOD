package br.com.appcoral.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import br.com.appcoral.R;
import br.com.appcoral.model.Coralista;
import br.com.appcoral.model.MensalidadePaga;
import br.com.appcoral.util.ItemMensalidadeListView;

public class MensalidadeDAO {

	public static final String MENSALIDADE_PAGA_PARCIALMENTE = "mensalidadePagaParcialmente";
	public static final String MENSALIDADE_QUITADA = "mensalidadeQuitada";
	public static final String MENSALIDADE_EM_ABERTO = "mensalidadeEmAberto";
	private DBConnections dbConnections;

	public MensalidadeDAO(DBConnections dbConnections) {
		this.dbConnections = dbConnections;
	}

	public void cobrarMensalidade(MensalidadePaga mensalidadePaga) {
		ContentValues values = new ContentValues();
		values.put(IDataBaseConstants.MENSALIDADE_PAGA.FK_ID_CORALISTA, mensalidadePaga.getIdCoralista());
		values.put(IDataBaseConstants.MENSALIDADE_PAGA.ANO, mensalidadePaga.getAno());
		values.put(IDataBaseConstants.MENSALIDADE_PAGA.MES, mensalidadePaga.getMes());
		values.put(IDataBaseConstants.MENSALIDADE_PAGA.VALOR_PAGO, mensalidadePaga.getValorPago());
		values.put(IDataBaseConstants.MENSALIDADE_PAGA.SITUACAO_PAGAMENTO, verificaSituacaoPagamento(mensalidadePaga));
		values.put(IDataBaseConstants.MENSALIDADE_PAGA.DATA_PAGAMENTO, mensalidadePaga.getDataPagamento());

		if (mensalidadePaga.getIdMensalidade() == null) {
			dbConnections.getWritableDatabase().insert(IDataBaseConstants.MENSALIDADE_PAGA.TABLE, null, values);
		} else {
			String[] args = { mensalidadePaga.getIdMensalidade().toString() };
			dbConnections.getWritableDatabase().update(IDataBaseConstants.MENSALIDADE_PAGA.TABLE, values,
					IDataBaseConstants.MENSALIDADE_PAGA.ID_MENSALIDADE + "=?", args);
		}

	}

	private String verificaSituacaoPagamento(MensalidadePaga mensalidadePaga) {
		Double valorPago = mensalidadePaga.getValorPago();

		if (valorPago == null || valorPago.equals(0.0)) {
			return MENSALIDADE_EM_ABERTO;
		}
		Double valorCobrado = dbConnections.getValorMensalidadeDAO().recuperaValorMensalidade().getValorMensalidade();
		
		if (mensalidadePaga.isDescontoTerceiroFamiliar()) {
			valorCobrado = valorCobrado/2;
		}

		if (valorPago > 0.0 && valorPago < valorCobrado) {
			return MENSALIDADE_PAGA_PARCIALMENTE;
		}
		return MENSALIDADE_QUITADA;
	}

	public ArrayList<ItemMensalidadeListView> itensMensalidades(Coralista coralista, int ano) {

		// Criamos nossa lista que preenchera o ListView
		ArrayList<ItemMensalidadeListView> itens = new ArrayList<ItemMensalidadeListView>();
		ItemMensalidadeListView janeiro = new ItemMensalidadeListView("Janeiro", R.drawable.pagamento_em_aberto);
		ItemMensalidadeListView fevereiro = new ItemMensalidadeListView("Fevereiro", R.drawable.pagamento_em_aberto);
		ItemMensalidadeListView marco = new ItemMensalidadeListView("Mar�o", R.drawable.pagamento_em_aberto);
		ItemMensalidadeListView abril = new ItemMensalidadeListView("Abril", R.drawable.pagamento_em_aberto);
		ItemMensalidadeListView maio = new ItemMensalidadeListView("Maio", R.drawable.pagamento_em_aberto);
		ItemMensalidadeListView junho = new ItemMensalidadeListView("Junho", R.drawable.pagamento_em_aberto);
		ItemMensalidadeListView julho = new ItemMensalidadeListView("Julho", R.drawable.pagamento_em_aberto);
		ItemMensalidadeListView agosto = new ItemMensalidadeListView("Agosto", R.drawable.pagamento_em_aberto);
		ItemMensalidadeListView setembro = new ItemMensalidadeListView("Setembro", R.drawable.pagamento_em_aberto);
		ItemMensalidadeListView outubro = new ItemMensalidadeListView("Outubro", R.drawable.pagamento_em_aberto);
		ItemMensalidadeListView novembro = new ItemMensalidadeListView("Novembro", R.drawable.pagamento_em_aberto);
		ItemMensalidadeListView dezembro = new ItemMensalidadeListView("Dezembro", R.drawable.pagamento_em_aberto);
		itens.add(janeiro);
		itens.add(fevereiro);
		itens.add(marco);
		itens.add(abril);
		itens.add(maio);
		itens.add(junho);
		itens.add(julho);
		itens.add(agosto);
		itens.add(setembro);
		itens.add(outubro);
		itens.add(novembro);
		itens.add(dezembro);

		String sql = "SELECT * FROM " + IDataBaseConstants.MENSALIDADE_PAGA.TABLE + //
				" WHERE " + IDataBaseConstants.MENSALIDADE_PAGA.FK_ID_CORALISTA + " = " + coralista.getIdCoralista() + //
				" AND " + IDataBaseConstants.MENSALIDADE_PAGA.ANO + " = " + ano;
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		HashMap<String, String> mesesPagos = new HashMap<String, String>();
		while (c.moveToNext()) {
			String mes = c.getString(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.MES));
			String situacaoPagamento = c
					.getString(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.SITUACAO_PAGAMENTO));
			mesesPagos.put(mes, situacaoPagamento);
		}
		for (ItemMensalidadeListView item : itens) {
			if (mesesPagos.containsKey(item.getMes())) {
				String situacao = mesesPagos.get(item.getMes());
				if (situacao.equals(MENSALIDADE_EM_ABERTO)) {
					item.setIconeResourceId(R.drawable.pagamento_em_aberto);
				}
				if (situacao.equals(MENSALIDADE_PAGA_PARCIALMENTE)) {
					item.setIconeResourceId(R.drawable.pago_parcial);
				}
				if (situacao.equals(MENSALIDADE_QUITADA)) {
					item.setIconeResourceId(R.drawable.pago_total);
				}
			} else {
				item.setIconeResourceId(R.drawable.pagamento_em_aberto);
			}
		}
		return itens;
	}

	public MensalidadePaga mensalidadePaga(Long idCoralista, String anoSelecionado, String mesSelecionado) {
		String sql = "SELECT * FROM " + IDataBaseConstants.MENSALIDADE_PAGA.TABLE + //
				" WHERE " + IDataBaseConstants.MENSALIDADE_PAGA.FK_ID_CORALISTA + " = " + idCoralista + //
				" AND " + IDataBaseConstants.MENSALIDADE_PAGA.ANO + " = " + anoSelecionado + //
				" AND " + IDataBaseConstants.MENSALIDADE_PAGA.MES + " = '" + mesSelecionado + "'";
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		MensalidadePaga mensalidadePaga = null;
		while (c.moveToNext()) {
			mensalidadePaga = new MensalidadePaga();
			mensalidadePaga
					.setIdMensalidade(c.getLong(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.ID_MENSALIDADE)));
			mensalidadePaga
					.setIdCoralista(c.getLong(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.FK_ID_CORALISTA)));
			mensalidadePaga.setAno(c.getString(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.ANO)));
			mensalidadePaga.setMes(c.getString(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.MES)));
			mensalidadePaga.setValorPago(c.getDouble(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.VALOR_PAGO)));
			mensalidadePaga.setSituacaoPagamento(
					c.getString(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.SITUACAO_PAGAMENTO)));
			mensalidadePaga.setDataPagamento(
					c.getString(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.DATA_PAGAMENTO)));

			return mensalidadePaga;
		}
		return mensalidadePaga;
	}

	public List<MensalidadePaga> mensalidadesPagas() {
		List<MensalidadePaga> lista = new ArrayList<MensalidadePaga>();
		String sql = "SELECT * FROM " + IDataBaseConstants.MENSALIDADE_PAGA.TABLE;
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		MensalidadePaga mensalidadePaga = null;
		while (c.moveToNext()) {
			mensalidadePaga = new MensalidadePaga();
			mensalidadePaga
					.setIdMensalidade(c.getLong(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.ID_MENSALIDADE)));
			mensalidadePaga
					.setIdCoralista(c.getLong(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.FK_ID_CORALISTA)));
			mensalidadePaga.setAno(c.getString(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.ANO)));
			mensalidadePaga.setMes(c.getString(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.MES)));
			mensalidadePaga.setValorPago(c.getDouble(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.VALOR_PAGO)));
			mensalidadePaga.setDataPagamento(
					c.getString(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.DATA_PAGAMENTO)));
			lista.add(mensalidadePaga);
		}
		return lista;
	}

	public List<MensalidadePaga> mensalidadesPagasDoCoralista(Long idCoralista) {
		List<MensalidadePaga> lista = new ArrayList<MensalidadePaga>();
		String sql = "SELECT * FROM " + IDataBaseConstants.MENSALIDADE_PAGA.TABLE + " WHERE "
				+ IDataBaseConstants.MENSALIDADE_PAGA.FK_ID_CORALISTA + " = " + idCoralista;
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		MensalidadePaga mensalidadePaga = null;
		while (c.moveToNext()) {
			mensalidadePaga = new MensalidadePaga();
			mensalidadePaga
					.setIdMensalidade(c.getLong(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.ID_MENSALIDADE)));
			mensalidadePaga
					.setIdCoralista(c.getLong(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.FK_ID_CORALISTA)));
			mensalidadePaga.setAno(c.getString(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.ANO)));
			mensalidadePaga.setMes(c.getString(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.MES)));
			mensalidadePaga.setValorPago(c.getDouble(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.VALOR_PAGO)));
			mensalidadePaga.setDataPagamento(
					c.getString(c.getColumnIndex(IDataBaseConstants.MENSALIDADE_PAGA.DATA_PAGAMENTO)));
			lista.add(mensalidadePaga);
		}
		return lista;
	}

	public Double totalMensalidadesPagas() {
		Double total = 0.0;
		List<MensalidadePaga> mensalidadesPagas = mensalidadesPagas();
		for (MensalidadePaga mensalidadePaga : mensalidadesPagas) {
			total += mensalidadePaga.getValorPago();
		}
		return total;
	}
	

	public void cancelarPagamentoMensalidadeCoralista(Long idCoralista, String anoSelecionado, String mesSelecionado) {
		String sql = "DELETE FROM " + IDataBaseConstants.MENSALIDADE_PAGA.TABLE + " WHERE " + //
				IDataBaseConstants.MENSALIDADE_PAGA.FK_ID_CORALISTA + " = " + idCoralista + " AND " + //
				IDataBaseConstants.MENSALIDADE_PAGA.ANO + " = '" + anoSelecionado + "' AND " + //
				IDataBaseConstants.MENSALIDADE_PAGA.MES + " = '" + mesSelecionado + "'";
		dbConnections.getWritableDatabase().execSQL(sql);
	}

}
