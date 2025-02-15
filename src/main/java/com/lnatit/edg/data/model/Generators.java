package com.lnatit.edg.data.model;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Function;

public class Generators {

    interface ModelAcceptor {
        Impl withModel(ResourceLocation modelResourceLocation);

        default Impl withModel(ResourceLocation id, String prefix) {
            return this.withModel(id.withPrefix(prefix));
        }
    }

    interface TextureMappingAcceptor {
        Impl withTextureMapping(TextureMapping textureMapping);
    }

    interface ModelTemplateAcceptor {
        Impl withModelTemplate(ModelTemplate modelTemplate);
    }

    interface ClientItemAcceptor {
        ClientItemModelGenerators.ClientItemBuilder getClientItemBuilder();

        Impl withClientItem(ClientItem clientItem);

        default Impl withClientItem(Function<ClientItemModelGenerators.ClientItemBuilder, ClientItem> clientItem) {
            return this.withClientItem(clientItem.apply(this.getClientItemBuilder()));
        }
    }

    public interface Init {
        Impl withId(ResourceLocation id);

        default Impl withId(DeferredHolder<?, ?> registryItem) {
            return this.withId(registryItem.getId());
        }
    }

    public interface Impl extends ModelAcceptor, TextureMappingAcceptor, ModelTemplateAcceptor, ClientItemAcceptor {
        void all();

        void modelOnly();

        void clientItemOnly();

        default void generate() {
            this.all();
        }
    }
}
