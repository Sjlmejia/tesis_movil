package com.tesis.capacitysoft;


import org.json.JSONArray;
import org.json.JSONException;

import com.tesis.capacitysoft.servidor.HttpGetData;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Enviar_Llamar extends Activity {
	Button correo;
	Button llamar;
	Button sms;
	JSONArray ja;
	String data;

	TextView email;
	TextView tel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email_llamar);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
		llamar=(Button)findViewById(R.id.btn_llamar);
		correo=(Button)findViewById(R.id.btn_correo);
		sms=(Button)findViewById(R.id.btn_sms);
	
		email=(TextView)findViewById(R.id.lbl_correoconsulta);
		tel=(TextView)findViewById(R.id.lbl_celularConsulta);
		 Bundle bolsa=getIntent().getExtras();
		 Bundle bolsa1=getIntent().getExtras();
		 Bundle bolsa2=getIntent().getExtras();
		 
		 final String aux=bolsa.getString("Capacitador");
		 final String aux1=bolsa1.getString("correo");
		 final String aux2=bolsa2.getString("telefono");
		 email.setText(aux1);
		 tel.setText(aux2);

		 llamar.setOnClickListener(new View.OnClickListener(){
	        	public void onClick(View view){
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							ja = null;
							HttpGetData va=new HttpGetData();
							data = va.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperarTelefono.php?cedula="+aux);
							if (data.length()>0){
								try {
									ja = new JSONArray(data);
									String aux2=ja.getString(0);
									 Intent intent = new Intent(Intent.ACTION_CALL);
									   intent.setData(Uri.parse("tel:"+aux2));
									   startActivity(intent);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							}
						}
					}).start();	
	        	}  	});
		 correo.setOnClickListener(new View.OnClickListener(){
	        	public void onClick(View view){
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							ja = null;
							HttpGetData va=new HttpGetData();
							data = va.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperarCorreoCapacitador.php?cedula="+aux);
							if (data.length()>0){
								try {
									ja = new JSONArray(data);
									String aux2=ja.getString(0);
									 //Intent intent = new Intent(Intent.ACTION_CALL);
									 // intent.setData(Uri.parse("tel:"+aux2));
									 //startActivity(intent);
									Intent i = new Intent(Enviar_Llamar.this, Enviar_correocapacitador.class );
									 Bundle bolsa=new Bundle();
									 bolsa.putString("Capacitador",aux2);
									 i.putExtras(bolsa);
									 startActivity(i);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							}
						}
					}).start();	
	        	}  	});
		 sms.setOnClickListener(new View.OnClickListener(){
	        	public void onClick(View view){
					new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							ja = null;
							HttpGetData va=new HttpGetData();
							data = va.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperarTelefono.php?cedula="+aux);
							if (data.length()>0){
								try {
									ja = new JSONArray(data);
									String aux2=ja.getString(0);
									final String p1,p2,p3,tp;
									
									p1=aux2.substring(0,4);
								    p2=aux2.substring(5,8);
								    p3=aux2.substring(9);
								    tp=p1+p2+p3;
							Intent i = new Intent(Enviar_Llamar.this, Email_capacitador.class );
								 Bundle bolsa=new Bundle();
								 bolsa.putString("Capacitador",tp);
								 i.putExtras(bolsa);
								 startActivity(i);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							}
						}
					}).start();	
	        	}  	});
		 
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
