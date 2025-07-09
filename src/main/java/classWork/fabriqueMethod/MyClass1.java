package classWork.fabriqueMethod;

class MyClass1<T> {
    private T val;

    MyClass1(T val) {
        this.val = val;
    }

    MyClass1() {
        this.val = null;
    }

    T getVal() {
        return val;
    }
}
