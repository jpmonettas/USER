package com.androidapps.user;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.sql.Date;

public class UserActivity extends Activity {
    private static final int PICK_CATEGORY = 0;
    private EditText amountText=null;
    private DatePicker expenseDate=null;
    private EditText categoryText = null;
    private ToggleButton currencyToggle =null;
    private RecordsDBOpenHelper db = null;
    
    
    private OnClickListener mCashButtonListener = new OnClickListener() {
        public void onClick(View v) {
        	ContentValues values = new ContentValues(); 
        	values.put("amount", Float.parseFloat(amountText.getText().toString()));
        	values.put("date",(new Date(expenseDate.getYear()-1900, expenseDate.getMonth(), expenseDate.getDayOfMonth()).toString()));
        	values.put("category", categoryText.getText().toString());
        	values.put("currency", currencyToggle.getText().toString());
            db.getWritableDatabase().insert("exprecords",null , values);
            finish();
        }
    };
    
    private OnClickListener mReportsButtonListener = new OnClickListener() {
        public void onClick(View v) {
        	showReport();
        }
    };
    
    private OnClickListener mDumpButtonListener = new OnClickListener() {
        public void onClick(View v) {
        	try {
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();

                if (sd.canWrite()) {
                  	Log.d("deb", "Data : " + data);
                  	Log.d("deb", "SD : " + sd);
                    File currentDB = new File(data + "/data/com.androidapps.user/databases/", "user");
                    File backupDB = new File(sd, "Download/user-dump");

                    if (currentDB.exists()) {
                        FileChannel src = new FileInputStream(currentDB).getChannel();
                        FileChannel dst = new FileOutputStream(backupDB).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                    }
                }else{
                	Log.d("ex", "Cant write");
                }
            } catch (Exception e) {
            	Log.d("ex", e.getMessage());
            }
            finish();
        }
    };
    
    private OnClickListener mCategoryTextListener = new OnClickListener() {
        public void onClick(View v) {
          pickCategory();
        }
    };
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button cashButton = (Button)findViewById(R.id.cash);
        cashButton.setOnClickListener(mCashButtonListener);
        
        Button reportsButton = (Button)findViewById(R.id.reportsButton);
        reportsButton.setOnClickListener(mReportsButtonListener);
    
        Button dumpButton = (Button)findViewById(R.id.dumpButton);
        dumpButton.setOnClickListener(mDumpButtonListener);
        
        categoryText=(EditText)findViewById(R.id.categoryText);
        categoryText.setOnClickListener(mCategoryTextListener);
        
        expenseDate=(DatePicker)findViewById(R.id.expenseDate);
        amountText=(EditText)findViewById(R.id.amountText);
        currencyToggle=(ToggleButton)findViewById(R.id.currencyToggle);
        db=new RecordsDBOpenHelper(this);
    
    }
    
    @Override
    public void onDestroy(){
    	db.close();
    	super.onDestroy();
    }
    
    
    private void showReport(){
    	Intent intent = new Intent(this, PerMonthDetailReport.class);
    	startActivity(intent);
    }
    
    private void pickCategory() {
        Intent intent = new Intent(this, CategorySelectionActivity.class);
        startActivityForResult(intent, PICK_CATEGORY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the request went well (OK) and the request was PICK_CATEGORY
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_CATEGORY) {
            categoryText.setText(data.getStringExtra("selectedCategory"));
        }
    }
}