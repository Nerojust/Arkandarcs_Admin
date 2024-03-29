package com.nerojust.arkandarcsadmin.admin.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nerojust.arkandarcsadmin.R;
import com.nerojust.arkandarcsadmin.admin.adapters.PageViewModel;
import com.nerojust.arkandarcsadmin.admin.adapters.ReadyToShipOrderAdapter;
import com.nerojust.arkandarcsadmin.models.orders.OrdersResponse;
import com.nerojust.arkandarcsadmin.web_services.WebServiceRequestMaker;
import com.nerojust.arkandarcsadmin.web_services.interfaces.OrdersInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadyToShipFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ReadyToShipFragment() {
        // Required empty public constructor
    }

    public static ReadyToShipFragment newInstance(int index) {
        ReadyToShipFragment fragment = new ReadyToShipFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageViewModel pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_ready_to_ship, container, false);
        initViews(view);

        getAllOrders();
        return view;
    }

    private void initViews(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(this::getAllOrders);

        recyclerView = view.findViewById(R.id.recycler_readytoship);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void getAllOrders() {
        swipeRefreshLayout.setRefreshing(true);

        WebServiceRequestMaker webServiceRequestMaker = new WebServiceRequestMaker();
        webServiceRequestMaker.getAllrtsOrders(new OrdersInterface() {
            @Override
            public void onSuccess(OrdersResponse response) {
                ReadyToShipOrderAdapter ordersAdapter = new ReadyToShipOrderAdapter(response, getContext());
                recyclerView.setAdapter(ordersAdapter);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onErrorCode(int errorCode) {
                Toast.makeText(getContext(), errorCode + "", Toast.LENGTH_SHORT).show();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

}
