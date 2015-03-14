package com.aaron.unitconverter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



public class MainActivity extends Activity implements OnClickListener, AdapterView.OnItemSelectedListener{
    //Called when the activity is first created
	
	private Spinner SpinnerUnit;
	private EditText inputValue;
	private Spinner SpinnerFrom;
	private Spinner SpinnerTo;
	private Button ButtonConvert;
	private EditText ResultView;
    ArrayAdapter<String> unitarray;
    ArrayAdapter<String> unitarrayadapter;
    private Strategy currentStrategy;
    @SuppressWarnings("unused")
    private Strategy lastStrategy;
    private String unitfrom;
    private String unitto;
    private static MainActivity instance;

	@SuppressLint("ShowToast")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        
        
       
        //to get the parameter passed from the URI that has launched this app
        final Intent intent = getIntent();
        String scheme = intent.getScheme();
        if(scheme != null){
	        final Uri myURI=intent.getData();
	        String queryString = new String();
	        
	        if(myURI!=null)
	        {
	        	queryString = myURI.getQuery();
	        }
	        @SuppressWarnings("unused")
			String split1 = "&";
	        @SuppressWarnings("unused")
			String split2 = "=";
	     
	        if (queryString != null)
	        {
	            final String[] arrParameters = queryString.split("&");
	            for (final String tempParameterString : arrParameters)
	            {
	                final String[] arrTempParameter = tempParameterString.split("=");
	                if (arrTempParameter.length >= 2)
	                {
	                    @SuppressWarnings("unused")
						final String parameterKey = arrTempParameter[0];
	                    final String parameterValue = arrTempParameter[1];
	                    //do something with the parameters
	                    Toast.makeText(this, parameterValue, 500).show();
	                }
	            }
	        }
	       	
        }
       	
       
        
        setContentView(R.layout.activity_main);
        
        //set spinner list
        SpinnerUnit = (Spinner)findViewById(R.id.SpinnerUnit);
        SpinnerUnit.setOnItemSelectedListener(this);
        
        unitarray=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        unitarray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerUnit.setAdapter(unitarray);
        unitarray.add(getResources().getString(R.string.unit1));
        unitarray.add(getResources().getString(R.string.unit2));
        unitarray.add(getResources().getString(R.string.unit3));
        unitarray.add(getResources().getString(R.string.unit4));
        unitarray.add(getResources().getString(R.string.unit5));
        unitarray.add(getResources().getString(R.string.unit6));
        unitarray.add(getResources().getString(R.string.unit7));
        unitarray.add(getResources().getString(R.string.unit8));
        unitarray.setNotifyOnChange(true);
        
        
        SpinnerFrom = (Spinner)findViewById(R.id.SpinnerFrom);
        SpinnerFrom.setOnItemSelectedListener(this);
        
        
        SpinnerTo = (Spinner)findViewById(R.id.SpinnerTo);
        SpinnerTo.setOnItemSelectedListener(this);
        
        unitarrayadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item); 
        unitarrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerFrom.setAdapter(unitarrayadapter);
        SpinnerTo.setAdapter(unitarrayadapter);
        
        unitarrayadapter.setNotifyOnChange(true);
        
        //get by id TextViewComputeResult
        ResultView = (EditText)findViewById(R.id.TextViewComputeResult);
        ResultView.setClickable(false);
        
      //get by id Convert
        ButtonConvert = (Button)findViewById(R.id.Convert);
        ButtonConvert.setOnClickListener(this);
        //get by id EditTextValue
        inputValue = (EditText)findViewById(R.id.EditTextValue);
        
        //initialization
       currentStrategy = new TemperatureStrategy();
       
       lastStrategy = currentStrategy;
       
       instance = this;
       
       
        
    }
    
	public static MainActivity getInstance(){
		return instance;
	}
	
    public void onItemSelected(AdapterView<?> parent){	
    }
    
    public void onNothingSelected(AdapterView<?> parent){
    	
    }
    
    public void onItemSelected(AdapterView<?> parent, View v,
    		int position, long id){
        
    	if(v.getParent() == SpinnerUnit){
        		
        		switch(position){
        		case 0:
        			setStrategy(new TemperatureStrategy());
        			break;
        			
        		case 1:
        			setStrategy( new WeightStrategy());
        			break;
        		
        		case 2:
        			setStrategy(new LengthStrategy());
        			break;
        			
        		case 3:
        			setStrategy(new PowerStrategy());
        			break;
        			
        		case 4:
        			setStrategy(new EnergyStrategy());
        			break;
        			
        		case 5:
        			setStrategy(new VelocityStrategy());
        			break;
        			
        		case 6:
        			setStrategy(new AreaStrategy());
        			break;
        			
        		case 7:
        			setStrategy(new VolumeStrategy());
        			break;
        		}
        		
        		
        		fillFromToSpinner(position);
        		
        		SpinnerFrom.setSelection(0);
        		SpinnerTo.setSelection(0);
        		
        		//If only first spinner is selected and
        		//the from and to spinners are not clicked at all
        		unitfrom = (String)(SpinnerFrom.getItemAtPosition(0).toString());
        		unitto = (String)(SpinnerTo.getItemAtPosition(0).toString());
        		
        		//reset the result
        		ResultView.setText("");
        		
        		
    	}
    	else if(v.getParent() == SpinnerFrom){
    		unitfrom = (String)(SpinnerFrom.getSelectedItem().toString());	
    	}
    	
    	else if(v.getParent() == SpinnerTo){
    		unitto = (String)(SpinnerTo.getSelectedItem().toString());
    		}
    	}  	
    
    
    private void fillFromToSpinner(int position){
    	
    	switch(position)
    	{
    	case 0:
    		UnitTemperature();
    		break;
    		
    	case 1:
    		UnitWeight();
    		break;
    		
    	case 2:
    		UnitLength();
    		break;
    		
    	case 3:
    		UnitPower();
    		break;
    		
    	case 4:
    		UnitEnergy();
    		break;
    		
    	case 5:
    		UnitVelocity();
    		break;
    		
    	case 6:
    		UnitArea();
    		break;
    		
    	case 7:
    		UnitVolume();
    		break;
    	}
    	
    }
    
    private void UnitTemperature(){
    	unitarrayadapter.clear();
    	unitarrayadapter.add(getResources().getString(R.string.temperatureunitc));
    	unitarrayadapter.add(getResources().getString(R.string.temperatureunitf));
    	unitarrayadapter.notifyDataSetChanged();
    }
    
    private void UnitWeight(){
    	unitarrayadapter.clear();
    	unitarrayadapter.add(getResources().getString(R.string.weightunitkg));
    	unitarrayadapter.add(getResources().getString(R.string.weightunitgm));
    	unitarrayadapter.add(getResources().getString(R.string.weightunitlb));
    	unitarrayadapter.add(getResources().getString(R.string.weightunitounce));
    	unitarrayadapter.add(getResources().getString(R.string.weightunitmg));
    	unitarrayadapter.notifyDataSetChanged();
    }
    
    private void UnitLength(){
    	unitarrayadapter.clear();
    	unitarrayadapter.add(getResources().getString(R.string.lengthunitmile));
    	unitarrayadapter.add(getResources().getString(R.string.lengthunitkm));
    	unitarrayadapter.add(getResources().getString(R.string.lengthunitm));
    	unitarrayadapter.add(getResources().getString(R.string.lengthunitcm));
    	unitarrayadapter.add(getResources().getString(R.string.lengthunitmm));
    	unitarrayadapter.add(getResources().getString(R.string.lengthunitinch));
    	unitarrayadapter.add(getResources().getString(R.string.lengthunitfeet));
    }
    
    private void UnitPower(){
    	unitarrayadapter.clear();
    	unitarrayadapter.add(getResources().getString(R.string.powerunitwatts));
    	unitarrayadapter.add(getResources().getString(R.string.powerunithorseposer));
    	unitarrayadapter.add(getResources().getString(R.string.powerunitkilowatts));
    }
    
    private void UnitEnergy(){
    	unitarrayadapter.clear();
    	unitarrayadapter.add(getResources().getString(R.string.energyunitcalories));
    	unitarrayadapter.add(getResources().getString(R.string.energyunitjoules));
    	unitarrayadapter.add(getResources().getString(R.string.energyunitkilocalories));
    	
    }
    
    private void UnitVelocity(){
	    unitarrayadapter.clear();
	    unitarrayadapter.add(getResources().getString(R.string.velocityunitkmph));
	    unitarrayadapter.add(getResources().getString(R.string.velocityunitmilesperh));
	    unitarrayadapter.add(getResources().getString(R.string.velocityunitmeterpers));
	    unitarrayadapter.add(getResources().getString(R.string.velocityunitfeetpers));
    }
    
    private void UnitArea(){
    	unitarrayadapter.clear();
    	unitarrayadapter.add(getResources().getString(R.string.areaunitsqkm));
    	unitarrayadapter.add(getResources().getString(R.string.areaunitsqmiles));
    	unitarrayadapter.add(getResources().getString(R.string.areaunitsqm));
    	unitarrayadapter.add(getResources().getString(R.string.areaunitsqcm));
    	unitarrayadapter.add(getResources().getString(R.string.areaunitsqmm));
    	unitarrayadapter.add(getResources().getString(R.string.areaunitsqyard));	
    }
    
    private void UnitVolume(){
    	unitarrayadapter.clear();
    	unitarrayadapter.add(getResources().getString(R.string.volumeunitlitres));
    	unitarrayadapter.add(getResources().getString(R.string.volumeunitmillilitres));
    	unitarrayadapter.add(getResources().getString(R.string.volumeunitcubicm));
    	unitarrayadapter.add(getResources().getString(R.string.volumeunitcubiccm));
    	unitarrayadapter.add(getResources().getString(R.string.volumeunitcubicmm));
    	unitarrayadapter.add(getResources().getString(R.string.volumeunitcubicfeet));
    }
    
    public void onClick(View v){
    	if(v == ButtonConvert){
    		if(!inputValue.getText().toString().equals("")){
		    	double in = Double.parseDouble(inputValue.getText().toString());
		    	double result = currentStrategy.Convert(unitfrom, unitto, in);
		    	ResultView.setText(Double.toString(result));
    		}
    		else {
    			ResultView.setText("");
    		}
    	}
    }
    
    private void setStrategy(Strategy s){
    	
    	lastStrategy = currentStrategy;
    	currentStrategy = s;
    	//make the last strategy eligible for garbage collection
    	lastStrategy = null;
    }   
}
