# Chat Editor

A plugin to make OSRS chat text editing better.

The existing chat input is very bare bones. By default, you can't:

- Select text
- Copy/Cut/Paste text
- Cursor through text with arrow keys
- Use other common shortcuts like <kbd>ctrl + arrow</kbd>,  <kbd>shift + arrow</kbd>, <kbd>ctrl + backspace</kbd>, etc.

The goal is to provide those features so you can edit chat messages in a way that feels closer to an input element you'd find on a native desktop app or on the web.

## Current Implementation

The plugin uses the same chatbox input that pops up when you go to add a note to your marked tile. This input is pretty broken if you try to use the "other common shortcuts" mentioned above, but it's better than nothing. 

I'd like to avoid opening a separate input box altogether and have it "just work" where the current input is, but that's a much steeper task. There would probably need to be a separate input mode similar to the key remapping plugin's "Press enter to chat..." 
