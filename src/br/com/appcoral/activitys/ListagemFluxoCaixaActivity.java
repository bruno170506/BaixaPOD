package br.com.appcoral.activitys;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.appcoral.R;
import br.com.appcoral.dao.FLuxoCaixaDAO;
import br.com.appcoral.model.FluxoCaixa;
import br.com.appcoral.util.Moeda;

public class ListagemFluxoCaixaActivity extends MainActivity {

	public static final String FLUXO_CAIXA_SELECIONADO = "fluxoCaixaSelecionado";
	private TextView saldo;
	private TextView entradas;
	private TextView saidas;
	private ListView listagemFluxoDeCaixa;
	private ImageButton btnAdicionar;
	private EditText pesquisarDescricao;
	protected FluxoCaixa fluxoCaixaSelecionado;
	private TextView totalMensalidadesPagasTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_fluxo_caixa);

		desabilitarTecladoInicialmente();

		pesquisarDescricao = (EditText) findViewById(R.id.pesquisaDescricao);
		pesquisarDescricao.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				List<FluxoCaixa> fluxosDeCaixa = getDbConnections().getFluxoCaixaDAO().listaFluxoCaixa();
				recarregaLista(fluxosDeCaixa);
			}

		});

		saldo = (TextView) findViewById(R.id.saldo);
		entradas = (TextView) findViewById(R.id.entradas);
		saidas = (TextView) findViewById(R.id.saidas);
		totalMensalidadesPagasTextView = (TextView) findViewById(R.id.totalMensalidadesPagas);

		btnAdicionar = (ImageButton) findViewById(R.id.btn_adicionarFluxoCaixa);
		btnAdicionar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				irParaFormularioFluxoCaixa(false);
			}
		});

		listagemFluxoDeCaixa = (ListView) findViewById(R.id.listaDeEntradasESaidas);
		registerForContextMenu(listagemFluxoDeCaixa);
		recarregaLista(getDbConnections().getFluxoCaixaDAO().listaFluxoCaixa());

		listagemFluxoDeCaixa.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
				fluxoCaixaSelecionado = (FluxoCaixa) adapter.getItemAtPosition(posicao);
				return false;
			}
		});
		listagemFluxoDeCaixa.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
				FluxoCaixa c = (FluxoCaixa) adapter.getItemAtPosition(posicao);
				String descFluxo = c.getTipoFluxo().equals(FluxoCaixa.FLUXO_TIPO_ENTRADA)?"Entrada":"Saída";
				String msg = descFluxo + ": " + Moeda.valorFormatado(c.getValor());
				Toast.makeText(ListagemFluxoCaixaActivity.this, msg,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void recarregaLista(List<FluxoCaixa> fluxosDeCaixa) {
		ArrayAdapter<FluxoCaixa> arrayAdapter = new ArrayAdapter<FluxoCaixa>(this, android.R.layout.simple_list_item_1,
				fluxosDeCaixa);
		listagemFluxoDeCaixa.setAdapter(arrayAdapter);

		saldo.setText("Saldo: " + Moeda.valorFormatado(getDbConnections().getFluxoCaixaDAO().saldoFluxoCaixa()));
		entradas.setText("Entradas: " + Moeda.valorFormatado(getDbConnections().getFluxoCaixaDAO().totalDeEntradas()));
		saidas.setText("Saídas: " + Moeda.valorFormatado(getDbConnections().getFluxoCaixaDAO().totalDeSaidas()));
		totalMensalidadesPagasTextView.setText("Mensalidades Pagas: "
				+ Moeda.valorFormatado(getDbConnections().getMensalidadeDAO().totalMensalidadesPagas()));
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.setHeaderTitle(fluxoCaixaSelecionado.getDescricao());
		MenuItem editar = menu.add("Editar");
		editar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				irParaFormularioFluxoCaixa(true);
				return false;
			}
		});

		MenuItem excluir = menu.add("Excluir");
		excluir.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				FLuxoCaixaDAO dao = getDbConnections().getFluxoCaixaDAO();
				dao.deletar(fluxoCaixaSelecionado);
				recarregaLista(getDbConnections().getFluxoCaixaDAO().listaFluxoCaixa());
				Toast.makeText(ListagemFluxoCaixaActivity.this, "Removido com sucesso!", Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	private void desabilitarTecladoInicialmente() {
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	private void irParaFormularioFluxoCaixa(Boolean emModoDeEdicao) {
		Intent intent = new Intent(ListagemFluxoCaixaActivity.this, FormularioFluxoCaixaActivity.class);
		if (emModoDeEdicao) {
			intent.putExtra(FLUXO_CAIXA_SELECIONADO, fluxoCaixaSelecionado);
		}
		startActivity(intent);
		finish();
	}

}
