// @ExecutionModes({ON_SINGLE_NODE})

import org.freeplane.features.edge.EdgeStyle
import org.freeplane.features.nodestyle.NodeStyleModel
import org.freeplane.features.nodestyle.mindmapmode.MNodeStyleController
import org.freeplane.features.mode.Controller
import org.freeplane.features.mode.ModeController
import org.freeplane.features.nodestyle.NodeStyleController
import org.freeplane.plugin.script.proxy.Proxy

import org.freeplane.features.cloud.CloudController
import org.freeplane.features.cloud.mindmapmode.MCloudController

// Simple structures and enums:

class ColorSet
{
    String title
    String backgroundColorCode
    String textColorCode
    String edgeColorCode
}

enum ColorSequence
{
    COLUMNS,
    WHEEL
}

class FormatSelection
{
	def colorPalette = []
	boolean applyLevelStyles
	// Color indices are zero-based.
	int rootColorIndex
	int topRightNodeColorIndex
	ColorSequence colorSequence
	boolean addBranchClouds
	boolean clearSubClouds
}

// Helper classes that perform specific formatting actions:

class NodeShape
{
    static String getShapeStyle(Proxy.Node nodeToRead)
    {
        final ModeController modeController = Controller.getCurrentModeController()
        final MNodeStyleController styleController = 
            (MNodeStyleController) modeController.getExtension(NodeStyleController.class);
        return styleController.getShape(nodeToRead.delegate)
    }

    static void setShapeStyleBubble (Proxy.Node nodeToSet)
    {
        setShapeStyle(nodeToSet, NodeStyleModel.STYLE_BUBBLE)
    }

    static void setShapeStyleFork(Proxy.Node nodeToSet)
    {
        setShapeStyle(nodeToSet, NodeStyleModel.STYLE_FORK)
    }

    static void resetShapeStyle(Proxy.Node nodeToSet)
    {
        setShapeStyle(nodeToSet, null)
    }
        
    static void setShapeStyle(Proxy.Node nodeToSet, String style)
    {
        final ModeController modeController = Controller.getCurrentModeController()
        final MNodeStyleController styleController = 
            (MNodeStyleController) modeController.getExtension(NodeStyleController.class);
        styleController.setShape(nodeToSet.delegate, style)
    }
}

class NodeCloud
{
	static void clearCloud(Proxy.Node nodeToFormat)
	{
		setCloud(nodeToFormat, false)
	}
	
	static void setCloud(Proxy.Node nodeToFormat)
	{
		setCloud(nodeToFormat, true)
	}

	// Set to true to add a cloud, false to remove a cloud from selected node.
	//	Is idempotent: Attempting to add a cloud if one already exists, or 
	//	remove one if no cloud exists, has no effect (doesn't add second 
	//	cloud and causes no error).	
	static void setCloud (Proxy.Node nodeToFormat, enableCloud)
    {
		final MCloudController cloudController = 
			(MCloudController) CloudController.getController()
		cloudController.setCloud(nodeToFormat.delegate, enableCloud)
    }
}

class NodeFormat
{
	static void clearNodeFormatting(Proxy.Node nodeToFormat)
	{
		NodeShape.resetShapeStyle(nodeToFormat)
		
		def nodeStyle = nodeToFormat.style
		def nodeEdge = nodeStyle.edge
		def nodeFont = nodeStyle.font
		
		nodeStyle.backgroundColorCode = null
		nodeStyle.floating = false
		nodeStyle.maxNodeWidth = -1
		nodeStyle.minNodeWidth = -1
		nodeStyle.name = null
		nodeStyle.textColorCode = null

		nodeEdge.colorCode = null
		nodeEdge.width = -1

		nodeFont.resetBold()
		nodeFont.resetItalic()
		nodeFont.resetName()
		nodeFont.resetSize()
	}
}

// Colour palettes:

def colorPalette0 = [new ColorSet(title:"brown", backgroundColorCode:"#cc9900", textColorCode:"#000000", 
                                edgeColorCode:"#8b4513"), 
                  new ColorSet(title:"red", backgroundColorCode:"#f97777", textColorCode:"#000000", 
                                edgeColorCode:"#b22222"), 
                  new ColorSet(title:"orange", backgroundColorCode:"#ff9900", textColorCode:"#000000", 
                                edgeColorCode:"#d86800"), 
                  new ColorSet(title:"yellow", backgroundColorCode:"#ffff66", textColorCode:"#000000", 
                                edgeColorCode:"#a0a000"), 
                  new ColorSet(title:"green", backgroundColorCode:"#66cc00", textColorCode:"#000000", 
                                edgeColorCode:"#006400"), 
                  new ColorSet(title:"turquoise", backgroundColorCode:"#66ffcc", textColorCode:"#000000", 
                                edgeColorCode:"#009999"), 
                  new ColorSet(title:"blue", backgroundColorCode:"#0099cc", textColorCode:"#000000", 
                                edgeColorCode:"#005080"), 
                  new ColorSet(title:"purple", backgroundColorCode:"#8a8aff", textColorCode:"#000000", 
                                edgeColorCode:"#5c00b8"), 
                  new ColorSet(title:"magenta", backgroundColorCode:"#ff8aff", textColorCode:"#000000", 
                                edgeColorCode:"#b800b8"), 
                  new ColorSet(title:"grey", backgroundColorCode:"#cccccc", textColorCode:"#000000", 
                                edgeColorCode:"#696969"), 
                  
                  new ColorSet(title:"dark brown", backgroundColorCode:"#8b4513", textColorCode:"#ffffff", 
                                edgeColorCode:"#cc9900"), 
                  new ColorSet(title:"dark red", backgroundColorCode:"#b22222", textColorCode:"#ffffff", 
                                edgeColorCode:"#f97777"), 
                  new ColorSet(title:"dark orange", backgroundColorCode:"#d86800", textColorCode:"#ffffff", 
                                edgeColorCode:"#ff9900"), 
                  new ColorSet(title:"dark yellow", backgroundColorCode:"#a0a000", textColorCode:"#ffffff", 
                                edgeColorCode:"#d0d050"), 
                  new ColorSet(title:"dark green", backgroundColorCode:"#006400", textColorCode:"#ffffff", 
                                edgeColorCode:"#66cc00"), 
                  new ColorSet(title:"dark turquoise", backgroundColorCode:"#009999", textColorCode:"#ffffff", 
                                edgeColorCode:"#66ffcc"), 
                  new ColorSet(title:"dark blue", backgroundColorCode:"#005080", textColorCode:"#ffffff", 
                                edgeColorCode:"#0099cc"), 
                  new ColorSet(title:"dark purple", backgroundColorCode:"#5c00b8", textColorCode:"#ffffff", 
                                edgeColorCode:"#8a8aff"), 
                  new ColorSet(title:"dark magenta", backgroundColorCode:"#b800b8", textColorCode:"#ffffff", 
                                edgeColorCode:"#ff8aff"), 
                  new ColorSet(title:"dark grey", backgroundColorCode:"#696969", textColorCode:"#ffffff", 
                                edgeColorCode:"#aaaaaa")]

def colorPalette1 = [new ColorSet(title:"purple", backgroundColorCode:"#c898ff", textColorCode:"#000000", 
                                edgeColorCode:"#815fa9"), 
                  new ColorSet(title:"blue-purple", backgroundColorCode:"#cbcbff", textColorCode:"#000000", 
                                edgeColorCode:"#8080b3"), 
                  new ColorSet(title:"weak blue", backgroundColorCode:"#b5d7ff", textColorCode:"#000000", 
                                edgeColorCode:"#6198bc"), 
                  new ColorSet(title:"strong blue", backgroundColorCode:"#6fdcff", textColorCode:"#000000", 
                                edgeColorCode:"#49a69f"), 
                  new ColorSet(title:"weak green", backgroundColorCode:"#a2ffd0", textColorCode:"#000000", 
                                edgeColorCode:"#53bb6b"), 
                  new ColorSet(title:"dull green", backgroundColorCode:"#aedda5", textColorCode:"#000000", 
                                edgeColorCode:"#679a67"), 
                  new ColorSet(title:"lime green", backgroundColorCode:"#BDFF78", textColorCode:"#000000", 
                                edgeColorCode:"#8aaf3e"), 
                  new ColorSet(title:"yellow", backgroundColorCode:"#ffff8a", textColorCode:"#000000", 
                                edgeColorCode:"#bbb75e"), 
                  new ColorSet(title:"weak orange", backgroundColorCode:"#fdcd92", textColorCode:"#000000", 
                                edgeColorCode:"#f09a46"), 
                  new ColorSet(title:"weak red", backgroundColorCode:"#fb9a98", textColorCode:"#000000", 
                                edgeColorCode:"#ee4444"), 
                  new ColorSet(title:"pink", backgroundColorCode:"#ff8ebc", textColorCode:"#000000", 
                                edgeColorCode:"#ed4497"), 
                  new ColorSet(title:"magenta", backgroundColorCode:"#fa98ff", textColorCode:"#000000", 
                                edgeColorCode:"#b863a7"), 
                                
                  new ColorSet(title:"dark purple", backgroundColorCode:"#815fa9", textColorCode:"#ffffff", 
                                edgeColorCode:"#c898ff"), 
                  new ColorSet(title:"dark blue-purple", backgroundColorCode:"#8080b3", textColorCode:"#ffffff", 
                                edgeColorCode:"#cbcbff"), 
                  new ColorSet(title:"dark weak blue", backgroundColorCode:"#6198bc", textColorCode:"#ffffff", 
                                edgeColorCode:"#b5d7ff"), 
                  new ColorSet(title:"dark strong blue", backgroundColorCode:"#49a69f", textColorCode:"#ffffff", 
                                edgeColorCode:"#6fdcff"), 
                  new ColorSet(title:"dark weak green", backgroundColorCode:"#53bb6b", textColorCode:"#ffffff", 
                                edgeColorCode:"#47ff90"), 
                  new ColorSet(title:"dark dull green", backgroundColorCode:"#679a67", textColorCode:"#ffffff", 
                                edgeColorCode:"#aedda5"), 
                  new ColorSet(title:"dark lime green", backgroundColorCode:"#8aaf3e", textColorCode:"#ffffff", 
                                edgeColorCode:"#aedd68"), 
                  new ColorSet(title:"dark yellow", backgroundColorCode:"#bbb75e", textColorCode:"#ffffff", 
                                edgeColorCode:"#d0d050"), 
                  new ColorSet(title:"dark orange", backgroundColorCode:"#f09a46", textColorCode:"#ffffff", 
                                edgeColorCode:"#fdcd92"), 
                  new ColorSet(title:"dark red", backgroundColorCode:"#ee4444", textColorCode:"#ffffff", 
                                edgeColorCode:"#fb9a98"), 
                  new ColorSet(title:"dark pink", backgroundColorCode:"#ed4497", textColorCode:"#ffffff", 
                                edgeColorCode:"#ff8ebc"), 
                  new ColorSet(title:"dark magenta", backgroundColorCode:"#b863a7", textColorCode:"#ffffff", 
                                edgeColorCode:"#fa98ff")]

def colorPalettes = ["Resistor Rainbow":colorPalette0, "Pastels":colorPalette1]

// Invariant colours:

def forkTextColorCode = "#000000"
def forkBackgroundColorCode = null	// Transparent
def mapBackgroundColorCode = "#ffffdb"
def rootTextColorCode = "#000000"

// Formatting functions:

// Applies appropriate style to a node based on its level.
void applyLevelStyle(Proxy.Node nodeToFormat)
{
    NodeFormat.clearNodeFormatting(nodeToFormat)
    
    boolean countHidden = true
    int nodeLevel = nodeToFormat.getNodeLevel(countHidden)
    
    def fontName = "SansSerif"
    def fontSize = 10
    def fontIsBold = false
    def nodeShape = NodeStyleModel.STYLE_FORK
	def edgeType = EdgeStyle.EDGESTYLE_BEZIER
	def edgeWidth = 1
    switch(nodeLevel)
    {
        case 0: // root
            fontSize = 16
            fontIsBold = true
			edgeType = EdgeStyle.EDGESTYLE_SHARP_BEZIER
			edgeWidth = 4
            break
        case 1: 
            fontSize = 14
            fontIsBold = true
            nodeShape = NodeStyleModel.STYLE_BUBBLE
			edgeType = EdgeStyle.EDGESTYLE_SHARP_BEZIER
			edgeWidth = 4
            break
        case 2: 
            fontSize = 12
            fontIsBold = true
            nodeShape = NodeStyleModel.STYLE_BUBBLE
			edgeType = EdgeStyle.EDGESTYLE_BEZIER
			edgeWidth = 2
            break
        case 3: 
            fontSize = 12
            fontIsBold = false
            nodeShape = NodeStyleModel.STYLE_BUBBLE
			edgeType = EdgeStyle.EDGESTYLE_BEZIER
			edgeWidth = 2
            break
        case 4: 
            fontSize = 10
            fontIsBold = false
            nodeShape = NodeStyleModel.STYLE_BUBBLE
			edgeType = EdgeStyle.EDGESTYLE_BEZIER
			edgeWidth = 2
            break
        default: 
            fontSize = 10
            fontIsBold = false
            nodeShape = NodeStyleModel.STYLE_FORK
			edgeType = EdgeStyle.EDGESTYLE_BEZIER
			edgeWidth = 2
            break        
    }
    
    def nodeFont = nodeToFormat.style.font
    nodeFont.name = fontName 
    nodeFont.size = fontSize 
    nodeFont.bold = fontIsBold
    
    NodeShape.setShapeStyle(nodeToFormat, nodeShape)
	
	def nodeEdge = nodeToFormat.style.edge
	nodeEdge.type = edgeType
	nodeEdge.width = edgeWidth
}

// Determines the colour offset of the top-most branch on the left side of 
//	the root when the colorSequence is set to COLUMNS.  
//	Remarks: The offset is from the colour index of the top right branch.  The 
// 	palettes have been arranged into two equally-sized sub-palettes, one of 
//	lighter colours and the other of corresponding darker colours.  When 
//	colourSequence COLUMNS is selected then the colours of matching branches 
//	on the left and right sides of the root will be complementary.  eg If the 
//	second branch down on the right is red, then the second branch down on the 
//	left will be dark red.  If the fifth branch down on the right is dark 
//	yellow then the fifth branch down on the left will be yellow.
int getLeftNodesColumnsColorOffset(int numberColors)
{
    def isEven = (numberColors % 2 == 0)
    if (isEven)
    {
        return numberColors / 2
    }
    
    return (int)(numberColors / 2) + 1
}

// Sets the colour of the root node.
void setRootColor(rootNode, colorPalette, rootColorIndex, 
    rootTextColorCode)
{
    def colorSet = colorPalette[rootColorIndex]
    rootNode.style.edge.colorCode = colorSet.edgeColorCode
    rootNode.style.backgroundColorCode = colorSet.backgroundColorCode
    rootNode.style.textColorCode = rootTextColorCode
}

// Sets or clears a cloud on a branch (ie top-level node) depending on whether 
//	the branch is an odd or even numbered one (counting from the top on either 
//	side of the root node.  Top-most branch on either side will not have a 
//	cloud, second branch down will have a cloud, and so on).
void setBranchCloud(topLevelNode, addBranchClouds, currentNodeCount)
{
    if (!addBranchClouds)
	{
		return
	}
	
	def isEven = (currentNodeCount % 2 == 0)
	
	// Add clouds only to the odd numbered nodes.
	if (isEven)
	{
		NodeCloud.clearCloud(topLevelNode)
	}
	else
	{
		NodeCloud.setCloud(topLevelNode)
	}
}

// User's selection:

FormatSelection formatSelection = new FormatSelection(
	colorPalette:colorPalettes["Pastels"], applyLevelStyles:true, 
	rootColorIndex:7, topRightNodeColorIndex:1, 
	colorSequence:ColorSequence.WHEEL, 
	addBranchClouds:true, clearSubClouds:true)

// Apply selected formatting:

def numberColors = formatSelection.colorPalette.size

formatSelection.rootColorIndex = formatSelection.rootColorIndex % numberColors
formatSelection.topRightNodeColorIndex = formatSelection.topRightNodeColorIndex % numberColors

def root = node.map.root
def level1Nodes = root.children
def numberLevel1Nodes = level1Nodes.size

if (formatSelection.applyLevelStyles)
{
    applyLevelStyle(root)
}

setRootColor(root, formatSelection.colorPalette, 
	formatSelection.rootColorIndex, rootTextColorCode)

// Order that nodes are returned in may jump from right side of root to left 
//  and back again.  However, on each side the nodes are always returned in 
//  descending order, from top to bottom.  So keep track of the node counts 
//  on each side separately.
def rightNodeRawColorIndex = 0
def leftNodeRawColorIndex = 0
if (formatSelection.colorSequence == ColorSequence.COLUMNS)
{
    rightNodeRawColorIndex = formatSelection.topRightNodeColorIndex
    leftNodeRawColorIndex = formatSelection.topRightNodeColorIndex + getLeftNodesColumnsColorOffset(numberColors)
}
else
{
    rightNodeRawColorIndex = formatSelection.topRightNodeColorIndex
    leftNodeRawColorIndex = rightNodeRawColorIndex + numberLevel1Nodes + 1
}

// Increment colour indices at start of each loop so step back 1 before start 
//	to ensure they start at the correct index.
rightNodeRawColorIndex--
leftNodeRawColorIndex--

def leftNodeCount = -1
def rightNodeCount = -1
def currentNodeCount = -1
def colourIndex = -1
for (topLevelNode in level1Nodes)
{
    if (topLevelNode.left)
    {
        if (formatSelection.colorSequence == ColorSequence.COLUMNS)
        {
            leftNodeRawColorIndex++
        }
        else
        {
            leftNodeRawColorIndex--
        }        
        colourIndex = leftNodeRawColorIndex % numberColors
		leftNodeCount++
		currentNodeCount = leftNodeCount
    }
    else
    {
        rightNodeRawColorIndex++
        colourIndex = rightNodeRawColorIndex % numberColors
		rightNodeCount++
		currentNodeCount = rightNodeCount
    }
    
	setBranchCloud(topLevelNode, formatSelection.addBranchClouds, currentNodeCount)
	
    def colorSet = formatSelection.colorPalette[colourIndex]
    
    def parentNodeShapes = [(root.id):NodeStyleModel.STYLE_FORK]
    branchNodes = topLevelNode.findAll()
    for (branchNode in branchNodes)
    {
        if (formatSelection.applyLevelStyles)
        {
            applyLevelStyle(branchNode)
        }
		
		if (formatSelection.clearSubClouds && branchNode != topLevelNode)
		{
			NodeCloud.clearCloud(branchNode)
		}
        
        def currentNodeShape = NodeShape.getShapeStyle(branchNode)
        if (currentNodeShape == NodeStyleModel.SHAPE_AS_PARENT)
        {
           currentNodeShape = parentNodeShapes[branchNode.parent.id]
        }
        // COMBINED style: Bubble when folded, fork when unfolded.
        // Use FORK formatting so when folded will be map background, black 
        // text and a coloured border around the bubble.
        else if (currentNodeShape == NodeStyleModel.SHAPE_COMBINED)
        {
           currentNodeShape = NodeStyleModel.STYLE_FORK
        }
        
        branchNode.style.edge.colorCode = colorSet.edgeColorCode
        if (currentNodeShape == NodeStyleModel.STYLE_BUBBLE)
        {
            branchNode.style.backgroundColorCode = colorSet.backgroundColorCode
            branchNode.style.textColorCode = colorSet.textColorCode
        }
        else
        {
            branchNode.style.backgroundColorCode = forkBackgroundColorCode
            branchNode.style.textColorCode = forkTextColorCode
        }
        
        parentNodeShapes[branchNode.id] = currentNodeShape
    }
}

root.map.backgroundColorCode = mapBackgroundColorCode