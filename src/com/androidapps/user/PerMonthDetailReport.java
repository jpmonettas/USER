package com.androidapps.user;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PerMonthDetailReport   extends Activity {
	
	static class CategoryBuckets{
		public float pesos;
		public float dolars;
		public CategoryBuckets(){pesos=0;dolars=0;}
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.permonthdetailreport);
	    
	    Float totalPesos=0f;
	    Float totalDolars=0f;
	    
	    HashMap<String,CategoryBuckets> buckets = new HashMap<String,CategoryBuckets>();
	    
	    RecordsDBOpenHelper db =null;
	    try {
	    	db = new RecordsDBOpenHelper(this);
            Cursor results = db.getReadableDatabase().rawQuery("select category,currency,amount from exprecords", null);
            if (results.moveToFirst()) {
                for (; !results.isAfterLast(); results.moveToNext()) {
                	String categoryName=results.getString(0);
                	if(buckets.get(categoryName)==null) buckets.put(categoryName, new CategoryBuckets()); 
                	if(results.getString(1).equals("$U")){
                		buckets.get(categoryName).pesos+=results.getFloat(2);
                		totalPesos+=results.getFloat(2);
                	}else if(results.getString(1).equals("US$")){
                		buckets.get(categoryName).dolars+=results.getFloat(2);
                		totalDolars+=results.getFloat(2);
                	}
                }
            }
        } catch (Exception e) {
        }finally{
        	db.close();
        } 
	   
	    
	    ListView listView = (ListView) findViewById(R.id.detailsList);
	    
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	    	android.R.layout.simple_list_item_1, android.R.id.text1, getBucketsStrings(buckets));

	    // Assign adapter to ListView
	    listView.setAdapter(adapter);
	    
	    TextView totalPesosText = (TextView) findViewById(R.id.totalPesos);
	    totalPesosText.setText(totalPesos.toString());
	    
	    TextView totalDolarsText = (TextView) findViewById(R.id.totalDolars);
	    totalDolarsText.setText(totalDolars.toString());
	}
	
	private ArrayList<String> getBucketsStrings(HashMap<String,CategoryBuckets> buckets){
		ArrayList<String> result = new ArrayList<String>();
		for(String category : buckets.keySet()){
			String resultLine = category + " : ";
			if(buckets.get(category).pesos > 0)
				resultLine+=buckets.get(category).pesos + " $U";
			if(buckets.get(category).dolars > 0)
				resultLine+=(buckets.get(category).pesos > 0 ? "  -  " : "") + buckets.get(category).dolars + " US$";
			
			result.add(resultLine);
			
		}
		return result;
	}

}
