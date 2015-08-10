package com.tesis.capacitysoft;
import java.util.ArrayList;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONException;

import com.tesis.capacitysoft.servidor.HttpGetData;


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
public class Capacitaciones extends Activity {
	TextView prueba;
	ListView capacitaciones;
	//Button historial;
	JSONArray ja;
	String data;
	
	String data1;
	JSONArray ja1;
	String data2;
	JSONArray ja2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capacitaciones);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
	
		 capacitaciones=(ListView)findViewById(R.id.listaCapacitaciones);
		
		// historial=(Button)findViewById(R.id.btn_hCapacitaciones);
		 Bundle bolsa=getIntent().getExtras();
		 Bundle bolsa1=getIntent().getExtras();
		 final String aux1=bolsa1.getString("cedula");
		 //prueba.setText(bolsa.getString("Nombre"));
		ArrayList<String> listaCap=new ArrayList<String>();

		// ArrayList<String> lista=new ArrayList<String>();
		 final ArrayAdapter<String> aa=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaCap);
		capacitaciones.setAdapter(aa);
		 
		 //lista=bolsa.getStringArrayList("Nombre");
		 final HttpGetData hgd=new HttpGetData();
		listaCap.addAll(bolsa.getStringArrayList("Nombre"));
		aa.notifyDataSetChanged();
		//capacitaciones.setOnClickListener(l)

		capacitaciones.setOnItemClickListener(new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				

				final Intent i = new Intent(Capacitaciones.this, Menu_principal.class );
				final Intent p = new Intent(Capacitaciones.this, Menu_principal1.class );
			   final  Bundle bolsa =new Bundle();
			    final Bundle bolsa1=new Bundle();
			    final Bundle bolsa2=new Bundle();
				final String listChoice = (String) ( capacitaciones.getItemAtPosition(position)) ; 
				Executors.newSingleThreadExecutor().submit(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						data2= hgd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaidcapacitacion.php?tema="+listChoice);
						try {
							ja2=new JSONArray(data2);
							String auxq=ja2.getString(0);
							data1 = hgd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperatodoR.php?capacitacion_id="+ja2.getString(0)+"&capacitado_id="+aux1);
							if(data1.length()>0){
								bolsa.putString("Capacitacion",listChoice);
								bolsa1.putString("cedula", aux1);
								i.putExtras(bolsa1);
								i.putExtras(bolsa);
								startActivity(i);	
							}else{
								bolsa.putString("Capacitacion",listChoice);
								bolsa1.putString("cedula", aux1);
								bolsa2.putString("idcap", auxq);
								p.putExtras(bolsa2);
								p.putExtras(bolsa1);
								p.putExtras(bolsa);
								startActivity(p);	
							}
							
								
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
			
				  

			
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
