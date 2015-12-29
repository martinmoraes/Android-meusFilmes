package br.com.escolaarcadia.muesfilmes.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import br.com.escolaarcadia.muesfilmes.Comunicacao;
import br.com.escolaarcadia.muesfilmes.R;
import br.com.escolaarcadia.muesfilmes.UserPicture;

public class EnviarActivity extends Activity {
    private ImageView imagemView;
    private Bitmap imagemBitmap;
    private EditText edTitulo;
    private EditText edAno;
    private EditText edGenero;
    private EditText edPontos;
    private Uri fotoURI = null;
    private final int FOTO_CAMERA = 1;
    private final int GALERIA_FOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityenviar);
        imagemView = (ImageView) findViewById(R.id.imageView);
        edTitulo = (EditText) findViewById(R.id.titulo);
        edAno = (EditText) findViewById(R.id.ano);
        edGenero = (EditText) findViewById(R.id.genero);
        edPontos = (EditText) findViewById(R.id.pontos);
    }


    public void bateFoto(View view) {
        String fotoArquivo = UUID.randomUUID().toString() + ".jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fotoArquivo);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fotoArquivo);
        values.put(MediaStore.Images.Media.DESCRIPTION, "Uma Foto");
        fotoURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent fotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI);
        startActivityForResult(fotoIntent, FOTO_CAMERA);
    }

    public void abreGaleria(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Selecione..."), GALERIA_FOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        UserPicture up = null;
        if (resultCode == RESULT_OK) {
            if (requestCode == FOTO_CAMERA) {
                up = new UserPicture(fotoURI, getContentResolver());
            } else if (requestCode == GALERIA_FOTO) {
                up = new UserPicture(intent.getData(), getContentResolver());
            }
            try {
                imagemBitmap = up.getBitmap();
                imagemView.setImageBitmap(imagemBitmap);
            } catch (IOException e) {
                Log.e("GALERIA", "Falha ao carregar a imagem.", e);
            }
        } else {
            Toast.makeText(this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
        }
    }

    public void enviaMenssagem(View view) {
        if (imagemBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imagemBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            InputStream myInputStream = new ByteArrayInputStream(stream.toByteArray());
            RequestParams params = new RequestParams();
            params.setForceMultipartEntityContentType(true);
           /* byte[] myByteArray = stream.toByteArray();
            params.put("arq", new ByteArrayInputStream(myByteArray), "image.png");*/

            params.put("arq", myInputStream);
            params.put("titulo", edTitulo.getText().toString());
            params.put("ano", edAno.getText().toString());
            params.put("nota", edPontos.getText().toString());
            params.put("genero", edGenero.getText().toString());

            AsyncHttpClient cliente = new AsyncHttpClient();
            cliente.post(
                    Comunicacao.urlEnviaPOST,
                    params,
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Comunicacao.setNovoPost();
                            Toast.makeText(getApplication(), "Postado", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getApplication(), "Falha de comunicação!!!\n" + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
            Toast.makeText(this, "Enviando.", Toast.LENGTH_SHORT).show();
            imagemBitmap = null;
            edTitulo.setText("");
            edAno.setText("");
            edGenero.setText("");
            edPontos.setText("");
            imagemView.setPadding(220, 220, 220, 220);
            imagemView.setImageResource(R.mipmap.fotobranco);

        } else {
            Toast.makeText(this, "Selecione uma image.", Toast.LENGTH_SHORT).show();
        }
    }


}
