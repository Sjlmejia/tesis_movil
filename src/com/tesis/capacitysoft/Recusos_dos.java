package com.tesis.capacitysoft;
import java.util.ArrayList;

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
import android.widget.VideoView;

public class Recusos_dos extends Activity {
	Button video;
	Button pdf;

	String aux;
	String data;
	JSONArray ja;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recursos_dos);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
		video=(Button)findViewById(R.id.btn_recursovideo);
		pdf=(Button)findViewById(R.id.btn_recursopdf);
		
		Bundle bolsa=getIntent().getExtras();
		aux=bolsa.getString("Recurso");
		//VideoView video=(VideoView)findViewById(R.id.vi_cap);
		//Uri path = Uri.parse("https://www.youtube.com/watch?v=_l4e0hJGOIY8");

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
		
				 //   video.setVideoURI(path);
			
		//video.start();
		 video.setOnClickListener(new View.OnClickListener(){
	        	public void onClick(View view){
					new Thread(new Runnable() {
						@Override
						public void run() {
							HttpGetData h=new HttpGetData();
							data = h.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaRecursovideo.php?nombre="+aux);
							try {
								ja=new JSONArray(data);
								if(ja.getString(0).equals("null")){
									h1.sendEmptyMessage(1);
								}else{
									String aux=ja.getString(0);
									//String aux2=aux.substring(25);
									
									Intent lVideoIntent = new Intent(null, Uri.parse("ytv://"+"_"+aux), Recusos_dos.this, OpenYouTubePlayerActivity.class);
										startActivity(lVideoIntent);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
								
							
						}
					}).start();	
	        	}  	});
		 pdf.setOnClickListener(new View.OnClickListener(){
	        	public void onClick(View view){
					new Thread(new Runnable() {
						@Override
						public void run() {
							
							HttpGetData v=new HttpGetData();
							data = v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaRecursopdf.php?nombre="+aux);
							try {
								ja=new JSONArray(data);
								
								if(ja.getString(0).equals("null")){
									h.sendEmptyMessage(1);
									
								}else{
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
