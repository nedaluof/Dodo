package com.eightkdevelopers.dodo.utilities;
/**
 * Created by nidalouf on 3/8/2020.
 */
public class Constants {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EIDT_NOTE_REQUEST = 2;

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "EXTRA_PRIORITY";


    public static int getAddNoteRequest() {
        return ADD_NOTE_REQUEST;
    }

    public static int getEditNoteRequest() {
        return EIDT_NOTE_REQUEST;
    }

    public static String getExtraId() {
        return EXTRA_ID;
    }

    public static String getExtraTitle() {
        return EXTRA_TITLE;
    }

    public static String getExtraDescription() {
        return EXTRA_DESCRIPTION;
    }

    public static String getExtraPriority() {
        return EXTRA_PRIORITY;
    }
}
