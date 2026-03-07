package com.chateditor;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ScriptID;
import net.runelite.api.VarClientStr;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.chatbox.ChatboxPanelManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
		name = "Chat Editor",
		description = "Press a hotkey to open a chat text editor that supports copy/paste, ",
		tags = {"chat", "input", "cursor", "text", "edit", "copy", "paste"}
)
public class ChatEditorPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ChatEditorConfig config;

	@Inject
	private KeyManager keyManager;

	@Inject
	private ChatboxPanelManager chatboxPanelManager;

	@Inject
	private ChatEditorKeyListener keyListener;

	@Provides
	ChatEditorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ChatEditorConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		keyManager.registerKeyListener(keyListener);
		log.info("Chat Editor started");
	}

	@Override
	protected void shutDown() throws Exception
	{
		keyManager.unregisterKeyListener(keyListener);
		log.info("Chat Editor stopped");
	}

	public void openTextEditMode()
	{
		String currentText = client.getVarcStrValue(VarClientStr.CHATBOX_TYPED_TEXT);
		if (currentText == null)
		{
			currentText = "";
		}

		chatboxPanelManager.openTextInput("Edit Message")
			.value(currentText)
			.onDone((newText) ->
			{
				clientThread.invoke(() ->
				{
					if (newText != null && !newText.isEmpty())
					{
						if (config.autoSendMessage())
						{
							// Auto-send the message using CHAT_SEND script
							// Parameters: modes, clan type, use target, set target
							client.runScript(ScriptID.CHAT_SEND, newText, 0, 0, 0, 0);
							// reset chatbox text to be empty
							client.setVarcStrValue(VarClientStr.CHATBOX_TYPED_TEXT, "");
						}
						else
						{
							// Place the edited text back in the chatbox input
							client.setVarcStrValue(VarClientStr.CHATBOX_TYPED_TEXT, newText);
						}
					}
				});
			})
			.build();
	}
}
