/*
 * This file ("TileEntityHeatCollector.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyProvider;
import ellpeck.actuallyadditions.util.Util;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public class TileEntityHeatCollector extends TileEntityBase implements IEnergyProvider{

    public EnergyStorage storage = new EnergyStorage(30000);

    public static final int ENERGY_PRODUCE = 40;
    public static final int BLOCKS_NEEDED = 4;

    @Override
    public void updateEntity(){
        super.updateEntity();
        if(!worldObj.isRemote){
            ArrayList<Integer> blocksAround = new ArrayList<Integer>();
            if(ENERGY_PRODUCE <= this.getMaxEnergyStored(ForgeDirection.UNKNOWN)-this.getEnergyStored(ForgeDirection.UNKNOWN)){
                for(int i = 1; i <= 5; i++){
                    WorldPos coords = WorldUtil.getCoordsFromSide(WorldUtil.getDirectionBySidesInOrder(i), worldObj, xCoord, yCoord, zCoord, 0);
                    if(coords != null){
                        Block block = worldObj.getBlock(coords.getX(), coords.getY(), coords.getZ());
                        if(block != null && block.getMaterial() == Material.lava && worldObj.getBlockMetadata(coords.getX(), coords.getY(), coords.getZ()) == 0){
                            blocksAround.add(i);
                        }
                    }
                }

                if(blocksAround.size() >= BLOCKS_NEEDED){
                    this.storage.receiveEnergy(ENERGY_PRODUCE, false);
                    this.markDirty();

                    if(Util.RANDOM.nextInt(10) == 0){
                        int randomSide = blocksAround.get(Util.RANDOM.nextInt(blocksAround.size()));
                        WorldUtil.breakBlockAtSide(WorldUtil.getDirectionBySidesInOrder(randomSide), worldObj, xCoord, yCoord, zCoord);
                    }
                }
            }

            if(this.getEnergyStored(ForgeDirection.UNKNOWN) > 0){
                WorldUtil.pushEnergy(worldObj, xCoord, yCoord, zCoord, ForgeDirection.UP, this.storage);
            }
        }
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
        return this.storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from){
        return this.storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from){
        return this.storage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from){
        return from == ForgeDirection.UP;
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        this.storage.writeToNBT(compound);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        this.storage.readFromNBT(compound);
    }
}
