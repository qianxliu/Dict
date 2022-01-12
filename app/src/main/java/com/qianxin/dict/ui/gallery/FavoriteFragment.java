package com.qianxin.dict.ui.gallery;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qianxin.dict.R;
import com.qianxin.dict.databinding.FragmentFavoriteBinding;
import com.qianxin.dict.ui.item.ProverbListAdapter;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel favoriteViewModel;
    private FragmentFavoriteBinding binding;
    private ProverbListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_fav);
        adapter = new ProverbListAdapter(new ProverbListAdapter.ProverbDiff());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        favoriteViewModel.getFavoriteProverbs().observe(
                getViewLifecycleOwner(), proverbs -> adapter.submitList(proverbs)
        );

        binding.inputFav.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        favoriteViewModel.searchFavoriteProverbs(s.toString()).observe(
                                getViewLifecycleOwner(), myProverbs -> {
                                    if (!s.toString().isEmpty()) {
                                        adapter.submitList(myProverbs);
                                    } else {
                                        favoriteViewModel.getFavoriteProverbs().observe(
                                                getViewLifecycleOwner(), proverbs -> adapter.submitList(proverbs)
                                        );
                                    }
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
}