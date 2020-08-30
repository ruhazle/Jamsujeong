package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_library_check extends Fragment {
    private Context mContext;

    GridView gridView;

    String[] seatsArray = {
            "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18"
    };

    Integer[] seatsId = {
            301, 302, 303, 304, 305, 306,
            307, 308, 309, 310, 311, 312,
<<<<<<< HEAD
            313, 314, 315, 316, 317
=======
            313, 314, 315, 316, 317, 318
>>>>>>> 28c8a695bf49abec7bb560fc34a25524b9003853
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_library, container, false);

        gridView = (GridView) rootView.findViewById(R.id.seatsView);
        ButtonAdapter_check buttonAdapterCheck = new ButtonAdapter_check(mContext, seatsArray, seatsId);
        gridView.setAdapter(buttonAdapterCheck);

        return rootView;
    }
}
