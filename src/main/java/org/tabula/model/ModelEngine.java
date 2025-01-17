package org.tabula.model;

import org.tabula.graphicalcomponents.tree.ModelNode;
import org.tabula.objects.model.Dependence;
import org.tabula.objects.model.Field;
import org.tabula.objects.model.Model;
import org.tabula.objects.model.Table;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;



/*
 *  Класс содержит методы, которые совершают действия в модели:
 *  - Создание объектов
 *  - Удаление объектов
 *  - Копирование и вставка объектов в модель
 *  - Создание маппинга между таблицами
 */
public class ModelEngine {

    // Метод позволяет выбирать одну или несколько таблиц на отрисовочной панели
    // для копирования или удаления
    public static void tableSelection(Model model, Table selectedTable, MouseEvent evt) {
        selectedTable = null;
        for (Table table : model.getTables()) {
            if (table.contains(evt.getX(), evt.getY())) {
                selectedTable = table;
            }
        }

        if (selectedTable != null) {
            selectedTable.setCursor(true);
            if (!evt.isControlDown()) {
                for (Table table : model.getTables()) {
                    if (table != selectedTable) {
                        table.setCursor(false);
                    }
                }
            }
        } else {
            model.resetSelection();
        }
    }

    // Описывает перетягивание объекта мышью
    public static void dragObject(Model model,
            Table selectedTable, Dependence selectedDependence,
            Integer deltaX, Integer deltaY, MouseEvent evt) {

        List<Table> tables = model.getTables();

        if (selectedTable != null) {
            Collections.swap(tables, tables.indexOf(selectedTable), tables.size() - 1);
            if(evt.getX() > 0 && evt.getY() > 0 && evt.getX() < model.getSize().width && evt.getY() < model.getSize().height) {
                selectedTable.move(evt.getX() - deltaX, evt.getY() - deltaY);
            }
            model.repaint();
        }

        if (selectedDependence != null) {
            selectedDependence.move(evt.getX());
            model.repaint();
        }
    }

    public static void showCursorOnDependence(Model model, MouseEvent evt) {
        model.setCursor(Cursor.getDefaultCursor());
        for (Dependence dependence : model.getDependencies()) {
            if (dependence.contains(evt.getX(), evt.getY())) {
                model.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));

            }
        }
    }

    static public void deleteTablesWithDependencies(Model model, ModelNode modelNode) {
        List<Table> tablesToDelete = new ArrayList<>();
        for (Table table : model.getTables()) {
            if (table.isCursor()) {
                tablesToDelete.add(table);
            }
        }

        List<Dependence> dependenciesToDelete = new ArrayList<>();

        tablesToDelete.forEach(table -> {
            String tableName = table.getName();
            List<Dependence> dep = model.findTableDependencies(tableName);
            dependenciesToDelete.addAll(dep);
        });

        for(Dependence dependence : dependenciesToDelete) {
            Dependence foundDependence = model.getDependencies().stream().filter(dependence::equals).findFirst().get();
            Field fieldFrom = dependence.getFieldFrom();
            fieldFrom.removeDependence(foundDependence);
            Table tableFrom = dependence.getTableFrom();
            if(fieldFrom.getDependencies().isEmpty()) {
                tableFrom.removeField(fieldFrom);
            }

            Table foundTable = model.getTables().stream().filter(tableFrom::equals).findFirst().get();
            foundTable.calculatePosition();
            foundTable.calculateDimension();
            foundTable.calculateDependenciesPosition();
            foundTable.calculateDependenciesDimension();

            Field fieldTo = dependence.getFieldTo();
            fieldTo.removeDependence(dependence);
            Table tableTo = fieldTo.getTable();

            if(fieldTo.getDependencies().isEmpty()) {
                tableTo.removeField(fieldTo);
            }

            foundTable = model.getTables().stream().filter(tableTo::equals).findFirst().get();
            foundTable.calculatePosition();
            foundTable.calculateDimension();
            foundTable.calculateDependenciesPosition();
            foundTable.calculateDependenciesDimension();
        }

        model.getTables().removeAll(tablesToDelete);
        model.getDependencies().removeAll(dependenciesToDelete.stream().distinct().collect(Collectors.toList()));

        modelNode.deleteTableNodes(tablesToDelete);
        modelNode.deleteDependenceNodes(dependenciesToDelete);
    }
}
