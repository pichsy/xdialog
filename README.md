# xdialog
最喜欢的自定义弹窗

### 引入控件
最新版本:  [![](https://jitpack.io/v/pichsy/xdialog.svg)](https://jitpack.io/#pichsy/xdialog)
    
        
       implementation 'com.github.pichsy:xdialog:1.0'
       
       

## 升级
1.3 Popactions 用法采用建造者，跟之前不一样了，建议使用新版，功能更多。
## 用法
   
      new CustomDialog.Builder(mActivity)
          .setTitle("大家好")
          .setMessage("我是小学生，我的技术非常好，带飞躺赢")
          .setCancelButtonText("滚一边去")
          .setConfirmButtonText("上车")
          .setOnCancelClickListener(new CustomDialog.OnClickListener() {
              @Override
              public void onClick(BaseDialogFragment dialog, View view) {
                  dialog.dismiss();
                  new PopToast(mActivity)
                          .setIconResId(R.mipmap.ic_launcher_round)
                          .setMessage("大哥，给点面子，给个start好吗?")
                          .setTitle("呜呜")
                          .show(btn);
              }
          })
          .setOnConfirmClickListener(new CustomDialog.OnClickListener() {
              @Override
              public void onClick(BaseDialogFragment dialog, View view) {
                  dialog.dismiss();
                  new PopToast(mActivity)
                          .setIconResId(R.mipmap.ic_launcher_round)
                          .setMessage("大哥牛逼，赏个start吧！")
                          .setTitle("欧耶")
                          .show(btn);
              }
          })
          .build().show();
          
          
          
          PopActions菜单弹窗框架，封装了一个弹起的壳，坐标计算都计算好了，以anchor的上下边的中心为箭头展示起点。
          里面的内容可自定义。只需关注内容布局，无需考虑弹起坐标。箭头也可以设置大小或者隐藏显示，高度、宽度为0即隐藏。
          eg:
          private void popMenu(View v) {
                  TextView tv = new TextView(this);
                  tv.setText("jdslfjldsajflasdjfldjsa");
                  tv.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                   new PopActions.Builder(this)
                          .setContentWidth(XDisplayHelper.dp2px(this, 200))
                          .setContentHeight(XDisplayHelper.dp2px(this, 80))
                          .setDimAmountEnable(true)
                          .setBackgroundColor(Color.YELLOW)
                          .setRadius(XDisplayHelper.dp2px(this, 20))
                          .setDimAmountEnable(true)
                          .setOnPopupWindowDismissListener(new OnPopupWindowDismissListener() {
                              @Override
                              public void onPopDismiss() {
          
                              }
                          })
                          .setContentView(tv)
                          .setAnchor(v)
                          .setAnimationStyle(R.style.XP_Animation_PopDownMenu_Right)
                          .build()
                          .show();
          }
        
 