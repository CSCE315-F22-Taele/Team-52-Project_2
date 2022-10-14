package edu.tamu.spinnstone.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class NavBar {
    public JPanel navContainer;
    public JToggleButton MANAGERVIEWButton;
    public JToggleButton SERVERVIEWButton;
    public JToggleButton INVENTORYButton;
    public JToggleButton SHIPMENTSButton;
    private JPanel col1;
    private JPanel col2;
    private JPanel col3;
    private JPanel col4;

    private void toggleButtons(ActiveButton activeButton, boolean showManagerButtons) {

        for (NavBar navbar : Actions.navBars.getValue()) {

            navbar.SERVERVIEWButton.setSelected(false);
            navbar.MANAGERVIEWButton.setSelected(false);
            navbar.INVENTORYButton.setSelected(false);
            navbar.SHIPMENTSButton.setSelected(false);


            java.util.List<JToggleButton> buttons =
                    Arrays.asList(navbar.INVENTORYButton, navbar.SHIPMENTSButton, navbar.SERVERVIEWButton, navbar.MANAGERVIEWButton);

            switch (activeButton) {
                case inventory:
                    navbar.INVENTORYButton.setSelected(true);
                    break;
                case manager:
                    navbar.MANAGERVIEWButton.setSelected(true);
                    break;
                case server:
                    navbar.SERVERVIEWButton.setSelected(true);
                    break;
                case shipment:
                    navbar.SHIPMENTSButton.setSelected(true);
                    break;
            }

            if (showManagerButtons) {
                navbar.INVENTORYButton.setVisible(true);
                navbar.SHIPMENTSButton.setVisible(true);
                navbar.MANAGERVIEWButton.setSelected(true);
            } else {
                navbar.INVENTORYButton.setVisible(false);
                navbar.SHIPMENTSButton.setVisible(false);
                navbar.SERVERVIEWButton.setSelected(true);
            }

            for (JToggleButton button : buttons) {
                button.repaint();
            }
        }
    }

    enum ActiveButton {
        server,
        manager,
        inventory,
        shipment
    }

    public NavBar() {
        $$$setupUI$$$();
        SERVERVIEWButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Actions.setViewCard.onNext(Actions.ViewNames.SERVER);
                toggleButtons(ActiveButton.server, false);
            }
        });
        MANAGERVIEWButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Actions.setViewCard.onNext(Actions.ViewNames.MANAGER);
                toggleButtons(ActiveButton.manager, true);
                Actions.setViewCard.onNext(Actions.ViewNames.DASHBOARD);
            }
        });

        INVENTORYButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Actions.setViewCard.onNext(Actions.ViewNames.INVENTORY);
                toggleButtons(ActiveButton.inventory, true);

            }
        });
        SHIPMENTSButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                Actions.setViewCard.onNext(Actions.ViewNames.SHIPMENTS);
                toggleButtons(ActiveButton.shipment, true);
            }
        });
    }
    // @formatter:off

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        navContainer = new JPanel();
        navContainer.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), 0, 0));
        navContainer.setBackground(new Color(-8355712));
        col1 = new JPanel();
        col1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 8, 0, 8), 0, 0));
        col1.setBackground(new Color(-8355712));
        navContainer.add(col1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        SERVERVIEWButton = new JToggleButton();
        SERVERVIEWButton.setBackground(new Color(-15066598));
        SERVERVIEWButton.setForeground(new Color(-1));
        SERVERVIEWButton.setText("SERVER VIEW");
        col1.add(SERVERVIEWButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        navContainer.add(spacer1, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        col2 = new JPanel();
        col2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 8, 0, 8), 0, 0));
        col2.setBackground(new Color(-8355712));
        navContainer.add(col2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        MANAGERVIEWButton = new JToggleButton();
        MANAGERVIEWButton.setBackground(new Color(-15066598));
        MANAGERVIEWButton.setForeground(new Color(-1));
        MANAGERVIEWButton.setText("MANAGER VIEW");
        col2.add(MANAGERVIEWButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        col3 = new JPanel();
        col3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 8, 0, 8), -1, -1));
        col3.setBackground(new Color(-8355712));
        navContainer.add(col3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        INVENTORYButton = new JToggleButton();
        INVENTORYButton.setBackground(new Color(-15066598));
        INVENTORYButton.setForeground(new Color(-1));
        INVENTORYButton.setText("INVENTORY");
        col3.add(INVENTORYButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        col4 = new JPanel();
        col4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 8, 0, 8), -1, -1));
        col4.setBackground(new Color(-8355712));
        navContainer.add(col4, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        SHIPMENTSButton = new JToggleButton();
        SHIPMENTSButton.setBackground(new Color(-15066598));
        SHIPMENTSButton.setForeground(new Color(-1));
        SHIPMENTSButton.setText("SHIPMENTS");
        col4.add(SHIPMENTSButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return navContainer;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        SHIPMENTSButton = new JToggleButton();
        INVENTORYButton = new JToggleButton();

        SHIPMENTSButton.setVisible(false);
        INVENTORYButton.setVisible(false);
    }

    // @formatter:on

}
