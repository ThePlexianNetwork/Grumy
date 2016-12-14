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
package org.plexian.grumy.tile;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.plexian.grumy.Game;
import org.plexian.grumy.entity.player.Player;
import org.plexian.grumy.opengl.SpriteSheet;

/**
 * The main class for tiles. Holds a static map of all tiles and their ids.
 * @author Walt
 * @since 0.0.1
 */
public abstract class Tile {
	/**
	 * The map where we store all the tiles.
	 */
	public static HashMap<Integer, Tile> tileMap = new HashMap<Integer, Tile>();
	
	/**
	 * These are instances of all the tiles.
	 */
	public static Tile AIR = new TileAir();
	public static Tile DARKSTONE = new TileDarkstone();
	public static Tile FIRE_MAGIC = new TileFireMagic();
	public static Tile PLAYER_LEFT = new TilePlayerLeft();
	public static Tile PLAYER_RIGHT = new TilePlayerRight();
	public static Tile ITEM_SLOT = new TileItemSlot();
	
	public abstract int getId();
	public abstract boolean isTransparent();
	public abstract boolean isAnimated();
	public abstract boolean isSolid();

	public abstract float[] getTextureCoordinates();

	static{
		Tile.registerTile(0, AIR);
		Tile.registerTile(1, DARKSTONE);
		Tile.registerTile(2, FIRE_MAGIC);
		Tile.registerTile(901, PLAYER_LEFT);
		Tile.registerTile(900, PLAYER_RIGHT);
		Tile.registerTile(902, ITEM_SLOT);
	}
	
	/**
	 * Get an instance of Tile with id id.
	 * @param id The id of the tile.
	 * @return An instance of tile with id id, or an instance of TileAir.
	 */
	public static Tile getTile(int id){
		return tileMap.get(id) != null ? tileMap.get(id) : Tile.AIR;
	}
	
	public static void registerTile(int id, Tile tile){
		tileMap.put(id, tile);
	}
	
	/**
	 * Draw a tile at (x, y) with size size.
	 * @param x The X-coordinate to draw tile at (in world coordinates).
	 * @param y The Y-coordinate to draw tile at (in world coordinates).
	 * @param size The size (in tile sizes) to draw the tile as.
	 */
	public static void draw(double x, double y, int id, float size){
	    GL11.glColor3f(0, 0, 0);
		float[] textureCoordinates = Tile.getTile(id).getTextureCoordinates();
		textureCoordinates[0] *= SpriteSheet.tileTextures.uniformSize();
		textureCoordinates[1] *= SpriteSheet.tileTextures.uniformSize();
		
		x *= Game.TILE_SIZE;
		y *= Game.TILE_SIZE;
		size *= Game.TILE_SIZE;
		
		if(Tile.getTile(id).isTransparent()){
			//GL11.glEnable(GL11.GL_ALPHA_TEST);
		//	GL11.glAlphaFunc(GL11.GL_GREATER, 0.0f);
		}
		
		GL11.glBegin(GL11.GL_QUADS);
		
		GL11.glTexCoord2f(textureCoordinates[0], textureCoordinates[1] + SpriteSheet.tileTextures.uniformSize());
			GL11.glVertex2d(x, y);
		GL11.glTexCoord2f(textureCoordinates[0] + SpriteSheet.tileTextures.uniformSize(), textureCoordinates[1] + SpriteSheet.tileTextures.uniformSize());
			GL11.glVertex2d(x + size, y);
		GL11.glTexCoord2f(textureCoordinates[0] + SpriteSheet.tileTextures.uniformSize(), textureCoordinates[1]);
			GL11.glVertex2d(x + size, y + size);
		GL11.glTexCoord2f(textureCoordinates[0], textureCoordinates[1]);
		    GL11.glVertex2d(x, y + size);
		
		GL11.glEnd();
	}
	
	public static void drawPlayer(Player p, int x, int y){
	    GL11.glColor3f(0, 0, 0);
		float[] textureCoordinates = p.getSprite().getTextureCoordinates();
		textureCoordinates[0] *= SpriteSheet.tileTextures.uniformSize();
		textureCoordinates[1] *= SpriteSheet.tileTextures.uniformSize();
		
		x *= Game.TILE_SIZE;
		y *= Game.TILE_SIZE;
		float size = Game.PLAYER_SIZE;
		
		if(p.getSprite().isTransparent()){
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.0f);
		}
		
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(textureCoordinates[0], textureCoordinates[1] + SpriteSheet.tileTextures.uniformSize());
			GL11.glVertex2f(x, y);
		GL11.glTexCoord2f(textureCoordinates[0] + SpriteSheet.tileTextures.uniformSize(), textureCoordinates[1] + SpriteSheet.tileTextures.uniformSize());
			GL11.glVertex2f(x + size, y);
		
		GL11.glTexCoord2f(textureCoordinates[0] + SpriteSheet.tileTextures.uniformSize(), textureCoordinates[1]);
			GL11.glVertex2f(x + size, y + size);
		
		GL11.glTexCoord2f(textureCoordinates[0], textureCoordinates[1]);
			GL11.glVertex2f(x, y + size);
		
		GL11.glEnd();
	}
}