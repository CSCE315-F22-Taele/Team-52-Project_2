package edu.tamu.spinnstone.ui;


import edu.tamu.spinnstone.models.sql.Database;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import edu.tamu.spinnstone.models.OrderItem;
import edu.tamu.spinnstone.models.Order;

public class Actions {
    public static PublishSubject<OrderItem> addOrderItem = PublishSubject.create();
    public static PublishSubject<Order> orderUpdated = PublishSubject.create();

    public static BehaviorSubject<Database> getDatabase = BehaviorSubject.create();
    public static BehaviorSubject<Order> getOrder = BehaviorSubject.create();
}
