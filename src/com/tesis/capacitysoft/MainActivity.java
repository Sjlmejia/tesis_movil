package com.tesis.capacitysoft;




import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;


import com.tesis.capacitysoft.servidor.HttpGetData;
import com.tesis.capacitysoft.validacion.validacion;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
public class MainActivity extends Activity  {
	EditText correo;
	EditText contrasenia;
	ImageView logo;
	Button Login;
	Button Registrar;
	Button Recuperar;
	TextView inicio;
	JSONArray ja;
	JSONArray jas;
	JSONArray jas2;
	String data;
	JSONArray jas3;
	String data3;
	JSONArray jas4;
	String data4;
	JSONArray jas5;
	String data5;
	String data1;
	String data2;
	String cedula;
	Intent i;
	Intent j;
	String prueba;
	String capacita;
	String con2;
	Handler h = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "Campos vacios", 3000).show();
		}
		
	};
	
  	Handler h1 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "Datos Incorrectos", 3000).show();
		}
		
	};
 	Handler h2 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "No tiene Internet revise su conexión", 5000).show();
			
		}
		
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
        
        getActionBar().setIcon(
        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
        correo =(EditText)findViewById(R.id.txt_correoca);
        contrasenia =(EditText)findViewById(R.id.txt_contrasenia);
        Login =(Button)findViewById(R.id.btn_login);
        Recuperar=(Button)findViewById(R.id.btn_recuperarCon);
     
       logo=(ImageView)findViewById(R.id.imageView1);
       validacion v=new validacion();
       final HttpGetData hgd=new HttpGetData();
      
      Login.setOnClickListener(new OnClickListener()  {
    	  
			@Override
			public void onClick(View v)  {
				  ConnectivityManager connMgr = (ConnectivityManager) 
						     getSystemService(Context.CONNECTIVITY_SERVICE);
						       NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
						       if (networkInfo != null && networkInfo.isConnected()) {
				new Thread(new Runnable() {
					
					@Override
					public void run()  {
						// TODO Auto-generated method stub
						
						 jas = null;
					
						
						try {
							data5=hgd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/encriptar.php?contrasenia="+contrasenia.getText());
							//jas5=new JSONArray(data5);
							//con2=jas5.getString(0);
							
								
								if(correo.getText().equals("")||contrasenia.getText().equals("")){
									h.sendEmptyMessage(1);
								}else{
									 data1 = hgd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/login.php?correo="+correo.getText()+"&contrasenia="+data5);
								
								if (data1.length()>0){

						
									jas=new JSONArray(data1);
									
									cedula=jas.getString(0);

								    i= new Intent(MainActivity.this, Menu_Capacitaciones.class );
								    //j= new Intent(Menu_Capacitaciones, Capacitaciones_SR.class );
									Bundle bolsa=new Bundle();
								
							
									 bolsa.putString("Capacitado",cedula);
									
									 i.putExtras(bolsa);
								
								    startActivity(i);

									
								}else{
								    h1.sendEmptyMessage(1);
								}
								}
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					
						
								   
						}//FIN RUN

					
				}).start();
			    }else{
			    	  h2.sendEmptyMessage(1);
			       }
			}
		});
       
        Recuperar.setOnClickListener(new View.OnClickListener(){
            
        	public void onClick(View view){
        		Intent i = new Intent(MainActivity.this, Recuperar_Contrasenia.class );
                startActivity(i);
        		
        	}
        
        													});
    
    }//fin oncreateview


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //@Override
   // public boolean onOptionsItemSelected(MenuItem item) {
      //  switch (item.getItemId()) {
        //    case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
          //      this.finish();
            //    return true;
            //default:
              //  return super.onOptionsItemSelected(item);
        //}
    //}

	

}
