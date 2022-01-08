package be.dimitrigevers.android.geversdimitrinotifier.models

class Notification(val id: String, val message: String, val fromId: String, val toId:String, val timestamp:Long) {
    constructor(): this("","","","",-1)
}
