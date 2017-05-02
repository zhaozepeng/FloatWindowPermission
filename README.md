# FloatWindowPermission
this repository is aimed to adapt android float window permission in most of phone models and how to request it at runtime </br>

its result is as follows : </br>
![这里写图片描述](http://img.blog.csdn.net/20161121163115438)

if you want to see more,[click here](http://blog.csdn.net/self_study/article/details/52859790)

from now on，the models below android M that have been adapted are :
<ul><li>xiaomi:v5,v6,v7,v8</li><li>huawei:partial</li><li>meizu:partial</li><li>360:partial</li><li>others:phones like samsung,sony or other model can directly show the float window, so there is no need to adapt,but if you find one that can not,contact me via my email(zhao_zepeng@hotmail.com) or leave a message on my blog i mentioned above,thanks</li></ul>

more details about the models that have been adapted(thanks [ruanqin0706](https://github.com/ruanqin0706) for help):</br>
## **6.0/6.0+**
most models are OK with this way of adaption except meizu:

| 机型      | 版本   |详细信息| 适配完成 |具体表现|
| ------------- |:-------------:| :-----:| :-----:| :-----:|
| 魅族 PRO6 | 6.0|型号：PRO6；版本：6.0；分辨率：1920*1080|否  | 检测权限结果有误，微信可正常缩小放大，而我方检测为未开启权限，为跳转至开启权限页 |
| 魅族 U20  | 6.0 |型号：U20；版本：6.0；分辨率：1920*1080| 否| 检测权限结果有误，微信可正常缩小放大，而我方检测为未开启权限，为跳转至开启权限页 |

conclusion:

|汇总结果  |
| :-------------: |
| Android6.0 及以上机型覆盖：58款，其中：|
|三星：10款，均正常|
|华为：21款，均正常|
|小米：5款，均正常|
|魅族：2款，异常（1.检测权限未开启，点击 Android 6.0 及以上跳转，无法跳转，却可以选择魅族手机设置，设置后，悬浮窗打开缩小正常；2.在魅族上，及时设置悬浮窗关闭，微信也可正常缩小，但是我们检测的悬浮窗是否开发结果，和实际系统的设置是匹配的。）|
|其他：20款，均正常|

<font color='red'>until now(2016-11-21), this problem has been solved, we have made a special treatment to the ROM above 6.0 include 6.0 in meizu mobiles.</font></br>

## **huawei**
here is the test result for huawei mobiles:

| 机型      | 版本   | 适配完成 |具体表现|默认设置|
| ------------- |:-------------:| :-----:| :-----:| :-----:|
| 华为荣耀x2 | 5.0|否  | 跳转至通知中心页面，而非悬浮窗管理处 |默认关闭 |
| 华为畅玩4x（电信版）  | 4.4.4 |可以优化 | 跳转至通知中心标签页面，用户需切换标签页（通知中心、悬浮窗为两个不同标签页） |默认关闭 |
| 华为 p8 lite  | 4.4.4 |可以优化 | 跳转至通知中心标签页面，用户需切换标签页（通知中心、悬浮窗为两个不同标签页） |默认关闭 |
| 华为荣耀 6 移动版  | 4.4.2 |可以优化 | 跳转至通知中心标签页面，用户需切换标签页（通知中心、悬浮窗为两个不同标签页） |默认关闭 |
| 华为荣耀 3c 电信版  | 4.3 |是 | 跳转至通知中心，但默认是开启悬浮窗的 |默认关闭 |
|华为 G520  | 4.1.2 |否 |直接点击华为跳转设置页按钮，闪退 |默认开启 |

conclusion:

|汇总结果  |完全兼容机型数量 |次兼容机型数量 |总测试机型数|兼容成功率|
| ------------- | ------------- | ------------- | ------------- | ------------- |
|华为6.0以下机型覆盖：18款，其中：<br>5.0.1以上：11款，均默认开启，且跳转设置页面正确；5.0：1款，处理异常<br>（默认未开启悬浮窗权限，且点击跳转至通知栏，非悬浮窗设置入口）<br>4.4.4、4.4.2：3款，处理可接受<br>（默认未开启悬浮窗权限，点击跳转至通知中心的“通知栏”标签页，可手动切换至“悬浮窗”标签页设置）<br>4.3：1款，处理可接受<br>（默认开启，但点击华为跳转设置页，跳转至通知中心，无悬浮窗设置处）<br>4.2.2：1款，默认开启，处理正常<br>4.1.2：1款，处理有瑕疵<br>（默认开启，但若直接点击华为跳转按钮，出现闪退）|12 |5 |18 |94.44%|
waiting for adapted...</br>

## **xiaomi**
most of xiaomi mobiles are adapted very well except some paticular models:</br>

| 机型      | 版本   | 适配完成 |具体表现|
| :-------------: |:-------------:| :-----:| :-----:|
| 小米 MI 4S| 5.1.1|否  | 无悬浮窗权限，点击小米手机授权页跳转按钮，无反应|
| 小米 红米NOTE 1S | 4.4.4 |未执行|未修改开启悬浮窗成功，真机平台不支持（为权限与之前系统有别） |
|小米 红米1(联通版) | 4.2.2|未执行| 未安装成功 |
here is the conclusion:

|汇总结果  |完全兼容机型数量 |次兼容机型数量 |总测试机型数|兼容成功率|
| :-------------: | :-------------: | :-------------: | :-------------: | :-------------: |
|小米6.0以下机型覆盖：10款，其中：<br>5.1.1 小米 MI 4S：1款，兼容失败<br>（默认未开启，点击小米手机授权按钮，无跳转）<br>其他：9款，均成功|9|0|10|90%|

## **samsung**
almost 100% of the samsung mobiles are adapted very well and here is the conclusion of samsung:

|汇总结果  |完全兼容机型数量 |次兼容机型数量 |总测试机型数|兼容成功率|
| :-------------: | :-------------: | :-------------: | :-------------: | :-------------: |
|三星6.0以下机型覆盖：28款，全部检测处理成功<br>（默认均开启悬浮窗权限）|28|0|28|100%|

## **oppo&&vivo**##
only test a small part of the mobiles and here is the result:

| 机型      | 版本   | 适配完成 |是否默认开启|
| :-------------: |:-------------:| :-----:| :-----:|
| OPPO R7sm| 5.1.1|是| 默认开启|
| OPPO R7 Plus | 5.0 |是|默认开启|
|OPPO R7 Plus(全网通) |5.1.1|是|默认开启|
|OPPO A37m|5.1|未执行|默认未开启，且无法设置开启（平台真机限制修改权限导致）|
|OPPO A59m|5.1.1|是|默认开启|

conclusion:

|汇总结果  |
| ------------- | 
|抽查3款，2个系统版本，均兼容，100%|</br>

## **others**##
we random pick some other models like HTC and Sony and here is the result:

|机型|是否正常|
| :-------------: | :-------------: | 
|蓝魔 R3|是|
|HTC A9|是|
|摩托罗拉 Nexus 6|是|
|VIVO V3Max A|是|
|金立 M5|是|
|HTC One E8|是|
|努比亚 Z11 Max|是|
|Sony  Xperia Z3+ Dual|是|
|酷派 大神Note3|是|
|三星 GALAXY J3 Pro（双4G）|是|
|三星 Note 5|是|
|中兴 威武3|是|
|中兴 Axon Mini|是|

conclusion:

|汇总结果  |
| :-------------: | 
|随机抽查看13款，全部测试正常，100%|