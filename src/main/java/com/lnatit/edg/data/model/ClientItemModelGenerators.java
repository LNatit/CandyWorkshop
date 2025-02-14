package com.lnatit.edg.data.model;

import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <li>Models: ResourceLocation(ModelLocation), TextureMapping & ModelTemplate
 * <li>ClientItems: ResourceLocation(RegistryId) & ClientItem
 * <p>
 * For mostly clientItems {@code ModelLocation = RegistryId.withSuffix("item/");}
 */
public class ClientItemModelGenerators
{
    public final ClientItemCollector clientItemOutput;
    public final BiConsumer<ResourceLocation, ModelInstance> modelOutput;

    public ClientItemModelGenerators(ClientItemCollector clientItemOutput, ItemModelGenerators template) {
        this.clientItemOutput = clientItemOutput;
        this.modelOutput = template.modelOutput;
    }

    public ClientItemCollector clientItemOnly() {
        return clientItemOutput;
    }

//    public class ModelCurried implements Function<ResourceLocation, Function<TextureMapping, Consumer<ModelTemplate>>> {
//        @Override
//        public Function<TextureMapping, Consumer<ModelTemplate>> apply(ResourceLocation modelLocation) {
//            return new CurriedTextureMapping(modelLocation);
//        }
//
//        public class CurriedTextureMapping implements Function<TextureMapping, Consumer<ModelTemplate>>
//        {
//            private final ResourceLocation modelLocation;
//
//            public CurriedTextureMapping(ResourceLocation modelLocation) {
//                this.modelLocation = modelLocation;
//            }
//
//            @Override
//            public Consumer<ModelTemplate> apply(TextureMapping textureMapping) {
//                return new CurriedModelTemplate(textureMapping);
//            }
//
//            public class CurriedModelTemplate implements Consumer<ModelTemplate>
//            {
//                private final TextureMapping textureMapping;
//
//                public CurriedModelTemplate(TextureMapping textureMapping) {
//                    this.textureMapping = textureMapping;
//                }
//
//                @Override
//                public void accept(ModelTemplate modelTemplate) {
//                    modelTemplate.create(CurriedTextureMapping.this.modelLocation, textureMapping, ClientItemModelGenerators.this.modelOutput);
//                }
//            }
//        }
//    }

    public Generator gen() {
        return new Generator();
    }

    public void genClientItem(ResourceLocation registryId, ClientItem clientItem) {
        this.clientItemOutput.accept(registryId, clientItem);
    }

    public void genModel(ResourceLocation modelLocation, TextureMapping textureMapping, ModelTemplate modelTemplate) {
        modelTemplate.create(modelLocation, textureMapping, this.modelOutput);
    }

    public void generate(ResourceLocation registryId, ClientItem clientItem, ResourceLocation modelLocation, TextureMapping textureMapping, ModelTemplate modelTemplate) {
        this.genClientItem(registryId, clientItem);
        this.genModel(modelLocation, textureMapping, modelTemplate);
    }

    public static class Generator implements GeneratorInit, GeneratorImpl
    {
        private ResourceLocation id;
        private ResourceLocation modelResourceLocation;
        private TextureMapping textureMapping;
        private ModelTemplate modelTemplate;
        private ClientItem clientItem;

        @Override
        public GeneratorImpl withId(ResourceLocation id) {
            this.id = id;
            return this;
        }
    }

    public static class ClientItemBuilder
    {
        @Nonnull
        private ResourceLocation modelResourceLocation;
        private Function<ResourceLocation, ItemModel.Unbaked> unbakedGen;
        private ItemModel.Unbaked unbaked;
        private ClientItem.Properties properties;

        public ClientItemBuilder(ResourceLocation modelResourceLocation) {
            this.modelResourceLocation = modelResourceLocation;
        }

        public void withModel(ResourceLocation modelResourceLocation) {
            this.modelResourceLocation = modelResourceLocation;
        }

        public void withUnbakedGen(Function<ResourceLocation, ItemModel.Unbaked> unbakedGen) {
            this.unbakedGen = unbakedGen;
        }

        public void withUnbaked(ItemModel.Unbaked unbaked) {
            this.unbaked = unbaked;
        }

        public void withProperties(ClientItem.Properties properties) {
            this.properties = properties;
        }

        public ClientItem build() {
            if (this.unbaked == null) {
                if (this.unbakedGen == null) {
                    this.unbakedGen = ItemModelUtils::plainModel;
                }
                this.unbaked = this.unbakedGen.apply(this.modelResourceLocation);
            }
            if (this.properties == null) {
                this.properties = ClientItem.Properties.DEFAULT;
            }

            return new ClientItem(this.unbaked, this.properties);
        }
    }

    /**
     * A more nimble way to DataGen {@link ClientItem} that doesn't actually bound to a registered {@link net.minecraft.world.item.Item}.
     * Basically you can call {@link ClientItemCollector#accept(ResourceLocation, ClientItem)} to generate,
     * but there are also many overloads for convenient uses.
     *
     * @see ModelProvider.ItemInfoCollector
     */
    public static class ClientItemCollector implements BiConsumer<ResourceLocation, ClientItem>
    {
        private final Map<ResourceLocation, ClientItem> clientItemInfos = new HashMap<>();

        @Override
        public void accept(ResourceLocation resourceLocation, ClientItem clientItem) {
            this.clientItemInfos.put(resourceLocation, clientItem);
        }

        protected CompletableFuture<?> save(CachedOutput output, PackOutput.PathProvider pathProvider) {
            return DataProvider.saveAll(output, ClientItem.CODEC, pathProvider::json, this.clientItemInfos);
        }

        public Currier accept(ResourceLocation resourceLocation) {
            return new Currier(resourceLocation);
        }

        public Currier accept(DeferredHolder<?, ?> registry) {
            return this.accept(registry.getId());
        }

        public class Currier
        {
            private final ResourceLocation id;

            private Currier(ResourceLocation id) {
                this.id = id;
            }

            public void with(ClientItem clientItem) {
                ClientItemCollector.this.accept(this.id, clientItem);
            }

            public void with(ItemModel.Unbaked unbakedModel) {
                this.with(new ClientItem(unbakedModel, ClientItem.Properties.DEFAULT));
            }

            // TODO
            public void flat() {
                //            this.with(ItemModelUtils.plainModel());
            }
        }
    }
}
