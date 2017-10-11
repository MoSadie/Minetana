package io.github.mosadie.Minetana.Blocks;

import javax.annotation.Nullable;

import io.github.mosadie.Minetana.Minetana;
import io.github.mosadie.Minetana.Items.ItemManager;
import io.github.mosadie.Minetana.TileEntitys.SmartSwitchTE;
import io.github.mosadie.Minetana.events.MinetanaInteractEvent;
import io.github.mosadie.Minetana.events.MinetanaInteractEvent.Intent;
import io.github.mosadie.Minetana.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SmartSwitch extends Block implements ITileEntityProvider {

	private SmartSwitchTE te;
	
	public static final IProperty<Boolean> on = PropertyBool.create("on");

	public SmartSwitch() {
		super(Material.PISTON);
		this.setUnlocalizedName("smartswitch");
		this.setCreativeTab(ItemManager.CreativeTab);
		this.setHardness(2.5f);
		this.setResistance(15);
		this.isBlockContainer = true;
		this.setDefaultState(this.getDefaultState().withProperty(on, false));
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, on);
	}
	
	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(on, meta == 1 ? true : false);
    }
	
	@Override
	public int getMetaFromState(IBlockState state)
    {
        return state.getValue(on) == true ? 1 : 0;
    }

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side)
	{
		return true;
	}
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return 0;
	}
	
	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getValue(on) ? 15 : 0;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		te = new SmartSwitchTE();
		return te;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			((SmartSwitchTE) worldIn.getTileEntity(pos)).setCustomName(stack.getDisplayName());
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote) return te.toggleOwner(playerIn);
		else return false;
	}
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }
	
	@Override
	public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return false;
	}
}
