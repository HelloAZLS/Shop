package ysg.gdcp.cn.shop.Utils;

/**
 * Created by Administrator on 2017/6/6 17:21.
 *
 * @author ysg
 */

public  class Contants {
    public static final String  COMPAINGAIN_ID="compaigin_id";
    public static final String  WARES_ID="wares_id";
    public static final String USER_JSON="user_json";
    public static final String TOKEN="token";

    public  static final String DES_KEY="ysg_ydy";

    public  static final int REQUEST_CODE=0;



    public static class API{
        public  static final  String BASE_URL="http://112.124.22.238:8081/course_api/";
        //主页轮播条url
        public static  final  String BANNER_URL =BASE_URL+"banner/query?type=1";

        public static final String CAMPAIGN_HOME=BASE_URL+"campaign/recommend";
        //热门商品url
        public static final String WARES_HOT = BASE_URL+"wares/hot";

        public static final String BANNER =BASE_URL+"banner/query";
        public static final String WARES_LIST=BASE_URL+"wares/list";
        public static final String WARES_CAMPAIN_LIST=BASE_URL +"wares/campaign/list";
        public static final String  CATEGORY_LIST=BASE_URL+"category/list";
        public static final String WARES_DETAIL=BASE_URL +"wares/detail.html";
        public static final String LOGIN=BASE_URL +"auth/login";


    }

}
