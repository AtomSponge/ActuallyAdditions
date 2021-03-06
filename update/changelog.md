# 1.7.10-r8
- Added Lens Mechanic for the Reconstructor
- Added Color Cycling Recipes for wool, stained clay and stained glass for the Reconstructor
- Added Booklet Stand
- Made Drill fire block breaking event
- Fix some blocks not working with the Drill
- Fixed a bug where items would be deleted when exiting the booklet sometimes
- Fixed Solar Panel working even though blocked
- Made text for bugged items less dumb
- Removed german lang file as it's neither getting used nor updated
##### (If you want to localize ActAdd in any language yourself, just make a pull request!)
- Fix Reconstructor not using the right amount of energy for items
- Made Update Checker show after a certain amount of time
- Made Drill harvest slowly instead of not at all when out of energy

# 1.7.10-r7
- Made the Atomic Reconstructor save its power
- Added Automation Tutorial for Atomic Reconstructor
- Added Background Graphic for current chapter's name in booklet
- Added Bookmarks
- Made Magnet Ring Energy balancing better (Might need to reset configs!)
- Made Phantom Device Range being tested better (Check your Phantomfaces, they might not be in range anymore now!)
- Sneaking breaks rings' ability now (Magnet Ring, Growth Ring, Water Removal Ring)
- Made TileEntities update less often to reduce server traffic
- Made ActAdd Fluid Names not clash with other mods' ones
- Made Wild Plants not generate on Mycelium and plop off right away
- Added OreGen and Plant Gen Dimension Blacklist
- Atomic Reconstructor now uses Energy depending on the recipe
- Added misc. reconstructor Recipes
- Added Ethetic Quartz, a decor block and wall, slab & stair variants of them

# 1.7.10-r6
- Added Atomic Reconstructor
- Added Crystals replacing Items like Redstone & Iron Ingots in most crafting recipes
- Seriously fixed Laser Relays disconnecting on their own now, promise! (THE DESPERATION..)
- Fix memory leaks
- Fixed Blocks having an Item Renderer in Inventory
- Updated Smiley Clouds
- New Cheeves! (More to come!)
- Achievement Information for the booklet
- Emerald & Purple Drill
- Added Dungeon Loot

# 1.7.10-r5
- Fixed some unnecessary performance use when transfering power with laser relays
- Fixed Laser Relays disconnecting themselves when
    -they're not edited the entire time a world is loaded
    -a world is loaded wrongly
    -a player leaves an area on servers sometimes
- //Sorry for this update, it's a really important fix though//

# 1.7.10-r4
- Fixed the Drill not being able to mine Obsidian correctly when its harvest level was set higher than 4
- AtomSponge Smiley Cloud
- TB Stairs, Walls, Slabs and Blocks. Unused as of yet, however you can build with them.
- Moved Villager Registring to PostInit to prevent OreDictionary derpups with items getting changed
- Drill + Torches = Instant 3x3 Mining? Nope!
- Misc Crusher Recipes
- Added Word and Character Counter to the booklet. Doesn't do anything, it's just kinda fun
- Update Smiley Cloud Easter Eggs
- Hopefully fixed Laser Relay Connections sometimes breaking when quitting a world or server

# 1.7.10-r3
- Fixed the Heat Collector not outputting power if its internal buffer is full
- Fixed Crash when the manual was open during a world crash or kick
- Removed seasonal booklet page changes
- Changed the way special overhead rendering for special people is handled (preparing for something coming up in the future!)
- Added Black Lotus that spawns in the world
- Added black dye that is craftable from the Black Lotus
- Fixed a potential bug with booklet crafting page crashing if a Crafting Recipe doesn't have inputs
- Added some more smiley clouds

# 1.7.10-r2
- Fix a Crash on Server with the Laser Relay
- Gave the Laser Relay an Energy Transfer Cap
- Added Preview Pictures to the booklet (Thanks to KittyVanCat for help!)
- Fix Slabs
- Fixed Bug with Double Furnace and Double Crusher not crushing if both slots are filled (Thanks to KittyVanCat)
- Remove the Magnetic Miner

# 1.7.10-r1
- Finally out of beta! ~
- Added the Laser Relay, a block that works like a cable to transfer RF, only without the cable but with LAZORZ
- Added Laser Wrench (Texture by MineLoad)
- Moved server-side Player Data to WorldSavedData because ExtendedEntityProperties doesn't work properly
- Made PersistentClientData less write-y
- Added Booklet Priority Colors & Changed Selection Design
- Added Valentine's Day Seasonal
- Chocolate Toast (canitzp)
- New AIOT Textures
- AIOT Integration for SimpleOres 2
- Changed Ball Of Hair Name to "Ball Of Fur", added Booklet Page
- Added Iron Casing & Drill Core
