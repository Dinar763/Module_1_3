package classWork.refToMethWithGenericMeth;

class MyArrayOps {

    static <T> int countMatching(T[] vals, T target) {
        int count = 0;

        for (int i = 0; i < vals.length; i++) {
            if (vals[i] == target)
                count++;
        }

        return count;
    }
}
