package it.cosenonjaviste.mywearapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.wearable.view.FragmentGridPagerAdapter;

public class MyPagerAdapter extends FragmentGridPagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getFragment(int i, int i1) {
        return new AnswerFragment();
    }

    @Override public int getRowCount() {
        return 1;
    }

    @Override public int getColumnCount(int i) {
        return 1;
    }
}
