# libhttpapidemo
基于retrofit2,rxjava2 封装的泛型网络数据解析框架，全部APP就一个MODEL网络请求，传class，参数map，还有URL进去就能自动解析出您想要的结果,网上好像还找不到这种全APP一个泛型解析的，只有那种一个请求对应一个实体class的，很不好用。此框架中也就一个ApiService,其中的get，post几乎通用于所有的网络请求情况，当然不满足的可以自己修改，很方便，在项目中使用，只需要导入libhttpapi MODULE就行。
当然想要更全一点的MVVM框架中使用，可以参考我的https://github.com/mazwu110/libhttpapiMvvmDemo，里面也是封装了lighttpapi的使用，只是基于谷歌AAC 的 MVVM框架使用而已


特别说明：调用处必须要明确后台返回的data是JSON还是JSON数组；
1. 如果后台返回的data是非数组JSON，比如： data: {"userType":"1", list:[]},则参数class必须传MyClassName.class,然后在接收处用对象这样接收数据即可：MyClassName bean = (MyClassName) data;即可接收到后台返回的Data数据
2. 如果后台返回的data是纯JSON数组,比如: data:[{"id":"1","name":"小李飞刀"}],则参数class必须传MyClassName[].class,然后在接收处用 List<MyClassName> list = (List<MyClassName>) data;即可接收到后台返回的JSON数组数据
3.数据接收处 demo中有样例，可以参考。使用过程中有问题QQ或邮箱交流,QQ：315145320
