package com.tesis.capacitysoft;


import java.util.ArrayList;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;

import com.tesis.capacitysoft.servidor.HttpGetData;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") public class Respuesta extends Activity {
  LayoutInflater inf;
  TextView nombre;
  JSONArray ja;
  String data;
  JSONArray ja2;
  String data2;
  JSONArray ja3;
  String data3;
  JSONArray ja4;
  String data4;
  JSONArray ja5;
  String data5;
  JSONArray ja6;
  String data6;
  JSONArray ja7;
  String data7;
  JSONArray ja8;
  String data8;
  RadioButton ra;
  CheckBox ch;
  TextView cedula;
  Button guardar;
  String aux2;
   String aux5;
   ArrayList<Integer> listasauxiliar=new ArrayList<Integer>();
    ArrayList<Object> listasauxiliar2=new ArrayList<Object>();
    ArrayList<Object> listasauxiliar3=new ArrayList<Object>();
       

   Double a;
   Double respuesta;
  Handler h1 = new Handler(){

    @Override
    public void handleMessage(Message msg) {
      // TODO Auto-generated method stub
      super.handleMessage(msg);
      
      Toast.makeText(getApplicationContext(), "Se actualizo la respuesta", 3000).show();
    }
    
  };
  Handler h = new Handler(){

    @Override
    public void handleMessage(Message msg) {
      // TODO Auto-generated method stub
      super.handleMessage(msg);
      
      Toast.makeText(getApplicationContext(), "Se guardo la respuesta", 3000).show();
    }
    
  };
   ArrayList<CheckBox> listas2;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.respuesta);
     getActionBar().setDisplayHomeAsUpEnabled(true);
          getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
          getActionBar().setIcon(
          new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
          getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
  
     Bundle bolsa=getIntent().getExtras();
     Bundle bolsa1=getIntent().getExtras();
     Bundle bolsa2=getIntent().getExtras();
     Bundle bolsa3=getIntent().getExtras();
     Bundle bolsa4=getIntent().getExtras();
     final String aux3=bolsa1.getString("cedula");
     final String aux4=bolsa2.getString("evaid");
     final String aux=bolsa.getString("Pregunta");
    final String aux6=bolsa4.getString("calificacionp");
    
  //   cedula.setText(aux4);
     guardar=(Button)findViewById(R.id.btn_guardarRespuesta);
     
     //final LinearLayout ly=new LinearLayout(Respuesta.this);
    // nombre.setText(aux);

     ja = null;
     final LinearLayout pantalla=(LinearLayout)findViewById(R.id.lyt_respuesta);
    new Thread(new Runnable() {
        
        @Override
        public void run() {
          //ly.setOrientation(ly.VERTICAL);
          //final ArrayList<RadioButton> listas=new ArrayList<RadioButton>();
          
          HttpGetData v=new HttpGetData();
           
          data = v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaridpreguntita.php?descripcion="+aux);
          if(data.length()>0){
            try {
              ja=new JSONArray(data);
              aux2=ja.getString(0);
              data3 = v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperaidrespuestas.php?id="+aux2);
          data2 = v.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperarespuestas.php?id="+aux2);
          if(data2.length()>0){
            listas2=new ArrayList<CheckBox>();
            ja2=new JSONArray(data2);
            ja4=new JSONArray(data3);
            Respuesta.this.runOnUiThread(new Runnable() {
              
              @Override
              public void run() {
                // TODO Auto-generated method stub
                  for(int i=0;i<=ja2.length()-1;i++) {
                              
                              //RadioButton pswOpcion = new RadioButton(MainActivity.this);
                     ch=new CheckBox(Respuesta.this);
                            
                      try {
                        ch.setText( ja2.getString(i));
                        ch.setId(ja4.getInt(i));
                      } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                      }  
                            listas2.add(ch);
                            //ly.addView(ch);
                             pantalla.addView(ch);       
                           } 
              }
            });
             
            
            
          }
            }catch (JSONException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            } 
            
          }
           guardar.setOnClickListener(new View.OnClickListener() {
              
              @Override
              public void onClick(View v) {
                
                    // TODO Auto-generated method stub
                    //HttpGetData l=new HttpGetData();
                
                final HttpGetData vd=new HttpGetData();
              
                      Executors.newSingleThreadExecutor().submit(new Runnable() {
                              @Override
                              public void run() {
                                
                        
                          try {
                        	  data8=vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperarrespuestascorrecta.php?pregunta_id="+aux2);
                            ja8=new JSONArray(data8);
                                if(ja8.length()>0){
                              
                              a=Double.parseDouble(aux6);
                             
                              respuesta=a/ja8.length();
                              
                              String respuesta2=respuesta.toString();
                              
                      for(int i=0;i<=listas2.size()-1;i++){
                    	
                        if(listas2.get(i).isChecked()){
                        	
                          final Object aux=listas2.get(i).getId();
                                    data6 = vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperarcorrecto.php?id="+aux);
                                    if (data6.length()>0){
                                    
                                ja6=new JSONArray(data6);
                                if(ja6.getString(0).equals("1")){
                                  listasauxiliar2.add(aux);
                                }else{
                                  listasauxiliar3.add(aux);
                                }
                                    }
                                  listasauxiliar.add(listas2.get(i).getId());
                            } 
                          }//final listas 2for
                          int suma=listasauxiliar2.size()+listasauxiliar3.size();
                          vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/eliminarregistro.php?capacitado_id="+aux3+
                                "&pregunta_id="+aux2+"&evaluacion_id="+aux4);
                          for(int i=0;i<=suma-1;i++){
                             if(!listasauxiliar2.isEmpty()){
                             vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/registroevaluacion.php?capacitado_id="+aux3+"&pregunta_id="+aux2+"&respuesta_id="
                                        +listasauxiliar2.get(i)+"&calificacion_estudiante="+respuesta2+"&evaluacion_id="+aux4);
                             }
                             if(!listasauxiliar3.isEmpty()){
                               final Double califica=-Double.parseDouble(respuesta2);
                              final String calificacion2=califica.toString();
                            
                                //aux+ "&id="+aux5+"&calificacion_estudiante="+aux6+"&evaluacion_id="+aux4);
                             vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/registroevaluacion.php?capacitado_id="+aux3+"&pregunta_id="+aux2+"&respuesta_id="
                                          +listasauxiliar3.get(i)+"&calificacion_estudiante="+calificacion2+"&evaluacion_id="+aux4); 
                              
                              }
                                  h.sendEmptyMessage(1);
                                  finish();
                          }
                              }//final dat8
                                else{

                            final ArrayList<Integer> listasauxiliar=new ArrayList<Integer>();
                            final ArrayList<Object> listasauxiliar2=new ArrayList<Object>();
                            final ArrayList<Object> listasauxiliar3=new ArrayList<Object>();
                            a=Double.parseDouble(aux6);
                            respuesta=a/listas2.size();
                            String respuesta2=aux6;
                    for(int i=0;i<=listas2.size()-1;i++){
                      
                      if(listas2.get(i).isChecked()){
                        final Object aux=listas2.get(i).getId();
                                  data6 = vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/recuperarcorrecto.php?id="+aux);
                                  if (data6.length()>0){
                              ja6=new JSONArray(data6);
                              if(ja6.getString(0).equals("1")){
                                listasauxiliar2.add(aux);
                              }else{
                                listasauxiliar3.add(aux);
                              }
                                  }
                                listasauxiliar.add(listas2.get(i).getId());
                          } 
                        }//final listas 2for
                        int suma=listasauxiliar2.size()+listasauxiliar3.size();
                        vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/eliminarregistro.php?capacitado_id="+aux3+
                              "&pregunta_id="+aux2+"&evaluacion_id="+aux4);
                        for(int i=0;i<=suma-1;i++){
                          if(!listasauxiliar2.isEmpty()){
                           vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/registroevaluacion.php?capacitado_id="+aux3+"&pregunta_id="+aux2+"&respuesta_id="
                                      +listasauxiliar2.get(i)+"&calificacion_estudiante="+respuesta2+"&evaluacion_id="+aux4);
                          }
                           if(!listasauxiliar3.isEmpty()){
                             final Double califica=-Double.parseDouble(respuesta2);
                            final String calificacion2=califica.toString();
                          
                              //aux+ "&id="+aux5+"&calificacion_estudiante="+aux6+"&evaluacion_id="+aux4);
                           vd.httpGetData("http://siscap.shiriculapo.com/siscap-webservice/registroevaluacion.php?capacitado_id="+aux3+"&pregunta_id="+aux2+"&respuesta_id="
                                        +listasauxiliar3.get(i)+"&calificacion_estudiante="+calificacion2+"&evaluacion_id="+aux4); 
                            
                            }
                                h.sendEmptyMessage(1);
                                finish();
                        }
                            
                                }
                                } catch (JSONException e1) {
                          // TODO Auto-generated catch block
                          e1.printStackTrace();
                        }
                                  }
                              });//final del hilo
                      
              
              //  data3 = l.httpGetData("http://192.168.43.20/ejemplo/guardarRespuesta.php?capacitadoid="+aux3+"&pregunta_id="+tipoCapacitado.getText());

                  // TODO Auto-generated method stub
                  
              }
            });

        }
        
        
      }).start();
    
    
      
//pantalla.addView(new CheckBox(ly.));


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