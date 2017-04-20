package com.qmr.news.model.provider;

import android.util.Log;

import com.google.gson.Gson;
import com.qmr.news.BuildConfig;
import com.qmr.news.model.entity.ChannelEntity;
import com.qmr.news.model.entity.ChannelEntityy;
import com.qmr.news.model.entity.NewsEntity;
import com.qmr.news.network.Channel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by qmr on 2017/3/6.
 *
 * @author qmr777
 */

public class NewsRemoteRepository {

    public static final String BASE_URL = "http://api.jisuapi.com/news/";
    public static final String FAKE_DATA = "\n" +
            "{\"status\":\"0\",\"msg\":\"ok\",\"result\":{\"channel\":\"头条\",\"num\":\"3\",\"li" +
            "st\":[{\"title\":\"中国计划到2020年新建、扩改建旅游厕所10万座\",\"time\":\"2017-03-08 16:37" +
            "\",\"src\":\"中国新闻网\",\"category\":\"travel\",\"pic\":\"http:\\/\\/api.jisuapi.com\\/n" +
            "ews\\/upload\\/20170308\\/230004_13878.jpg\",\"content\":\"<p class=\\\"art_t\\\">　　中新" +
            "" +
            "" +
            "社北京3月6日电 （记者 周音）中国国家旅游局6日公布了《“十三五”全国旅游公共服务规划》（以下简称《规划" +
            "》）。其中提出，要大力推进厕所革命，到2020年，全国新建、改扩建旅游厕所累计完成10万座。<\\/p><p clas" +
            "s=\\\"art_t\\\">　　《规划》指出，“十三五”旅游公共服务要以转型升级、提质增效为主题，扩大旅游公共服" +
            "务有效供给，优化旅游公共服务内容，全面提升旅游公共服务品质，提高旅游业可持续发展保障能力，为实现旅游强" +
            "国“三步走战略”奠定坚实基础。<\\/p><p class=\\\"art_t\\\">　　“大力推进厕所革命”是《规划》的一大看" +
            "点。《规划》提出，到2020年，全国新建、改扩建旅游厕所累计完成10万座；主要旅游景区、旅游度假区、旅游线" +
            "路、旅游步行街区、乡村旅游点等游客集中区域的旅游厕所全部达到A级标准；旅游厕所地图覆盖所有A级旅游厕" +
            "所；无水冲、泡沫节水、微生物降解等厕所技术得到广泛应用；4A级以上旅游景区80%以上的旅游厕所实现生态" +
            "化；所有5A级旅游景区全部具备第三卫生间（家庭卫生间）。<\\/p><p class=\\\"art_t\\\">　　《规划" +
            "》重点部署了未来旅游公共服务发展的16项重点工程。包括优化提升“12301”国家智慧旅游公共服务平台；构建" +
            "中国特色旅游服务中心体系，实现游客集中区域的旅游服务中心全覆盖；完善旅游景区主要交通连接线，构建旅" +
            "游“快进”交通网络，推进旅游“一公里”建设，实现旅游目的地内部交通无缝衔接，到2020年，基本实现高速公" +
            "路等一种及以上“快进”交通方式通达4A级景区，两种及以上通达5A级景区；推动跨区域旅游休闲绿道建设，构建覆" +
            "盖主要旅游城市和旅游区的绿道“慢游”系统，到2020年，建成完成20条跨区域自行车旅游休闲绿道，全国总里程达到" +
            "5000公里等。（完）<\\/p>\",\"url\":\"http:\\/\\/travel.sina.cn\\/domestic\\/news\\/201" +
            "7-03-08\\/detail-ifycaafm5522413.d.html?vt=4&pos=108\",\"weburl\":\"http:\\/\\/travel.sina.com.cn\\/domestic\\/news\\/2017-03-08\\/detail-ifycaafm5522413.shtml\"},{\"title\":\"全美机场将执行更严格安检\",\"time\":\"2017-03-08 18:39\",\"src\":\"环球网\",\"category\":\"travel\",\"pic\":\"http:\\/\\/api.jisuapi.com\\/news\\/upload\\/20170308\\/230004_53350.jpg\",\"content\":\"<p class=\\\"art_t\\\">　　中新社休斯敦3月7日电 美国交通安全管理局（TSA）宣布，将逐步在美国各机场采取更严格摸身检查的新安检措施。<\\/p><p class=\\\"art_t\\\">　　全美机场每天有超过200万人次接受TSA检查。直到上周，安检员还可以采用5种不同选项中的一种，对那些拒绝接受电子扫描的乘客进行搜身。但新的强化搜身检查将替换之前所有5种选项，成为标准化程序。<\\/p><p class=\\\"art_t\\\">　　TSA官方网站指南指出，TSA的工作人员可以用手背轻拍乘客身体敏感部位。在有限情况下，可能需要使用手掌对乘客涉及敏感的身体部位进行检查，以确保排除威胁。敏感部位包括头巾、乳房、臀部和腹股沟等区域。新措施已由较小型机场开始执行，逐步过渡到全美。<\\/p><p class=\\\"art_t\\\">　　按照规定，那些拒绝通过电子扫描机和金属探测器、或通过时引发了警报、亦或被嗅弹犬选出的乘客，都将同样接受严格的摸身检查。所有摸身检查将由与乘客同性别的工作人员完成，并由另一名同性别的安检员见证。<\\/p><p class=\\\"art_t\\\">　　TSA公共事务经理尼克尔·梅南德兹说，今年2月20日的那一周，美国机场检测出79支枪械创下历史新高，其中21支枪已经上膛。新的轻拍搜身检查可提高检测出枪支和其他危险品的成功率，TSA不时调整措施是为满足不断变化的威胁、实现高水平的运输安全，预计新的搜身检查不会增加机场整体安检时间。<\\/p><p class=\\\"art_t\\\">　　《华盛顿邮报》消息，共和党前议员罗恩·保罗对此发表评论说，“TSA发起的‘比以前更亲密’的轻拍检查释放出‘侵略性’。”部分民众通过社交媒体抨击这一新措施是“让抚摸合法化”。<\\/p><p class=\\\"art_t\\\">　　“对于新的轻拍摸身检查，我不会使用‘侵略性’这个词，”TSA发言人迈克尔·英格兰说，“这个新程序将更有效地发现违禁物品。这只是简单的程序改变，99.99%的人甚至不会注意到。”<\\/p><p class=\\\"art_t\\\">　　福克斯新闻指出，TSA这项新措施的出台，部分原因是该机构2015年内部调查显示：全美机场有数十次安检失误，卧底调查员携带模拟爆炸物和禁止的武器通过了安检，通过率达95%以上。此后，美国前国土安全部部长杰·约翰逊要求TSA实施一系列行动来解决报告中的问题。（完）<\\/p>\",\"url\":\"http:\\/\\/travel.sina.cn\\/outbound\\/news\\/2017-03-08\\/detail-ifychhuq3270718.d.html?vt=4&pos=108\",\"weburl\":\"http:\\/\\/travel.sina.com.cn\\/outbound\\/news\\/2017-03-08\\/detail-ifychhuq3270718.shtml\"},{\"title\":\"MH370失联3周年\",\"time\":\"2017-03-08 20:56\",\"src\":\"FATIII\",\"category\":\"travel\",\"pic\":\"\",\"content\":\"<p class=\\\"art_t\\\">2014年3月8日凌晨00:41，马航MH370航班如常由吉隆坡起飞前往北京，这一别眨眼已经整整三年。今年1月17日，中、马、澳三国政府共同宣布中止MH370搜索工作，由2014年3月8日起的第1046天，这场世界民航有史以来持续时间最长、搜索范围最广、耗费资金最大、参与人员最多的飞机搜索任务，终以阶段性的一无所获宣告暂停。不过中马澳三国此前曾明确表示，本次停止搜索任务只是「中止」而非「终止」，将来一旦出现可信的信息或线索，就将立刻重启搜索任务。<br\\/><\\/p><p class=\\\"art_t\\\">我们一直跟踪MH370事件的所有进展，如今整整三年，有必要以时间线方式重温一下，才能不让时间冲淡记忆，直至找到MH370的一天。<\\/p><p class=\\\"art_t\\\"><img src=\\\"http:\\/\\/api.jisuapi.com\\/news\\/upload\\/20170308\\/230003_29859.jpg\\\"\\/><\\/p><p class=\\\"art_t\\\"><br\\/><\\/p><p class=\\\"art_t\\\"><img src=\\\"http:\\/\\/api.jisuapi.com\\/news\\/upload\\/20170308\\/230003_56286.jpg\\\"\\/><\\/p><p class=\\\"art_t\\\"><img src=\\\"http:\\/\\/api.jisuapi.com\\/news\\/upload\\/20170308\\/230003_85984.jpg\\\" alt=\\\"ATSB于15年12月1日公布的水下搜索进度\\\"\\/><\\/p><p class=\\\"art_t\\\"><img src=\\\"http:\\/\\/api.jisuapi.com\\/news\\/upload\\/20170308\\/230003_55675.jpg\\\" alt=\\\"2014年3月18日至5月28日的南印度洋搜索范围 By Andrew Heneen\\\"\\/><\\/p><p class=\\\"art_t\\\"><img src=\\\"http:\\/\\/api.jisuapi.com\\/news\\/upload\\/20170308\\/230003_92726.jpg\\\"\\/><\\/p><p class=\\\"art_t\\\"><img src=\\\"http:\\/\\/api.jisuapi.com\\/news\\/upload\\/20170308\\/230003_90339.jpg\\\" alt=\\\"澳洲ATSB专家检验在坦桑尼亚发现的疑似MH370襟翼残骸\\\"\\/><\\/p><p class=\\\"art_t\\\">这一系列的残骸全部都在非洲大陆东岸被发现，也恰好印证了此前关于MH370坠海地点的推测。参照印度洋的洋流运动，这些残骸发现的地点和时间均与推算的残骸移动轨迹模型相符。<\\/p><p class=\\\"art_t\\\"><img src=\\\"http:\\/\\/api.jisuapi.com\\/news\\/upload\\/20170308\\/230004_25195.jpg\\\" alt=\\\"西澳大学Pattiaratchi教授根据洋流推算的MH370残骸漂流模型\\\"\\/><\\/p><p class=\\\"art_t\\\">然而，除了这仅有的几块残骸，我们再也没有更多关于MH370的线索。<\\/p><p class=\\\"art_t\\\">2014.03.08-2017.03.08，1096天的期盼，却未曾找到任何答案。<\\/p><p class=\\\"art_t\\\">中马澳三方坚持1046天的搜索，不断调整方案，投入大量资金，一切的付出虽然没有获得回报，却承载着所有家属的希望，也承载着了人们对MH370和航空安全的关心。然而，在南印度洋1038天的一无所获最终让中马澳三方政府选择暂时放下。<\\/p><p class=\\\"art_t\\\"><b>希望在未来的某一刻，我们能够找到这架跟全世界玩了很久「捉迷藏」的飞机，以及机上239个曾经闪耀的生命。<\\/b><br\\/><\\/p><p class=\\\"art_t\\\"><b><br\\/><\\/b><\\/p><p class=\\\"art_t\\\"><img src=\\\"http:\\/\\/api.jisuapi.com\\/news\\/upload\\/20170308\\/230004_38000.jpg\\\" alt=\\\"版权所有：Sandro Koster\\\"\\/><\\/p>\",\"url\":\"http:\\/\\/travel.sina.cn\\/flights\\/news\\/2017-03-08\\/detail-ifychhus0072827.d.html?vt=4&pos=108\",\"weburl\":\"http:\\/\\/travel.sina.com.cn\\/flights\\/news\\/2017-03-08\\/detail-ifychhus0072827.shtml\"}]}}";
    private static final String TAG = "NewsRemoteRepository";
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Log.i(TAG, "intercept: " + request.method());
                    Response resp = chain.proceed(request);
                    Log.i(TAG, "intercept: " + resp.peekBody(1024*1024).string());
                    return resp;
                    //chain.proceed()
                }
            }).build();
    static NewsRemoteRepository instance;

    private NewsRemoteRepository() {
    }

    public static NewsRemoteRepository getInstance() {
        if (instance == null)
            instance = new NewsRemoteRepository();
        return instance;
    }

    public void getChannel(Observer<List<ChannelEntity>> observer){
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(Channel.class).getChannel(BuildConfig.API_KEY)
                .map(new Function<ChannelEntityy, List<ChannelEntity>>() {
                    @Override
                    public List<ChannelEntity> apply(@NonNull ChannelEntityy channelEntityy) throws Exception {
                        List<String> strings = channelEntityy.getResult();
                        List<ChannelEntity> channelEntities = new ArrayList<>(strings.size());
                        for(int i = 0;i<strings.size();i++){
                            ChannelEntity c = new ChannelEntity(strings.get(i),i);
                            channelEntities.add(c);
                        }
                        return channelEntities;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getNews(String channel, int start, int num,
                        Observer<List<NewsEntity.ResultBean.ListBean>> ob) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        retrofit.create(NewsService.class).getNewsList(channel,start,num,BuildConfig.API_KEY)
        .map(new Function<NewsEntity, List<NewsEntity.ResultBean.ListBean>>() {
            @Override
            public List<NewsEntity.ResultBean.ListBean> apply(@NonNull NewsEntity newsEntity) throws Exception {
                return newsEntity.getResult().getList();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ob);
//
//        retrofit.create(NewsService.class)
//                .getNewsList(channel, start, num, BuildConfig.API_KEY)
//                .map(new Function<NewsEntity, List<NewsEntity.ResultBean.ListBean>>() {
//                    @Override
//                    public List<NewsEntity.ResultBean.ListBean> apply(@NonNull NewsEntity newsEntity) throws Exception {
//                        Log.i(TAG, "apply: " + newsEntity.getMsg() + newsEntity.getResult().getChannel());
//                        return newsEntity.getResult().getList();
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(ob);
    }

    interface NewsService {

        @GET("get")
        Observable<NewsEntity> getNewsList(
                @Query("channel") String channel, @Query("start") int start, @Query("num") int num,
                @Query("appkey") String appkey);
    }


}
