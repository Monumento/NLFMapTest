package mygame;

import CliientMap.ClientMap;
import CliientMap.ClientMapSpatial;
import Connection.Client;

import JAVARMI.WeltInterface;

import SettingsGUI.SettingsGUI;
import Connection.TCPIP.ClientThread;
import Mapping.WeltElementMapping;
import Welt.NLFWelt;

import Welt.Welt;
import Welt.biome.Ebene;
import beduerfnisse.tiere.GrundBeduerfnisse;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;

import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;

import com.jme3.math.ColorRGBA;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;

import com.jme3.scene.Spatial;
import combatSystem.UnitSpatialandGhostControl;
import java.util.HashMap;

/**
 * test
 *
 * @author normenhansen
 */
public class MainClientScript extends SimpleApplication implements ActionListener {

	public void displayPart(int i, int j, int k, int elementNumber) {
		// parameter weltelement
		if (!loadedParts.containsKey(map.map[0].welt.worldBlocks.weltElemente[i][j][k].weltElementID)) {

			Vector3f position = map.map[0].welt.worldBlocks.weltElemente[i][j][k].positon.clone();
			position = position.mult(2f).add(new Vector3f(0, -50, 0));
			Spatial terrain = assetManager.loadModel("assets/Models/lowResDirt/lowResDirt.j3o");

			RigidBodyControl landscapeControl = new RigidBodyControl(0);
			terrain.setLocalTranslation(position);

			terrain.addControl(landscapeControl);
			ClientMapSpatial mapSpatial = new ClientMapSpatial(map.map[0].welt.worldBlocks.weltElemente[i][j][k],
					terrain, landscapeControl);
			loadedParts.put(map.map[0].welt.worldBlocks.weltElemente[i][j][k].weltElementID, mapSpatial);
			if (elementNumber != 2) {
				rootNode.attachChild(terrain);
				bulletAppState.getPhysicsSpace().add(landscapeControl);
			}
		}
	}

	// game connection
	// engine
	private BulletAppState bulletAppState;
	private GhostControl ghostComputer, ghostFeuerball;
	private CharacterControl player;
	private Vector3f walkDirection = new Vector3f(0.0f, 0.0f, 0.0f);
	private boolean two, one, left, right, up, down, jump, run, shoot, firstStart = true, speichern, laden;
	private GhostControl unitControl[], grassControl;
	private Spatial units[];
	int unitCount = 0;
	// mygame
	public static UnitSpatialandGhostControl unit, playerThings, otherPlayer;
	public static WeltInterface welt1;
	int count = 0;
	int saveCount = 0;
	long lastTime;
	// combatSystem
	Quaternion q, q2, q3;
	Welt welt;
	// Connection
	public static ClientMap map;
	public static Client client;
	HashMap<Integer, ClientMapSpatial> loadedParts;

	public static void main(String[] args) {
		//

		// Choose Server/CLient
		// SettingsGUI settings = new SettingsGUI();
		// settings.inputSettings();
		// startMain

		MainClientScript app = new MainClientScript();
		app.start();
		// TCP/IP Configuration

		System.out.print("Setup Finished");
	}

	@Override
	public void simpleInitApp() {
		WeltElementMapping.initMapping();
		loadedParts = new HashMap<Integer, ClientMapSpatial>();
		map = new ClientMap();
		map.setMainCLient(this);
		// serveretst
		count = 0;
		// digging

		// camera
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		// World
		initClient();
		initPlayer();
		initKeys();

		initLight();
		initSky();

		// combatSystem
		q = new Quaternion();
		q2 = new Quaternion();
		// von 155 bis -155 x und z
		// max w wert anpassen oder anderen wert finden
		q2.fromAngles(155, 0, 0);

	}

	@Override
	public void simpleUpdate(float tpf) {

		// receive messages
		long lastTime = System.currentTimeMillis();
		// last servermessage to compare for updates
		// player updates(-health, changedModel)
		// ground = "...*1*1*5*5*1*5*17*..."
		// units = "position and rotation*collisionshape size*unitSize,model"
		// plants = "size collshape* model"
		// servermessage = playerUpdates;ground;units;plants;
		// clientmessage = pos,rotation,hit?
		String[] s = new String[1];
		s[0] = "noMessage";
		if (player != null) {
			if (player.getPhysicsLocation().x > 10 && player.getPhysicsLocation().z > 10) {
				s[0] = "pos:" + (int) player.getPhysicsLocation().x + "," + (int) (player.getPhysicsLocation().y) + ","
						+ (int) player.getPhysicsLocation().z;
				map.addPlayerPosition(cam.getLocation());
				System.out.println(s[0]);
			}

		}
		client.updateToServer(s);
		String[] temp = client.getUpdateFromServer();
		// connection id suche

		// System.out.println(temp[0]);
		if (temp != null && temp.length > 0 && temp[0] != null) {
			String[] commandTypeAndText = temp[0].split(":");
			if (commandTypeAndText[0].contains("map")) {
				if (commandTypeAndText.length > 1) {
					map.updateMap(commandTypeAndText[1]);
					map.displayParts();

				}
			}
		}
		// send Messages
		if (playerThings != null) {
			playerMovement();
		}
		// wait
		// while (System.currentTimeMillis() - lastTime < 5)
		// ;
	}

	@Override
	public void simpleRender(RenderManager rm) {
		// TODO: add render code
	}

	private void initPlayer() {

		// unitSpatial kennt rootNode und bulletappstate damit das hier nicht
		// mehr in der main steht
		CapsuleCollisionShape playerShape = new CapsuleCollisionShape(1f, 1.5f);

		player = new CharacterControl(playerShape, 0.5f);

		Spatial playerSpatial = assetManager.loadModel("assets/Models/CrystalGuy/CrystalGuy.j3o");

		Spatial weaponSpatial = assetManager.loadModel("assets/Models/weapon002/weapon002.j3o");
		CapsuleCollisionShape collSHape = new CapsuleCollisionShape(0.2f, 1.5f);
		GhostControl weaponGhost = new GhostControl(collSHape);
		weaponSpatial.addControl(weaponGhost);
		rootNode.attachChild(weaponSpatial);
		rootNode.attachChild(playerSpatial);

		player.setFallSpeed(0);
		player.setGravity(800);
		player.setJumpSpeed(75);
		player.setPhysicsLocation(new Vector3f(5, 1, 5.0f));
		weaponGhost.setPhysicsLocation(new Vector3f(100, 100, 100));
		bulletAppState.getPhysicsSpace().add(weaponGhost);

		bulletAppState.getPhysicsSpace().add(player);

		playerThings = new UnitSpatialandGhostControl(playerSpatial, 1, true);
		Node node = new Node("myNode");
		node.attachChild(weaponSpatial);
		playerThings.addWeapon(weaponSpatial, weaponGhost, node);

		rootNode.attachChild(node);
		// wenn multiplayer TODO
		playerThings.setPhysicsLocation(player.getPhysicsLocation());
		playerThings.camera = cam;

		cam.setLocation(new Vector3f(25, 50, 25));
		playerThings.setPhysicsLocation(new Vector3f(25, 50, 25));
		player.setPhysicsLocation(new Vector3f(25, 50, 25));
	}

	private void initLight() {
		AmbientLight ambientLight = new AmbientLight();
		ambientLight.setColor(ColorRGBA.White);
		rootNode.addLight(ambientLight);

		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(-1, -1, -1));
		sun.setColor(ColorRGBA.White);
		rootNode.addLight(sun);
	}

	private void initSky() {
		Spatial sky = assetManager.loadModel("Scenes/Sky.j3o");
		rootNode.attachChild(sky);
	}

	public void initKeys() {

		inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
		inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
		inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
		inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
		inputManager.addMapping("Run", new KeyTrigger(KeyInput.KEY_LSHIFT));
		inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addMapping("One", new KeyTrigger(KeyInput.KEY_1));
		inputManager.addMapping("Two", new KeyTrigger(KeyInput.KEY_2));
		inputManager.addMapping("Speichern", new KeyTrigger(KeyInput.KEY_0));
		inputManager.addMapping("Laden", new KeyTrigger(KeyInput.KEY_9));

		inputManager.addListener(this, "Left");
		inputManager.addListener(this, "Right");
		inputManager.addListener(this, "Up");
		inputManager.addListener(this, "Down");
		inputManager.addListener(this, "Jump");
		inputManager.addListener(this, "Run");
		inputManager.addListener(this, "Shoot");
		inputManager.addListener(this, "One");
		inputManager.addListener(this, "Two");
		inputManager.addListener(this, "Speichern");
		inputManager.addListener(this, "Laden");

	}

	public void onAction(String name, boolean isPressed, float tpf) {
		if (name.equals("Left")) {
			left = isPressed;
		} else if (name.equals("Right")) {
			right = isPressed;
		} else if (name.equals("Up")) {
			up = isPressed;
		} else if (name.equals("Down")) {
			down = isPressed;
		} else if (name.equals("Jump")) {
			player.jump();
		} else if (name.equals("Run")) {
			run = isPressed;
		} else if (name.equals("One") && !isPressed) {
			count++;
			// optimize TODO >0
			// player.setPhysicsLocation(player.getPhysicsLocation().add(cam.getDirection().mult(50)));
		} else if (name.equals("Two") && !isPressed) {
			two = isPressed;
			if (player.getFallSpeed() == 0) {

				player.setFallSpeed(50);
			} else {
				player.setFallSpeed(0);
			}

			// walkDirection.set(cam.getDirection().mult(20));
			// player.setWalkDirection(walkDirection);
			// cam.setLocation(player.getPhysicsLocation());
		}
		if (name.equals("Speichern")) {

			System.out.println(1);
		}
		if (name.equals("Laden")) {

			System.out.println(2);
		}
		if (name.equals("Shoot") && !isPressed) {
			shootFireball();
		}

	}

	private void initUnits() {
		// bulletAppState.getPhysicsSpace().add(cowControl);
	}

	public void shootFireball() {
		if (!playerThings.hitting) {
			playerThings.hitting = true;
		}

	}

	private void initClient() {
		client = new Client("127.0.0.1", 1000, 4);
		client.startClient();
		client.startNewConnectionToServer("127.0.0.1", 1000);
	}

	private void playerMovement() {
		playerThings.updateRotationPlayer();

		// Test
		if (playerThings.hitting) {
			playerThings.getWeapons()[0].node.getLocalRotation().slerp(q3, 0.5f);
			float comparePThingsX = (float) (((int) (playerThings.getWeapons()[0].node.getLocalRotation().getX()
					* 1000)) / 1000.0);
			float compareQuantX = (float) (((int) (q3.getX() * 1000)) / 1000.0);
			float comparePThingsY = (float) (((int) (playerThings.getWeapons()[0].node.getLocalRotation().getY()
					* 1000)) / 1000.0);
			float compareQuantY = (float) (((int) (q3.getY() * 1000)) / 1000.0);

			float comparePThingsZ = (float) (((int) (playerThings.getWeapons()[0].node.getLocalRotation().getZ()
					* 1000)) / 1000.0);
			float compareQuantZ = (float) (((int) (q3.getZ() * 1000)) / 1000.0);
			float comparePThingsW = (float) (((int) (playerThings.getWeapons()[0].node.getLocalRotation().getW()
					* 1000)) / 1000.0);
			float compareQuantW = (float) (((int) (q3.getW() * 1000)) / 1000.0);

			if ((comparePThingsZ == compareQuantZ && comparePThingsX == compareQuantX
					&& comparePThingsY == compareQuantY && comparePThingsW == compareQuantW)
					|| (comparePThingsZ == -compareQuantZ && comparePThingsX == -compareQuantX
							&& comparePThingsY == -compareQuantY && comparePThingsW == -compareQuantW)) {
				playerThings.hitting = false;
				playerThings.getWeapons()[0].node.setLocalRotation(q);
			}
		} else {
			q3 = new Quaternion();
			if (cam.getDirection().getX() > 0) {
				q3 = q3.fromAngles(90, -110 - (cam.getDirection().z + 1f) * 1.5f, 50);
			} else {
				q3 = q3.fromAngles(90, 110 + (cam.getDirection().z + 1f) * 1.5f, 50);
			}
		}
		// TODO

		// Player moveMent
		Vector3f camDir = cam.getDirection().multLocal(2);
		Vector3f camLeft = cam.getLeft().multLocal(2);

		walkDirection.set(0.0f, 0.0f, 0.0f);

		if (left) {
			if (run) {
				walkDirection.addLocal(camLeft).mult(0.4f);
			} else {
				walkDirection.addLocal(camLeft).mult(0.2f);
			}
		} else if (right) {
			if (run) {
				walkDirection.addLocal(camLeft.negate().mult(0.4f));
			} else {
				walkDirection.addLocal(camLeft.negate().mult(0.2f));
			}
		} else if (up) {
			if (run) {
				walkDirection.addLocal(camDir.mult(0.4f));
			}
			walkDirection.addLocal(camDir.mult(0.2f));
		} else if (down) {
			if (run) {
				walkDirection.addLocal(camDir.negate().mult(0.4f));
			}
			walkDirection.addLocal(camDir.negate().mult(0.2f));
		}

		walkDirection.setY(0.0f);
		player.setWalkDirection(walkDirection);
		cam.setLocation(player.getPhysicsLocation());

	}
}
