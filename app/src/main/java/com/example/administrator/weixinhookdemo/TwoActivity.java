package com.example.administrator.weixinhookdemo;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.media.audiofx.AudioEffect;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        imageView = findViewById(R.id.videoThumb);

      /*  String url="http://weiyou-file.weiyoucrm.com/video/1523187247158.mp4";
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
        }*/

        final String vfile =
                Environment.getExternalStorageDirectory()
                        + File.separator
                        + "sight_12811353156136874160.mp4";

        if (!StringUtils.isTrimEmpty(vfile) && new File(vfile).exists()) {
            //            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            //            mmr.setDataSource(vfile);
            //            // api level 10, 即从GB2.3.3开始有此功能
            //            String title =
            // mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            //            LogUtils.d("标题:" + title);
            //            // 专辑名
            //            String album =
            // mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            //            LogUtils.d("专辑名:" + album);
            //            // 媒体格式
            //            String mime =
            // mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            //            LogUtils.d("媒体格式:" + mime);
            //            // 艺术家
            //            String artist =
            // mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            //            LogUtils.d("艺术家:" + artist);
            //            // 播放时长单位为毫秒
            //            String duration =
            // mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            //            LogUtils.d("播放时长单位为毫秒:" + duration);
            //            // 从api level 14才有，即从ICS4.0才有此功能
            //            String bitrate =
            // mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
            //            LogUtils.d("比特率:" + bitrate);
            //            // 路径
            //            String date =
            // mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE);
            //            LogUtils.d("路径:" + date);
            //            LogUtils.d(
            //                    "videoTrackMime:"
            //                            + mmr.extractMetadata(
            //
            // MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER));
            //
            //            // 获取视频缩略图
            //            Bitmap b =
            //                    ThumbnailUtils.createVideoThumbnail(
            //                            vfile, MediaStore.Video.Thumbnails.MINI_KIND);
            //
            //            //            String thumbPath = ImageUtils.saveImageToGallery(b);
            //            //            LogUtils.d("缩略图路径:" + thumbPath);
            //            //            if (!StringUtils.isTrimEmpty(thumbPath)) {
            //            //            }
            //
            //            imageView.setImageBitmap(b);

            AsyncTask.execute(
                    new Runnable() {
                        @Override
                        public void run() {
                            MediaExtractor extractor;
                            //            MediaCodec decoder = null;
                            extractor = new MediaExtractor();
                            try {
                                //                extractor.setDataSource(
                                //
                                // "http://weiyou-file.weiyoucrm.com/wyMaterial/2018/05/18/9/wx-vedio.mp4");
                                //
                                // extractor.setDataSource("http://weiyou-file.weiyoucrm.com/video/1526619352127.mp4");
                                extractor.setDataSource(vfile);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            int trackCount = selectTrack(extractor);
                            LogUtils.d("trackCount:" + trackCount);
                        }
                    });

            //            if (trackIndex < 0) {
            //                throw new RuntimeException("No video track found in " + vfile);
            //            }
            /*extractor.selectTrack(trackIndex);
            MediaFormat mediaFormat = extractor.getTrackFormat(trackIndex);
            String mme = mediaFormat.getString(MediaFormat.KEY_MIME);
            try {
                decoder = MediaCodec.createDecoderByType(mme);
            } catch (IOException e) {
                e.printStackTrace();
            }
            showSupportedColorFormat(decoder.getCodecInfo().getCapabilitiesForType(mime));*/
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private static int selectTrack(MediaExtractor extractor) {
        int numTracks = extractor.getTrackCount();
        for (int i = 0; i < numTracks; i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            LogUtils.d("Extractor selected track " + i + " (" + mime + "): " + format);
        }
        return numTracks;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showSupportedColorFormat(MediaCodecInfo.CodecCapabilities caps) {
        System.out.print("supported color format: ");
        for (int c : caps.colorFormats) {
            System.out.print(c + "\t");
        }
        System.out.println();
    }

    @Override
    public void onBackPressed() {
        setResult(1);
        super.onBackPressed();
    }
}
