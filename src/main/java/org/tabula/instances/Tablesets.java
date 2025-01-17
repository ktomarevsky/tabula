package org.tabula.instances;

import org.tabula.objects.Tableset;
import org.tabula.utils.fileutils.FileReaderWriter;
import org.tabula.utils.xmlutils.XMLHandler;
import org.w3c.dom.Document;

import java.io.File;
import java.util.List;

public class Tablesets {

    static private Tablesets instance;
    private List<Tableset> tablesets;

    static public Tablesets getInstance() {
        if(instance == null) {
            instance = new Tablesets();
            return instance;
        }
        return instance;
    }

    private Tablesets() {
        Document document = FileReaderWriter.readXMLFromFile(new File(FileReaderWriter.PATH_TO_TABLESET_LIST));
        tablesets = XMLHandler.getTablesetsFromXML(document);
    }

    public List<Tableset> getTablesets() {
        return tablesets;
    }
}
