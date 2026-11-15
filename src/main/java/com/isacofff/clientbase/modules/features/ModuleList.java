package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

public class ModuleList extends Module {
    public ModuleList() {
        super("ModuleList", Category.Client);
        this.description = "Shows enabled KYZXX modules in a clean on-screen list.";
    }
}
