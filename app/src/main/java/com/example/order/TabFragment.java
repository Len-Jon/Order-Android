package com.example.order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.order.adapter.CustomAdapter;
import com.example.order.constant.Constant;
import com.example.order.entity.Item;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragment extends Fragment {
    private static final String SUB_TYPE_ID = "SUB_TYPE_ID";
    private static final String TYPE_ID = "TYPE_ID";

    private int typeId;
    private int subTypeId;
    private List<Item> itemList;
    private RecyclerView recyclerView;

    public TabFragment() {
        // Required empty public constructor
    }

    /**
     * @param subTypeId 子分类id
     * @return fragment
     */
    public static TabFragment newInstance(Integer typeId, Integer subTypeId) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_ID, typeId);
        args.putInt(SUB_TYPE_ID, subTypeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.typeId = getArguments().getInt(TYPE_ID);
            this.subTypeId = getArguments().getInt(SUB_TYPE_ID);
            if (subTypeId == 0) {
                itemList = Constant.itemList.stream().filter(x -> x.getItemType() == this.typeId).collect(Collectors.toList());
            } else {
                itemList = Constant.itemList.stream().filter(x -> x.getItemSubType() == this.subTypeId).collect(Collectors.toList());
            }
        }
    }

    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new CustomAdapter(itemList));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

}