package edu.tamu.spinnstone.ui;


import edu.tamu.spinnstone.models.Order;
import edu.tamu.spinnstone.models.OrderItem;
import edu.tamu.spinnstone.models.sql.Database;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import javax.swing.*;
import java.util.ArrayList;

public class Actions {
    public enum OptionCards {
        PIZZA("pizza"), OTHER("other"), DRINKS("drinks");
        private final String name;

        private OptionCards(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }
    }

    public enum ViewNames {
        SERVER("ServerCard"), MANAGER("ManagerCard"), INVENTORY("InventoryCard"), SHIPMENTS("ShipmentsCard"), DASHBOARD("DashboardCard"), ANALYTICS("AnalyticsCard");

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
    public static PublishSubject<Void> menuItemAdded = PublishSubject.create();

    public static BehaviorSubject<Database> getDatabase = BehaviorSubject.create();
    public static BehaviorSubject<Order> getOrder = BehaviorSubject.create();
    public static BehaviorSubject<OrderItem> activeOrderItem = BehaviorSubject.create();
    public static BehaviorSubject<ArrayList<NavBar>> navBars = BehaviorSubject.create();
    public static BehaviorSubject<JFrame> getFrame = BehaviorSubject.create();
    public static BehaviorSubject<JDialog> activeDialog = BehaviorSubject.create();
}
