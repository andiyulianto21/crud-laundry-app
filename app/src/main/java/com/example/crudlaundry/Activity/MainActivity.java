package com.example.crudlaundry.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.crudlaundry.API.APIRequestData;
import com.example.crudlaundry.API.RetroServer;
import com.example.crudlaundry.Adapter.AdapterData;
import com.example.crudlaundry.Model.DataModel;
import com.example.crudlaundry.Model.ResponseModel;
import com.example.crudlaundry.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<DataModel> dataModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_laundry);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        retrieveData();
    }

    private void retrieveData() {

        APIRequestData ardData = RetroServer.connectRetrofit().create(APIRequestData.class);
        Call<ResponseModel> tampilData = ardData.ardRetrieveData();
        tampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(MainActivity.this, "Kode: " + kode + " | Pesan: " + pesan, Toast.LENGTH_SHORT).show();

                dataModelList = response.body().getData();
                adapter = new AdapterData(MainActivity.this, dataModelList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(layoutManager);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal menghubungkan server"+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}