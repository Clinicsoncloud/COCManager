package com.coc.cocmanager.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.ApiUtils;
import com.coc.cocmanager.Utils.HttpService;
import com.coc.cocmanager.Utils.Utils;
import com.coc.cocmanager.interfaces.RvClickListener;
import com.coc.cocmanager.model.Items_Response;
import com.coc.cocmanager.model.Request_Item_Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vaibhav.
 */
public class AddItemsListAdapter extends RecyclerView.Adapter<AddItemsListAdapter.ViewHolder> {
    private List<Request_Item_Data> selectedItemsList;
    private ArrayList itemsCategoryList;
    private ArrayList itemsCategoryIdList;
    private ArrayList itemsList;
    private ArrayList itemsIDList;

    private Context context;
    private View itemView;
    private RvClickListener rvClickListener;
    private String selectedCategory, selectedCategoryID;
    private String selectedItem, selectedItemID;

    ViewHolder viewHolder;
    int itemPosition;


    public AddItemsListAdapter(Context context, List<Request_Item_Data> selectedItemsList,
                               ArrayList itemsCategoryList, ArrayList itemsCategoryIdList) {
        this.context = context;
        this.selectedItemsList = selectedItemsList;
        this.itemsCategoryList = itemsCategoryList;
        this.itemsCategoryIdList = itemsCategoryIdList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Spinner snpItemCategory;
        Spinner snpItem;
        EditText edtItemQuantity;
        ImageView imgRemoveItem;

        public ViewHolder(View itemView) {
            super(itemView);
            snpItem = itemView.findViewById(R.id.spn_Item);
            imgRemoveItem = itemView.findViewById(R.id.img_RemoveItem);
            edtItemQuantity = itemView.findViewById(R.id.edt_itemQuantity);
            snpItemCategory = itemView.findViewById(R.id.snp_ItemCategory);
        }
    }

    public void setRvClickListener(RvClickListener rvClickListener) {
        this.rvClickListener = rvClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(context).inflate(R.layout.row_add_items_layout, parent, false);

        ViewHolder vh = new ViewHolder(itemView);

        return vh;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {

            ArrayAdapter<String> dataAdapter;
            dataAdapter = new ArrayAdapter<String>(context.getApplicationContext(),
                    R.layout.simple_item_selected, itemsCategoryList);

            dataAdapter.setDropDownViewResource(R.layout.simple_item);
            holder.snpItemCategory.setAdapter(dataAdapter);

            if (selectedItemsList.get(position).getCategory_id() != null && !selectedItemsList.get(position).getCategory_id().equals("")) {
                holder.snpItemCategory.setSelection(itemsCategoryIdList.indexOf(selectedItemsList.get(position).getCategory_id()));
            }

            holder.snpItemCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                    if (pos != 0) {
                        viewHolder = holder;
                        itemPosition = position;

                        selectedCategory = holder.snpItemCategory.getSelectedItem().toString();
                        selectedCategoryID = String.valueOf(itemsCategoryIdList.get(itemsCategoryList.indexOf(selectedCategory)));
                        selectedItemsList.get(position).setCategory_id(selectedCategoryID);

                        getItemList();

                        Log.e("selectedCategory_Log", ":" + selectedCategory
                                + "  : selectedCategoryID_Log :  " + selectedCategoryID);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            holder.snpItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                    if (pos != 0) {
                        selectedItem = holder.snpItem.getSelectedItem().toString();
                        selectedItemID = String.valueOf(itemsIDList.get(itemsList.indexOf(selectedItem)));
                        selectedItemsList.get(position).setId(selectedItemID);

                        Log.e("selectedItemID_Log", ":" + selectedItemID
                                + "  : selectedItemID_Log :  " + selectedItemID);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            holder.edtItemQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (!holder.edtItemQuantity.getText().toString().equals(""))
                        selectedItemsList.get(position).setQuantity(holder.edtItemQuantity.getText().toString());
                }
            });

            holder.imgRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rvClickListener.rv_click(position, 0, "remove_item");
                }
            });


        } catch (Exception e) {
        }
    }

    private void getItemList() {
        try {
            Map<String, String> headerParams = new HashMap<>();
            Map<String, String> requestBodyParams = new HashMap<>();

            requestBodyParams.put("item_category_id", selectedCategoryID);
            requestBodyParams.put("status", "true");

            Log.e("ItemsFilters", ":" + ApiUtils.ITEM_LIST);
            HttpService.accessWebServices(
                    context,
                    ApiUtils.ITEM_LIST,
                    requestBodyParams,
                    headerParams,
                    (response, error, status) -> handleItemsAPIResponse(response, error, status));
        } catch (Exception e) {
        }

    }

    private void handleItemsAPIResponse(String response, VolleyError error, String status) {

        Log.e("res_Item_Filter", ":" + response);

        try {
            if (status.equals("response")) {

                Items_Response items_response = (Items_Response) Utils.parseResponse(response, Items_Response.class);

                if (items_response.getFound()) {

                    itemsList = new ArrayList();
                    itemsIDList = new ArrayList();

                    itemsList.add("-Select-");
                    itemsIDList.add("0");

                    for (int i = 0; i < items_response.getData().size(); i++) {
                        itemsList.add(items_response.getData().get(i).getName());
                        itemsIDList.add(items_response.getData().get(i).getId());
                    }
                    Log.e("itemsCategoryList_Size", ":" + itemsCategoryList.size());

                    try {
                        ArrayAdapter<String> dataAdapter;
                        dataAdapter = new ArrayAdapter<String>(context.getApplicationContext(), R.layout.simple_item_selected, itemsList);
                        dataAdapter.setDropDownViewResource(R.layout.simple_item);
                        viewHolder.snpItem.setAdapter(dataAdapter);

                        if (selectedItemsList.get(itemPosition).getId() != null && !selectedItemsList.get(itemPosition).getId().equals("")) {
                            viewHolder.snpItem.setSelection(itemsIDList.indexOf(selectedItemsList.get(itemPosition).getId()));
                        }
                    } catch (Exception e) { }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return selectedItemsList.size();
    }
}
