package com.antar.merchant.item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antar.merchant.R;
import com.antar.merchant.activity.OrdervalidasiActivity;
import com.antar.merchant.models.TransaksiMerchantModel;
import com.antar.merchant.utils.LocaleHelper;
import com.antar.merchant.utils.SettingPreference;
import com.antar.merchant.utils.Utility;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class OrderItem extends RecyclerView.Adapter<OrderItem.ItemRowHolder> {

    private List<TransaksiMerchantModel> dataList;
    private Context mContext;
    private int rowLayout;


    public OrderItem(Context context, List<TransaksiMerchantModel> dataList, int rowLayout) {
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
        final TransaksiMerchantModel singleItem = dataList.get(position);

        switch (singleItem.getStatus()) {
            case "2":
                holder.status.getBackground().setColorFilter(mContext.getResources().getColor(R.color.colorgradient), PorterDuff.Mode.SRC_ATOP);
                holder.status.setTextColor(mContext.getResources().getColor(R.color.colorgradient));
                holder.status.setText("New Order");
                holder.images.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_progress));
                holder.images.setColorFilter(mContext.getResources().getColor(R.color.colorgradient));
                break;
            case "3":
                holder.status.getBackground().setColorFilter(mContext.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                holder.status.setTextColor(mContext.getResources().getColor(R.color.yellow));
                holder.status.setText("Delivery");
                holder.images.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_delivery));
                holder.images.setColorFilter(mContext.getResources().getColor(R.color.yellow));
                break;
            case "4":
                holder.status.getBackground().setColorFilter(mContext.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                holder.status.setTextColor(mContext.getResources().getColor(R.color.green));
                holder.status.setText("Finish");
                holder.images.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_checklist));
                holder.images.setColorFilter(mContext.getResources().getColor(R.color.green));
                break;
            case "5":
                holder.status.getBackground().setColorFilter(mContext.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                holder.status.setTextColor(mContext.getResources().getColor(R.color.red));
                holder.status.setText("Cancel");
                holder.images.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_close));
                holder.images.setColorFilter(mContext.getResources().getColor(R.color.red));
                break;
        }
        holder.text.setText(singleItem.fullnama);
        holder.textinv.setText("INV-"+singleItem.getId_transaksi()+singleItem.getIdtransmerchant());

//        SettingPreference sp = new SettingPreference(mContext);
//        if (singleItem.total_biaya.length() == 1) {
//            holder.nominal.setText(singleItem.getQuantity()+" item "+mContext.getString(R.string.text_with_bullet)+" "+sp.getSetting()[0]+"0.0" + singleItem.total_biaya);
//        } else if (singleItem.total_biaya.length() == 2) {
//            holder.nominal.setText(singleItem.getQuantity()+" item "+mContext.getString(R.string.text_with_bullet)+" "+sp.getSetting()[0]+"0." + singleItem.total_biaya);
//        } else {
//            Double getprice = Double.valueOf(singleItem.total_biaya);
//            DecimalFormat formatter = new DecimalFormat("#,###,###");
//            String formattedString = formatter.format(getprice);
//            holder.nominal.setText(singleItem.getQuantity()+" item "+mContext.getString(R.string.text_with_bullet)+" "+sp.getSetting()[0] + formattedString.replace(",","."));
//
//        }

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        switch (LocaleHelper.getLanguage(mContext))
        {
            case "en":
                Currency currency = Currency.getInstance("USD");
                DecimalFormat decimalFormat = new DecimalFormat("#¤");
                decimalFormat.setCurrency(currency);
                Double currencyDollar = 0.000067;
                Double convertDollar = Double.parseDouble(singleItem.total_biaya) * currencyDollar;

                String formattedString = decimalFormat.format(convertDollar);

                holder.nominal.setText(singleItem.getQuantity()+" item "+mContext.getString(R.string.text_with_bullet)+" "+ formattedString.replace(",", "."));
                break;
            case "km":
                Double currencyCambodianReal = 0.270410891973339136;
                Double convertToCambodianReal = Double.parseDouble(singleItem.total_biaya) * currencyCambodianReal;

                if (singleItem.total_biaya.length() == 1) {
                    holder.nominal.setText("៛"+"0.0" + singleItem.total_biaya);
                } else if (singleItem.total_biaya.length() == 2) {
                    holder.nominal.setText("៛"+"0." + singleItem.total_biaya);
                } else {
                    String formattedStringCambodiaReal = formatter.format(convertToCambodianReal);
                    holder.nominal.setText("៛" + formattedStringCambodiaReal.replace(",","."));
                }
                break;
            case "in":
                if (singleItem.total_biaya.length() == 1) {
                    holder.nominal.setText("Rp"+"0.0" + singleItem.total_biaya);
                } else if (singleItem.total_biaya.length() == 2) {
                    holder.nominal.setText("Rp"+"0." + singleItem.total_biaya);
                } else {
                    Double getprice = Double.valueOf(singleItem.total_biaya);
                    String formattedStringRupiah = formatter.format(getprice);
                    holder.nominal.setText("Rp" + formattedStringRupiah.replace(",","."));
                }
                break;
        }

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = null;
        try {
            date = inputFormat.parse(singleItem.getCreated());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        final String finalDate = timeFormat.format(Objects.requireNonNull(date));
        holder.tanggal.setText(finalDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, OrdervalidasiActivity.class);
                i.putExtra("invoice","INV-"+singleItem.getId_transaksi()+singleItem.getIdtransmerchant());
                i.putExtra("id",singleItem.id_transaksi);
                i.putExtra("idpelanggan",singleItem.idpelanggan);
                i.putExtra("iddriver",singleItem.iddriver);
                i.putExtra("ordertime",finalDate);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    static class ItemRowHolder extends RecyclerView.ViewHolder {
        TextView text, tanggal, nominal, textinv,status;
        ImageView images;

        ItemRowHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.icon);
            text = itemView.findViewById(R.id.text);
            textinv = itemView.findViewById(R.id.textinvoice);
            tanggal = itemView.findViewById(R.id.tanggal);
            nominal = itemView.findViewById(R.id.price);
            status = itemView.findViewById(R.id.status);
        }
    }


}
