package mod.beethoven92.betterendforge.common.integration.jei.alloying;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.recipes.AlloyingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class AlloyingRecipeCategory implements IRecipeCategory<AlloyingRecipeWrapper> {
    protected static final int FIRST_INPUT_SLOT = 0;
    protected static final int SECOND_INPUT_SLOT = 1;
    protected static final int FUEL_SLOT = 2;
    protected static final int OUTPUT_SLOT = 3;

    private final IDrawable background;
    private final IDrawable icon;
    protected final IDrawableStatic staticFlame;
    protected final IDrawableAnimated animatedFlame;

    private final LoadingCache<Integer, IDrawableAnimated> cachedArrows;

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(BetterEnd.MOD_ID, "textures/gui/jei/jei_smelter_gui.png");
    public static final String UID = "betterendforge.alloying";

    public AlloyingRecipeCategory(IGuiHelper guiHelper) {
        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.END_STONE_SMELTER));
        background = guiHelper.createDrawable(GUI_TEXTURE, 0, 0, 106, 54);
        staticFlame = guiHelper.createDrawable(GUI_TEXTURE, 106, 0, 14, 14);
        animatedFlame = guiHelper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);
        this.cachedArrows = CacheBuilder.newBuilder()
                .maximumSize(25)
                .build(new CacheLoader<Integer, IDrawableAnimated>() {
                    @Override
                    public IDrawableAnimated load(Integer cookTime) {
                        IDrawableStatic arrowDrawable = guiHelper.createDrawable(GUI_TEXTURE, 106, 14, 24, 17);
                        return guiHelper.createAnimatedDrawable(arrowDrawable, cookTime, IDrawableAnimated.StartDirection.LEFT, false);
                    }
                });
    }

    @Nonnull
    @Override
    public String getUid() {
        return UID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return I18n.format("gui.jei.category.alloying");
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
    public void drawExtras(@Nonnull Minecraft minecraft) {
        animatedFlame.draw(minecraft, 12, 20);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull AlloyingRecipeWrapper recipeWrapper, @Nonnull IIngredients ingredients) {
        recipeLayout.getItemStacks().init(FIRST_INPUT_SLOT, true, 0, 0);
        recipeLayout.getItemStacks().init(SECOND_INPUT_SLOT, true, 22, 0);
        recipeLayout.getItemStacks().init(FUEL_SLOT, true, 11, 36);
        recipeLayout.getItemStacks().init(OUTPUT_SLOT, false, 84, 18);
        recipeLayout.getItemStacks().set(ingredients);
    }

    protected IDrawableAnimated getArrow(AlloyingRecipe recipe) {
        int smeltTime = recipe.getSmeltTime();
        return this.cachedArrows.getUnchecked(smeltTime);
    }
}