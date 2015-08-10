package com.tesis.capacitysoft;
import java.util.Random;
import java.util.concurrent.Executors;

import org.json.JSONArray;

import com.tesis.capacitysoft.servidor.HttpGetData;
import com.tesis.capacitysoft.validacion.validacion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.telephony.SmsManager;
import android.text.Html;


public class Recuperar_Contrasenia extends Activity {
	Button Enviar;
	
	EditText email;
	String data1;
	JSONArray ja1;
	String pwd;
	  String celular1;
	ProgressDialog pdialog = null;
	Context context = null;
	
	String rec, subject, textMessage;
	Handler h = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "Mensaje Enviado", 3000).show();
		}
		
	};
	Handler h2 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "Mensaje Fallido intente mas tarde", 3000).show();
		}
		
	};
	Handler h1 = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			Toast.makeText(getApplicationContext(), "Número de celular no encontrado", 3000).show();
		}
		
	};
	String getCadenaAlfanumAleatoria (int longitud){
		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while ( i < longitud){
		char c = (char)r.nextInt(255);
		if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
		cadenaAleatoria += c;
		i ++;
		}
		}
		return cadenaAleatoria;
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recuperar_contrasenia);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
		email=(EditText)findViewById(R.id.txt_recuperarCont);
		Enviar=(Button)findViewById(R.id.btn_enviar);
		
		Enviar.setOnClickListener(new OnClickListener() {
		String aux= getCadenaAlfanumAleatoria(5);
		
			@Override
			public void onClick(View v) {
				sendSMSMessage();
			}
			
			 
			protected void sendSMSMessage() {
			      Log.i("Send SMS", "");
			      final String aux1,aux2,aux3,telefono;
			    
			    final  String phoneNo = email.getText().toString();
			    validacion vd=new validacion();
				if (!vd.isValidPhone(phoneNo)) {
					email.setError("Campo Invalido");
				}else{
					celular1=phoneNo;
				}
				if(celular1!=null){
				
			     final String message =aux;
			     aux1=phoneNo.substring(0,4);
			     aux2=phoneNo.substring(4,7);
			     aux3=phoneNo.substring(7);
			     telefono=aux1+"-"+aux2+"-"+aux3;
			  	Executors.newSingleThreadExecutor().submit(new Runnable() {
			  	  @Override
		            public void run() {
			  		  HttpGetData hg=new HttpGetData();
			  	 	data1=  hg.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/concelular.php?celular="+telefono
	            			);
		    	if(data1.length()>0){
			      try {
			   
			         SmsManager smsManager = SmsManager.getDefault();
			         hg.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/actualizarcontraseniaC.php?celular="+telefono+
		            			"&contrasenia="+message);
			         smsManager.sendTextMessage(phoneNo, null,"Su nueva contraseña es:"+ message, null, null);
			        
			        h.sendEmptyMessage(1);
			        finish();
			      } catch (Exception e) {
			        h2.sendEmptyMessage(1);
			         e.printStackTrace();
			      }
			  	  }else{
			  		h1.sendEmptyMessage(1);
						     
			  	  }
		    	
			  	  }});
			  	}
				
			}
		 });
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
