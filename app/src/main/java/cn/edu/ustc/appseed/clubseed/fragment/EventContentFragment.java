package cn.edu.ustc.appseed.clubseed.fragment;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.jsondata.ViewActivityPhp;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;

public class EventContentFragment extends Fragment {
    TextView mTextView;
    ImageView mImageView;
    int ID;

    public EventContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ID = getActivity().getIntent().getIntExtra(NoticeFragment.EXTRA_ACTIVITY_ID, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_content, container, false);
        mTextView = (TextView) v.findViewById(R.id.textViewEventContent);
        mImageView = (ImageView) v.findViewById(R.id.imgContent);
        if (ID == 0) {
            Toast.makeText(getActivity(), "未知的网络错误！", Toast.LENGTH_SHORT).show();
        } else {
            new ContentAsyncTask().execute(ID);
        }
        return v;
    }

    private class ContentAsyncTask extends AsyncTask<Integer, Void, Content> {
        @Override
        protected Content doInBackground(Integer... params) {
            String url = "http://clubseed.sinaapp.com/api/viewactivity.php?format=json2&ID=" + params[0];
            ViewActivityPhp mViewActivityPhp = null;
            Content content = new Content();
            try {
                String jsonString = AppUtils.getJSONString(url);
                mViewActivityPhp =
                        JSON.parseObject(
                                jsonString,
                                ViewActivityPhp.class);
                content.setText( mViewActivityPhp.getData().getContent());
            } catch (Exception e) {
                e.printStackTrace();
                content.setText(null);
            }
            try{
                Bitmap bitmap = AppUtils.getBitmapFromURL( mViewActivityPhp.getData().getPhotoURL());
                content.setBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
                content.setBitmap(null);
            }
            return content;
        }

        @Override
        protected void onPostExecute(Content content) {
            String text = content.getText();
            Bitmap bitmap = content.getBitmap();
            if (text == null) {
                Toast.makeText(getActivity(), "未知的网络错误", Toast.LENGTH_SHORT).show();
            } else
                mTextView.setText(text);
            if(bitmap == null){
                ;
            }else
                mImageView.setImageBitmap(bitmap);
        }
    }

    private class Content{
        Bitmap bitmap;
        String text;

        private Content(){};

        private Content(Bitmap bitmap, String text) {
            this.bitmap = bitmap;
            this.text = text;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
