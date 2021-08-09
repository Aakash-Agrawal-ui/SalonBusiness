package com.salononline.salonbusiness.Parse;

public class FileData {
    private String fileName;
    private String fileLocation;
    private String fileType;
    private int size;
    private String fileGroup;

    public FileData(String fieldName, String fileLocation, String fileType, int size, String fileGroup) {
        this.fileName = fieldName;
        this.fileLocation = fileLocation;
        this.fileType = fileType;
        this.size = size;
        this.fileGroup = fileGroup;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFileGroup() {
        return fileGroup;
    }

    public void setFileGroup(String fileGroup) {
        this.fileGroup = fileGroup;
    }
}
