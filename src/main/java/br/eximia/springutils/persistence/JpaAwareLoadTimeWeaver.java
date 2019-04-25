package br.eximia.springutils.persistence;

import org.springframework.instrument.classloading.SimpleLoadTimeWeaver;

public class JpaAwareLoadTimeWeaver extends SimpleLoadTimeWeaver {
    @Override
    public ClassLoader getInstrumentableClassLoader() {
        return super.getInstrumentableClassLoader().getParent();
    }
}
