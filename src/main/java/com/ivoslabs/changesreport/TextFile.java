/**
 * 
 */
package com.ivoslabs.changesreport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.BiConsumer;

/**
 * @author VI9XXL8
 *
 */
public class TextFile {

    public void readFile(String url, BiConsumer<Reader, String> byLine) {

	try (BufferedReader br = new BufferedReader(new FileReader(url))) {

	    String sCurrentLine;

	    Reader reader = new Reader();
	    int c = 0;

	    while ((sCurrentLine = br.readLine()) != null) {
		reader.setLine(c);
		byLine.accept(reader, sCurrentLine);
		reader.setPrevLine(sCurrentLine);
		c++;
	    }

	} catch (IOException e) {
	    throw new RuntimeException("readTextFile", e);
	}
    }

}
