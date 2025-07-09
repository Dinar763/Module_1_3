package classWork.socket;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpURLDemo {
    public static void main(String[] args) throws IOException {
        URL hp = new URL("http://www.sempermoto.ru");
        HttpURLConnection hpCon = (HttpURLConnection) hp.openConnection();

        System.out.println("Метод запроса: " + hpCon.getRequestMethod());

        // Отобразить код ответа
        System.out.println("Код ответа: " + hpCon.getResponseCode());

        // Отобразить сообщение ответа
        System.out.println("Сообщение ответа: " + hpCon.getResponseMessage());

        // Получить список полей заголовка и набор ключей заголовка
        Map<String, List<String>> hdrMap = hpCon.getHeaderFields();

        Set<String> hdrField = hdrMap.keySet();

        System.out.println("\nЗаголовок:");

        // Отобразить все ключи и значения заголовка
        for (String k : hdrField) {
            System.out.println("Ключ: " + k + " Значение: " + hdrMap.get(k));
        }
    }
}
