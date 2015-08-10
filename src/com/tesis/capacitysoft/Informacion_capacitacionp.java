package com.tesis.capacitysoft;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Informacion_capacitacionp extends Activity {
	String aux;
	TextView tema;
	TextView lugar;
	TextView f_inicio;
	TextView f_fin;
	TextView h_inicio;
	TextView h_fin;
	TextView calificacion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.informacion_capacitacionpasada);
		 getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0288D1")));
	        getActionBar().setIcon(
	        new ColorDrawable(getResources().getColor(android.R.color.transparent)));  
	        getActionBar().setTitle(Html.fromHtml("<font color='#757575' size=3 >Siscap </font>"));
		ArrayList<String> listaRec=new ArrayList<String>();
		Bundle bolsa=getIntent().getExtras();
		Bundle bolsa1=getIntent().getExtras();
		listaRec.addAll(bolsa.getStringArrayList("Capacitacion"));
		 tema=(TextView)findViewById(R.id.lbl_temap);
		 lugar=(TextView)findViewById(R.id.lbl_lugarp);
		 f_inicio=(TextView)findViewById(R.id.lbl_iniciop);
		 f_fin=(TextView)findViewById(R.id.lbl_finp);
		 h_inicio=(TextView)findViewById(R.id.lbl_hiniciop);
		 h_fin=(TextView)findViewById(R.id.lbl_hfip);
		 String cal=bolsa1.getString("calificacion");
		 calificacion=(TextView)findViewById(R.id.lbl_calificacionh);
		 tema.setText(listaRec.get(0));
		 lugar.setText(listaRec.get(1));
		 f_inicio.setText(listaRec.get(2));
		 f_fin.setText(listaRec.get(3));
		 h_inicio.setText(listaRec.get(4));
		 h_fin.setText(listaRec.get(5));
		 if(cal.equals("null")){
			 calificacion.setText("0.0");
		 }else{
			 calificacion.setText(cal);
		 }
		

	
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
