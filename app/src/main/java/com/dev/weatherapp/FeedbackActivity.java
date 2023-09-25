package com.dev.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.dev.weatherapp.adapter.FeedbackAdapter;
import com.dev.weatherapp.database.DatabaseHelper;
import com.dev.weatherapp.model.Feedback;
import com.dev.weatherapp.model.HelperClass;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {
    ImageView ivBack;
    RecyclerView rvReviews;
    RelativeLayout rlAddReview;
    List<Feedback> list = new ArrayList<>();
    DatabaseHelper databaseHelper;
    FeedbackAdapter feedbackAdapter;
    Feedback feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        ivBack = findViewById(R.id.ivBack);
        rvReviews = findViewById(R.id.rvReviews);
        rlAddReview = findViewById(R.id.rlAddReview);
        databaseHelper = new DatabaseHelper(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rlAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        list = databaseHelper.getAllFeedback();
        if (list.isEmpty()){
            Toast.makeText(this, "No Feedback Found", Toast.LENGTH_SHORT).show();
        }else{
            feedbackAdapter = new FeedbackAdapter(list, this);
            rvReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvReviews.setAdapter(feedbackAdapter);
        }

    }

    public void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_reviews);
        //Initializing all the variables
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        EditText etComments = (EditText) dialog.findViewById(R.id.etComments);
        AppCompatRatingBar ratingBar = (AppCompatRatingBar) dialog.findViewById(R.id.ratingBar);
        feedback = new Feedback();
        feedback = databaseHelper.getUserFeedbackByUserId(HelperClass.users.getId());
        if (feedback != null){
            ratingBar.setRating(Float.parseFloat(feedback.getRating()));
            etComments.setText(feedback.getComment());
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //Clicking on Submit Button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Storing the fields data to string variables
                String totalStars = String.valueOf(ratingBar.getRating());
                String getComments = etComments.getText().toString();
                //Checking the validations
                if (totalStars.isEmpty() || totalStars.contains("0.0")) {
                    Toast.makeText(FeedbackActivity.this, "Please rate the App", Toast.LENGTH_SHORT).show();
                } else if (getComments.isEmpty()) {
                    Toast.makeText(FeedbackActivity.this, "Please enter comments", Toast.LENGTH_SHORT).show();
                } else {
                    if (feedback != null){
                        Feedback model = new Feedback(feedback.getId(), HelperClass.users.getId(), totalStars, getComments, HelperClass.users.getUserName());
                        databaseHelper.updateFeedback(model);
                    }else{
                        Feedback model = new Feedback(HelperClass.users.getId(), totalStars, getComments, HelperClass.users.getUserName());
                        databaseHelper.insertFeedback(model);
                    }
                    Toast.makeText(FeedbackActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    list.clear();
                    list = databaseHelper.getAllFeedback();
                    if (!list.isEmpty()){
                        feedbackAdapter = new FeedbackAdapter(list, FeedbackActivity.this);
                        rvReviews.setLayoutManager(new LinearLayoutManager(FeedbackActivity.this, LinearLayoutManager.VERTICAL, false));
                        rvReviews.setAdapter(feedbackAdapter);

                    }
                }

            }
        });

        dialog.show();

    }

}