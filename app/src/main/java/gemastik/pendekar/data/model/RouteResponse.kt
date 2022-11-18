package gemastik.pendekar.data.model

import com.google.gson.annotations.SerializedName

data class RouteResponse(
    @SerializedName("routes")
    val route: List<RouteItem>
)


data class RouteItem(
    @SerializedName("id")
    val id:String,

    @SerializedName("sections")
    val sections:List<SectionData>
)

data class SectionData(
    @SerializedName("id")
    val id:String,

    @SerializedName("type")
    val type:String,

    @SerializedName("polyline")
    val polyline:String,

    @SerializedName("arrival")
    val arrival:ArrivalData
)

data class ArrivalData(
    @SerializedName("time")
    val time:String,

    @SerializedName("place")
    val place:PlaceData
)

data class PlaceData(
    @SerializedName("type")
    val type:String,

    @SerializedName("location")
    val location:LocationData,

    @SerializedName("originalLocation")
    val originalLocation:LocationData,
)

data class LocationData(
    @SerializedName("lat")
    val lat:Double,

    @SerializedName("lng")
    val lng:Double
)