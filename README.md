# RecyclerView万能适配器

##功能
- 支持加载更多
- 支持不同item的添加
- 支持添加头部
- 支持线性与瀑布流显示的切换

##Download
使用Gradle:

```gradle
repositories {
  maven{ url : "https://dl.bintray.com/hanshaofengs/maven" }
}

dependencies {
    compile 'com.hanshao:universallibrary:1.0.0'
}
```
##使用
###1.创建UniversalAdapter
```
  mUniversalAdapter = new UniversalAdapter();
```
###2.继承UniversalViewHolder类
```
public class DemoViewHolder extends UniversalViewHolder<数据类型> {

    public DemoViewHolder(View v) {
        super(v);
    }

    @Override
    protected void initView(View view) {
        //对绑定的view进行初始化操作
    }

    @Override
    public void refreshUi(ItemInfo data) {
        //item显示时绑定的数据,进行刷新Item的UI
    }
}
```
###3.继承UniversalProvider类
```
  //ViewHolder提供者 数据类型与提供的ViewHolder一致
  public class DemoProvider extends UniversalProvider<数据类型> {

    public DemoProvider(Context context, int resId) {
        super(context, resId);
    }

    @Override
    public UniversalViewHolder<数据类型> realNewInstance(View v) {
         //提供ViewHolder
         return new DemoViewHolder(v);
    }
}
```
###4.将UniversalProvider注册到UniversalAdapter中
####一种类型Item的使用
```
   mUniversalAdapter.registerHolder(key,数据集合,new DemoProvider(context,布局资源id));
   mRecyclerView.setAdapter(mUniversalAdapter);
```
####多种类型Item的使用
```
     mUniversalAdapter.registerHolder(key,数据集合,new DemoProvider(context,布局资源id));
     mUniversalAdapter.registerHolder(key,数据集合,new SecondProvider(context,布局资源id));
```
####一种类型Item的线性与瀑布流切换
 ```
     mUniversalAdapter.switchMode(true);
     mUniversalAdapter.registerHolder("1",infos,new LineProvider(context,线性资源id),new WaterfalProvider(context,瀑布流资源Id));
 ```
#####线性与瀑布流的切换

######线性切换
```
    mUniversalAdapter.switchLayoutManager(new LinearLayoutManager(context));
```
######瀑布流切换
```
    mUniversalAdapter.switchLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));
```
###5.监听机制
####加载更多的监听
```
    mUniversalAdapter.setOnLoadMoreListener(监听器);
```
###6.加载更多失败控制
```
    mUniversalAdapter.setLoadMoreFailed();
```
####注意:调用此方法,RecyclerView底部会显示"重新加载更多"的UI,当点击"重新加载更多"的时候会调用加载更多的监听器的方法
```
```
