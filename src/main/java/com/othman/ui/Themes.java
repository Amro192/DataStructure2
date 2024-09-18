package com.othman.ui;

import atlantafx.base.theme.*;

/*
 * This enum is used to store all the themes available in the application.
 */
public enum Themes {
    CUPERTINO_LIGHT(new CupertinoLight()),
    CUPERTINO_DARK(new CupertinoDark()),
    NORD_DARK(new NordDark()),
    NORD_LIGHT(new NordLight()),
    PRIMER_DARK(new PrimerDark()),
    PRIMER_LIGHT(new PrimerLight());

    private final Theme theme;

    Themes(Theme theme) {
        this.theme = theme;
    }

    public Theme getTheme() {
        return theme;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
