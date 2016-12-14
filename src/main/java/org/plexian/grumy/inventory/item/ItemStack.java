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
package org.plexian.grumy.inventory.item;

import org.plexian.grumy.inventory.item.meta.BlankItemMeta;
import org.plexian.grumy.inventory.item.meta.ItemMeta;
import org.plexian.grumy.material.Material;

public class ItemStack {
    private int type = 0, amount = 0;
    private short durability = 0;
    private boolean canDrop = true;
    private ItemMeta meta;
    
    public ItemStack(Material material){
        this(material, 1);
    }
    
    public ItemStack(Material material, int amount){
        this(material, amount, new BlankItemMeta());
    }

    public ItemStack(Material material, int amount, ItemMeta meta){
        this(material, amount, meta, (short)0);
    }
    
    public ItemStack(Material material, int amount, ItemMeta meta, short durability){
        this(material, amount, meta, durability, true);
    }
    
    public ItemStack(Material material, int amount, ItemMeta meta, short durability, boolean canDrop){
        this.type = material.getMaterialId();
        this.amount = amount;
        this.meta = meta.clone();
        this.durability = durability;
        this.canDrop = canDrop;
    }
    
    public Material getType(){
        return Material.getMaterial(this.type);
    }
    
    public int getTypeId(){
        return this.type;
    }
}
