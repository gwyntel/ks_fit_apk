package com.bumptech.glide;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class GlideExperiments {
    private final Map<Class<?>, Experiment> experiments;

    static final class Builder {
        private final Map<Class<?>, Experiment> experiments = new HashMap();

        Builder() {
        }

        Builder b(Experiment experiment) {
            this.experiments.put(experiment.getClass(), experiment);
            return this;
        }

        GlideExperiments c() {
            return new GlideExperiments(this);
        }

        Builder d(Experiment experiment, boolean z2) {
            if (z2) {
                b(experiment);
            } else {
                this.experiments.remove(experiment.getClass());
            }
            return this;
        }
    }

    interface Experiment {
    }

    GlideExperiments(Builder builder) {
        this.experiments = Collections.unmodifiableMap(new HashMap(builder.experiments));
    }

    public boolean isEnabled(Class<? extends Experiment> cls) {
        return this.experiments.containsKey(cls);
    }
}
