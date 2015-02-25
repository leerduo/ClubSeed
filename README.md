# ClubSeed

## 0.0.9 ##

### Bug ###
1. 从其他fragment返回到EventShowFragment会导致ESF空白**（已解决）**
2. 现在的刷新功能就是安慰剂……不能重新加载

### 预期改进 ###
1. 下拉加载更多到头了应该提示一下
2. EventContentFragment 直接读取EventContentActivity的Intent，这个将来收藏界面不好移植，应该改成在ECA中读取ID信息，再发送给ECF
3. 界面改的再好看些……
4. 缓存功能加上
