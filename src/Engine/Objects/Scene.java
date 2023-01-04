package Engine.Objects;

import java.util.ArrayList;
import java.util.List;

import Engine.Rendering.Renderer;

public class Scene {
    public List<IDrawable> gameObjects = new ArrayList<IDrawable>();

    private Renderer renderer;

    public Scene()
    {
        renderer = new Renderer();
    }

    public void Render(Camera camera)
    {
        for(IDrawable gameObject : gameObjects)
        {
            if(gameObject == null)
                continue;
                
            renderer.RenderGameObject(camera, gameObject);
        }
    }

    public void AddObject(IDrawable gameObject)
    {
        gameObjects.add(gameObject);
    }

    public void DestroyObject(IDrawable gameObject)
    {
        if(gameObject == null)
            return;

        gameObjects.remove(gameObject);
        gameObject.Destroy();
    }

    public void Destroy()
    {
        for (IDrawable gameObject : gameObjects) {
            gameObject.Destroy();
        }
    }
}
