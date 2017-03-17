package com.example.hp.pocket.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hp.pocket.R;
import com.example.hp.pocket.model.SettingsPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.sort)
    RadioGroup mSort;
    @BindView(R.id.sort_asc)
    RadioButton mSortAsc;
    @BindView(R.id.sort_desc)
    RadioButton mSortDesc;
    @BindView(R.id.show_phones)
    CheckBox mShowPhones;
    @BindView(R.id.show_links)
    CheckBox mShowLinks;

    private SettingsPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        mPrefs = new SettingsPreferences(this);

        mShowPhones.setChecked(mPrefs.isShowPhones());
        mShowLinks.setChecked(mPrefs.isShowLinks());
        selectSort(mPrefs.isSort());
    }

    private void selectSort(boolean descending) {
        mSort.check(descending ? R.id.sort_desc : R.id.sort_asc);
    }

    @OnClick(R.id.save)
    void onSaveClick() {
        if (!mShowLinks.isChecked() && !mShowPhones.isChecked()) {
            Toast.makeText(this, "Zaznacz co najmniej jeden typ !", Toast.LENGTH_SHORT).show();
            return;
        }

        mPrefs.setShowLinks(mShowLinks.isChecked());
        mPrefs.setShowPhones(mShowPhones.isChecked());
        mPrefs.setSort(mSort.getCheckedRadioButtonId() == R.id.sort_desc);

        finish();
    }
}
