package airacle.air.core.interpreter

interface IContexts {
    fun normalizePath(path: AirValue): AirValue
    fun focus(path: AirValue): AirValue
    fun focused(): AirValue

    fun exist(path: AirValue): AirValue
    fun read(path: AirValue): AirValue

    fun write(path: AirValue, value: AirValue): AirValue
    fun move(oldPath: AirValue, newPath: AirValue): AirValue
    fun insert(path: AirValue, value: AirValue): AirValue
    fun delete(path: AirValue): AirValue
}