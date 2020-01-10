package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitinterface;
    private String Base_URL = "http://10.0.2.2:3000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitinterface = retrofit.create(RetrofitInterface.class);


        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDetails();
            }
        });
    }

    private void displayDetails() {

        final EditText phone = findViewById(R.id.phone);
        final EditText name = findViewById(R.id.name);
        final EditText age = findViewById(R.id.age);

        HashMap<String, String> map = new HashMap<>();

        map.put("phone", phone.getText().toString());
        map.put("name", name.getText().toString());
        map.put("age", age.getText().toString());

        Call<Detail> call = retrofitinterface.exectueDetail(map);

        call.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                if (response.code() == 200) {

                    Detail result = response.body();

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setTitle(result.getName());
                    builder1.setMessage("Age: " + result.getAge() + "\nPhone: " + result.getPhone());

                    builder1.show();

                } else if (response.code() == 404) {
                    Toast.makeText(MainActivity.this, "Duplicate Credentials",
                            Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
