package com.xecoder.core.entity;

import java.io.Serializable;

/**
 * Created by vincent on 1/15/17.
 */
public class Menu implements Serializable {
    private static final long serialVersionUID = 5587610037067616570L;
    private String title;
    private String icon = "ion-android-home";
    private boolean selected = false;
    private boolean expanded = false;
    private int order = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
