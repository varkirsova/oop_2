package oop_2;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.XMLStreamException;

public class XmlProcessor {

    public static void process(Path filePath) throws IOException, XMLStreamException {
        Statistic stats = new Statistic();

        XMLInputFactory factory = XMLInputFactory.newInstance();

        try (FileInputStream fis = new FileInputStream(filePath.toFile())) {
            XMLEventReader reader = factory.createXMLEventReader(fis);

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();

                    if ("item".equals(startElement.getName().getLocalPart())) {
                        String city = getAttribute(startElement, "city");
                        String street = getAttribute(startElement, "street");
                        String house = getAttribute(startElement, "house");
                        int floor = parseFloor(getAttribute(startElement, "floor"));

                        stats.addRecord(city, street, house, floor);
                    }
                }
            }
        }

        stats.printStatistics();
    }


    private static String getAttribute(StartElement element, String name) {
        if (element.getAttributeByName(javax.xml.namespace.QName.valueOf(name)) != null) {
            return element.getAttributeByName(javax.xml.namespace.QName.valueOf(name)).getValue().trim();
        }
        return "";
    }

    private static int parseFloor(String floorStr) {
        try {
            int f = Integer.parseInt(floorStr);
            if (f >= 1 && f <= 5) return f;
        } catch (NumberFormatException ignored) { }
        return 0;
    }
}
