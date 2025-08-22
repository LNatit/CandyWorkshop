package com.lnatit.ccw.compat;

import com.lnatit.ccw.CandyWorkshop;
import net.minecraftforge.fml.ModList;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.List;

public class CompatManager
{
    private static final List<Entry> COMPATS = List.of(
            new Entry("farmersdelight", "com.lnatit.ccw.compat.farmersdelight.FarmersDelightCompats"),
            new Entry("apothic_attributes", "com.lnatit.ccw.compat.apothesis.ApothSugars"),
            new Entry("apotheosis", "com.lnatit.ccw.compat.apothesis.ApothBlends"),
            new Entry("youkaishomecoming", "com.lnatit.ccw.compat.youkaishomecoming.YoukaisHomecomingCompats"),
            new Entry("fruitsdelight", "com.lnatit.ccw.compat.fruitsdelight.FruitsDelightCompats")
    );

    public static void loadCompats() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType type = MethodType.methodType(void.class);
        MethodHandle handle;
        for (var entry : COMPATS) {
            if (ModList.get().isLoaded(entry.modid()))
            {
                try {
                    Class<?> clazz = Class.forName(entry.className());
                    handle = lookup.findStatic(clazz, entry.initMethod(), type);
                    handle.invoke();
                }
                catch (Throwable e) {
                    throw new RuntimeException(e);
                }
                finally {
                    CandyWorkshop.LOGGER.info("Hello there [" + entry.modid() + "]!");
                }
            }
        }
    }

    private record Entry(String modid, String className)
    {
        public String initMethod() {
            return "init";
        }
    }
}
