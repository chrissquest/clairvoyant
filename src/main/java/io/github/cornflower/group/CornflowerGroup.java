package io.github.cornflower.group;

import io.github.cornflower.util.ModConstants;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class CornflowerGroup {

  public static final ItemGroup CORNFLOWER_GROUP = FabricItemGroupBuilder.create(new Identifier(ModConstants.MOD_ID, "cornflower_group"))
      .icon(Items.CORNFLOWER::getStackForRender).build();

  public static void init() {}
}
