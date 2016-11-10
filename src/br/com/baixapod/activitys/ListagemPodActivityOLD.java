//package br.com.baixapod.activitys;
//
//import java.util.ArrayList;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemLongClickListener;
//import android.widget.EditText;
//import android.widget.ListView;
//import br.com.baixapod.R;
//import br.com.baixapod.model.ItemPOD;
//
//public class ListagemPodActivityOLD extends MainActivity {
//
//	public static final String CORALISTA_SELECIONADO = "coralistaSelecionado";
//	private ListView lista;
//	private EditText pesquisar;
//	private ArrayList<ItemPOD> listaItens;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.listagem_pod);
//
//		desabilitarTecladoInicialmente();
//
//
//		lista = (ListView) findViewById(R.id.listaCoralista);
//		registerForContextMenu(lista);
//
////		atualizarListaComTodosOsCoralistas();
//		
//		
//		listaItens = new ArrayList<ItemPOD>();
//		ItemPOD item1 = new ItemPOD();
//		item1.setCodBarraComNomeDestinatario("541 - Joao");
//		item1.setRua(" Osvaldo Cunha");
//		item1.setNumero("231");
//		item1.setBairro("Trindade");
//		item1.setCidade("Florianópolis");
//		item1.setEstado("SC");
//		item1.setNumTentativas("Tentativas Entrega: (2)");
//		
//		listaItens.add(item1);
//		
//		ItemPOD item2 = new ItemPOD();
//		item2.setCodBarraComNomeDestinatario("421 - Joao");
//		item2.setRua(" Josué Di Bernardes");
//		item2.setNumero("421");
//		item2.setBairro("Campinas/Kobrasol");
//		item2.setCidade("São José");
//		item2.setEstado("SC");
//		item2.setNumTentativas("Tentativas Entrega: (1)");
//		
//		listaItens.add(item2);
//		
//		
//
//		recarregaLista(listaItens);
//		
//
//	}
//
//	private void desabilitarTecladoInicialmente() {
//		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//	}
//	
//	public void recarregaLista(ArrayList<ItemPOD> list) {
//		lista.setAdapter(new ListaAdpterItemOLD(this, list));
//		lista.setOnItemLongClickListener(new OnItemLongClickListener() {
//			@Override
//			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
////				coralista = (Coralista) adapter.getItemAtPosition(posicao);
//				return false;
//			}
//		});
//	}
//	
//	
//	
//
//}
