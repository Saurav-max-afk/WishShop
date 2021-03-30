package com.saurav.wishshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Detail2Activity extends AppCompatActivity {
    TextView dataDescription;
    ImageView dataImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        dataDescription=findViewById(R.id.txtDescription);
        dataImage=findViewById(R.id.ivImage2);

        Bundle mBundle= getIntent().getExtras();
        if (mBundle!=null)
        {
            dataDescription.setText(mBundle.getString("Description"));
            //   dataImage.setImageResource(mBundle.getInt("Image"));

            Glide.with(this)
                    .load(mBundle.getString("Image"))
                    .into(dataImage);
        }

    }
}