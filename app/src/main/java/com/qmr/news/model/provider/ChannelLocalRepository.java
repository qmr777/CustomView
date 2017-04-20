package com.qmr.news.model.provider;

import com.qmr.news.model.db.ChannelEntityDao;
import com.qmr.news.model.db.DBManager;
import com.qmr.news.model.entity.ChannelEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by qmr on 2017/3/13.
 *
 * @author qmr777
 */

public class ChannelLocalRepository {

    private static class Holder{
        static ChannelLocalRepository channelLocalRepository = new ChannelLocalRepository();
    }

    private ChannelLocalRepository() {    }

    public static ChannelLocalRepository getInstance(){
        return Holder.channelLocalRepository;
    }

    private static ChannelEntityDao channelEntityDao =
            DBManager.getInstance().getDaoSession().getChannelEntityDao();

    private static final Comparator<ChannelEntity> comparator = new Comparator<ChannelEntity>() {
        @Override
        public int compare(ChannelEntity o1, ChannelEntity o2) {
            return o1.order - o2.order;
        }
    };

    public void insertAll(List<ChannelEntity> channelEntities){
        channelEntityDao.insertInTx(channelEntities);
    }

    public void deleteAll(){
        channelEntityDao.deleteAll();
    }

    public List<ChannelEntity> getAll(){
        List<ChannelEntity> channelEntities = channelEntityDao.loadAll();
        Collections.sort(channelEntities, comparator);
        return channelEntities;
    }

    public long insert(ChannelEntity channelEntity){
        return channelEntityDao.insertOrReplace(channelEntity);
    }

}
