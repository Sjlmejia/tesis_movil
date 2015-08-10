package com.tesis.capacitysoft;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.tesis.capacitysoft.servidor.HttpGetData;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Prueba extends Activity {
	TextView prueba;
	Button iniciar;

	String data;
	JSONArray ja;
	String data2;
	JSONArray ja2;
	String data3;
	JSONArray ja3;
	String data4;
	JSONArray ja4;
	String data8;
	JSONArray ja8;
	String aux1;
	Handler h = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "Esta prueba ya la rindio no hay como volverla a dar" ,
					
					5000).show();
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prueba);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
		Bundle bolsa=getIntent().getExtras();
		Bundle bolsa1=getIntent().getExtras();
		aux1=bolsa1.getString("cedula");
		final String aux=bolsa.getString("Evaluacion");
		prueba=(TextView)findViewById(R.id.lbl_nombreprueba);
	
		prueba.setText(aux);
		iniciar=(Button)findViewById(R.id.btn_iniciaeva);

		 iniciar.setOnClickListener(new View.OnClickListener(){
				
	        	public void onClick(View view){
	        		
	        		new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							ja = null;
							HttpGetData v=new HttpGetData();
							data = v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaidevaluacion.php?tema="+aux);
							
							if (data.length()>0){
								try {
									
									ja = new JSONArray(data);
									String aux2=ja.getString(0);
									data8 = v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaidevaluacions.php?evaluacion_id="+aux2+"&capacitado_id="+aux1);
									if(data8.length()>0){
										h.sendEmptyMessage(1);
										 finish();
									}else{
									data4 = v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperacalificacionevaluacions.php?id="+aux2);
									
									ja4=new JSONArray(data4);
									String aux6=ja4.getString(0);
									Bundle bolsa2 =new Bundle();
									Bundle bolsa5 =new Bundle();
									 bolsa5.putString("calificacion", aux6);
									 bolsa2.putString("evaid", aux2);
									data2 = v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperapreguntasevaluacion.php?id="+aux2);
								if(data2.length()>0){
									ja2 = new JSONArray(data2);
									ArrayList<String> lista =new ArrayList<String>();
									for( int j = 0; j <= ja2.length()-1; j ++){
									data3 = v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperadescripcionpregunta.php?id="+ja2.getString(j));
									if (data3.length()>0){
									ja3=new JSONArray(data3);
									 lista.add(ja3.getString(0));
									
									}}
					                Intent i = new Intent(Prueba.this, Prueba1.class );
								    Bundle bolsa =new Bundle();
								    Bundle bolsa1=new Bundle();
								    Bundle bolsa4=new Bundle();
								    bolsa4.putString("tema", aux);
								    bolsa1.putString("cedula", aux1);
									bolsa.putStringArrayList("Pregunta", lista);
									i.putExtras(bolsa1);
									i.putExtras(bolsa);
									i.putExtras(bolsa2);
									i.putExtras(bolsa4);
									i.putExtras(bolsa5);
									startActivity(i);
								}
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
							}else{
								h.sendEmptyMessage(1);
								finish();
							}
						}
					}).start();	
	        	}  													});
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
