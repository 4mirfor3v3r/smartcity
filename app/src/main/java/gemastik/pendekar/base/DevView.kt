package gemastik.pendekar.base

interface DevView {
    fun onMessageAlert(message:String)
    fun onMessageError(message: String)
    fun onSessionEnd()
}