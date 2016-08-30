public class Sample {
    @Test public static void method1(){}

    public static void method2() {}

//    @Test public void method5() {}


    @ExceptionTest(ArithmeticException.class)
    public static void method3(){
        int i = 0/0;
    }

    @ExceptionTest(ArithmeticException.class)
    public static void method4() {  // Should fail (wrong exception)
        int[] a = new int[0];
        int i = a[1];
    }

    @ExceptionTest(ArithmeticException.class)
    public static void method6() {}
}
