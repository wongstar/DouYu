package com.team.zhuoke.presenter.common.impl;


import android.util.Log;

import com.google.gson.Gson;
import com.team.zhuoke.model.logic.common.bean.OldLiveVideoInfo;
import com.team.zhuoke.model.logic.common.bean.TempLiveVideoInfo;
import com.team.zhuoke.presenter.common.interfaces.CommonPcLiveVideoContract;
import com.team.zhuoke.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 作者：gaoyin
 * 电话：18810474975
 * 邮箱：18810474975@163.com
 * 版本号：1.0
 * 类描述：
 * 备注消息：
 * 修改时间：2017/2/24 下午3:27
 **/
public class CommonPcLiveVideoPresenterImp extends CommonPcLiveVideoContract.Presenter {
    @Override
    public void getPresenterPcLiveVideoInfo(String room_id) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        client.newCall(mModel.getModelPcLiveVideoInfo(mContext, room_id)).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e("error",e.getMessage()+"---");
                L.e("错误信息:"+e.getMessage());
                mView.showErrorWithStatus(e.getMessage());
            }
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String json =response.body().string().toString();
                Log.e("onResponse",json);
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.getInt("error")==0) {
                        Gson gson = new Gson();
                        TempLiveVideoInfo mLiveVideoInfo = gson.fromJson(json, TempLiveVideoInfo.class);
                        mView.getViewPcLiveVideoInfo(mLiveVideoInfo);
                    } else {
                        mView.showErrorWithStatus("获取数据失败!");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//        mModel.getModelPhoneLiveVideoInfo(mContext, room_id);
    }
}
