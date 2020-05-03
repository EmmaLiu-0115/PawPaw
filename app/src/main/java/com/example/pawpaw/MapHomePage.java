package com.example.pawpaw;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MapHomePage extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener,GoogleMap.OnInfoWindowClickListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Location mLastLocation;
    private FloatingActionButton LogoButton;
    private FloatingActionButton ListButton;
    private FloatingActionButton ContactButton;
    private LatLng currentLocation;
    private FusedLocationProviderClient fusedLocationClient;

    String pasID;
    String name;
    String Type;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_home_page);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);



        LogoButton = (FloatingActionButton) findViewById(R.id.logo);
        ContactButton = (FloatingActionButton) findViewById(R.id.contact);
        ListButton = (FloatingActionButton) findViewById(R.id.list);

        LogoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addALocationIntent = new Intent(MapHomePage.this, AddALocation.class);
                Bundle args = new Bundle();
                args.putParcelable("currentLocation", currentLocation);
                //TODO: send user ID

                addALocationIntent.putExtra("bundle",args);
                startActivity(new Intent(addALocationIntent));
            }
        });
        ContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapHomePage.this, Friends.class));
            }
        });
        ListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapHomePage.this, ListMainActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 3
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);

        final LatLng snowqualmie = new LatLng(43.0712741,-89.3911507);

        final ArrayList<com.example.pawpaw.Location> LocationMarker= new ArrayList<>();
        final FirebaseFirestore database = FirebaseFirestore.getInstance();

        Log.w("MapHomePage","count1");



        final Intent LocationInfoIntent;
        LocationInfoIntent = new Intent(MapHomePage.this, LocationInfo.class);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(final Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.w("MapHomePage",location.getLatitude() +"," +location.getLongitude()+"test");
                            //TODO: Change snowqualmie to current location

                            database.collection("locations")
                                    .whereLessThan("longitude", location.getLongitude() + 1.0)
                                    .whereGreaterThan("longitude", location.getLongitude()-1.0)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                Log.w("MapHomePage","count2");
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    //Put all results that we get from database in result list
                                                    LocationMarker.add(document.toObject(com.example.pawpaw.Location.class));

                                                    Log.d("MapHomePage", document.getId() + " => " + document.getData());
                                                }
                                                Log.d("MapHomePage", String.valueOf(LocationMarker.size()));

                                                double a;
                                                double b;
                                                for(int i = 0 ;i<LocationMarker.size();i++) {
                                                    com.example.pawpaw.Location lo = LocationMarker.get(i);
                                                    LatLng lc = new LatLng(LocationMarker.get(i).getLatitude(),LocationMarker.get(i).getLongitude());
                                                    Log.d("MapHomePage", lc.longitude +","+lc.latitude);
                                                    a = lc.latitude;
                                                    b = lc.longitude;

                                                    if (location.getLatitude()-1.0<= a && location.getLatitude()+1.0>= a ){
                                                        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(a,b))
                                                                .title(LocationMarker.get(i).getLocationName())
                                                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                                                        InfoData info = new InfoData();

                                                        pasID = lo.getLocationID();
                                                        String pasID = lo.getLocationID();
                                                        Log.w("LocationID",pasID);
                                                        String name = lo.getLocationName();
                                                        String Type = lo.getLocationType();
                                                        String address = lo.getLocationAddress();

                                                        double rate = lo.getAvgRating();
                                                        double price = lo.getAvgPrice();
//                                                        info.setImage(image);
                                                        info.setHotel("Type: attraction "+ Type);
                                                        info.setFood("Rating : " +rate);
                                                        info.setAddres("Address " + address);

                                                        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(MapHomePage.this);
                                                        mMap.setInfoWindowAdapter(customInfoWindow);

                                                        if (getIntent().getExtras() == null || getIntent().getExtras().getString("newUser").equals("0")){
                                                            Marker m = mMap.addMarker(markerOptions);
                                                            m.setSnippet(pasID);
                                                            Log.w("MapHomePageaa", lc.latitude +","+lc.longitude);
                                                            m.setTag(info);
                                                            //m.showInfoWindow();
                                                        }


                                                    }
                                                }
                                                Log.w("MapHomePageaa", "added");

//                                                Bundle args = new Bundle();
                                                //args.putString("LocationID", pasID);

                                            } else {
                                                Log.d("MapHomePage", "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });
                        }
                        Log.w("MapHomePage","count4");
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));

                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                LocationInfoIntent.putExtra("LocationID",marker.getSnippet());
                                startActivity(LocationInfoIntent);
                            }
                        });
                    }
                });
    }

    @Override
    public void onLocationChanged(Location location) {

    }
    protected void placeMarkerOnMap(LatLng location) {
        // 1
        MarkerOptions markerOptions = new MarkerOptions().position(location);
        // 2
        mMap.addMarker(markerOptions);
    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        mMap.setMyLocationEnabled(true);

        LocationAvailability locationAvailability =
                LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation
                        .getLongitude());

                //add pin at user's location
                //placeMarkerOnMap(currentLocation);
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setUpMap();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        startActivity(new Intent(MapHomePage.this, LocationInfo.class));

    }
}
