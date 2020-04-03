package io.github.cornflower.item;

import io.github.cornflower.block.CornflowerCauldronBlock;
import io.github.cornflower.util.ModConstants;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CornflowerItems {

    public static final Item CORNFLOWER_WAND = registerItem(new CornflowerWand(), "wand_cornflower");


    private static Item registerItem(Item item, String id) {
        return Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, id), new CornflowerWand());
    }

    public static void init() {}
}
