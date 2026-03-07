package com.chateditor;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

@ConfigGroup("chateditor")
public interface ChatEditorConfig extends Config
{
	@ConfigItem(
			keyName = "editHotkey",
			name = "Edit Chat Hotkey",
			description = "Hotkey to open the editor"
	)
	default Keybind editHotkey()
	{
		return new Keybind(KeyEvent.VK_ENTER, InputEvent.CTRL_DOWN_MASK);
	}

	@ConfigItem(
			keyName = "autoSendMessage",
			name = "Auto Send Message",
			description = "Automatically send the message after editing instead of placing it back in the chatbox"
	)
	default boolean autoSendMessage()
	{
		return true;
	}
}
