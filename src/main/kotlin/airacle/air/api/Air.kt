package airacle.air.api

class Air : IAir, IAirMetaInfo {

    companion object {
        const val VERSION_CODE = 1
        const val VERSION_NAME = "0.0.1"
    }

    override fun metaInfo(): IAirMetaInfo {
        return this
    }

    override fun versionCode(): Int {
        return VERSION_CODE
    }

    override fun versionName(): String {
        return VERSION_NAME
    }
}