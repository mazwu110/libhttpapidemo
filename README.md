# libhttpapidemo
基于retrofit2,rxjava2 封装的泛型网络数据解析框架，全部APP就一个MODEL网络请求，传class，参数map，还有URL进去就能自动解析出您想要的结果,网上好像还找不到这种全APP一个泛型解析的，只有那种一个请求对应一个实体class的，很不好用。此框架中也就一个ApiService,其中的get，post,单文件上传，多文件上传，文件下载等，几乎通用于所有的网络请求情况，当然不满足的可以自己修改，很方便，在项目中使用，只需要导入libhttpapi MODULE就行。
当然想要更全一点的MVVM框架中使用，可以参考我的https://github.com/mazwu110/libhttpapiMvvmDemo，里面也是封装了lighttpapi的使用，只是基于谷歌AAC 的 MVVM框架使用而已


特别说明：调用处必须要明确后台返回的data是JSON还是JSON数组；
1. 如果后台返回的data是非数组JSON，比如： data: {"userType":"1", list:[]},则参数class必须传MyClassName.class,然后在接收处用对象这样接收数据即可：MyClassName bean = (MyClassName) data;即可接收到后台返回的Data数据       
2. 如果后台返回的data是纯JSON数组,比如: data:[{"id":"1","name":"小李飞刀"}],则参数class必须传MyClassName[].class,然后在接收处用 List<MyClassName> list = (List<MyClassName>) data;即可接收到后台返回的JSON数组数据
  
3.使用案例
    
public class MainActivity extends AppCompatActivity implements OnHttpApiListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       // 1.1 非数组JSON使用样例：
        Map<String, Object> params = new HashMap<>();
        params.put("city", "北京");
        params.put("key", "0132423b3e085efed24b7b8f00d83a91");
        // 第三个参数，需要用到哪个类解析数据结果就传哪个类进去就行，这里采用了泛型解析
        QHttpApi.doGet(Constants.getWeather, params, MyClassName.class, HttpWhatConfig.CODE_10, MainActivity.this);
        
        //2.1 纯数组JSON使用样例：
        Map<String, Object> params = new HashMap<>();
        params.put("city", "北京");
        params.put("key", "0132423b3e085efed24b7b8f00d83a91");
        // 第三个参数，需要用到哪个类解析数据结果就传哪个类进去就行，这里采用了泛型解析
        QHttpApi.doGet(Constants.getWeather, params, MyClassName[].class, HttpWhatConfig.CODE_11, MainActivity.this);
    }
    
    @Override
    public void onSuccess(int what, Object data) {
        switch (what) {
            case HttpWhatConfig.CODE_10: {
                // 使用请求数据的时候的class反解析就行
                MyClassName bean = (MyClassName) data;
                // 泛型数据解析出来了,后台返回的data数据就在bean中，想怎么用，自己写逻辑
                break;
            }
            case HttpWhatConfig.CODE_11:
                // 使用请求数据的时候的class反解析就行
                List<MyClassName> list = (List<MyClassName>) data;
                // 泛型数据解析出来了,后台返回的data数据就在list中，想怎么用，自己写逻辑
                break;
        }
    }

    @Override
    public void onFailure(int what, String msg, int code) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    
    public class MyClassName {
        private String id; // 字段名和类型必须要和后台返回的一模一样，否则GSON解析对应不上
        private String name; // 字段名和类型必须要和后台返回的一模一样，否则GSON解析对应不上
        private String[] strArr; // 字段名和类型必须要和后台返回的一模一样，否则GSON解析对应不上
        private List<InnerData> workDayList;
        // get,set...
    }
  
    public class InnerData {
        private String id;
        private String name;
        private String workTime;
        // get, set ...
    }
}

