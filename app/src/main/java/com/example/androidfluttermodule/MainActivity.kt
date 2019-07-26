package com.example.androidfluttermodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.flutter.facade.Flutter
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StringCodec
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       //创建 flutter view
        val flutterView  = Flutter.createView(this@MainActivity,lifecycle,"main")
        //将flutter 的 view 添加到 linearlayout 中
        container.addView(flutterView)
        //创建通信通道
        val basicMessageChannel = BasicMessageChannel<String>(flutterView,"test_channel",StringCodec.INSTANCE)
        //设置回调
        basicMessageChannel.setMessageHandler(object : BasicMessageChannel.MessageHandler<String>{
            override fun onMessage(p0: String?, p1: BasicMessageChannel.Reply<String>) {
                //收到flutter发过来的消息显示在edittext中
                et.setText(p0)
            }
        })
        bt.setOnClickListener {
            //将edittext的输入传递到flutter,如果未输入则给个默认值
            basicMessageChannel.send(if (et.text.toString().isNotEmpty()){ et.text.toString() }else "100")
        }
    }
}
