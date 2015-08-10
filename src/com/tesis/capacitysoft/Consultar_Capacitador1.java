package com.tesis.capacitysoft;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONException;

import com.tesis.capacitysoft.servidor.HttpGetData;



import android.os.Bundle;

import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
public class Consultar_Capacitador1 extends Activity {
	TextView entrada;
	ListView capacitadores;

	String data;
	JSONArray ja;
	String data1;
	JSONArray ja1;
	String aux1,aux2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultar_capacitado_1);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
	
		capacitadores=(ListView)findViewById(R.id.list_capacitadores);
	
		ArrayList<String> listaRec=new ArrayList<String>();
		 Bundle bolsa=getIntent().getExtras();

		 //ArrayList<String> lista=new ArrayList<String>();
		 final ArrayAdapter<String> aa=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaRec);
		capacitadores.setAdapter(aa);
		 
		 //lista=bolsa.getStringArrayList("Nombre");
	
		listaRec.addAll(bolsa.getStringArrayList("Nombre"));
		aa.notifyDataSetChanged();
		capacitadores.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				final Intent i = new Intent(Consultar_Capacitador1.this, Enviar_Llamar.class );
			   final Bundle bolsa =new Bundle();
			   final Bundle bolsa1 =new Bundle();
			    final Bundle bolsa2=new Bundle();
				String listChoice = (String) ( capacitadores.getItemAtPosition(position)) ;
				final String aux=listChoice.substring(0,10);
				Executors.newSingleThreadExecutor().submit(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						HttpGetData va=new HttpGetData();
						data = va.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperarTelefono.php?cedula="+aux);
						data1 = va.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperarCorreoCapacitador.php?cedula="+aux);
						try {
							ja=new JSONArray(data);
							ja1=new JSONArray(data1);
							 aux1=ja.getString(0);
						aux2=ja1.getString(0);
						bolsa2.putString("telefono", aux1);
						bolsa1.putString("correo", aux2);
						bolsa.putString("Capacitador",aux);
						i.putExtras(bolsa1);
						i.putExtras(bolsa2);
						i.putExtras(bolsa);
						startActivity(i);
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
