package com.coc.cocmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.coc.cocmanager.R;
import com.coc.cocmanager.interfaces.ListClickListener;

import java.util.ArrayList;

public class TransportListAdapter extends RecyclerView.Adapter<TransportListAdapter.ViewHolder> {
    private final ArrayList list;
    private Context context;
    private View itemView;
    private ListClickListener listClickListener;

    public TransportListAdapter(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
        }

    }

    public void setListClickListener(ListClickListener listClickListener) {
        this.listClickListener = listClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_transport_list_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listClickListener.click(1, 1);

            }
        });
        return vh;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        /*holder.tv_particulars.setText(list.get(position).getServiceName());*/
       /* holder.ivAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listClickListener.click(position,1);
            }
        });*/

    }


    @Override
    public int getItemCount() {

        return list.size();
    }

}
