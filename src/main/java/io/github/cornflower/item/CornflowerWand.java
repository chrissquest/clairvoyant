package io.github.cornflower.item;

import io.github.cornflower.group.CornflowerGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sun.jvm.hotspot.opto.Block;

import java.util.Objects;

import static io.github.cornflower.group.CornflowerGroup.CORNFLOWER_GROUP;

public class CornflowerWand extends Item {

    private BlockPos blockInput;
    private BlockPos blockOutput;

    public CornflowerWand() {
        super(new Settings().group(CORNFLOWER_GROUP).maxCount(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        blockInput = context.getBlockPos();

        System.out.println("Block right clicked on: " + blockInput);
        Objects.requireNonNull(context.getPlayer()).addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use"), true);

        return super.useOnBlock(context);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        blockInput = user.getBlockPos();

        System.out.println("User pos: " + blockInput);
        user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use"), true);


        return super.use(world, user, hand);
    }
}
