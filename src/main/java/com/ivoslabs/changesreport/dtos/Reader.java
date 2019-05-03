/**
 * 
 */
package com.ivoslabs.changesreport.dtos;

/**
 * @author VI9XXL8
 *
 */
public class Reader {

    private int line;

    private boolean open;

    private boolean waitCloseTag;

    private boolean waitDesc;

    private String prevLine;

    private Change change;

    /**
     * Gets the line
     *
     * @return {@code int} The line
     */
    public int getLine() {
	return this.line;
    }

    /**
     * Setted at TextFile.readFile<br>
     * Sets the line
     *
     * @param line {@code int} The line to set
     */
    public void setLine(int line) {
	this.line = line;
    }

    /**
     * Gets the open
     *
     * @return {@code boolean} The open
     */
    public boolean isOpen() {
	return this.open;
    }

    /**
     * Sets the open
     *
     * @param open {@code boolean} The open to set
     */
    public void setOpen(boolean open) {
	this.open = open;
    }

    /**
     * Gets the waitCloseTag
     *
     * @return {@code boolean} The waitCloseTag
     */
    public boolean isWaitCloseTag() {
	return this.waitCloseTag;
    }

    /**
     * Sets the waitCloseTag
     *
     * @param waitCloseTag {@code boolean} The waitCloseTag to set
     */
    public void setWaitCloseTag(boolean waitCloseTag) {
	this.waitCloseTag = waitCloseTag;
    }

    /**
     * Gets the waitDesc
     *
     * @return {@code boolean} The waitDesc
     */
    public boolean isWaitDesc() {
	return this.waitDesc;
    }

    /**
     * Sets the waitDesc
     *
     * @param waitDesc {@code boolean} The waitDesc to set
     */
    public void setWaitDesc(boolean waitDesc) {
	this.waitDesc = waitDesc;
    }

    /**
     * Gets the prevLine
     *
     * @return {@code String} The prevLine
     */
    public String getPrevLine() {
	return this.prevLine;
    }

    /**
     * Setted at TextFile.readFile<br>
     * Sets the prevLine
     *
     * @param prevLine {@code String} The prevLine to set
     */
    public void setPrevLine(String prevLine) {
	this.prevLine = prevLine;
    }

    /**
     * Gets the change
     *
     * @return {@code Change} The change
     */
    public Change getChange() {
	return this.change;
    }

    /**
     * Sets the change
     *
     * @param change {@code Change} The change to set
     */
    public void setChange(Change change) {
	this.change = change;
    }

}
