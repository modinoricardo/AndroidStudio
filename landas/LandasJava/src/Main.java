//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Double a = 3.7 , b = 5.0 ;

        LaCalculadora miCalculadora = new LaCalculadora();
        Double resultado = miCalculadora.operacionDosParametros(a, b, (a1, b1) -> a1 + b1);
        System.out.println(resultado);
    }

}