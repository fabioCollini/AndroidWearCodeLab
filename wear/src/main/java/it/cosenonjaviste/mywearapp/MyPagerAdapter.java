package it.cosenonjaviste.mywearapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.wearable.view.FragmentGridPagerAdapter;

public class MyPagerAdapter extends FragmentGridPagerAdapter {
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public Fragment getFragment(int row, int column) {
        if (column == 0) {
            return new AnswerFragment();
        } else {
            return new SurveyDetailFragment();
        }
    }

    @Override public int getRowCount() {
        return 1;
    }

    @Override public int getColumnCount(int i) {
        return 2;
    }
}
