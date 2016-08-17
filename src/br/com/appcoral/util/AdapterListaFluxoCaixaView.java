package br.com.appcoral.util;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.appcoral.R;

@SuppressLint("ViewHolder")
public class AdapterListaFluxoCaixaView extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<ItemFluxoCaixaListView> itens;

	public AdapterListaFluxoCaixaView(Context context, ArrayList<ItemFluxoCaixaListView> itens) {
		// Itens que preencheram o listview
		this.itens = itens;
		// responsavel por pegar o Layout do item.
		mInflater = LayoutInflater.from(context);
	}

	/**
	 * Retorna a quantidade de itens
	 *
	 * @return
	 */
	public int getCount() {
		return itens.size();
	}

	/**
	 * Retorna o item de acordo com a posicao dele na tela.
	 *
	 * @param position
	 * @return
	 */
	public ItemFluxoCaixaListView getItem(int position) {
		return itens.get(position);
	}

	/**
	 * Sem implementação
	 *
	 * @param position
	 * @return
	 */
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	public View getView(int position, View view, ViewGroup parent) {
		// Pega o item de acordo com a posção.
		ItemFluxoCaixaListView item = itens.get(position);
		// infla o layout para podermos preencher os dados
		view = mInflater.inflate(R.layout.item_lista_fluxo_caixa, null);

		// atravez do layout pego pelo LayoutInflater, pegamos cada id
		// relacionado
		// ao item e definimos as informações.
		((TextView) view.findViewById(R.id.descricao_fluxo_caixa)).setText(item.getDescricao());
		((ImageView) view.findViewById(R.id.icon_fluxo_caixa)).setImageResource(item.getIcone());

		return view;
	}
}