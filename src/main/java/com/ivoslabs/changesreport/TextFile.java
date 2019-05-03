/**
 * 
 */
package com.ivoslabs.changesreport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.BiConsumer;

import com.ivoslabs.changesreport.dtos.Reader;
import com.ivoslabs.changesreport.dtos.Rsc;

/**
 * @author VI9XXL8
 *
 */
public class TextFile {

    public void readFile(Rsc resource, BiConsumer<Reader, String> byLine) {

	try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))) {

	    String sCurrentLine;

	    Reader reader = new Reader();
	    int c = 1;

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
