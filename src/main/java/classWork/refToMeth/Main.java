package classWork.refToMeth;

public class Main {

    static <T> int counter(T[] vals, MyFunc<T> f, T v) {
        int count = 0;

        for (int i = 0; i < vals.length; i++) {
            if (f.func(vals[i], v))
                count++;
        }
        return count;
    }


    public static void main(String[] args) {
        int count;

        HighTemp[] weekDayHighs = {
                new HighTemp(89), new HighTemp(81),
                new HighTemp(82), new HighTemp(89),
                new HighTemp(99), new HighTemp(79),
                new HighTemp(92), new HighTemp(90) };
        count = counter(weekDayHighs, HighTemp::sameTemp, new HighTemp(89));
        System.out.println("Количество суток, когда самая высокая температура была" +
                " 89 градусов:" +
                + count);

        HighTemp[] weekDayHighs2 = {
                new HighTemp(39), new HighTemp(31),
                new HighTemp(32), new HighTemp(49),
                new HighTemp(-1), new HighTemp(39),
                new HighTemp(32), new HighTemp(40) };
        count = counter(weekDayHighs2, HighTemp::sameTemp , new HighTemp(39));
        System.out.println("Количество суток, когда самая высокая " +
                "температура была 39 градусов:" +
                + count);

        count = counter(weekDayHighs, HighTemp::lessThanTemp , new HighTemp(89));
        System.out.println("Количество суток, когда самая высокая температура " +
                "была меньше 89 градусов:" +
                + count);

        count = counter(weekDayHighs2, HighTemp::lessThanTemp , new HighTemp(19));
        System.out.println("Количество суток, когда самая высокая температура" +
                " была меньше 19 градусов:" +
                + count);
    }
}
