package airacle.air.api

class Air {

    companion object {
        val META_INFO: AirMetaInfo = AirMetaInfo(
            1,
            "0.0.1"
        )
    }

    fun metaInfo(): AirMetaInfo {
        return META_INFO
    }
}

data class AirMetaInfo(
    val versionCode: Int,
    val versionName: String,
)