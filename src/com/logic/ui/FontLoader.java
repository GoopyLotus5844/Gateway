package com.logic.ui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class FontLoader {

    public Font sevenSegFont;

    public FontLoader(){
        sevenSegFont = getFont("/DSEG7Classic-Bold.ttf");
    }

    private Font getFont(String path) {
        URL url = getClass().getResource(path);
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(url.toURI()));
        } catch (FontFormatException | IOException | URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
