package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.registry.VersionedIdentifier;
import net.minecraft.resource.*;
import net.minecraft.resource.metadata.ResourceMetadataSerializer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;

public class PyrelliumResourcePack implements ResourcePack {
    public static final PyrelliumResourcePack INSTANCE = new PyrelliumResourcePack(new ResourcePackInfo(Pyrellium.MOD_ID + "_resources", Text.literal("Pyrellium Resources"), ResourcePackSource.BUILTIN, Optional.empty()));

    private final ResourcePackInfo info;

    public PyrelliumResourcePack(ResourcePackInfo info) {
        this.info = info;
    }

    @Override
    public @Nullable InputSupplier<InputStream> openRoot(String... segments) {
        return null;
    }

    @Override
    public @Nullable InputSupplier<InputStream> open(ResourceType type, Identifier id) {
        return null;
    }

    @Override
    public void findResources(ResourceType type, String namespace, String prefix, ResultConsumer consumer) {

    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        return Set.of();
    }

    @Override
    public @Nullable <T> T parseMetadata(ResourceMetadataSerializer<T> metadataSerializer) throws IOException {
        return null;
    }

    @Override
    public ResourcePackInfo getInfo() {
        return info;
    }

    @Override
    public String getId() {
        return ResourcePack.super.getId();
    }

    @Override
    public Optional<VersionedIdentifier> getKnownPackInfo() {
        return ResourcePack.super.getKnownPackInfo();
    }

    @Override
    public void close() {

    }
}