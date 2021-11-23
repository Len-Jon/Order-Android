package com.example.order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SUB_TYPE = "SUB_TYPE";

    // TODO: Rename and change types of parameters
    private int subTypeId;
    private List<Item> itemList;

    public TabFragment() {
        // Required empty public constructor
    }

    /**
     *
     * @param subTypeId 子分类id
     * @return fragment
     */
    public static TabFragment newInstance(Integer subTypeId) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(SUB_TYPE, subTypeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.subTypeId = getArguments().getInt(SUB_TYPE);
            itemList = Constant.itemList.parallelStream().filter(x -> x.getItemSubType() == this.subTypeId).collect(Collectors.toList());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }
}