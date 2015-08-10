package com.tesis.capacitysoft;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import com.tesis.capacitysoft.servidor.HttpGetData;
import com.tesis.capacitysoft.validacion.validacion;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
public class Registro extends Activity {
EditText nombre;
String nombre1;
EditText apellido;
String apellido1;
EditText cedula;
String cedula1;
EditText h_clinica;
EditText direccion;
String direccion1;
EditText telefono_domicilio;
EditText celular;
String celular1;
EditText email;
String email1;
EditText cargo;
String cargo1;
EditText contrasenia;
String contrasenia1;
EditText contraseniar;
String contraseniar1;
Spinner sector;
Spinner tipoCapacitado;
EditText telefonoOficina;
EditText extensionOficina;
Button registrar;
Button cancelar; 
JSONArray ja;
String data;
JSONArray ja2;
String data3;
JSONArray ja3;
String data4;
JSONArray ja4;
String data5;
JSONArray ja5;
String data2;
ArrayList<String> lista;
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
Handler h1 = new Handler(){

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		
		Toast.makeText(getApplicationContext(), "Resgistro correctamente la informacion", 3000).show();
	}
	
};
Handler h2 = new Handler(){

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		
		Toast.makeText(getApplicationContext(), "No se realizo el registro hay campos invalidos", 4000).show();
	}
	
};
Handler h3 = new Handler(){

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		
		Toast.makeText(getApplicationContext(), "Contraseñas no coinciden", 4000).show();
	}
	
};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registrar_capacitado);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
	nombre=(EditText)findViewById(R.id.txt_nom);
	apellido=(EditText)findViewById(R.id.txt_apellidos);
	cedula=(EditText)findViewById(R.id.txt_cedula);
	h_clinica=(EditText)findViewById(R.id.txt_hClinica);
	direccion=(EditText)findViewById(R.id.txt_direccion);
	telefono_domicilio=(EditText)findViewById(R.id.txt_teleDomicilio);
	celular=(EditText)findViewById(R.id.txt_celular);
	email=(EditText)findViewById(R.id.txt_email);
	cargo=(EditText)findViewById(R.id.txt_cargo);
	sector=(Spinner)findViewById(R.id.txt_sector);
	tipoCapacitado=(Spinner)findViewById(R.id.txt_tCapacitado);
	telefonoOficina=(EditText)findViewById(R.id.txt_tOficina);
	extensionOficina=(EditText)findViewById(R.id.txt_extOficina);
	registrar=(Button)findViewById(R.id.btn_registroCap);
	cancelar=(Button)findViewById(R.id.btn_cancelar);
	contrasenia=(EditText)findViewById(R.id.txt_contraseniaC);
	contraseniar=(EditText)findViewById(R.id.txt_contraseniaR);
	Bundle bolsa=getIntent().getExtras();
	ArrayList<String> listasec=new ArrayList<String>();
	Bundle bolsa2=getIntent().getExtras();
	ArrayList<String> listatip=new ArrayList<String>();
	//listasec.add("Hola");
	//listasec.add("Adios");
	listasec.addAll(bolsa.getStringArrayList("Nombre"));
	listatip.addAll(bolsa.getStringArrayList("Tipo"));
	final ArrayAdapter<String> aa=new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, listasec );
	sector.setAdapter(aa);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 //lista=bolsa.getStringArrayList("Nombre");
	aa.notifyDataSetChanged();
	final ArrayAdapter<String> a2=new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, listatip );
	tipoCapacitado.setAdapter(a2);
	a2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			 //lista=bolsa.getStringArrayList("Nombre");
	a2.notifyDataSetChanged();

	//ArrayAdapter<String> spinner_adapter =new  ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,listasec);
		//ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, listasec, android.R.layout.simple_spinner_item);
		
		//spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	//sector.setAdapter(spinner_adapter);
	cancelar.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
	});
	final validacion vd=new validacion();
	registrar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			
			if ((!vd.isLetter(nombre.getText().toString()))||nombre.getText().toString().equals("")) {
				nombre.setError("Campo Invalido");
			
			}else{
				nombre1=nombre.getText().toString();
			}	if ((!vd.isLetter(apellido.getText().toString()))||apellido.getText().toString().equals("")) {
				apellido.setError("Campo Invalido");
			
			}else{
			 apellido1=apellido.getText().toString();
			}
			if ((!vd.isValidEmail(email.getText().toString()))||email.getText().toString().equals("")) {
				email.setError("Campo Invalido");
			
			}else{
			 email1=email.getText().toString();
			}
			if ((!vd.isLetter(cargo.getText().toString()))||cargo.getText().toString().equals("")) {
				cargo.setError("Campo Invalido");
			
			}else{
			 cargo1=cargo.getText().toString();
			}
			if (!vd.isValidPhone(celular.getText().toString())) {
				celular.setError("Campo Invalido");
			}else{
				celular1=celular.getText().toString();
			}
			if (!vd.isCedula(cedula.getText().toString())) {
				cedula.setError("Campo Invalido");
			}else{
				cedula1=cedula.getText().toString();
			}
			if (contrasenia.getText().toString().equals("")) {
				contrasenia.setError("Campo Vacio");
			}else{
				contrasenia1=contrasenia.getText().toString();
			}
			if (contraseniar.getText().toString().equals("")) {
				contraseniar.setError("Campo Vacio");
			}else{
				contraseniar1=contraseniar.getText().toString();
			}
			if (direccion.getText().toString().equals("")) {
				direccion.setError("Campo Vacio");
			}else{
				direccion1=direccion.getText().toString();
			}
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					HttpGetData vd=new HttpGetData();
				
				
					data4= 	vd.httpGetData("http://192.168.1.5/ejemplo/recuperaRsector.php?nombre="+sector.getSelectedItem());
					data5= 	vd.httpGetData("http://192.168.1.5/ejemplo/recuperaRtipo.php?nombre="+tipoCapacitado.getSelectedItem());
					try {
						ja4=new JSONArray(data4);
						String asector=ja4.getString(0);
						ja5=new JSONArray(data5);
						String atipo=ja5.getString(0);
						if(nombre1!=null&&apellido1!=null&&email1!=null&&cargo1!=null&&celular1!=null&&contrasenia1!=null&&contraseniar1!=null
								&&cedula1!=null){
						if(contrasenia1.equals(contraseniar1)){
							
							data3= 	vd.httpGetData("http://192.168.1.5/ejemplo/registro.php?nombre="+nombre1+
		    					"&apellido="+apellido1+"&cargo="
									+cargo1+"&cedula="+cedula.getText()+"&correo="+email1+
									"&direccion="+direccion1+"&telefono_oficina="+telefonoOficina.getText()
								+"&telefono_domicilio="+telefono_domicilio.getText()+"&ext_telefono_oficina="+extensionOficina.getText()	
								+"&contrasenia="+contrasenia1+"&celular="+celular1+"&num_historia_clinica="+h_clinica.getText()
								+"&sector_id="+asector+"&tipo_Capacitado_id="+atipo
		    					);
							h1.sendEmptyMessage(1);
							finish();
						}else{
							h3.sendEmptyMessage(1);
						}
						}else{
							h2.sendEmptyMessage(1);
						}
					
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		
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
