/**
 * Copyright 2016 The Plexian Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package main.java.org.plexian.grumy.effect;

import java.util.Random;

import main.java.org.plexian.grumy.math.Vector2d;
import main.java.org.plexian.grumy.world.particle.ParticleFactory;

public class EffectRenderer {

    public enum EffectType {
        NONE, EXPLOSION, FIZZLE;
    }

    public void renderEffect(EffectType type, double x, double y) {
        ParticleFactory factory = new ParticleFactory(new Vector2d(x, y));

        if (type == EffectType.EXPLOSION) {
            factory.render();
        }
    }
}
