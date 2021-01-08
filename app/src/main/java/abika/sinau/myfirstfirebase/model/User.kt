package abika.sinau.myfirstfirebase.model

import java.io.Serializable

/**
 * Created by Abika Chairul Yusri on 27/12/2020
 * Bismillahirrahmanirrahim
 */
data class User(
    val name: String,
    var pekerjaan: String,
    var hp: String,
    var alamat: String,
    val key: String? = null
) : Serializable {
    constructor() : this("", "", "", "", "")
}
