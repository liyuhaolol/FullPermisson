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
                my = 1;
                if (hasPermission(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    doAfterPermission();
                }
                break;
            case R.id.btn2:
                my = 2;
                if (hasPermission(Manifest.permission.CAMERA)){
                    doAfterPermission();
                }
                break;
        }
    }


    @Override
    public void doAfterPermission() {
        switch (my){
            case 1:
                Toast.makeText(this,"方法1",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this,"方法2",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
