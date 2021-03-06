package com.xloong.bluetoothsocketdemo;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xloong.library.bluesocket.BlueSocketBaseThread;
import com.xloong.library.bluesocket.BluetoothSppHelper;
import com.xloong.library.bluesocket.message.IMessage;
import com.xloong.library.bluesocket.message.ImageMessage;
import com.xloong.library.bluesocket.message.StringMessage;

import java.io.File;

/**
 * @author bingbing
 * @date 16/4/7
 */
public class ServiceActivity extends BaseActivity implements BluetoothSppHelper.BlueSocketListener {

    private EditText mEdit;
    private BluetoothSppHelper mHelper;
    private TextView mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mEdit = (EditText) findViewById(R.id.edit);
        mStatus = (TextView) findViewById(R.id.text);
        mHelper = new BluetoothSppHelper();
        mHelper.setBlueSocketListener(this);
        mHelper.strat();
    }


    @Override
    public void onBlueSocketStatusChange(BlueSocketBaseThread.BlueSocketStatus status, BluetoothDevice remoutDevice) {
        mStatus.setText(status.toString());
    }

    @Override
    public void onBlueSocketMessageReceiver(IMessage message) {
        if (message instanceof StringMessage){
            Toast.makeText(this, ((StringMessage)message).getContent(), Toast.LENGTH_SHORT).show();
        }else if (message instanceof ImageMessage){
            Toast.makeText(this, ((ImageMessage)message).getContent().getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHelper.stop();
    }

    public void sendText(View view) {
        StringMessage message = new StringMessage();
        message.setContent("我是Service内容","扩展信息");
        mHelper.write(message);

    }

    public void sendImage(View view) {
        chooseImage();
    }

    @Override
    public void onChooseImage(String path) {
        File file = new File(path);
        ImageMessage message = new ImageMessage();
        message.setContent(file,file.getName());
        mHelper.write(message);
    }
}
