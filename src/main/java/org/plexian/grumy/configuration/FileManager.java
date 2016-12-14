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
package org.plexian.grumy.configuration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.plexian.grumy.Game;
import org.plexian.grumy.launcher.Launcher;
import org.plexian.grumy.opengl.Shader;
import org.plexian.grumy.opengl.ShaderProgram;
import org.plexian.grumy.world.Chunk;
import org.plexian.grumy.world.Chunk.ChunkState;
import org.plexian.grumy.world.World;

/**
 * The chunk and world loader and saver.
 * 
 * @author walt
 * @since 0.3
 */
public class FileManager {
    /**
     * This is a File object pointing to where Pendulum is located.
     */
    private File gameDirectory;

    /**
     * This is a File object pointing to where the current world is saved to.
     */
    private File currentWorldDirectory;

    private World world;

    /**
     * This method creates a new instance of FileManager, allow for the creation
     * of save files, loading of save files, and saving of chunks.
     * 
     * @param w
     *            The world that contains the chunks we will be saving and
     *            loading.
     */
    public FileManager(World w) {
        this.gameDirectory = new File(System.getProperty("user.home"), Launcher.GAME_FOLDER);

        if (!this.gameDirectory.exists()) {
            this.gameDirectory.mkdirs();
        }

        updateWorldName(w.getName());
    }

    public void updateWorldName(String name) {
        this.currentWorldDirectory = new File(this.gameDirectory, name + "/");

        if (!this.currentWorldDirectory.exists()) {
            this.currentWorldDirectory.mkdirs();
        }
    }

    /**
     * Saves a chunk to disk.
     * 
     * @param c
     *            The chunk to save.
     */
    public void saveChunk(Chunk c) {
        if (c.getState() == ChunkState.BUILDING || c.getState() == ChunkState.DELETING) {
            return;
        }

        c.setState(ChunkState.SAVING);

        try {
            File outDir = new File(this.currentWorldDirectory + System.getProperty("file.separator")
                    + (int) (c.getPosition().getX() / Game.CHUNK_SIZE) + "-"
                    + (int) (c.getPosition().getY() / Game.CHUNK_SIZE) + ".chk");

            outDir.delete();
            outDir.createNewFile();

            DataOutputStream outStream = new DataOutputStream(new FileOutputStream(outDir));

            for (int x = 0; x < Game.CHUNK_SIZE; x++) {
                for (int y = 0; y < Game.CHUNK_SIZE; y++) {
                    outStream.writeInt(c.getTile(x, y));
                }
            }

            outStream.close();
        } catch (Exception e) {
            Game.LOG.severe(e.getLocalizedMessage());
        }

        c.setState(ChunkState.PASSIVE);
    }

    /**
     * Load a chunk from a file on disk.
     * 
     * @param x
     *            The X-coordinate for the chunk.
     * @param y
     *            The Y-coordinate for the chunk.
     * @return The chunk that was saved on disk.
     */
    public Chunk loadChunk(int x, int y) {
        File outDir = new File(
                this.currentWorldDirectory + System.getProperty("file.separator") + x + "-" + y + ".chk");
        
        if(!outDir.exists()){
            Game.LOG.info("Not exist");
            return null;
        }
        
        Shader temp = new Shader("shaders/world.vert", "shaders/world.frag");
        ShaderProgram shaderProgram = new ShaderProgram(temp.getvShader(), temp.getfShader());
        Chunk c = new Chunk(shaderProgram, x, y, (Game.LEVEL_BUILDER == true ? 0 : 1));

        c.setState(ChunkState.LOADING);
        DataInputStream in = null;

        try {
            in = new DataInputStream(new FileInputStream(outDir));

            for (int x1 = 0; x1 < Game.CHUNK_SIZE; x1++) {
                for (int y1 = 0; y1 < Game.CHUNK_SIZE; y1++) {
                    int i = in.readInt();
                    c.setTile(x1, y1, i);
                }
            }

            c.rebuild();
            in.close();
        } catch (Exception e) {
        }

        return c;
    }

    public YAMLConfiguration getConfiguration() {
        YAMLConfiguration config = new YAMLConfiguration("config.yml", this.currentWorldDirectory);

        return config;
    }
}