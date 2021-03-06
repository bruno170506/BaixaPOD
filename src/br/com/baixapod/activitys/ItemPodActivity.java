package br.com.baixapod.activitys;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import br.com.baixapod.R;
import br.com.baixapod.dto.CoordenadaDTO;
import br.com.baixapod.model.ItemPOD;
import br.com.baixapod.model.MovimentoPOD;
import br.com.baixapod.model.Ocorrencia;
import br.com.baixapod.model.PesquisaPOD;
import br.com.baixapod.util.GPS;
import br.com.baixapod.util.Internet;
import br.com.baixapod.webservice.WebServiceConstants;

@SuppressLint("NewApi")
public class ItemPodActivity extends MainActivity {

	private static final int CAPTURA_IMAGEM = 123;
	private static final String NAO_BAIXA = "0";
	private static final String SUCESSO_BAIXA = "1";
	protected static final String EXTENSAO_IMAGEM = ".jpg";
	private TextView numeroHAWBcomNomeDestinatario;
	private TextView rua;
	private TextView numero;
	private TextView bairro;
	private TextView cidade;
	private TextView numTentativas;
	private LinearLayout layoutNaoBaixa;
	private Spinner ocorrencia;
	private List<ItemPOD> listaItens;
	private int posicaoAtual;
	private TextView paginacao;
	private Spinner pesquisaPOD;
	private TableLayout tableLayout;

	private String matricula;
	private GPS gps;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_principal, menu);
		return super.onCreateOptionsMenu(menu);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURA_IMAGEM) {
			if (resultCode == ItemPodActivity.RESULT_OK) {
				Bitmap photo = (Bitmap) data.getExtras().get("data");
				try {
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
					byte[] bytes = stream.toByteArray();
					ItemPOD itemPOD = recuperaItemPODnaPosicao(posicaoAtual);
					String nomeFinalDoArquivo = itemPOD.getN_hawb() + EXTENSAO_IMAGEM;
					FileOutputStream fos = new FileOutputStream(
							Environment.getExternalStorageDirectory() + "/ImagemPOD/" + nomeFinalDoArquivo);
					fos.write(bytes);
					fos.close();
				} catch (Exception e) {
				}
				bateuFoto();
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.enviarMovimento:
			enviarMovimentos();
			break;
		case R.id.sair:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public GPS getGps() {
		if (gps == null) {
			gps = new GPS();
		}
		return gps;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_pod);

		Bundle bundle = getIntent().getExtras();
		matricula = bundle.getString("matricula");

		ocultarTecladoInicialmente();

		tableLayout = (TableLayout) findViewById(R.id.conteudoTabela);
		pesquisaPOD = (Spinner) findViewById(R.id.pesquisaPodTexto);
		pesquisaPOD.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int posicao, long arg3) {
				posicaoAtual = posicao;
				popularItemPod(recuperaItemPODnaPosicao(posicao));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		paginacao = (TextView) findViewById(R.id.paginacao);
		numeroHAWBcomNomeDestinatario = (TextView) findViewById(R.id.n_hawb_nome_destinatario);
		rua = (TextView) findViewById(R.id.rua);
		numero = (TextView) findViewById(R.id.numero);
		bairro = (TextView) findViewById(R.id.bairro);
		cidade = (TextView) findViewById(R.id.cidade);
		numTentativas = (TextView) findViewById(R.id.numTentativasEntrega);
		layoutNaoBaixa = (LinearLayout) findViewById(R.id.layoutNaoBaixa);
		layoutNaoBaixa.setVisibility(View.INVISIBLE);
		ocorrencia = (Spinner) findViewById(R.id.motivo);

		Button efetuarBaixa = (Button) findViewById(R.id.btnEfetuarBaixa);
		efetuarBaixa.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO
				Intent intentCapturaImagem = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File diretorioDestino = new File(Environment.getExternalStorageDirectory() + "/ImagemPOD");
				if (!diretorioDestino.exists()) {
					// se não existir o diretorio e criado
					diretorioDestino.mkdir();
				}
				// ItemPOD itemPOD = recuperaItemPODnaPosicao(posicaoAtual);
				// String nomeFinalDoArquivo = itemPOD.getN_hawb() +
				// EXTENSAO_IMAGEM;
				// Uri uriDestino = Uri.fromFile(new
				// File(Environment.getExternalStorageDirectory() +
				// "/ImagemPOD", nomeFinalDoArquivo));
				// intentCapturaImagem.putExtra(MediaStore.EXTRA_OUTPUT,
				// uriDestino);

				// intentCapturaImagem.putExtra(MediaStore.EXTRA_OUTPUT,
				// uriDestino);
				startActivityForResult(intentCapturaImagem, CAPTURA_IMAGEM);
			}
		});

		Button naoBaixa = (Button) findViewById(R.id.btnNao);
		naoBaixa.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				layoutNaoBaixa.setVisibility(v.VISIBLE);
				ocorrencia.setSelection(0);
			}
		});

		Button confirmar = (Button) findViewById(R.id.btnConfirmar);
		confirmar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Ocorrencia ocSelecionada = (Ocorrencia) ocorrencia.getSelectedItem();
				ItemPOD itemPOD = listaItens.get(posicaoAtual);
				itemPOD.setOcorrencia(ocSelecionada.getCodigo());
				itemPOD.setSucesso(NAO_BAIXA);
				itemPOD.setN_tentativas(String.valueOf(Integer.parseInt(itemPOD.getN_tentativas()) + 1));
				final ArrayList<ItemPOD> itensParaEnviar = new ArrayList<ItemPOD>();
				itensParaEnviar.add(itemPOD);

				if (Internet.temConexaoComInternet(ItemPodActivity.this, matricula)) {
					naoBaixaPelaInternet(itensParaEnviar);
				} else {
					naoBaixaSemInternet(v, itemPOD, itensParaEnviar);
				}
			}
		});

		Button cancelar = (Button) findViewById(R.id.btnCancelar);
		cancelar.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("static-access")
			@Override
			public void onClick(View v) {
				layoutNaoBaixa.setVisibility(v.INVISIBLE);
			}
		});

		posicaoAtual = 0;
		ImageView proximo = (ImageView) findViewById(R.id.next);
		proximo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				posicaoAtual += 1;
				if (posicaoAtual >= listaItens.size()) {
					posicaoAtual -= 1;
				}
				popularItemPod(recuperaItemPODnaPosicao(posicaoAtual));

			}
		});

		ImageView anterior = (ImageView) findViewById(R.id.previous);
		anterior.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				posicaoAtual -= 1;
				if (posicaoAtual < 0) {
					posicaoAtual += 1;
				}
				popularItemPod(recuperaItemPODnaPosicao(posicaoAtual));
			}
		});

		// preenche Motivos Ocorrencia
		popularListaMotivosOcorrencia(getBancoDoCelularDAO().listaOcorrencia());

		// preenche lista ItemPODs
		popularListaPODs(getBancoDoCelularDAO().listaPODs());

		// Verifica se o GPS está habilitado
		if (!getGps().verificaGPSAtivo(ItemPodActivity.this)) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("O GPS está Desabilitado!");
			builder.setMessage("ATIVE SUA LOCALIZAÇÃO!");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			builder.show();
		}

		List<MovimentoPOD> listaMovimentos = getBancoDoCelularDAO().listaMovimentoPODs();
		if (listaMovimentos.size() > 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Existem Movimentos não enviados!");
			builder.setMessage("ENVIE-OS!");
			builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});
			builder.show();
		}

	}

	private void bateuFoto() {
		if (Internet.temConexaoComInternet(this, matricula)) {
			efetuarBaixaPelaInternetComImagem();
		} else {
			efetuarBaixaSemInternetComImagem();
		}
	}

	private void efetuarBaixaPelaInternetComImagem() {

		final ItemPOD itemPOD = recuperaItemPODnaPosicao(posicaoAtual);
		final String nomeArquivoComExtensao = itemPOD.getN_hawb() + EXTENSAO_IMAGEM;
		Uri uriDestino = Uri
				.fromFile(new File(Environment.getExternalStorageDirectory() + "/ImagemPOD", nomeArquivoComExtensao));
		final String sourceFileUri = uriDestino.getPath();

		// faz upload do arquivo e aguarda status 200 OK
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (uploadFile(sourceFileUri, nomeArquivoComExtensao) == 200) {
					runOnUiThread(new Runnable() {
						public void run() {
							final ArrayList<ItemPOD> itensParaEnviar = new ArrayList<ItemPOD>();
							ItemPOD itemPOD = listaItens.get(posicaoAtual);
							itemPOD.setOcorrencia("69");// Entregue
							itemPOD.setSucesso(SUCESSO_BAIXA);
							String n_tentativas = String.valueOf(Integer.parseInt(itemPOD.getN_tentativas()) + 1);
							itemPOD.setN_tentativas(n_tentativas);
							itensParaEnviar.add(itemPOD);

							CoordenadaDTO coordenadaAtual = coordenadaAtual();
							getBancoNaNuvemDAO().efetuarBaixaPods(ItemPodActivity.this, itensParaEnviar,
									coordenadaAtual);
						}
					});
				} else {
					msgToast(ItemPodActivity.this, getString(R.string.baixa_pod_online_failed));
				}
			}
		}).start();

	}

	private void efetuarBaixaSemInternetComImagem() {
		final ItemPOD itemPOD = recuperaItemPODnaPosicao(posicaoAtual);
		final ArrayList<ItemPOD> itensParaEnviar = new ArrayList<ItemPOD>();
		itemPOD.setOcorrencia("69");// Entregue
		itemPOD.setSucesso(SUCESSO_BAIXA);
		itemPOD.setN_tentativas(String.valueOf(Integer.parseInt(itemPOD.getN_tentativas()) + 1));
		itensParaEnviar.add(itemPOD);

		CoordenadaDTO coordenadaAtual = coordenadaAtual();
		getBancoDoCelularDAO().inserirMovimento(coordenadaAtual, itemPOD);
		baixaEfetuadaComSucesso(itensParaEnviar, getString(R.string.baixa_pod_offline_sucess));
	}

	private void preenchePrimeiroDaLista() {
		popularItemPod(recuperaItemPODnaPosicao(0));
	}

	private void atualizaPaginacao() {
		String pos = String.valueOf(posicaoAtual + 1);
		paginacao.setText("N° PODs: " + pos + " de " + listaItens.size());
	}

	private ItemPOD recuperaItemPODnaPosicao(int posicao) {
		ItemPOD item = null;
		try {
			item = listaItens.get(posicao);
		} catch (Exception e) {
		}
		return item;
	}

	private void popularItemPod(ItemPOD itemPOD) {
		if (itemPOD == null) {
			nenhumRegistroEncontrado();
			return;
		}
		tableLayout.setVisibility(View.VISIBLE);
		numeroHAWBcomNomeDestinatario.setText(itemPOD.getN_hawb() + "-" + itemPOD.getNome_desti());
		rua.setText(itemPOD.getRua_desti());
		numero.setText(itemPOD.getNumero_desti());
		bairro.setText(itemPOD.getBairro_desti());
		cidade.setText(itemPOD.getCidade_desti());
		numTentativas.setText(itemPOD.getN_tentativas());
		atualizaPaginacao();
	}

	private void nenhumRegistroEncontrado() {
		tableLayout.setVisibility(View.INVISIBLE);
		paginacao.setText("Nenhum POD para Entrega!");
	}

	private void ocultarTecladoInicialmente() {
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	public void popularListaPODs(List<ItemPOD> listaPODs) {
		this.listaItens = listaPODs;
		popularCampoPesquisa(listaItens);
		popularItemPod(recuperaItemPODnaPosicao(0));
	}

	private void popularCampoPesquisa(List<ItemPOD> listaItens) {
		ArrayList<PesquisaPOD> lista = new ArrayList<PesquisaPOD>();
		for (ItemPOD itemPOD : listaItens) {
			PesquisaPOD obj = new PesquisaPOD();
			obj.setN_hawb(itemPOD.getN_hawb());
			obj.setDescricao(itemPOD.getN_hawb() + "-" + itemPOD.getNome_desti());
			lista.add(obj);
		}
		ArrayAdapter<PesquisaPOD> adapter = new ArrayAdapter<PesquisaPOD>(this, android.R.layout.simple_list_item_1,
				lista);
		pesquisaPOD.setAdapter(adapter);
	}

	public void popularListaMotivosOcorrencia(List<Ocorrencia> listaOcorrencia) {
		ArrayAdapter<Ocorrencia> adapter = new ArrayAdapter<Ocorrencia>(this, android.R.layout.simple_list_item_1,
				listaOcorrencia);
		ocorrencia.setAdapter(adapter);
	}

	private int uploadFile(String sourceFileUri, String nomeArquivoComExtensao) {

		int serverResponseCode = 0;
		File sourceFile = new File(sourceFileUri);
		if (!sourceFile.isFile()) {
			return serverResponseCode;
		}

		try {

			String fileName = sourceFileUri;
			HttpURLConnection conn = null;
			DataOutputStream dos = null;
			String lineEnd = "\r\n";
			String twoHyphens = "--";
			String boundary = "*****";
			int bytesRead, bytesAvailable, bufferSize;
			byte[] buffer;
			int maxBufferSize = 1 * 1024 * 1024;

			// open a URL connection to the Servlet
			FileInputStream fileInputStream = new FileInputStream(sourceFile);
			URL url = new URL(WebServiceConstants.PODs.uploadPOD);
			// Open a HTTP connection to the URL
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use a Cached Copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("uploaded_file", fileName);
			dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename=" + nomeArquivoComExtensao + ""
					+ lineEnd);
			dos.writeBytes(lineEnd);
			// create a buffer of maximum size
			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];
			// read file and write it into form...
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}
			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			serverResponseCode = conn.getResponseCode();
			String result = readStream(conn.getInputStream());// TODO da pra ler
																// o resultado
			fileInputStream.close();
			dos.flush();
			dos.close();
		} catch (MalformedURLException ex) {
			return serverResponseCode;
		} catch (Exception e) {
			return serverResponseCode;
		}
		return serverResponseCode;
	}

	public static String readStream(InputStream in) {
		BufferedReader reader = null;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return builder.toString();
	}

	public void baixaEfetuadaComSucesso(List<ItemPOD> pods, String msg) {
		for (ItemPOD itemPOD : pods) {
			listaItens.remove(itemPOD);
			popularCampoPesquisa(listaItens);
		}
		posicaoAtual = 0;
		preenchePrimeiroDaLista();
		msgToast(ItemPodActivity.this, msg);
	}

	public void baixaPODFailed(String msg) {
		msgToast(ItemPodActivity.this, msg);
	}

	public void sucessoAoEnviarMovimentoPODs() {
		msgToast(ItemPodActivity.this, getString(R.string.envio_movimentos_sucesso));
	}

	public void falhaAoEnviarMovimentoPODs() {
		msgToast(ItemPodActivity.this, getString(R.string.envio_movimentos_falha));
	}

	private void naoBaixaPelaInternet(final ArrayList<ItemPOD> itensParaEnviar) {
		try {
			CoordenadaDTO coordenadaAtual = coordenadaAtual();
			getBancoNaNuvemDAO().efetuarBaixaPods(ItemPodActivity.this, itensParaEnviar, coordenadaAtual);
		} catch (Exception e) {
			msgToast(ItemPodActivity.this, getString(R.string.baixa_pod_online_failed));
		}
	}

	@SuppressWarnings("static-access")
	private void naoBaixaSemInternet(final View v, final ItemPOD itemPOD, final ArrayList<ItemPOD> itensParaEnviar) {
		try {
			CoordenadaDTO coordenadaAtual = coordenadaAtual();
			getBancoDoCelularDAO().inserirMovimento(coordenadaAtual, itemPOD);
			baixaEfetuadaComSucesso(itensParaEnviar, getString(R.string.baixa_pod_offline_sucess));
			layoutNaoBaixa.setVisibility(v.INVISIBLE);
		} catch (Exception e) {
			msgToast(ItemPodActivity.this, getString(R.string.baixa_pod_online_failed));
		}
	}

	private void enviarMovimentos() {
		if (getBancoDoCelularDAO().listaMovimentoPODs().size() == 0) {
			msgToast(ItemPodActivity.this, getString(R.string.nao_tem_movimentos_para_enviar));
		} else {
			if (!Internet.temConexaoComInternet(ItemPodActivity.this, matricula)) {
				msgToast(ItemPodActivity.this, getString(R.string.conecte_a_internet));
			} else {
				try {
					final File diretorioDestino = new File(Environment.getExternalStorageDirectory() + "/ImagemPOD");
					if (!diretorioDestino.exists()) {
						// se não existir o diretorio e criado
						diretorioDestino.mkdir();
					}					
					//upload das imagens arquivadas em /ImagemPOD do celular e envio dos pods gravados no sqlite
					new Thread(new Runnable() {
						@Override
						public void run() {
							String[] list = diretorioDestino.list();
							boolean uploadOK = true;
							for (String nomeArquivoComExtensao : list) {
								String sourceFileUri = diretorioDestino + "/" + nomeArquivoComExtensao;
								if(uploadFile(sourceFileUri, nomeArquivoComExtensao)!= 200){
									uploadOK = false;
									break;
								}
							}
							if(uploadOK){
								runOnUiThread(new Runnable() {
									public void run() {
										getBancoDoCelularDAO().enviarTodosMovimentosPODs(ItemPodActivity.this);
									}
								});
							}else{
								msgToast(ItemPodActivity.this, getString(R.string.envio_movimentos_falha));
							}
						}
					}).start();					
				} catch (Exception e) {
					msgToast(ItemPodActivity.this, getString(R.string.envio_movimentos_falha));
				}
			}

		}
	}

	public CoordenadaDTO coordenadaAtual() {
		return getGps().recuperarCoordenada(ItemPodActivity.this);
	}
}
