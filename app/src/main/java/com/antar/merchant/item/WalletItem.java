package com.antar.merchant.item;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antar.merchant.R;
import com.antar.merchant.models.WalletModel;
import com.antar.merchant.utils.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.internal.Util;


public class WalletItem extends RecyclerView.Adapter<WalletItem.ItemRowHolder> {

    private List<WalletModel> dataList;
    private Context mContext;
    private int rowLayout;


    public WalletItem(Context context, List<WalletModel> dataList, int rowLayout) {
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ItemRowHolder holder, final int position) {
        final WalletModel singleItem = dataList.get(position);


        if (singleItem.getType().equals("withdraw")) {
            holder.text.setText(singleItem.getType());
//            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            Utility.convertLocaleCurrencyTV(holder.nominal, mContext, singleItem.getJumlah());

            switch (singleItem.getStatus()) {
                case "1":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.red));
                    holder.keterangan.setText("Dana Keluar");
                    break;
                case "0":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.yellow));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.yellow));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.yellow));
                    holder.keterangan.setText("Pending");
                    break;
                case "2":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.gray));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.gray));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.gray));
                    holder.keterangan.setText("Dibatalkan");
                    break;
            }

        } else if (singleItem.getType().equals("Order-")) {
            holder.text.setText("Order " + singleItem.getBank());
//            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            Utility.convertLocaleCurrencyTV(holder.nominal, mContext, singleItem.getJumlah());

            switch (singleItem.getStatus()) {
                case "1":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.red));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.red));
                    holder.keterangan.setText("Dana Keluar");
                    break;
                case "0":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.yellow));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.yellow));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.yellow));
                    holder.keterangan.setText("Pending");
                    break;
                case "2":
                    holder.text.setTextColor(mContext.getResources().getColor(R.color.gray));
                    holder.nominal.setTextColor(mContext.getResources().getColor(R.color.gray));
                    holder.background.setColorFilter(mContext.getResources().getColor(R.color.gray));
                    holder.keterangan.setText("Dibatalkan");
                    break;
            }

        } else if (singleItem.getType().equals("topup") && singleItem.getStatus().equals("1")) {
            holder.text.setText(singleItem.getType()+" "+singleItem.getBank());
//            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            Utility.convertLocaleCurrencyTV(holder.nominal, mContext, singleItem.getJumlah());
            holder.text.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.background.setColorFilter(mContext.getResources().getColor(R.color.green));
            holder.keterangan.setText("Dana Masuk");

        } else if (singleItem.getType().equals("topup") && singleItem.getStatus().equals("2")) {
//            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            Utility.convertLocaleCurrencyTV(holder.nominal, mContext, singleItem.getJumlah());
            holder.text.setText(singleItem.getType()+" "+singleItem.getBank());
            holder.text.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.gray));
            holder.background.setColorFilter(mContext.getResources().getColor(R.color.gray));
            holder.keterangan.setText("Dibatalkan");

        } else if (singleItem.getType().equals("topup") && singleItem.getStatus().equals("0")) {
//            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            Utility.convertLocaleCurrencyTV(holder.nominal, mContext, singleItem.getJumlah());
            holder.text.setText(singleItem.getType()+" "+singleItem.getBank());
            holder.background.setColorFilter(mContext.getResources().getColor(R.color.yellow));
            holder.text.setTextColor(mContext.getResources().getColor(R.color.yellow));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.yellow));
            holder.keterangan.setText("Pending");

        } else if (singleItem.getType().equals("Order+")) {
            holder.text.setText("Order " + singleItem.getBank());
//            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            Utility.convertLocaleCurrencyTV(holder.nominal, mContext, singleItem.getJumlah());
            holder.text.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.background.setColorFilter(mContext.getResources().getColor(R.color.green));
            holder.keterangan.setText("Dana Keluar");

        } else {
            holder.background.setColorFilter(mContext.getResources().getColor(R.color.yellow));
            holder.text.setText(singleItem.getType()+" "+singleItem.getBank());
            holder.text.setTextColor(mContext.getResources().getColor(R.color.yellow));
            holder.nominal.setTextColor(mContext.getResources().getColor(R.color.yellow));
//            Utility.currencyTXT(holder.nominal, singleItem.getJumlah(), mContext);
            Utility.convertLocaleCurrencyTV(holder.nominal, mContext, singleItem.getJumlah());

            holder.keterangan.setText("Pending");


        }
        Date myDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US);
        try {
            myDate = dateFormat.parse(singleItem.getWaktu());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        String finalDate = timeFormat.format(Objects.requireNonNull(myDate));
        holder.tanggal.setText(finalDate);
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text, tanggal, nominal, keterangan;
        ImageView background;

        ItemRowHolder(View itemView) {
            super(itemView);
            background = itemView.findViewById(R.id.background);
            text = itemView.findViewById(R.id.text);
            tanggal = itemView.findViewById(R.id.texttanggal);
            nominal = itemView.findViewById(R.id.textharga);
            keterangan = itemView.findViewById(R.id.textket);
        }
    }


}
