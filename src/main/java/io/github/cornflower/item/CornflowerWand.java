package io.github.cornflower.item;

import io.github.cornflower.group.CornflowerGroup;
import net.minecraft.item.Item;

import static io.github.cornflower.group.CornflowerGroup.CORNFLOWER_GROUP;

public class CornflowerWand extends Item {

    public CornflowerWand() {
        super(new Settings().group(CORNFLOWER_GROUP).maxCount(1));
    }


}
