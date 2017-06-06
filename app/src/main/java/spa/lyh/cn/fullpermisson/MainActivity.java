package spa.lyh.cn.fullpermisson;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends PermissionActivity {
    int my;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn1:
                //必要，执行方法
                my = 1;
                if (hasPermission(REQUIRED_LOAD_METHOD,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    doAfterPermission();
                }
                break;
            case R.id.btn2:
                //必要，不执行方法
                hasPermission(REQUIRED_ONLY_REQUEST,Manifest.permission.ACCESS_FINE_LOCATION);
                break;
            case R.id.btn3:
                //不必要，执行方法
                my = 2;
                if (hasPermission(NOT_REQUIRED_LOAD_METHOD,Manifest.permission.RECORD_AUDIO)){
                    doAfterPermission();
            }
                break;
            case R.id.btn4:
                //不必要，不执行方法
                my = 2;
                hasPermission(NOT_REQUIRED_ONLY_REQUEST,Manifest.permission.READ_CALENDAR);
                break;
        }
    }


    @Override
    public void doAfterPermission() {
        switch (my){
            case 1:
                Toast.makeText(this,"必要，执行方法",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this,"不必要，执行方法",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 这个方法可写可不写，完全看具体需求
     */
    @Override
    public void rejectAfterPermission() {
        switch (my){
            case 1:
                break;
            case 2:
                Toast.makeText(this,"拒绝了相应的权限",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
