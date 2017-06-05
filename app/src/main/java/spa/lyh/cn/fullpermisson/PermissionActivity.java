package spa.lyh.cn.fullpermisson;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by liyuhao on 2017/6/5.
 */

public class PermissionActivity extends AppCompatActivity{
    private static final int PERMISSION_REQUEST = 1;

    private static final String PACKAGE_URL_SCHEME = "package:";


    private AlertDialog.Builder builder;



    /**
     * 判断是否拥有权限
     * 有权限返回true，没有权限返回false并自动申请权限
     * @param permissions 不定长数组
     * @return 是否有权限，一般调用不判断返回否的操作
     */
    public boolean hasPermission(String...permissions){
        for (String permission:permissions){
            if (ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                requestPermission(PERMISSION_REQUEST, permissions);
                return false;
            }
        }
        return true;
    }

    /**
     * 请求权限
     * @param code 请求码
     * @param permissions 权限列表
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionFlag = true;//权限是否全部通过
        boolean dialogFlag = false;//是否显示设置dialog
        ArrayList<String> per = new ArrayList<>();//保存被拒绝的权限列表
        initMissingPermissionDialog();
        switch (requestCode) {
            case PERMISSION_REQUEST:
                for (int i = 0 ;i < grantResults.length; i++){
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //存在被拒绝的权限
                        //per.add(permissions[i]);
                        permissionFlag = false;
                    }
                }
                break;
        }
        //List<String> names = selectGroup(per);//判断被拒绝的权限组名称

        if (permissionFlag) {
            doAfterPermission();//权限通过，执行对应方法
        }else{
            /*if (per.size() > 0){//严谨判断大于0
                for (String permission:per){
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this,permission)){
                        //当前权限被设置了"不在询问",永远不会弹出进入这里，将dialog显示标志设为true
                        dialogFlag = true;
                    }
                }
                if (dialogFlag){
                    //显示缺少权限，并解释为何需要这个权限
                    showMissingPermissionDialog(names);
                }
            }*/
        }
    }

    /**
     * 给子类提供重写的接口
     */
    public void doAfterPermission(){}


    private void initMissingPermissionDialog(){
        builder = new AlertDialog.Builder(this);
        builder.setTitle("帮助");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消", null);

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                //跳转到，设置的对应界面
                startAppSettings();
            }
        });

    }

    /**
     * 显示解释设置dialog
     * @param names 权限组名
     */
    private void showMissingPermissionDialog(List<String> names) {
        String content = "";
        //将权限组名字转换为字符串
        if (names.size() > 0){
            for (String name:names){
                content = content+name+"\n";
            }
        }
        builder.setMessage("当前应用缺少必要权限:\n"+content+"请点击\"设置\"-\"权限\"-打开所需权限。");
        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                builder.show();
            }
        },5000);
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }

    /**
     * 将权限，对应到权限组名，并去重
     * @param permissions 权限名
     * @return 权限组名
     */
    private List<String> selectGroup(List<String> permissions){
        List<String> group = new ArrayList<>();
        //匹配权限表
        for (String permission:permissions){
            if (permission.equals(Manifest.permission.READ_CALENDAR) || permission.equals(Manifest.permission.READ_CALENDAR)){
                group.add("日历");
            }else if (permission.equals(Manifest.permission.CAMERA)){
                group.add("相机");
            }else if (permission.equals(Manifest.permission.READ_CONTACTS) || permission.equals(Manifest.permission.WRITE_CONTACTS) || permission.equals(Manifest.permission.GET_ACCOUNTS)){
                group.add("联系人");
            }else if (permission.equals(Manifest.permission.ACCESS_FINE_LOCATION) || permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION)){
                group.add("定位");
            }else if (permission.equals(Manifest.permission.RECORD_AUDIO)){
                group.add("录音");
            }else if (permission.equals(Manifest.permission.READ_PHONE_STATE) || permission.equals(Manifest.permission.CALL_PHONE) || permission.equals(Manifest.permission.READ_CALL_LOG) || permission.equals(Manifest.permission.WRITE_CALL_LOG) || permission.equals(Manifest.permission.ADD_VOICEMAIL) || permission.equals(Manifest.permission.USE_SIP) || permission.equals(Manifest.permission.PROCESS_OUTGOING_CALLS)){
                group.add("手机状态");
            }else if (permission.equals(Manifest.permission.BODY_SENSORS)){
                group.add("传感器");
            }else if (permission.equals(Manifest.permission.SEND_SMS) || permission.equals(Manifest.permission.RECEIVE_SMS) || permission.equals(Manifest.permission.READ_SMS) || permission.equals(Manifest.permission.RECEIVE_WAP_PUSH) || permission.equals(Manifest.permission.RECEIVE_MMS)){
                group.add("短信");
            }else if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE) || permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                group.add("存储读写");
            }
        }
        //去重
        group = new ArrayList<>(new HashSet<>(group));
        return group;
    }
}