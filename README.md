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

    public FristNormalViewHolder(View v) {
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
