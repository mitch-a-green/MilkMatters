package com.milkmatters.honoursproject.milkmatters.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.milkmatters.honoursproject.milkmatters.R;

/**
 * Customised Map Info Window Adapter.
 */
public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private LayoutInflater layoutInflater;
    private View view;

    /**
     * Constructor.
     * @param inflater the layout inflater
     */
    public MapInfoWindowAdapter(LayoutInflater inflater) {
        this.layoutInflater = inflater;
    }

    /**
     * Overridden getInfoWindow method.
     * @param marker the marker
     * @return null
     */
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    /**
     * Overridden getInfoContents method.
     * Inflates the window layout, and populates the text views.
     * @param marker the marker
     * @return the info window view
     */
    @Override
    public View getInfoContents(Marker marker) {
        this.view = layoutInflater.inflate(R.layout.map_info_window, null);

        TextView titleTextView = (TextView) this.view.findViewById(R.id.title_textview);
        TextView contentTextView = (TextView) this.view.findViewById(R.id.content_textview);

        titleTextView.setText(marker.getTitle());
        contentTextView.setText(marker.getSnippet());

        return this.view;
    }
}