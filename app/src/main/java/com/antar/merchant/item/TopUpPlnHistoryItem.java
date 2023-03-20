package com.antar.merchant.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.antar.merchant.R;
import com.antar.merchant.utils.local_interface.OnItemClicked;
import com.antar.merchant.json.MobileTopUpDetailResponseModel;
import com.antar.merchant.json.TopUpRequestResponse;
import com.antar.merchant.utils.Utility;

import java.util.ArrayList;
import java.util.List;

public class TopUpPlnHistoryItem extends RecyclerView.Adapter<TopUpPlnHistoryItem.ViewHolder> {

    List<TopUpRequestResponse> topUpRequestResponseList = new ArrayList<>();

    private OnItemClicked itemClicked;

    public TopUpPlnHistoryItem(List<TopUpRequestResponse> data, OnItemClicked onItemClicked) {
        this.topUpRequestResponseList.addAll(data);
        this.itemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topup_request, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TopUpRequestResponse topUp = topUpRequestResponseList.get(position);
        holder.titleTag.setText(topUp.getPhoneNumber());
        holder.priceTag.setText(Utility.convertCurrency(String.valueOf(topUp.getPrice() + 600), holder.itemView.getContext()));
        holder.itemView.setOnClickListener(v -> itemClicked.onClick(topUp));
    }

    @Override
    public int getItemCount() {
        return topUpRequestResponseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView priceTag, titleTag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            priceTag = itemView.findViewById(R.id.priceTopUp);
            titleTag = itemView.findViewById(R.id.idPel);
        }
    }

    public static interface onRecyclerViewOnClicked {
        void onClick(MobileTopUpDetailResponseModel model);
    }
}
