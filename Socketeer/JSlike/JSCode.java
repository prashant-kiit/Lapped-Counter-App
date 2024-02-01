/**
 * JSCode
 */

package Socketeer.JSlike;

interface Function1 {
    int add(int a);
}

interface Function {
    int show(int a);
}
public class JSCode {
    static {
        new Thread(new CallStack()).start();
        new Thread(new DeathLetterQueue()).start();
        new Thread(new EventLoop()).start();
    }
    public static void main(String[] args) {
        // //
        // // Lambda Function
        // //
        // Function1 fun1 = (a) -> { return a;};
        // System.out.println(fun1.add(3));
        
        // // global scope not closure
        // int c = 7;
        // Function1 fun2 = (a) -> { return a + c;};
        // System.out.println(fun2.add(5));

        // // local scope
        // Function1 fun3 = (a) -> { int d = 9; return a + d;};
        // System.out.println(fun3.add(7));
        // // System.out.println(d); // not visible

        // JS like Async Demo
        Function fun = (para) -> para;
        System.out.println(fun);
        // "fun(1)"
        // "fun(2)"
        Browser.setTimeout(fun, 1, 5000);
        Browser.setTimeout(fun, 2, 2500);
    }
}