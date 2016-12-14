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
package org.plexian.grumy.launcher;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Random;
import java.util.logging.ConsoleHandler;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.plexian.grumy.Game;
import org.plexian.grumy.PlexianLogFormatter;
import org.plexian.grumy.configuration.YAMLConfiguration;
import org.plexian.grumy.world.World;

public class Launcher extends JFrame {
    private static final long serialVersionUID = 1L;
    public static final String GAME_FOLDER = "Grumy";
    
    private LauncherConnectionManager connectionManager;
    private JPanel loginPanel;
    private JPanel gamePanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField worldField;
    private Checkbox loadWorldField;
    private Checkbox levelEditorModeField;
    private JButton loginButton;
    public String[] args;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel messageLabel;
    private JLabel worldLabel;

    private LauncherMessage loginMessage;
    private boolean authenticated = false;
    private String result = "";

    public static void main(String args[]) {
        @SuppressWarnings("unused")
        Launcher launcher = new Launcher(args);
    }

    public Launcher(String[] args) {
        super("Login - Pendulum - Plexian");

        this.args = args;
        this.connectionManager = new LauncherConnectionManager("http://files.plexian.org/api.php");
        this.loginPanel = new JPanel();
        this.gamePanel = new JPanel();
        this.loginButton = new JButton("Get Playing!");
        this.usernameField = new JTextField(15);
        this.passwordField = new JPasswordField(15);
        this.worldField = new JTextField(15);
        this.usernameLabel = new JLabel("Username: ");
        this.passwordLabel = new JLabel("Password: ");
        this.messageLabel = new JLabel("");
        this.worldLabel = new JLabel("World: ");
        this.loadWorldField = new Checkbox("Load world?");
        this.levelEditorModeField = new Checkbox("Level editor mode?");
        this.setSize(800, 640);
        this.setLocation(500, 280);

        this.loginPanel.setLayout(new FlowLayout());
        this.loginPanel.add(usernameLabel);
        this.loginPanel.add(usernameField);
        this.loginPanel.add(passwordLabel);
        this.loginPanel.add(passwordField);
        this.loginPanel.add(worldLabel);
        this.loginPanel.add(worldField);
        
        this.gamePanel.setLayout(new FlowLayout());
        this.gamePanel.add(worldLabel);
        this.gamePanel.add(worldField);
        this.gamePanel.add(loadWorldField);
        this.gamePanel.add(levelEditorModeField);
        this.getContentPane().add(gamePanel);
        this.loginPanel.add(gamePanel);
        this.loginPanel.add(loginButton);
        
        this.getContentPane().add(loginPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usernameField.getText().length() > 0 && passwordField.getText().length() > 0) {
                    usernameField.setBackground(Color.WHITE);
                    passwordField.setBackground(Color.WHITE);

                    try {
                        result = connectionManager.issueGetRequest("request=login&username="
                                + URLEncoder.encode(usernameField.getText()) + "&password=" + MessageDigest
                                        .getInstance("MD5").digest(new String(passwordField.getText()).getBytes())
                                + "&from=launcher");

                        if (result.equals("valid") && result.indexOf("valid") > -1) {
                            loginMessage = LauncherMessage.VALID_LOGIN;
                            authenticated = true;
                        } else {
                            loginMessage = LauncherMessage.INVALID_LOGIN;
                            authenticated = false;
                        }
                    } catch (Exception ex) {
                        loginMessage = LauncherMessage.SERVER_ERROR;
                    }

                    loginMessage = LauncherMessage.INVALID_LOGIN;
                    messageLabel.setText(loginMessage.getMessage());
                    loginPanel.add(messageLabel);
                    loginPanel.repaint();
                    getContentPane().remove(loginPanel);
                    getContentPane().add(loginPanel);
                    getContentPane().revalidate();
                    getContentPane().repaint();

                    login();
                } else {
                    if (usernameField.getText().length() <= 0) {
                        usernameField.setBackground(Color.RED);
                    }

                    if (passwordField.getText().length() <= 0) {
                        passwordField.setBackground(Color.RED);
                    }
                }
            }
        });
    }

    public void login() {
        authenticated = true;

        if (authenticated == true) {
            try {
                Game.LOG.setUseParentHandlers(false);

                /**
                 * Here we setup the log so that it looks pretty.
                 */
                PlexianLogFormatter formatter = new PlexianLogFormatter();
                ConsoleHandler consoleHandler = new ConsoleHandler();

                consoleHandler.setFormatter(formatter);
                Game.LOG.addHandler(consoleHandler);

                /**
                 * Let's just print out a little information, just to be nice to
                 * the nerds who read the console.
                 */
                Game.LOG.info("-------------------------------------------------------------");
                Game.LOG.info("----------            Welcome to Grumy             ----------");
                Game.LOG.info("-------------------------------------------------------------");
                Game.LOG.info("-  Copyright 2015-2016, Plexian Studios all rights reserved -");
                Game.LOG.info("-    For support visit github.com/ThePlexianNetwork/Grumy   -");
                Game.LOG.info("-   Make sure to check out our website: http://plexian.org  -");
                Game.LOG.info("-------------------------------------------------------------");
                Game.LOG.info("Creating game class.");

                Game game = new Game();

                /**
                 * Here we load Pendulum.yml as the main configuration.
                 */
                Game.CONFIG = new YAMLConfiguration("Grumy.yml");

                Game.LOG.info("Setting up enviroment...");

                if (Game.CONFIG.getRootSection().getString("world.name") != null) {
                    Game.WORLD_NAME = Game.CONFIG.getRootSection().getString("world.name");
                } else {
                    try {
                        String worldName = World.WORLD_NAMES[new Random().nextInt(World.WORLD_NAMES.length - 1)];
                        
                        Game.CONFIG.getRootSection().put("world.name", World.WORLD_NAMES[new Random().nextInt(World.WORLD_NAMES.length - 1)]);
                        Game.CONFIG.getRootSection().put("setup", true);
                        Game.CONFIG.save();
                        Game.WORLD_NAME = worldName;
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new IllegalStateException("Unable to open/create Grumy.yml configuration file. Please check file permissions. Addition data above.");
                    }
                }

                Game.LOAD_WORLD = loadWorldField.getState();
                Game.LEVEL_BUILDER = levelEditorModeField.getState();

                Game.LOG.info("Loading native libraries...");
                OSUtils.loadNatives();

                Game.LOG.info("Starting Pendulum game.");
                game.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            loginMessage = LauncherMessage.INVALID_LOGIN;
            messageLabel.setText(loginMessage.getMessage());
            loginPanel.add(messageLabel);
            loginPanel.repaint();
            this.getContentPane().remove(loginPanel);
            this.getContentPane().add(loginPanel);
            this.getContentPane().revalidate();
            this.getContentPane().repaint();
        }
    }
}
