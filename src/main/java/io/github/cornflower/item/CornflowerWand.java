package io.github.cornflower.item;

import io.github.cornflower.entity.FeyEntity;
import io.github.cornflower.group.CornflowerGroup;
import net.minecraft.entity.LivingEntity;
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
        System.out.println("Block right clicked on: " + context.getBlockPos());

        if(context.getPlayer() != null) {
            if(context.getPlayer().isSneaking()) {
                // Set output block
                blockOutput = context.getBlockPos();
                context.getPlayer().addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_output"), true);

                return ActionResult.SUCCESS;
            }
        }

        // Set input block
        blockInput = context.getBlockPos();
        if(context.getPlayer() != null) {
            context.getPlayer().addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_input"), true);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public boolean useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        System.out.println("Entity right clicked on: " + entity.getBlockPos());

        // Handle clicking on a Fey to set the input/output block
        if(entity instanceof FeyEntity) {
            FeyEntity feyEntity = (FeyEntity)entity;

            feyEntity.setInputBlock(this.blockInput);
            feyEntity.setOutputBlock(this.blockOutput);

            user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_fey"), true);

            return true;
        }

        // Handle user sneaking to set the output block
        if(user != null) {
            if(user.isSneaking()) {
                // Set output block
                blockOutput = entity.getBlockPos();
                user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_output"), true);

                return true;
            }
        }

        // Set input block
        blockInput = entity.getBlockPos();
        if(user != null) {
            user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_input"), true);
        }
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        /*blockInput = user.getBlockPos();

        System.out.println("User pos: " + blockInput);
        user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use"), true);*/


        return super.use(world, user, hand);
    }
}
