package com.example.dudu.awsexample.Models;

import android.content.Context;
import android.widget.Toast;


import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.DefaultSyncCallback;
import com.amazonaws.regions.Regions;

import java.util.List;

/**
 * Created by Dudu on 7/30/17.
 */

public class ManagerClass {
    CognitoCachingCredentialsProvider credentialsProvider = null;
    CognitoSyncManager syncManager = null;
    public ManagerClass(final Context context){
         credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-west-2:044330a6-3b3e-4549-95c8-e7df09de3377", // Identity pool ID
                Regions.US_WEST_2 // Region
        );
        // Initialize the Cognito Sync client
        CognitoSyncManager syncClient = new CognitoSyncManager(
                context,
                Regions.US_WEST_2, // Region
                credentialsProvider);

// Create a record in a dataset and synchronize with the server
      /*  Dataset dataset = syncClient.openOrCreateDataset("myDataset");
        dataset.put("myKey", "myValue");
        dataset.synchronize(new DefaultSyncCallback() {
            @Override
            public void onSuccess(Dataset dataset, List newRecords) {
                //Your handler code here
            }
        });*/
    }
    public CognitoCachingCredentialsProvider getCredentialsProvider(){

        return credentialsProvider;
    }

}
