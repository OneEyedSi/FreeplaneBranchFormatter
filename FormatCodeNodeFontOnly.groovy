// @ExecutionModes({ON_SINGLE_NODE})

import org.freeplane.plugin.script.proxy.Proxy

// Applies appropriate font for displaying code example to specified node.
void applyCodeFont(Proxy.Node nodeToFormat)
{
    def fontName = "Consolas"
        
    def nodeFont = nodeToFormat.style.font
    nodeFont.name = fontName
}

applyCodeFont(node)