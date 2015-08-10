package com.tesis.capacitysoft;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONException;

import com.keyes.youtube.OpenYouTubePlayerActivity;
import com.tesis.capacitysoft.servidor.HttpGetData;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Recursos  extends Activity {
	ListView recursosob;
	String data;
	JSONArray ja;
	String data1;
	JSONArray ja1;
	String data2;
	JSONArray ja2;

	final Handler h1 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "No existe video para esta capactacion" ,
					
					3000).show();
		}
		
	};
	final Handler h = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "No existe pdf para esta capacitacion" ,
					
					3000).show();
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recursos);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
		recursosob=(ListView)findViewById(R.id.listarecursos);
		
		ArrayList<String> listaRec=new ArrayList<String>();
		 Bundle bolsa=getIntent().getExtras();
		 //ArrayList<String> lista=new ArrayList<String>();
		 final ArrayAdapter<String> aa=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaRec);
		recursosob.setAdapter(aa);
		 
		 //lista=bolsa.getStringArrayList("Nombre");
	
		listaRec.addAll(bolsa.getStringArrayList("Nombre"));
		aa.notifyDataSetChanged();
		final HttpGetData h=new HttpGetData();
	
		recursosob.setOnItemClickListener(new ListView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//Intent i = new Intent(Recursos.this, Recusos_dos.class );
			    Bundle bolsa =new Bundle();
				final String listChoice = (String) ( recursosob.getItemAtPosition(position)) ; 
				//data = h.httpGetData("http://192.168.1.7/ejemplo/recuperaRecurso.php?capacitacion="+listChoice);
				bolsa.putString("Recurso",listChoice);
				
				Executors.newSingleThreadExecutor().submit(new Runnable() {
								@Override
								public void run() {
									HttpGetData h=new HttpGetData();
									data1 = h.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaRecursopdf.php?nombre="+listChoice);
									
									try {
										ja=new JSONArray(data1);
										if(ja.getString(0).equals("null")){
											data = h.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaRecursovideo.php?nombre="+listChoice);
											ja2=new JSONArray(data);
												String video=ja2.getString(0);
												String aux2=video.split("=")[1];
												Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + aux2));
												startActivity(intent);
												
										}else{
											
											//ja1=new JSONArray(data1);
											Uri pat = Uri.parse("https://"+ja.getString(0));
											  Intent intent = new Intent(Intent.ACTION_VIEW);
											   intent.setData(Uri.parse(ja.getString(0)));
											   startActivity(intent);
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
										
									
								}
							});	
			        	
				
			        	}  	});
				
			
				// TODO Auto-generated method stub
				
		
		
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