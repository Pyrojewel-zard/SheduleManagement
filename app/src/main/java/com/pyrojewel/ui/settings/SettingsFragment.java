package com.pyrojewel.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSettingsBinding;
import com.pyrojewel.BaseFuncActivity;

/**
 * @author Pyrojewel
 */
public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private ImageView  courseDetail;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentSettingsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        courseDetail =getActivity().findViewById(R.id.Course);
        courseDetail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), BaseFuncActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}