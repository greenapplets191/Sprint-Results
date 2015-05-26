package com.greenapplets.planimal;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ASUS on 3/14/2015.
 */
public class FragmentShop extends Fragment {
    private ListView listInventory;
    private ArrayList<Item> itemList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        itemList = new ArrayList<Item>();


        Resources res = getActivity().getResources();
        String[] totalItemNames = res.getStringArray(R.array.item_names);
        String[] itemDescriptions = res.getStringArray(R.array.item_descriptions);
        int[] itemPrices = res.getIntArray(R.array.item_prices);
        Item[] list = new Item[totalItemNames.length];
        int[] images = {R.drawable.sicon_bit_bento,R.drawable.sicon_apple_juice, R.drawable.sicon_sweet_nibble,
                R.drawable.sicon_heal_potion};

        for(int i = 0; i < totalItemNames.length; i++){
            Item  item = new Item(images[i], totalItemNames[i]);
            item.setDescription(itemDescriptions[i]);
            list[i] = item;
            item.setAmount(1);
            item.setValue(itemPrices[i]);
            itemList.add(item);
        }


        listInventory = (ListView)view.findViewById(R.id.shopListView);
        listInventory.setAdapter(new ShopListAdapter(itemList, getActivity()));
        return view;
    }
}

