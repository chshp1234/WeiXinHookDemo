package com.example.administrator.weixinhookdemo;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author Administrator
 * @date 2018/4/17
 */
public class TwoActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);


        imageView = findViewById(R.id.videoThumb);

        String url="http://weiyou-file.weiyoucrm.com/video/1523187247158.mp4";
        String path = Environment.getExternalStorageDirectory() + File.separator+"video";
        File filePath = new File(path);
        if (!filePath.exists()){
            filePath.mkdir();
        }
        String name = url.substring(url.lastIndexOf('/') + 1);
        final File fileName = new File(filePath, name);
        try {

            //把服务器上图片下载到本地F盘的abc.jpg图片
            org.apache.commons.io.FileUtils.copyURLToFile( new URL( url ) , fileName );

        } catch (IOException e) {
            e.printStackTrace();
        }

        String vfile =
                Environment.getExternalStorageDirectory() + File.separator + "1523187247158.mp4";

        MediaMetadataRetriever mmr = new MediaMetadataRetriever();

        if (!StringUtils.isTrimEmpty(vfile) && new File(vfile).exists()) {
            mmr.setDataSource(vfile);
            // api level 10, 即从GB2.3.3开始有此功能
            String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            LogUtils.d("标题:" + title);
            // 专辑名
            String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            LogUtils.d("专辑名:" + album);
            // 媒体格式
            String mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            LogUtils.d("媒体格式:" + mime);
            // 艺术家
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            LogUtils.d("艺术家:" + artist);
            // 播放时长单位为毫秒
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            LogUtils.d("播放时长单位为毫秒:" + duration);
            // 从api level 14才有，即从ICS4.0才有此功能
            String bitrate = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
            LogUtils.d("比特率:" + bitrate);
            // 路径
            String date = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);
            LogUtils.d("路径:" + date);
            // 获取视频缩略图
            Bitmap b =
                    ThumbnailUtils.createVideoThumbnail(
                            vfile, MediaStore.Video.Thumbnails.MINI_KIND);

//            String thumbPath = ImageUtils.saveImageToGallery(b);
//            LogUtils.d("缩略图路径:" + thumbPath);
//            if (!StringUtils.isTrimEmpty(thumbPath)) {
//                ToastUtils.showShort("图片已保存至:" + thumbPath);
//            }

            imageView.setImageBitmap(b);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(1);
        super.onBackPressed();
    }
}
