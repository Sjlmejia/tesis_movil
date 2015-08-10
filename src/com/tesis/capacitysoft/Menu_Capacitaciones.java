package com.tesis.capacitysoft;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.tesis.capacitysoft.servidor.HttpGetData;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.Toast;

import android.widget.TextView;

public class Menu_Capacitaciones extends Activity{
	Button historial_capacitaciones;
	Button capacitaciones_actuales;

	Button capacitaciones_sin_registrar;
	String data2;
	JSONArray jas2;
	String data3;
	JSONArray jas3;
	String data4;
	JSONArray jas4;
	TextView cedula;
	ImageView iv;
 	Handler h1 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "No hay Capacitaciones Disponibles", 4000).show();
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultar_historial);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
		capacitaciones_actuales=(Button)findViewById(R.id.btn_CapDispon);
		historial_capacitaciones=(Button)findViewById(R.id.btn_hisCap);

		 Bundle bolsa=getIntent().getExtras();
		 
		 final String aux=bolsa.getString("Capacitado");
			final HttpGetData v=new HttpGetData();


			
			historial_capacitaciones.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub

					new Thread(new Runnable() {
						@Override
						public void run() {
						
							data2 = v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperacapacitacion.php?id="+aux);
						    Bundle bolsa =new Bundle();
						    Intent i = new Intent(Menu_Capacitaciones.this, Capacitaciones_pasadas.class );
							if (data2.length()>0){
								jas2=null;
							
								//String capacitacion=data2.toString();
									try {
										jas2= new JSONArray(data2);
									
									//JSONObject jo=new JSONObject(data2.toString());
									ArrayList<String> lista =new ArrayList<String>();
									//String capacitacion;
									for ( int j = 0; j <= jas2.length()-1; j ++ ) {
									     
									data3= v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperanombrecapacitacionp.php?id="+jas2.getString(j));
									if(data3.length()>0){
										jas3=new JSONArray(data3);
										
										 lista.add(jas3.getString(0));
										
									}
									}
									
									if(lista.isEmpty()){
										h1.sendEmptyMessage(1);
										}else{
									//lista.add(jas2.toString());
									//capacitacion=jo.getString("tema");
									//lista.add(jas2.toString());
									//capacita=jas2.toString();
										Bundle bolsa1=new Bundle();
										bolsa1.putString("cedula", aux);
										i.putExtras(bolsa1);
									bolsa.putStringArrayList("Nombre",lista);
									i.putExtras(bolsa);
									startActivity(i);
							 
									
								 }
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
							}else{
								h1.sendEmptyMessage(1);
							}
							  
						}
					}).start();	
	        	
					
				}
			});
			capacitaciones_actuales.setOnClickListener(new View.OnClickListener(){
	        	public void onClick(View view){
					new Thread(new Runnable() {
						@Override
						public void run() {
						
							data2 = v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperacapacitacion.php?id="+aux);
						    Bundle bolsa =new Bundle();
						    Intent i = new Intent(Menu_Capacitaciones.this, Capacitaciones.class );
							if (data2.length()>0){
								jas2=null;
							
								//String capacitacion=data2.toString();
									try {
										jas2= new JSONArray(data2);
									
									//JSONObject jo=new JSONObject(data2.toString());
									ArrayList<String> lista =new ArrayList<String>();
									//String capacitacion;
									for ( int j = 0; j <= jas2.length()-1; j ++ ) {
									     
									data3= v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperanombrecapacitacion.php?id="+jas2.getString(j));
									if(data3.length()>0){
										jas3=new JSONArray(data3);
										
										 lista.add(jas3.getString(0));
										
									}
									}
									
									if(lista.isEmpty()){
										h1.sendEmptyMessage(1);
									}else{
									//lista.add(jas2.toString());
									//capacitacion=jo.getString("tema");
									//lista.add(jas2.toString());
									//capacita=jas2.toString();
										Bundle bolsa1=new Bundle();
										bolsa1.putString("cedula", aux);
										i.putExtras(bolsa1);
									bolsa.putStringArrayList("Nombre",lista);
									i.putExtras(bolsa);
									startActivity(i);
							 
									
								 }
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
