package com.tesis.capacitysoft;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import com.tesis.capacitysoft.servidor.HttpGetData;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
public class Menu_principal1 extends Activity {
	Button recursos;
	
	Button consultar_capacitado;

	Button informacion_capacitacion;
	EditText recurso;

	JSONArray ja;
	JSONArray jas2;
	JSONArray jas3;
	JSONArray jas4;
	JSONArray jas5;
	JSONArray jas6;
	JSONArray jas7;
	String data;
	JSONArray jas8;
	String data8;
	JSONArray jas9;
	String data9;
	String data2;
	String data3;
	String data4;
	String data5;
	String data6;
	String data7;
	String aux;
	String aux1;
	String aux3;
	TextView texto;
	

	Button registrar;
	Handler h1 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "No hay cupos disponibles", 3000).show();
			
		}	
	};
	Handler h2 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "No hay  recursos para esta capacitacion", 4000).show();
			
		}	
	};
	Handler h = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "Registrado correctamente", 4000).show();
			
		}	
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_principal1);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
		recursos=(Button)findViewById(R.id.btn_recurso1);
		
		consultar_capacitado=(Button)findViewById(R.id.btn_concap1);
		registrar=(Button)findViewById(R.id.btn_registrasecap);
		informacion_capacitacion=(Button)findViewById(R.id.btn_iCapacitacion1);
		recurso=(EditText)findViewById(R.id.lbl_recursos);
		
		 Bundle bolsa=getIntent().getExtras();
		 Bundle bolsa1=getIntent().getExtras();
		 Bundle bolsa2=getIntent().getExtras();
		 aux=bolsa.getString("Capacitacion");
		 aux1=bolsa1.getString("cedula");
		 aux3=bolsa2.getString("idcap");

		registrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final HttpGetData vd=new HttpGetData();
				Executors.newSingleThreadExecutor().submit(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						data8=vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperanumcap.php?id="+aux3);
					
							try {
								jas8=new JSONArray(data8);
								String aux=jas8.getString(0);
								Integer a=Integer.parseInt(aux);
								data9=vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaidregistro.php?id="+aux3);
								jas9=new JSONArray(data9);
				
								if(jas9.length()>=a){
									h1.sendEmptyMessage(1);
									
								}else{
									vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/registroasistencia.php?capacitado_id="+
								aux1+"&capacitacion_id="+aux3);
									
					            	h.sendEmptyMessage(1);
					            	finish();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
					
		            	
					}
				});
				
			}
		});
		 informacion_capacitacion.setOnClickListener(new View.OnClickListener(){
				
	        	public void onClick(View view){
	        		
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							ja = null;
							data = httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperatodocapacitacion.php?tema="+aux);
							if (data.length()>0){
								try {
									ja = new JSONArray(data);
									String aux2=ja.getString(0);
									ArrayList<String> lista =new ArrayList<String>();
									for ( int j = 0; j <= ja.length()-1; j ++ ) {
									      lista.add(ja.getString(j));
									}
					                Intent i = new Intent(Menu_principal1.this, Informacion_capacitacion.class );
								    Bundle bolsa =new Bundle();
									bolsa.putStringArrayList("Capacitacion", lista);
									i.putExtras(bolsa);
									startActivity(i);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							}
						}
					}).start();	
	        	}  													});
		recursos.setOnClickListener(new View.OnClickListener(){
			
        	public void onClick(View view){
        		
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						ja = null;
						data = httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaidCap.php?tema="+aux);
						if (data.length()>0){
							try {
								ja = new JSONArray(data);
								String aux2=ja.getString(0);
								data2 = httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaRecurso.php?capacitacion="+aux2);
								Intent i = new Intent(Menu_principal1.this, Recursos.class );
							    Bundle bolsa =new Bundle();
								if (data2.length()>0){
									jas2= new JSONArray(data2);
									ArrayList<String> lista =new ArrayList<String>();
									for ( int j = 0; j <= jas2.length()-1; j ++ ) {
									      lista.add(jas2.getString(j));
									}
									if(lista.isEmpty()){
									h2.sendEmptyMessage(1);
									}else{
									bolsa.putStringArrayList("Nombre",lista);
									i.putExtras(bolsa);
									startActivity(i);
								 }
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
						}
					}
				}).start();	
        	}  													});
		consultar_capacitado.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
						new Thread(new Runnable() {
					@Override
					public void run() {
						Bundle bolsa =new Bundle();
						Intent i=new Intent(Menu_principal1.this, Consultar_Capacitador1.class);
						jas3 = null;
						jas4=null;
						jas5=null;
						data3 = httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaidCap.php?tema="+aux);
						ArrayList<String> lista =new ArrayList<String>();
						if (data3.length()>0){
							try {
								jas3=new JSONArray(data3);
								String aux3=jas3.getString(0);
								data4=httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaidCapacitador.php?Capacitacion_id="+aux3);
								if(data4.length()>0){
									jas4=new JSONArray(data4);
								
								for(int j=0;j<=jas4.length()-1;j++){
								data5=httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperacedulaCapacitador.php?id="+jas4.getString(j));	
									if(data5.length()>0){
										jas5=new JSONArray(data5);
								data6=httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperanombreCCapacitador.php?cedula="+jas5.getString(0));	
									if(data6.length()>0){
										jas6=new JSONArray(data6);
										lista.add(jas6.getString(2)+" "+jas6.getString(0)+" "+jas6.getString(1));
									
									}
									}
								}
								if(lista.isEmpty()){
									lista.add("No hay Capacitadores");
								}else{
									bolsa.putStringArrayList("Nombre", lista);
									i.putExtras(bolsa);
									startActivity(i);
								}	
									
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
					}
}).start();
			}
			
			
			
		});
		
	}
	public String httpGetData(String mURL){
		
		String response="";
		mURL= mURL.replace(" ", "%20");
		HttpClient httpclient = new  DefaultHttpClient();
		HttpGet httpget = new HttpGet(mURL);
		
		ResponseHandler<String> responsehandler = new BasicResponseHandler();
		try {
			response = httpclient.execute(httpget, responsehandler);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
		
		
	}
	
	//Clase interna para ejecutar tareas en segundo plano con dialogos
	public class activity_principal extends AsyncTask<Void, Void, Void>{
		//metodo que se va a ejecutar antes del hilo por de debajo
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		//hilo que se va a ejecutar por debajo lo mismo que tienes en el hilo del boton
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
		//metodo final luego de ejecutar el hilo costoso
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
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
