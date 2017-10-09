package io.github.mosadie.Minetana.Blocks;

import javax.annotation.Nullable;

import io.github.mosadie.Minetana.Items.ItemManager;
import io.github.mosadie.Minetana.TileEntitys.SmartSwitchTE;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
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

public class SmartSwitch extends Block implements ITileEntityProvider {

	private SmartSwitchTE te;

	public SmartSwitch() {
		super(Material.PISTON);
		this.setUnlocalizedName("smartswitch");
		this.setCreativeTab(ItemManager.CreativeTab);
		this.setHardness(2.5f);
		this.setResistance(15);
		this.isBlockContainer = true;
	}

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side)
	{
		return true;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return te.isOn() ? 15 : 0;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return te=new SmartSwitchTE(this);
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
}
