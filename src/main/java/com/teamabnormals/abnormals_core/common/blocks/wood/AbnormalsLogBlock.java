package com.teamabnormals.abnormals_core.common.blocks.wood;

import java.util.function.Supplier;

import com.teamabnormals.abnormals_core.core.utils.BlockUtils;
import com.teamabnormals.abnormals_core.core.utils.ItemStackUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

/**
 * @author SmellyModder(Luke Tonon)
 */
public class AbnormalsLogBlock extends RotatedPillarBlock {
	private final Supplier<Block> block;

	public AbnormalsLogBlock(Supplier<Block> strippedBlock, Properties properties) {
		super(properties);
		this.block = strippedBlock;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
		if(player.getHeldItem(hand).getItem() instanceof AxeItem) {
			world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.setBlockState(pos, BlockUtils.transferAllBlockStates(state, this.block.get().getDefaultState()));
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if(ItemStackUtils.isInGroup(this.asItem(), group)) {
			int targetIndex = ItemStackUtils.findIndexOfItem(Items.DARK_OAK_LOG, items);
			if(targetIndex != -1) {
				items.add(targetIndex + 1, new ItemStack(this));
			} else {
				super.fillItemGroup(group, items);
			}
		}
	}
}