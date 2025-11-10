package com.lankaster.pyrellium.world;

import blue.endless.jankson.annotation.Nullable;
import com.google.gson.*;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModNoiseSettings {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public final Identifier target;
    public Function<JsonElement, String> provider;
    public final Supplier<Boolean> enabled;

    public ModNoiseSettings(Identifier target, Supplier<Boolean> enabled, Function<JsonElement, String> provider) {
        this.target = target;
        this.provider = provider;
        this.enabled = enabled;
    }

    public String apply(@Nullable String original){
        return gson.fromJson(this.provider.apply(gson.fromJson(original == null ? "" : original, JsonElement.class)), JsonElement.class).toString();
    }

    private static JsonElement getJson(String string) {
        return gson.fromJson(string, JsonElement.class);
    }

    public static boolean detectModification(JsonElement json) {
        JsonElement defaultNoise = getJson("""
                {"height":128,"min_y":0,"size_horizontal":1,"size_vertical":2}""");
        JsonElement defaultRouter = getJson("""
                {"barrier":0.0,"continents":0.0,"depth":0.0,"erosion":0.0,"final_density":{"type":"minecraft:squeeze","argument":{"type":"minecraft:mul","argument1":0.64,"argument2":{"type":"minecraft:interpolated","argument":{"type":"minecraft:blend_density","argument":{"type":"minecraft:add","argument1":2.5,"argument2":{"type":"minecraft:mul","argument1":{"type":"minecraft:y_clamped_gradient","from_value":0.0,"from_y":-8,"to_value":1.0,"to_y":24},"argument2":{"type":"minecraft:add","argument1":-2.5,"argument2":{"type":"minecraft:add","argument1":0.9375,"argument2":{"type":"minecraft:mul","argument1":{"type":"minecraft:y_clamped_gradient","from_value":1.0,"from_y":104,"to_value":0.0,"to_y":128},"argument2":{"type":"minecraft:add","argument1":-0.9375,"argument2":"minecraft:nether/base_3d_noise"}}}}}}}}}},"fluid_level_floodedness":0.0,"fluid_level_spread":0.0,"initial_density_without_jaggedness":0.0,"lava":0.0,"ridges":0.0,"temperature":{"type":"minecraft:shifted_noise","noise":"minecraft:temperature","shift_x":"minecraft:shift_x","shift_y":0.0,"shift_z":"minecraft:shift_z","xz_scale":0.25,"y_scale":0.0},"vegetation":{"type":"minecraft:shifted_noise","noise":"minecraft:vegetation","shift_x":"minecraft:shift_x","shift_y":0.0,"shift_z":"minecraft:shift_z","xz_scale":0.25,"y_scale":0.0},"vein_gap":0.0,"vein_ridged":0.0,"vein_toggle":0.0}""");
        return (!json.getAsJsonObject().get("noise").equals(defaultNoise)) || (!json.getAsJsonObject().get("noise_router").equals(defaultRouter));
    }

    public static String changeNoiseRouter(JsonElement json) {
        json.getAsJsonObject().asMap().replace("noise", getJson("""
                        { "min_y": 0, "height": 192, "size_horizontal": 1, "size_vertical": 2 }"""));

        json.getAsJsonObject().get("noise_router")
                .getAsJsonObject().asMap().replace("final_density", new JsonPrimitive("pyrellium:final_density"));

        return gson.toJson(json);
    }

    public static String changeSurfaceRules(JsonElement json) {
        JsonObject baseRules = json.getAsJsonObject().get("surface_rule").getAsJsonObject();
        JsonArray sequence = new JsonArray();
        sequence.add(getJson("""
                {
                           "type": "minecraft:sequence",
                           "sequence": [
                             {
                               "type": "minecraft:condition",
                               "if_true": {
                                 "type": "minecraft:biome",
                                 "biome_is": [
                                   "pyrellium:frostburn_valley"
                                 ]
                               },
                               "then_run": {
                                 "type": "minecraft:sequence",
                                 "sequence": [
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:stone_depth",
                                       "offset": 0,
                                       "surface_type": "ceiling",
                                       "add_surface_depth": true,
                                       "secondary_depth_range": 0
                                     },
                                     "then_run": {
                                       "type": "minecraft:sequence",
                                       "sequence": [
                                         {
                                           "type": "minecraft:condition",
                                           "if_true": {
                                             "type": "minecraft:noise_threshold",
                                             "noise": "minecraft:nether_state_selector",
                                             "min_threshold": 0,
                                             "max_threshold": 1.7976931348623157e+308
                                           },
                                           "then_run": {
                                             "type": "minecraft:block",
                                             "result_state": {
                                               "Name": "pyrellium:freezing_ice"
                                             }
                                           }
                                         },
                                         {
                                           "type": "minecraft:block",
                                           "result_state": {
                                             "Name": "minecraft:soul_soil"
                                           }
                                         }
                                       ]
                                     }
                                   },
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:stone_depth",
                                       "offset": 0,
                                       "surface_type": "floor",
                                       "add_surface_depth": true,
                                       "secondary_depth_range": 0
                                     },
                                     "then_run": {
                                       "type": "minecraft:sequence",
                                       "sequence": [
                                         {
                                           "type": "minecraft:condition",
                                           "if_true": {
                                             "type": "minecraft:noise_threshold",
                                             "noise": "minecraft:patch",
                                             "min_threshold": -0.012,
                                             "max_threshold": 1.7976931348623157e+308
                                           },
                                           "then_run": {
                                             "type": "minecraft:condition",
                                             "if_true": {
                                               "type": "minecraft:y_above",
                                               "anchor": {
                                                 "absolute": 30
                                               },
                                               "surface_depth_multiplier": 0,
                                               "add_stone_depth": true
                                             },
                                             "then_run": {
                                               "type": "minecraft:condition",
                                               "if_true": {
                                                 "type": "minecraft:not",
                                                 "invert": {
                                                   "type": "minecraft:y_above",
                                                   "anchor": {
                                                     "absolute": 35
                                                   },
                                                   "surface_depth_multiplier": 0,
                                                   "add_stone_depth": true
                                                 }
                                               },
                                               "then_run": {
                                                 "type": "minecraft:block",
                                                 "result_state": {
                                                   "Name": "minecraft:gravel"
                                                 }
                                               }
                                             }
                                           }
                                         },
                                         {
                                           "type": "minecraft:condition",
                                           "if_true": {
                                             "type": "minecraft:noise_threshold",
                                             "noise": "minecraft:nether_state_selector",
                                             "min_threshold": 0,
                                             "max_threshold": 1.7976931348623157e+308
                                           },
                                           "then_run": {
                                             "type": "minecraft:block",
                                             "result_state": {
                                               "Name": "pyrellium:freezing_ice"
                                             }
                                           }
                                         },
                                         {
                                           "type": "minecraft:block",
                                           "result_state": {
                                             "Name": "minecraft:soul_soil"
                                           }
                                         }
                                       ]
                                     }
                                   }
                                 ]
                               }
                             },
                             {
                               "type": "minecraft:condition",
                               "if_true": {
                                 "type": "minecraft:stone_depth",
                                 "offset": 0,
                                 "surface_type": "floor",
                                 "add_surface_depth": false,
                                 "secondary_depth_range": 0
                               },
                               "then_run": {
                                 "type": "minecraft:sequence",
                                 "sequence": [
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:not",
                                       "invert": {
                                         "type": "minecraft:y_above",
                                         "anchor": {
                                           "absolute": 32
                                         },
                                         "surface_depth_multiplier": 0,
                                         "add_stone_depth": false
                                       }
                                     },
                                     "then_run": {
                                       "type": "minecraft:condition",
                                       "if_true": {
                                         "type": "minecraft:hole"
                                       },
                                       "then_run": {
                                         "type": "minecraft:block",
                                         "result_state": {
                                           "Name": "minecraft:lava",
                                           "Properties": {
                                             "level": "0"
                                           }
                                         }
                                       }
                                     }
                                   },
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:biome",
                                       "biome_is": [
                                         "pyrellium:monolith_plains"
                                       ]
                                     },
                                     "then_run": {
                                       "type": "minecraft:condition",
                                       "if_true": {
                                         "type": "minecraft:not",
                                         "invert": {
                                           "type": "minecraft:noise_threshold",
                                           "noise": "minecraft:netherrack",
                                           "min_threshold": 0.54,
                                           "max_threshold": 1.7976931348623157e+308
                                         }
                                       },
                                       "then_run": {
                                         "type": "minecraft:condition",
                                         "if_true": {
                                           "type": "minecraft:y_above",
                                           "anchor": {
                                             "absolute": 31
                                           },
                                           "surface_depth_multiplier": 0,
                                           "add_stone_depth": false
                                         },
                                         "then_run": {
                                           "type": "minecraft:sequence",
                                           "sequence": [
                                             {
                                               "type": "minecraft:condition",
                                               "if_true": {
                                                 "type": "minecraft:noise_threshold",
                                                 "noise": "minecraft:nether_wart",
                                                 "min_threshold": 1.17,
                                                 "max_threshold": 1.7976931348623157e+308
                                               },
                                               "then_run": {
                                                 "type": "minecraft:block",
                                                 "result_state": {
                                                   "Name": "minecraft:nether_wart_block"
                                                 }
                                               }
                                             },
                                             {
                                               "type": "minecraft:block",
                                               "result_state": {
                                                 "Name": "minecraft:crimson_nylium"
                                               }
                                             }
                                           ]
                                         }
                                       }
                                     }
                                   }
                                 ]
                               }
                             },
                             {
                               "type": "minecraft:condition",
                               "if_true": {
                                 "type": "minecraft:biome",
                                 "biome_is": [
                                   "pyrellium:blackstone_springs"
                                 ]
                               },
                               "then_run": {
                                 "type": "minecraft:sequence",
                                 "sequence": [
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:stone_depth",
                                       "offset": 0,
                                       "surface_type": "ceiling",
                                       "add_surface_depth": true,
                                       "secondary_depth_range": 0
                                     },
                                     "then_run": {
                                       "type": "minecraft:block",
                                       "result_state": {
                                         "Name": "minecraft:blackstone",
                                         "Properties": {
                                           "axis": "y"
                                         }
                                       }
                                     }
                                   },
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:stone_depth",
                                       "offset": 0,
                                       "surface_type": "floor",
                                       "add_surface_depth": true,
                                       "secondary_depth_range": 0
                                     },
                                     "then_run": {
                                       "type": "minecraft:sequence",
                                       "sequence": [
                                         {
                                           "type": "minecraft:block",
                                           "result_state": {
                                             "Name": "minecraft:blackstone"
                                           }
                                         }
                                       ]
                                     }
                                   }
                                 ]
                               }
                             },
                             {
                               "type": "minecraft:condition",
                               "if_true": {
                                 "type": "minecraft:biome",
                                 "biome_is": [
                                   "pyrellium:infested_valley"
                                 ]
                               },
                               "then_run": {
                                 "type": "minecraft:sequence",
                                 "sequence": [
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:stone_depth",
                                       "offset": 0,
                                       "surface_type": "ceiling",
                                       "add_surface_depth": true,
                                       "secondary_depth_range": 0
                                     },
                                     "then_run": {
                                       "type": "minecraft:sequence",
                                       "sequence": [
                                         {
                                           "type": "minecraft:block",
                                           "result_state": {
                                             "Name": "minecraft:soul_sand"
                                           }
                                         }
                                       ]
                                     }
                                   },
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:stone_depth",
                                       "offset": 0,
                                       "surface_type": "floor",
                                       "add_surface_depth": true,
                                       "secondary_depth_range": 0
                                     },
                                     "then_run": {
                                       "type": "minecraft:sequence",
                                       "sequence": [
                                         {
                                           "type": "minecraft:condition",
                                           "if_true": {
                                             "type": "minecraft:noise_threshold",
                                             "noise": "minecraft:patch",
                                             "min_threshold": -0.012,
                                             "max_threshold": 1.7976931348623157e+308
                                           },
                                           "then_run": {
                                             "type": "minecraft:condition",
                                             "if_true": {
                                               "type": "minecraft:y_above",
                                               "anchor": {
                                                 "absolute": 30
                                               },
                                               "surface_depth_multiplier": 0,
                                               "add_stone_depth": true
                                             },
                                             "then_run": {
                                               "type": "minecraft:condition",
                                               "if_true": {
                                                 "type": "minecraft:not",
                                                 "invert": {
                                                   "type": "minecraft:y_above",
                                                   "anchor": {
                                                     "absolute": 35
                                                   },
                                                   "surface_depth_multiplier": 0,
                                                   "add_stone_depth": true
                                                 }
                                               },
                                               "then_run": {
                                                 "type": "minecraft:block",
                                                 "result_state": {
                                                   "Name": "minecraft:gravel"
                                                 }
                                               }
                                             }
                                           }
                                         },
                                         {
                                           "type": "minecraft:block",
                                           "result_state": {
                                             "Name": "minecraft:soul_sand"
                                           }
                                         }
                                       ]
                                     }
                                   }
                                 ]
                               }
                             },
                             {
                               "type": "minecraft:condition",
                               "if_true": {
                                 "type": "minecraft:biome",
                                 "biome_is": [
                                   "pyrellium:quartz_caverns"
                                 ]
                               },
                               "then_run": {
                                 "type": "minecraft:sequence",
                                 "sequence": [
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:stone_depth",
                                       "offset": 0,
                                       "surface_type": "floor",
                                       "add_surface_depth": true,
                                       "secondary_depth_range": 0
                                     },
                                     "then_run": {
                                       "type": "minecraft:sequence",
                                       "sequence": [
                                         {
                                           "type": "minecraft:condition",
                                           "if_true": {
                                             "type": "minecraft:noise_threshold",
                                             "noise": "minecraft:patch",
                                             "min_threshold": -0.012,
                                             "max_threshold": 1.7976931348623157e+308
                                           },
                                           "then_run": {
                                             "type": "minecraft:condition",
                                             "if_true": {
                                               "type": "minecraft:y_above",
                                               "anchor": {
                                                 "absolute": 30
                                               },
                                               "surface_depth_multiplier": 0,
                                               "add_stone_depth": true
                                             },
                                             "then_run": {
                                               "type": "minecraft:condition",
                                               "if_true": {
                                                 "type": "minecraft:not",
                                                 "invert": {
                                                   "type": "minecraft:y_above",
                                                   "anchor": {
                                                     "absolute": 35
                                                   },
                                                   "surface_depth_multiplier": 0,
                                                   "add_stone_depth": true
                                                 }
                                               },
                                               "then_run": {
                                                 "type": "minecraft:block",
                                                 "result_state": {
                                                   "Name": "minecraft:gravel"
                                                 }
                                               }
                                             }
                                           }
                                         },
                                         {
                                           "type": "minecraft:block",
                                           "result_state": {
                                             "Name": "minecraft:smooth_basalt"
                                           }
                                         }
                                       ]
                                     }
                                   }
                                 ]
                               }
                             },
                             {
                               "type": "minecraft:condition",
                               "if_true": {
                                 "type": "minecraft:stone_depth",
                                 "offset": 0,
                                 "surface_type": "floor",
                                 "add_surface_depth": false,
                                 "secondary_depth_range": 0
                               },
                               "then_run": {
                                 "type": "minecraft:sequence",
                                 "sequence": [
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:not",
                                       "invert": {
                                         "type": "minecraft:y_above",
                                         "anchor": {
                                           "absolute": 32
                                         },
                                         "surface_depth_multiplier": 0,
                                         "add_stone_depth": false
                                       }
                                     },
                                     "then_run": {
                                       "type": "minecraft:condition",
                                       "if_true": {
                                         "type": "minecraft:hole"
                                       },
                                       "then_run": {
                                         "type": "minecraft:block",
                                         "result_state": {
                                           "Name": "minecraft:lava",
                                           "Properties": {
                                             "level": "0"
                                           }
                                         }
                                       }
                                     }
                                   },
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:biome",
                                       "biome_is": [
                                         "pyrellium:crystal_forest"
                                       ]
                                     },
                                     "then_run": {
                                       "type": "minecraft:condition",
                                       "if_true": {
                                         "type": "minecraft:not",
                                         "invert": {
                                           "type": "minecraft:noise_threshold",
                                           "noise": "minecraft:netherrack",
                                           "min_threshold": 0.54,
                                           "max_threshold": 1.7976931348623157e+308
                                         }
                                       },
                                       "then_run": {
                                         "type": "minecraft:condition",
                                         "if_true": {
                                           "type": "minecraft:y_above",
                                           "anchor": {
                                             "absolute": 31
                                           },
                                           "surface_depth_multiplier": 0,
                                           "add_stone_depth": false
                                         },
                                         "then_run": {
                                           "type": "minecraft:sequence",
                                           "sequence": [
                                             {
                                               "type": "minecraft:condition",
                                               "if_true": {
                                                 "type": "minecraft:noise_threshold",
                                                 "noise": "minecraft:nether_wart",
                                                 "min_threshold": 1.17,
                                                 "max_threshold": 1.7976931348623157e+308
                                               },
                                               "then_run": {
                                                 "type": "minecraft:block",
                                                 "result_state": {
                                                   "Name": "minecraft:warped_wart_block"
                                                 }
                                               }
                                             },
                                             {
                                               "type": "minecraft:block",
                                               "result_state": {
                                                 "Name": "minecraft:warped_nylium"
                                               }
                                             }
                                           ]
                                         }
                                       }
                                     }
                                   }
                                 ]
                               }
                             },
                             {
                               "type": "minecraft:sequence",
                               "sequence": [
                                 {
                                   "type": "minecraft:condition",
                                   "if_true": {
                                     "type": "minecraft:not",
                                     "invert": {
                                       "type": "minecraft:y_above",
                                       "anchor": {
                                         "absolute": 32
                                       },
                                       "surface_depth_multiplier": 0,
                                       "add_stone_depth": false
                                     }
                                   },
                                   "then_run": {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:hole"
                                     },
                                     "then_run": {
                                       "type": "minecraft:block",
                                       "result_state": {
                                         "Name": "minecraft:lava",
                                         "Properties": {
                                           "level": "0"
                                         }
                                       }
                                     }
                                   }
                                 },
                                 {
                                   "type": "minecraft:condition",
                                   "if_true": {
                                     "type": "minecraft:biome",
                                     "biome_is": [
                                       "pyrellium:burning_grove"
                                     ]
                                   },
                                   "then_run": {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:not",
                                       "invert": {
                                         "type": "minecraft:noise_threshold",
                                         "noise": "minecraft:netherrack",
                                         "min_threshold": 0.54,
                                         "max_threshold": 1.7976931348623157e+308
                                       }
                                     },
                                     "then_run": {
                                       "type": "minecraft:sequence",
                                       "sequence": [
                                         {
                                           "type": "minecraft:condition",
                                           "if_true": {
                                             "type": "minecraft:stone_depth",
                                             "offset": 0,
                                             "surface_type": "floor",
                                             "add_surface_depth": true,
                                             "secondary_depth_range": 0
                                           },
                                           "then_run": {
                                             "type": "minecraft:condition",
                                             "if_true": {
                                               "type": "minecraft:noise_threshold",
                                               "noise": "minecraft:nether_state_selector",
                                               "min_threshold": -0.075,
                                               "max_threshold": 0.075
                                             },
                                             "then_run": {
                                               "type": "minecraft:block",
                                               "result_state": {
                                                 "Name": "minecraft:blackstone"
                                               }
                                             }
                                           }
                                         },
                                         {
                                           "type": "minecraft:condition",
                                           "if_true": {
                                             "type": "minecraft:y_above",
                                             "anchor": {
                                               "absolute": 31
                                             },
                                             "surface_depth_multiplier": 0,
                                             "add_stone_depth": false
                                           },
                                           "then_run": {
                                             "type": "minecraft:condition",
                                             "if_true": {
                                               "type": "minecraft:stone_depth",
                                               "offset": 0,
                                               "surface_type": "floor",
                                               "add_surface_depth": false,
                                               "secondary_depth_range": 0
                                             },
                                             "then_run": {
                                               "type": "minecraft:block",
                                               "result_state": {
                                                 "Name": "pyrellium:burning_nylium"
                                               }
                                             }
                                           }
                                         }
                                       ]
                                     }
                                   }
                                 }
                               ]
                             },
                             {
                               "type": "minecraft:condition",
                               "if_true": {
                                 "type": "minecraft:biome",
                                 "biome_is": [
                                   "pyrellium:ghostly_woods"
                                 ]
                               },
                               "then_run": {
                                 "type": "minecraft:sequence",
                                 "sequence": [
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:stone_depth",
                                       "offset": 0,
                                       "surface_type": "ceiling",
                                       "add_surface_depth": true,
                                       "secondary_depth_range": 0
                                     },
                                     "then_run": {
                                       "type": "minecraft:sequence",
                                       "sequence": [
                                         {
                                           "type": "minecraft:block",
                                           "result_state": {
                                             "Name": "minecraft:soul_soil"
                                           }
                                         }
                                       ]
                                     }
                                   },
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:stone_depth",
                                       "offset": 0,
                                       "surface_type": "floor",
                                       "add_surface_depth": true,
                                       "secondary_depth_range": 0
                                     },
                                     "then_run": {
                                       "type": "minecraft:sequence",
                                       "sequence": [
                                         {
                                           "type": "minecraft:condition",
                                           "if_true": {
                                             "type": "minecraft:noise_threshold",
                                             "noise": "minecraft:patch",
                                             "min_threshold": -0.012,
                                             "max_threshold": 1.7976931348623157e+308
                                           },
                                           "then_run": {
                                             "type": "minecraft:condition",
                                             "if_true": {
                                               "type": "minecraft:y_above",
                                               "anchor": {
                                                 "absolute": 30
                                               },
                                               "surface_depth_multiplier": 0,
                                               "add_stone_depth": true
                                             },
                                             "then_run": {
                                               "type": "minecraft:condition",
                                               "if_true": {
                                                 "type": "minecraft:not",
                                                 "invert": {
                                                   "type": "minecraft:y_above",
                                                   "anchor": {
                                                     "absolute": 35
                                                   },
                                                   "surface_depth_multiplier": 0,
                                                   "add_stone_depth": true
                                                 }
                                               },
                                               "then_run": {
                                                 "type": "minecraft:block",
                                                 "result_state": {
                                                   "Name": "minecraft:gravel"
                                                 }
                                               }
                                             }
                                           }
                                         },
                                         {
                                           "type": "minecraft:block",
                                           "result_state": {
                                             "Name": "minecraft:soul_soil"
                                           }
                                         }
                                       ]
                                     }
                                   }
                                 ]
                               }
                             },
                             {
                               "type": "minecraft:condition",
                               "if_true": {
                                 "type": "minecraft:stone_depth",
                                 "offset": 0,
                                 "surface_type": "floor",
                                 "add_surface_depth": false,
                                 "secondary_depth_range": 0
                               },
                               "then_run": {
                                 "type": "minecraft:sequence",
                                 "sequence": [
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:not",
                                       "invert": {
                                         "type": "minecraft:y_above",
                                         "anchor": {
                                           "absolute": 32
                                         },
                                         "surface_depth_multiplier": 0,
                                         "add_stone_depth": false
                                       }
                                     },
                                     "then_run": {
                                       "type": "minecraft:condition",
                                       "if_true": {
                                         "type": "minecraft:hole"
                                       },
                                       "then_run": {
                                         "type": "minecraft:block",
                                         "result_state": {
                                           "Name": "minecraft:lava",
                                           "Properties": {
                                             "level": "0"
                                           }
                                         }
                                       }
                                     }
                                   },
                                   {
                                     "type": "minecraft:condition",
                                     "if_true": {
                                       "type": "minecraft:biome",
                                       "biome_is": [
                                         "pyrellium:mushroom_wastes"
                                       ]
                                     },
                                     "then_run": {
                                       "type": "minecraft:condition",
                                       "if_true": {
                                         "type": "minecraft:not",
                                         "invert": {
                                           "type": "minecraft:noise_threshold",
                                           "noise": "minecraft:netherrack",
                                           "min_threshold": 0.54,
                                           "max_threshold": 1.7976931348623157e+308
                                         }
                                       },
                                       "then_run": {
                                         "type": "minecraft:condition",
                                         "if_true": {
                                           "type": "minecraft:y_above",
                                           "anchor": {
                                             "absolute": 31
                                           },
                                           "surface_depth_multiplier": 0,
                                           "add_stone_depth": false
                                         },
                                         "then_run": {
                                           "type": "minecraft:sequence",
                                           "sequence": [
                                             {
                                               "type": "minecraft:condition",
                                               "if_true": {
                                                 "type": "minecraft:noise_threshold",
                                                 "noise": "minecraft:nether_wart",
                                                 "min_threshold": 1.17,
                                                 "max_threshold": 1.7976931348623157e+308
                                               },
                                               "then_run": {
                                                 "type": "minecraft:block",
                                                 "result_state": {
                                                   "Name": "minecraft:warped_wart_block"
                                                 }
                                               }
                                             },
                                             {
                                               "type": "minecraft:block",
                                               "result_state": {
                                                 "Name": "pyrellium:netherrack_mycelium"
                                               }
                                             }
                                           ]
                                         }
                                       }
                                     }
                                   }
                                 ]
                               }
                             }
                           ]
                         }"""));
        sequence.add(baseRules);
        JsonObject rules = getJson("""
                    {
                      "type": "minecraft:sequence"
                    }""").getAsJsonObject();
        rules.add("sequence", sequence);
        json.getAsJsonObject().asMap().replace("surface_rule", rules);

        return gson.toJson(json);
    }
}
