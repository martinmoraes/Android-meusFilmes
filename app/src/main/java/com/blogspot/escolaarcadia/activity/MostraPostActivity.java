package com.blogspot.escolaarcadia.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.escolaarcadia.meusfilmes.R;

public class MostraPostActivity extends Activity {

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
}
