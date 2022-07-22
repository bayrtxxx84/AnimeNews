package com.example.animenews.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.animenews.R
import com.example.animenews.databinding.FragmentMapBoxBinding
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.OnMapClickListener
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.location


class MapBoxFragment : Fragment() {

    private lateinit var mapView: MapView
    private lateinit var binding: FragmentMapBoxBinding

    private val LATITUDE = -1.4863975
    private val LONGITUDE = -78.3046659

    private lateinit var mapboxMap: MapboxMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBoxBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Preguntamos por los permisos */
        gpsPermission.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )

        initMap()
    }

    /* Recuperamos los permisos concedidos */
    @SuppressLint("MissingPermission")
    val gpsPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perm ->

            when {
                perm.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    getLocation()
                }
                perm.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    getLocation()
                }
                else -> {
                    Snackbar.make(binding.mapView, "Permiso denegado", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }

    /* Obtenemos la ubicacion del ususario */
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        activity?.let { LocationServices.getFusedLocationProviderClient(it).lastLocation }
            ?.apply {
                addOnSuccessListener { loc ->
                    loc.let {
                        if (loc != null) {
                            updateMap(loc.latitude, loc.longitude, true)
                        } else {
                            updateMap(LATITUDE, LONGITUDE, false)
                        }
                    }
                }
            }
    }

    /* Se inicializa el mapa con la opciones necesarias */
    private fun initMap() {
        mapView = binding.mapView
        mapView.getMapboxMap().loadStyleUri(
            Style.MAPBOX_STREETS
        )
        mapboxMap = mapView.getMapboxMap()

        binding.mapView.gestures.addOnMapClickListener { point ->
            binding.mapView.location
                .isLocatedAt(point) { isPuckLocatedAtPoint ->
                    if (isPuckLocatedAtPoint) {
                        animationOnMapClick(point)
                    }
                }
            true
        }

    }

    /* Se actualiza el mapa con la ubicacion del usuario y se agrega una marca */
    private fun updateMap(latitude: Double, longitude: Double, gps: Boolean) {
        if (gps) {

            val point = Point.fromLngLat(longitude, latitude)
            val icon = BitmapFactory.decodeResource(resources, R.drawable.red_marker)
            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(icon)
            pointAnnotationManager.create(pointAnnotationOptions)

            val camera = CameraOptions.Builder()
                .center(point)
                .zoom(15.5)
                .bearing(-17.6)
                .build()
            mapView.getMapboxMap().setCamera(camera)
        }
    }

    /* Animación para cuando se hace clic en la marca */
    fun animationOnMapClick(point: Point): Boolean {
        mapboxMap.flyTo(
            cameraOptions {
                center(point) // Sets the new camera position on click point
                zoom(12.0) // Sets the zoom
                bearing(180.0) // Rotate the camera
                pitch(50.0) // Set the camera pitch
            },
            mapAnimationOptions {
                duration(5000)
            }
        )
        return true
    }
}