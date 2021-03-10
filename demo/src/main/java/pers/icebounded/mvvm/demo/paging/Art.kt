package pers.icebounded.mvvm.demo.paging

import kotlinx.serialization.Serializable


/**
 * Created by Andy
 * on 2021/3/10
 */
@Serializable
data class Art(
    val id: Long,
    val title: String? = null,
    val url: String? = null,
    val images: ArrayList<ArtImage>? = null,
    val people: ArrayList<ArtPeople>? = null,
) {
    fun getImageUrl() :String{
        if(!images.isNullOrEmpty()) {
            return images[0].baseimageurl
        }
        return ""
    }
}

@Serializable
data class ArtImage(
    val baseimageurl: String
)

@Serializable
data class ArtPeople(
    val name: String,
    val gender: String,
    val role: String,
)