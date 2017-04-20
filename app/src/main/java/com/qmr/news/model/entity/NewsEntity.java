package com.qmr.news.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by qmr on 2017/2/27.
 *
 * @author qmr777
 */

public class NewsEntity {

    /**
     * status : 0
     * msg : ok
     * result : {"channel":"头条","num":"1","list":[{"title":"于大宝:希望新赛季能打进亚冠","time":"2017-02-27 17:08","src":"新浪体育","category":"sports","pic":"http://api.jisuapi.com/news/upload/20170227/210002_42903.jpg","content":"<p class=\"art_t\">　　（稿件来源：北京中赫国安俱乐部官方微博）<\/p><p class=\"art_t\">　　北京中赫国安队今日的媒体公开训练课结束后，主教练何塞和球员于大宝接受了媒体的采访，就大家关心的一系列问题一一进行解答。<\/p>","url":"http://sports.sina.cn/china/2017-02-27/detail-ifyavvsk3732448.d.html?vt=4&pos=108","weburl":"http://sports.sina.com.cn/china/j/2017-02-27/doc-ifyavvsk3732448.shtml"}]}
     */

    private String status;
    private String msg;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * channel : 头条
         * num : 1
         * list : [{"title":"于大宝:希望新赛季能打进亚冠","time":"2017-02-27 17:08","src":"新浪体育","category":"sports","pic":"http://api.jisuapi.com/news/upload/20170227/210002_42903.jpg","content":"<p class=\"art_t\">　　（稿件来源：北京中赫国安俱乐部官方微博）<\/p><p class=\"art_t\">　　北京中赫国安队今日的媒体公开训练课结束后，主教练何塞和球员于大宝接受了媒体的采访，就大家关心的一系列问题一一进行解答。<\/p>","url":"http://sports.sina.cn/china/2017-02-27/detail-ifyavvsk3732448.d.html?vt=4&pos=108","weburl":"http://sports.sina.com.cn/china/j/2017-02-27/doc-ifyavvsk3732448.shtml"}]
         */

        private String channel;
        private String num;
        private List<ListBean> list;

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Parcelable{
            /**
             * title : 于大宝:希望新赛季能打进亚冠
             * time : 2017-02-27 17:08
             * src : 新浪体育
             * category : sports
             * pic : http://api.jisuapi.com/news/upload/20170227/210002_42903.jpg
             * content : <p class="art_t">　　（稿件来源：北京中赫国安俱乐部官方微博）</p><p class="art_t">　　北京中赫国安队今日的媒体公开训练课结束后，主教练何塞和球员于大宝接受了媒体的采访，就大家关心的一系列问题一一进行解答。</p>
             * url : http://sports.sina.cn/china/2017-02-27/detail-ifyavvsk3732448.d.html?vt=4&pos=108
             * weburl : http://sports.sina.com.cn/china/j/2017-02-27/doc-ifyavvsk3732448.shtml
             */

            private String title;
            private String time;
            private String src;
            private String category;
            private String pic;
            private String content;
            private String url;
            private String weburl;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getWeburl() {
                return weburl;
            }

            public void setWeburl(String weburl) {
                this.weburl = weburl;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.title);
                dest.writeString(this.time);
                dest.writeString(this.src);
                dest.writeString(this.category);
                dest.writeString(this.pic);
                dest.writeString(this.content);
                dest.writeString(this.url);
                dest.writeString(this.weburl);
            }

            public ListBean() {
            }

            protected ListBean(Parcel in) {
                this.title = in.readString();
                this.time = in.readString();
                this.src = in.readString();
                this.category = in.readString();
                this.pic = in.readString();
                this.content = in.readString();
                this.url = in.readString();
                this.weburl = in.readString();
            }

            public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
                @Override
                public ListBean createFromParcel(Parcel source) {
                    return new ListBean(source);
                }

                @Override
                public ListBean[] newArray(int size) {
                    return new ListBean[size];
                }
            };
        }
    }
}
