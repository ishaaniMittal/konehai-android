package com.kone_hai.activityNew;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.kone_hai.R;
import com.kone_hai.SessionManager;
import com.kone_hai.network.GSONRequest;
import com.kone_hai.network.MySingleton;
import com.kone_hai.vo.TimeToReachHome;

/**
 * Created by imittal on 2/19/17.
 */

public class OrderLiftFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private View rootView;
    private SessionManager sessionManager;
    private Button orderLift, trackMe;
    private EditText boardAt, deboardAt;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private String countDownTimer;

    public static Fragment newInstance() {
        Fragment fragment = new OrderLiftFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_order_lift, container, false);
        initializeViews();
        sessionManager = new SessionManager(getActivity().getApplicationContext());

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        orderLift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationAndTimeRemaining();
            }
        });

        trackMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocationAndTimeRemaining();
            }
        });


        return rootView;
    }

    private void initializeViews() {
        orderLift = (Button) rootView.findViewById(R.id.order_now_button);
        trackMe = (Button) rootView.findViewById(R.id.track_me_button);
        boardAt = (EditText) rootView.findViewById(R.id.input_boardAt);
        deboardAt = (EditText) rootView.findViewById(R.id.input_deboardAt);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void getLocationAndTimeRemaining() {
        if (mLastLocation != null) {
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + mLastLocation.getLatitude() + "," +
                    mLastLocation.getLongitude() +
                    "&destinations=18.5471164,73.9335888&mode=car&language=fr-FR";

            GSONRequest<TimeToReachHome> stringRequest = new GSONRequest(Request.Method.GET, url, TimeToReachHome.class, null,
                    new Response.Listener<TimeToReachHome>() {
                        @Override
                        public void onResponse(TimeToReachHome response) {
                            countDownTimer = response.getRows().get(0).getElements().get(0).getDuration().getText();
                            new CountDownTimer(300000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                    orderLift.setText("seconds remaining: " + millisUntilFinished / 1000);
                                    trackMe.setVisibility(View.INVISIBLE);
                                }

                                public void onFinish() {
                                    orderLift.setText("Order Another Lift");
                                    trackMe.setVisibility(View.VISIBLE);
                                }
                            }.start();
                            // Display the first 500 characters of the response string.
                            //   mLatitudeText.setText(String.valueOf(response.getRows().get(0).getElements().get(0).getDuration().getText()));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // mLatitudeText.setText("That didn't work!");
                }
            });
            MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

        }
    }
}
