package io.github.cornflower.item;

import io.github.cornflower.entity.FeyEntity;
import io.github.cornflower.group.CornflowerGroup;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static io.github.cornflower.group.CornflowerGroup.CORNFLOWER_GROUP;

public class CornflowerWand extends Item {

    private BlockPos blockInput;
    private BlockPos blockOutput;

    public CornflowerWand() {
        super(new Settings().group(CORNFLOWER_GROUP).maxCount(1));
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        System.out.println("Block right clicked on: " + context.getBlockPos());

        if(context.getPlayer() != null && context.getWorld().getBlockEntity(context.getBlockPos()) instanceof Inventory) {
            if(context.getPlayer().isSneaking()) {
                // Set output block
                blockOutput = context.getBlockPos();
                context.getPlayer().addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_output"), true);
                return ActionResult.SUCCESS;
            } else {
                // Set input block
                blockInput = context.getBlockPos();
                context.getPlayer().addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_input"), true);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
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
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        if(blockInput == null) tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip1"));
        else tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip1").append(blockInput.toShortString()));
        if(blockOutput == null) tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip2"));
        else tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip2").append(blockOutput.toShortString()));
    }

    public void setBlockInput(BlockPos blockInput) {
        this.blockInput = blockInput;
    }

    public void setBlockOutput(BlockPos blockOutput) {
        this.blockOutput = blockOutput;
    }

}
