package com.tesis.capacitysoft;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class Enviar_correocapacitador extends Activity {

	EditText contenido;
	Button enviar;
	EditText asunto;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enviar_correocapacitador);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
		Bundle bolsa=getIntent().getExtras();
		final String aux=bolsa.getString("Capacitador");
	    enviar=(Button)findViewById(R.id.btn_enviarcorreo); 
	    contenido=(EditText)findViewById(R.id.txt_enviarcorreo);
	    asunto=(EditText)findViewById(R.id.txt_asunto);

		enviar.setOnClickListener(new OnClickListener() {
			  
			@Override
			public void onClick(View v) {
				
				if(!aux.equals("")){
					Intent i=new Intent(android.content.Intent.ACTION_SEND);
					i.setType("message/rfc822");
					i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{aux});
					i.putExtra(android.content.Intent.EXTRA_SUBJECT, asunto.getText().toString());
					i.putExtra(android.content.Intent.EXTRA_TEXT, contenido.getText().toString());
					startActivity(i.createChooser(i, "Enviando Correo"));
				}
			}
			 
			
		 });

}
    @Override
   public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
             //  app icon in action bar clicked; goto parent activity.
              this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
