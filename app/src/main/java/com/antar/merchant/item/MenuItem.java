package com.antar.merchant.item;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.merchant.R;
import com.antar.merchant.models.ItemPesananModel;
import com.antar.merchant.utils.Utility;

import java.util.List;

/**
 * Created by Ourdevelops Team on 3/24/2019.
 */

public class MenuItem extends RecyclerView.Adapter<MenuItem.ItemRowHolder> {

    private List<ItemPesananModel> dataList;
    private Context mContext;
    private int rowLayout;

    SharedPreferences sharedPreferences;

    public MenuItem(Context context, List<ItemPesananModel> dataList, int rowLayout) {
        this.dataList = dataList;
        this.mContext = context;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final ItemPesananModel singleItem = dataList.get(position);
        holder.namamenu.setText(singleItem.getNama_item());
        holder.jumlahmenu.setText(singleItem.getJumlah_item());
        if (singleItem.getCatatan().isEmpty()) {
            holder.catatan.setVisibility(View.GONE);
        } else {
            holder.catatan.setText(singleItem.getCatatan());
        }
//        Utility.currencyTXT(holder.jumlahhargamenu, singleItem.getTotalharga(), mContext);
        Utility.convertLocaleCurrencyTV(holder.jumlahhargamenu, mContext, singleItem.getTotalharga());

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {

        TextView namamenu, jumlahmenu, catatan, jumlahhargamenu;

        ItemRowHolder(View itemView) {
            super(itemView);

            namamenu = itemView.findViewById(R.id.namamenu);
            jumlahmenu = itemView.findViewById(R.id.jumlahmenu);
            catatan = itemView.findViewById(R.id.catatan);
            jumlahhargamenu = itemView.findViewById(R.id.jumlahhargamenu);
        }
    }
}
