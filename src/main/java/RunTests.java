import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTests {
    static int tests = 0;
    static int passed = 0;

    public static void main(String[] args) throws ClassNotFoundException {

        Class testClass = Class.forName(args[0]);

        for (Method method : testClass.getMethods()) {
            tests(method);
            exceptionTests(method);
        }

        System.out.println("Passed: " + passed + ", Failed: " + (tests - passed));
    }

    public static void tests (Method method) {
        if (method.isAnnotationPresent(Test.class)) {
                tests++;
                try {
                    method.invoke(null);
                    passed++;
                } catch (InvocationTargetException wrappedException) {
                    Throwable exception = wrappedException.getCause();
                    System.out.println(method + " failed: " + exception);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Invalid @Test " + method);
                }
            }
    }

    public static void exceptionTests(Method method) {
        if (method.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
                try {
                    method.invoke(null);
                    passed++;
                } catch (InvocationTargetException wrappedException) {
                    Throwable exception = wrappedException.getCause();
                    Class<? extends Exception> exceptionType = method.getAnnotation(ExceptionTest.class).value();
                    if (exceptionType.isInstance(exception)) {
                        passed++;
                    } else {
                        System.out.println("Test " + method + "failed: expected" + exceptionType.getName() + ", got " + exception);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Invalid @Test " + method);
                }
            }
    }
}
