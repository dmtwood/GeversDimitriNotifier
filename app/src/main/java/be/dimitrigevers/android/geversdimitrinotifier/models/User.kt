package be.dimitrigevers.android.geversdimitrinotifier

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val userName: String, val user_img_uri: String): Parcelable {
  constructor() : this("", "", "")
}