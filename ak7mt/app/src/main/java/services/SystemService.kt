package services

object SystemService {


    val allowedLocation: Boolean = false


    fun initLocation() {}


    private  val units :String = "something"

    fun getUnits(): String {
        if(units.equals("si")){
            return  "°C"
        }
        else{
            return "F"
        }
    }
}