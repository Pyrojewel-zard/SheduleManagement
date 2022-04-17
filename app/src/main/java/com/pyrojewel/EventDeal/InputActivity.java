package com.pyrojewel.EventDeal;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;
import com.pyrojewel.ui.input.InputGallery;
import com.pyrojewel.ui.input.InputText;
import com.pyrojewel.ui.input.InputVoice;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

/**
 * @author Pyrojewel
 * 输入任务的界面
 */
public class InputActivity extends AppCompatActivity {
    private SeekBar sb_normal;
    private TextView txt_cur;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_input);
        SectionsPagerAdapter pagerAdapter=new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager=(ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        TabLayout tabLayout=(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new InputText();
                case 1:
                    return new InputGallery();
                case 2:
                    return new InputVoice();
            }
            return null;
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.Text);
                case 1:
                    return getResources().getText(R.string.Gallery);
                case 2:
                    return getResources().getText(R.string.Voice);
            }
            return null;
        }
    }
}