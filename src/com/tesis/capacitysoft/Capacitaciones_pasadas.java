package com.tesis.capacitysoft;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.tesis.capacitysoft.servidor.HttpGetData;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
public class Capacitaciones_pasadas extends Activity {
	TextView prueba;
	ListView capacitaciones;
	//Button historial;
	JSONArray ja;
	String data;
	JSONArray ja2;
	String data2;
	JSONArray ja3;
	String data3;
	
	Double calificacion_final;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capacitacionespasadas);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
		
		 capacitaciones=(ListView)findViewById(R.id.listaCapacitacionespasadas);
	
		// historial=(Button)findViewById(R.id.btn_hCapacitaciones);
		 Bundle bolsa=getIntent().getExtras();
		 
		 final String idcap=bolsa.getString("cedula");
		 //prueba.setText(bolsa.getString("Nombre"));
		ArrayList<String> listaCap=new ArrayList<String>();

		// ArrayList<String> lista=new ArrayList<String>();
		 final ArrayAdapter<String> aa=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaCap);
		capacitaciones.setAdapter(aa);
		
		 //lista=bolsa.getStringArrayList("Nombre");
	
		listaCap.addAll(bolsa.getStringArrayList("Nombre"));
		aa.notifyDataSetChanged();
		//capacitaciones.setOnClickListener(l)

		capacitaciones.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final Intent i = new Intent(Capacitaciones_pasadas.this, Informacion_capacitacionp.class );
			
			    final Bundle bolsa1=new Bundle();
			    
				final String listChoice = (String) ( capacitaciones.getItemAtPosition(position)) ; 
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						HttpGetData hgd=new HttpGetData();
						ja = null;
						data = hgd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperatodocapacitacion.php?tema="+listChoice);
						data2 = hgd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaidevaluacion.php?tema="+listChoice);
						
						if (data.length()>0){
							try {
								ja = new JSONArray(data);
								ja2=new JSONArray(data2);
								data3 = hgd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperacalificacionrespuestas.php?id="+ja2.getString(0)+"&capacitado_id="+idcap);
								//String aux2=ja.getString(0);
								if (data3.length()>0){
									ja3=new JSONArray(data3);
									calificacion_final=Double.parseDouble(ja3.getString(0));
								}else{
									calificacion_final=0.0;
								}
								
								
			            	String cal=calificacion_final.toString();
								ArrayList<String> lista =new ArrayList<String>();
								for ( int j = 0; j <= ja.length()-1; j ++ ) {
								      lista.add(ja.getString(j));
								}
				               // Intent i = new Intent(Menu_principal.this, Informacion_capacitacion.class );
							    Bundle bolsa =new Bundle();
							    bolsa.putStringArrayList("Capacitacion",lista);
								bolsa1.putString("calificacion", cal);
								i.putExtras(bolsa);
								i.putExtras(bolsa1);
								startActivity(i);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}	
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
