package classWork.refToMeth;

class HighTemp {

    private int hTemp;

    public HighTemp(int ht) {
        hTemp = ht;
    }

    boolean sameTemp(HighTemp ht2) {
        return hTemp == ht2.hTemp;
    }

    boolean lessThanTemp (HighTemp ht2) {
        return hTemp < ht2.hTemp;
    }
}
