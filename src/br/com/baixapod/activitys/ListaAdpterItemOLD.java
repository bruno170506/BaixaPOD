//package br.com.baixapod.activitys;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//import br.com.baixapod.R;
//import br.com.baixapod.model.ItemPOD;
//
//public class ListaAdpterItemOLD extends ArrayAdapter<ItemPOD> {
//
//	private Context contexto;
//	private ArrayList<ItemPOD> lista;
//
//	public ListaAdpterItemOLD(Context context, ArrayList<ItemPOD> lista) {
//		super(context, 0, lista);
//		this.contexto = context;
//		this.lista = lista;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//
//		ItemPOD itemPosicao = lista.get(position);
//		convertView = LayoutInflater.from(contexto).inflate(R.layout.item_pod, null);
//
//		final TextView codBarraNomeDestinatario = (TextView) convertView.findViewById(R.id.cod_barra_nome_destinatario);
//		codBarraNomeDestinatario.setText(itemPosicao.getCodBarraComNomeDestinatario());
//
//		TextView rua = (TextView) convertView.findViewById(R.id.rua);
//		rua.setText("Rua: "+ itemPosicao.getRua());
//		
//		TextView numero = (TextView) convertView.findViewById(R.id.numero);
//		numero.setText("N°: "+ itemPosicao.getNumero());
//		
//		TextView bairro = (TextView) convertView.findViewById(R.id.bairro);
//		bairro.setText("Bairro: "+ itemPosicao.getBairro());
//		
//		TextView cidade = (TextView) convertView.findViewById(R.id.cidade);
//		cidade.setText("Cidade: "+ itemPosicao.getCidade());
//		
//		TextView estado = (TextView) convertView.findViewById(R.id.estado);
//		estado.setText("Estado: "+ itemPosicao.getEstado());
//
//		TextView numTentativas = (TextView) convertView.findViewById(R.id.numTentativasEntrega);
//		numTentativas.setText(itemPosicao.getNumTentativas());
//
//		final LinearLayout layoutNaoBaixa = (LinearLayout) convertView.findViewById(R.id.layoutNaoBaixa);
//		layoutNaoBaixa.setVisibility(View.INVISIBLE);
//		
//		final Spinner motivo = (Spinner) convertView.findViewById(R.id.motivo);
//		String[] list = new String[] { "Selecionar Motivo","aaa", "bbb"};
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(contexto, android.R.layout.simple_list_item_1,list);
//		motivo.setAdapter(adapter);
//
//		
//		Button efetuarBaixa = (Button) convertView.findViewById(R.id.btnEfetuarBaixa);
//		efetuarBaixa.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(contexto, "Efetuar Baixa - "+codBarraNomeDestinatario.getText(), Toast.LENGTH_LONG).show();
//			}
//		});
//		
//		Button naoBaixa = (Button) convertView.findViewById(R.id.btnNao);
//		naoBaixa.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				layoutNaoBaixa.setVisibility(v.VISIBLE);
//				motivo.setSelection(0);
//			}
//		});
//
//		Button confirmar = (Button) convertView.findViewById(R.id.btnConfirmar);
//		confirmar.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(contexto, "Ok"+codBarraNomeDestinatario.getText(), Toast.LENGTH_LONG).show();
//			}
//		});
//		
//		Button cancelar = (Button) convertView.findViewById(R.id.btnCancelar);
//		cancelar.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				layoutNaoBaixa.setVisibility(v.INVISIBLE);
//			}
//		});
//		return convertView;
//	}
//
//
//}
