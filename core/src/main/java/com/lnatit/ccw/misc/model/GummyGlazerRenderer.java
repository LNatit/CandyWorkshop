package com.lnatit.ccw.misc.model;

import com.lnatit.ccw.item.GummyGlazerItem;
import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.component.GummyContents;
import com.lnatit.ccw.menu.client.GummyGlazerScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;
import org.joml.Vector3f;

import static net.minecraft.client.renderer.entity.ItemRenderer.getFoilBufferDirect;


public class GummyGlazerRenderer extends BlockEntityWithoutLevelRenderer {
    public static final double STEP = 0.0625;
    public static final ItemTransform STEP_1 = new ItemTransform(
            new Vector3f(0, 180, 0),
            new Vector3f(),
            new Vector3f(1, 1, 1)
    );

    public GummyGlazerRenderer() {
        super(dispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(
            ItemStack stack,
            ItemDisplayContext displayContext,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay
    ) {
        if (stack.getItem() instanceof GummyGlazerItem glazer) {
            boolean leftHand = displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
                    || displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
            // since we already have a push-pop in ItemRenderer
            poseStack.translate(0.5F, 0.5F, 0.5F);
            BakedModel baked = Minecraft.getInstance().getModelManager().getModel(of(glazer));
            // maybe useless cuz no overrides here
            baked = baked.getOverrides().resolve(baked, stack, null, null, 0);
            baked = ClientHooks.handleCameraTransforms(poseStack, baked, displayContext, leftHand);
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            renderBaked(baked, stack, poseStack, bufferSource, packedLight, packedOverlay);
            poseStack.translate(0.5F, 0.5F, 0.5F);
            STEP_1.apply(leftHand, poseStack);

            if (displayContext.firstPerson() && stack.has(ItemRegistry.GLAZER_CONTENTS_DCTYPE)) {
                NonNullList<ItemStack> items = getContents(stack);
                for (int i = 0; i < items.size(); i++) {
                    ItemStack gummy = items.get(i);
                    if (gummy.isEmpty())
                        continue;

                    poseStack.pushPose();
                    baked = Minecraft.getInstance().getItemRenderer().getModel(gummy, Minecraft.getInstance().level, Minecraft.getInstance().player, 0);
                    // Why here mojang resolved it twice?
                    baked = baked.getOverrides().resolve(baked, gummy, Minecraft.getInstance().level, Minecraft.getInstance().player, 0);
                    step2(i).apply(leftHand, poseStack);
                    poseStack.translate(-0.5F, -0.5F, -0.5F);
                    renderBaked(baked, gummy, poseStack, bufferSource, packedLight, packedOverlay);
                    poseStack.popPose();
                }
            }
        }
    }

    private static void renderBaked(BakedModel baked, ItemStack stack, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        for (BakedModel model : baked.getRenderPasses(stack, true)) {
            for (RenderType rendertype : model.getRenderTypes(stack, true)) {
                VertexConsumer vertexconsumer =
                        getFoilBufferDirect(bufferSource, rendertype, true, stack.hasFoil());
                Minecraft.getInstance()
                        .getItemRenderer()
                        .renderModelLists(model, stack, packedLight, packedOverlay, poseStack, vertexconsumer);
            }
        }
    }

    public static BlockEntityRenderDispatcher dispatcher() {
        return Minecraft.getInstance().getBlockEntityRenderDispatcher();
    }

    public static ModelResourceLocation of(GummyGlazerItem glazer) {
        if (glazer == ItemRegistry.NETHER_GLAZER.get()) {
            return ModelHandler.NETHER_GLAZER;
        }
        if (glazer == ItemRegistry.ENDER_GLAZER.get()) {
            return ModelHandler.ENDER_GLAZER;
        }
        return ModelHandler.GUMMY_GLAZER;
    }

    private static NonNullList<ItemStack> getContents(ItemStack stack) {
        if (Minecraft.getInstance().screen instanceof GummyGlazerScreen screen) {
            return screen.getMenu().items();
        }
        GummyContents contents = stack.get(ItemRegistry.GLAZER_CONTENTS_DCTYPE);
        return contents.items();
    }

    private static ItemTransform step2(int index) {
        return new ItemTransform(
                new Vector3f(0, 0, 0),
                new Vector3f(0, 5f - (index * 5), 2.5f).mul((float) STEP),
                new Vector3f(.25f, .25f, .25f)
        );
    }
}
