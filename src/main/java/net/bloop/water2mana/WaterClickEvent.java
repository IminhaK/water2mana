package net.bloop.water2mana;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.tile.mana.TilePool;

@EventBusSubscriber(modid = Water2Mana.MODID)
public class WaterClickEvent {

    @SubscribeEvent
    public static void EmptyPool(FillBucketEvent event)
    {
        RayTraceResult lookingAt = getLookingAt(event.getEntityPlayer());
        if(!(lookingAt == null))
        {
            BlockPos blockpos = lookingAt.getBlockPos();
            Block block = event.getWorld().getBlockState(blockpos).getBlock();
            Item bucket = event.getEmptyBucket().getItem();
            World world = event.getWorld();
            EntityPlayer player = event.getEntityPlayer();

            if(block == ModBlocks.pool) {
                TilePool pool = (TilePool) event.getWorld().getTileEntity(blockpos);
                if (bucket == Items.BUCKET && !world.isRemote && !player.isSneaking() && pool.isFull())
                {
                    event.setCanceled(true);
                    pool.recieveMana(-1000000);
                    if (!player.capabilities.isCreativeMode)
                        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.WATER_BUCKET));
                }

                if (bucket == Items.WATER_BUCKET && !world.isRemote && !player.isSneaking())
                {
                    event.setCanceled(true);
                    pool.recieveMana(1000000);
                    if (!player.capabilities.isCreativeMode)
                        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BUCKET));
                }
            }
        }
    }

    //thanks direwolf20
    public static RayTraceResult getLookingAt(EntityPlayer player) {
        double rayTraceRange = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();

        World world = player.world;
        Vec3d look = player.getLookVec();
        Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3d end = new Vec3d(player.posX + look.x * rayTraceRange, player.posY + player.getEyeHeight() + look.y * rayTraceRange, player.posZ + look.z * rayTraceRange);
        return world.rayTraceBlocks(start, end, false, false, false);
    }
}
