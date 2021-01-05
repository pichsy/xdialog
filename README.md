# xdialog
最喜欢的自定义弹窗

### 引入控件
最新版本:  [![](https://jitpack.io/v/pichsy/xdialog.svg)](https://jitpack.io/#pichsy/xdialog)
    
        
       implementation 'com.github.pichsy:xdialog:1.0'
       
       


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
        
 