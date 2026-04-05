package com.example.myapplication.ui.order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.model.Order;
import com.example.myapplication.data.order.OrderRepository;

import java.util.List;

public class OrderViewModel extends ViewModel {
    private OrderRepository orderRepository;
    private MutableLiveData<List<Order>> currentOrders = new MutableLiveData<>();
    private MutableLiveData<List<Order>> historyOrders = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCurrentOrderSelected = new MutableLiveData<>(true);
    public OrderViewModel(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        refresh();
    }
    public LiveData<List<Order>> getCurrentOrders() {
        return currentOrders;
    }

    public LiveData<List<Order>> getHistoryOrders() {
        return historyOrders;
    }
    public LiveData<Boolean> getIsCurrentOrderSelected() {
        return isCurrentOrderSelected;
    }
    public void refresh() {
        currentOrders.setValue(orderRepository.getCurrentOrders());
        historyOrders.setValue(orderRepository.getHistoryOrders());
    }

    // 切换左侧分类
    public void selectCurrentOrder() {isCurrentOrderSelected.setValue(true);}

    public void selectHistoryOrders() {
        isCurrentOrderSelected.setValue(false);
    }
}
