// @ExecutionModes({ON_SINGLE_NODE})

import org.freeplane.plugin.script.proxy.Proxy

// Applies appropriate font for displaying code example to specified node.
void applyCodeFont(Proxy.Node nodeToFormat)
{
    def fontName = "Consolas"
	def nodeBackgroundColour = "#ededed"
        
    def nodeStyle = nodeToFormat.style
	def nodeFont = nodeStyle.font
    nodeFont.name = fontName
	nodeStyle.backgroundColorCode = nodeBackgroundColour
}

applyCodeFont(node)