/*
 * This file ("ContainerRangedCollector.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.inventory;

import ellpeck.actuallyadditions.inventory.slot.SlotFilter;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityRangedCollector;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

@InventoryContainer
public class ContainerRangedCollector extends Container{

    private TileEntityRangedCollector collector;

    public ContainerRangedCollector(InventoryPlayer inventory, TileEntityBase tile){
        this.collector = (TileEntityRangedCollector)tile;

        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 3; j++){
                this.addSlotToContainer(new Slot(this.collector, j+i*3, 96+j*18, 24+i*18));
            }
        }
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 3; j++){
                this.addSlotToContainer(new SlotFilter(this.collector, 6+j+i*3, 20+j*18, 6+i*18));
            }
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j+i*9+9, 8+j*18, 90+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 148));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        final int inventoryStart = 18;
        final int inventoryEnd = inventoryStart+26;
        final int hotbarStart = inventoryEnd+1;
        final int hotbarEnd = hotbarStart+8;

        Slot theSlot = (Slot)this.inventorySlots.get(slot);

        if(theSlot != null && theSlot.getHasStack()){
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Other Slots in Inventory excluded
            if(slot >= inventoryStart){
                //Shift from Inventory
                if(!this.mergeItemStack(newStack, 0, TileEntityRangedCollector.WHITELIST_START, false)){
                    //
                    if(slot >= inventoryStart && slot <= inventoryEnd){
                        if(!this.mergeItemStack(newStack, hotbarStart, hotbarEnd+1, false)){
                            return null;
                        }
                    }
                    else if(slot >= inventoryEnd+1 && slot < hotbarEnd+1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd+1, false)){
                        return null;
                    }
                }
            }
            else if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, false)){
                return null;
            }

            if(newStack.stackSize == 0){
                theSlot.putStack(null);
            }
            else{
                theSlot.onSlotChanged();
            }

            if(newStack.stackSize == currentStack.stackSize){
                return null;
            }
            theSlot.onPickupFromSlot(player, newStack);

            return currentStack;
        }
        return null;
    }

    @Override
    public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer player){
        if(par1 >= 0 && par1 < this.inventorySlots.size() && this.getSlot(par1) instanceof SlotFilter){
            //Calls the Filter's SlotClick function
            return ((SlotFilter)getSlot(par1)).slotClick(player);
        }
        else{
            return super.slotClick(par1, par2, par3, player);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.collector.isUseableByPlayer(player);
    }
}