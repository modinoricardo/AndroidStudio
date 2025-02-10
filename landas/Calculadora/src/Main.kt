//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val a = 3.1;
    val b = 1.7;
    val miCalculadora = Calculadora()

    val funSumar = fun(a:Double, b:Double):Double{ return a+b }
    val resultado = miCalculadora.operacionDosParametros(a,b,funSumar)
    println(resultado)

    val funSumar2 = {x:Double,y:Double->x+y}
    val resultado2 = miCalculadora.operacionDosParametros(a,b,funSumar)
    println(resultado2)

    val resultado3 = miCalculadora.operacionDosParametros(a,b) {x:Double,y:Double->x-y}
    println(resultado3)

    val funCuadrado = {x:Double ->x * x}
    val resultado4 = miCalculadora.operacionUnParametro(a, funCuadrado)
    println(resultado4)

    val resultado5 = miCalculadora.operacionUnParametro(a) {it * it}
    println(resultado5)

}