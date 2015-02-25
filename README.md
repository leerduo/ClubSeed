# ClubSeed

## 0.0.9 ##

### Bug ###
1. 从其他fragment返回到EventShowFragment会导致ESF空白**（已解决）**
2. 刷新之后那个小圆圈一直在转……另外界面会变得混乱
3. 上拉加载更多并不是拉到底部触发的，而是还没到底的时候就触发，这个倒是可以用但是给人感觉怪怪的=_=

### 预期改进 ###
1. 下拉加载更多到头了应该提示一下**（已解决）**
2. EventContentFragment 直接读取EventContentActivity的Intent，这个将来收藏界面不好移植，应该改成在ECA中读取ID信息，再发送给ECF
3. 界面改的再好看些……
4. 缓存功能加上

### Tip ###
获取json数据我全都用AppUtils.class里面的getJSONString方法获取，没用网络队列的方式……因为使用网络队列需要在onSuccess里加入回调函数，代码会变得好长而且不利于模块化=_=

下拉加载更多的操作我是这么处理的：设置一个标志*boolFirstLoad*（其实应该叫*isFirstLoad*，我才发现），如果为true就调用startListView方法，然后设置为false；如果是false直接appendData