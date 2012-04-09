package com.androidapps.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CategorySelectionActivity  extends Activity {
	
	
	private Intent intent;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.categories);
	    intent=this.getIntent();
	    
	    ListView listView = (ListView) findViewById(R.id.categoryList);
	    String[] values = new String[] { "Comida",
	    								 "Montevideo M",
	    								 "Combustible",
	    								 "Luz",
	    								 "Lavado Auto",
	    								 "Gastos Comunes",
	    								 "Telefono",
	    								 "ADLS",
	    								 "Celular",
	    								 "Alquiler",
	    								 "Gimnasio",
	    								 "Garage",
	    								 "Boliche",
	    								 "Mecanico",
	    								 "Impuestos Casa",
	    								 "Otros Gral"};

	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	    	android.R.layout.simple_list_item_1, android.R.id.text1, values);

	    // Assign adapter to ListView
	    listView.setAdapter(adapter);
	    
	    listView.setOnItemClickListener(listVewItemClick);
	}
	
	private OnItemClickListener listVewItemClick = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> adapterView, View v, int position,
				long id) {
		String item=(String) adapterView.getItemAtPosition(position);
		intent.putExtra("selectedCategory", item);
		setResult(RESULT_OK, intent);
		finish();
		}
	};
}
