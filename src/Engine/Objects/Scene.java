package Engine.Objects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import Engine.Rendering.Renderer;

public class Scene {
    public List<GameObject> gameObjects = new ArrayList<GameObject>();

    private Renderer renderer;

    public Scene()
    {
        renderer = new Renderer();
    }

    public void Render(Camera camera)
    {
        for(GameObject gameObject : gameObjects)
        {
            if(gameObject == null)
                continue;
                
            renderer.RenderGameObject(camera, gameObject);
        }
    }

    public void AddObject(GameObject gameObject)
    {
        gameObjects.add(gameObject);
    }

    public void DestroyObject(GameObject gameObject)
    {
        if(gameObject == null)
            return;

        gameObjects.remove(gameObject);
        gameObject.Destroy();
    }

    public void Destroy()
    {
        for (GameObject gameObject : gameObjects) {
            gameObject.Destroy();
        }
    }
}
