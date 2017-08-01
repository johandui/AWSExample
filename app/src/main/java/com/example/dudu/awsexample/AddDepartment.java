package com.example.dudu.awsexample;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.dudu.awsexample.Models.Department;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.example.dudu.awsexample.Models.ManagerClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Dudu on 7/30/17.
 */

public class AddDepartment extends AppCompatActivity {
    private Button btn_signup;
    private EditText input_name;
    private EditText input_street;
    private EditText input_desc;
    private Spinner spinner_start;
    private Spinner spinner_end;
    private Spinner spinner_cat;
    private double lng, lat;
    private AmazonDynamoDBClient dbClient;
    private DynamoDBMapper mapper;
    private GoogleMap googleMap;
    private Geocoder geocoder;
    private GoogleApiClient client;
    private int[] startTime;
    ManagerClass managerClass;
    private static final int[] toggleBtn = {
            R.id.toggleButton0, R.id.toggleButton1, R.id.toggleButton2,
            R.id.toggleButton3, R.id.toggleButton4, R.id.toggleButton5,
            R.id.toggleButton6,
    };
    private ToggleButton[] days = new ToggleButton[7];
    private void setupControls(){
        btn_signup = (Button)findViewById(R.id.btn_create);
        input_name = (EditText) findViewById(R.id.input_name);
        input_street = (EditText) findViewById(R.id.input_street);
        input_desc = (EditText) findViewById(R.id.input_desc);
        spinner_start = (Spinner)findViewById(R.id.spinner_start);
        spinner_end = (Spinner)findViewById(R.id.spinner_end);
        spinner_cat = (Spinner)findViewById(R.id.spinner_category);
        for(int i = 0; i < toggleBtn.length; i++){
            days[i] = (ToggleButton)findViewById(toggleBtn[i]);
        }

        setupSpinner(spinner_start);
        setupSpinner(spinner_end);
        setupSpinner(spinner_cat);
        btn_signup.setOnClickListener(onClick);


    }
    private View.OnClickListener onClick = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if(v == btn_signup){

                if(!TextUtils.isEmpty(input_name.getText().toString())){
                    if(!TextUtils.isEmpty(input_desc.getText().toString())){
                        if (!TextUtils.isEmpty(input_street.getText().toString())) {
                            getPosition();
                            insertData();
                        }
                    }
                }
            }
        }
    };
    private void insertData(){
        dbClient = new AmazonDynamoDBClient(managerClass.getCredentialsProvider());
        mapper = new DynamoDBMapper(dbClient);
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Department dp = new Department();
                dp.setCategoryId(String.valueOf(spinner_cat.getSelectedItemId()));
                dp.setDescription(input_desc.getText().toString());
                dp.setLat(lat);
                dp.setLng(lng);
                dp.setName(input_name.getText().toString());
                dp.setEndTime(Double.parseDouble(String.valueOf(spinner_cat.getSelectedItem())));
                dp.setStartTime(Double.parseDouble(String.valueOf(spinner_cat.getSelectedItem())));
                dp.setStreet(input_street.getText().toString());

                mapper.save(dp);
            }
        };
        Thread thread = new Thread(run);
        thread.start();

    }
    private void setupSpinner(Spinner e){
        List list = new ArrayList();
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        for(int i = 0; i < 24; i++){
            ad.add(i);
        }
        e.setAdapter(ad);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_department);
        setupControls();
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        managerClass = new ManagerClass(getApplication());
    }
    private void getPosition(){
        String street = input_street.getText().toString();
        try {
            List<Address> addresses = geocoder.getFromLocationName(street + ", Ulaanbaatar", 20);
            if(addresses.size() > 0){
                lat = addresses.get(0).getLatitude();
                lng = addresses.get(0).getLongitude();
                Toast.makeText(getApplicationContext(), addresses.get(0).getAddressLine(0), Toast.LENGTH_LONG).show();
            }
            else
            Toast.makeText(getApplicationContext(), "putang", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
