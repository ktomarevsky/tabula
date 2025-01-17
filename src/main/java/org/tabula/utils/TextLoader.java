package org.tabula.utils;



public class TextLoader {
    
    // Tree Menu
    public static String CREATE_EMPTY_MODEL;
    public static String LOAD_FROM_DB;
    public static String PASTE;
    public static String COPY;
    public static String DELETE;
    public static String SHOW_MAPPING;
    
    // Table Constructor
    public static String TABLE_CONSTRUCTOR;
    public static String ADD_COLUMN;
    public static String DELETE_COLUMN;
    public static String OK;
    public static String CANCEL;
    
    public static int language;
    public static final int RUSSIAN = 1;
    public static final int ENGLISH = 2;
    
    static {
        language = ENGLISH;
        if(language == RUSSIAN) {
            initRussian();
        }
        if(language == ENGLISH) {
            initEnglish();
        }
    }
    
    public static void initRussian() {
        CREATE_EMPTY_MODEL = "Создать пустую модель";
        LOAD_FROM_DB = "Загрузить модель из базы";
        PASTE = "Вставить";
        COPY = "Копировать";
        DELETE = "Удалить";
        SHOW_MAPPING = "Отобразить маппинг";
        
        TABLE_CONSTRUCTOR = "Конструктор таблицы";
        ADD_COLUMN = "Добавить колонку";
        DELETE_COLUMN = "Удалить колонку";
        OK = "OK";
        CANCEL = "Отмена";
    }
    
    public static void initEnglish() {
        // Tree Menu
        CREATE_EMPTY_MODEL = "Create empty Model";
        LOAD_FROM_DB = "Load Model from Database ";
        PASTE = "Paste";
        COPY = "Copy";
        DELETE = "Delete";
        SHOW_MAPPING = "Show Mapping";

        // Table Constructor
        TABLE_CONSTRUCTOR = "Table Constructor";
        ADD_COLUMN = "Add Column";
        DELETE_COLUMN = "Delete Column";
        OK = "OK";
        CANCEL = "Cancel";
    }
}
