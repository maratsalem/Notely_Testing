package notely.app;

import java.io.File;

public class FilePath {
    String file;
    public FilePath(){
        file = "";
    }
    public FilePath(String file){
        this.file = file;
    }
    public void setFileName(String file){
        this.file = file;
    }
    public String getFileName(){
        return file;
    }

}