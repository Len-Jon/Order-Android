package com.example.order.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.order.ItemDetailActivity;
import com.example.order.R;
import com.example.order.constant.Constant;
import com.example.order.entity.Item;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 购物车列表Fragment适配器
 */
@Getter
@Setter
public class ChosenItemListAdapter extends RecyclerView.Adapter<ChosenItemListAdapter.ViewHolder> {

    private List<Item> itemList;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public ChosenItemListAdapter(List<Item> dataSet) {
        itemList = dataSet;
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    @Getter
    @Setter
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
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
            view.setOnLongClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            Item item = itemListAdapter.getItemList().get(mPosition);
            Intent intent = new Intent(view.getContext(), ItemDetailActivity.class)
                    .putExtra(Constant.ITEM_ID_KEY, item.getId())
                    .putExtra(ItemDetailActivity.FROM_ORDER_LIST_KEY, true);
            view.getContext().startActivity(intent);
        }

        @SuppressLint({"NonConstantResourceId", "NotifyDataSetChanged"})
        @Override
        public boolean onLongClick(View view) {
            int mPosition = getLayoutPosition();
            Item item = itemListAdapter.getItemList().get(mPosition);
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.action_remove_cnt:
                        Constant.itemCntMap.put(item.getId(), 0);
                        itemListAdapter.setItemList(Constant.getChosenList());
                        itemListAdapter.notifyDataSetChanged();
                        break;
                    case R.id.action_modify_cnt:
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        EditText inputEditText = new EditText(view.getContext());
                        builder.setTitle("请输入修改数量");
                        builder.setView(inputEditText);
                        builder.setPositiveButton("确认", (dialogInterface, i) -> {
                            String input = inputEditText.getText().toString();
                            try {
                                int inputNum = Integer.parseInt(input);
                                if (inputNum <= 0) {
                                    throw new RuntimeException();
                                }
                                Constant.itemCntMap.put(item.getId(), inputNum);
                                itemListAdapter.setItemList(Constant.getChosenList());
                                itemListAdapter.notifyDataSetChanged();
                            } catch (Exception e) {
                                Toast.makeText(view.getContext(), "请输入有效数字", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("取消", (dialogInterface, i) -> dialogInterface.dismiss());
                        builder.show();
                        break;
                    default:
                        break;
                }
                return true;
            });

            popupMenu.show();
            return false;
        }
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
}
