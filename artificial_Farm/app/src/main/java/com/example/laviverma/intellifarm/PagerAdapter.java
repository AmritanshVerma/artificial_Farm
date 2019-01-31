package com.example.laviverma.intellifarm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Lavi Verma on 05-Jul-18.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    @Override
    public Fragment getItem(int position) {
       Nocamera tab4 = new Nocamera();
        Camera tab3 = new Camera();
      //  Fertigation tab2 = new Fertigation();
        switch(position)
        {

            case 0:

                return  tab4;
                //Atmosphere tab1 = new Atmosphere();
               // return tab1;
            case 1:
               // return  tab4;

             //   return  tab4;

                return  tab3;
            case 2:
                return tab3;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;}

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }
}
