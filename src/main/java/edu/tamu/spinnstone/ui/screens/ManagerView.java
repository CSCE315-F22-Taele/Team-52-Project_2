package edu.tamu.spinnstone.ui.screens;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.tamu.spinnstone.ui.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ManagerView {
    public ManagerView() {
        $$$setupUI$$$();
        Actions.setViewCard.subscribe(cardName -> ((CardLayout) ManagerViewCard.getLayout()).show(ManagerViewCard, cardName.toString()));
    }

    private JPanel Container;
    private JPanel ManagerViewCard;
    private NavBar navBar;

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        Container = new JPanel();
        Container.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(8, 8, 8, 8), -1, -1));
        panel1.setBackground(new Color(-8355712));
        panel1.setForeground(new Color(-8355712));
        Container.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.add(navBar.$$$getRootComponent$$$(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        ManagerViewCard = new JPanel();
        ManagerViewCard.setLayout(new CardLayout(0, 0));
        Container.add(ManagerViewCard, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Shipments nestedForm1 = new Shipments();
        ManagerViewCard.add(nestedForm1.$$$getRootComponent$$$(), "ShipmentsCard");
        final Inventory nestedForm2 = new Inventory();
        ManagerViewCard.add(nestedForm2.$$$getRootComponent$$$(), "InventoryCard");
        final ManagerDashboard nestedForm3 = new ManagerDashboard();
        ManagerViewCard.add(nestedForm3.$$$getRootComponent$$$(), "DashboardCard");
        final Analytics nestedForm4 = new Analytics();
        ManagerViewCard.add(nestedForm4.$$$getRootComponent$$$(), "AnalyticsCard");
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return Container;
    }

    private void createUIComponents() {
        ArrayList<NavBar> navBars = Actions.navBars.getValue();
        navBar = new NavBar();
        navBars.add(navBar);
        Actions.navBars.onNext(navBars);
    }
}
