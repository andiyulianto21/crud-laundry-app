package com.example.crudlaundry.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crudlaundry.Model.DataModel;
import com.example.crudlaundry.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {

    private Context context;
    private List<DataModel> listLaundry;

    public AdapterData(Context context, List<DataModel> listLaundry) {
        this.context = context;
        this.listLaundry = listLaundry;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        HolderData holder = new HolderData(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, @SuppressLint("RecyclerView") int position) {
        DataModel dataModel = listLaundry.get(position);
        holder.tv_id.setText(String.valueOf(dataModel.getId()));
        holder.tv_nama.setText(dataModel.getNama());
        holder.tv_alamat.setText(dataModel.getAlamat());
        holder.tv_telepon.setText(dataModel.getTelepon());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("HAPUS").setMessage("Apakah yakin ingin menghapus data ini?")
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String id = holder.tv_id.getText().toString();
                        String deleteUrl = "http://10.0.2.2/laundry/delete.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteUrl,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams(){
                                Map<String, String> params = new HashMap<>();
                                params.put("data_id", id);

                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(stringRequest);
                        listLaundry.remove(position);
                        notifyDataSetChanged();
                    }
                    
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Batal Hapus", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                }).show().create();
                
             notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listLaundry.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView tv_id, tv_nama, tv_alamat,tv_telepon;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_telepon = itemView.findViewById(R.id.tv_telepon);
        }
    }

}
