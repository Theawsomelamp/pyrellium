package com.lankaster.pyrellium.config;

import com.google.gson.*;
import com.lankaster.pyrellium.Pyrellium;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Pair;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static final List<Pair<String, String>> ENTRIES = new ArrayList<>();

    public static String biomesToJson(Config.Biomes biomes) {
        return gson.toJson(biomes);
    }

    public static void load(Path path) {
        File config = new File(path.toString());
        if (!Files.isRegularFile(path)) {
            Path oldPath = FabricLoader.getInstance().getConfigDir().resolve("pyrellium.json");
            if (Files.isRegularFile(oldPath)) {
                File oldFile = new File(oldPath.toString());
                oldFile.delete();
            }
            write(path);
        }

        try {
            String content = FileUtils.readFileToString(config, StandardCharsets.UTF_8);
            Config.INSTANCE = gson.fromJson(parseJson(content), Config.class);
        } catch (JsonParseException e) {
            Pyrellium.LOGGER.error("Couldn't parse config file, resetting to default config");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        write(path);
    }

    private static void write(Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(appendComments(gson.toJson(Config.INSTANCE)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String parseJson(String text) {
        StringBuilder result = new StringBuilder();

        for (String line : text.split("\n")) {
            if (!line.strip().startsWith("//"))
                result.append("\n").append(line);
        }

        return result.toString();
    }

    public static String appendComments(String text) {
        StringBuilder result = new StringBuilder();

        for (String line : text.split("\n")) {
            String[] txt = line.split(":");
            for (Pair<String, String> entry : entries()) {
                if (entry.getLeft().equals(txt[0].replaceAll("\"", "").strip())) {
                    result.append("\n").append(entry.getRight());
                }
            }
            result.append("\n").append(line);
        }

        return result.toString();
    }

    public static void registerEntries() {
        ENTRIES.add(new Pair<>("blocks", "  // Configuration options applying to blocks"));
        ENTRIES.add(new Pair<>("placeable_bones", "    // Makes the vanilla bone item placeable\n    // Has no effect on if the block can generate in the world"));
        ENTRIES.add(new Pair<>("placeable_wall_mushrooms", "    // Makes the vanilla mushroom items placeable on walls\n    // Has no effect on if the block can generate in the world"));
        ENTRIES.add(new Pair<>("black_ice_freezing", "    // Determines if black ice gives powdered snow like freezing when standing on it"));
        ENTRIES.add(new Pair<>("red_bounceshroom_bounce", "    // The upwards velocity gained by landing on a red bounceshroom\n    // 1.0 correlates to about 4 blocks"));
        ENTRIES.add(new Pair<>("brown_bounceshroom_bounce", "    // The upwards velocity gained by landing on a brown bounceshroom\n    // 0.75 correlates to about 2.5 blocks"));
        ENTRIES.add(new Pair<>("bomb_plant_explosion_strength", "    // Strength of the explosion caused by disturbing a bomb plant\n    // Value controls both the size and the damage"));
        ENTRIES.add(new Pair<>("spores_effect", "    // The status effect spores should give on contact"));
        ENTRIES.add(new Pair<>("spores_effect_time", "    // The time in ticks the status effect given by spores should be applied for\n    // 20 ticks is 1 second"));

        ENTRIES.add(new Pair<>("items", "  // Configuration options applying to items"));
        ENTRIES.add(new Pair<>("bomb_flower_explosion_strength", "    // Strength of the explosion caused by a thrown bomb flower\n    // Value controls both the size and the damage"));
        ENTRIES.add(new Pair<>("opal_spyglass_block_sharing", "    // Determines if players are able to share blocks marked by their opal spyglass to other players\n    // Server config has control over this"));
        ENTRIES.add(new Pair<>("mushroom_cap_effect_multiplier", "    // The amount the effects in 'mushroom_cap_effects' should be multiplied with when wearing the mushroom cap\n    // 0.5 means they are halved"));
        ENTRIES.add(new Pair<>("mushroom_cap_effects", "    // A list of effects that are affected by the mushroom cap"));
        ENTRIES.add(new Pair<>("crystal_arrow_shatter_radius", "    // The radius the amethyst and opal arrow deal damage in when shattering"));
        ENTRIES.add(new Pair<>("crystal_arrow_shatter_damage", "    // The damage the amethyst and opal arrow deal when shattering"));

        ENTRIES.add(new Pair<>("entities", "  // Configuration options applying to entities"));
        ENTRIES.add(new Pair<>("skeleton_crystal_arrow_chance", "    // The chance from 0 to 1 for a skeleton in the Nether to spawn holding either an Opal or Amethyst Arrow\n    // Only applies to skeletons that spawn in the Nether"));
        ENTRIES.add(new Pair<>("spawn_weight", "      // The likelihood of an entity to spawn\n      // Higher numbers mean more likely, though is also dependent on spawn group\n      // Setting this to 0 will prevent the entity from spawning at all"));
        ENTRIES.add(new Pair<>("attributes", "      // The attributes of the mob\n      // See https://minecraft.wiki/w/Attribute if you need more information"));
        ENTRIES.add(new Pair<>("conversion_time", "      // The time in ticks a geodin needs to be stuck for to convert into block form"));

        ENTRIES.add(new Pair<>("enchants", "  // Configuration options applying to enchants"));
        ENTRIES.add(new Pair<>("max_level", "      // Maximum level of the enchantment\n      // Setting this to 0 means it cannot appear in survival"));
        ENTRIES.add(new Pair<>("distribution", "      // Configuration options determining where the enchantment can be found"));
        ENTRIES.add(new Pair<>("enchant_table_roll", "        // Whether the enchantment can appear as an option on the enchanting table"));
        ENTRIES.add(new Pair<>("librarian_book_trade", "        // Whether the enchantment can be offered on enchanted books sold by librarians"));
        ENTRIES.add(new Pair<>("found_on_enchanted_loot", "        // Whether the enchantment can be found on items or books found in chests/from fishing"));
        ENTRIES.add(new Pair<>("added_velocity_per_level", "      // The amount of velocity each level of the enchantment adds to the redirected entity"));
        ENTRIES.add(new Pair<>("rebound_range", "      // The range in blocks that you can redirect entities from\n      // Due to fast movement and still needing to hit the hitbox,\n      // this number can feel a lot lower than it actually is."));
        ENTRIES.add(new Pair<>("added_updraft_velocity_per_level", "      // The amount of upwards velocity each level of the enchantment adds to the base updraft velocity\n      // Base value can be set with 'updraft_velocity'"));

        ENTRIES.add(new Pair<>("misc", "  // Configuration options for aspects that didn't really fit any other category"));
        ENTRIES.add(new Pair<>("updraft_velocity", "    // The base amount of upwards velocity gained by catching an updraft\n    // The wind rider enchant adds onto this"));
        ENTRIES.add(new Pair<>("elytra_catch_updrafts", "    // Whether players can catch updrafts while elytra flying\n    // Also determines whether elytra can be enchanted with wind rider"));

        ENTRIES.add(new Pair<>("global_features", "  // World generation features that apply to multiple biomes"));
        ENTRIES.add(new Pair<>("raised_nether_height", "    // Raises the nether height to 192\n    // Will be ignored when another mod/datapack already changes the nether height"));
        ENTRIES.add(new Pair<>("thicker_bedrock_ceiling", "    // Makes the ceiling of the nether 5 solid layers of bedrock, compared to one\n    // Intended to discourage nether roof usage"));
        ENTRIES.add(new Pair<>("opal_geodes", "    // Whether to generate opal geodes across all nether biomes"));
        ENTRIES.add(new Pair<>("basalt_iron_ore", "    // Whether to generate patches of basalt iron ore in basalt all across the nether"));
        ENTRIES.add(new Pair<>("lava_lake_additions", "    // Whether to add magma blocks and blackstone rocks to 'colder' nether biomes"));
        ENTRIES.add(new Pair<>("nether_forest_fallen_logs", "    // Whether to add fallen logs to crimson and warped forests"));
        ENTRIES.add(new Pair<>("gilded_blackstone_patches", "    // Whether to add occasional gilded blackstone in biomes with a lot of blackstone"));
        ENTRIES.add(new Pair<>("soul_sand_valley_bones", "    // Whether to generate bones on the floor of soul sand valley"));
        ENTRIES.add(new Pair<>("basalt_deltas_vents", "    // Whether to add basalt vents across the basalt deltas"));

        ENTRIES.add(new Pair<>("biomes", "  // Configuration for the individual biomes Pyrellium adds"));
        ENTRIES.add(new Pair<>("enable_biome", "      // Whether to generate the biome in the world"));
        ENTRIES.add(new Pair<>("generate_blackstone_rocks", "      // Whether to generate blackstone rocks in the deltas"));
        ENTRIES.add(new Pair<>("generate_pyrolily", "      // Whether to generate patches of pyrolilies on the floor\n      // Does not affect the ability for pyrolilies to spawn from bone mealing amber nylium"));
        ENTRIES.add(new Pair<>("generate_floor_crystals", "      // Whether to generate patches of amethyst and opal buds on the floor"));
        ENTRIES.add(new Pair<>("generate_soul_fire", "      // Whether to generate soul fire patches across the biome"));
        ENTRIES.add(new Pair<>("generate_headstones", "      // Whether to generate headstones on the floor. Can cause world gen lag"));
        ENTRIES.add(new Pair<>("generate_floor_decorations", "      // Whether to generate cobwebs and silk carpets on the floor"));
        ENTRIES.add(new Pair<>("generate_hanging_silk", "      // Whether to generate hanging silk from the ceiling"));
        ENTRIES.add(new Pair<>("generate_bomb_flowers", "      // Whether to generate bomb plants across the floor"));
        ENTRIES.add(new Pair<>("generate_monolith", "      // Whether to generate the obsidian pillars"));
        ENTRIES.add(new Pair<>("generate_spores", "      // Whether to generate spores in the air"));
        ENTRIES.add(new Pair<>("generate_wall_mushrooms", "      // Whether to generate any of the wall mushrooms"));
        ENTRIES.add(new Pair<>("generate_quartz_spikes", "      // Whether to generate the smooth quartz spikes across the biome"));
        ENTRIES.add(new Pair<>("generate_quartz_cracks", "      // Whether to generate cracks of rough and budding quartz"));

        ENTRIES.add(new Pair<>("compatibility", "  // Configuration options applying to compatibility between other mods\n  // Will do nothing if the relevant mod is not installed"));
        ENTRIES.add(new Pair<>("render_equipment", "      // Whether to render Pyrellium equipment when in a Trinkets slot"));
        ENTRIES.add(new Pair<>("apply_equipment_effects", "      // Whether apply the effects of Pyrellium equipment when in a Trinkets slot"));
    }

    public static List<Pair<String, String>> entries() {
        return ENTRIES;
    }
}
