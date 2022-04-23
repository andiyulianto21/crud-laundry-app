package com.example.crudlaundry.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudlaundry.Model.DataModel;
import com.example.crudlaundry.R;

import java.util.List;

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
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dataModel = listLaundry.get(position);
        holder.tv_id.setText(String.valueOf(dataModel.getId()));
        holder.tv_nama.setText(dataModel.getNama());
        holder.tv_alamat.setText(dataModel.getAlamat());
        holder.tv_telepon.setText(dataModel.getTelepon());
    }

    @Override
    public int getItemCount() {
        return listLaundry.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {

        private TextView tv_id, tv_nama, tv_alamat,tv_telepon;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_telepon = itemView.findViewById(R.id.tv_telepon);
        }
    }

}
