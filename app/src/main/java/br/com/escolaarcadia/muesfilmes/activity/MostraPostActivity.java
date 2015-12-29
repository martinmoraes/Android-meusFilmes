package br.com.escolaarcadia.muesfilmes.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.escolaarcadia.muesfilmes.R;

public class MostraPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymostrapost);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        String msg = intent.getStringExtra("MSG");
        String url = intent.getStringExtra("URL");

        textView.setText(msg);

        if (url.contains("MEU APP LOCAL")) {
            url = url.split(":")[1];
            Log.d("MEU_APP", "Povoando o adapter local");
            int img = getResources().getIdentifier(url, "mipmap", getApplicationContext().getPackageName());
            imageView.setImageResource(img);
        } else {
            Picasso.with(getApplicationContext())
                    .load(url)
                    .error(R.mipmap.foto)
                    .into(imageView);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mostra_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
