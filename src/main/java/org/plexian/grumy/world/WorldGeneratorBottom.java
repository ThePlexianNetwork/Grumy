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
package org.plexian.grumy.world;

import org.plexian.grumy.Game;
import org.plexian.grumy.tile.Tile;

public class WorldGeneratorBottom implements Generator{

	public int[][] generateChunk(int[][] tiles, double xPos, double yPos){
	    for(int y = 0; y < tiles.length; y++){
	        for(int x = 0; x < tiles[y].length; x++){
	            tiles[x][y] = Tile.DARKSTONE.getId();
	            tiles[x][y] = Tile.DARKSTONE.getId();
	        }
	    }
		
		return tiles;
	}
	
	public int[][] generateWorld(int[][] tiles){
	    for(int y = 0; y < tiles.length; y++){
            for(int x = 0; x < tiles[y].length; x++){
                tiles[x][y] = Tile.DARKSTONE.getId();
                tiles[x][y] = Tile.DARKSTONE.getId();
            }
        }
        
		
		return tiles;
	}
	
	@Override
	public int[][] generateChunk(Chunk c) {
		return generateChunk(c.getTiles(), c.getPosition().getX(), c.getPosition().getY());
	}

	@Override
	public int[][] generateWorld(World w) {
		int[][] tiles = new int[w.getChunks().size() * (int)Game.CHUNK_SIZE][w.getChunks().size() * (int)Game.CHUNK_SIZE];
		
		for(Chunk c : w.getChunks()){
			for(int x = 0; x < c.getTiles().length; x++){
				for(int y = 0; y < c.getTiles().length; y++){
					tiles[x + (int)c.getPosition().x][y + (int)c.getPosition().y] = c.getTile(x, y);
				}
			}
		}
		
		return generateWorld(tiles);
	}
}
