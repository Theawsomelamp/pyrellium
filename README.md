# Pyrellium

Fabric Nether Overhaul Mod

___
### Compatibily

Pyrellium is built with compatibility in mind. Any mod or datapack that makes changes to the nether should theoratically be compatible, though there might be some outliers. Every major nether mod or datapack has been tested and is perfectly compatible.

### Configuration

The configuration file can be found at `/config/pyrellium.json`
```json5 
{
  "comment": "For help with the config, refer to https://github.com/Theawsomelamp/pyrellium#configuration",
  // Configuration options applying to blocks
  "blocks": {
    // Makes the vanilla bone item placeable
    // Has no effect on if the block can generate in the world
    "placeable_bones": true,
    // Makes the vanilla mushroom items placeable on walls
    // Has no effect on if the block can generate in the world
    "placeable_wall_mushrooms": true,
    // The upwards velocity gained by landing on a red bounceshroom
    // 1.0 correlates to about 4 blocks
    "red_bounceshroom_bounce": 1.0,
    // The upwards velocity gained by landing on a brown bounceshroom
    // 0.75 correlates to about 2.5 blocks
    "brown_bounceshroom_bounce": 0.75,
    // Strength of the explosion caused by disturbing a bomb plant
    // Value controls both the size and the damage
    "bomb_plant_explosion_strength": 1.0,
    // The status effect spores should give on contact
    "spores_effect": "minecraft:poison",
    // The time in ticks the status effect given by spores should be applied for
    // 20 ticks is 1 second
    "spores_effect_time": 100
  },
  // Configuration options applying to items
  "items": {
    // Strength of the explosion caused by a thrown bomb flower
    // Value controls both the size and the damage
    "bomb_flower_explosion_strength": 1.0,
    // Determines if players are able to share blocks marked by their opal spyglass to other players
    // Server config has control over this
    "opal_spyglass_block_sharing": true,
    // The amount the effects in 'mushroom_cap_effects' should be multiplied with when wearing the mushroom cap
    // 0.5 means they are halved
    "mushroom_cap_effect_multiplier": 0.5,
    // A list of effects that are affected by the mushroom cap
    "mushroom_cap_effects": [
      "minecraft:poison"
    ],
    // The radius the amethyst and opal arrow deal damage in when shattering.
    "crystal_arrow_shatter_radius": 2,
    // The damage the amethyst and opal arrow deal when shattering.
    "crystal_arrow_shatter_damage": 2
  },
  // Configuration options applying to enchants
  "enchants": {
    "rebound": {
      // Maximum level of the enchantment
      "max_level": 3,
      // Configuration options determining where the enchantment can be found
      // Setting these all to false means the enchantment can't be obtained in survival
      "distribution": {
        // Whether the enchantment can appear as an option on the enchanting table
        "enchant_table_roll": false,
        // Whether the enchantment can be offered on enchanted books sold by librarians
        "librarian_book_trade": true,
        // Whether the enchantment can be found on items or books found in chests/from fishing
        "found_on_enchanted_loot": true
      },
      // The amount of velocity each level of the enchantment adds to the redirected entity
      "added_velocity_per_level": 0.2,
      // The range in blocks that you can redirect entities from
      // Due to fast movement and still needing to hit the hitbox,
      // this number can feel a lot lower than it actually is.
      "rebound_range": 6.0
    }
  },
  // World generation features that apply to multiple biomes
  "globalFeatures": {
    // Raises the nether height to 192
    // Will be ignored when another mod/datapack already changes the nether height
    "raised_nether_height": true,
    // Makes the ceiling of the nether 5 solid layers of bedrock, compared to one. 
    // Intended to discourage nether roof usage.
    "thicker_bedrock_ceiling": true,
    // Whether to generate opal geodes across all nether biomes
    "opal_geodes": true,
    // Whether to generate patches of basalt iron ore in basalt all across the nether
    "basalt_iron_ore": true,
    // Whether to add magma blocks and blackstone rocks to 'colder' nether biomes
    "lava_lake_additions": true,
    // Whether to add fallen logs to crimson and warped forests
    "nether_forest_fallen_logs": true,
    // Whether to add occasional gilded blackstone in biomes with a lot of blackstone
    "gilded_blackstone_patches": true,
    // Whether to generate bones on the floor of soul sand valley
    "soul_sand_valley_bones": true
  },
  // Configuration for the individual biomes pyrellium adds
  // Duplicate options across biomes do the same thing, just applied to their respective biome
  "biomes": {
    "blackstone_springs": {
      // Whether to generate the biome in the world
      "enable_biome": true,
      // Where the biome is placed according to minecraft's noise placement.
      // Vanilla nether biomes only use temperature, humidity, and offset.
      // See https://minecraft.wiki/w/World_generation#Biomes for more information.
      "biome_noise": {
        "temperature": -0.25,
        "humidity": 0.0,
        "continentalness": 0.0,
        "erosion": 0.0,
        "depth": 0.0,
        "weirdness": 0.0,
        "offset": 0.15
      },
      // Whether to generate blackstone rocks in the deltas
      "generate_blackstone_rocks": true
    },
    "burning_grove": {
      "enable_biome": true,
      "biome_noise": {
        "temperature": -0.5,
        "humidity": -0.5,
        "continentalness": 0.0,
        "erosion": 0.0,
        "depth": 0.0,
        "weirdness": 0.0,
        "offset": 0.0
      },
      // Whether to generate patches of pyrolillies on the floor
      // Does not affect the ability for pyrolilies to spawn from bone mealing amber nylium
      "generate_pyrolily": true
    },
    "crystal_forest": {
      "enable_biome": true,
      "biome_noise": {
        "temperature": 0.0,
        "humidity": 0.75,
        "continentalness": 0.0,
        "erosion": 0.0,
        "depth": 0.0,
        "weirdness": 0.0,
        "offset": 0.325
      },
      // Whether to generate patches of amethyst and opal buds on the floor
      "generate_floor_crystals": true
    },
    "frostburn_valley": {
      "enable_biome": true,
      "biome_noise": {
        "temperature": 0.0,
        "humidity": -0.75,
        "continentalness": 0.0,
        "erosion": 0.0,
        "depth": 0.0,
        "weirdness": 0.0,
        "offset": 0.0
      }
    },
    "ghostly_woods": {
      "enable_biome": true,
      "biome_noise": {
        "temperature": 0.5,
        "humidity": -0.5,
        "continentalness": 0.0,
        "erosion": 0.0,
        "depth": 0.0,
        "weirdness": 0.0,
        "offset": 0.0
      },
      // Whether to generate headstones on the floor. Can cause lag.
      "generate_headstones": true
    },
    "infested_valley": {
      "enable_biome": true,
      "biome_noise": {
        "temperature": 0.5,
        "humidity": 0.5,
        "continentalness": 0.0,
        "erosion": 0.0,
        "depth": 0.0,
        "weirdness": 0.0,
        "offset": 0.325
      },
      // Whether to generate cobwebs and silk carpets on the floor.
      "generate_floor_decorations": true,
      // Whether to generate hanging silk from the ceiling.
      "generate_hanging_silk": true
    },
    "monolith_plains": {
      "enable_biome": true,
      "biome_noise": {
        "temperature": 0.75,
        "humidity": 0.0,
        "continentalness": 0.0,
        "erosion": 0.0,
        "depth": 0.0,
        "weirdness": 0.0,
        "offset": 0.0
      },
      // Whether to generate bomb plants across the floor.
      "generate_bomb_flowers": true,
      // Whether to generate the obsidian pillars
      "generate_monolith": true
    },
    "mushroom_wastes": {
      "enable_biome": true,
      "biome_noise": {
        "temperature": -0.5,
        "humidity": 0.5,
        "continentalness": 0.0,
        "erosion": 0.0,
        "depth": 0.0,
        "weirdness": 0.0,
        "offset": 0.2
      },
      // Whether to generate spores in the air.
      "generate_spores": true,
      // Whether to generate any of the wall mushrooms
      "generate_wall_mushrooms": true
    },
    "quartz_caverns": {
      "enable_biome": true,
      "biome_noise": {
        "temperature": 0.25,
        "humidity": 0.25,
        "continentalness": 0.0,
        "erosion": 0.0,
        "depth": 0.0,
        "weirdness": 0.0,
        "offset": 0.25
      },
      // Whether to generate the smooth quartz spikes across the biome.
      "generate_quartz_spikes": true
    }
  }
}
```