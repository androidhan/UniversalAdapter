# RecyclerView万能适配器

## 功能
- 支持加载更多
- 支持多种item的添加
- 支持添加头部及给予数据动态刷新头部UI
- 支持单类型item线性与瀑布流显示的切换
- 支持加载状态的失败时,可重新触发加载更多
- 已支持多类型item的线性与瀑布流显示切换
- 新增:已支持多类型item的交叉内存复用(可做类似淘宝item的效果)


## 1.3.2版本新增功能
- 支持每注册一种item添加各自的OnItemClickListener(条目点击事件)
- 支持header添加点击的监听
- 在UniversalViewHolder提供了保护成员mActivity，更加方便在UniversalViewHolder可以在item处理自己的业务逻辑
- 对UniversalAdapter构造以及UniversalProvider结构以及UniversalAdapter有了小变化，使得UniversalAdapter使用起来更加方便
- 与1.3.1版本不兼容

## 效果
![][img]


### 交叉item复用效果(新增)

![][img_uv]

### 优化对比

#### 优化前
 
 ![][img_b]
 
#### 优化后
 
 ![][img_a]
 


## Download

使用Gradle:

```gradle
dependencies {
    compile 'com.hanshao:universallibrary:1.3.2'
}
```
## 使用
### 1.创建UniversalAdapter
```
    mUniversalAdapter = new UniversalAdapter(activity);
```
### 2.继承UniversalViewHolder类
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
### 3.继承UniversalProvider类
```
  public class DemoProvider extends UniversalProvider {

    public DemoProvider(int resId) {
        super(resId);
    }

    @Override
    public UniversalViewHolder realNewInstance(View v) {
         //提供ViewHolder
         return new DemoViewHolder(v);
    }
}
```
### 4.将UniversalProvider注册到UniversalAdapter中
#### 一种类型Item的使用
```
    //未添加item点击监听
    mUniversalAdapter.registerHolder(key,数据集合,new DemoProvider(布局资源id));
    //添加item点击监听
    mUniversalAdapter.registerHolder(key,数据集合,new DemoProvider(布局资源id),new OnItemClickListener<E>(){
        void onItemClick(int recyclerPosition,int dataPosition,E itemData){
            //recyclerPosition:item在RecyclerView的位置
            //dataPosition:itemData在数据集合(List)中所在的索引
            //itemData:item所需的对应的数据
        }
    });
    mRecyclerView.setAdapter(mUniversalAdapter);
```
#### 多种类型Item的使用
```
    //未添加item监听，需监听item点击事件如上
    mUniversalAdapter.registerHolder(key,数据集合,new DemoProvider(布局资源id));
     //未添加item监听，需监听item点击事件如上
    mUniversalAdapter.registerHolder(key,数据集合,new SecondProvider(布局资源id));
```
##### 注意事项:多类型Item的使用时,不同Item注册Holder需要的参数key一定要不相同
#### 一种类型Item的线性与瀑布流切换
 ```
    mUniversalAdapter.registerHolder(key,infos,new LineProvider(context,线性资源id),new WaterfalProvider(context,瀑布流资源Id));
 ```
##### 线性与瀑布流的切换

###### 线性切换
```
    mUniversalAdapter.switchLayoutManager(new LinearLayoutManager(context));
```
###### 瀑布流切换
```
    mUniversalAdapter.switchLayoutManager(new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL));
```
### 5.监听机制
#### 加载更多的监听
```
    mUniversalAdapter.setOnLoadMoreListener(监听器);
```
### 6.加载更多时对RecyclerView刷新UI
#### 完成加载更多,并获取了新的数据
```
     //注册Item时所对应的key  
    mUniversalAdapter.addDatas(key,数据集合);
    //进行刷新
    mUniversalAdapter.notifyMoreFinish(true);
```
#### 完成加载更多,没有新的数据
```
    mUniversalAdapter.notifyMoreFinish(false);
```
#### 加载更多失败
```
    mUniversalAdapter.setLoadMoreFailed();
```
#### 注意:调用此方法,RecyclerView底部会显示"重新加载更多"的UI,当点击"重新加载更多"的时候会调用加载更多的监听器的方法

### 7.交叉Item复用注意

```
    mUniversalAdapter.registerHolder(key,datas,new ViewHolderProvider(context,资源布局Id));
```
当你需要加载更多数据刷新UI，若想复用之前相同item，必须这样调用

```
   mUniversalAdapter.registerHolder(key,newDatas,new ViewHolderProvider(context,资源布局Id));
   //刷新UI
   mUniversalAdapter.notifyMoreFinish(true);
```
确保key相同，否则复用不了先前注册的item。

### 其他
#### 具体的核心实现参考博客链接:<http://www.xiaohanshao.cn/2017/03/04/recyclerview%E4%B8%87%E8%83%BD%E9%80%82%E9%85%8D%E5%99%A8/>
#### 后续版本
##### 继续改造UniversalAdapter让其使用更方便
#### 有BUG或者有其他意见改进的地方以及可以考虑增加某些功能支持可以联系我
#### 联系方式QQ:1844225993  
#### 邮箱:1844225993@qq.com

--------------
[img]: https://raw.githubusercontent.com/androidhan/UniversalAdapter/master/images/b.gif
[img_a]:https://raw.githubusercontent.com/androidhan/UniversalAdapter/master/images/after.png
[img_b]:https://raw.githubusercontent.com/androidhan/UniversalAdapter/master/images/befor.png
[img_uv]:https://raw.githubusercontent.com/androidhan/UniversalAdapter/master/images/uv.gif
