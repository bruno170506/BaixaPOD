package br.com.baixapod.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import br.com.baixapod.activitys.AutenticacaoActivity;
import br.com.baixapod.activitys.ItemPodActivity;
import br.com.baixapod.dto.CoordenadaDTO;
import br.com.baixapod.model.ItemPOD;
import br.com.baixapod.model.MovimentoPOD;
import br.com.baixapod.model.Ocorrencia;
import br.com.baixapod.model.Pessoa;
import br.com.baixapod.model.UsuarioConectado;
import br.com.baixapod.tasks.AutenticandoNoCelularTask;
import br.com.baixapod.tasks.EnviarMovimentosPODTask;
import br.com.baixapod.webservice.ItemPODConverter;
import br.com.baixapod.webservice.OcorrenciaConverter;
import br.com.baixapod.webservice.PessoaConverter;

public class BancoDoCelularDAO {

	public DBConnections dbConnections;

	public BancoDoCelularDAO(DBConnections dbConnections) {
		this.dbConnections = dbConnections;
	}

	public void fazerAutenticacaoBancoDoCelular(AutenticacaoActivity contexto, Pessoa p) {
		new AutenticandoNoCelularTask(contexto, this).execute(p);
	}

	public boolean bancoPreenchido() {
		return listaPessoa().size() > 0;
	}

	public void popularTabelaPessoa(String pessoaJson, String matricula) {
		List<Pessoa> listaPessoa = PessoaConverter.toJSON(pessoaJson);
		// limpa tabela pessoa
		dbConnections.getWritableDatabase().execSQL("delete from " + IDataBaseConstants.PESSOA.TABLE);
		// popula novamente a tabela pessoa
		for (Pessoa obj : listaPessoa) {
			if (obj.getMatricula().equals(matricula)) {
				ContentValues values = new ContentValues();
				values.put(IDataBaseConstants.PESSOA.MATRICULA, obj.getMatricula());
				values.put(IDataBaseConstants.PESSOA.USUARIO, obj.getUsuario());
				values.put(IDataBaseConstants.PESSOA.SENHA, obj.getSenha());
				dbConnections.getWritableDatabase().insert(IDataBaseConstants.PESSOA.TABLE, null, values);
			}
		}
	}

	public void popularTabelaOcorrencia(String ocorrenciaJson) {
		List<Ocorrencia> listaOorrencia = OcorrenciaConverter.toJSON(ocorrenciaJson);
		// limpa tabela ocorrencia
		dbConnections.getWritableDatabase().execSQL("delete from " + IDataBaseConstants.OCORRENCIA.TABLE);
		// popula novamente a tabela ocorrencia
		for (Ocorrencia obj : listaOorrencia) {
			ContentValues values = new ContentValues();
			values.put(IDataBaseConstants.OCORRENCIA.CODIGO, obj.getCodigo());
			values.put(IDataBaseConstants.OCORRENCIA.DESCRICAO, obj.getDescricao());
			dbConnections.getWritableDatabase().insert(IDataBaseConstants.OCORRENCIA.TABLE, null, values);
		}
	}

	public void popularTabelaItemPODs(String itemPODsJson) {
		List<ItemPOD> listaItemPODs = ItemPODConverter.toJSON(itemPODsJson);
		// limpa tabela ItemPODs
		dbConnections.getWritableDatabase().execSQL("delete from " + IDataBaseConstants.PODS.TABLE);
		// popula novamente a tabela ItemPODs
		for (ItemPOD obj : listaItemPODs) {
			ContentValues values = new ContentValues();
			values.put(IDataBaseConstants.PODS.N_HAWB, obj.getN_hawb());
			values.put(IDataBaseConstants.PODS.NOME_DESTI, obj.getNome_desti());
			values.put(IDataBaseConstants.PODS.RUA_DESTI, obj.getRua_desti());
			values.put(IDataBaseConstants.PODS.NUMERO_DESTI, obj.getNumero_desti());
			values.put(IDataBaseConstants.PODS.BAIRRO_DESTI, obj.getBairro_desti());
			values.put(IDataBaseConstants.PODS.CIDADE_DESTI, obj.getCidade_desti());
			values.put(IDataBaseConstants.PODS.N_TENTATIVAS, obj.getN_tentativas());
			dbConnections.getWritableDatabase().insert(IDataBaseConstants.PODS.TABLE, null, values);
		}
	}

	public List<Pessoa> listaPessoa() {
		ArrayList<Pessoa> lista = new ArrayList<Pessoa>();
		String sql = "SELECT * FROM " + IDataBaseConstants.PESSOA.TABLE;
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			Pessoa ob = new Pessoa();
			ob.setMatricula(c.getString(c.getColumnIndex(IDataBaseConstants.PESSOA.MATRICULA)));
			ob.setUsuario(c.getString(c.getColumnIndex(IDataBaseConstants.PESSOA.USUARIO)));
			ob.setSenha(c.getString(c.getColumnIndex(IDataBaseConstants.PESSOA.SENHA)));
			lista.add(ob);
		}
		return lista;
	}

	public List<Ocorrencia> listaOcorrencia() {
		ArrayList<Ocorrencia> lista = new ArrayList<Ocorrencia>();
		String sql = "SELECT * FROM " + IDataBaseConstants.OCORRENCIA.TABLE;
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			Ocorrencia ob = new Ocorrencia();
			ob.setCodigo(c.getString(c.getColumnIndex(IDataBaseConstants.OCORRENCIA.CODIGO)));
			ob.setDescricao(c.getString(c.getColumnIndex(IDataBaseConstants.OCORRENCIA.DESCRICAO)));
			lista.add(ob);
		}
		return lista;
	}

	public List<ItemPOD> listaPODs() {
		ArrayList<ItemPOD> lista = new ArrayList<ItemPOD>();
		String sql = "SELECT * FROM " + IDataBaseConstants.PODS.TABLE;
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			ItemPOD ob = new ItemPOD();
			ob.setN_hawb(c.getString(c.getColumnIndex(IDataBaseConstants.PODS.N_HAWB)));
			ob.setNome_desti(c.getString(c.getColumnIndex(IDataBaseConstants.PODS.NOME_DESTI)));
			ob.setRua_desti(c.getString(c.getColumnIndex(IDataBaseConstants.PODS.RUA_DESTI)));
			ob.setNumero_desti(c.getString(c.getColumnIndex(IDataBaseConstants.PODS.RUA_DESTI)));
			ob.setBairro_desti(c.getString(c.getColumnIndex(IDataBaseConstants.PODS.BAIRRO_DESTI)));
			ob.setCidade_desti(c.getString(c.getColumnIndex(IDataBaseConstants.PODS.CIDADE_DESTI)));
			ob.setN_tentativas(c.getString(c.getColumnIndex(IDataBaseConstants.PODS.N_TENTATIVAS)));
			lista.add(ob);
		}
		return lista;
	}

	public List<MovimentoPOD> listaMovimentoPODs() {
		ArrayList<MovimentoPOD> lista = new ArrayList<MovimentoPOD>();
		String sql = "SELECT * FROM " + IDataBaseConstants.MOVIMENTO.TABLE;
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			MovimentoPOD ob = new MovimentoPOD();
			ob.setN_hawb(c.getString(c.getColumnIndex(IDataBaseConstants.MOVIMENTO.N_HAWB)));
			ob.setDt_entrega(c.getString(c.getColumnIndex(IDataBaseConstants.MOVIMENTO.DT_ENTREGA)));
			ob.setHr_entrega(c.getString(c.getColumnIndex(IDataBaseConstants.MOVIMENTO.HR_ENTREGA)));
			ob.setN_tentativas(c.getString(c.getColumnIndex(IDataBaseConstants.MOVIMENTO.N_TENTATIVA)));
			ob.setOcorrencia(c.getString(c.getColumnIndex(IDataBaseConstants.MOVIMENTO.OCORRENCIA)));
			ob.setSucesso(c.getString(c.getColumnIndex(IDataBaseConstants.MOVIMENTO.SUCESSO)));
			ob.setLongitude(c.getString(c.getColumnIndex(IDataBaseConstants.MOVIMENTO.LONGITUDE)));
			ob.setLatitude(c.getString(c.getColumnIndex(IDataBaseConstants.MOVIMENTO.LATITUDE)));
			ob.setObservacao(c.getString(c.getColumnIndex(IDataBaseConstants.MOVIMENTO.OBSERVACAO)));
			lista.add(ob);
		}
		return lista;
	}

	public void inserirMovimento(CoordenadaDTO coordenadaDTO, ItemPOD itemPOD) {

		// insere o movimento para posteriormente poder enviar ao servidor
		MovimentoPOD movimento = new MovimentoPOD(itemPOD, coordenadaDTO);

		ContentValues values = new ContentValues();
		values.put(IDataBaseConstants.MOVIMENTO.N_HAWB, movimento.getN_hawb());
		values.put(IDataBaseConstants.MOVIMENTO.DT_ENTREGA, movimento.getDt_entrega());
		values.put(IDataBaseConstants.MOVIMENTO.HR_ENTREGA, movimento.getHr_entrega());
		values.put(IDataBaseConstants.MOVIMENTO.N_TENTATIVA, movimento.getN_tentativas());
		values.put(IDataBaseConstants.MOVIMENTO.OCORRENCIA, movimento.getOcorrencia());
		values.put(IDataBaseConstants.MOVIMENTO.SUCESSO, String.valueOf(movimento.getSucesso()));
		values.put(IDataBaseConstants.MOVIMENTO.LONGITUDE, String.valueOf(movimento.getLongitude()));
		values.put(IDataBaseConstants.MOVIMENTO.LATITUDE, String.valueOf(movimento.getLatitude()));
		values.put(IDataBaseConstants.MOVIMENTO.OBSERVACAO, movimento.getObservacao());
		dbConnections.getWritableDatabase().insert(IDataBaseConstants.MOVIMENTO.TABLE, null, values);

		// remove da lista de pods
		String[] args = { itemPOD.getN_hawb().toString() };
		dbConnections.getWritableDatabase().delete(IDataBaseConstants.PODS.TABLE, IDataBaseConstants.PODS.N_HAWB + "=?",
				args);
	}

	public void enviarTodosMovimentosPODs(ItemPodActivity contexto) {
		new EnviarMovimentosPODTask(contexto).execute(listaMovimentoPODs());
	}

	public void limparTabelaMovimentoPODs() {
		// limpa tabela movimento PODs
		dbConnections.getWritableDatabase().execSQL("delete from " + IDataBaseConstants.MOVIMENTO.TABLE);
	}

	public UsuarioConectado usuarioConectado() {
		String sql = "SELECT * FROM " + IDataBaseConstants.USUARIO_CONECTADO.TABLE;
		Cursor c = dbConnections.getReadableDatabase().rawQuery(sql, null);
		while (c.moveToNext()) {
			UsuarioConectado usuarioConectado = new UsuarioConectado();
			usuarioConectado.setUsuario(c.getString(c.getColumnIndex(IDataBaseConstants.USUARIO_CONECTADO.USUARIO)));
			usuarioConectado.setSenha(c.getString(c.getColumnIndex(IDataBaseConstants.USUARIO_CONECTADO.SENHA)));
			return usuarioConectado;
		}
		return null;
	}

	public void atualizaUsuarioConectado(Pessoa p) {
		// limpa tabela movimento PODs
		dbConnections.getWritableDatabase().execSQL("delete from " + IDataBaseConstants.USUARIO_CONECTADO.TABLE);
		if (p.isConectado()) {
			ContentValues values = new ContentValues();
			values.put(IDataBaseConstants.USUARIO_CONECTADO.USUARIO, p.getUsuario());
			values.put(IDataBaseConstants.USUARIO_CONECTADO.SENHA, p.getSenha());
			dbConnections.getWritableDatabase().insert(IDataBaseConstants.USUARIO_CONECTADO.TABLE, null, values);
		}
	}

}
