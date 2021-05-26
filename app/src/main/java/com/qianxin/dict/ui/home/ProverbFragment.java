package com.qianxin.dict.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qianxin.dict.R;
import com.qianxin.dict.databinding.FragmentProverbBinding;
import com.qianxin.dict.db.entity.Proverb;
import com.qianxin.dict.ui.newproverb.NewProverbActivity;

import static android.app.Activity.RESULT_OK;

public class ProverbFragment extends Fragment {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private ProverbViewModel proverbViewModel;
    private FragmentProverbBinding binding;
    private ProverbListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProverbBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        adapter = new ProverbListAdapter(new ProverbListAdapter.ProverbDiff());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        FloatingActionButton fab = requireActivity().findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), NewProverbActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        proverbViewModel =
                new ViewModelProvider(this).get(ProverbViewModel.class);
        proverbViewModel.getAllProverbs().observe(
                getViewLifecycleOwner(), proverbs -> {
                    adapter.submitList(proverbs);
                }
        );

        binding.input.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        proverbViewModel.searchAllProverbs(s.toString()).observe(
                                getViewLifecycleOwner(), myProverbs -> {
                                    adapter.submitList(myProverbs);
                                });
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // 当堆栈上面的activity结束后
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Proverb proverb = new Proverb(data.getStringArrayExtra(NewProverbActivity.EXTRA_REPLY));
            proverbViewModel.insert(proverb);
            Toast.makeText(getContext(), "已自动保存！", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(
                    getContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}