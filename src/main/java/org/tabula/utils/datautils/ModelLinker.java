package org.tabula.utils.datautils;

import org.tabula.objects.model.*;

public class ModelLinker {

    static public void compose(Model model, String tableFromName, String fieldFromName, String tableToName, String fieldToName) {
        Table tableFrom = model.findTable(tableFromName);
        if(tableFrom == null) {

            tableFrom = new Table();
            tableFrom.setName(tableFromName);

            model.getTables().add(tableFrom);
        }

        Field fieldFrom = tableFrom.findField(fieldFromName);
        if(fieldFrom == null) {

            fieldFrom = new Field();
            fieldFrom.setName(fieldFromName);
            fieldFrom.setKeyType(FieldParameters.PRIMARY_KEY);
            fieldFrom.setTable(tableFrom);

            tableFrom.addField(fieldFrom);
        }

        Table tableTo = model.findTable(tableToName);
        if(tableTo == null) {

            tableTo = new Table();
            tableTo.setName(tableToName);

            model.getTables().add(tableTo);
        }

        Field fieldTo = tableTo.findField(fieldToName);
        if(fieldTo == null) {

            fieldTo = new Field();
            fieldTo.setName(fieldToName);
            fieldTo.setKeyType(FieldParameters.FOREIGN_KEY);
            fieldTo.setTable(tableTo);

            tableTo.addField(fieldTo);
        }

        Dependence dependence = model.findDependence(tableFromName, fieldFromName, tableToName, fieldToName);
        if(dependence == null) {
            dependence = new Dependence();
            dependence.setTableFrom(tableFrom);
            dependence.setFieldFrom(fieldFrom);
            dependence.setTableTo(tableTo);
            dependence.setFieldTo(fieldTo);

            fieldFrom.addDependence(dependence);
            fieldTo.addDependence(dependence);

            model.getDependencies().add(dependence);
        }
    }
}
