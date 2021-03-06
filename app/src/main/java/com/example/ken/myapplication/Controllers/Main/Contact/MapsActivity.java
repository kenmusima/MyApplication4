package com.example.ken.myapplication.Controllers.Main.Contact;



import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.ken.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera 51.583020, 4.786258
        LatLng latLng = new LatLng(-1.283101, 36.825316);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Odeon Cinema"));

        //Change positioning
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17.5f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
