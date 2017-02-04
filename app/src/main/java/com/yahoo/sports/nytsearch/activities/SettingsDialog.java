package com.yahoo.sports.nytsearch.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.yahoo.sports.nytsearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import models.Settings;

/**
 * Created by nikhilba on 2/3/17.
 */

public class SettingsDialog extends DialogFragment {
    @BindView(R.id.etBeginDate)
    EditText etBeginDate;

    @BindView(R.id.spSortOrder)
    Spinner spSortOrder;

    @BindView(R.id.cbArts)
    CheckBox cbArts;

    @BindView(R.id.cbFashion)
    CheckBox cbFashion;

    @BindView(R.id.cbSports)
    CheckBox cbSports;

    public interface SettingsDialogListener {
        void OnFinishedSettingsEdit(Bundle args);
    }

    private Context mContext;

    @Override
    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }


    public static SettingsDialog newInstance(Context context) {
        Bundle args = new Bundle();
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.setContext(context);
        settingsDialog.setArguments(args);
        return settingsDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().setTitle("Edit Settings");
        return inflater.inflate(R.layout.dialog_settings, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        // fetch intent data
        Bundle args = getArguments();
        Settings settings = (Settings) args.getSerializable("settings");

        etBeginDate.setText(settings.mBeginDate);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                                        R.array.sortorderarray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSortOrder.setAdapter(adapter);
        spSortOrder.setSelection(adapter.getPosition(settings.mSortOrder.toString()));

        cbArts.setChecked(settings.mIsArts);
        cbFashion.setChecked(settings.mIsFashion);
        cbSports.setChecked(settings.mIsSports);

        Button saveButton = (Button) view.findViewById(R.id.btnSave);
        saveButton.setOnClickListener(v -> onSave(v));
    }

    public void onSave(View view) {
        Settings settings = new Settings();
        settings.mBeginDate = etBeginDate.getText().toString();
        settings.mSortOrder = Settings.SortOrder.valueOf(spSortOrder.getSelectedItem().toString());
        settings.mIsArts = cbArts.isChecked();
        settings.mIsFashion = cbFashion.isChecked();
        settings.mIsSports = cbSports.isChecked();

        Bundle args = new Bundle();
        args.putSerializable("settings", settings);

        // set the result for caller activity
        SettingsDialogListener listener = (SettingsDialogListener) getActivity();
        listener.OnFinishedSettingsEdit(args);

        // dismiss dialog
        dismiss();
    }
}
