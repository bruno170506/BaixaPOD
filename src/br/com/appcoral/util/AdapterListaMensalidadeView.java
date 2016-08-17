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
public class AdapterListaMensalidadeView extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<ItemMensalidadeListView> itens;

	public AdapterListaMensalidadeView(Context context, ArrayList<ItemMensalidadeListView> itens) {
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
	public ItemMensalidadeListView getItem(int position) {
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
		ItemMensalidadeListView item = itens.get(position);
		// infla o layout para podermos preencher os dados
		view = mInflater.inflate(R.layout.item_lista_mensalidades, null);

		// atravez do layout pego pelo LayoutInflater, pegamos cada id
		// relacionado
		// ao item e definimos as informações.
		((TextView) view.findViewById(R.id.option_text)).setText(item.getMes());
		((ImageView) view.findViewById(R.id.option_icon)).setImageResource(item.getIconeResourceId());

		return view;
	}
}