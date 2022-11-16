package gemastik.pendekar.data.model

data class SearchHistoryDangerModel(
    var id: Int,
    var searchName: String,
    var searchAddress:String,
    var lat: Double,
    var lng: Double
)
