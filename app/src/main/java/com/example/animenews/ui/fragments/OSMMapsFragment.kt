package com.example.animenews.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.example.animenews.databinding.FragmentOsmmapsBinding
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class OSMMapsFragment : Fragment() {

    private lateinit var binding: FragmentOsmmapsBinding
    private lateinit var map: MapView
    private val LATITUDE = -1.4863975
    private val LONGITUDE = -78.3046659

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOsmmapsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Preguntamos directamente los permisos */
        gpsPermission.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
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

    /* Se inicializa el mapa con la opciones necesarias */
    private fun initMap() {

        Configuration.getInstance()
            .load(
                binding.root.context,
                getDefaultSharedPreferences(binding.root.context)
            )

        map = binding.mapView
        map.setTileSource(TileSourceFactory.MAPNIK)
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

    /* Se actualiza el mapa con la ubicacion del usuario y se agrega una marca */
    private fun updateMap(latitude: Double, longitude: Double, gps: Boolean) {
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.ALWAYS)
        map.setMultiTouchControls(true)
        val point = GeoPoint(latitude, longitude)

        if (gps) {
            val marker = Marker(map)
            marker.position = point
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

            val d = ResourcesCompat.getDrawable(
                resources,
                com.example.animenews.R.drawable.red_marker,
                null
            )

            marker.icon = d
            marker.title = "Usted está aquí"
            map.overlays.add(marker)
            map.controller.setZoom(16.0)
        } else {
            map.controller.setZoom(8.0)
            Snackbar.make(binding.mapView, "El GPS esta desactivado", Snackbar.LENGTH_SHORT).show()
        }

        with(map.controller) {
            setCenter(point)
            animateTo(point)
        }
    }
}