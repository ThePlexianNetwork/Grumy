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
package main.java.org.plexian.grumy.entity.player;

import main.java.org.plexian.grumy.effect.EffectRenderer.EffectType;

public class PlayerEffect {
    private Player player;
    private PlayerEffectType effectType;

    public enum PlayerEffectType {
        NONE("none"), DIE("die"), HURT("hurt");

        private String name;
        private Player p;

        PlayerEffectType(String name) {
            this.name = name;
        }
    }

    public PlayerEffect(Player player) {
        this.player = player;
    }

    public void render() {
        // EffectRenderer.renderEffect(EffectType.EXPLOSION,
        // this.player.getLocation().getX(), this.player.getLocation().getY());
    }

    public void setEffectType(PlayerEffectType type) {
        this.effectType = type;
    }
}