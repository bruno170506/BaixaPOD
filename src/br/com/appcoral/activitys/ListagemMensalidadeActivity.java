package br.com.appcoral.activitys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.appcoral.R;
import br.com.appcoral.dao.MensalidadeDAO;
import br.com.appcoral.dto.MensalidadeDTO;
import br.com.appcoral.model.Coralista;
import br.com.appcoral.model.MensalidadePaga;
import br.com.appcoral.model.ValorMensalidade;
import br.com.appcoral.util.AdapterListaMensalidadeView;
import br.com.appcoral.util.AndroidCallback;
import br.com.appcoral.util.DialogUtil;
import br.com.appcoral.util.ItemMensalidadeListView;
import br.com.appcoral.util.Moeda;

public class ListagemMensalidadeActivity extends MainActivity {

	public static final String MENSALIDADE_DTO = "cobrancaMensalidade";
	private Coralista coralistaSelecionado;
	private ListView listView;
	private AdapterListaMensalidadeView adapterListView;
	private List<Integer> anos;
	private Spinner ano;
	private ValorMensalidade valorMensalidade;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_mensalidades);
		
		valorMensalidade = getDbConnections().getValorMensalidadeDAO().recuperaValorMensalidade();

		anos = criarArrayDeAnos();
		coralistaSelecionado = (Coralista) getIntent().getExtras()
				.getSerializable(ListagemCoralistaActivity.CORALISTA_SELECIONADO);

		TextView nome = (TextView) findViewById(R.id.coralista);
		nome.setText(coralistaSelecionado.getNome());
		ArrayAdapter<Integer> adapterAno = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, anos);
		ano = (Spinner) findViewById(R.id.spinnerAno);
		ano.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapter, View view, int posicao, long duracao) {
				carregarLista();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		ano.setAdapter(adapterAno);
		ano.setSelection(getPosicaoAnoAtual());
		

		listView = (ListView) findViewById(R.id.listaMesesAPagar);
		// Define o Listener quando alguem clicar no item.
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int posicao, long duracao) {
				// Pega o item que foi selecionado.
				ItemMensalidadeListView item = adapterListView.getItem(posicao);
				irParaCobrancaMensalidade(item);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long duracao) {
				ItemMensalidadeListView item = adapterListView.getItem(posicao);
				final String anoSelecionado = ano.getSelectedItem().toString();
				final String mesSelecionado = item.getMes();
				String mensagem = coralistaSelecionado.getNome()+" - "+ anoSelecionado+" / "+ mesSelecionado;
				DialogUtil.confirmacao(ListagemMensalidadeActivity.this, "Cancelar Pagamento", mensagem, new AndroidCallback() {
					@Override
					public void callbackResult(Boolean result) {
						if(result){
							//cancelar pagamento da mensalidade
							getDbConnections().getMensalidadeDAO().cancelarPagamentoMensalidadeCoralista(coralistaSelecionado.getIdCoralista(),anoSelecionado,mesSelecionado);
							carregarLista();
							DialogUtil.feedback(ListagemMensalidadeActivity.this, "Pagamento Cancelado!");
						}
					}
				});

				return true;
			}
		});
		
		registerForContextMenu(listView);
		
		carregarLista();

	}

	@SuppressLint("SimpleDateFormat")
	private int getPosicaoAnoAtual() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int anoAtual = Integer.valueOf(sdf.format(new Date()));
		for (int i = 0; i < anos.size(); i++) {
			if (anoAtual == anos.get(i)) {
				return i;
			}
		}
		return 0;
	}

	private List<Integer> criarArrayDeAnos() {
		List<Integer> lista = new ArrayList<Integer>();
		int anoInicial = 2015;
		for (int i = 0; i < 100; i++) {
			lista.add(anoInicial + i);
		}
		return lista;
	}

	private void carregarLista() {
		// Cria o adapter
		adapterListView = new AdapterListaMensalidadeView(this, getDbConnections().getMensalidadeDAO()
				.itensMensalidades(coralistaSelecionado, Integer.parseInt(ano.getSelectedItem().toString())));
		// Define o Adapter
		listView.setAdapter(adapterListView);
		// Cor quando a lista é selecionada para rolagem.
		listView.setCacheColorHint(Color.TRANSPARENT);
	}

	private void irParaCobrancaMensalidade(ItemMensalidadeListView item) {
		if(valorMensalidade==null){
			DialogUtil.feedback(ListagemMensalidadeActivity.this, "Defina o Valor da Mensalidade");
			Intent intent = new Intent(ListagemMensalidadeActivity.this, DefineValorMensalidadeActivity.class);
			startActivity(intent);
			finish();
			return;
		}		
		
		String anoSelecionado = ano.getSelectedItem().toString();
		Double valorCobrancaMensalidade = valorMensalidade.getValorMensalidade();
		
		MensalidadeDTO mensalidadeDTO = new MensalidadeDTO(coralistaSelecionado, item,anoSelecionado, valorCobrancaMensalidade);
		
		MensalidadePaga mensalidadePaga = getDbConnections().getMensalidadeDAO().mensalidadePaga(coralistaSelecionado.getIdCoralista(),anoSelecionado, item.getMes());
		if(mensalidadePaga!=null){
			mensalidadeDTO.setIdMensalidade(mensalidadePaga.getIdMensalidade());
			mensalidadeDTO.setSituacaoPagamento(mensalidadePaga.getSituacaoPagamento());
			if(mensalidadeDTO.getSituacaoPagamento().equals(MensalidadeDAO.MENSALIDADE_QUITADA)){
				String valorPago = Moeda.valorFormatado(mensalidadePaga.getValorPago()).toString();
				DialogUtil.feedback(ListagemMensalidadeActivity.this,mensalidadePaga.getMes()+": "+ valorPago);
				return;
			}
		}
		
		
		Intent intent = new Intent(ListagemMensalidadeActivity.this, FormularioCobrancaMensalidadeActivity.class);
		intent.putExtra(MENSALIDADE_DTO, mensalidadeDTO);
		startActivity(intent);
		finish();

	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent(ListagemMensalidadeActivity.this, ListagemCoralistaActivity.class);
		startActivity(intent);
		finish();
	}

}
