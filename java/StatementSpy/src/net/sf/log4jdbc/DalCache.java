package net.sf.log4jdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: shanhong.cheng
 * Date: 11/15/13
 * Time: 5:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DalCache {
    private static final DalCache instance = new DalCache();

    public static DalCache getInstance() {
        return instance;
    }


    public static DalCache getInstance(String path) {
        instance.setPath(path);
        return instance;
    }

    private File saveDir;

    public void setPath(String path) {
        File dir = new File(path);
        if (dir.isDirectory()) saveDir = dir;
        else {
            try {
                dir.mkdirs();
                saveDir = dir;
            } catch (Exception e) {
                throw new RuntimeException("cannot create directory for storing dal results");
            }
        }
    }

    public void appendResult(String file, String data)  {
        File f = new File(saveDir, file);

        try{
            //if file doesnt exists, then create it
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter writer = new FileWriter(f, true);

            BufferedWriter bufferWritter = new BufferedWriter(writer);
            bufferWritter.write(data);
            bufferWritter.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}