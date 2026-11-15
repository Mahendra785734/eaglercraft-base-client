package com.isacofff.clientbase.modules.features;

import com.isacofff.clientbase.Category;
import com.isacofff.clientbase.modules.Module;

/**
 * Metadata-only entry for a planned client-side quality-of-life feature.
 * A catalog entry is not presented as a completed implementation. Features
 * that have a real game hook are implemented by their own module class.
 */
public final class CatalogModule extends Module {

    public CatalogModule(String name, String description, Category category) {
        super(name, description, category);
    }
}
