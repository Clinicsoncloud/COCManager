package com.coc.cocmanager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.coc.cocmanager.R;
import com.coc.cocmanager.interfaces.ListClickListener;
import com.coc.cocmanager.model.UserData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * creat
 */
public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private Context context;
    private View itemView;
    private ListClickListener listClickListener;
    private final List<UserData.User_Info> list;

    public UsersListAdapter(Context context, List<UserData.User_Info> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserName;
        ImageView ivEditUser;
        ImageView ivDeleteUser;

        public ViewHolder(View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tv_user_name);
            ivEditUser = itemView.findViewById(R.id.iv_edit_user);
            ivDeleteUser = itemView.findViewById(R.id.iv_delete_user);
        }
    }

    public void setListClickListener(ListClickListener listClickListener) {
        this.listClickListener = listClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user_list_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
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
        if (list.get(position).getFirst_name() != null)
            holder.tvUserName.setText(""+list.get(position).getFirst_name());
        else
           holder.tvUserName.setText("");

        holder.ivDeleteUser.setOnClickListener(v -> {listClickListener.click(position,0);});
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
