package com.qmr.news.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by qmr on 2017/3/12.
 *
 * @author qmr777
 */

@Entity
public class ChannelEntity {

    @Property(nameInDb = "name")
    public String name;
    @Property(nameInDb = "order")
    public int order;
    @Generated(hash = 712822823)
    public ChannelEntity(String name, int order) {
        this.name = name;
        this.order = order;
    }
    @Generated(hash = 781881457)
    public ChannelEntity() {
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getOrder() {
        return this.order;
    }
    public void setOrder(int order) {
        this.order = order;
    }
}
