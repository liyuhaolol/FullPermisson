# Android 6.0动态运行时权限----自我理解与封装

我自己理解并对动态运行时权限的封装，原理简单，使用更简单。所以大家爱用用，不用我自己用，233

## 引用方法

作为一个`BaseActivity`用来被你的`Activity`继承的。`Fragment`同理的。

## 主要的类介绍

- `hasPermission` : 判断是否有此权限
- `doAfterPermission` : 权限申请成功后，可以执行的方法
- `rejectAfterPermission` : 权限申请失败后，可以执行的方法

## 使用方式

- 使用`hasPermission`判断是否有权限，没有会自动申请
- 重写`doAfterPermission`方法，完成方法调用
- 重写`rejectAfterPermission`方法，完成方法调用

## 框架的完成思路

- 原生代码实现，未使用任何框架

    `API LEVEL >= 15 && API LEVEL < 21`:因为我就没在往下试

## 注意事项

- 使用事项，权限是按照权限组来授权的，所以申请权限时，尽量不要同时申请同一权限组的权限，比如
WRITE_EXTERNAL_STORAGE和READ_EXTERNAL_STORAGE，只要申请其中一个权限，整个group.STORAGE都会被赋予权限
- 不同权限需求种类，不要在同一个权限组里发起申请，因为code你一次只能传1种，4种需求种类对应4种应用场景，所以
不要尝试使用一套逻辑来同时兼容4种模式，应该是不现实的。
- 不要去判断hasPermission()为false的情况，因为会自动申请权限，false的返回是没有意义的

## 联系方式

- Github: https://github.com/liyuhaolol
- 博客: http://blog.csdn.net/ccffvii
- 邮箱: liyuhaoid@sina.com

有任何意见和问题，欢迎在issues中提出，一定尽快回复。
