package com.example.myapplication;
import android.os.Bundle;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class CheckActivity extends AppCompatActivity {

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

//        final Spinner spinner = (Spinner) findViewById(R.id.Spinner);
//        String[] str = getResources().getStringArray(R.array.location);
//
//        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.spinner_item, str);
//        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        pager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        Fragment_snapzone_check snapzone = new Fragment_snapzone_check();
        pagerAdapter.addItem(snapzone);

        Fragment_forest_check forest = new Fragment_forest_check();
        pagerAdapter.addItem(forest);

        Fragment_library_check library = new Fragment_library_check();
        pagerAdapter.addItem(library);

        pager.setAdapter(pagerAdapter);
    }

    class PagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(Fragment item) {
            items.add(item);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0) {
                return "snapzone";
            } else if (position == 1) {
                return "forest";
            } else {
                return "library";
            }
        }
    }
}
