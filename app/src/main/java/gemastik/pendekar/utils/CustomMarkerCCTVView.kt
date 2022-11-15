package gemastik.pendekar.utils

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import gemastik.pendekar.R

class CustomMarkerCCTVView(root: ViewGroup,
                           text: String?) : FrameLayout(root.context) {
    private var mImage: ImageView
    private var mTitle: TextView

    init {
        View.inflate(context, R.layout.custom_marker_view, this)
        mImage = findViewById(R.id.marker_image)
        mTitle = findViewById(R.id.marker_title)
        measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        mTitle.text = text
//        if (isSelected) {
        mImage.setImageResource(R.drawable.ic_pin_cctv)
//        } else {
//            mImage.setImageResource(R.drawable.ic_marker_not_selected)
//        }
    }

    companion object {
        fun getMarkerIcon(root: ViewGroup, text: String?): BitmapDescriptor {
            val markerView = CustomMarkerCCTVView(root, text)
            markerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            markerView.layout(0, 0, markerView.measuredWidth, markerView.measuredHeight)
            markerView.isDrawingCacheEnabled = true
            markerView.invalidate()
            markerView.buildDrawingCache(false)
            return BitmapDescriptorFactory.fromBitmap(markerView.drawingCache)
        }
    }
}