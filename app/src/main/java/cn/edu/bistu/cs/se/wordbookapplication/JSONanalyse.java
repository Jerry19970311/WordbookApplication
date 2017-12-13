package cn.edu.bistu.cs.se.wordbookapplication;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XZL on 2017/11/21.
 */

public class JSONanalyse implements Serializable {

    /**
     * web : [{"value":["好","善","商品"],"key":"Good"},{"value":["公共物品","公益事业","公共财"],"key":"public good"},{"value":["干的出色","干得好","好工作"],"key":"Good Job"}]
     * query : good
     * translation : ["很好"]
     * errorCode : 0
     * dict : {"url":"yddict://m.youdao.com/dict?le=eng&q=good"}
     * webdict : {"url":"http://m.youdao.com/dict?le=eng&q=good"}
     * basic : {"us-phonetic":"ɡʊd","phonetic":"gʊd","uk-phonetic":"gʊd","explains":["n. 好处；善行；慷慨的行为","adj. 好的；优良的；愉快的；虔诚的","adv. 好","n. (Good)人名；(英)古德；(瑞典)戈德"]}
     * l : EN2zh-CHS
     */
    private java.util.List<WebEntity> web;
    private String query;
    private java.util.List<String> translation;
    private String errorCode;
    private DictEntity dict;
    private WebdictEntity webdict;
    private BasicEntity basic;
    private String l;

    @Override
    public String toString() {
        if(basic!=null) {
            String tran = "";
            int index = translation.size();
            for (int i = 0; i < index; i++) {
                tran = tran + translation.get(i) + "\t";
            }
            return query + "\n" + tran + "\n" + basic.toString() + "\n" + web.toString();
        }else{
            return "找不到该单词！";
        }
    }

    public List<WebEntity> getWeb() {
        return web;
    }

    public String getQuery() {
        return query;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public DictEntity getDict() {
        return dict;
    }

    public WebdictEntity getWebdict() {
        return webdict;
    }

    public BasicEntity getBasic() {
        return basic;
    }

    public String getL() {
        l=l.replace('_','-');
        return l;
    }

    public class WebEntity {
        /**
         * value : ["好","善","商品"]
         * key : Good
         */
        private java.util.List<String> value;
        private String key;

        public void setValue(List<String> value) {
            this.value = value;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public String getKey() {
            return key;
        }

        @Override
        public String toString() {
            String s="单词:"+key+"\n意思:";
            int index=value.size();
            for(int i=0;i<index;i++){
                s=s+value.get(i)+"\t";
            }
            return "单词:"+key+"\n"+value;
        }
    }

    public class DictEntity {
        /**
         * url : yddict://m.youdao.com/dict?le=eng&q=good
         */
        private String url;

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    public class WebdictEntity {
        /**
         * url : http://m.youdao.com/dict?le=eng&q=good
         */
        private String url;

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

    public class BasicEntity{
        private String us_phonetic;
        private String phonetic;
        private String uk_phonetic;
        private List<String> explains;

        public String getPhonetic() {
            return phonetic;
        }

        public String getUs_phonetic() {
            return us_phonetic;
        }

        public String getUk_phonetic() {
            return uk_phonetic;
        }

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public void setUk_phonetic(String uk_phonetic) {
            this.uk_phonetic = uk_phonetic;
        }

        public void setUs_phonetic(String us_phonetic) {
            this.us_phonetic = us_phonetic;
        }

        @Override
        public String toString() {
            String s = "\n英式英标:/" + uk_phonetic + "/\n美式音标:/" + us_phonetic + "/\n";
            int index = explains.size();
            for (int i = 0; i < index; i++) {
                s = s + explains.get(i) + "\n";
            }
            return s;
        }
    }


}
