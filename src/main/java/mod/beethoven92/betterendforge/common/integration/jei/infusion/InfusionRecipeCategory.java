package mod.beethoven92.betterendforge.common.integration.jei.infusion;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class InfusionRecipeCategory implements IRecipeCategory<InfusionRecipeWrapper> {
    protected static final int INPUT_SLOT = 0;
    protected static final int[] CATALYST_SLOTS = {1, 2, 3, 4, 5, 6, 7, 8};
    protected static final int OUTPUT_SLOT = 9;

    private final IDrawable background;
    private final IDrawable icon;

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/gui/jei/jei_infusion_gui.png");
    public static final String UID = "betterendforge.infusion";

    public InfusionRecipeCategory(IGuiHelper guiHelper) {
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.INFUSION_PEDESTAL));
        background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 146, 98);
    }

    @Nonnull
    @Override
    public String getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return I18n.format("gui.jei.category.infusion");
    }

    @Nonnull
    @Override
    public String getModName() {
        return BetterEnd.MOD_ID;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull InfusionRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
        recipeLayout.getItemStacks().init(INPUT_SLOT, true, 38, 46);
        recipeLayout.getItemStacks().init(CATALYST_SLOTS[0], true, 38, 18);
        recipeLayout.getItemStacks().init(CATALYST_SLOTS[1], true, 62, 22);
        recipeLayout.getItemStacks().init(CATALYST_SLOTS[2], true, 66, 46);
        recipeLayout.getItemStacks().init(CATALYST_SLOTS[3], true, 62, 70);
        recipeLayout.getItemStacks().init(CATALYST_SLOTS[4], true, 38, 74);
        recipeLayout.getItemStacks().init(CATALYST_SLOTS[5], true, 14, 70);
        recipeLayout.getItemStacks().init(CATALYST_SLOTS[6], true, 10, 46);
        recipeLayout.getItemStacks().init(CATALYST_SLOTS[7], true, 14, 22);
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 118, 46);

        recipeLayout.getItemStacks().set(ingredients);
    }
}