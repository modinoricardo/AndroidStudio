class Calculadora {
    fun operacionDosParametros(x: Double, y: Double, myFun: (Double, Double) -> Double): Double {
        return myFun(x,y)
    }

    fun operacionUnParametro(x: Double, myFun: (Double) -> Double): Double {
        return myFun(x)
    }
}