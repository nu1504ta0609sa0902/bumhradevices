package com.mhra.mdcm.devices.appian.utils.selenium.others;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author TPD_Auto
 */
public class FileUtils {

    public final static String userFileName = "data" + File.separator + "users.properties";

    private final static String resourceFolder = "src" + File.separator + "test" + File.separator + "resources" + File.separator;
    private final static String userFileLocation = "configs" + File.separator ;
    private final static Map<String, Properties> mapOfProperties = new HashMap<String, Properties>();

    /**
     * Load properties files from system
     *
     * @param fileName
     * @return
     */
    public static Properties loadPropertiesFile(String fileName) {

        Properties prop = mapOfProperties.get(fileName);

        if (prop == null) {
            try {
                String root = new File("").getAbsolutePath();
                String location = root + File.separator + resourceFolder + userFileLocation + fileName;
                prop = new Properties();
                InputStream in = new FileInputStream(new File(location));
                prop.load(in);
                in.close();

                //update map
                mapOfProperties.put(fileName, prop);
            } catch (Exception e) {
                prop = null;
                e.printStackTrace();
            }
        }

        return prop;
    }

    /**
     * If we need to override the default user name for running the tests
     *
     * By default its the Automation usernames
     * @param overrideUsername
     * @param uname
     * @return username key from the properties file
     *  one of : business+uname, manufacturer+uname, authorisedrep+uname
     */
    public static String getOverriddenUsername(String overrideUsername, String uname) {
        if(overrideUsername!=null && !overrideUsername.equals("")){
            if(uname.contains("business")){
                uname = "business" + overrideUsername;
            }else if(uname.contains("manufacturer")){
                uname = "manufacturer" + overrideUsername;
            }else if(uname.contains("authorised")){
                uname = "authorisedRep" + overrideUsername;
            }
        }
        return uname;
    }

    public static String getSpecificPropertyFromFile(String fileName, String property) {
        Properties prop = loadPropertiesFile(fileName);
        String o = prop.getProperty(property);
        return o;
    }

    public static String getFileFullPath(String tmpFolderName, String fileName) {
        File file = new File("");
        String rootFolder = file.getAbsolutePath();
        String data = (rootFolder + File.separator + resourceFolder + tmpFolderName + File.separator + fileName);
        return data;
    }
}
