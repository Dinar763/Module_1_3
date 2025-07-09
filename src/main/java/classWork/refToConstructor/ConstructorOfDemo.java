package classWork.refToConstructor;

public class ConstructorOfDemo {
    public static void main(String[] args) {
        MyFunc myFunc = MyClass::new;

        MyClass mc = myFunc.func(100);

        System.out.println(mc.getVal());
    }
}
