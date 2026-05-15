package com.pasterdream.pasterdreammod.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.pasterdream.pasterdreammod.client.model.DreamMeterItemModel;
import com.pasterdream.pasterdreammod.client.util.AnimUtils;
import com.pasterdream.pasterdreammod.item.DreamMeterItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.HashSet;
import java.util.Set;

/**
 * 忆梦魔导透镜渲染器 (Dream Meter Item Renderer)
 * 使用 GeckoLib 渲染 3D 手持模型
 *
 * 渲染特性：
 * - 第三人称显示 3D 透镜模型
 * - 第一人称隐藏手臂骨骼，渲染玩家手臂
 * - 使用半透明渲染以支持纹理透明度
 */
public class DreamMeterItemRenderer extends GeoItemRenderer<DreamMeterItem> {

    private static final float SCALE_RECIPROCAL = 1.0f / 16.0f;

    protected boolean renderArms = false;
    protected MultiBufferSource currentBuffer;
    protected RenderType renderType;
    public ItemDisplayContext transformType;
    protected DreamMeterItem animatable;

    private final Set<String> hiddenBones = new HashSet<>();
    private final Set<String> suppressedBones = new HashSet<>();

    public DreamMeterItemRenderer() {
        super(new DreamMeterItemModel());
    }

    @Override
    public RenderType getRenderType(DreamMeterItem animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext transformType, PoseStack matrixStack,
                             MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
        this.transformType = transformType;
        if (this.animatable != null)
            this.animatable.getTransformType(transformType);
        super.renderByItem(stack, transformType, matrixStack, bufferIn, combinedLightIn, p_239207_6_);
    }

    @Override
    public void actuallyRender(PoseStack matrixStackIn, DreamMeterItem animatable, BakedGeoModel model, RenderType type,
                               MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, boolean isRenderer,
                               float partialTicks, int packedLightIn, int packedOverlayIn, int color) {
        this.currentBuffer = renderTypeBuffer;
        this.renderType = type;
        this.animatable = animatable;
        super.actuallyRender(matrixStackIn, animatable, model, type, renderTypeBuffer, vertexBuilder,
                isRenderer, partialTicks, packedLightIn, packedOverlayIn, color);
        if (this.renderArms) {
            this.renderArms = false;
        }
    }

    @Override
    public void renderRecursively(PoseStack stack, DreamMeterItem animatable, GeoBone bone, RenderType type,
                                  MultiBufferSource buffer, VertexConsumer bufferIn, boolean isReRender,
                                  float partialTick, int packedLightIn, int packedOverlayIn, int color) {
        Minecraft mc = Minecraft.getInstance();
        String name = bone.getName();
        boolean renderingArms = false;
        if (name.equals("right") || name.equals("left")) {
            bone.setHidden(true);
            renderingArms = true;
        } else {
            bone.setHidden(this.hiddenBones.contains(name));
        }
        if (this.transformType != null && this.transformType.firstPerson() && renderingArms && mc.player != null) {
            AbstractClientPlayer player = (AbstractClientPlayer) mc.player;
            float armsAlpha = player.isInvisible() ? 0.15f : 1.0f;
            PlayerRenderer playerRenderer = (PlayerRenderer) mc.getEntityRenderDispatcher().getRenderer(player);
            if (playerRenderer != null) {
                PlayerModel<AbstractClientPlayer> model = playerRenderer.getModel();
                stack.pushPose();
                translateMatrixToBone(stack, bone);
                translateToPivotPoint(stack, bone);
                rotateMatrixAroundBone(stack, bone);
                scaleMatrixForBone(stack, bone);
                translateAwayFromPivotPoint(stack, bone);
                ResourceLocation loc = playerRenderer.getTextureLocation(player);
                VertexConsumer armBuilder = this.currentBuffer.getBuffer(RenderType.entitySolid(loc));
                VertexConsumer sleeveBuilder = this.currentBuffer.getBuffer(RenderType.entityTranslucent(loc));
                if (name.equals("right")) {
                    stack.translate(-1.0f * SCALE_RECIPROCAL, 2.0f * SCALE_RECIPROCAL, 0.0f);
                    AnimUtils.renderPartOverBone(model.leftArm, bone, stack, armBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, armsAlpha);
                    AnimUtils.renderPartOverBone(model.leftSleeve, bone, stack, sleeveBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, armsAlpha);
                } else if (name.equals("left")) {
                    stack.translate(1.0f * SCALE_RECIPROCAL, 2.0f * SCALE_RECIPROCAL, 0.0f);
                    AnimUtils.renderPartOverBone(model.rightArm, bone, stack, armBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, armsAlpha);
                    AnimUtils.renderPartOverBone(model.rightSleeve, bone, stack, sleeveBuilder, packedLightIn, OverlayTexture.NO_OVERLAY, armsAlpha);
                }
                this.currentBuffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(this.animatable)));
                stack.popPose();
            }
        }
        super.renderRecursively(stack, animatable, bone, type, buffer, bufferIn, isReRender, partialTick,
                packedLightIn, packedOverlayIn, color);
    }

    @Override
    public ResourceLocation getTextureLocation(DreamMeterItem instance) {
        return super.getTextureLocation(instance);
    }

    private static void translateMatrixToBone(PoseStack stack, GeoBone bone) {
        stack.translate(bone.getPivotX() / 16.0f, bone.getPivotY() / 16.0f, bone.getPivotZ() / 16.0f);
    }

    private static void translateToPivotPoint(PoseStack stack, GeoBone bone) {
        stack.translate(bone.getPivotX() / 16.0f, bone.getPivotY() / 16.0f, bone.getPivotZ() / 16.0f);
    }

    private static void rotateMatrixAroundBone(PoseStack stack, GeoBone bone) {
        if (bone.getRotX() != 0 || bone.getRotY() != 0 || bone.getRotZ() != 0) {
            stack.mulPose(new Quaternionf().rotationXYZ(bone.getRotX(), bone.getRotY(), bone.getRotZ()));
        }
    }

    private static void scaleMatrixForBone(PoseStack stack, GeoBone bone) {
        if (bone.getScaleX() != 1.0f || bone.getScaleY() != 1.0f || bone.getScaleZ() != 1.0f) {
            stack.scale(bone.getScaleX(), bone.getScaleY(), bone.getScaleZ());
        }
    }

    private static void translateAwayFromPivotPoint(PoseStack stack, GeoBone bone) {
        stack.translate(-bone.getPivotX() / 16.0f, -bone.getPivotY() / 16.0f, -bone.getPivotZ() / 16.0f);
    }
}