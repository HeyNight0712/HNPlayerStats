package heyblock0712.hnplayerstats.core;

import java.io.File;


public class Folder {
    public static File ensureFolderExists(File dataFolder) {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        return dataFolder;
    }
}
