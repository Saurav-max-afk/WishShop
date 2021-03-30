package com.saurav.wishshop;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RedmiFragment extends Fragment {
    RecyclerView mRecyclerView;
    private List<Main2Modal> main2ModalList;
  private   Main2Modal main2Modal;

    private DatabaseReference databaseReference;
    private ValueEventListener eventListener;
    ProgressDialog progressDialog;
    EditText textSearch1;
    MyAdapter myAdapter;



    public RedmiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_redmi, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        textSearch1=view.findViewById(R.id.txt_SearchText1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Items...");

        main2ModalList = new ArrayList<>();



         myAdapter = new MyAdapter(getContext(),main2ModalList);
        mRecyclerView.setAdapter(myAdapter);

        databaseReference= FirebaseDatabase.getInstance().getReference("Redmi_Name");
        progressDialog.show();
        eventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                main2ModalList.clear();
                for (DataSnapshot itemSnapshot: snapshot.getChildren()){
                    main2Modal=itemSnapshot.getValue(Main2Modal.class);
                    main2ModalList.add(main2Modal);
                }
                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        textSearch1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });

        return view;

    }

    private void filter(String text) {
        ArrayList<Main2Modal> filterList = new ArrayList<>();
        for (Main2Modal item: main2ModalList){

            if (item.getItemName().toLowerCase().contains(text.toLowerCase())){

                filterList.add(item);
            }

        }
        myAdapter.filteredList(filterList);

    }
}