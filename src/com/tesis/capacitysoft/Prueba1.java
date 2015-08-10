package com.tesis.capacitysoft;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

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

import android.telephony.SmsManager;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class Prueba1 extends Activity {
	ListView preguntas;
	Button guardar;
	//TextView calificacion;
	Double a;
	Double respuesta;
	String data;
	JSONArray ja;
	String data1;
	JSONArray jas1;
	String data2;
	JSONArray ja2;
	String data3;
	JSONArray ja3;
	String data4;
	JSONArray ja4;
	String data5;
	JSONArray ja5;
	String data6;
	JSONArray ja6;
String tp;
	Chronometer prueba;
	double calificacion_final;
	String horario;
	static final int unico=773672;
	Handler h1 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "Su calificacíon es: "+calificacion_final +"  Se la envio a su telefono" ,
					
					3000).show();
		}
		
	};
	Handler h = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "Su prueba a finalizado"+calificacion_final ,
					
					5000).show();
		}
		
	};
	Handler h2 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "Tiempo para realizar la prueba: " +horario,
					
					5000).show();
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prueba1);
		 
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
		preguntas=(ListView)findViewById(R.id.list_pregunta);
		guardar=(Button)findViewById(R.id.btn_califica);
		prueba=(Chronometer)findViewById(R.id.cronometroprueba);
		//calificacion=(TextView)findViewById(R.id.lbl_calificacion);
	
		Bundle bolsa=getIntent().getExtras();
		Bundle bolsa1=getIntent().getExtras();
		Bundle bolsa2=getIntent().getExtras();
		Bundle bolsa3=getIntent().getExtras();
		Bundle bolsa5=getIntent().getExtras();
		final String aux1=bolsa1.getString("cedula");
		final String aux2=bolsa2.getString("evaid");
		final String aux3=bolsa3.getString("calificacion");
		final String tema1=bolsa5.getString("tema");
		//calificacion.setText(aux1);
		
		final ArrayList<String> listaCap=new ArrayList<String>();
		// ArrayList<String> lista=new ArrayList<String>();
		 final ArrayAdapter<String> aa=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaCap);
		preguntas.setAdapter(aa);
		listaCap.addAll(bolsa.getStringArrayList("Pregunta"));
		aa.notifyDataSetChanged();
		a=Double.parseDouble(aux3);
		respuesta=a/listaCap.size();
		String respuesta2=respuesta.toString();
		final Bundle bolsa4=new Bundle();
		//bolsa4.putString("calificacionr", respuesta2);
	
		prueba.start();
		
		Executors.newSingleThreadExecutor().submit(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				final HttpGetData hgd=new HttpGetData();
				jas1=null;
            	data1 = hgd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperatiempoevaluacion.php?id="+aux2);
            	try {
					jas1=new JSONArray(data1);
					horario=jas1.getString(0);
					String aux1=horario.substring(0, 2);
				    String aux22=horario.substring(3, 5);
				    String aux3=horario.substring(6);
					h2.sendEmptyMessage(1);
				   final String aux4="0";
				    Integer a=Integer.parseInt(aux1);
				    Integer b=Integer.parseInt(aux22);
				    Integer c=Integer.parseInt(aux3);
				    Long d=(long) (a*3600000);
				    Long e=(long) (b*60000);
				    Long f=(long) (c*1000);
				    Long suma=d+e+f;
				    TimerTask task = new TimerTask() {
				      @Override
				      public void run() {
				       
				        finish();//Destruimos esta activity para prevenit que el usuario retorne aqui presionando el boton Atras.
				        data3 = hgd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/registrofinevaluacion.php?activo="+aux4+"&id="+aux2);
				        h.sendEmptyMessage(1);
				      }
				    };

				    Timer timer = new Timer();
				    timer.schedule(task, suma);//Pasado los 6 segundos dispara la tarea
				  
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});


		 guardar.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Executors.newSingleThreadExecutor().submit(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							HttpGetData vd=new HttpGetData();
							ja = null;
						
							data2 = vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperarCorreoCapacitador1.php?id="+aux1);
			            	data = vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperacalificacionrespuestas.php?id="+aux2);
			            	if(data.length()>0){
			            		calificacion_final=0.0;
			            	try {
								ja=new JSONArray (data);
								for(int i=0; i<ja.length();i++){
									calificacion_final=calificacion_final+Double.parseDouble((String) ja.get(i));
									
								}
								if(calificacion_final<0.0){
									calificacion_final=0.0;
								}
								ja2=new JSONArray (data2);
								String aux=ja2.getString(0);
								//Intent y=new Intent(android.content.Intent.ACTION_SEND);
								//y.setType("message/rfc822");
								//y.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{aux});
								//y.putExtra(android.content.Intent.EXTRA_SUBJECT, "Nota de Evaluacion");
								//y.putExtra(android.content.Intent.EXTRA_TEXT, calificacion_final);
								//startActivity(y.createChooser(y, "Enviando Correo"));
								//vd.httpGetData("http://192.168.1.5/ejemplo/actualizarAcEva.php?id="+aux2+"&activo="+"0");
								h1.sendEmptyMessage(1);
								finish();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			          
			            	
			            	}
						}
					});
				
					
				}
			});
		
		preguntas.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Intent i = new Intent(Prueba1.this, Respuesta.class );
			   final  Bundle bolsa =new Bundle();
			   final Bundle bolsa1=new Bundle();
			   final Bundle bolsa2=new Bundle();
				final String listChoice = (String) ( preguntas.getItemAtPosition(position)) ; 
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						HttpGetData hgd=new HttpGetData();
					
						data6 = hgd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperarcalificacionrespuestas.php?descripcion="+listChoice+"&evaluacion_id="+aux2);
						try {
							ja6 =new JSONArray(data6);
							String calificacion=ja6.getString(0);
							
							bolsa.putString("Pregunta",listChoice);
							bolsa1.putString("cedula", aux1);
							bolsa2.putString("evaid", aux2);
							bolsa4.putString("calificacionp", calificacion);
							i.putExtras(bolsa1);
							i.putExtras(bolsa);
							i.putExtras(bolsa2);
							i.putExtras(bolsa4);
							startActivity(i);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
				}).start();
			
				  

			
				// TODO Auto-generated method stub
				
			}});
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
