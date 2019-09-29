package com.owl.mvc;

import com.owl.comment.OwlInit;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * time 2018/05/07.
 */
@Component
public class OwlCommentInit implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        OwlInit.print();
    }
}
