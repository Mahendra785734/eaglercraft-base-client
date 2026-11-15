package net.minecraft.client.gui;

import java.io.IOException;

import net.lax1dude.eaglercraft.PauseMenuCustomizeState;
import net.lax1dude.eaglercraft.minecraft.GuiButtonWithStupidIcons;
import net.lax1dude.eaglercraft.notifications.GuiButtonNotifBell;
import net.lax1dude.eaglercraft.notifications.GuiScreenNotifications;
import net.lax1dude.eaglercraft.opengl.GlStateManager;
import net.lax1dude.eaglercraft.sp.SingleplayerServerController;
import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.Mouse;
import net.lax1dude.eaglercraft.webview.GuiScreenPhishingWarning;
import net.lax1dude.eaglercraft.webview.GuiScreenRecieveServerInfo;
import net.lax1dude.eaglercraft.webview.GuiScreenServerInfo;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.gui.advancements.GuiScreenAdvancements;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;

public class GuiIngameMenu extends GuiScreen {

	boolean hasSentAutoSave = !SingleplayerServerController.isWorldRunning();

	private GuiButtonNotifBell notifBellButton;
	
	/**
	 * Adds the buttons (and other controls) to the screen in question. Called when
	 * the GUI is displayed and when the window resizes, the buttonList is cleared
	 * beforehand.
	 */
	public void initGui() {
		this.buttonList.clear();
		int i = -16;
		int j = 98;
		this.buttonList.add(new GuiButtonWithStupidIcons(1, this.width / 2 - 100, this.height / 4 + 120 + -16, I18n.format("menu.returnToMenu"), PauseMenuCustomizeState.icon_disconnect_L,
				PauseMenuCustomizeState.icon_disconnect_L_aspect, PauseMenuCustomizeState.icon_disconnect_R,
				PauseMenuCustomizeState.icon_disconnect_R_aspect));

		if (!this.mc.isIntegratedServerRunning()) {
			(this.buttonList.get(0)).displayString = I18n.format("menu.disconnect");
			if (this.mc.player != null && this.mc.player.connection.getEaglerMessageProtocol().ver >= 4) {
				this.buttonList.add(notifBellButton = new GuiButtonNotifBell(11, width - 22, height - 22));
				notifBellButton.setUnread(mc.player.connection.getNotifManager().getUnread());
			}
		}

		this.buttonList.add(new GuiButtonWithStupidIcons(4, this.width / 2 - 100, this.height / 4 + 24 + -16, I18n.format("menu.returnToGame"), PauseMenuCustomizeState.icon_backToGame_L,
				PauseMenuCustomizeState.icon_backToGame_L_aspect, PauseMenuCustomizeState.icon_backToGame_R,
				PauseMenuCustomizeState.icon_backToGame_R_aspect));
		
		this.buttonList.add(new GuiButtonWithStupidIcons(0, this.width / 2 - 100, this.height / 4 + 96 + -16, 98, 20,
				I18n.format("menu.options"), PauseMenuCustomizeState.icon_options_L,
				PauseMenuCustomizeState.icon_options_L_aspect, PauseMenuCustomizeState.icon_options_R,
				PauseMenuCustomizeState.icon_options_R_aspect));
		
		GuiButton lanButton = this.addButton(new GuiButtonWithStupidIcons(7, this.width / 2 + 2, this.height / 4 + 96 + -16, 98, 20,
				I18n.format("menu.shareToLan"), PauseMenuCustomizeState.icon_discord_L, PauseMenuCustomizeState.icon_discord_L_aspect,
				PauseMenuCustomizeState.icon_discord_R, PauseMenuCustomizeState.icon_discord_R_aspect));
		lanButton.enabled = false;
		
		this.buttonList.add(new GuiButtonWithStupidIcons(5, this.width / 2 - 100, this.height / 4 + 48 + -16, 98, 20,
				I18n.format("gui.advancements"), PauseMenuCustomizeState.icon_achievements_L,
				PauseMenuCustomizeState.icon_achievements_L_aspect, PauseMenuCustomizeState.icon_achievements_R,
				PauseMenuCustomizeState.icon_achievements_R_aspect));
		
		this.buttonList.add(
				new GuiButtonWithStupidIcons(6, this.width / 2 + 2, this.height / 4 + 48 + -16, 98, 20, I18n.format("gui.stats"), PauseMenuCustomizeState.icon_statistics_L,
						PauseMenuCustomizeState.icon_statistics_L_aspect, PauseMenuCustomizeState.icon_statistics_R,
						PauseMenuCustomizeState.icon_statistics_R_aspect));
		
		if (PauseMenuCustomizeState.discordButtonMode != PauseMenuCustomizeState.DISCORD_MODE_NONE) {
			lanButton.enabled = true;
			lanButton.id = 8;
			lanButton.displayString = "" + PauseMenuCustomizeState.discordButtonText;
		}
		
		if (PauseMenuCustomizeState.serverInfoMode != PauseMenuCustomizeState.DISCORD_MODE_NONE) {
			this.buttonList.add(new GuiButtonWithStupidIcons(9, this.width / 2 - 100, this.height / 4 + 72 + (byte)-16,
					PauseMenuCustomizeState.serverInfoButtonText, PauseMenuCustomizeState.icon_serverInfo_L,
					PauseMenuCustomizeState.icon_serverInfo_L_aspect, PauseMenuCustomizeState.icon_serverInfo_R,
					PauseMenuCustomizeState.icon_serverInfo_R_aspect));
		}
		
		if (!hasSentAutoSave) {
			hasSentAutoSave = true;
			SingleplayerServerController.autoSave();
		}
	}

	/**
	 * Called by the controls from the buttonList when activated. (Mouse pressed for
	 * buttons)
	 */
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case 0:
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
			break;

		case 1:
			boolean flag = this.mc.isIntegratedServerRunning();
			button.enabled = false;
			this.mc.world.sendQuittingDisconnectingPacket();
			this.mc.loadWorld((WorldClient) null);

			if (flag) {
				this.mc.shutdownIntegratedServer(new GuiMainMenu());
			} else {
				this.mc.shutdownIntegratedServer(new GuiMultiplayer(new GuiMainMenu()));
			}

		case 2:
		case 3:
		default:
			break;

		case 4:
			this.mc.displayGuiScreen((GuiScreen) null);
			this.mc.setIngameFocus();
			break;

		case 5:
			this.mc.displayGuiScreen(new GuiScreenAdvancements(this.mc.player.connection.func_191982_f()));
			break;

		case 6:
			this.mc.displayGuiScreen(new GuiStats(this, this.mc.player.getStatFileWriter()));
			break;
		case 7:
			throw new UnsupportedOperationException("LAN is not supported yet you eagler!");
		case 8:
			if (PauseMenuCustomizeState.discordButtonMode == PauseMenuCustomizeState.DISCORD_MODE_INVITE_URL
					&& PauseMenuCustomizeState.discordInviteURL != null) {
				EagRuntime.openLink(PauseMenuCustomizeState.discordInviteURL);
			}
			break;
		case 9:
			switch (PauseMenuCustomizeState.serverInfoMode) {
			case PauseMenuCustomizeState.SERVER_INFO_MODE_EXTERNAL_URL:
				if (PauseMenuCustomizeState.serverInfoURL != null) {
					EagRuntime.openLink(PauseMenuCustomizeState.serverInfoURL);
				}
				break;
			case PauseMenuCustomizeState.SERVER_INFO_MODE_SHOW_EMBED_OVER_HTTP:
				if (PauseMenuCustomizeState.serverInfoURL != null) {
					GuiScreen screen = GuiScreenServerInfo.createForCurrentState(this,
							PauseMenuCustomizeState.serverInfoURL);
					if (!this.mc.gameSettings.hasHiddenPhishWarning && !GuiScreenPhishingWarning.hasShownMessage) {
						screen = new GuiScreenPhishingWarning(screen);
					}
					this.mc.displayGuiScreen(screen);
				}
				break;
			case PauseMenuCustomizeState.SERVER_INFO_MODE_SHOW_EMBED_OVER_WS:
				if (PauseMenuCustomizeState.serverInfoHash != null) {
					GuiScreen screen = new GuiScreenRecieveServerInfo(this, PauseMenuCustomizeState.serverInfoHash);
					if (!this.mc.gameSettings.hasHiddenPhishWarning && !GuiScreenPhishingWarning.hasShownMessage) {
						screen = new GuiScreenPhishingWarning(screen);
					}
					this.mc.displayGuiScreen(screen);
				}
				break;
			default:
				break;
			}
			break;
		case 11:
			this.mc.displayGuiScreen(new GuiScreenNotifications(this));
			break;
		}
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		super.updateScreen();
		if (Mouse.isActuallyGrabbed()) {
			Mouse.setGrabbed(false);
		}
		if (notifBellButton != null && mc.player != null) {
			notifBellButton.setUnread(mc.player.connection.getNotifManager().getUnread());
		}
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawGradientRect(0, 0, this.width, this.height, 0x7706101A, 0xB8070D16);

        int panelWidth = Math.min(292, Math.max(244, this.width - 16));
        int panelLeft = (this.width - panelWidth) / 2;
        int panelRight = panelLeft + panelWidth;
        int panelTop = 18;
        int panelBottom = Math.min(this.height - 18, this.height / 4 + 156);

        this.drawGradientRect(panelLeft, panelTop, panelRight, panelBottom, 0xED101A28, 0xE90A1420);
        this.drawRect(panelLeft, panelTop, panelRight, panelTop + 3, 0xFF36B9E8);
        this.drawRect(panelLeft, panelTop + 3, panelLeft + 1, panelBottom, 0x7736B9E8);
        this.drawRect(panelRight - 1, panelTop + 3, panelRight, panelBottom, 0x5536B9E8);
        this.fontRendererObj.drawStringWithShadow("KYZXX CLIENT", panelLeft + 18, 34, 0xFFFFFFFF);
        this.fontRendererObj.drawStringWithShadow("PAUSED", panelLeft + 18, 50, 0xFF49C8F2);
        this.fontRendererObj.drawString("GAME MENU", panelLeft + 18, 70, 0xFF8196AA);
        this.drawRect(panelLeft + 18, 84, panelRight - 18, 85, 0x664E91B0);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
	
	@Override
	protected boolean isPartOfPauseMenu() {
		return true;
	}
}
