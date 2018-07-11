package com.dongann.app.common;

import java.util.List;

/**
 * @FileName: AppPaginationBean
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/6/7 下午2:26
 * @Version: v1.0
 * @description:
 */
public class AppPaginationBean {

    private boolean hasMore;

    private List<?> processedList;

    public AppPaginationBean(List<?> list, Integer length) {
        if (null == list || list.isEmpty()) {
            this.hasMore = false;
            this.processedList = list;
        } else {
            this.hasMore = (list.size()) >= length + 1;
            if (this.hasMore) {
                this.processedList = list.subList(0, length);
            } else {
                this.processedList = list;
            }
        }
    }


    public AppPaginationBean(List<?> list, Integer offset, Integer length) {
        if (null == list || list.isEmpty()) {
            this.hasMore = false;
            this.processedList = list;
        } else {
            this.hasMore = (list.size()) >= offset + length + 1;
            if (this.hasMore) {
                this.processedList = list.subList(offset, offset+length);
            } else if((list.size()) >= offset + 1){
                this.processedList = list.subList(offset, list.size());
            }
        }
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public List<?> getProcessedList() {
        return processedList;
    }
}