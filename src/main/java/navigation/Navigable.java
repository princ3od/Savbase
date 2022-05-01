package navigation;

import java.util.Stack;

public abstract class Navigable {
    protected Stack<String> tabs = new Stack();

    protected String getCurrentTab() {
        return tabs.peek();
    }

    protected abstract void push(String tabName);

    protected void replace(String tabName) {
        if (!tabs.empty()) {
            tabs.pop();
        }
        push(tabName);
    }

    protected void back() {
        if (!tabs.empty()) {
            tabs.pop();
        }
        String previousTab = tabs.pop();
        push(previousTab);
    }

    protected void pushAndRemoveAll(String tabName) {
        while (!tabs.empty()) {
            tabs.pop();
        }
        push(tabName);
    }
}
