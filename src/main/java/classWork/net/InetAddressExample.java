package classWork.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressExample {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println(address);
        address = InetAddress.getByName("www.ya.ru");
        System.out.println(address);
        InetAddress[] arrAddres = InetAddress.getAllByName("www.ya.ru");
        for (InetAddress addre : arrAddres) {
            System.out.println(addre);
        }
    }
}
