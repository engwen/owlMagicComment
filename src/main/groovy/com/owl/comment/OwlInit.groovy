package com.owl.comment

import com.owl.comment.annotations.OwlLogInfo
import groovy.transform.CompileStatic
import org.apache.tools.ant.taskdefs.Java

import java.util.jar.Attributes
import java.util.jar.Manifest

/**
 *
 * author engwen
 * email xiachanzou@outlook.com
 * 2019/9/20.
 */
@CompileStatic
class OwlInit {
    static print() {

        URLClassLoader cl = (URLClassLoader) OwlLogInfo.class.getClassLoader();
        try {
            URL url = cl.findResource("META-INF/MANIFEST.MF");
            Manifest manifest = new Manifest(url.openStream());
            Attributes mainAttributes = manifest.getMainAttributes();
            String implVersion = mainAttributes.getValue("Implementation-Version");

            System.out.println(implVersion);
        } catch (IOException E) {
            // handle
        }
        print("""
  ____         ____  ___          _    
 / __ \\_    __/ /  |/  ___ ____ _(_____
/ /_/ | |/|/ / / /|_/ / _ `/ _ `/ / __/
\\____/|__,__/_/_/  /_/\\_,_/\\_, /_/\\__/ 
                          /___/              
       (v1.2.1)
""")
    }
}
