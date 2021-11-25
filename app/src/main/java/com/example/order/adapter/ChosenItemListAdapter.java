package com.example.order.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order.ItemDetailActivity;
import com.example.order.R;
import com.example.order.constant.Constant;
import com.example.order.entity.Item;

import java.util.List;

/**
 * 购物车列表Fragment适配器
 */
public class ChosenItemListAdapter extends RecyclerView.Adapter<ChosenItemListAdapter.ViewHolder> {

    private List<Item> itemList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView itemNameTextView;
        private final TextView itemCountTextView;
        private final ChosenItemListAdapter itemListAdapter;

        public ViewHolder(View view, ChosenItemListAdapter itemListAdapter) {
            super(view);
            // Define click listener for the ViewHolder's View
            itemNameTextView = view.findViewById(R.id.chosen_name_text_view);
            itemCountTextView = view.findViewById(R.id.chosen_cnt_text_view);
            this.itemListAdapter = itemListAdapter;
            view.setOnClickListener(this);
        }

        public TextView getItemNameTextView() {
            return itemNameTextView;
        }

        public TextView getItemCountTextView() {
            return itemCountTextView;
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            Item item = itemListAdapter.getItemList().get(mPosition);
            Intent intent = new Intent(view.getContext(), ItemDetailActivity.class)
                    .putExtra(ItemDetailActivity.ITEM_ID_KEY, item.getId())
                    .putExtra(ItemDetailActivity.FROM_ORDER_LIST_KEY, true);
            view.getContext().startActivity(intent);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public ChosenItemListAdapter(List<Item> dataSet) {
        itemList = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.chosen_item_row, viewGroup, false);
        return new ViewHolder(view, this);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Item item = itemList.get(position);
        viewHolder.getItemNameTextView().setText(item.getName());
        viewHolder.getItemCountTextView().setText(String.valueOf(Constant.itemCntMap.getOrDefault(item.getId(), 0)));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> dataset){
        itemList = dataset;
    }
}
