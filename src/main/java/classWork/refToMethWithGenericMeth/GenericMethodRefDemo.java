package classWork.refToMethWithGenericMeth;

class GenericMethodRefDemo {

    static <T> int myOp(MyFunc<T> f, T[] vals, T target) {
        return f.func(vals, target);
    }

    public static void main(String[] args) {
        Integer[] vals = {1, 2, 3, 4, 5, 6, 7, 8, 4, 4};
        String[] strs = {"One", "Two", "Three", "Two"};
        int count;

        count = myOp(MyArrayOps::countMatching, vals, 4);
        System.out.println("В массиве vals вот столько " + count);

        count = myOp(MyArrayOps::countMatching, strs, "Two");
        System.out.println("В массиве str вот столько " + count);
    }
}
