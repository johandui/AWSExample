package com.example.dudu.awsexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.example.dudu.awsexample.Models.Category;
import com.example.dudu.awsexample.Models.ManagerClass;

public class MainActivity extends AppCompatActivity {
    private Category category;
    private ManagerClass m;
    private DynamoDBMapper mapper;

    CognitoCachingCredentialsProvider credentialsProvider = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


}

