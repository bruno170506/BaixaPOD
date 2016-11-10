package br.com.baixapod.dao;

import java.util.ArrayList;

import br.com.baixapod.activitys.AutenticacaoActivity;
import br.com.baixapod.activitys.ItemPodActivity;
import br.com.baixapod.model.ItemPOD;
import br.com.baixapod.model.Pessoa;
import br.com.baixapod.tasks.AutenticacaoUsuarioTask;
import br.com.baixapod.tasks.EfetuarBaixaPODTask;
import br.com.baixapod.tasks.ListaOcorrenciaTask;
import br.com.baixapod.tasks.ListarPODsTask;
import br.com.baixapod.tasks.PopularTabelasNoBancoDoCelularTask;

public class BancoNaNuvemDAO {

	public void carregarOcorrencias(ItemPodActivity contexto) {
		new ListaOcorrenciaTask(contexto).execute();		
	}

	public void fazerLogin(AutenticacaoActivity contexto, Pessoa p) {
		new AutenticacaoUsuarioTask(contexto).execute(p);
	}

	public void efetuarBaixaPods(ItemPodActivity contexto, ArrayList<ItemPOD> pods) {
		new EfetuarBaixaPODTask(contexto).execute(pods);
	}

	public void popularTodasAsTabelasNoBancoDoCelular(AutenticacaoActivity contexto, Pessoa pessoa) {
		new PopularTabelasNoBancoDoCelularTask(contexto).execute(pessoa);
	}


}
