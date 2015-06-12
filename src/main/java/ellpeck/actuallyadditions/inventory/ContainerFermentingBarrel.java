package ellpeck.actuallyadditions.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.inventory.slot.SlotOutput;
import ellpeck.actuallyadditions.tile.TileEntityBase;
import ellpeck.actuallyadditions.tile.TileEntityFermentingBarrel;
import invtweaks.api.container.InventoryContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

@InventoryContainer
public class ContainerFermentingBarrel extends Container{

    private TileEntityFermentingBarrel barrel;

    private int lastProcessTime;
    private int lastCanolaTank;
    private int lastOilTank;

    public ContainerFermentingBarrel(InventoryPlayer inventory, TileEntityBase tile){
        this.barrel = (TileEntityFermentingBarrel)tile;

        this.addSlotToContainer(new Slot(this.barrel, 0, 42, 74));
        this.addSlotToContainer(new SlotOutput(this.barrel, 1, 42, 43));
        this.addSlotToContainer(new Slot(this.barrel, 2, 118, 74));
        this.addSlotToContainer(new SlotOutput(this.barrel, 3, 118, 43));

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 155));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.barrel.isUseableByPlayer(player);
    }

    @Override
    public void addCraftingToCrafters(ICrafting iCraft){
        super.addCraftingToCrafters(iCraft);
        iCraft.sendProgressBarUpdate(this, 0, this.barrel.oilTank.getFluidAmount());
        iCraft.sendProgressBarUpdate(this, 1, this.barrel.canolaTank.getFluidAmount());
        iCraft.sendProgressBarUpdate(this, 2, this.barrel.currentProcessTime);
    }

    @Override
    public void detectAndSendChanges(){
        super.detectAndSendChanges();
        for(Object crafter : this.crafters){
            ICrafting iCraft = (ICrafting)crafter;

            if(this.lastOilTank != this.barrel.oilTank.getFluidAmount()) iCraft.sendProgressBarUpdate(this, 0, this.barrel.oilTank.getFluidAmount());
            if(this.lastCanolaTank != this.barrel.canolaTank.getFluidAmount()) iCraft.sendProgressBarUpdate(this, 1, this.barrel.canolaTank.getFluidAmount());
            if(this.lastProcessTime != this.barrel.currentProcessTime) iCraft.sendProgressBarUpdate(this, 2, this.barrel.currentProcessTime);
        }

        this.lastOilTank = this.barrel.oilTank.getFluidAmount();
        this.lastCanolaTank = this.barrel.canolaTank.getFluidAmount();
        this.lastProcessTime = this.barrel.currentProcessTime;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2){
        if(par1 == 0) this.barrel.oilTank.setFluid(new FluidStack(InitBlocks.fluidOil, par2));
        if(par1 == 1) this.barrel.canolaTank.setFluid(new FluidStack(InitBlocks.fluidCanolaOil, par2));
        if(par1 == 2) this.barrel.currentProcessTime = par2;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        final int inventoryStart = 4;
        final int inventoryEnd = inventoryStart+26;
        final int hotbarStart = inventoryEnd+1;
        final int hotbarEnd = hotbarStart+8;

        Slot theSlot = (Slot)this.inventorySlots.get(slot);
        if(theSlot.getHasStack()){
            ItemStack currentStack = theSlot.getStack();
            ItemStack newStack = currentStack.copy();

            if(currentStack.getItem() != null){
                if(slot <= hotbarEnd && slot >= inventoryStart){
                    if(FluidContainerRegistry.containsFluid(currentStack, new FluidStack(InitBlocks.fluidCanolaOil, FluidContainerRegistry.BUCKET_VOLUME))){
                        this.mergeItemStack(newStack, 0, 1, false);
                    }
                    if(currentStack.getItem() == Items.bucket){
                        this.mergeItemStack(newStack, 2, 3, false);
                    }
                }

                if(slot <= hotbarEnd && slot >= hotbarStart){
                    this.mergeItemStack(newStack, inventoryStart, inventoryEnd+1, false);
                }

                else if(slot <= inventoryEnd && slot >= inventoryStart){
                    this.mergeItemStack(newStack, hotbarStart, hotbarEnd+1, false);
                }

                else if(slot < inventoryStart){
                    this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, false);
                }

                if(newStack.stackSize == 0) theSlot.putStack(null);
                else theSlot.onSlotChanged();
                if(newStack.stackSize == currentStack.stackSize) return null;
                theSlot.onPickupFromSlot(player, newStack);

                return currentStack;
            }
        }
        return null;
    }
}