package io.github.cgh;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Timed
public class OrderService {

    @Inject
    MetricRegistry metricRegistry;

    @Inject
    @Metric
    Counter ordersNumber;

    private Map<String, Order> orders = new ConcurrentHashMap<>();

    public void order(String name) {
        String id = UUID.randomUUID().toString();
        this.orders.put(id, new Order(id, name));
        metricRegistry.counter(name + "_orders").inc();
        ordersNumber.inc();
    }

    public Stream<Order> orders() {
        return orders.values().stream();
    }

    public Order getOrder(String id) {
        return orders.get(id);
    }

}
