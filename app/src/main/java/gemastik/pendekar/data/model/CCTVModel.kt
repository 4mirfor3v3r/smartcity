package gemastik.pendekar.data.model

import com.google.gson.annotations.SerializedName

data class CCTVModel(
    @SerializedName("_id")
    val id:String,

    @SerializedName("address")
    val address:String,

    @SerializedName("lat")
    val lat:String,

    @SerializedName("lng")
    val lng:String,

    @SerializedName("status")
    val status:String
)
