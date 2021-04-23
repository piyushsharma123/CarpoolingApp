package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import static android.R.color.black;
import static android.R.color.white;

public class carousel extends AppCompatActivity {
    private int[] nImages = new int[] {
            R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image5,R.drawable.image6
    };

    private String[] nImagesTitle = new String[] {
            "Enjoy Every Ride With Friends!!!","Find nearest Friend!!!","Set Your Pickup Point!!!","Keep Track On Your Ride!!!","Minimize Your Fare!!!","Stay Safe And Secure!!!"
    };

    private TextSwitcher textSwitcher;
    private int stringIndex = 0;
    private String row[] = {"\"A ride for your success\"","\"Drive, Ride, Reach...\"","\"Save Time, Save Price\"","\"You Drive, You Ride\"","\"Your Own Commute\"","\"Enjoy Your, Safe Ride\""};
    private TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carousel);
        Button bt1 = findViewById(R.id.ls);
        textSwitcher = findViewById(R.id.shlogan);
        TextView t2 = (TextView) findViewById(R.id.intro);
        String info = "PICKAPP is user friendly interface for SKIT's internal carpooling system. Pickapp helps users to find car pool provider and also provides information about car owners and the other driver related information like contact details And SKIT ID. Enjoy your secure Ride...\n";
        t2.setText(info);

        CarouselView carouselView = findViewById(R.id.carousel);
        carouselView.setPageCount(nImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(nImages[position]);
            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(carousel.this,nImagesTitle[position], Toast.LENGTH_SHORT).show();
            }
        });

        textSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stringIndex == row.length-1) {
                    stringIndex = 0;
                    textSwitcher.setText(row[stringIndex]);
                }else{
                    textSwitcher.setText(row[++stringIndex]);
                }
            }
        });
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                t1 =new TextView(carousel.this);
                t1.setGravity(Gravity.CENTER_HORIZONTAL);
                t1.setTextColor(Color.parseColor("#FFFFFF"));
                t1.setTextSize(40);
                return t1;
            }
        });
        textSwitcher.setText(row[stringIndex]);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(carousel.this, login.class);
                    startActivity(i);
                }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.first_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.contacts:
                Intent i = new Intent(carousel.this, contacts.class);
                startActivity(i);
                return true;
            case R.id.login:
                Intent j = new Intent(carousel.this, login.class);
                startActivity(j);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
