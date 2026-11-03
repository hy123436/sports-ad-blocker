package com.adblocker.sports;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

public class AdBlockerService extends AccessibilityService {
    private static final String TARGET = "com.sports.world";
    
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (!TARGET.equals(event.getPackageName().toString())) return;
        
        AccessibilityNodeInfo root = getRootInActiveWindow();
        if (root == null) return;
        
        // 点击跳过按钮
        List<AccessibilityNodeInfo> skip = root.findAccessibilityNodeInfosByText("跳过");
        if (!skip.isEmpty() && skip.get(0).isClickable()) {
            skip.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        
        // 点击关闭按钮
        String[] closes = {"关闭", "取消", "我知道了", "×"};
        for (String txt : closes) {
            List<AccessibilityNodeInfo> nodes = root.findAccessibilityNodeInfosByText(txt);
            if (!nodes.isEmpty() && nodes.get(0).isClickable()) {
                nodes.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                break;
            }
        }
        root.recycle();
    }
    
    @Override
    public void onInterrupt() {}
}
