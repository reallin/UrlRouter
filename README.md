# UrlRouter
通过Url来进行activity的跳转,此方案应用很广。也可用于Hybrid混合编程中。
## 功能描述
点击按钮时，通过url来判断并进行页面的跳转。首先界面如下：

![](https://github.com/reallin/UrlRouter/blob/master/urlRouter1.png)

点击后跳转到第二个Activity：

![](https://github.com/reallin/UrlRouter/blob/master/urlRouter3.png)

也可以带上参数：

![](https://github.com/reallin/UrlRouter/blob/master/urlRouter2.png)

## 实现思路
### 读取注解
利用来编译时读取注解，在编译时生成一个Map,它存储的就是Url与Activity的对应信息。下面就是生成的类。
```java
public class SchemeDispatcher$$SchemeDatabase implements SchemeDataBase {
  @Override
  public Map<String, Class> getSchemeActivityClasses() {
    Map<String, Class> classes = new HashMap<String, Class>();
    classes.put("linxj://jumpActivity", com.example.linxj.urlrouter.JumpScheme.class);
    return classes;
  }
}
```
每一个Activity配置一个Scheme，用来跳转到这个Activity，这样就完全解藕了。
```java
@UrlScheme("linxj://jumpActivity")
public class JumpScheme implements IScheme{


    @Override
    public void doWithScheme(Scheme scheme) throws Exception {
        Intent i = new Intent(scheme.getContext(),UrlJumpActivity.class);
        if(scheme.hasParameter("name")) {
            i.putExtra("name", scheme.getParameter("name"));
        }
        scheme.getContext().startActivity(i);
    }
}
```
### 跳转示例
```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void jump(View view){
        new Scheme.Builder(MainActivity.this, Uri.parse("linxj://jumpActivity")).dispatch();
    }

    public void jumpParam(View view){
        new Scheme.Builder(MainActivity.this, Uri.parse("linxj://jumpActivity?name=lxj")).dispatch();
    }

}
```
上面代码jump是没有参数跳转，而jumpParam是带参数跳转。通过去生成的Map里找到对应启动类的Scheme来启动相应的类。
