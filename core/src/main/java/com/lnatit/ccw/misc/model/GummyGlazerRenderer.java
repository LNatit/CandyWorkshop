package com.lnatit.ccw.misc.model;

import com.lnatit.ccw.item.ItemRegistry;
import com.lnatit.ccw.item.component.GummyContents;
import com.lnatit.ccw.menu.client.GummyGlazerScreen;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3fc;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

public record GummyGlazerRenderer() implements SpecialModelRenderer<NonNullList<ItemStack>>
{
    private static final GummyGlazerRenderer INSTANCE = new GummyGlazerRenderer();

    @Override
    public @Nullable NonNullList<ItemStack> extractArgument(ItemStack stack) {
        if (Minecraft.getInstance().screen instanceof GummyGlazerScreen screen) {
            return screen.getMenu().items();
        }
        GummyContents contents = stack.get(ItemRegistry.GLAZER_CONTENTS_DCTYPE);
        return contents != null ? contents.items() : null;
    }

    @Override
    public void submit(
            @Nullable NonNullList<ItemStack> argument,
            PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            int lightCoords,
            int overlayCoords,
            boolean hasFoil,
            int outlineColor
    ) {
        if (argument == null) {
            return;
        }
        var renderState = new ItemStackRenderState();
        Minecraft.getInstance().getItemModelResolver().appendItemLayers(renderState, argument.getFirst(), ItemDisplayContext.GUI, null, null, 0);
        poseStack.pushPose();
        poseStack.translate(0.75F, 0.75F, 0.532f);
        poseStack.scale(0.6F, 0.6F, 0.001F);
        renderState.submit(poseStack, submitNodeCollector, lightCoords, overlayCoords, outlineColor);
        poseStack.popPose();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
    }

//    public static final double STEP = 0.0625;
//    public static final ItemTransform STEP_1 = new ItemTransform(
//            new Vector3f(0, 180, 0),
//            new Vector3f(),
//            new Vector3f(1, 1, 1)
//    );
//
//    @Override
//    public void renderByItem(
//            ItemStack stack,
//            ItemDisplayContext displayContext,
//            PoseStack poseStack,
//            MultiBufferSource bufferSource,
//            int packedLight,
//            int packedOverlay
//    ) {
//        if (stack.getItem() instanceof GummyGlazerItem glazer) {
//            boolean leftHand = displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND
//                    || displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
//            // since we already have a push-pop in ItemRenderer
//            poseStack.translate(0.5F, 0.5F, 0.5F);
//            BakedModel baked = Minecraft.getInstance().getModelManager().getModel(of(glazer));
//            // maybe useless cuz no overrides here
//            baked = baked.getOverrides().resolve(baked, stack, null, null, 0);
//            baked = ClientHooks.handleCameraTransforms(poseStack, baked, displayContext, leftHand);
//            poseStack.translate(-0.5F, -0.5F, -0.5F);
//            renderBaked(baked, stack, poseStack, bufferSource, packedLight, packedOverlay);
//            poseStack.translate(0.5F, 0.5F, 0.5F);
//            STEP_1.apply(leftHand, poseStack);
//
//            if (displayContext.firstPerson() && stack.has(ItemRegistry.GLAZER_CONTENTS_DCTYPE)) {
//                NonNullList<ItemStack> items = getContents(stack);
//                for (int i = 0; i < items.size(); i++) {
//                    ItemStack gummy = items.get(i);
//                    if (gummy.isEmpty())
//                        continue;
//
//                    poseStack.pushPose();
//                    baked = Minecraft.getInstance().getItemRenderer().getModel(gummy, Minecraft.getInstance().level, Minecraft.getInstance().player, 0);
//                    // Why here mojang resolved it twice?
//                    baked = baked.getOverrides().resolve(baked, gummy, Minecraft.getInstance().level, Minecraft.getInstance().player, 0);
//                    step2(i).apply(leftHand, poseStack);
//                    poseStack.translate(-0.5F, -0.5F, -0.5F);
//                    renderBaked(baked, gummy, poseStack, bufferSource, packedLight, packedOverlay);
//                    poseStack.popPose();
//                }
//            }
//        }
//    }
//
//    private static void renderBaked(BakedModel baked, ItemStack stack, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
//        for (BakedModel model : baked.getRenderPasses(stack, true)) {
//            for (RenderType rendertype : model.getRenderTypes(stack, true)) {
//                VertexConsumer vertexconsumer =
//                        getFoilBufferDirect(bufferSource, rendertype, true, stack.hasFoil());
//                Minecraft.getInstance()
//                        .getItemRenderer()
//                        .renderModelLists(model, stack, packedLight, packedOverlay, poseStack, vertexconsumer);
//            }
//        }
//    }
//
//    public static Identifier of(GummyGlazerItem glazer) {
//        if (glazer == ItemRegistry.NETHER_GLAZER.get()) {
//            return ModelHandler.NETHER_GLAZER;
//        }
//        if (glazer == ItemRegistry.ENDER_GLAZER.get()) {
//            return ModelHandler.ENDER_GLAZER;
//        }
//        return ModelHandler.GUMMY_GLAZER;
//    }
//
//    private static NonNullList<ItemStack> getContents(ItemStack stack) {
//        if (Minecraft.getInstance().screen instanceof GummyGlazerScreen screen) {
//            return screen.getMenu().items();
//        }
//        GummyContents contents = stack.get(ItemRegistry.GLAZER_CONTENTS_DCTYPE);
//        return contents.items();
//    }
//
//    private static ItemTransform step2(int index) {
//        return new ItemTransform(
//                new Vector3f(0, 0, 0),
//                new Vector3f(0, 5f - (index * 5), 2.5f).mul((float) STEP),
//                new Vector3f(.25f, .25f, .25f)
//        );
//    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<NonNullList<ItemStack>> {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(Unbaked::new);

        @Override
        public SpecialModelRenderer<NonNullList<ItemStack>> bake(BakingContext bakingContext) {
            return INSTANCE;
        }

        @Override
        public MapCodec<? extends SpecialModelRenderer.Unbaked<NonNullList<ItemStack>>> type() {
            return MAP_CODEC;
        }
    }
}
