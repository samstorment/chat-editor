package com.chateditor;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.VarClientStr;
import net.runelite.client.input.KeyListener;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.event.KeyEvent;

/**
 * Listens for the configured hotkey and opens the
 * text edit mode using RuneLite's built-in text input panel.
 */
@Slf4j
@Singleton
public class ChatEditorKeyListener implements KeyListener
{
    @Inject private Client client;
    @Inject private ChatEditorPlugin plugin;
    @Inject private ChatEditorConfig config;

    @Override
    public void keyTyped(KeyEvent e)
    {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (!chatboxExists()) return;

        // Check if the pressed key matches the configured hotkey
        if (config.editHotkey().matches(e))
        {
            plugin.openTextEditMode();
            e.consume();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // Not used
    }

    private boolean chatboxExists()
    {
        try
        {
            var val = client.getVarcStrValue(VarClientStr.CHATBOX_TYPED_TEXT);
            return val != null;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
