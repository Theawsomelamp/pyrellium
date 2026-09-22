package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;

public class PyrelliumResourcePack implements PackResources {
    public static final PyrelliumResourcePack INSTANCE = new PyrelliumResourcePack(new PackLocationInfo(Pyrellium.MOD_ID + "_resources", Component.literal("Pyrellium Resources"), PackSource.BUILT_IN, Optional.empty()));

    private final PackLocationInfo info;

    public PyrelliumResourcePack(PackLocationInfo info) {
        this.info = info;
    }

    @Override
    public @Nullable IoSupplier<InputStream> getRootResource(String... segments) {
        return null;
    }

    @Override
    public @Nullable IoSupplier<InputStream> getResource(PackType type, ResourceLocation id) {
        return null;
    }

    @Override
    public void listResources(PackType type, String namespace, String prefix, ResourceOutput consumer) {

    }

    @Override
    public Set<String> getNamespaces(PackType type) {
        return Set.of();
    }

    @Override
    public @Nullable <T> T getMetadataSection(MetadataSectionSerializer<T> metaReader) throws IOException {
        return null;
    }

    @Override
    public PackLocationInfo location() {
        return info;
    }

    @Override
    public String packId() {
        return PackResources.super.packId();
    }

    @Override
    public Optional<KnownPack> knownPackInfo() {
        return PackResources.super.knownPackInfo();
    }

    @Override
    public void close() {

    }
}