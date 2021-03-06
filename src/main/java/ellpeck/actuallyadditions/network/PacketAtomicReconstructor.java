/*
 * This file ("PacketAtomicReconstructor.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.recipe.ReconstructorRecipeHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PacketAtomicReconstructor implements IMessage{

    private int startX;
    private int startY;
    private int startZ;
    private int endX;
    private int endY;
    private int endZ;
    private int lensTypeOrdinal;

    @SuppressWarnings("unused")
    public PacketAtomicReconstructor(){

    }

    public PacketAtomicReconstructor(int startX, int startY, int startZ, int endX, int endY, int endZ, ReconstructorRecipeHandler.LensType type){
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
        this.lensTypeOrdinal = type.ordinal();
    }

    @Override
    public void fromBytes(ByteBuf buf){
        this.startX = buf.readInt();
        this.startY = buf.readInt();
        this.startZ = buf.readInt();
        this.endX = buf.readInt();
        this.endY = buf.readInt();
        this.endZ = buf.readInt();
        this.lensTypeOrdinal = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(this.startX);
        buf.writeInt(this.startY);
        buf.writeInt(this.startZ);
        buf.writeInt(this.endX);
        buf.writeInt(this.endY);
        buf.writeInt(this.endZ);
        buf.writeInt(this.lensTypeOrdinal);
    }

    public static class Handler implements IMessageHandler<PacketAtomicReconstructor, IMessage>{

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketAtomicReconstructor message, MessageContext ctx){
            World world = Minecraft.getMinecraft().theWorld;

            if(Minecraft.getMinecraft().thePlayer.getDistance(message.startX, message.startY, message.startZ) <= 64){
                int difX = message.startX-message.endX;
                int difY = message.startY-message.endY;
                int difZ = message.startZ-message.endZ;
                double distance = Vec3.createVectorHelper(message.startX, message.startY, message.startZ).distanceTo(Vec3.createVectorHelper(message.endX, message.endY, message.endZ));

                for(int times = 0; times < 5; times++){
                    for(double i = 0; i <= 1; i += 1/(distance*8)){
                        ReconstructorRecipeHandler.LensType type = ReconstructorRecipeHandler.LensType.values()[message.lensTypeOrdinal];
                        float[] color = type.getColor();
                        Minecraft.getMinecraft().effectRenderer.addEffect(new EntityReddustFX(world, (difX*i)+message.endX+0.5, (difY*i)+message.endY+0.5, (difZ*i)+message.endZ+0.5, 2F, color[0], color[1], color[2]));
                    }
                }
            }

            return null;
        }
    }
}
