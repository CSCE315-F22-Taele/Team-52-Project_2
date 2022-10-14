package edu.tamu.spinnstone.ui;


import edu.tamu.spinnstone.models.Order;
import edu.tamu.spinnstone.models.OrderItem;
import edu.tamu.spinnstone.models.sql.Database;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import java.util.ArrayList;
import java.util.List;

public class Actions {
    public static PublishSubject<Order> orderUpdated = PublishSubject.create();

    public static enum OptionCards {

        PIZZA("pizzaType"), DRINKS("drinks");

        private final String name;

        private OptionCards(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

    }

    public static enum ViewNames {
        SERVER("ServerCard"), MANAGER("ManagerCard"),

        INVENTORY("InventoryCard"),

        SHIPMENTS("ShipmentsCard"), DASHBOARD("DashboardCard");

        private final String name;

        private ViewNames(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    public static PublishSubject<OptionCards> setOptionsCard = PublishSubject.create();
    public static PublishSubject<ViewNames> setViewCard = PublishSubject.create();

    public static BehaviorSubject<Database> getDatabase = BehaviorSubject.create();
    public static BehaviorSubject<Order> getOrder = BehaviorSubject.create();
    public static BehaviorSubject<OrderItem> activeOrderItem = BehaviorSubject.create();
    public static BehaviorSubject<ArrayList<NavBar>> navBars = BehaviorSubject.create();
}
