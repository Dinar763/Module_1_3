package classWork.fabriqueMethod;

class ConstructorRefDemo3 {

    static <R, T> R myClassFactory(MyFunc<R, T> cons, T v) {
        return cons.func(v);
    }

    public static void main(String[] args) {

        MyFunc<MyClass1<Double>, Double> myClassCons = MyClass1::new;
        MyClass1<Double> mc = myClassFactory(myClassCons, 100.1);
        System.out.println(mc.getVal());

        MyFunc<MyClass2, String> myClassCons2 = MyClass2::new;
        MyClass2 mc2 = myClassFactory(myClassCons2, "Diego");
        System.out.println(mc2.getStr());
    }
}
