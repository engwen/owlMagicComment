package com.owl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/05/07.
 */
@Component
class OwlCommentInit implements InitializingBean {
    @Override
    void afterPropertiesSet() throws Exception {
        System.out.print("""
  ____         ____  ___          _    
 / __ \\_    __/ /  |/  ___ ____ _(_____
/ /_/ | |/|/ / / /|_/ / _ `/ _ `/ / __/
\\____/|__,__/_/_/  /_/\\_,_/\\_, /_/\\__/ 
                          /___/""");
    }
}
