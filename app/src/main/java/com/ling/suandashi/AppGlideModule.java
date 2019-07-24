package com.ling.suandashi;

import com.bumptech.glide.annotation.GlideModule;

@GlideModule
public class AppGlideModule extends com.bumptech.glide.module.AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}

