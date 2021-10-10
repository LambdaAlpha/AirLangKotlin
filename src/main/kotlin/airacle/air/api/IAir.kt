package airacle.air.api

interface IAir {
    fun metaInfo(): IAirMetaInfo
}

interface IAirMetaInfo {
    fun versionCode(): Int

    fun versionName(): String
}

