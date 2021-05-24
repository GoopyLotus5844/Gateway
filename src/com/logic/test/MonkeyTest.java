package com.logic.test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.util.Iterator;

public class MonkeyTest {

    public static void main(String[] args){
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("JPEG");
        while (readers.hasNext()) {
            System.out.println("reader: " + readers.next());
        }
    }

}
