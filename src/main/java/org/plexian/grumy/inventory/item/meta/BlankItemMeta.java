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
package org.plexian.grumy.inventory.item.meta;

import java.util.ArrayList;
import java.util.List;

public class BlankItemMeta implements ItemMeta {
    private String displayName = "";
    private List<String> lore = new ArrayList<String>();
    private boolean unbreakable;
    
    @Override
    public boolean hasDisplayName() {
        return true;
    }

    @Override
    public boolean hasLore() {
        return true;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public List<String> getLore() {
        return lore;
    }

    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    @Override
    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    @Override
    public boolean isUnbreakable() {
        return unbreakable;
    }

    @Override
    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    @Override
    public ItemMeta clone() {
        BlankItemMeta meta = new BlankItemMeta();
        
        meta.setDisplayName(this.displayName);
        meta.setLore(this.lore);
        meta.setUnbreakable(this.unbreakable);
        
        return meta;
    }
}
