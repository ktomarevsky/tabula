package org.tabula.utils.checksumutils;

import org.tabula.utils.xmlutils.XMLHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class ChecksumHandler {

    static public long getCRC32Checksum(String xmlAsText) {
        byte[] bytes = xmlAsText.getBytes();
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue();
    }

    static public void verifyChecksum(Document document) {
        Node checkSum = document.getElementsByTagName("checksum").item(0);
        String checksumStr = checkSum.getTextContent();
        long expectedChecksum = 0;
        try {
            expectedChecksum = Long.parseLong(checksumStr);
        } catch(NumberFormatException numberFormatException) {
            throw new RuntimeException("Checksum is not a number!");
        }

        checkSum.setTextContent("");

        String xmlAsString = XMLHandler.getXMLAsString(document);
        long actualChecksum = ChecksumHandler.getCRC32Checksum(xmlAsString);

        if(expectedChecksum != actualChecksum) {
            throw new RuntimeException("XML document is corrupted: checksum not match!");
        }
    }
}
