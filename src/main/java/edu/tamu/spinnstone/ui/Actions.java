package edu.tamu.spinnstone.ui;


import edu.tamu.spinnstone.models.sql.Database;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import edu.tamu.spinnstone.models.OrderItem;
import edu.tamu.spinnstone.models.Product;
import edu.tamu.spinnstone.models.Order;

public class Actions {
    public static PublishSubject<Order> orderUpdated = PublishSubject.create();

    public static enum OptionCards {

        PIZZA("pizzaType"),
        DRINKS("drinks");

        private final String name;

        private OptionCards(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    public static PublishSubject<OptionCards> setOptionsCard = PublishSubject.create();

    public static BehaviorSubject<Database> getDatabase = BehaviorSubject.create();
    public static BehaviorSubject<Order> getOrder = BehaviorSubject.create();
    public static BehaviorSubject<OrderItem> activeOrderItem = BehaviorSubject.create();
}
