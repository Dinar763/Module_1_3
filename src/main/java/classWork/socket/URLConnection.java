package classWork.socket;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class URLConnection {
    public static void main(String[] args) throws IOException {
        int c;
        URL hp = new URL("http://www.sempermoto.ru");
        java.net.URLConnection hpCOn = hp.openConnection();

        long d = hpCOn.getDate();
        if (d == 0)
            System.out.println("Нет инфы");
        else
            System.out.println("Дата: " + new Date(d));

        System.out.println("Content-type: " + hpCOn.getContentType());

        d = hpCOn.getExpiration();
        if (d == 0)
            System.out.println("Нет инфы о сроке действия");
        else
            System.out.println("Срок действия до: " + new Date(d));

        d = hpCOn.getLastModified();
        if (d == 0)
            System.out.println("Нет инфы о последнем изменении");
        else
            System.out.println("Дата последнего изменения : " + new Date(d));


    }
}
