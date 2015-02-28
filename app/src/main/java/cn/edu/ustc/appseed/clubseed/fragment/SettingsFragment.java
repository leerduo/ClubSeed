package cn.edu.ustc.appseed.clubseed.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import cn.edu.ustc.appseed.clubseed.R;
import cn.edu.ustc.appseed.clubseed.activity.AuthorActivity;
import cn.edu.ustc.appseed.clubseed.utils.AppUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {
    private Preference prefClearCache;
    private Preference prefFeedback;
    private Preference prefShare;
    private Preference prefAuthor;
//    private Preference prefReadingMode;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        prefClearCache = findPreference(getString(R.string.pref_clear_cache));
        prefAuthor = findPreference(getString(R.string.pref_author));
        prefFeedback = findPreference(getString(R.string.pref_feedback));
        prefShare = findPreference(getString(R.string.pref_share));
//        prefReadingMode = findPreference(getString(R.string.pref_reading_mode));
        prefClearCache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (AppUtils.clearCache())
                    Toast.makeText(AppUtils.sAppContext, "清除成功", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        prefAuthor.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(getActivity(), AuthorActivity.class);
                startActivity(i);
                return false;
            }
        });
        prefFeedback.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822") ;
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"ustcappseed@163.com"});
                i.putExtra(Intent.EXTRA_SUBJECT,"反馈意见");
                i.putExtra(Intent.EXTRA_TEXT,"\n\n以下信息为检测错误所需，请不要删除或修改，我们不会索取您的个人信息，谢谢合作！\n"+AppUtils.phoneInfo);
                startActivity(Intent.createChooser(i, "选择邮件客户端"));
                return false;
            }
        });
        prefShare.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"分享");
                intent.putExtra(Intent.EXTRA_TEXT, "我正在使用一款超赞的社团活动发布应用ClubSeed，快来使用吧~下载地址：xxx");
                startActivity(Intent.createChooser(intent, "分享"));
                return false;
            }
        });
//        prefReadingMode.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                if (AppUtils.sSharedPreferences.getBoolean(getString(R.string.pref_reading_mode), true)) {
//                    getActivity().findViewById(R.id.fragmentContainer).setBackgroundColor(getResources().getColor(R.color.reading_mode));
//                }else{
//                    getActivity().findViewById(R.id.fragmentContainer).setBackgroundColor(getResources().getColor(R.color.normal_mode));
//                }
//                return false;
//            }
//        });
    }
}
