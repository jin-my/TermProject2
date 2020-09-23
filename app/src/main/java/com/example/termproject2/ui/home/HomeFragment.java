package com.example.termproject2.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.termproject2.MainActivity;
import com.example.termproject2.MapsActivity;
import com.example.termproject2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment implements OnMapReadyCallback, LocationListener {
//    public static Context mContext;
    GoogleMap mMap;
    Location mCurrentLocation;
    int markerNum = 1;
    Marker currentMarker = null;
    Circle myCircle = null;
    Marker addedMarker = null;



    //    private HomeViewModel homeViewModel;
    private static final String TAG = "HomeFragment";
    private LocationManager mLocationManager;

    public HomeFragment() {
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
//        SupportMapFragment mapFragment = (SupportMapFragment)getFragmentManager().findFragmentById(R.id.map1); //옛코드
        FloatingActionButton button = (FloatingActionButton) v.findViewById(R.id.fab);
        button.setOnClickListener(mClickListener);
        FloatingActionButton button2 = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        button2.setOnClickListener(mClickListener);

        return v;
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;/    }

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

//        // 허용하겠습니까? 구문
//        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show();
//            return;
//        }
//        // For LolliPop and Higher
//
//        // 실제 로케이션 관련
//        LocationManager locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 25, locationListener);
//        //GPS data receive 할 때의 조건, 수신시간(ms), 수신 기준 거리(m)

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //초기 지도 설정, 인하대학교
        String inhaTitle = "Inha University";
        double lon = 126.65304444; //경도
        double lat = 37.44965000; //위도
        LatLng inhaPos = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(inhaPos).title(inhaTitle)).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inhaPos, 14));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }

        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener(){

            @Override
            public void onMapLongClick(final LatLng latLng) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_place_info, null);
                builder.setView(view);
                final Button button_submit = (Button) view.findViewById(R.id.button_dialog_placeInfo);
                final EditText editText_placeTitle = (EditText) view.findViewById(R.id.editText_dialog_placeTitle);
                final EditText editText_placeDesc = (EditText) view.findViewById(R.id.editText_dialog_placeDesc);

                final AlertDialog dialog = builder.create();
                button_submit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String string_placeTitle = editText_placeTitle.getText().toString();
                        String string_placeDesc = editText_placeDesc.getText().toString();
                        Toast.makeText(getActivity(), string_placeTitle+"\n"+string_placeDesc,Toast.LENGTH_SHORT).show();


                        //맵을 클릭시 현재 위치에 마커 추가
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(string_placeTitle);
                        markerOptions.snippet(string_placeDesc);
                        markerOptions.draggable(true);
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

//                        if ( addedMarker != null ) mMap.clear();
                        addedMarker = mMap.addMarker(markerOptions);

                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "onResume");
        if ((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, this);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, this);
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(TAG, "onPause");
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, String.valueOf(location.getLatitude()));
        Log.i(TAG, String.valueOf(location.getLongitude()));
        //            LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
        //?
        String markerTitle = "#"+ markerNum + ", 위도:" + String.valueOf(location.getLatitude())
                + " 경도:" + String.valueOf(location.getLongitude());

        //현재 위치에 마커 생성하고 이동
//        setCurrentLocation(location, markerTitle);

        mCurrentLocation = location;
        onAddMarker(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i(TAG, "Provider " + provider + " has now status: " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i(TAG, "Provider " + provider + " is enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(TAG, "Provider " + provider + " is disabled");

    }

//    public void setCurrentLocation(Location location, String markerTitle) {
//        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//        markerNum++;
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(currentLatLng);
//        markerOptions.title(markerTitle);
//        mMap.addMarker(markerOptions).showInfoWindow();
//        //또는 아래 방법 이용
//        //mMap.addMarker(new MarkerOptions().position(currentLatLng).title(markerTitle)).showInfoWindow();
//
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 18));
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                return false;
//            }
//        });
//    }

    public void onAddMarker(Location location){
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());

        if (currentMarker!=null) {
            currentMarker.remove();
            myCircle.remove();
            currentMarker = null;
        }
        else if (currentMarker==null) {
//                currentMarker = mMap.addMarker(new MarkerOptions().position(location).
//                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            //나의위치 마커
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.circle);
            Bitmap b=bitmapdraw.getBitmap();
            Bitmap circleMarker = Bitmap.createScaledBitmap(b, 50, 50, false);
            MarkerOptions mymarker = new MarkerOptions()
                    .position(position)
                    .icon(BitmapDescriptorFactory.fromBitmap(circleMarker))
                    .anchor(0.5f,0.5f);;
                       //마커위치

            // 반경 100M원
            CircleOptions circleoptions = new CircleOptions().center(position) //원점
                    .radius(100)      //반지름 단위 : m
                    .strokeWidth(0f)  //선너비 0f : 선없음
                    .fillColor(Color.parseColor("#4DEEA5A5")); //배경색


            //마커추가
            currentMarker = mMap.addMarker(mymarker);

            //원추가
            myCircle = mMap.addCircle(circleoptions);
        }

    }
    Button.OnClickListener mClickListener = new Button.OnClickListener(){
        public void onClick(View v){
            switch (v.getId()){
                case R.id.fab:
                    if (mCurrentLocation == null) {
                        Toast.makeText(getActivity(), "현재 GPS 신호가 약해, 위치를 찾을 수 없습니다.",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    LatLng position2 = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                    Log.d(TAG, position2.toString());


                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position2, 17));
                    break;
                case R.id.floatingActionButton:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.place_list, null);
                    builder.setView(view);
//                    final Button button_submit = (Button) view.findViewById(R.id.button_dialog_placeInfo);
//                    final EditText editText_placeTitle = (EditText) view.findViewById(R.id.editText_dialog_placeTitle);
//                    final EditText editText_placeDesc = (EditText) view.findViewById(R.id.editText_dialog_placeDesc);

                    final AlertDialog dialog = builder.create();
//                    button_submit.setOnClickListener(new View.OnClickListener() {
//                        public void onClick(View v) {
//                            String string_placeTitle = editText_placeTitle.getText().toString();
//                            String string_placeDesc = editText_placeDesc.getText().toString();
//                            Toast.makeText(getActivity(), string_placeTitle+"\n"+string_placeDesc,Toast.LENGTH_SHORT).show();

//                    dialog.dismiss();
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.WRAP_CONTENT;;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                    dialog.show();
                    Window window = dialog.getWindow();
                    window.setAttributes(lp);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    dialog.show();




            }

            } // end of switch
         //end of onClick
    }; // end of mClickListener



//    public void curlocClick(){
//        LatLng position2 = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position2, 17));
//    }

}

