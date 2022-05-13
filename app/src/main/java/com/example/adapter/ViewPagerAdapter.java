package com.example.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.fragment.FrBill;
import com.example.fragment.FrFavorite;
import com.example.fragment.FrOrder;
import com.example.fragment.FrMenu;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new FrMenu();
            case 1: return new FrFavorite();
            case 2: return new FrOrder();
            default: return new FrBill();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Menu";
            case 1: return "Most favorite";
            case 2: return "Your order";
            default: return "Your bill";
        }
    }
}
