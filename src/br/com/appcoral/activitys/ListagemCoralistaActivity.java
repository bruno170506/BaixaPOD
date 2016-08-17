package br.com.appcoral.activitys;

import java.util.ArrayList;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import br.com.appcoral.R;
import br.com.appcoral.dao.CoralistaDAO;
import br.com.appcoral.model.Coralista;
import br.com.appcoral.util.AndroidCallback;
import br.com.appcoral.util.DialogUtil;
import br.com.appcoral.util.webservice.coralista.listarCoralistasTask;

public class ListagemCoralistaActivity extends MainActivity {

	public static final String CORALISTA_SELECIONADO = "coralistaSelecionado";
	private Coralista coralista;
	private ListView lista;
	private EditText pesquisar;
	private RadioButton ativos;
	private RadioButton inativos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_coralistas);

		desabilitarTecladoInicialmente();
		
		ativos = (RadioButton) findViewById(R.id.coralistasAtivos);
		ativos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				recarregaLista(getDbConnections().getCoralistaDAO().listaCoralistas(CoralistaDAO.STATUS_ATIVO));
			}
		});
		inativos = (RadioButton) findViewById(R.id.coralistasInativos);
		inativos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				recarregaLista(getDbConnections().getCoralistaDAO().listaCoralistas(CoralistaDAO.STATUS_INATIVO));
			}
		});

		pesquisar = (EditText) findViewById(R.id.pesquisaCoralista);
		pesquisar.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				ArrayList<Coralista> result = getDbConnections().getCoralistaDAO().pesquisarPorNome(s,
						ativos.isChecked() ? CoralistaDAO.STATUS_ATIVO : CoralistaDAO.STATUS_INATIVO);
				recarregaLista(result);
			}
		});

		ImageButton btnNovo = (ImageButton) findViewById(R.id.btn_novoCoralista);
		btnNovo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				irParaOFormulario(false);
			}

		});

		lista = (ListView) findViewById(R.id.listaCoralista);
		registerForContextMenu(lista);
//		recarregaLista(getDbConnections().getCoralistaDAO().listaCoralistas(CoralistaDAO.STATUS_ATIVO));
		
		
		new listarCoralistasTask(this).execute();
		
//		List<Coralista> listaVazia = new ArrayList<Coralista>();
//		recarregaLista(listaVazia);
		

	}

	private void desabilitarTecladoInicialmente() {
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	private void irParaOFormulario(Boolean emModoDeEdicao) {
		Intent irParaOFormulario = new Intent(ListagemCoralistaActivity.this, FormularioCoralistaActivity.class);
		if (emModoDeEdicao) {
			irParaOFormulario.putExtra(CORALISTA_SELECIONADO, coralista);
		}
		startActivity(irParaOFormulario);
		ListagemCoralistaActivity.this.finish();
	}

	public void recarregaLista(List<Coralista> listaCoralistas) {
		ArrayAdapter<Coralista> arrayAdapter = new ArrayAdapter<Coralista>(this, android.R.layout.simple_list_item_1,
				listaCoralistas);
		lista.setAdapter(arrayAdapter);
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
				coralista = (Coralista) adapter.getItemAtPosition(posicao);
				return false;
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.setHeaderTitle(coralista.getNome());

		if (coralista.getStatusAtivo().equals(CoralistaDAO.STATUS_ATIVO)) {

			MenuItem cobrarMensalidade = menu.add("Cobrar Mensalidade");
			cobrarMensalidade.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					Intent intent = new Intent(ListagemCoralistaActivity.this, ListagemMensalidadeActivity.class);
					intent.putExtra(CORALISTA_SELECIONADO, coralista);
					startActivity(intent);
					finish();
					return false;
				}
			});

			MenuItem editar = menu.add("Editar");
			editar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					irParaOFormulario(true);
					return false;
				}
			});

			// Inativar
			MenuItem inativar = menu.add("Inativar");
			inativar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					String mensagem = "Inativar Coralista: " + coralista.getNome() + "?";
					DialogUtil.confirmacao(ListagemCoralistaActivity.this, "Confirma��o", mensagem,
							new AndroidCallback() {
						@Override
						public void callbackResult(Boolean result) {
							if (result) {
								CoralistaDAO dao = getDbConnections().getCoralistaDAO();
								dao.inativarCoralista(coralista);
								recarregaLista(getDbConnections().getCoralistaDAO()
										.listaCoralistas(CoralistaDAO.STATUS_ATIVO));
								DialogUtil.feedback(ListagemCoralistaActivity.this, "Coralista Inativado!");
								ativos.setChecked(true);
							}
						}
					});
					return false;
				}
			});

		} else {
			// Ativar
			MenuItem inativar = menu.add("Ativar");
			inativar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					String mensagem = "Ativar Coralista: " + coralista.getNome() + "?";
					DialogUtil.confirmacao(ListagemCoralistaActivity.this, "Confirma��o", mensagem,
							new AndroidCallback() {
						@Override
						public void callbackResult(Boolean result) {
							if (result) {
								CoralistaDAO dao = getDbConnections().getCoralistaDAO();
								dao.ativarCoralista(coralista);
								recarregaLista(getDbConnections().getCoralistaDAO()
										.listaCoralistas(CoralistaDAO.STATUS_ATIVO));
								DialogUtil.feedback(ListagemCoralistaActivity.this, "Coralista Ativado!");
								ativos.setChecked(true);
							}
						}
					});
					return false;
				}
			});
		}

		super.onCreateContextMenu(menu, v, menuInfo);
	}

}
