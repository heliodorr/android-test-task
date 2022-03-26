package ru.cft.cfttesttask.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.cft.cfttesttask.model.CurrencyModel.CurrencyItem;


import java.util.List;

import ru.cft.cfttesttask.R;
import ru.cft.cfttesttask.model.CurrencyModel;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {

    private List<CurrencyItem> dataList;

    public CurrencyAdapter() {
        super();
    }

    public void refresh(List<CurrencyItem> refreshedData) {
        dataList = refreshedData;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler_item, parent, false);

        CurrencyViewHolder viewHolder = new CurrencyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {

        String str = dataList.get(position).recyclerText();

        holder.bind(str);

    }

    @Override
    public int getItemCount() {

        return dataList == null ? 0 : dataList.size();
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder{

        TextView text;

        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.textfield);

        }

        void bind(String str) {

            text.setText(str);

        }



    }

}
