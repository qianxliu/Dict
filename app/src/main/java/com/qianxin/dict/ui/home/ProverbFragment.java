package com.qianxin.dict.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.qianxin.dict.R;
import com.qianxin.dict.databinding.FragmentHomeBinding;
import com.qianxin.dict.db.entity.Proverb;
import com.qianxin.dict.ui.newproverb.NewProverbActivity;

import static android.app.Activity.RESULT_OK;

public class ProverbFragment extends Fragment {

    private ProverbViewModel proverbViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);

        final ProverbListAdapter adapter = new ProverbListAdapter(new ProverbListAdapter.ProverbDiff());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));


        proverbViewModel =
                new ViewModelProvider(this).get(ProverbViewModel.class);

        proverbViewModel.getAllProverbs().observe(
                getViewLifecycleOwner(), proverbs -> {
                    adapter.submitList(proverbs);
                }
        );



        FloatingActionButton fab = requireActivity().findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), NewProverbActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    // 当堆栈上面的activity结束后
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Proverb proverb = new Proverb(data.getStringArrayExtra(NewProverbActivity.EXTRA_REPLY));
            proverbViewModel.insert(proverb);

        } else {
            Toast.makeText(
                    getContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

}