package com.lankaster.pyrellium.data;

import com.lankaster.pyrellium.Pyrellium;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class PyrelliumResourcePack implements ResourcePack {
    public static final PyrelliumResourcePack INSTANCE = new PyrelliumResourcePack();

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
    public @Nullable <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
        return null;
    }

    @Override
    public String getName() {
        return Pyrellium.MOD_ID + "_resources";
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isAlwaysStable() {
        return true;
    }
}
