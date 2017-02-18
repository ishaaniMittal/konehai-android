package com.kone_hai.activityNew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kone_hai.R;
import com.kone_hai.SessionManager;

/**
 * Created by imittal on 2/19/17.
 */

public class HealthTrackerFragment extends Fragment {
    private View rootView;
    private SessionManager sessionManager;

    public static Fragment newInstance() {
        Fragment fragment = new OrderLiftFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_healthcare, container, false);
        //initializeViews();
        sessionManager = new SessionManager(getActivity().getApplicationContext());

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        return rootView;
    }
}
