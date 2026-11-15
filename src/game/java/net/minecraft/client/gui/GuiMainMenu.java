package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.EaglercraftRandom;
import net.lax1dude.eaglercraft.EaglercraftVersion;
import net.lax1dude.eaglercraft.IOUtils;
import net.lax1dude.eaglercraft.internal.PlatformApplication;
import net.lax1dude.eaglercraft.internal.PlatformOpenGL;
import net.lax1dude.eaglercraft.minecraft.MainMenuSkyboxTexture;
import net.lax1dude.eaglercraft.opengl.WorldRenderer;
import net.lax1dude.eaglercraft.sp.SingleplayerServerController;
import net.lax1dude.eaglercraft.sp.gui.GuiScreenIntegratedServerBusy;
import net.lax1dude.eaglercraft.sp.gui.GuiScreenIntegratedServerStartup;
import net.lax1dude.eaglercraft.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.opengl.GlStateManager;
import net.lax1dude.eaglercraft.opengl.RealOpenGLEnums;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.peyton.eagler.gui.GuiCredits;
import net.lax1dude.eaglercraft.profile.*;

public class GuiMainMenu extends GuiScreen {
	private static int kyzxxBackgroundStyle = 0;
	private static final String[] KYZXX_BACKGROUNDS = new String[] { "WINTER", "AURORA", "STARS", "SUNSET", "CYBER", "NEBULA" };
	private static final EaglercraftRandom RANDOM = new EaglercraftRandom();

	/** Counts the number of screen updates. */
	private final float updateCounter;

	/** The splash message. */
	private String splashText;
	private GuiButton buttonResetDemo;

	/** Timer used to rotate the panorama, increases every tick. */
	private float panoramaTimer;

	/**
	 * Texture allocated for the current viewport of the main menu's panorama
	 * background.
	 */
	private static MainMenuSkyboxTexture viewportTexture = null;
	private static MainMenuSkyboxTexture viewportTexture2 = null;

	/**
	 * The Object object utilized as a thread lock when performing non thread-safe
	 * operations
	 */
	// private final Object threadLock = new Object();
	public static final String MORE_INFO_TEXT = "Please click " + TextFormatting.UNDERLINE + "here"
			+ TextFormatting.RESET + " for more information.";

	private static final ResourceLocation SPLASH_TEXTS = new ResourceLocation("texts/splashes.txt");
	private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation(
			"textures/gui/title/minecraft.png");
	private static final ResourceLocation field_194400_H = new ResourceLocation("textures/gui/title/edition.png");

	/** An array of all the paths to the panorama pictures. */
	private static final ResourceLocation[] TITLE_PANORAMA_PATHS = new ResourceLocation[] {
			new ResourceLocation("textures/gui/title/background/panorama_0.png"),
			new ResourceLocation("textures/gui/title/background/panorama_1.png"),
			new ResourceLocation("textures/gui/title/background/panorama_2.png"),
			new ResourceLocation("textures/gui/title/background/panorama_3.png"),
			new ResourceLocation("textures/gui/title/background/panorama_4.png"),
			new ResourceLocation("textures/gui/title/background/panorama_5.png") };
	private ResourceLocation backgroundTexture = null;
	private static ResourceLocation backgroundTexture2 = null;

	private int field_193978_M;
	private int field_193979_N;

	public GuiMainMenu() {
		this.splashText = "missingno";
		IResource iresource = null;

		try {
			List<String> list = Lists.<String>newArrayList();
			iresource = Minecraft.getMinecraft().getResourceManager().getResource(SPLASH_TEXTS);
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
			String s;

			while ((s = bufferedreader.readLine()) != null) {
				s = s.trim();

				if (!s.isEmpty()) {
					list.add(s);
				}
			}

			if (!list.isEmpty()) {
				while (true) {
					this.splashText = list.get(RANDOM.nextInt(list.size()));

					if (this.splashText.hashCode() != 125780783) {
						break;
					}
				}
			}
		} catch (IOException var8) {
			;
		} finally {
			IOUtils.closeQuietly((Closeable) iresource);
		}

		this.updateCounter = RANDOM.nextFloat();
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
	}

	/**
	 * Returns true if this GUI should pause the game when it is displayed in
	 * single-player
	 */
	public boolean doesGuiPauseGame() {
		return false;
	}

	/**
	 * Fired when a key is typed (except F11 which toggles full screen). This is the
	 * equivalent of KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when
	 * the GUI is displayed and when the window resizes, the buttonList is cleared
	 * beforehand.
	 */
	public void initGui() {
        if (!this.mc.gameSettings.hasSeenFirstLoad) {
            this.mc.displayGuiScreen(new GuiScreenFirstLoad(this.mc.gameSettings));
            return;
        }

        viewportTexture = new MainMenuSkyboxTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        viewportTexture2 = new MainMenuSkyboxTexture(256, 256);
        backgroundTexture2 = this.mc.getTextureManager().getDynamicTextureLocation("background", viewportTexture2);
        this.field_193978_M = this.fontRendererObj.getStringWidth("Resources copyright Mojang AB");
        this.field_193979_N = this.width - this.field_193978_M - 2;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            this.splashText = I18n.format("eaglercraft.splashes.xmas");
        } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            this.splashText = I18n.format("eaglercraft.splashes.newyear");
        } else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
            this.splashText = I18n.format("eaglercraft.splashes.halloween");
        }

        int navX = 18;
        int buttonX = navX + 16;
        int buttonY = 132;
        if (this.mc.isDemo()) this.addDemoButtons(buttonY, 38);
        else this.addSingleplayerMultiplayerButtons(buttonY, 38);

        for (int n = 0; n < this.buttonList.size(); ++n) {
            GuiButton button = (GuiButton) this.buttonList.get(n);
            if (button.id == 1 || button.id == 11) {
                button.xPosition = buttonX;
                button.yPosition = buttonY;
                button.width = 160;
                button.height = 32;
            } else if (button.id == 2 || button.id == 12) {
                button.xPosition = buttonX;
                button.yPosition = buttonY + 40;
                button.width = 160;
                button.height = 32;
            } else if (button.id == 14) {
                button.xPosition = buttonX;
                button.yPosition = buttonY + 80;
                button.width = 160;
                button.height = 32;
            }
        }

        this.buttonList.add(new GuiButton(0, buttonX, buttonY + 132, 76, 26, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(4, buttonX + 84, buttonY + 132, 76, 26, "Cosmetics"));
        this.buttonList.add(new GuiButtonLanguage(5, navX + 16, this.height - 40));
    }

    /**
	 * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have
	 * bought the game.
	 */
	private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
		this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer")));
		this.buttonList.add(
				new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 1, I18n.format("menu.multiplayer")));
		this.buttonList
				.add(new GuiButton(14, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, I18n.format("menu.credits")));
	}

	/**
	 * Adds Demo buttons on Main Menu for players who are playing Demo.
	 */
	private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
		this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo")));
		this.buttonResetDemo = this.addButton(
				new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo")));
		ISaveFormat isaveformat = this.mc.getSaveLoader();
		WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

		if (worldinfo == null) {
			this.buttonResetDemo.enabled = false;
		}
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for
	 * buttons)
	 */
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}

		if (button.id == 5) {
			this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
		}

		if (button.id == 1) {
			this.mc.displayGuiScreen(new GuiScreenIntegratedServerStartup(this));
		}

		if (button.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}

		if (button.id == 4) {
			this.mc.displayGuiScreen(new GuiScreenEditProfile(this));
		}

		if (button.id == 11) {
			this.mc.launchIntegratedServer("Demo_World", "Demo_World", WorldServerDemo.DEMO_WORLD_SETTINGS);
		}

		if (button.id == 12) {
			ISaveFormat isaveformat = this.mc.getSaveLoader();
			WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

			if (worldinfo != null) {
				this.mc.displayGuiScreen(new GuiYesNo(this, I18n.format("selectWorld.deleteQuestion"),
						"'" + worldinfo.getWorldName() + "' " + I18n.format("selectWorld.deleteWarning"),
						I18n.format("selectWorld.deleteButton"), I18n.format("gui.cancel"), 12));
			}
		}

		if (button.id == 14) {
			this.mc.displayGuiScreen(new GuiCredits(this, "/assets/eagler/credits.txt"));
		}

		if (button.id == 15) {
			kyzxxBackgroundStyle = (kyzxxBackgroundStyle + 1) % KYZXX_BACKGROUNDS.length;
			button.displayString = "BG: " + KYZXX_BACKGROUNDS[kyzxxBackgroundStyle];
		}
	}

	public void confirmClicked(boolean result, int id) {
		if (result && id == 12) {
			ISaveFormat isaveformat = this.mc.getSaveLoader();
			isaveformat.flushCache();
			isaveformat.deleteWorldDirectory("Demo_World");
			this.mc.displayGuiScreen(new GuiScreenIntegratedServerBusy(this, "singleplayer.busy.deleting",
					"singleplayer.failed.deleting", SingleplayerServerController::isReady));
		} else if (id == 12) {
			this.mc.displayGuiScreen(this);
		}
	}

	/**
	 * Draws the main menu panorama
	 */
	private void drawPanorama(int mouseX, int mouseY, float partialTicks) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer bufferbuilder = tessellator.getBuffer();
		GlStateManager.matrixMode(5889);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		GlStateManager.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
		GlStateManager.matrixMode(5888);
		GlStateManager.pushMatrix();
		GlStateManager.loadIdentity();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.disableCull();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(RealOpenGLEnums.GL_SRC_ALPHA, RealOpenGLEnums.GL_ONE_MINUS_SRC_ALPHA,
				RealOpenGLEnums.GL_ONE, RealOpenGLEnums.GL_ZERO);
		byte b0 = 4;

		for (int i = 0; i < b0 * b0; ++i) {
			GlStateManager.pushMatrix();
			float f = ((float) (i % b0) / (float) b0 - 0.5F) / 64.0F;
			float f1 = ((float) (i / b0) / (float) b0 - 0.5F) / 64.0F;
			float f2 = 0.0F;
			GlStateManager.translate(f, f1, f2);
			GlStateManager.rotate(MathHelper.sin(((float) this.panoramaTimer + partialTicks) / 400.0F) * 25.0F + 20.0F,
					1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(-((float) this.panoramaTimer + partialTicks) * 0.1F, 0.0F, 1.0F, 0.0F);

			for (int j = 0; j < 6; ++j) {
				GlStateManager.pushMatrix();
				if (j == 1) {
					GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
				}

				if (j == 2) {
					GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
				}

				if (j == 3) {
					GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
				}

				if (j == 4) {
					GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				}

				if (j == 5) {
					GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
				}

				this.mc.getTextureManager().bindTexture(TITLE_PANORAMA_PATHS[j]);

				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
				int k = 255 / (i + 1);
				float f3 = 0.0F;
				bufferbuilder.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, k).endVertex();
				bufferbuilder.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, k).endVertex();
				bufferbuilder.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, k).endVertex();
				bufferbuilder.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, k).endVertex();
				tessellator.draw();
				GlStateManager.popMatrix();
			}

			GlStateManager.popMatrix();
			GlStateManager.colorMask(true, true, true, false);
		}

		bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
		GlStateManager.colorMask(true, true, true, true);
		GlStateManager.matrixMode(5889);
		GlStateManager.popMatrix();
		GlStateManager.matrixMode(5888);
		GlStateManager.popMatrix();
		GlStateManager.depthMask(true);
		GlStateManager.enableCull();
		GlStateManager.enableDepth();
	}

	/**
	 * Rotate and blurs the skybox view in the main menu
	 */
	private void rotateAndBlurSkybox() {
		EaglercraftGPU.glTexParameteri(3553, 10241, 9729);
		EaglercraftGPU.glTexParameteri(3553, 10240, 9729);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(RealOpenGLEnums.GL_SRC_ALPHA, RealOpenGLEnums.GL_ONE_MINUS_SRC_ALPHA,
				RealOpenGLEnums.GL_ONE, RealOpenGLEnums.GL_ZERO);
		GlStateManager.colorMask(true, true, true, false);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		GlStateManager.disableAlpha();
		int i = 3;

		for (int j = 0; j < 3; ++j) {
			float f = 1.0F / (float) (j + 1);
			int k = this.width;
			int l = this.height;
			float f1 = (float) (j - 1) / 256.0F;
			bufferbuilder.pos((double) k, (double) l, (double) this.zLevel).tex((double) (0.0F + f1), 1.0D)
					.color(1.0F, 1.0F, 1.0F, f).endVertex();
			bufferbuilder.pos((double) k, 0.0D, (double) this.zLevel).tex((double) (1.0F + f1), 1.0D)
					.color(1.0F, 1.0F, 1.0F, f).endVertex();
			bufferbuilder.pos(0.0D, 0.0D, (double) this.zLevel).tex((double) (1.0F + f1), 0.0D)
					.color(1.0F, 1.0F, 1.0F, f).endVertex();
			bufferbuilder.pos(0.0D, (double) l, (double) this.zLevel).tex((double) (0.0F + f1), 0.0D)
					.color(1.0F, 1.0F, 1.0F, f).endVertex();
		}

		tessellator.draw();
		GlStateManager.enableAlpha();
		GlStateManager.colorMask(true, true, true, true);
	}

	/**
	 * Renders the skybox in the main menu
	 */
	private void renderSkybox(int mouseX, int mouseY, float partialTicks) {
		viewportTexture.bindFramebuffer();
		GlStateManager.viewport(0, 0, 256, 256);
		GlStateManager.clearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GlStateManager.clear(RealOpenGLEnums.GL_COLOR_BUFFER_BIT);
		this.drawPanorama(mouseX, mouseY, partialTicks);
		viewportTexture2.bindFramebuffer();
		GlStateManager.clearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GlStateManager.clear(RealOpenGLEnums.GL_COLOR_BUFFER_BIT);
		this.mc.getTextureManager().bindTexture(backgroundTexture);
		this.rotateAndBlurSkybox();
		viewportTexture.bindFramebuffer();
		this.mc.getTextureManager().bindTexture(backgroundTexture2);
		this.rotateAndBlurSkybox();
		viewportTexture2.bindFramebuffer();
		this.mc.getTextureManager().bindTexture(backgroundTexture);
		this.rotateAndBlurSkybox();
		viewportTexture.bindFramebuffer();
		this.mc.getTextureManager().bindTexture(backgroundTexture2);
		this.rotateAndBlurSkybox();
		viewportTexture2.bindFramebuffer();
		this.mc.getTextureManager().bindTexture(backgroundTexture);
		this.rotateAndBlurSkybox();
		viewportTexture.bindFramebuffer();
		this.mc.getTextureManager().bindTexture(backgroundTexture2);
		this.rotateAndBlurSkybox();

		PlatformOpenGL._wglBindFramebuffer(0x8D40, null);
		this.mc.getTextureManager().bindTexture(backgroundTexture);
		
		GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		float f = 120.0F / (float) (this.width > this.height ? this.width : this.height);
		float f1 = (float) this.height * f / 256.0F;
		float f2 = (float) this.width * f / 256.0F;
		int i = this.width;
		int j = this.height;
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		bufferbuilder.pos(0.0D, (double) j, (double) this.zLevel).tex((double) (0.5F - f1), (double) (0.5F + f2))
				.color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		bufferbuilder.pos((double) i, (double) j, (double) this.zLevel).tex((double) (0.5F - f1), (double) (0.5F - f2))
				.color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		bufferbuilder.pos((double) i, 0.0D, (double) this.zLevel).tex((double) (0.5F + f1), (double) (0.5F - f2))
				.color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		bufferbuilder.pos(0.0D, 0.0D, (double) this.zLevel).tex((double) (0.5F + f1), (double) (0.5F + f2))
				.color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
		tessellator.draw();
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.panoramaTimer += partialTicks;
        GlStateManager.disableAlpha();
        this.renderSkybox(mouseX, mouseY, partialTicks);
        GlStateManager.enableAlpha();

        this.drawGradientRect(0, 0, this.width, this.height, 0x55101825, 0xDD070B12);
        this.drawGradientRect(0, 0, this.width, this.height, 0x00000000, 0x99000000);
        this.drawKyzxxWinterOverlay();

        int sideX = 18;
        int sideY = 48;
        int sideRight = 194;
        int bottom = this.height - 22;
        int mainX = 208;
        int mainRight = this.width - 18;

        // Top client bar
        drawRect(0, 0, this.width, 36, 0xF00A1019);
        drawRect(0, 35, this.width, 37, 0xFFFFC15A);
        this.drawString(this.fontRendererObj, "KYZXX", 18, 12, 0xFFFFC15A);
        this.drawString(this.fontRendererObj, "CLIENT", 59, 12, 0xFFF2F4F8);
        this.drawString(this.fontRendererObj, "EAGLERCRAFT 1.12.2", 111, 12, 0xFF91A0B2);
        this.drawString(this.fontRendererObj, "1.12.2", this.width - 58, 12, 0xFF91A0B2);

        // Left navigation rail
        drawRect(sideX, sideY, sideRight, bottom, 0xE90D141F);
        drawRect(sideX, sideY, sideRight, sideY + 2, 0xFFFFC15A);
        drawRect(sideX, sideY, sideX + 1, bottom, 0xFF8B6538);
        this.drawString(this.fontRendererObj, "MAIN MENU", sideX + 16, sideY + 18, 0xFF9AA7B8);
        this.drawString(this.fontRendererObj, "PLAY", sideX + 16, sideY + 68, 0xFFFFC15A);
        this.drawString(this.fontRendererObj, "Choose a game mode", sideX + 16, sideY + 82, 0xFF8794A5);
        this.drawString(this.fontRendererObj, "SETTINGS", sideX + 16, sideY + 226, 0xFF9AA7B8);
        drawRect(sideX + 14, sideY + 215, sideRight - 14, sideY + 216, 0xFF354154);

        // Main content panel
        drawRect(mainX, sideY, mainRight, bottom, 0xDD101925);
        drawRect(mainX, sideY, mainRight, sideY + 2, 0xFF39485B);
        this.drawString(this.fontRendererObj, "WELCOME BACK", mainX + 24, sideY + 22, 0xFFFFC15A);
        this.drawString(this.fontRendererObj, this.mc.getSession().getProfile().getName(),
                mainX + 24, sideY + 42, 0xFFF2F4F8);
        this.drawString(this.fontRendererObj, "Your game is ready.", mainX + 24, sideY + 57, 0xFF9AA7B8);

        int cardY = sideY + 94;
        int profileX = Math.max(mainX + 250, mainRight - 184);
        int textRight = profileX - 22;
        drawRect(mainX + 20, cardY, textRight, bottom - 22, 0xB91A2533);
        drawRect(mainX + 20, cardY, mainX + 22, bottom - 22, 0xFFFFC15A);
        this.drawString(this.fontRendererObj, "KYZXX CLIENT", mainX + 36, cardY + 22, 0xFFFFC15A);
        this.drawString(this.fontRendererObj, "A cleaner way to play Eaglercraft.", mainX + 36, cardY + 42, 0xFFE8EDF4);
        this.drawString(this.fontRendererObj, "Select Singleplayer to enter your world,", mainX + 36, cardY + 70, 0xFF9AA7B8);
        this.drawString(this.fontRendererObj, "or Multiplayer to connect to a server.", mainX + 36, cardY + 84, 0xFF9AA7B8);
        drawRect(mainX + 36, cardY + 111, textRight - 16, cardY + 112, 0xFF354154);
        this.drawString(this.fontRendererObj, "CLIENT FEATURES", mainX + 36, cardY + 126, 0xFFFFC15A);
        this.drawString(this.fontRendererObj, "Custom HUD  |  Cosmetics  |  Module GUI", mainX + 36, cardY + 145, 0xFFCBD4E0);

        // Player card with the actual in-game skin renderer
        int profileY = sideY + 94;
        drawRect(profileX, profileY, mainRight - 18, bottom - 22, 0xB9141D29);
        drawRect(profileX, profileY, mainRight - 18, profileY + 2, 0xFFB88643);
        this.drawCenteredString(this.fontRendererObj, "PLAYER PROFILE",
                (profileX + mainRight - 18) / 2, profileY + 15, 0xFFE7B66B);
        int avatarX = (profileX + mainRight - 18) / 2;
        int avatarY = profileY + 105;
        SkinPreviewRenderer.renderPreview(avatarX, avatarY, avatarX, avatarY, false,
                EaglerProfile.getActiveSkinModel(), EaglerProfile.getActiveSkinResourceLocation(), null);
        this.drawCenteredString(this.fontRendererObj, this.mc.getSession().getProfile().getName(),
                avatarX, profileY + 174, 0xFFF2F4F8);
        this.drawCenteredString(this.fontRendererObj, "SKIN LOADED",
                avatarX, profileY + 190, 0xFF54C58A);

        this.drawString(this.fontRendererObj, "KYZXX CLIENT  /  EAGLERCRAFT 1.12.2",
                sideX + 16, bottom - 20, 0xFFB8C1CE);
        this.drawString(this.fontRendererObj, "Resources copyright Mojang AB",
                this.field_193979_N, this.height - 10, -1);

        super.drawScreen(mouseX, mouseY, partialTicks);
        for (int i = 0; i < this.buttonList.size(); ++i) {
            GuiButton button = (GuiButton) this.buttonList.get(i);
            if (button.id == 1 || button.id == 2 || button.id == 14 || button.id == 11
                    || button.id == 12 || button.id == 0 || button.id == 4) {
                this.drawKyzxxMenuButton(button, mouseX, mouseY);
            }
        }
    }

    private void drawKyzxxMenuButton(GuiButton button, int mouseX, int mouseY) {
		if (!button.visible) return;
		boolean hovered = mouseX >= button.xPosition && mouseY >= button.yPosition
				&& mouseX < button.xPosition + button.width && mouseY < button.yPosition + button.height;
		int background = !button.enabled ? 0xB8141922 : (hovered ? 0xF0443825 : 0xE51A222E);
		int accent = !button.enabled ? 0xFF59616D : (hovered ? 0xFFFFD17C : 0xFFB88643);
		int text = !button.enabled ? 0xFF7D8795 : (hovered ? 0xFFFFDFA8 : 0xFFF0F3F8);
		drawRect(button.xPosition, button.yPosition, button.xPosition + button.width,
				button.yPosition + button.height, background);
		drawRect(button.xPosition, button.yPosition, button.xPosition + 3,
				button.yPosition + button.height, accent);
		drawRect(button.xPosition + 4, button.yPosition, button.xPosition + button.width,
				button.yPosition + 1, 0x24FFFFFF);
		this.drawCenteredString(this.fontRendererObj, button.displayString,
				button.xPosition + button.width / 2, button.yPosition + (button.height - 8) / 2, text);
	}

	private void drawKyzxxWinterOverlay() {
		long time = Minecraft.getSystemTime();
		int style = kyzxxBackgroundStyle;
		if (style == 0) {
			for (int n = 0; n < 72; ++n) {
				int x = (int) ((n * 97L + time / (25L + n % 5)) % Math.max(1, this.width));
				int y = (int) ((n * 53L + time / (18L + n % 7)) % Math.max(1, this.height));
				int size = n % 6 == 0 ? 3 : 2;
				drawRect(x, y, x + size, y + size, 0xDDEAF4FF);
			}
			int sx = 28;
			int sy = Math.max(118, this.height - 72);
			drawRect(sx + 4, sy + 24, sx + 22, sy + 42, 0xFFF4F7FA);
			drawRect(sx + 6, sy + 12, sx + 20, sy + 28, 0xFFF4F7FA);
			drawRect(sx + 8, sy + 2, sx + 18, sy + 15, 0xFFF4F7FA);
			drawRect(sx + 7, sy - 3, sx + 19, sy + 2, 0xFFB52D32);
			drawRect(sx + 7, sy - 8, sx + 19, sy - 3, 0xFF20242C);
			drawRect(sx + 8, sy + 17, sx + 20, sy + 20, 0xFFB52D32);
			drawRect(sx + 12, sy + 7, sx + 14, sy + 9, 0xFF252A32);
			drawRect(sx + 12, sy + 14, sx + 14, sy + 16, 0xFF252A32);
			drawRect(sx + 12, sy + 25, sx + 14, sy + 27, 0xFF252A32);
			drawRect(sx + 12, sy + 31, sx + 14, sy + 33, 0xFF252A32);
		} else if (style == 1) {
			for (int n = 0; n < 8; ++n) {
				int y = 35 + n * 18 + (int)(Math.sin((time / 900.0D) + n) * 7.0D);
				drawRect(0, y, this.width, y + 9, n % 2 == 0 ? 0x2635D6A4 : 0x263A83D8);
			}
			for (int n = 0; n < 55; ++n) {
				int x = (n * 137 + 29) % Math.max(1, this.width);
				int y = (n * 71 + 11) % Math.max(1, this.height);
				drawRect(x, y, x + 2, y + 2, 0xBFE8F5FF);
			}
		} else if (style == 2) {
			for (int n = 0; n < 110; ++n) {
				int x = (n * 97 + 17) % Math.max(1, this.width);
				int y = (n * 53 + 9) % Math.max(1, this.height);
				int size = (n % 9 == 0 && (time / 500L) % 2L == 0L) ? 3 : 1;
				drawRect(x, y, x + size, y + size, n % 3 == 0 ? 0xFFE7D9FF : 0xFFC9D9F0);
			}
			drawRect(this.width - 100, 75, this.width - 72, 103, 0x55FFFFFF);
			drawRect(this.width - 94, 69, this.width - 78, 109, 0x55FFFFFF);
		} else if (style == 3) {
			for (int n = 0; n < this.height; n += 5) {
				float t = n / (float)Math.max(1, this.height);
				int red = (int)(35 + 170 * t);
				int green = (int)(30 + 75 * (1.0F - t));
				int blue = (int)(90 + 65 * (1.0F - t));
				drawRect(0, n, this.width, n + 5, 0x33000000 | red << 16 | green << 8 | blue);
			}
			for (int n = 0; n < 34; ++n) {
				int x = (n * 83 + 13) % Math.max(1, this.width);
				int y = (n * 41 + 23) % Math.max(1, this.height);
				drawRect(x, y, x + 2, y + 2, 0xCCFFE0C2);
			}
		} else if (style == 4) {
			for (int x = 0; x < this.width; x += 24) drawRect(x, 0, x + 1, this.height, 0x2238D9C5);
			for (int y = 0; y < this.height; y += 24) drawRect(0, y, this.width, y + 1, 0x2238D9C5);
			for (int n = 0; n < 42; ++n) {
				int x = (n * 67 + (int)(time / 18L)) % Math.max(1, this.width);
				int y = (n * 47 + (int)(time / 27L)) % Math.max(1, this.height);
				drawRect(x, y, x + 3, y + 3, 0xAA45E6D2);
			}
		} else {
			for (int n = 0; n < 90; ++n) {
				int x = (n * 113 + 19) % Math.max(1, this.width);
				int y = (n * 61 + 31) % Math.max(1, this.height);
				int size = n % 7 == 0 ? 4 : 2;
				drawRect(x, y, x + size, y + size, n % 2 == 0 ? 0x887B63D9 : 0x8874A9E8);
			}
		}
		String username = this.mc.getSession().getProfile().getName();
		int nameWidth = Math.min(150, this.fontRendererObj.getStringWidth(username));
		int chipLeft = this.width - nameWidth - 25;
		drawRect(chipLeft, 12, this.width - 10, 35, 0xC810141C);
		this.fontRendererObj.drawString(username, chipLeft + 8, 20, 0xFFFFFFFF);

		// Render the selected Eagler skin using its real model (Steve/Alex/high-poly).
		int avatarX = this.width - 42;
		int avatarY = Math.max(150, this.height - 70);
		drawRect(avatarX - 30, avatarY - 92, avatarX + 30, avatarY + 8, 0x6610141C);
		SkinPreviewRenderer.renderPreview(avatarX, avatarY, avatarX, avatarY, false,
				EaglerProfile.getActiveSkinModel(), EaglerProfile.getActiveSkinResourceLocation(), null);
	}
	/**
	 * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
	 */
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if (mouseX > this.field_193979_N && mouseX < this.field_193979_N + this.field_193978_M
				&& mouseY > this.height - 10 && mouseY < this.height) {
			this.mc.displayGuiScreen(new GuiWinGame(false, null));
		}

	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	public void onGuiClosed() {
	}
}
